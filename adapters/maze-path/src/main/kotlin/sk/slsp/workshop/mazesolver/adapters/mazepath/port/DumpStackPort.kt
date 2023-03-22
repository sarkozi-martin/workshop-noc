package sk.slsp.workshop.mazesolver.adapters.mazepath.port

import sk.slsp.workshop.mazesolver.Coordinates

fun interface DumpStackPort {
	fun dump(): List<Coordinates>
}