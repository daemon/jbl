package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.DiscreteVariable;

public abstract class DiscreteDistribution<T extends Enum> extends Distribution<T> {
  public abstract DiscreteVariable<T> response();
}
