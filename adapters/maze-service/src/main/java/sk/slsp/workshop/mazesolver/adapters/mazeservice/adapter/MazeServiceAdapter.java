package sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.slsp.workshop.mazesolver.Directions;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.GoPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.IsExitPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.WhereCanIGoPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MazeServiceAdapter implements GoPort, IsExitPort, WhereCanIGoPort {
	
	private final Logger logger = LoggerFactory.getLogger(MazeServiceAdapter.class);
	
	private Integer posX = 1;
	
	private Integer posY = 0;
	
	private final Character[][] maze = {
		{'X', 's', 'X', 'X', 'X'},
		{'X', ' ', 'X', ' ', 'X'},
		{'X', ' ', ' ', ' ', 'X'},
		{'X', ' ', 'X', ' ', 'X'},
		{'X', 'X', 'X', 'e', 'X'}
	};
	
	
	@Override
	public void go(Directions direction) {
		if (direction == Directions.UP) {
			goUp();
		} else if (direction == Directions.DOWN) {
			goDown();
		} else if (direction == Directions.LEFT) {
			goLeft();
		} else if (direction == Directions.RIGHT) {
			goRight();
		}
	}
	
	
	@Override
	public Boolean isExit() {
		return maze[posY][posX] == 'e';
	}
	
	
	@Override
	public List<Directions> whereCanIGo() {
		List<Directions> res = new ArrayList<>();
		
		if (canGoUp()) {
			res.add(Directions.UP);
		}
		if (canGoDown()) {
			res.add(Directions.DOWN);
		}
		if (canGoLeft()) {
			res.add(Directions.LEFT);
		}
		if (canGoRight()) {
			res.add(Directions.RIGHT);
		}
		
		return res;
	}
	
	
	private void goUp() {
		if (canGoUp()) {
			posY -= 1;
		}
		logger.debug(String.format("going up; pos: [%d,%d]", posX, posY));
	}
	
	
	private void goDown() {
		if (canGoDown()) {
			posY += 1;
		}
		logger.debug(String.format("going down; pos: [%d,%d]", posX, posY));
	}
	
	
	private void goLeft() {
		if (canGoLeft()) {
			posX -= 1;
		}
		logger.debug(String.format("going left; pos: [%d,%d]", posX, posY));
	}
	
	
	private void goRight() {
		if (canGoRight()) {
			posX += 1;
		}
		logger.debug(String.format("going right; pos: [%d,%d]", posX, posY));
	}
	
	
	private Boolean canGoUp() {
		return posY > 0 && Arrays.asList('s', 'e', ' ').contains(maze[posY - 1][posX]);
	}
	
	
	private Boolean canGoDown() {
		return posY < (maze.length - 1) && Arrays.asList('s', 'e', ' ').contains(maze[posY + 1][posX]);
	}
	
	
	private Boolean canGoLeft() {
		return posX > 0 && Arrays.asList('s', 'e', ' ').contains(maze[posY][posX - 1]);
	}
	
	
	private Boolean canGoRight() {
		return posX < (maze[0].length - 1) && Arrays.asList('s', 'e', ' ').contains(maze[posY][posX + 1]);
	}
}
