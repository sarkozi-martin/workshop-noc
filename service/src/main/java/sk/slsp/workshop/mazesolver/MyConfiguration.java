package sk.slsp.workshop.mazesolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.AddPlaceToStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.GetPlaceFromStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazepath.port.RemovePlaceFromStackPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.adapter.MazeServiceAdapter;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.GoPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.IsExitPort;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.port.WhereCanIGoPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.IsAlreadyVisitedPort;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.port.StoreVisitedPlacePort;

@Configuration
public class MyConfiguration {

	@Bean
	public HelloService helloService() {
		return new HelloService();
	}

	@Bean
	public MouseService mouseService(
			WhereCanIGoPort whereCanIGoPort,
			GoPort goPort,
			IsExitPort isExitPort,
			StoreVisitedPlacePort storeVisitedPlacePort,
			IsAlreadyVisitedPort isAlreadyVisitedPort,
			AddPlaceToStackPort addPlaceToStackPort,
			GetPlaceFromStackPort getPlaceFromStackPort,
			RemovePlaceFromStackPort removePlaceFromStackPort
	) {
		return new MouseService(
				whereCanIGoPort,
				goPort,
				isExitPort,
				storeVisitedPlacePort,
				isAlreadyVisitedPort,
				addPlaceToStackPort,
				getPlaceFromStackPort,
				removePlaceFromStackPort
		);
	}

}
