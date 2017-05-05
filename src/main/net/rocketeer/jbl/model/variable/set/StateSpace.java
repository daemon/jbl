package net.rocketeer.jbl.model.variable.set;

import net.rocketeer.jbl.model.variable.Variable;
import net.rocketeer.jbl.compute.Sampler;

public interface StateSpace<T> {
  boolean contains(T value);
  T read(Object object);
  Sampler createUniformSampler(Variable boundVariable);
  default Sampler createUniformSampler() {
    return this.createUniformSampler(new Variable<>(this));
  }
}
