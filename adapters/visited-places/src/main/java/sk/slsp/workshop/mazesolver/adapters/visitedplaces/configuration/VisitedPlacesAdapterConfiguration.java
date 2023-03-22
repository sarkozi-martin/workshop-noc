package sk.slsp.workshop.mazesolver.adapters.visitedplaces.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.adapter.VisitedPlacesInMemoryAdapter;

@Configuration
public class VisitedPlacesAdapterConfiguration {
	
	@Bean
	public VisitedPlacesInMemoryAdapter visitedPlacesInMemoryAdapter() {
		return new VisitedPlacesInMemoryAdapter();
	}
	
}
