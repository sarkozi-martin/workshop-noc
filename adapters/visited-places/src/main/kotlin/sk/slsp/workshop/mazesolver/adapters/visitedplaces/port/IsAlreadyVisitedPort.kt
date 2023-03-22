package sk.slsp.workshop.mazesolver.adapters.visitedplaces.port

fun interface IsAlreadyVisitedPort {
	fun isAlreadyVisited(x: Int, y: Int): Boolean
}