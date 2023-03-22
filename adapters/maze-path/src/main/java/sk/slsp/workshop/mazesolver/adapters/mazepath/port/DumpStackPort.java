package sk.slsp.workshop.mazesolver.adapters.mazepath.port;

import sk.slsp.workshop.mazesolver.Coordinates;

import java.util.List;

@FunctionalInterface
public interface DumpStackPort {
	List<Coordinates> dump();
}
