package sk.slsp.workshop.mazesolver;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import sk.slsp.workshop.mazesolver.adapters.mazepath.configuration.MazePathAdapterConfiguration;
import sk.slsp.workshop.mazesolver.adapters.mazeservice.configuration.MazeServiceAdapterConfiguration;
import sk.slsp.workshop.mazesolver.adapters.visitedplaces.configuration.VisitedPlacesAdapterConfiguration;

import java.util.Collections;

@SpringBootTest(classes = {
	MySpringBootConfigurationJava.class,
	MyConfiguration.class,
	VisitedPlacesAdapterConfiguration.class,
	MazePathAdapterConfiguration.class,
	MazeServiceAdapterConfiguration.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class ProcessTestJava {
	
	private final Logger logger = LoggerFactory.getLogger(ProcessTestJava.class);
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	@Test
	@Deployment(resources = {"process.bpmn"})
	public void myInitialProcessTest() {
		
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("my-project-process");
		
		Task task = taskService.createTaskQuery().singleResult();
		
		logger.info(task.getName());
		
		taskService.complete(task.getId(), Collections.singletonMap("name", "tester"));
	}
}
