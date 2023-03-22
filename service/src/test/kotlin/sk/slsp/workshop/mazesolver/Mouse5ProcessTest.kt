package sk.slsp.workshop.mazesolver

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.test.Deployment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import sk.slsp.workshop.mazesolver.adapters.mazepath.configuration.MazePathAdapterConfiguration
import sk.slsp.workshop.mazesolver.adapters.mazeservice.configuration.MazeServiceAdapterConfiguration
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.configuration.VisitedPlacesAdapterConfiguration

@SpringBootTest(classes = [
	MySpringBootConfiguration::class,
	MyConfiguration::class,
	VisitedPlacesAdapterConfiguration::class,
	MazePathAdapterConfiguration::class,
	MazeServiceAdapterConfiguration::class,
])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class Mouse5ProcessTest(
	@Autowired private val runtimeService: RuntimeService,
	@Autowired private val taskService: TaskService,
) {
	@Test
	@Deployment(resources = ["mouse5.bpmn"])
	fun `my initial process test`() {

		val instance = runtimeService.startProcessInstanceByKey("mouse-5")

	}

}
