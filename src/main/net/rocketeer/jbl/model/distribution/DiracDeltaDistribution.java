package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.*;

public class DiracDeltaDistribution<T extends Number> implements NumericalDistribution<T> {
  private final T value;
  private NumericalVariable<T> xVar;
  private final Double maxValue;

  private DiracDeltaDistribution(T value, Double maxValue, NumericalVariable<T> x) {
    this.value = value;
    this.maxValue = maxValue;
    this.xVar = x;
  }

  @Override
  public double valueAt(IOBundle pack) {
    T value = pack.read(this.xVar);
    if (value.equals(this.value))
      return this.maxValue;
    return 0;
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    return IOBundle.builder().set(this.xVar, this.value).build();
  }

  @Override
  public NumericalVariable<T> response() {
    return this.xVar;
  }

  public static DiracDeltaDistribution<Double> createConstant(Double value) {
    NumericalVariable<Double> x = new NumericalVariable<>(new RealStateSpace(value, value));
    return new DiracDeltaDistribution<>(value, Double.POSITIVE_INFINITY, x);
  }

  public static DiracDeltaDistribution<Double> createConstant(Double value, NumericalVariable<Double> variable) {
    return new DiracDeltaDistribution<>(value, Double.POSITIVE_INFINITY, variable);
  }

  public static DiracDeltaDistribution<Integer> createConstant(Integer value) {
    NumericalVariable<Integer> x = new NumericalVariable<>(new IntegerStateSpace(value, value));
    return new DiracDeltaDistribution<>(value, 1.0, x);
  }

  public static DiracDeltaDistribution<Integer> createConstant(Integer value, NumericalVariable<Integer> variable) {
    return new DiracDeltaDistribution<>(value, 1.0, variable);
  }
}
