package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.RealStateSpace;
import net.rocketeer.jbl.model.variable.Variable;

public class NormalDistribution extends Distribution {
  private final Distribution muPrior;
  private final Distribution variancePrior;
  private final Variable<Double> muVar;
  private final Variable<Double> sigma2Var;
  private final Variable<Double> xVar;
  private org.apache.commons.math3.distribution.NormalDistribution dist;

  private NormalDistribution(Variable<Double> muVar, Variable<Double> sigma2Var, Distribution muPrior, Distribution variancePrior,
                             Variable<Double> xVar) {
    this.muVar = muVar;
    this.sigma2Var = sigma2Var;
    this.muPrior = muPrior;
    this.variancePrior = variancePrior;
    this.xVar = xVar;
    this.refresh();
  }

  private void refresh() {
    double mu = this.muPrior.sample().read(this.muVar);
    double sigma2 = this.variancePrior.sample().read(this.sigma2Var);
    this.dist = new org.apache.commons.math3.distribution.NormalDistribution(mu, Math.sqrt(sigma2));
  }

  public static Builder builder() {
    return new Builder();
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
      Double sigma2 = pack.read(this.sigma2Var);
      if (mu == null)
        mu = this.muPrior.sample().read(this.muVar);
      if (sigma2 == null)
        sigma2 = this.variancePrior.sample().read(this.sigma2Var);
      this.dist = new org.apache.commons.math3.distribution.NormalDistribution(mu, Math.sqrt(sigma2));
    }
    double val = this.dist.sample();
    return IOBundle.builder().set(this.xVar, val).build();
  }

  public static class Builder {
    private Variable<Double> muVar = new Variable<>(new RealStateSpace());
    private Variable<Double> sigma2Var = new Variable<>(new RealStateSpace());
    private Distribution muPrior;
    private Distribution sigma2Prior;
    private Variable<Double> xVar = new Variable<>(new RealStateSpace());

    protected Builder() {}

    public Builder mu(Variable<Double> variable, Distribution distribution) {
      this.muPrior = distribution;
      this.muVar = variable;
      return this;
    }

    public Builder sigma2(Variable<Double> variable, Distribution distribution) {
      this.sigma2Prior = distribution;
      this.sigma2Var = variable;
      return this;
    }

    public Builder mu(double mu) {
      this.muPrior = DiracDeltaDistribution.createConstant(mu, this.muVar);
      return this;
    }

    public Builder sigma2(double sigma2) {
      this.sigma2Prior = DiracDeltaDistribution.createConstant(sigma2, this.sigma2Var);
      return this;
    }

    public Builder response(Variable<Double> x) {
      this.xVar = x;
      return this;
    }

    public NormalDistribution build() {
      return new NormalDistribution(this.muVar, this.sigma2Var, this.muPrior, this.sigma2Prior, this.xVar);
    }
  }
}
