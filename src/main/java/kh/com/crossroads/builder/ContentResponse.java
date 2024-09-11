package kh.com.crossroads.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentResponse {
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private DataResponseDto data;
	
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
	public DataResponseDto getData() {
		return data;
	}
	public void setData(DataResponseDto data) {
		this.data = data;
	}
}
