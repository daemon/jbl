package net.rocketeer.jbl.compute.estimate;

public class MonteCarloEstimate {
  private final double result;
  private final double variance;
  private final int iterations;

  MonteCarloEstimate(double result, double variance, int iterations) {
    this.result = result;
    this.variance = variance;
    this.iterations = iterations;
  }

  public double estimate() {
    return this.result;
  }

  public double variance() {
    return this.variance;
  }

  public int iterations() {
    return this.iterations;
  }
}
