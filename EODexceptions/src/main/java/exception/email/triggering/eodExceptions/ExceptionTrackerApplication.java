package exception.email.triggering.eodExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ExceptionTrackerApplication {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionTrackerApplication.class);

	public static void main(String[] args) {

		logger.info("this is a info message");
		logger.warn("this is a warn message");
		logger.error("this is a error message");
		SpringApplication.run(ExceptionTrackerApplication.class, args);
	}

}
