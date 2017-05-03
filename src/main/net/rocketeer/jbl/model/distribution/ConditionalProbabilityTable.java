package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.variable.set.DiscreteStateSpace;
import net.rocketeer.jbl.model.variable.DiscreteVariable;
import net.rocketeer.jbl.model.io.RealizedValue;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.*;
import java.util.stream.Collectors;

public class ConditionalProbabilityTable<T extends Enum> extends DiscreteDistribution<T> {
  private final Map<Set<RealizedValue<? extends Enum>>, Map<RealizedValue<? extends Enum>, Double>> probabilityTable;
  private final DiscreteVariable<T> responseVariable;
  private final Map<DiscreteVariable<? extends Enum>, DiscreteDistribution> domain;

  protected ConditionalProbabilityTable(Map<Set<RealizedValue<? extends Enum>>, Map<RealizedValue<? extends Enum>, Double>> probabilityTable, DiscreteVariable<T> responseVariable,
                                        Map<DiscreteVariable<? extends Enum>, DiscreteDistribution> domain) {
    this.probabilityTable = probabilityTable;
    this.responseVariable = responseVariable;
    this.domain = domain;
  }

  // TODO: marginalize function that returns a new CPT without the specified variables

  @Override
  public double probabilityAt(IOBundle pack) {
    Set<RealizedValue<?>> realizedValues = new HashSet<>();
    for (DiscreteVariable<? extends Enum> variable : this.domain.keySet())
      realizedValues.add(new RealizedValue(variable, pack.read(variable)));
    return this.probabilityTable.get(realizedValues).get(new RealizedValue<>(this.response(), pack.read(this.response())));
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    Set<RealizedValue<?>> values = pack.extractAll();
    Set<Variable<?>> variables = values.stream().map(RealizedValue::variable).collect(Collectors.toSet());
    for (DiscreteVariable<? extends Enum> variable : this.domain.keySet()) {
      if (variables.contains(variable))
        continue;
      values.add(new RealizedValue(variable, this.domain.get(variable).sample().read(variable)));
    }
    double base = 0.0;
    double roll = Math.random();
    T value = null;
    Map<RealizedValue<? extends Enum>, Double> map = this.probabilityTable.get(values);
    for (RealizedValue<? extends Enum> rv : map.keySet()) {
      double p = map.get(rv);
      double currentCeil = base + p;
      if (roll >= base && roll <= currentCeil) {
        value = this.response().stateSpace().read(rv.value());
        break;
      }
      base = currentCeil;
    }
    IOBundle.Builder builder = IOBundle.builder();
    for (RealizedValue<?> rv : values)
      builder.set(rv.variable(), rv.value());
    return builder.set(this.response(), value).build();
  }

  @Override
  public DiscreteVariable<T> response() {
    return this.responseVariable;
  }

  public static <T extends Enum> Builder<T> builder() {
    return new Builder<>();
  }

  @Override
  public Set<Variable<?>> domain() {
    Set<Variable<?>> set = new HashSet<>();
    set.add(this.response());
    set.addAll(this.domain.keySet());
    return set;
  }

  public static class Builder<T extends Enum> {
    private DiscreteVariable<T> responseVar;
    private final Map<T, DiscreteStateSpace> enumMap = new HashMap<>();
    private final Map<DiscreteVariable<? extends Enum>, DiscreteDistribution> domain = new HashMap<>();
    private final Map<Set<RealizedValue<? extends Enum>>, Map<RealizedValue<? extends Enum>, Double>> probabilityTable = new HashMap<>();

    protected Builder() {}

    public <U extends Enum> Builder<T> addVariable(DiscreteVariable<U> variable, DiscreteDistribution<U> distribution) {
      this.domain.put(variable, distribution);
      return this;
    }

    public <U extends Enum> Builder<T> addVariable(DiscreteVariable<U> variable) {
      return this.addVariable(variable, new DiscreteUniformDistribution<U>(variable));
    }

    public Builder<T> response(DiscreteVariable<T> variable) {
      this.responseVar = variable;
      return this;
    }

    public Builder<T> probability(double probability, IOBundle bundle) {
      Set<RealizedValue<? extends Enum>> realizedValues = new HashSet<>();
      for (DiscreteVariable<? extends Enum> variable : this.domain.keySet())
        realizedValues.add(new RealizedValue(variable, bundle.read(variable)));
      if (!this.probabilityTable.containsKey(realizedValues))
        this.probabilityTable.put(realizedValues, new HashMap<>());
      this.probabilityTable.get(realizedValues).put(new RealizedValue<>(this.responseVar, bundle.read(this.responseVar)), probability);
      return this;
    }

    public ConditionalProbabilityTable<T> build() {
      return new ConditionalProbabilityTable<>(this.probabilityTable, this.responseVar, this.domain);
    }
  }
}
