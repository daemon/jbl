package net.rocketeer.jbl.model.variable;

public class IntegerStateSpace implements StateSpace<Integer> {
  private final int lowerBound;
  private final int upperBound;

  public IntegerStateSpace() {
    this(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public IntegerStateSpace(int lowerBound) {
    this(lowerBound, Integer.MAX_VALUE);
  }

  public IntegerStateSpace(int lowerBound, int upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean contains(Integer value) {
    return value > this.lowerBound && value < this.upperBound;
  }

  @Override
  public Integer read(Object object) {
    return (Integer) object;
  }
}
