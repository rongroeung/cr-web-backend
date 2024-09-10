package kh.com.crossroads.builder.getAllContentId;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllContentIdResponseDto {
	@JsonProperty("content_id")
	private ArrayList<String> content_id;

	public ArrayList<String> getContent_id() {
		return content_id;
	}
	public void setContent_id(ArrayList<String> content_id) {
		this.content_id = content_id;
	}
}
