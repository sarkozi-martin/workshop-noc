package sk.slsp.workshop.mazesolver.adapters.mazepath.port

import sk.slsp.workshop.mazesolver.Coordinates

fun interface RemovePlaceFromStackPort {
	fun removePlaceFromStack(): Coordinates
}