package sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter

import mu.KotlinLogging
import sk.slsp.workshop.mazesolver.Directions
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.GoPort
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.IsExitPort
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.WhereCanIGoPort

class MazeServiceAdapter:
	WhereCanIGoPort,
	GoPort,
	IsExitPort
{

	private val logger = KotlinLogging.logger {}

	override fun whereCanIGo(): Array<Directions> {
		val res: MutableList<Directions> = ArrayList()

		if (canGoUp()) res.add(Directions.UP)
		if (canGoDown()) res.add(Directions.DOWN)
		if (canGoLeft()) res.add(Directions.LEFT)
		if (canGoRight()) res.add(Directions.RIGHT)

		return res.toTypedArray()
	}

	override fun isExit() = maze[posY][posX] == 'e'

	override fun go(direction: Directions) = when(direction) {
		Directions.UP -> goUp()
		Directions.DOWN -> goDown()
		Directions.LEFT -> goLeft()
		Directions.RIGHT -> goRight()
	}

	private val maze = arrayOf(
		charArrayOf('X', 's', 'X', 'X', 'X'),
		charArrayOf('X', ' ', 'X', ' ', 'X'),
		charArrayOf('X', ' ', ' ', ' ', 'X'),
		charArrayOf('X', ' ', 'X', ' ', 'X'),
		charArrayOf('X', 'X', 'X', 'e', 'X')
	)

	private var posX = 1

	private var posY = 0

	private fun goUp() {
		if (canGoUp()) posY -= 1
		logger.debug("going up; pos: [$posX,$posY]")
	}

	private fun goDown() {
		if (canGoDown()) posY += 1
		logger.debug("going down; pos: [$posX,$posY]")
	}

	private fun goLeft() {
		if (canGoLeft()) posX -= 1
		logger.debug("going left; pos: [$posX,$posY]")
	}

	private fun goRight() {
		if (canGoRight()) posX += 1
		logger.debug("going right; pos: [$posX,$posY]")
	}

	private fun canGoUp() =
		posY > 0 && listOf('s', 'e', ' ').contains(maze[posY-1][posX])

	private fun canGoDown() =
		posY < (maze.size-1) && listOf('s', 'e', ' ').contains(maze[posY+1][posX])

	private fun canGoLeft() =
		posX > 0 && listOf('s', 'e', ' ').contains(maze[posY][posX-1])

	private fun canGoRight() =
		posX < (maze[0].size-1) && listOf('s', 'e', ' ').contains(maze[posY][posX+1])


}
