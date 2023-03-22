package sk.slsp.workshop.mazesolver

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableProcessApplication("mySimpleApplication")
@EnableJpaRepositories(basePackages = ["sk.slsp.workshop.mazesolver"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
