package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.DiscreteVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscreteUniformDistribution<T extends Enum> implements DiscreteDistribution<T> {
  private final DiscreteVariable<T> response;
  private final List<T> elements;

  public DiscreteUniformDistribution(DiscreteVariable<T> response) {
    this.response = response;
    List<T> elements = new ArrayList<>();
    elements.addAll(response.stateSpace().elements());
    this.elements = elements;
  }

  @Override
  public double valueAt(IOBundle pack) {
    if (!this.response.stateSpace().contains(pack.read(this.response)))
      return 0;
    return 1.0 / this.response.stateSpace().elements().size();
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    Random random = new Random();
    return IOBundle.builder().set(this.response(), this.elements.get(random.nextInt(this.elements.size()))).build();
  }

  @Override
  public DiscreteVariable<T> response() {
    return this.response;
  }
}
