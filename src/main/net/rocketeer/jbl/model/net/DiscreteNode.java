package net.rocketeer.jbl.model.net;

import net.rocketeer.jbl.model.distribution.ConditionalProbabilityTable;

public class DiscreteNode {
  private final ConditionalProbabilityTable table;

  public DiscreteNode(ConditionalProbabilityTable table) {
    this.table = table;
  }
}
