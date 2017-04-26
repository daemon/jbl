package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.*;

import java.util.UUID;

public class DiracDeltaDistribution<T> extends Distribution {
  private final T value;
  private Variable<T> xVar;
  private final Double maxValue;

  private DiracDeltaDistribution(T value, Double maxValue, Variable<T> x) {
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

  public static DiracDeltaDistribution<Double> createConstant(Double value, Variable<Double> variable) {
    return new DiracDeltaDistribution<>(value, Double.POSITIVE_INFINITY, variable);
  }

  public static DiracDeltaDistribution<Integer> createConstant(Integer value, Variable<Integer> variable) {
    return new DiracDeltaDistribution<>(value, 1.0, variable);
  }
}
