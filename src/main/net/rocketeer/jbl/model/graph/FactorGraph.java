package net.rocketeer.jbl.model.graph;

import net.rocketeer.jbl.model.compute.Function;
import net.rocketeer.jbl.model.variable.Variable;

import java.util.HashSet;
import java.util.Set;

public class FactorGraph {
  private static class VariableNode {
    private final Variable<?> variable;
    private Set<FactorNode> neighbors = new HashSet<>();

    VariableNode(Variable<?> variable) {
      this.variable = variable;
    }

    public VariableNode addNeighbor(FactorNode node) {
      this.neighbors.add(node);
      return this;
    }
  }

  private static class FactorNode {
    private final Function function;
    private Set<VariableNode> neighbors = new HashSet<>();

    FactorNode(Function function) {
      this.function = function;
    }

    public FactorNode addNeighbor(VariableNode node) {
      this.neighbors.add(node);
      return this;
    }
  }
}
