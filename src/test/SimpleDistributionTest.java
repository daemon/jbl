import net.rocketeer.jbl.model.distribution.DiracDeltaDistribution;
import net.rocketeer.jbl.model.distribution.NormalDistribution;

public class SimpleDistributionTest {
  public static void main(String[] args) {
    // Dirac delta pseudo-distribution
    System.out.println("Dirac delta pseudo-distribution sample");
    DiracDeltaDistribution<Double> constant = DiracDeltaDistribution.createConstant(1.0);
    System.out.println(constant.sample().read(constant.response()));

    // Gaussian distribution
    System.out.println("10 Gaussian distribution samples");
    NormalDistribution gaussian = NormalDistribution.builder().mu(0).sigma(constant).build();
    for (int i = 0; i < 10; ++i)
      System.out.println(gaussian.sample().read(gaussian.response()));

    // y ~ N(x, 1) where x ~ N(0, 1)
    System.out.println("10 Normal distribution samples");
    NormalDistribution y = NormalDistribution.builder().mu(gaussian).sigma(1).build();
    for (int i = 0; i < 10; ++i)
      System.out.println(y.sample().read(y.response()));

    // Performance test
    long a = System.currentTimeMillis();
    for (int i = 0; i < 300000; ++i) {
      Double val = gaussian.sample().read(gaussian.response());
    }
    System.out.println("Samples per second: " + 300000 / ((System.currentTimeMillis() - a) / 1000.0));
  }
}
