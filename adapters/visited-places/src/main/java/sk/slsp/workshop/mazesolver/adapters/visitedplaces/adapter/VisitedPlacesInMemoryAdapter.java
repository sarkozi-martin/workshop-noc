package sk.slsp.workshop.mazesolver.adapters.visitedplaces.adapter;

import sk.slsp.workshop.mazesolver.Coordinates;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.ClearVisitedPlacesPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.IsAlreadyVisitedPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.StoreVisitedPlacePort;

import java.util.HashMap;
import java.util.Map;

public class VisitedPlacesInMemoryAdapter implements ClearVisitedPlacesPort, IsAlreadyVisitedPort, StoreVisitedPlacePort {
	
	private final Map<Coordinates, Boolean> storage = new HashMap<>();
	
	
	@Override
	public void clearVisitedPlaces() {
		storage.clear();
	}
	
	
	@Override
	public Boolean isAlreadyVisited(Integer x, Integer y) {
		return storage.containsKey(new Coordinates(x, y));
	}
	
	
	@Override
	public void storeVisitedPoint(Integer x, Integer y) {
		storage.put(new Coordinates(x, y), true);
	}
}
