package kh.com.crossroads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.com.crossroads.service.BackendService;

@RestController
public class ServiceController {

	@Autowired
	BackendService backend;
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/getAllContentId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity GetAllContentId(@RequestParam(name = "sort", required = false) String sort) {
		// Set sort = '' if sort was not input
		if (sort == null) {
			sort = "";
		}
		return backend.GetAllContentId(sort);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/getContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity GetContentById(@RequestParam("id") String id, @RequestParam(name = "lang", required = false) String lang) {
		// Set lang = 'en' if lang was not input
		if (lang == null) {
			lang = "en";
		}
		return backend.GetContentById(id, lang);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/getContentAllLangById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity GetContentAllLangById(@RequestParam("id") String id) {
		return backend.GetContentAllLangById(id);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/cr-web-backend/api/v1/addNewContent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity AddNewContent(@RequestBody String req) {
		return backend.AddNewContent(req);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/cr-web-backend/api/v1/updateContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity UpdateContentById(@RequestBody String req) {
		return backend.UpdateContentById(req);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/cr-web-backend/api/v1/addNewDescription", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity AddNewDescription(@RequestBody String req) {
		return backend.AddNewDescription(req);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/cr-web-backend/api/v1/addNewMedia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity AddNewMedia(@RequestBody String req) {
		return backend.AddNewMedia(req);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/cr-web-backend/api/v1/addNewYoutube", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity AddNewYoutube(@RequestBody String req) {
		return backend.AddNewYoutube(req);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/removeDescription", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity RemoveDescription(@RequestParam("id") String id, @RequestParam("content_id") String content_id) {
		return backend.RemoveDescription(id, content_id);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/removeMedia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity RemoveMedia(@RequestParam("id") String id, @RequestParam("content_id") String content_id) {
		return backend.RemoveMedia(id, content_id);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/removeYoutube", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity RemoveYoutube(@RequestParam("id") String id, @RequestParam("content_id") String content_id) {
		return backend.RemoveYoutube(id, content_id);
	}
	
}
