package net.rocketeer.jbl.model.variable;

import net.rocketeer.jbl.model.variable.set.NumericalStateSpace;

import java.util.UUID;

public class NumericalVariable<T extends Number> extends Variable<T> {
  public NumericalVariable(NumericalStateSpace<T> space) {
    super(space);
  }

  public NumericalVariable(NumericalStateSpace<T> space, UUID id) {
    super(space, id);
  }

  public NumericalStateSpace<T> stateSpace() {
    return (NumericalStateSpace<T>) this.space;
  }

  public <U extends Number> NumericalVariable<U> convert(NumericalStateSpace<U> space) {
    return new NumericalVariable<>(space, this.id());
  }
}
