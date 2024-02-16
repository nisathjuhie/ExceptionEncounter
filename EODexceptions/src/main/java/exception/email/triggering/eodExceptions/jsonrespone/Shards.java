package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Shards{

	@JsonProperty("total")
	private int total;

	@JsonProperty("failed")
	private int failed;

	@JsonProperty("successful")
	private int successful;

	@JsonProperty("skipped")
	private int skipped;

	public int getTotal(){
		return total;
	}

	public int getFailed(){
		return failed;
	}

	public int getSuccessful(){
		return successful;
	}

	public int getSkipped(){
		return skipped;
	}

	public Shards(int total, int failed, int successful, int skipped) {
		this.total = total;
		this.failed = failed;
		this.successful = successful;
		this.skipped = skipped;
	}

	public Shards() {
	}
}
