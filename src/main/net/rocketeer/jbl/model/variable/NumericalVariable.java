package net.rocketeer.jbl.model.variable;

public class NumericalVariable<T extends Number> extends Variable<T> {
  public NumericalVariable(NumericalStateSpace<T> space) {
    super(space);
  }

  public NumericalVariable(NumericalStateSpace<T> space, String id) {
    super(space, id);
  }

  public NumericalStateSpace<T> stateSpace() {
    return (NumericalStateSpace<T>) this.space;
  }

  public <U extends Number> NumericalVariable<U> convert(NumericalStateSpace<U> space) {
    return new NumericalVariable<>(space, this.id());
  }
}
