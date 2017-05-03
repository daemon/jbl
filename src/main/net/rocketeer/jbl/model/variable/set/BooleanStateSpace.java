package net.rocketeer.jbl.model.variable.set;

public class BooleanStateSpace extends DiscreteStateSpace<BooleanStateSpace.BooleanEnum> {
  public enum BooleanEnum {TRUE, FALSE};
  public BooleanStateSpace() {
    super(BooleanEnum.TRUE, BooleanEnum.FALSE);
  }
}
