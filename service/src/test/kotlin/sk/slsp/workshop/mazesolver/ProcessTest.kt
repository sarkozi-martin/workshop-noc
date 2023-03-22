package sk.slsp.workshop.mazesolver

import mu.KotlinLogging
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
class ProcessTest(
	@Autowired private val runtimeService: RuntimeService,
	@Autowired private val taskService: TaskService,
) {

	private val logger = KotlinLogging.logger {}

	@Test
	@Deployment(resources = ["process.bpmn"])
	fun `my initial process test`() {

		val instance = runtimeService.startProcessInstanceByKey("my-project-process")

		val task = taskService.createTaskQuery().singleResult()

		logger.info(task.name)

		taskService.complete(task.id, mapOf(
			"name" to "tester"
		))
	}

}
