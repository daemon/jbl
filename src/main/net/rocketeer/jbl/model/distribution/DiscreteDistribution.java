package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.DiscreteStateSpace;
import net.rocketeer.jbl.model.variable.DiscreteVariable;

public interface DiscreteDistribution<T extends Enum> extends Distribution<T> {
  DiscreteVariable<T> response();
}
