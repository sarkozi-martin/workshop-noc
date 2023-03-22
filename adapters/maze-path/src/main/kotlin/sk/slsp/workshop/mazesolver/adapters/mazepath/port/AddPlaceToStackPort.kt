package sk.slsp.workshop.mazesolver.adapters.mazepath.port

import sk.slsp.workshop.mazesolver.Coordinates

fun interface AddPlaceToStackPort {
	fun addPlaceToStack(coordinates: Coordinates)
}