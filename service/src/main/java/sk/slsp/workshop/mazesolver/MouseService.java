package sk.slsp.workshop.mazesolver;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.AddPlaceToStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.GetPlaceFromStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.RemovePlaceFromStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.GoPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.IsExitPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.WhereCanIGoPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.IsAlreadyVisitedPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.StoreVisitedPlacePort;

@SuppressWarnings("unused")
public class MouseService {
	
	private static final String IS_FINISHED_KEY = "isFinished";
	private static final String CURRENT_POINT_KEY = "currentPoint";
	private static final String CAN_MAKE_NEXT_MOVE_KEY = "canMakeNextMove";
	
	private final Logger logger = LoggerFactory.getLogger(MouseService.class);
	
	private final WhereCanIGoPort whereCanIGoPort;
	
	private final GoPort goPort;
	
	private final IsExitPort isExitPort;
	
	private final StoreVisitedPlacePort storeVisitedPlacePort;
	
	private final IsAlreadyVisitedPort isAlreadyVisitedPort;
	
	private final AddPlaceToStackPort addPlaceToStackPort;
	
	private final GetPlaceFromStackPort getPlaceFromStackPort;
	
	private final RemovePlaceFromStackPort removePlaceFromStackPort;
	
	
	public MouseService(
		WhereCanIGoPort whereCanIGoPort,
		GoPort goPort,
		IsExitPort isExitPort,
		StoreVisitedPlacePort storeVisitedPlacePort,
		IsAlreadyVisitedPort isAlreadyVisitedPort,
		AddPlaceToStackPort addPlaceToStackPort,
		GetPlaceFromStackPort getPlaceFromStackPort,
		RemovePlaceFromStackPort removePlaceFromStackPort
	) {
		this.whereCanIGoPort = whereCanIGoPort;
		this.goPort = goPort;
		this.isExitPort = isExitPort;
		this.storeVisitedPlacePort = storeVisitedPlacePort;
		this.isAlreadyVisitedPort = isAlreadyVisitedPort;
		this.addPlaceToStackPort = addPlaceToStackPort;
		this.getPlaceFromStackPort = getPlaceFromStackPort;
		this.removePlaceFromStackPort = removePlaceFromStackPort;
	}
	
	
	public void init(DelegateExecution delegateExecution) {
		logger.info("MouseService::init");
		delegateExecution.setVariable(IS_FINISHED_KEY, true);
		delegateExecution.setVariable(CURRENT_POINT_KEY, new Coordinates(0, 0));
		storeVisitedPlacePort.storeVisitedPoint(0, 0);
		addPlaceToStackPort.addPlaceToStack(new Coordinates(0, 0));
	}
	
	
	public void isInFinish(DelegateExecution delegateExecution) {
		logger.info(String.format("MouseService::isInFinish: %b", isExitPort.isExit()));
		delegateExecution.setVariable(IS_FINISHED_KEY, isExitPort.isExit());
	}
	
	
	public void isNextMovePossible(DelegateExecution delegateExecution) {
		logger.info("MouseService::isNextMovePossible");
		final Coordinates currentPoint = (Coordinates) delegateExecution.getVariable(CURRENT_POINT_KEY);
		delegateExecution.setVariable(
			CAN_MAKE_NEXT_MOVE_KEY,
			isPossibleUnvisitedMove(Directions.UP, currentPoint) ||
				isPossibleUnvisitedMove(Directions.DOWN, currentPoint) ||
				isPossibleUnvisitedMove(Directions.LEFT, currentPoint) ||
				isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)
		);
	}
	
	
	public void makeNextMove(DelegateExecution delegateExecution) {
		logger.info("MouseService::makeNextMove");
		
		Coordinates currentPoint = (Coordinates) delegateExecution.getVariable(CURRENT_POINT_KEY);
		
		if (isPossibleUnvisitedMove(Directions.UP, currentPoint)) {
			goPort.go(Directions.UP);
			currentPoint = new Coordinates(currentPoint.getX(), currentPoint.getY() - 1);
		} else if (isPossibleUnvisitedMove(Directions.DOWN, currentPoint)) {
			goPort.go(Directions.DOWN);
			currentPoint = new Coordinates(currentPoint.getX(), currentPoint.getY() + 1);
		} else if (isPossibleUnvisitedMove(Directions.LEFT, currentPoint)) {
			goPort.go(Directions.LEFT);
			currentPoint = new Coordinates(currentPoint.getX() - 1, currentPoint.getY());
		} else if (isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)) {
			goPort.go(Directions.RIGHT);
			currentPoint = new Coordinates(currentPoint.getX() + 1, currentPoint.getY());
		}
		
		storeVisitedPlacePort.storeVisitedPoint(currentPoint.getX(), currentPoint.getY());
		addPlaceToStackPort.addPlaceToStack(currentPoint);
		delegateExecution.setVariable(CURRENT_POINT_KEY, currentPoint);
	}
	
	
	public void rollbackLastMove(DelegateExecution delegateExecution) {
		logger.info("MouseService::rollbackLastMove");
		
		final Coordinates previousPoint = removePlaceFromStackPort.removePlaceFromStack();
		final Coordinates currentPoint = getPlaceFromStackPort.getPlaceFromStack();
		if (previousPoint.getY() > currentPoint.getY()) {
			goPort.go(Directions.UP);
		} else if (previousPoint.getY() < currentPoint.getY()) {
			goPort.go(Directions.DOWN);
		} else if (previousPoint.getX() > currentPoint.getX()) {
			goPort.go(Directions.LEFT);
		} else if (previousPoint.getX() < currentPoint.getX()) {
			goPort.go(Directions.LEFT);
		}
		
		delegateExecution.setVariable(CURRENT_POINT_KEY, currentPoint);
	}
	
	
	public void completePrintPath(DelegateExecution delegateExecution) {
		logger.info("MouseService::completePrintPath");
		
		// TODO get variable with name "printPath" from delegateExecution
		
	}
	
	
	public void printPath(DelegateExecution delegateExecution) {
		logger.info("MouseService::printPath");
		
		// TODO call DumpPathPort :
		// 1) add DumpStackPort to MouseService declaration
		// 2) add DumpStackPort to MouseService bean definition in MyConfiguration
		// 3) print path with help of logger
		
	}
	
	
	private Boolean isPossibleUnvisitedMove(Directions nextMove, Coordinates currentPoint) {
		return whereCanIGoPort.whereCanIGo().contains(nextMove) && !isVisited(nextMove, currentPoint);
	}
	
	
	private Boolean isVisited(Directions nextMove, Coordinates currentPoint) {
		if (nextMove == Directions.UP) {
			return isAlreadyVisitedPort.isAlreadyVisited(currentPoint.getX(), currentPoint.getY() - 1);
		} else if (nextMove == Directions.DOWN) {
			return isAlreadyVisitedPort.isAlreadyVisited(currentPoint.getX(), currentPoint.getY() + 1);
		} else if (nextMove == Directions.LEFT) {
			return isAlreadyVisitedPort.isAlreadyVisited(currentPoint.getX() - 1, currentPoint.getY());
		} else if (nextMove == Directions.RIGHT) {
			return isAlreadyVisitedPort.isAlreadyVisited(currentPoint.getX() + 1, currentPoint.getY());
		}
		return false;
	}
}
