package net.rocketeer.jbl.model.variable;

public class IntegerStateSpace implements NumericalStateSpace<Integer> {
  private int lowerBound;
  private int upperBound;

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

  public StateSpace<Double> asRealStateSpace() {
    return new RealStateSpace(this.lowerBound, this.upperBound);
  }

  @Override
  public Integer upperBound() {
    return this.upperBound;
  }

  @Override
  public Integer lowerBound() {
    return this.lowerBound;
  }

  @Override
  public <U extends Number> IntegerStateSpace map(NumericalStateSpace<U> space) {
    this.lowerBound = space.lowerBound().intValue();
    this.upperBound = space.upperBound().intValue();
    return this;
  }
}
