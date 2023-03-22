package sk.slsp.workshop.mazesolver.adapters.mazeservice.port;

import sk.slsp.workshop.mazesolver.Directions;

@FunctionalInterface
public interface GoPort {
	void go(Directions direction);
}
