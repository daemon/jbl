package net.rocketeer.jbl.sample;

import net.rocketeer.jbl.model.distribution.Distribution;
import net.rocketeer.jbl.model.io.IOBundle;

import java.util.Collections;
import java.util.List;

public class KLDivergenceEstimator implements MonteCarloEstimator {
  private final Distribution<?> d1;
  private final Distribution<?> d2;
  private final List<Sampler> qSamplers;
  private double mean = 0;
  private double mean2 = 0;
  private int nIterations = 0;
  private final List<Sampler> pSamplers;

  public <T> KLDivergenceEstimator(Distribution<T> p, Distribution<T> q) {
    this.d1 = p;
    this.d2 = q;
    this.pSamplers = Collections.singletonList(p);
    this.qSamplers = Collections.singletonList(q);
  }

  public <T> KLDivergenceEstimator(Distribution<T> p, Distribution<T> q, List<Sampler> pSamplers, List<Sampler> qSamplers) {
    this.d1 = p;
    this.d2 = q;
    this.pSamplers = pSamplers;
    this.qSamplers = qSamplers;
  }

  private void iterate() {
    IOBundle bundle = new IOBundle();
    Distribution d1 = this.d1;
    Distribution d2 = this.d2;
    List<Sampler> samplers = this.pSamplers;
    if (Math.random() < 0.5) {
      d1 = this.d2;
      d2 = this.d1;
      samplers = this.qSamplers;
    }
    for (Sampler sampler : samplers)
      bundle.add(sampler.sample());
    double d1p = d1.probabilityAt(bundle);
    double d2p = d2.probabilityAt(bundle.changeVariable(d2.response(), d1.response()));
    double pp = this.d1.probabilityAt(bundle.changeVariable(this.d1.response(), d2.response()));
    if (Math.abs(d1p) < 0.000001)
      this.iterate();
    double divergence = 0;
    if (Math.abs(d2p) > 0.000001)
      divergence = pp * Math.log(d1p / d2p);
    ++this.nIterations;
    this.mean += (divergence - this.mean) / this.nIterations;
    this.mean2 += (divergence * divergence - this.mean2) / this.nIterations;
  }

  @Override
  public MonteCarloEstimate compute(int iterations) {
    for (int i = 0; i < iterations; ++i)
      this.iterate();
    return new MonteCarloEstimate(this.mean, (this.mean2 - this.mean * this.mean) / this.nIterations, this.nIterations);
  }

  public void clear() {
    this.mean = 0;
    this.mean2 = 0;
    this.nIterations = 0;
  }

  @Override
  public MonteCarloEstimate compute(double changeTolerance) {
    double lastMean;
    do {
      lastMean = this.mean;
      for (int i = 0; i < 100; ++i)
        this.iterate();
    } while (Math.abs(this.mean - lastMean) > changeTolerance);
    return new MonteCarloEstimate(this.mean, (this.mean2 - this.mean * this.mean) / this.nIterations, this.nIterations);
  }
}
