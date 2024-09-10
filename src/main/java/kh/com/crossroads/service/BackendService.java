package kh.com.crossroads.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kh.com.crossroads.DbProperties;
import kh.com.crossroads.builder.getContentAllLangById.GetContentAllLangByIdDescriptionResponseDto;
import kh.com.crossroads.builder.getContentAllLangById.GetContentAllLangByIdMediaResponseDto;
import kh.com.crossroads.builder.getContentAllLangById.GetContentAllLangByIdResponse;
import kh.com.crossroads.builder.getContentAllLangById.GetContentAllLangByIdResponseDto;
import kh.com.crossroads.builder.getContentAllLangById.GetContentAllLangByIdYoutubeResponseDto;
import kh.com.crossroads.builder.getContentById.GetContentByIdDescriptionResponseDto;
import kh.com.crossroads.builder.getContentById.GetContentByIdMediaResponseDto;
import kh.com.crossroads.builder.getContentById.GetContentByIdResponse;
import kh.com.crossroads.builder.getContentById.GetContentByIdResponseDto;
import kh.com.crossroads.builder.getContentById.GetContentByIdYoutubeResponseDto;

@Service
public class BackendService {
	
	private final Logger log = LoggerFactory.getLogger(BackendService.class);
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	private String dbUrl;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public BackendService(DbProperties pDb) {
		this.dbHost = pDb.getHost();
		this.dbPort = pDb.getPort();
		this.dbName = pDb.getName();
		this.dbUsername = pDb.getUsername();
		this.dbPassword = pDb.getPassword();
		this.dbUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@ResponseBody
	public ResponseEntity GetContentById(String id, String lang) {
		// Write log
		log.info("=======> GetContentById Request: id=" + id + ", lang=" + lang + "\r\n");
		GetContentByIdResponse response = new GetContentByIdResponse();
		GetContentByIdResponseDto responseDto = new GetContentByIdResponseDto();
		ArrayList<GetContentByIdDescriptionResponseDto> descriptions = new ArrayList<>();
		ArrayList<GetContentByIdMediaResponseDto> medium = new ArrayList<>();
		ArrayList<GetContentByIdYoutubeResponseDto> youtubes = new ArrayList<>();
		
		String queryContent = "SELECT * FROM tbl_content WHERE id = '" + id + "'";
		String queryDescription = "SELECT * FROM tbl_description WHERE content_id = '" + id + "'";
		String queryMedia = "SELECT * FROM tbl_media WHERE content_id = '" + id + "'";
		String queryYoutube = "SELECT * FROM tbl_youtube WHERE content_id = '" + id + "'";
		
		String language = "";
		if (lang.equals("kh")) {
			language = "kh_";
		}
		
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet;
			
			// Get Content from DB
			resultSet = statement.executeQuery(queryContent);
			while (resultSet.next()) {
				responseDto.setId(resultSet.getString("id"));
				responseDto.setTitle(resultSet.getString(language + "title"));
				responseDto.setSub_title(resultSet.getString(language + "sub_title"));
			}
			
			// Validate content id not found
			if (responseDto.getId() == null) {
				// Close DB connection
				statement.close();
				connection.close();
				
				response.setCode(404);
				response.setMessage("Content ID not found");
				
				// Write log
				try {
					log.info("=======> GetContentById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}
				
				return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.NOT_FOUND);
			}
			
			// Get Description from DB
			resultSet = statement.executeQuery(queryDescription);
			while (resultSet.next()) {
				GetContentByIdDescriptionResponseDto description = new GetContentByIdDescriptionResponseDto();
				description.setId(resultSet.getString("id"));
				description.setText(resultSet.getString(language + "text"));
				descriptions.add(description);
			}
			
			// Get Media from DB
			resultSet = statement.executeQuery(queryMedia);
			while (resultSet.next()) {
				GetContentByIdMediaResponseDto media = new GetContentByIdMediaResponseDto();
				media.setId(resultSet.getString("id"));
				media.setUrl(resultSet.getString("url"));
				media.setName(resultSet.getString("name"));
				medium.add(media);
			}
			
			// Get Youtube from DB
			resultSet = statement.executeQuery(queryYoutube);
			while (resultSet.next()) {
				GetContentByIdYoutubeResponseDto youtube = new GetContentByIdYoutubeResponseDto();
				youtube.setId(resultSet.getString("id"));
				youtube.setTitle(resultSet.getString(language + "title"));
				youtube.setVideo_url(resultSet.getString("video_url"));
				youtube.setDuration(resultSet.getString("duration"));
				youtube.setPublish_date(resultSet.getString("publish_date"));
				youtube.setThumbnail_url(resultSet.getString("thumbnail_url"));
				youtube.setThumbnail_name(resultSet.getString("thumbnail_name"));
				youtubes.add(youtube);
			}
			
			statement.close();
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			
			// Write log
			try {
				log.info("=======> GetContentById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
			} catch (JsonProcessingException e2) {
				e2.printStackTrace();
			}
			
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseDto.setDescription(descriptions);
		responseDto.setMedia(medium);
		responseDto.setYoutube(youtubes);
		response.setCode(200);
		response.setMessage("Success");
		response.setData(responseDto);
		
		// Write log
		try {
			log.info("=======> GetContentById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@ResponseBody
	public ResponseEntity GetContentAllLangById(String id) {
		// Write log
		log.info("=======> GetContentAllLangById Request: id=" + id + "\r\n");
		GetContentAllLangByIdResponse response = new GetContentAllLangByIdResponse();
		GetContentAllLangByIdResponseDto responseDto = new GetContentAllLangByIdResponseDto();
		ArrayList<GetContentAllLangByIdDescriptionResponseDto> descriptions = new ArrayList<>();
		ArrayList<GetContentAllLangByIdMediaResponseDto> medium = new ArrayList<>();
		ArrayList<GetContentAllLangByIdYoutubeResponseDto> youtubes = new ArrayList<>();
		
		String queryContent = "SELECT * FROM tbl_content WHERE id = '" + id + "'";
		String queryDescription = "SELECT * FROM tbl_description WHERE content_id = '" + id + "'";
		String queryMedia = "SELECT * FROM tbl_media WHERE content_id = '" + id + "'";
		String queryYoutube = "SELECT * FROM tbl_youtube WHERE content_id = '" + id + "'";
		
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet;
			
			// Get Content from DB
			resultSet = statement.executeQuery(queryContent);
			while (resultSet.next()) {
				responseDto.setId(resultSet.getString("id"));
				responseDto.setTitle(resultSet.getString("title"));
				responseDto.setKh_title(resultSet.getString("kh_title"));
				responseDto.setSub_title(resultSet.getString("sub_title"));
				responseDto.setKh_sub_title(resultSet.getString("kh_sub_title"));
			}
			
			// Validate content id not found
			if (responseDto.getId() == null) {
				// Close DB connection
				statement.close();
				connection.close();
				
				response.setCode(404);
				response.setMessage("Content ID not found");
				
				// Write log
				try {
					log.info("=======> GetContentAllLangById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}
				
				return new ResponseEntity<GetContentAllLangByIdResponse>(response, HttpStatus.NOT_FOUND);
			}
			
			// Get Description from DB
			resultSet = statement.executeQuery(queryDescription);
			while (resultSet.next()) {
				GetContentAllLangByIdDescriptionResponseDto description = new GetContentAllLangByIdDescriptionResponseDto();
				description.setId(resultSet.getString("id"));
				description.setText(resultSet.getString("text"));
				description.setKh_text(resultSet.getString("kh_text"));
				descriptions.add(description);
			}
			
			// Get Media from DB
			resultSet = statement.executeQuery(queryMedia);
			while (resultSet.next()) {
				GetContentAllLangByIdMediaResponseDto media = new GetContentAllLangByIdMediaResponseDto();
				media.setId(resultSet.getString("id"));
				media.setUrl(resultSet.getString("url"));
				media.setName(resultSet.getString("name"));
				medium.add(media);
			}
			
			// Get Youtube from DB
			resultSet = statement.executeQuery(queryYoutube);
			while (resultSet.next()) {
				GetContentAllLangByIdYoutubeResponseDto youtube = new GetContentAllLangByIdYoutubeResponseDto();
				youtube.setId(resultSet.getString("id"));
				youtube.setTitle(resultSet.getString("title"));
				youtube.setKh_title(resultSet.getString("kh_title"));
				youtube.setVideo_url(resultSet.getString("video_url"));
				youtube.setDuration(resultSet.getString("duration"));
				youtube.setPublish_date(resultSet.getString("publish_date"));
				youtube.setThumbnail_url(resultSet.getString("thumbnail_url"));
				youtube.setThumbnail_name(resultSet.getString("thumbnail_name"));
				youtubes.add(youtube);
			}
			
			statement.close();
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			
			// Write log
			try {
				log.info("=======> GetContentAllLangById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
			} catch (JsonProcessingException e2) {
				e2.printStackTrace();
			}
			
			return new ResponseEntity<GetContentAllLangByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseDto.setDescription(descriptions);
		responseDto.setMedia(medium);
		responseDto.setYoutube(youtubes);
		response.setCode(200);
		response.setMessage("Success");
		response.setData(responseDto);
		
		// Write log
		try {
			log.info("=======> GetContentAllLangById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<GetContentAllLangByIdResponse>(response, HttpStatus.OK);
	}
}
