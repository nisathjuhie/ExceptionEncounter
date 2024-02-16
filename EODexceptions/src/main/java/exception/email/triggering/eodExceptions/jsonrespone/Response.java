package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Response{

	@JsonProperty("shards")
	private Shards shards;

	@JsonProperty("hits")
	private Hits hits;

	@JsonProperty("took")
	private int took;

	@JsonProperty("timedOut")
	private boolean timedOut;

	public Shards getShards(){
		return shards;
	}

	public Hits getHits(){
		return hits;
	}

	public int getTook(){
		return took;
	}

	public boolean isTimedOut(){
		return timedOut;
	}

	public Response(Shards shards, Hits hits, int took, boolean timedOut) {
		this.shards = shards;
		this.hits = hits;
		this.took = took;
		this.timedOut = timedOut;
	}

	public Response() {
	}
}
