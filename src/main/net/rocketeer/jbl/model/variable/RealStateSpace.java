package net.rocketeer.jbl.model.variable;

public class RealStateSpace implements NumericalStateSpace<Double> {
  private double lowerBound;
  private double upperBound;

  public RealStateSpace() {
    this(Double.MIN_VALUE, Double.MAX_VALUE);
  }

  public RealStateSpace(double lowerBound) {
    this(lowerBound, Double.MAX_VALUE);
  }

  public RealStateSpace(double lowerBound, double upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean contains(Double value) {
    return value > this.lowerBound && value < this.upperBound;
  }

  @Override
  public Double read(Object object) {
    Number number = (Number) object;
    return number.doubleValue();
  }

  @Override
  public Double upperBound() {
    return this.upperBound;
  }

  @Override
  public Double lowerBound() {
    return this.lowerBound;
  }

  @Override
  public <U extends Number> RealStateSpace map(NumericalStateSpace<U> space) {
    this.lowerBound = space.lowerBound().doubleValue();
    this.upperBound = space.upperBound().doubleValue();
    return this;
  }
}
