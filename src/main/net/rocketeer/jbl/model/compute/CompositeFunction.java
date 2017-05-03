package net.rocketeer.jbl.model.compute;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.Set;

public class CompositeFunction implements Function {
  private final Function inner;
  private final Function outer;

  public CompositeFunction(Function inner, Function outer) {
    this.inner = inner;
    this.outer = outer;
  }
  @Override
  public IOBundle eval(IOBundle input) {
    return this.outer.eval(this.inner.eval(input));
  }

  @Override
  public Set<Variable<?>> domain() {
    return inner.domain();
  }

  @Override
  public Set<Variable<?>> codomain() {
    return outer.codomain();
  }
}
