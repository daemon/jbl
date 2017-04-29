package net.rocketeer.jbl.model.variable;

import java.util.UUID;

public class Variable<T> {
  private final UUID id;
  protected final StateSpace<T> space;

  public Variable(StateSpace<T> space) {
    this(space, UUID.randomUUID());
  }

  public Variable(StateSpace<T> space, UUID id) {
    this.id = id;
    this.space = space;
  }

  public UUID id() {
    return this.id;
  }

  public StateSpace<T> stateSpace() {
    return this.space;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Variable))
      return false;
    return ((Variable) other).id.equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  public <U> Variable<U> convert(StateSpace<U> space) {
    return new Variable<>(space, this.id());
  }
}
