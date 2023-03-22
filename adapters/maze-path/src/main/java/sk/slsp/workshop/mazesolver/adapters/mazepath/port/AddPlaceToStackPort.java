package sk.slsp.workshop.mazesolver.adapters.mazepath.port;

import sk.slsp.workshop.mazesolver.Coordinates;

@FunctionalInterface
public interface AddPlaceToStackPort {
	void addPlaceToStack(Coordinates coordinates);
}
