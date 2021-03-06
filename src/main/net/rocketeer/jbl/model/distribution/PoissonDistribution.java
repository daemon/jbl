package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.set.IntegerStateSpace;
import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.set.RealStateSpace;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PoissonDistribution extends NumericalDistribution<Integer> {
  private final Variable<Double> muVar;
  private final Distribution muPrior;
  private final NumericalVariable<Integer> xVar;
  private org.apache.commons.math3.distribution.PoissonDistribution dist;

  protected PoissonDistribution(Variable<Double> muVar, Distribution muPrior, NumericalVariable<Integer> xVar) {
    this.muVar = muVar;
    this.muPrior = muPrior;
    this.xVar = xVar;
    this.refresh();
  }

  private void refresh() {
    double mu = this.muPrior.sample().read(this.muVar);
    this.dist = new org.apache.commons.math3.distribution.PoissonDistribution(mu);
  }

  @Override
  public double probabilityAt(IOBundle pack) {
    this.refresh();
    int value = pack.read(this.response());
    return this.dist.probability(value);
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    if (pack.isEmpty())
      this.refresh();
    else {
      Double mu = pack.read(this.muVar);
      if (mu == null)
        mu = this.muPrior.sample().read(this.muVar);
      this.dist = new org.apache.commons.math3.distribution.PoissonDistribution(mu);
    }
    int val = this.dist.sample();
    pack = IOBundle.builder().set(this.xVar, val).build();
    return IOBundle.builder().add(pack).set(this.probability(), this.probabilityAt(pack)).build();
  }

  @Override
  public NumericalVariable<Integer> response() {
    return this.xVar;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public Set<Variable<?>> domain() {
    return new HashSet<>(Arrays.asList(this.response(), this.muVar));
  }

  public static class Builder {
    private NumericalDistribution muPrior;
    private NumericalVariable<Double> muVar;
    private NumericalVariable<Integer> xVar = new NumericalVariable<>(new IntegerStateSpace(0));

    private Builder() {}

    public Builder mu(NumericalDistribution distribution) {
      this.muPrior = distribution;
      NumericalVariable<?> response = distribution.response();
      this.muVar = response.convert(new RealStateSpace().map(response.stateSpace()));
      return this;
    }

    public Builder mu(double mu) {
      NumericalDistribution<Double> dist = DiracDeltaDistribution.createConstant(mu);
      this.muPrior = dist;
      this.muVar = dist.response();
      return this;
    }

    public Builder response(NumericalVariable<Integer> response) {
      this.xVar = response;
      return this;
    }

    public PoissonDistribution build() {
      return new PoissonDistribution(this.muVar, this.muPrior, this.xVar);
    }
  }
}
