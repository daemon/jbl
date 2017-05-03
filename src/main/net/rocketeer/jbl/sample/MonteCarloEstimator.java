package net.rocketeer.jbl.sample;

public interface MonteCarloEstimator {
  MonteCarloEstimate compute(int iterations);
  MonteCarloEstimate compute(double changeTolerance);
}
