package sk.slsp.workshop.mazesolver.adapters.visitedplaces.port;

@FunctionalInterface
public interface IsAlreadyVisitedPort {
	Boolean isAlreadyVisited(Integer x, Integer y);
}
