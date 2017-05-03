import net.rocketeer.jbl.model.distribution.IntegerUniformDistribution;
import net.rocketeer.jbl.model.distribution.PoissonDistribution;
import net.rocketeer.jbl.sample.KLDivergenceEstimator;
import net.rocketeer.jbl.sample.MonteCarloEstimate;

public class MonteCarloEstimatorTest {
  public static void main(String[] args) {
    // KL Divergence between uniform distribution and Poisson distribution
    IntegerUniformDistribution d1 = IntegerUniformDistribution.builder().lowerBound(1).upperBound(5).build();
    PoissonDistribution d2 = PoissonDistribution.builder().mu(25.2).build();
    KLDivergenceEstimator estimator = new KLDivergenceEstimator(d1, d2);
    MonteCarloEstimate estimate = estimator.compute(0.1);
    System.out.println(String.format("Estimate: %.6f Variance: %.8f Iterations: %d", estimate.estimate(),
        estimate.variance(), estimate.iterations()));
  }
}
