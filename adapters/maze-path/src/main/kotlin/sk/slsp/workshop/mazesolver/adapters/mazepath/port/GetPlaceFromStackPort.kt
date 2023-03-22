package sk.slsp.workshop.mazesolver.adapters.mazepath.port

import sk.slsp.workshop.mazesolver.Coordinates

fun interface GetPlaceFromStackPort {
	fun getPlaceFromStack(): Coordinates
}
