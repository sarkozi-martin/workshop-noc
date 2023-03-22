package sk.slsp.workshop.mazesolver.adapters.visitedplaces.adapter

import sk.slsp.workshop.mazesolver.Coordinates
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.ClearVisitedPlacesPort
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.IsAlreadyVisitedPort
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.StoreVisitedPlacePort

class VisitedPlacesInMemoryAdapter:
	ClearVisitedPlacesPort,
	StoreVisitedPlacePort,
	IsAlreadyVisitedPort
{

	private val storage: MutableMap<Coordinates, Boolean> = HashMap()

	override fun clearVisitedPlaces() {
		storage.clear()
	}

	override fun storeVisitedPoint(x: Int, y: Int) {
		storage[Coordinates(x, y)] = true
	}

	override fun isAlreadyVisited(x: Int, y: Int): Boolean {
		return storage.contains(Coordinates(x, y))
	}

}
