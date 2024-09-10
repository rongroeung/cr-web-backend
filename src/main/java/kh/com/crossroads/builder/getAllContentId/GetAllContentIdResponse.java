package kh.com.crossroads.builder.getAllContentId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllContentIdResponse {
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private GetAllContentIdResponseDto data;
	
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
	public GetAllContentIdResponseDto getData() {
		return data;
	}
	public void setData(GetAllContentIdResponseDto data) {
		this.data = data;
	}
}
