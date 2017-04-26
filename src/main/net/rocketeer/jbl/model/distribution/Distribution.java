package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.Variable;

public interface Distribution<T> {
  double valueAt(IOBundle pack);
  IOBundle sample(IOBundle pack);
  Variable<T> response();
  default IOBundle sample() {
    return this.sample(IOBundle.createEmpty());
  }
}
