package net.rocketeer.jbl.compute.optimize;

import net.rocketeer.jbl.model.compute.Function;
import net.rocketeer.jbl.model.io.IOBundle;

@FunctionalInterface
public interface FunctionOptimizer {
  IOBundle optimize();
}
