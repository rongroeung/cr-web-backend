package kh.com.crossroads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.com.crossroads.service.BackendService;

@RestController
public class ServiceController {

	@Autowired
	BackendService backend;
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/getContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity GetContentById(@RequestParam("id") String id, @RequestParam(name = "lang", required = false) String lang) {
		// Set lang = 'en' if lang was not input
		if (lang == null) {
			lang = "en";
		}
		return backend.GetContentById(id, lang);
	}
	
}
