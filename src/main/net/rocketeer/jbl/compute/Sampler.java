package net.rocketeer.jbl.compute;

import net.rocketeer.jbl.model.io.IOBundle;

public interface Sampler {
  default IOBundle sample() {
    return this.sample(IOBundle.createEmpty());
  }
  IOBundle sample(IOBundle given);
}
