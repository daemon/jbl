package net.rocketeer.jbl.sample;

import net.rocketeer.jbl.model.io.IOBundle;

public interface Sampler {
  default IOBundle sample() {
    return this.sample(IOBundle.createEmpty());
  }
  IOBundle sample(IOBundle given);
}
