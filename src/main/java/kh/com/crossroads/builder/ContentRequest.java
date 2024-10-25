package kh.com.crossroads.builder;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentRequest {
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
	@JsonProperty("create_time")
	private String create_time;
	@JsonProperty("description")
	private ArrayList<DescriptionDto> description;
	@JsonProperty("media")
	private ArrayList<MediaDto> media;
	@JsonProperty("youtube")
	private ArrayList<YoutubeDto> youtube;
	
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public ArrayList<DescriptionDto> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<DescriptionDto> description) {
		this.description = description;
	}
	public ArrayList<MediaDto> getMedia() {
		return media;
	}
	public void setMedia(ArrayList<MediaDto> media) {
		this.media = media;
	}
	public ArrayList<YoutubeDto> getYoutube() {
		return youtube;
	}
	public void setYoutube(ArrayList<YoutubeDto> youtube) {
		this.youtube = youtube;
	}
}
