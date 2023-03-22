package sk.slsp.workshop.mazesolver.adapters.visitedplaces.port;

@FunctionalInterface
public interface StoreVisitedPlacePort {
	void storeVisitedPoint(Integer x, Integer y);
}
