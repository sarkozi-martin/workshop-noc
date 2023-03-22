package sk.slsp.workshop.mazesolver

import mu.KotlinLogging
import org.camunda.bpm.engine.delegate.DelegateExecution
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.AddPlaceToStackPort
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.GetPlaceFromStackPort
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.RemovePlaceFromStackPort
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.GoPort
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.IsExitPort
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.WhereCanIGoPort
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.IsAlreadyVisitedPort
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.StoreVisitedPlacePort

class MouseService(
	private val whereCanIGoPort: WhereCanIGoPort,
	private val goPort: GoPort,
	private val isExitPort: IsExitPort,
	private val storeVisitedPlacePort: StoreVisitedPlacePort,
	private val isAlreadyVisitedPort: IsAlreadyVisitedPort,
	private val addPlaceToStackPort: AddPlaceToStackPort,
	private val getPlaceFromStackPort: GetPlaceFromStackPort,
	private val removePlaceFromStackPort: RemovePlaceFromStackPort,
) {

	private val logger = KotlinLogging.logger {}

	fun init(delegateExecution: DelegateExecution) {
		logger.info("MouseService::init")
		delegateExecution.variables = mapOf(
			"isFinished" to true,
			"currentPoint" to Coordinates(0, 0),
		)
		storeVisitedPlacePort.storeVisitedPoint(0 ,0)
		addPlaceToStackPort.addPlaceToStack(Coordinates(0, 0))
	}

	fun isInFinish(delegateExecution: DelegateExecution) {
		logger.info("MouseService::isInFinish: ${isExitPort.isExit()}")

		delegateExecution.variables = mapOf(
			"isFinished" to isExitPort.isExit(),
		)
	}

	fun isNextMovePossible(delegateExecution: DelegateExecution) {
		logger.info("MouseService::isNextMovePossible")
		val currentPoint = delegateExecution.getVariable("currentPoint") as Coordinates
		delegateExecution.variables = mapOf(
			"canMakeNextMove" to (
					isPossibleUnvisitedMove(Directions.UP, currentPoint) ||
					isPossibleUnvisitedMove(Directions.DOWN, currentPoint) ||
					isPossibleUnvisitedMove(Directions.LEFT, currentPoint) ||
					isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)
			)
		)
	}

	fun makeNextMove(delegateExecution: DelegateExecution) {
		logger.info("MouseService::makeNextMove")

		var currentPoint = delegateExecution.getVariable("currentPoint") as Coordinates

		if(isPossibleUnvisitedMove(Directions.UP, currentPoint)) {
			goPort.go(Directions.UP)
			currentPoint = Coordinates(currentPoint.x, currentPoint.y - 1)
		} else if(isPossibleUnvisitedMove(Directions.DOWN, currentPoint)) {
			goPort.go(Directions.DOWN)
			currentPoint = Coordinates(currentPoint.x, currentPoint.y + 1)
		} else if(isPossibleUnvisitedMove(Directions.LEFT, currentPoint)) {
			goPort.go(Directions.LEFT)
			currentPoint = Coordinates(currentPoint.x - 1, currentPoint.y)
		} else if(isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)) {
			goPort.go(Directions.RIGHT)
			currentPoint = Coordinates(currentPoint.x + 1, currentPoint.y)
		}
		storeVisitedPlacePort.storeVisitedPoint(
			currentPoint.x,
			currentPoint.y,
		)
		addPlaceToStackPort.addPlaceToStack(
			currentPoint,
		)
		delegateExecution.variables = mapOf(
			"currentPoint" to currentPoint,
		)
	}

	fun rollbackLastMove(delegateExecution: DelegateExecution) {
		logger.info("MouseService::rollbackLastMove")

		val previousPoint = removePlaceFromStackPort.removePlaceFromStack()
		val currentPoint = getPlaceFromStackPort.getPlaceFromStack()
		if(previousPoint.y > currentPoint.y) {
			goPort.go(Directions.UP)
		} else if(previousPoint.y < currentPoint.y) {
			goPort.go(Directions.DOWN)
		} else if(previousPoint.x > currentPoint.x) {
			goPort.go(Directions.LEFT)
		} else if(previousPoint.x < currentPoint.x) {
			goPort.go(Directions.LEFT)
		}

		delegateExecution.variables = mapOf(
			"currentPoint" to currentPoint,
		)
	}

	fun completePrintPath(delegateExecution: DelegateExecution) {
		logger.info("MouseService::completePrintPath")

		// TODO get variable with name "printPath" from delegateExecution

	}

	fun printPath(delegateExecution: DelegateExecution) {
		logger.info("MouseService::printPath")

		// TODO call DumpPathPort :
		// 1) add DumpStackPort to MouseService declaration
		// 2) add DumpStackPort to MouseService bean definition in MyConfiguration
		// 3) print path with help of logger

	}


	private fun isPossibleUnvisitedMove(nextMove: Directions, currentPoint: Coordinates) =
		whereCanIGoPort.whereCanIGo().contains(nextMove) && !isVisited(nextMove, currentPoint)


	private fun isVisited(nextMove: Directions, currentPoint: Coordinates) = when(nextMove) {
		Directions.UP -> isAlreadyVisitedPort.isAlreadyVisited(
			x = currentPoint.x,
			y = currentPoint.y-1,
		)
		Directions.DOWN -> isAlreadyVisitedPort.isAlreadyVisited(
			x = currentPoint.x,
			y = currentPoint.y+1,
		)
		Directions.LEFT -> isAlreadyVisitedPort.isAlreadyVisited(
			x = currentPoint.x-1,
			y = currentPoint.y,
		)
		Directions.RIGHT -> isAlreadyVisitedPort.isAlreadyVisited(
			x = currentPoint.x+1,
			y = currentPoint.y,
		)
	}}
