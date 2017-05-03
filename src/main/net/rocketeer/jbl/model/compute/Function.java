package net.rocketeer.jbl.model.compute;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.Set;

public interface Function {
  IOBundle eval(IOBundle input);
  Set<Variable<?>> domain();
  Set<Variable<?>> codomain();
}
