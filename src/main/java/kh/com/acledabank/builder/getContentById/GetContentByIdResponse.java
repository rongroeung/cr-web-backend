package kh.com.acledabank.builder.getContentById;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetContentByIdResponse {
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private GetContentByIdResponseDto data;
	
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
	public GetContentByIdResponseDto getData() {
		return data;
	}
	public void setData(GetContentByIdResponseDto data) {
		this.data = data;
	}
}
