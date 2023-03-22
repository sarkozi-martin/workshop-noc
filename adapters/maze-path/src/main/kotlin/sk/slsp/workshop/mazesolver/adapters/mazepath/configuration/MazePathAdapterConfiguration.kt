package sk.slsp.workshop.mazesolver.adapters.mazepath.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sk.slsp.workshop.mazesolver.adapters.mazepath.adapter.MazePathInMemoryAdapter

@Configuration
class MazePathAdapterConfiguration {

	@Bean
	fun mazePathInMemoryAdapter() =
		MazePathInMemoryAdapter()

}
