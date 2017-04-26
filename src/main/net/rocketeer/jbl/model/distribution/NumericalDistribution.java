package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.NumericalVariable;

public interface NumericalDistribution<T extends Number> extends Distribution<T> {
  NumericalVariable<T> response();
}
