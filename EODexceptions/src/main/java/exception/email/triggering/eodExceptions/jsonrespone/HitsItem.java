package exception.email.triggering.eodExceptions.jsonrespone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class HitsItem{

	@JsonProperty("index")
	private String index;
	@JsonProperty("type")
	private String type;

	@JsonProperty("_source")
	private Source source;

	@JsonProperty("id")
	private String id;

	@JsonProperty("score")
	private Object score;

	public String getIndex(){
		return index;
	}

	public String getType(){
		return type;
	}

	public Source getSource(){
		return source;
	}

	public String getId(){
		return id;
	}

	public Object getScore(){
		return score;
	}

	public HitsItem(String index, String type, Source source, String id, Object score) {
		this.index = index;
		this.type = type;
		this.source = source;
		this.id = id;
		this.score = score;
	}

	public HitsItem() {
	}
}
