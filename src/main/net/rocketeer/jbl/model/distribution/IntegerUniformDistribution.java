package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.Variable;
import net.rocketeer.jbl.model.variable.set.IntegerStateSpace;

import java.util.Collections;
import java.util.Set;

public class IntegerUniformDistribution extends NumericalDistribution<Integer> {
  private final int lowerBound;
  private final int upperBound;
  private final NumericalVariable<Integer> xVar;

  private IntegerUniformDistribution(NumericalVariable<Integer> intVar) {
    this.lowerBound = intVar.stateSpace().lowerBound();
    this.upperBound = intVar.stateSpace().upperBound();
    this.xVar = intVar;
  }

  @Override
  public double probabilityAt(IOBundle pack) {
    return 1.0 / (this.upperBound - this.lowerBound + 1);
  }

  @Override
  public IOBundle sample(IOBundle given) {
    int val = (int) Math.round(((this.upperBound - this.lowerBound) * Math.random()) + this.lowerBound);
    return IOBundle.builder().set(this.response(), val).build();
  }

  @Override
  public NumericalVariable<Integer> response() {
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
    private Integer lowerBound;
    private Integer upperBound;
    private NumericalVariable<Integer> xVar;

    public Builder lowerBound(int lowerBound) {
      this.lowerBound = lowerBound;
      return this;
    }

    public Builder upperBound(int upperBound) {
      this.upperBound = upperBound;
      return this;
    }

    public Builder response(NumericalVariable<Integer> var) {
      this.xVar = var;
      return this;
    }

    public IntegerUniformDistribution build() {
      Integer a = Integer.MIN_VALUE;
      Integer b = Integer.MAX_VALUE;
      if (this.lowerBound != null)
        a = this.lowerBound;
      if (this.upperBound != null)
        b = this.upperBound;
      if (this.xVar == null)
        this.xVar = new NumericalVariable<>(new IntegerStateSpace(a, b));
      return new IntegerUniformDistribution(this.xVar);
    }
  }
}
