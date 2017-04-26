package net.rocketeer.jbl.model.variable;

import java.util.UUID;

public class Variable<T> {
  private final String nameId;
  protected final StateSpace<T> space;

  public Variable(StateSpace<T> space) {
    this(space, UUID.randomUUID().toString());
  }

  public Variable(StateSpace<T> space, String id) {
    this.nameId = id.toLowerCase();
    this.space = space;
  }

  public String id() {
    return this.nameId;
  }

  public StateSpace<T> stateSpace() {
    return this.space;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Variable))
      return false;
    return ((Variable) other).nameId.equals(this.nameId);
  }

  @Override
  public int hashCode() {
    return this.nameId.hashCode();
  }

  public <U> Variable<U> convert(StateSpace<U> space) {
    return new Variable<>(space, this.id());
  }
}
