package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.compute.Function;
import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.NumericalVariable;
import net.rocketeer.jbl.model.variable.Variable;
import net.rocketeer.jbl.model.variable.set.RealStateSpace;
import net.rocketeer.jbl.sample.Sampler;

import java.util.Collections;
import java.util.Set;

public abstract class Distribution<T> implements Function, Sampler {
  private NumericalVariable<Double> probVar = new NumericalVariable<>(new RealStateSpace());
  public abstract double probabilityAt(IOBundle pack);
  public abstract IOBundle sample(IOBundle given);
  public abstract Variable<T> response();

  public IOBundle sample() {
    return this.sample(IOBundle.createEmpty());
  }

  public NumericalVariable<Double> probability() {
    return this.probVar;
  }

  public IOBundle eval(IOBundle input) {
    return IOBundle.builder().set(this.probVar, this.probabilityAt(input)).build();
  }

  public Set<Variable<?>> codomain() {
    return Collections.singleton(this.probability());
  }
}
