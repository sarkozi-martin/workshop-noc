package sk.slsp.workshop.mazesolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter.MazeServiceAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeTest {
	
	private final Logger logger = LoggerFactory.getLogger(MazeTest.class);
	
	private MazeServiceAdapter mazeServiceAdapter;
	
	private final Map<Coordinates, Boolean> visited = new HashMap<>();
	
	private final List<Coordinates> path = new ArrayList<>();
	
	
	@BeforeEach
	public void init() {
		mazeServiceAdapter = new MazeServiceAdapter();
		visited.clear();
		path.clear();
	}
	
	
	private Boolean isVisited(Directions nextMove, Coordinates currentPoint) {
		if (nextMove == Directions.UP) {
			return visited.containsKey(new Coordinates(currentPoint.getX(), currentPoint.getY() - 1));
		} else if (nextMove == Directions.DOWN) {
			return visited.containsKey(new Coordinates(currentPoint.getX(), currentPoint.getY() + 1));
		} else if (nextMove == Directions.LEFT) {
			return visited.containsKey(new Coordinates(currentPoint.getX() - 1, currentPoint.getY()));
		} else if (nextMove == Directions.RIGHT) {
			return visited.containsKey(new Coordinates(currentPoint.getX() + 1, currentPoint.getY()));
		}
		return false;
	}
	
	
	private Boolean isPossibleUnvisitedMove(Directions nextMove, Coordinates currentPoint) {
		return mazeServiceAdapter.whereCanIGo().contains(nextMove) && !isVisited(nextMove, currentPoint);
	}
	
	
	private Boolean isNextMovePossible(Coordinates currentPoint) {
		return isPossibleUnvisitedMove(Directions.UP, currentPoint) ||
			isPossibleUnvisitedMove(Directions.DOWN, currentPoint) ||
			isPossibleUnvisitedMove(Directions.LEFT, currentPoint) ||
			isPossibleUnvisitedMove(Directions.RIGHT, currentPoint);
	}
	
	
	@Test
	public void depthFirstSearch() {
		final Coordinates startPoint = new Coordinates(0, 0);
		Coordinates currentPoint = startPoint;
		
		path.add(startPoint);
		visited.put(startPoint, true);
		
		while (!mazeServiceAdapter.isExit()) {
			if (isNextMovePossible(currentPoint)) {
				if (isPossibleUnvisitedMove(Directions.UP, currentPoint)) {
					mazeServiceAdapter.go(Directions.UP);
					currentPoint = new Coordinates(currentPoint.getX(), currentPoint.getY() - 1);
				} else if (isPossibleUnvisitedMove(Directions.DOWN, currentPoint)) {
					mazeServiceAdapter.go(Directions.DOWN);
					currentPoint = new Coordinates(currentPoint.getX(), currentPoint.getY() + 1);
				} else if (isPossibleUnvisitedMove(Directions.LEFT, currentPoint)) {
					mazeServiceAdapter.go(Directions.LEFT);
					currentPoint = new Coordinates(currentPoint.getX() - 1, currentPoint.getY());
				} else if (isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)) {
					mazeServiceAdapter.go(Directions.RIGHT);
					currentPoint = new Coordinates(currentPoint.getX() + 1, currentPoint.getY());
				}
				visited.put(currentPoint, true);
				path.add(0, currentPoint);
			} else {
				final Coordinates previousPoint = path.remove(0);
				currentPoint = path.get(0);
				if (previousPoint.getY() > currentPoint.getY()) {
					mazeServiceAdapter.go(Directions.UP);
				} else if (previousPoint.getY() < currentPoint.getY()) {
					mazeServiceAdapter.go(Directions.DOWN);
				} else if (previousPoint.getX() > currentPoint.getX()) {
					mazeServiceAdapter.go(Directions.LEFT);
				} else if (previousPoint.getX() < currentPoint.getX()) {
					mazeServiceAdapter.go(Directions.RIGHT);
				}
			}
		}
		
		Collections.reverse(path);
		logger.debug("--- Path ---");
		path.forEach(point -> logger.debug(String.format("[%d,%d]", point.getX(), point.getY())));
	}
}
