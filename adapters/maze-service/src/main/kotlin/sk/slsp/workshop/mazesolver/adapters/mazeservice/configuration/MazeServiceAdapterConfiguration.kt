package sk.slsp.workshop.mazesolver.adapters.mazeservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter.MazeServiceAdapter

@Configuration
class MazeServiceAdapterConfiguration {

	@Bean
	fun mazeService() =
		MazeServiceAdapter()

}
