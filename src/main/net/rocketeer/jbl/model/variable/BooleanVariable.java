package net.rocketeer.jbl.model.variable;

public class BooleanVariable extends DiscreteVariable<BooleanStateSpace.BooleanEnum> {
  public BooleanVariable() {
    super(new BooleanStateSpace());
  }

  public BooleanVariable(DiscreteStateSpace<BooleanStateSpace.BooleanEnum> space) {
    super(space);
  }
}
