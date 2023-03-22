package sk.slsp.workshop.mazesolver.adapters.mazepath.adapter;

import sk.slsp.workshop.mazesolver.Coordinates;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.AddPlaceToStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.DumpStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.GetPlaceFromStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.RemovePlaceFromStackPort;

import java.util.ArrayList;
import java.util.List;

public class MazePathInMemoryAdapter implements AddPlaceToStackPort, DumpStackPort, GetPlaceFromStackPort, RemovePlaceFromStackPort {
	
	private final List<Coordinates> path = new ArrayList<>();
	
	
	@Override
	public void addPlaceToStack(Coordinates coordinates) {
		path.add(coordinates);
	}
	
	
	@Override
	public List<Coordinates> dump() {
		return path;
	}
	
	
	@Override
	public Coordinates getPlaceFromStack() {
		return path.get(path.size() - 1);
	}
	
	
	@Override
	public Coordinates removePlaceFromStack() {
		return path.remove(path.size() - 1);
	}
	
}
