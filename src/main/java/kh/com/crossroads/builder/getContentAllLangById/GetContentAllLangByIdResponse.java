package kh.com.crossroads.builder.getContentAllLangById;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetContentAllLangByIdResponse {
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private GetContentAllLangByIdResponseDto data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public GetContentAllLangByIdResponseDto getData() {
		return data;
	}
	public void setData(GetContentAllLangByIdResponseDto data) {
		this.data = data;
	}
}
