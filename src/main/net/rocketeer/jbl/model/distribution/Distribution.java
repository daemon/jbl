package net.rocketeer.jbl.model.distribution;

public abstract class Distribution {
  public abstract double valueAt(IOBundle pack);
  public abstract IOBundle sample(IOBundle pack);
  public IOBundle sample() {
    return this.sample(IOBundle.createEmpty());
  }
}
