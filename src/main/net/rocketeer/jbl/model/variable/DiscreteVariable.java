package net.rocketeer.jbl.model.variable;

import net.rocketeer.jbl.model.variable.set.DiscreteStateSpace;

import java.util.UUID;

public class DiscreteVariable<T extends Enum> extends Variable<T> {
  public DiscreteVariable(DiscreteStateSpace<T> space) {
    super(space);
  }

  public DiscreteVariable(DiscreteStateSpace<T> space, UUID id) {
    super(space, id);
  }

  public DiscreteStateSpace<T> stateSpace() {
    return (DiscreteStateSpace<T>) this.space;
  }
}
