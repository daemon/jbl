import net.rocketeer.jbl.compute.optimize.SimulatedAnnealingOptimizer;
import net.rocketeer.jbl.model.distribution.NormalDistribution;
import net.rocketeer.jbl.model.io.IOBundle;

public class OptimizationTest {
  public static void main(String[] args) {
    // Simulated annealing of Rosenbrock function
    // Multivariate normal distribution is also acceptable, here 2 1Ds are used
    NormalDistribution transDistn1 = NormalDistribution.builder().buildTransitionalDistribution(0);
    NormalDistribution transDistn2 = NormalDistribution.builder().buildTransitionalDistribution(0);
    SimulatedAnnealingOptimizer.TransitionalFunction transFn = (bundle, temp) -> {
      transDistn1.setSigma(temp);
      transDistn2.setSigma(temp);
      return transDistn1.sample(bundle).add(transDistn2.sample(bundle));
    };
    SimulatedAnnealingOptimizer.EnergyFunction nrgFn = bundle -> {
      double x = bundle.read(transDistn1.response());
      double y = bundle.read(transDistn2.response());
      return Math.pow((1 - x), 2) + 100 * Math.pow((y - x * x), 2);
    };
    SimulatedAnnealingOptimizer optimizer = new SimulatedAnnealingOptimizer(transFn, nrgFn, 100);
    // Global minimum is at (1, 1)
    IOBundle result = optimizer.optimize();
    System.out.println("Optimal point: (" + result.read(transDistn1.response()) + ", " + result.read(transDistn2.response()) + ")");
    System.out.println("Value: " + nrgFn.energyAt(result));
  }
}
