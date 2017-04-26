package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.RealStateSpace;
import net.rocketeer.jbl.model.variable.Variable;

public class NormalDistribution implements NumericalDistribution<Double> {
  private final Distribution muPrior;
  private final Distribution sdPrior;
  private final Variable<Double> muVar;
  private final Variable<Double> sigmaVar;
  private final NumericalVariable<Double> xVar;
  private org.apache.commons.math3.distribution.NormalDistribution dist;

  private NormalDistribution(Variable<Double> muVar, Variable<Double> sigmaVar, Distribution muPrior, Distribution variancePrior,
                             NumericalVariable<Double> xVar) {
    this.muVar = muVar;
    this.sigmaVar = sigmaVar;
    this.muPrior = muPrior;
    this.sdPrior = variancePrior;
    this.xVar = xVar;
    this.refresh();
  }

  private void refresh() {
    double mu = this.muPrior.sample().read(this.muVar);
    double sigma = this.sdPrior.sample().read(this.sigmaVar);
    this.dist = new org.apache.commons.math3.distribution.NormalDistribution(mu, sigma);
  }

  public static Builder builder() {
    return new Builder();
  }

  public NumericalVariable<Double> response() {
    return this.xVar;
  }

  @Override
  public double valueAt(IOBundle pack) {
    this.refresh();
    Double x = pack.read(this.xVar);
    return this.dist.density(x);
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    if (pack.isEmpty())
      this.refresh();
    else {
      Double mu = pack.read(this.muVar);
      Double sigma = pack.read(this.sigmaVar);
      if (mu == null)
        mu = this.muPrior.sample().read(this.muVar);
      if (sigma == null)
        sigma = this.sdPrior.sample().read(this.sigmaVar);
      this.dist = new org.apache.commons.math3.distribution.NormalDistribution(mu, sigma);
    }
    double val = this.dist.sample();
    return IOBundle.builder().set(this.xVar, val).build();
  }

  public static class Builder {
    private NumericalVariable<Double> muVar = new NumericalVariable<>(new RealStateSpace());
    private NumericalVariable<Double> sigmaVar = new NumericalVariable<>(new RealStateSpace(0));
    private NumericalDistribution muPrior;
    private NumericalDistribution sigmaPrior;
    private NumericalVariable<Double> xVar = new NumericalVariable<>(new RealStateSpace());

    protected Builder() {}

    public Builder mu(NumericalDistribution distribution) {
      this.muPrior = distribution;
      NumericalVariable<?> response = distribution.response();
      this.muVar = response.convert(new RealStateSpace().map(response.stateSpace()));
      return this;
    }

    public Builder sigma(NumericalDistribution distribution) {
      this.sigmaPrior = distribution;
      NumericalVariable<?> response = distribution.response();
      this.sigmaVar = response.convert(new RealStateSpace().map(response.stateSpace()));
      return this;
    }

    public Builder mu(double mu) {
      this.muPrior = DiracDeltaDistribution.createConstant(mu, this.muVar);
      return this;
    }

    public Builder sigma(double sigma) {
      this.sigmaPrior = DiracDeltaDistribution.createConstant(sigma, this.sigmaVar);
      return this;
    }

    public Builder response(NumericalVariable<Double> x) {
      this.xVar = x;
      return this;
    }

    public NormalDistribution build() {
      return new NormalDistribution(this.muVar, this.sigmaVar, this.muPrior, this.sigmaPrior, this.xVar);
    }
  }
}
