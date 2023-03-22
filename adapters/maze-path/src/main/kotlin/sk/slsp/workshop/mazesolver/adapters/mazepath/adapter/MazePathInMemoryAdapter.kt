package sk.slsp.workshop.mazesolver.adapters.mazepath.adapter

import sk.slsp.workshop.mazesolver.Coordinates
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.AddPlaceToStackPort
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.DumpStackPort
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.GetPlaceFromStackPort
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.RemovePlaceFromStackPort

class MazePathInMemoryAdapter:
	AddPlaceToStackPort,
	GetPlaceFromStackPort,
	RemovePlaceFromStackPort,
	DumpStackPort
{

	private val path: MutableList<Coordinates> = mutableListOf()

	override fun addPlaceToStack(coordinates: Coordinates) {
		path.add(coordinates)
	}

	override fun getPlaceFromStack(): Coordinates {
		return path.last()
	}

	override fun removePlaceFromStack(): Coordinates {
		return path.removeLast()
	}

	override fun dump(): List<Coordinates> {
		return path
	}

}
