package sk.slsp.workshop.mazesolver.adapters.mazeservice.port;

import sk.slsp.workshop.mazesolver.Directions;

import java.util.List;

@FunctionalInterface
public interface WhereCanIGoPort {
	List<Directions> whereCanIGo();
}
