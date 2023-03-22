package sk.slsp.workshop.mazesolver.adapters.visitedplaces.port

fun interface StoreVisitedPlacePort {
	fun storeVisitedPoint(x: Int, y: Int)
}