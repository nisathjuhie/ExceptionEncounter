package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Total{
	@JsonProperty("value")
	private int value;

	@JsonProperty("relation")
	private String relation;

	public int getValue(){
		return value;
	}

	public String getRelation(){
		return relation;
	}

	public Total(int value, String relation) {
		this.value = value;
		this.relation = relation;
	}

	public Total() {
	}
}
