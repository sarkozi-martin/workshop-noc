package sk.slsp.workshop.mazesolver;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
public class HelloService {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
	
	
	public void initHello(DelegateExecution delegateExecution) {
		delegateExecution.setVariable("initObject", new InitObject("someValue"));
	}
	
	
	public void create(DelegateTask task) {
		final InitObject initObject = (InitObject) task.getVariable("initObject");
		
		logger.info("HelloService::create " + initObject.getSomeVariable());
		
		task.setVariableLocal("userTaskInitObject", initObject);
	}
	
	
	public void complete(DelegateTask task) {
		final String name = (String) task.getVariable("name");
		task.getExecution().setVariable("name", name);
		task.getExecution().setVariable("forceShutdown", false);
	}
	
	
	public void sayHello(DelegateExecution delegateExecution) throws Exception {
		final String name = (String) delegateExecution.getVariable("name");
		
		logger.info("ahoj " + name + "!!!");
	}
	
}
