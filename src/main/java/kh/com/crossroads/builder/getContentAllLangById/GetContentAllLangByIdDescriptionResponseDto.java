package kh.com.crossroads.builder.getContentAllLangById;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetContentAllLangByIdDescriptionResponseDto {
	@JsonProperty("id")
	private String id;
	@JsonProperty("text")
	private String text;
	@JsonProperty("kh_text")
	private String kh_text;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getKh_text() {
		return kh_text;
	}
	public void setKh_text(String kh_text) {
		this.kh_text = kh_text;
	}
}
