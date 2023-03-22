package sk.slsp.workshop.mazesolver

import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter.MazeServiceAdapter
import kotlin.collections.HashMap

class MazeTest {

	private val logger = KotlinLogging.logger {}

	private lateinit var mazeServiceAdapter: MazeServiceAdapter

	private val visited: MutableMap<Coordinates, Boolean> = HashMap()

	private val path: MutableList<Coordinates> = mutableListOf()

	@BeforeEach
	fun init() {
		mazeServiceAdapter = MazeServiceAdapter()
		visited.clear()
		path.clear()
	}

	private fun isVisited(nextMove: Directions, currentPoint: Coordinates) = when(nextMove) {
		Directions.UP -> visited.contains(currentPoint.copy(y = currentPoint.y-1))
		Directions.DOWN -> visited.contains(currentPoint.copy(y = currentPoint.y+1))
		Directions.LEFT -> visited.contains(currentPoint.copy(x = currentPoint.x-1))
		Directions.RIGHT -> visited.contains(currentPoint.copy(x = currentPoint.x+1))
	}

	private fun isPossibleUnvisitedMove(nextMove: Directions, currentPoint: Coordinates) =
		mazeServiceAdapter.whereCanIGo().contains(nextMove) && !isVisited(nextMove, currentPoint)

	private fun isNextMovePossible(currentPoint: Coordinates) =
		isPossibleUnvisitedMove(Directions.UP, currentPoint) ||
				isPossibleUnvisitedMove(Directions.DOWN, currentPoint) ||
				isPossibleUnvisitedMove(Directions.LEFT, currentPoint) ||
				isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)


	@Test
	fun depthFirstSearch() {
		val startPoint = Coordinates(0, 0)
		var currentPoint = startPoint

		path.add(startPoint)

		visited[startPoint] = true

		while(!mazeServiceAdapter.isExit()) {
			if(isNextMovePossible(currentPoint)) {
				if(isPossibleUnvisitedMove(Directions.UP, currentPoint)) {
					mazeServiceAdapter.go(Directions.UP)
					currentPoint = Coordinates(currentPoint.x, currentPoint.y - 1)
				} else if(isPossibleUnvisitedMove(Directions.DOWN, currentPoint)) {
					mazeServiceAdapter.go(Directions.DOWN)
					currentPoint = Coordinates(currentPoint.x, currentPoint.y + 1)
				} else if(isPossibleUnvisitedMove(Directions.LEFT, currentPoint)) {
					mazeServiceAdapter.go(Directions.LEFT)
					currentPoint = Coordinates(currentPoint.x - 1, currentPoint.y)
				} else if(isPossibleUnvisitedMove(Directions.RIGHT, currentPoint)) {
					mazeServiceAdapter.go(Directions.RIGHT)
					currentPoint = Coordinates(currentPoint.x + 1, currentPoint.y)
				}
				visited[currentPoint] = true
				path.add(0, currentPoint)
			} else {
				val previousPoint = path.removeFirst()
				currentPoint = path.first()
				if(previousPoint.y > currentPoint.y) {
					mazeServiceAdapter.go(Directions.UP)
				} else if(previousPoint.y < currentPoint.y) {
					mazeServiceAdapter.go(Directions.DOWN)
				} else if(previousPoint.x > currentPoint.x) {
					mazeServiceAdapter.go(Directions.LEFT)
				} else if(previousPoint.x < currentPoint.x) {
					mazeServiceAdapter.go(Directions.RIGHT)
				}
			}
		}

		logger.debug {
			path.reversed().map { "[${it.x},${it.y}]" }
		}

	}

}
