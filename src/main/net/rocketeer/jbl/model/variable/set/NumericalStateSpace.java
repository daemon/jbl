package net.rocketeer.jbl.model.variable.set;

public interface NumericalStateSpace<T extends Number> extends StateSpace<T> {
  T upperBound();
  T lowerBound();
  <U extends Number> NumericalStateSpace<T> map(NumericalStateSpace<U> space);
}
