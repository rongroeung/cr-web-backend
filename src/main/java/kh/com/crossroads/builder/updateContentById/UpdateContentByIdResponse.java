package kh.com.crossroads.builder.updateContentById;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateContentByIdResponse {
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private UpdateContentByIdResponseDto data;
	
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
	public UpdateContentByIdResponseDto getData() {
		return data;
	}
	public void setData(UpdateContentByIdResponseDto data) {
		this.data = data;
	}
}
