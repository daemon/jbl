package net.rocketeer.jbl.compute.optimize;

import net.rocketeer.jbl.model.io.IOBundle;

public class SimulatedAnnealingOptimizer implements FunctionOptimizer {
  private final EnergyFunction energyFunction;
  private final double initTemp;
  private int step = 2;
  private final TransitionalFunction transitionalFn;

  public SimulatedAnnealingOptimizer(TransitionalFunction transitionalFn, EnergyFunction energyFunction) {
    this(transitionalFn, energyFunction, 100);
  }

  public SimulatedAnnealingOptimizer(TransitionalFunction transitionalFn, EnergyFunction energyFunction, double initTemp) {
    this.energyFunction = energyFunction;
    this.initTemp = initTemp;
    this.transitionalFn = transitionalFn;
  }

  /**
   * Optimizes the function whose energy is associated with <code>energyFunction</code>
   * @return the optimal parameters
   */
  @Override
  public IOBundle optimize() {
    IOBundle currentParams = this.transitionalFn.next(IOBundle.createEmpty(), this.initTemp);
    IOBundle bestParams = currentParams;
    double temp = this.initTemp;
    double currentEnergy = this.energyFunction.energyAt(currentParams);
    double bestEnergy = this.energyFunction.energyAt(bestParams);
    while (temp >= 0.00001) {
      IOBundle proposal = this.transitionalFn.next(bestParams, temp);
      double proposalEnergy = this.energyFunction.energyAt(proposal);
      double acceptance = 1.0 / (1 + Math.exp((proposalEnergy - currentEnergy) / temp));
      if (Math.random() < acceptance) {
        currentParams = proposal;
        currentEnergy = this.energyFunction.energyAt(currentParams);
        if (currentEnergy < bestEnergy) {
          bestParams = currentParams;
          bestEnergy = this.energyFunction.energyAt(bestParams);
        }
      }
      ++this.step;
      temp *= 0.9996;
    }
    return bestParams;
  }

  @FunctionalInterface
  public interface EnergyFunction {
    double energyAt(IOBundle input);
  }

  @FunctionalInterface
  public interface TransitionalFunction {
    IOBundle next(IOBundle input, double temperature);
  }
}
