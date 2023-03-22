package sk.slsp.workshop.mazesolver.adapters.visitedplaces.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.adapter.VisitedPlacesInMemoryAdapter

@Configuration
class VisitedPlacesAdapterConfiguration {

	@Bean
	fun visitedPointsInMemoryAdapter()
		= VisitedPlacesInMemoryAdapter()

}