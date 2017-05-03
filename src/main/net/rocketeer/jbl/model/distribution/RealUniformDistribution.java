package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.Variable;
import net.rocketeer.jbl.model.variable.set.IntegerStateSpace;
import net.rocketeer.jbl.model.variable.set.RealStateSpace;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RealUniformDistribution extends NumericalDistribution<Double> {
  private final double lowerBound;
  private final double upperBound;
  private final NumericalVariable<Double> xVar;

  private RealUniformDistribution(NumericalVariable<Double> realVar) {
    this.lowerBound = this.response().stateSpace().lowerBound();
    this.upperBound = this.response().stateSpace().upperBound();
    this.xVar = realVar;
  }

  @Override
  public double probabilityAt(IOBundle pack) {
    return 0;
  }

  @Override
  public IOBundle sample(IOBundle given) {
    double val = ThreadLocalRandom.current().nextDouble(this.lowerBound, this.upperBound);
    return IOBundle.builder().set(this.response(), val).build();
  }

  @Override
  public NumericalVariable<Double> response() {
    return this.xVar;
  }

  @Override
  public Set<Variable<?>> domain() {
    return Collections.singleton(this.xVar);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Double lowerBound;
    private Double upperBound;
    private NumericalVariable<Double> xVar;

    public Builder lowerBound(double lowerBound) {
      this.lowerBound = lowerBound;
      return this;
    }

    public Builder upperBound(double upperBound) {
      this.upperBound = upperBound;
      return this;
    }

    public Builder response(NumericalVariable<Double> var) {
      this.xVar = var;
      return this;
    }

    public RealUniformDistribution build() {
      Double a = Double.MIN_VALUE;
      Double b = Double.MAX_VALUE;
      if (this.lowerBound != null)
        a = this.lowerBound;
      if (this.upperBound != null)
        b = this.upperBound;
      if (this.xVar == null)
        this.xVar = new NumericalVariable<>(new RealStateSpace(a, b));
      return new RealUniformDistribution(this.xVar);
    }
  }
}
