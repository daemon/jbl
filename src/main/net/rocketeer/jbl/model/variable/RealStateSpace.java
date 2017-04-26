package net.rocketeer.jbl.model.variable;

public class RealStateSpace implements StateSpace<Double> {
  private final double lowerBound;
  private final double upperBound;

  public RealStateSpace() {
    this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
  }

  public RealStateSpace(double lowerBound) {
    this(lowerBound, Double.POSITIVE_INFINITY);
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
    return (Double) object;
  }
}
