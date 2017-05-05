package net.rocketeer.jbl.compute.estimate;

import net.rocketeer.jbl.compute.Sampler;
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

  public KLDivergenceEstimator(Distribution p, Distribution q) {
    this.d1 = p;
    this.d2 = q;
    this.pSamplers = Collections.singletonList(p);
    this.qSamplers = Collections.singletonList(q);
  }

  public KLDivergenceEstimator(Distribution p, Distribution q, List<Sampler> pSamplers, List<Sampler> qSamplers) {
    this.d1 = p;
    this.d2 = q;
    this.pSamplers = pSamplers;
    this.qSamplers = qSamplers;
  }

  private void iterate() {
    IOBundle bundle = new IOBundle();
    double roll = Math.random(); // For importance sampling, unused for now
    for (Sampler sampler : (roll < 1 ? this.pSamplers : this.qSamplers))
      bundle.add(sampler.sample());
    if (roll < 1)
      bundle = this.d1.sample(bundle);
    else
      bundle = this.d2.sample(bundle);
    double pp = this.d1.probabilityAt(bundle.changeVariable(this.d1.response(), this.d2.response()));
    double qp = this.d2.probabilityAt(bundle.changeVariable(this.d2.response(), this.d1.response()));
    if (Math.abs(qp) < 0.00000001) {
      this.iterate();
      return;
    }
    double ratio = Math.min(pp / qp, 10000000.0);
    double divergence = Math.log(ratio);
    if (roll > 1)
      divergence *= ratio;
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
