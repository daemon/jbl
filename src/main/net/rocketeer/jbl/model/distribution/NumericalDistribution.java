package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.NumericalVariable;

public abstract class NumericalDistribution<T extends Number> extends Distribution<T> {
  public abstract NumericalVariable<T> response();
}
