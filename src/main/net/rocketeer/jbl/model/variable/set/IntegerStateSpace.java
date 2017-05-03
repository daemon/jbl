package net.rocketeer.jbl.model.variable.set;

import net.rocketeer.jbl.model.distribution.IntegerUniformDistribution;
import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.Variable;
import net.rocketeer.jbl.sample.Sampler;

public class IntegerStateSpace implements NumericalStateSpace<Integer> {
  private int lowerBound;
  private int upperBound;

  public IntegerStateSpace() {
    this(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public IntegerStateSpace(int lowerBound) {
    this(lowerBound, Integer.MAX_VALUE);
  }

  public IntegerStateSpace(int lowerBound, int upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean contains(Integer value) {
    return value > this.lowerBound && value < this.upperBound;
  }

  @Override
  public Integer read(Object object) {
    Number number = (Number) object;
    return number.intValue();
  }

  @Override
  public Sampler createUniformSampler(Variable boundVariable) {
    if (!(boundVariable.stateSpace() instanceof IntegerStateSpace))
      throw new IllegalArgumentException("Variable has to be in same state space!");
    return IntegerUniformDistribution.builder().response(new NumericalVariable<>(this, boundVariable.id())).build();
  }

  public StateSpace<Double> asRealStateSpace() {
    return new RealStateSpace(this.lowerBound, this.upperBound);
  }

  @Override
  public Integer upperBound() {
    return this.upperBound;
  }

  @Override
  public Integer lowerBound() {
    return this.lowerBound;
  }

  @Override
  public <U extends Number> IntegerStateSpace map(NumericalStateSpace<U> space) {
    this.lowerBound = space.lowerBound().intValue();
    this.upperBound = space.upperBound().intValue();
    return this;
  }
}
