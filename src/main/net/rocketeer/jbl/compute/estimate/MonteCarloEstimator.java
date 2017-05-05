package net.rocketeer.jbl.compute.estimate;

public interface MonteCarloEstimator {
  MonteCarloEstimate compute(int iterations);
  MonteCarloEstimate compute(double changeTolerance);
}
