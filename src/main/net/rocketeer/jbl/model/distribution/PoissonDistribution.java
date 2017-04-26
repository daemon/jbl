package net.rocketeer.jbl.model.distribution;

import net.rocketeer.jbl.model.variable.IntegerStateSpace;
import net.rocketeer.jbl.model.variable.RealStateSpace;
import net.rocketeer.jbl.model.variable.Variable;

public class PoissonDistribution implements Distribution<Integer> {
  @Override
  public double valueAt(IOBundle pack) {
    return 0;
  }

  @Override
  public IOBundle sample(IOBundle pack) {
    return null;
  }

  @Override
  public Variable<Integer> response() {
    return null;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

  }
}
