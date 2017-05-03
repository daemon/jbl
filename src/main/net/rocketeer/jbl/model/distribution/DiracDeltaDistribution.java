package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.*;
import net.rocketeer.jbl.model.variable.set.IntegerStateSpace;
import net.rocketeer.jbl.model.variable.set.RealStateSpace;

import java.util.Collections;
import java.util.Set;

public class DiracDeltaDistribution<T extends Number> extends NumericalDistribution<T> {
  private final T value;
  private NumericalVariable<T> xVar;
  private final Double maxValue;

  private DiracDeltaDistribution(T value, Double maxValue, NumericalVariable<T> x) {
    this.value = value;
    this.maxValue = maxValue;
    this.xVar = x;
  }

  @Override
  public double probabilityAt(IOBundle pack) {
    T value = pack.read(this.xVar);
    if (value.equals(this.value))
      return this.maxValue;
    return 0;
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    return IOBundle.builder().set(this.xVar, this.value).set(this.probability(), this.maxValue).build();
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

  @Override
  public Set<Variable<?>> domain() {
    return Collections.singleton(this.response());
  }
}
