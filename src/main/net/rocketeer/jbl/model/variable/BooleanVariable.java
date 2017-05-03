package net.rocketeer.jbl.model.variable;

import net.rocketeer.jbl.model.variable.set.BooleanStateSpace;
import net.rocketeer.jbl.model.variable.set.DiscreteStateSpace;

public class BooleanVariable extends DiscreteVariable<BooleanStateSpace.BooleanEnum> {
  public BooleanVariable() {
    super(new BooleanStateSpace());
  }

  public BooleanVariable(DiscreteStateSpace<BooleanStateSpace.BooleanEnum> space) {
    super(space);
  }
}
