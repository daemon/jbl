import net.rocketeer.jbl.model.io.IOBundle;
import net.rocketeer.jbl.model.distribution.ConditionalProbabilityTable;
import net.rocketeer.jbl.model.variable.set.BooleanStateSpace;
import net.rocketeer.jbl.model.variable.BooleanVariable;

public class ProbabilityTableTest {
  public static void main(String[] args) {
    BooleanVariable raining = new BooleanVariable();
    BooleanVariable thundering = new BooleanVariable();
    BooleanVariable lightning = new BooleanVariable();

    // Raining, thundering
    IOBundle o1 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.TRUE)
        .set(thundering, BooleanStateSpace.BooleanEnum.TRUE)
        .set(lightning, BooleanStateSpace.BooleanEnum.TRUE).build();
    IOBundle o2 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.TRUE)
        .set(thundering, BooleanStateSpace.BooleanEnum.TRUE)
        .set(lightning, BooleanStateSpace.BooleanEnum.FALSE).build();

    // Not raining, thundering
    IOBundle o3 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.FALSE)
        .set(thundering, BooleanStateSpace.BooleanEnum.TRUE)
        .set(lightning, BooleanStateSpace.BooleanEnum.TRUE).build();
    IOBundle o4 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.FALSE)
        .set(thundering, BooleanStateSpace.BooleanEnum.TRUE)
        .set(lightning, BooleanStateSpace.BooleanEnum.FALSE).build();

    // Raining, not thundering
    IOBundle o5 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.TRUE)
        .set(thundering, BooleanStateSpace.BooleanEnum.FALSE)
        .set(lightning, BooleanStateSpace.BooleanEnum.TRUE).build();
    IOBundle o6 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.TRUE)
        .set(thundering, BooleanStateSpace.BooleanEnum.FALSE)
        .set(lightning, BooleanStateSpace.BooleanEnum.FALSE).build();

    // Not raining, not thundering
    IOBundle o7 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.FALSE)
        .set(thundering, BooleanStateSpace.BooleanEnum.FALSE)
        .set(lightning, BooleanStateSpace.BooleanEnum.TRUE).build();
    IOBundle o8 = IOBundle.builder().set(raining, BooleanStateSpace.BooleanEnum.FALSE)
        .set(thundering, BooleanStateSpace.BooleanEnum.FALSE)
        .set(lightning, BooleanStateSpace.BooleanEnum.FALSE).build();

    ConditionalProbabilityTable<BooleanStateSpace.BooleanEnum> table = ConditionalProbabilityTable.<BooleanStateSpace.BooleanEnum>builder()
        .addVariable(raining).addVariable(thundering).response(lightning)
        .probability(0.9, o1).probability(0.1, o2)
        .probability(0.6, o3).probability(0.4, o4)
        .probability(0, o5).probability(1, o6)
        .probability(0, o7).probability(1, o8).build();
    System.out.println("Probability of observing lightning given rain and thunder: " + table.probabilityAt(o1));
    System.out.println("10 samples:");
    for (int i = 0; i < 10; ++i) {
      IOBundle sample = table.sample();
      BooleanStateSpace.BooleanEnum isThunder = sample.read(thundering);
      BooleanStateSpace.BooleanEnum isRain = sample.read(raining);
      BooleanStateSpace.BooleanEnum isLightning = sample.read(lightning);
      System.out.println(String.format("Thundering: %10s Raining: %10s Lightning: %10s", isThunder.toString(),
          isRain.toString(), isLightning.toString()));
    }
  }
}
