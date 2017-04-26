package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.StateSpace;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class IOBundle {
  private Map<Variable, Object> dataMap = new HashMap<>();
  private IOBundle() {}

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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Map<Variable, Object> dataMap = new HashMap<>();
    private Builder() {}
    public Builder set(Variable var, Object data) {
      this.dataMap.put(var, data);
      return this;
    }

    public IOBundle build() {
      IOBundle pack = new IOBundle();
      pack.dataMap = this.dataMap;
      return pack;
    }
  }
}
