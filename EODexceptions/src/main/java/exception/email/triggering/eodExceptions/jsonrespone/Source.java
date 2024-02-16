package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Source{
	@JsonProperty("springAppName")
	private String springAppName;

	@JsonProperty("log_message")
	private String logMessage;
	@JsonProperty("stack_trace")
	private String stackTrace;

	@JsonProperty("springActiveProfile")
	private String springActiveProfile;

	public String getSpringAppName(){
		return springAppName;
	}

	public String getLogMessage(){
		return logMessage;
	}

	public String getStackTrace(){
		return stackTrace;
	}

	public String getSpringActiveProfile(){
		return springActiveProfile;
	}

	public Source(String springAppName, String logMessage, String stackTrace, String springActiveProfile) {

		this.springAppName = springAppName;
		this.logMessage = logMessage;
		this.stackTrace = stackTrace;
		this.springActiveProfile = springActiveProfile;
	}

	public Source() {
	}
}
