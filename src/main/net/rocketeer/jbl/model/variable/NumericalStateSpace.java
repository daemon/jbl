package net.rocketeer.jbl.model.variable;

public interface NumericalStateSpace<T extends Number> extends StateSpace<T> {
  T upperBound();
  T lowerBound();
  <U extends Number> NumericalStateSpace<T> map(NumericalStateSpace<U> space);
}
