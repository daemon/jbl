package net.rocketeer.jbl.model.variable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DiscreteStateSpace<T extends Enum> implements StateSpace<T> {
  private final Set<T> values = new HashSet<>();

  public DiscreteStateSpace(T...values) {
    Collections.addAll(this.values, values);
  }

  @Override
  public boolean contains(T value) {
    return this.values.contains(value);
  }

  @Override
  public T read(Object object) {
    return (T) object;
  }

  public Set<T> elements() {
    return this.values;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof DiscreteStateSpace))
      return false;
    DiscreteStateSpace<?> stateSpace = (DiscreteStateSpace<?>) other;
    if (stateSpace.values.size() != this.values.size())
      return false;
    for (Object o : stateSpace.values)
      if (!this.values.contains(o))
        return false;
    return true;
  }
}
