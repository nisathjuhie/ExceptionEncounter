package exception.email.triggering.eodExceptions.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionDataTble {

    @JsonProperty("springAppName")
    private String springAppName;

    @JsonProperty("log_message")
    private String logMessage;
    @JsonProperty("stack_trace")
    private String stackTrace;

    @JsonProperty("springActiveProfile")
    private String springActiveProfile;
    @JsonProperty("exceptionName")
    private String exceptionName;

    public ExceptionDataTble(String springAppName, String logMessage, String stackTrace, String springActiveProfile, String exceptionName) {
        this.springAppName = springAppName;
        this.logMessage = logMessage;
        this.stackTrace = stackTrace;
        this.springActiveProfile = springActiveProfile;
        this.exceptionName = exceptionName;
    }

    public String getSpringAppName() {
        return springAppName;
    }

    public void setSpringAppName(String springAppName) {
        this.springAppName = springAppName;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getSpringActiveProfile() {
        return springActiveProfile;
    }

    public void setSpringActiveProfile(String springActiveProfile) {
        this.springActiveProfile = springActiveProfile;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }
}
