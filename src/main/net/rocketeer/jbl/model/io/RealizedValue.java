package net.rocketeer.jbl.model.io;

import net.rocketeer.jbl.model.variable.Variable;

public class RealizedValue<T> {
  private final T value;
  private final Variable<T> variable;

  public RealizedValue(Variable<T> variable, T value) {
    this.value = value;
    this.variable = variable;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RealizedValue))
      return false;
    RealizedValue o = (RealizedValue) other;
    return o.variable.equals(this.variable) && o.value.equals(this.value);
  }

  @Override
  public int hashCode() {
    return (this.value.hashCode() << 4) ^ (this.variable.hashCode() >> 28) ^ this.value.hashCode();
  }

  public Variable<T> variable() {
    return this.variable;
  }

  public T value() {
    return this.value;
  }
}
