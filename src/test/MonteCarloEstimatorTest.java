import net.rocketeer.jbl.model.distribution.IntegerUniformDistribution;
import net.rocketeer.jbl.model.distribution.NormalDistribution;
import net.rocketeer.jbl.model.distribution.PoissonDistribution;
import net.rocketeer.jbl.compute.estimate.KLDivergenceEstimator;
import net.rocketeer.jbl.compute.estimate.MonteCarloEstimate;

public class MonteCarloEstimatorTest {
  private static void print(MonteCarloEstimate estimate) {
    System.out.println(String.format("Estimate: %.6f Variance: %.8f Iterations: %d", estimate.estimate(),
        estimate.variance(), estimate.iterations()));
  }

  public static void main(String[] args) {
    // KL divergence between uniform distribution and Poisson distribution
    IntegerUniformDistribution d1 = IntegerUniformDistribution.builder().lowerBound(1).upperBound(5).build();
    PoissonDistribution d2 = PoissonDistribution.builder().mu(2.6).build();
    KLDivergenceEstimator estimator = new KLDivergenceEstimator(d1, d2);
    MonteCarloEstimate estimate = estimator.compute(4000);
    print(estimate);

    // KL divergence between normal distribution and Poisson distribution
    NormalDistribution d3 = NormalDistribution.builder().mu(10).sigma(3.1).build();
    d2 = PoissonDistribution.builder().mu(10).build();
    estimator = new KLDivergenceEstimator(d3, d2);
    estimate = estimator.compute(4000);
    print(estimate);
  }
}
