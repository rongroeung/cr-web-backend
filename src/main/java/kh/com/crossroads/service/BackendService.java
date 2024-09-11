package kh.com.crossroads.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kh.com.crossroads.DbProperties;
import kh.com.crossroads.builder.getAllContentId.GetAllContentIdResponse;
import kh.com.crossroads.builder.getAllContentId.GetAllContentIdResponseDto;
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
import kh.com.crossroads.builder.updateContentById.UpdateContentByIdDescriptionRequestDto;
import kh.com.crossroads.builder.updateContentById.UpdateContentByIdMediaRequestDto;
import kh.com.crossroads.builder.updateContentById.UpdateContentByIdRequest;
import kh.com.crossroads.builder.updateContentById.UpdateContentByIdResponse;
import kh.com.crossroads.builder.updateContentById.UpdateContentByIdYoutubeRequestDto;

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
	public ResponseEntity GetAllContentId() {
		// Write log
		log.info("=======> GetAllContentId Request: endpoint=/cr-web-backend/api/v1/getAllContentId\r\n");
		GetAllContentIdResponse response = new GetAllContentIdResponse();
		GetAllContentIdResponseDto responseDto = new GetAllContentIdResponseDto();
		ArrayList<String> content_id = new ArrayList<>();
		
		String queryContent = "SELECT id FROM tbl_content ORDER BY id ASC";
		
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet;
			
			// Get All Content ID from DB
			resultSet = statement.executeQuery(queryContent);
			while (resultSet.next()) {
				content_id.add(resultSet.getString("id"));
			}
			
			statement.close();
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			
			// Write log
			try {
				log.info("=======> GetAllContentId Response: " + objectMapper.writeValueAsString(response) + "\r\n");
			} catch (JsonProcessingException e2) {
				e2.printStackTrace();
			}
			
			return new ResponseEntity<GetAllContentIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseDto.setContent_id(content_id);
		response.setCode(200);
		response.setMessage("Success");
		response.setData(responseDto);
		
		// Write log
		try {
			log.info("=======> GetAllContentId Response: " + objectMapper.writeValueAsString(response) + "\r\n");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<GetAllContentIdResponse>(response, HttpStatus.OK);
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
	
	@SuppressWarnings({ "rawtypes" })
	@ResponseBody
	public ResponseEntity UpdateContentById(String req) {
		// Write log
		log.info("=======> UpdateContentById Request: " + req + "\r\n");
		UpdateContentByIdResponse response = new UpdateContentByIdResponse();
		UpdateContentByIdRequest request = new UpdateContentByIdRequest();
		try {
			request = objectMapper.readValue(req, UpdateContentByIdRequest.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String content_id = request.getId();
		String updateContent = "UPDATE tbl_content "
				+ "SET "
				+ "title = '" + request.getTitle() + "', "
				+ "kh_title = '" + request.getKh_title() + "', "
				+ "sub_title = '" + request.getSub_title() + "', "
				+ "kh_sub_title = '" + request.getKh_sub_title() + "' "
				+ "WHERE id = '" + content_id + "';";
		String updateDescription = "";
		String updateMedia = "";
		String updateYoutube = "";
		
		try {
			if (request.getDescription().size() != 0) {
				for (UpdateContentByIdDescriptionRequestDto description : request.getDescription()) {
					updateDescription += "UPDATE tbl_description "
							+ "SET "
							+ "text = '" + description.getText() + "', "
							+ "kh_text = '" + description.getKh_text() + "' "
							+ "WHERE id = '" + description.getId() + "' AND content_id = '" + content_id + "';";
				}
			}
		} catch (Exception e) {}
		
		try {
			if (request.getMedia().size() != 0) {
				for (UpdateContentByIdMediaRequestDto media : request.getMedia()) {
					updateMedia += "UPDATE tbl_media "
							+ "SET "
							+ "url = '" + media.getUrl() + "', "
							+ "name = '" + media.getName() + "' "
							+ "WHERE id = '" + media.getId() + "' AND content_id = '" + content_id + "';";
				}
			}
		} catch (Exception e) {}
		
		try {
			if (request.getYoutube().size() != 0) {
				for (UpdateContentByIdYoutubeRequestDto youtube : request.getYoutube()) {
					updateYoutube += "UPDATE tbl_youtube "
							+ "SET "
							+ "title = '" + youtube.getTitle() + "', "
							+ "kh_title = '" + youtube.getKh_title() + "', "
							+ "video_url = '" + youtube.getVideo_url() + "', "
							+ "duration = '" + youtube.getDuration() + "', "
							+ "publish_date = '" + youtube.getPublish_date() + "', "
							+ "thumbnail_url = '" + youtube.getThumbnail_url() + "', "
							+ "thumbnail_name = '" + youtube.getThumbnail_name() + "'"
							+ "WHERE id = '" + youtube.getId() + "' AND content_id = '" + content_id + "';";
				}
			}
		} catch (Exception e) {}
		
		String updateScripts = updateContent + updateDescription + updateMedia + updateYoutube;
		String updateResult = "";
		// Write log
		log.info("=======> UpdateContentById DB Script Request:\r\n" + updateScripts + "\r\n");
		
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			// Create a prepared statement for the update script
			PreparedStatement preparedStatement = connection.prepareStatement(updateScripts);
			// Execute the update statement
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				updateResult = "Record updated successfully";
			} else {
				updateResult = "No record found to update";
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			
			// Write log
			try {
				log.info("=======> UpdateContentById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
			} catch (JsonProcessingException e2) {
				e2.printStackTrace();
			}
			
			return new ResponseEntity<UpdateContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setCode(200);
		response.setMessage(updateResult);
		
		// Write log
		try {
			log.info("=======> UpdateContentById Response: " + objectMapper.writeValueAsString(response) + "\r\n");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<UpdateContentByIdResponse>(response, HttpStatus.OK);
	}
}
