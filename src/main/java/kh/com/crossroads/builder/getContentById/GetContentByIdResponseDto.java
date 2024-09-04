package kh.com.crossroads.builder.getContentById;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetContentByIdResponseDto {
	@JsonProperty("id")
	private String id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("sub_title")
	private String sub_title;
	@JsonProperty("description")
	private ArrayList<GetContentByIdDescriptionResponseDto> description;
	@JsonProperty("media")
	private ArrayList<GetContentByIdMediaResponseDto> media;
	@JsonProperty("youtube")
	private ArrayList<GetContentByIdYoutubeResponseDto> youtube;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public ArrayList<GetContentByIdDescriptionResponseDto> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<GetContentByIdDescriptionResponseDto> description) {
		this.description = description;
	}
	public ArrayList<GetContentByIdMediaResponseDto> getMedia() {
		return media;
	}
	public void setMedia(ArrayList<GetContentByIdMediaResponseDto> media) {
		this.media = media;
	}
	public ArrayList<GetContentByIdYoutubeResponseDto> getYoutube() {
		return youtube;
	}
	public void setYoutube(ArrayList<GetContentByIdYoutubeResponseDto> youtube) {
		this.youtube = youtube;
	}
}
