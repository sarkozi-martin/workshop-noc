package sk.slsp.workshop.mazesolver.adapters.mazeservice.port

import sk.slsp.workshop.mazesolver.Directions

fun interface GoPort {
	fun go(direction: Directions)
}