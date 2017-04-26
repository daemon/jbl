package net.rocketeer.jbl.model.variable;

public interface StateSpace<T> {
  boolean contains(T value);
  T read(Object object);
}
