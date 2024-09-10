package kh.com.crossroads.builder.getContentAllLangById;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetContentAllLangByIdResponseDto {
	@JsonProperty("id")
	private String id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("kh_title")
	private String kh_title;
	@JsonProperty("sub_title")
	private String sub_title;
	@JsonProperty("kh_sub_title")
	private String kh_sub_title;
	@JsonProperty("description")
	private ArrayList<GetContentAllLangByIdDescriptionResponseDto> description;
	@JsonProperty("media")
	private ArrayList<GetContentAllLangByIdMediaResponseDto> media;
	@JsonProperty("youtube")
	private ArrayList<GetContentAllLangByIdYoutubeResponseDto> youtube;
	
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
	public String getKh_title() {
		return kh_title;
	}
	public void setKh_title(String kh_title) {
		this.kh_title = kh_title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getKh_sub_title() {
		return kh_sub_title;
	}
	public void setKh_sub_title(String kh_sub_title) {
		this.kh_sub_title = kh_sub_title;
	}
	public ArrayList<GetContentAllLangByIdDescriptionResponseDto> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<GetContentAllLangByIdDescriptionResponseDto> description) {
		this.description = description;
	}
	public ArrayList<GetContentAllLangByIdMediaResponseDto> getMedia() {
		return media;
	}
	public void setMedia(ArrayList<GetContentAllLangByIdMediaResponseDto> media) {
		this.media = media;
	}
	public ArrayList<GetContentAllLangByIdYoutubeResponseDto> getYoutube() {
		return youtube;
	}
	public void setYoutube(ArrayList<GetContentAllLangByIdYoutubeResponseDto> youtube) {
		this.youtube = youtube;
	}
}
