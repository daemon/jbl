package net.rocketeer.jbl.model.io;

import net.rocketeer.jbl.model.variable.Variable;

import java.util.*;

public class IOBundle {
  private Map<Variable, Object> dataMap = new HashMap<>();
  public IOBundle() {}

  public static IOBundle createEmpty() {
    return new IOBundle();
  }

  public boolean isEmpty() {
    return this.dataMap.isEmpty();
  }

  public <T> T read(Variable<T> variable) {
    Object data = this.dataMap.get(variable);
    if (data == null)
      return null;
    return variable.stateSpace().read(data);
  }

  public IOBundle add(IOBundle...other) {
    for (IOBundle o : other)
      o.dataMap.forEach(this.dataMap::put);
    return this;
  }

  public IOBundle set(Variable var, Object data) {
    this.dataMap.put(var, data);
    return this;
  }

  public IOBundle changeVariable(Variable to, Variable from) {
    if (this.dataMap.containsKey(from))
      this.dataMap.put(to, this.dataMap.remove(from));
    return this;
  }

  public Set<RealizedValue<?>> extractAll() {
    Set<RealizedValue<?>> realizedValues = new HashSet<>();
    for (Variable variable : this.dataMap.keySet())
      realizedValues.add(new RealizedValue(variable, this.read(variable)));
    return realizedValues;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    this.dataMap.forEach((v, o) -> {
      builder.append(v.id()).append(" : ").append(o.toString()).append(" | ");
    });
    return builder.toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Map<Variable, Object> dataMap = new HashMap<>();
    private List<IOBundle> bundles = new LinkedList<>();
    private Builder() {}

    public Builder set(Variable var, Object data) {
      this.dataMap.put(var, data);
      return this;
    }

    public Builder add(IOBundle bundle) {
      this.bundles.add(bundle);
      return this;
    }

    public IOBundle build() {
      IOBundle pack = new IOBundle();
      pack.dataMap = this.dataMap;
      this.bundles.forEach(pack::add);
      return pack;
    }
  }
}
