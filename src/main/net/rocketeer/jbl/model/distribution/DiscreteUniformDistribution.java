package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.DiscreteVariable;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.*;

public class DiscreteUniformDistribution<T extends Enum> extends DiscreteDistribution<T> {
  private final DiscreteVariable<T> response;
  private final List<T> elements;

  public DiscreteUniformDistribution(DiscreteVariable<T> response) {
    this.response = response;
    List<T> elements = new ArrayList<>();
    elements.addAll(response.stateSpace().elements());
    this.elements = elements;
  }

  @Override
  public double probabilityAt(IOBundle pack) {
    if (!this.response.stateSpace().contains(pack.read(this.response)))
      return 0;
    return 1.0 / this.response.stateSpace().elements().size();
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    Random random = new Random();
    return IOBundle.builder().set(this.response(), this.elements.get(random.nextInt(this.elements.size())))
        .set(this.probability(), 1.0 / this.response.stateSpace().elements().size()).build();
  }

  @Override
  public DiscreteVariable<T> response() {
    return this.response;
  }

  @Override
  public Set<Variable<?>> domain() {
    return Collections.singleton(this.response);
  }
}
