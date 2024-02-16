package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Hits{

	@JsonProperty("hits")
	private List<HitsItem> hits;
	@JsonProperty("total")
	private Total total;

	@JsonProperty("maxScore")
	private Object maxScore;

	public Hits(List<HitsItem> hits, Total total, Object maxScore) {
		this.hits = hits;
		this.total = total;
		this.maxScore = maxScore;
	}

	public Hits() {
	}

	public List<HitsItem> getHits(){
		return hits;
	}

	public Total getTotal(){
		return total;
	}

	public Object getMaxScore(){
		return maxScore;
	}
}