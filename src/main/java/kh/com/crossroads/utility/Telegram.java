package kh.com.crossroads.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kh.com.crossroads.service.BackendService;

@Service
public class Telegram {
	
	@Autowired
	RestTemplate restTemplate;
	
	private final Logger log = LoggerFactory.getLogger(BackendService.class);
	
	@SuppressWarnings("rawtypes")
	@Async
	public void alertMessage(String telegramUrl, String chatId, String threadId, String serverSide, String service, String function, String reqID, String reqBody, String reqDatetime, String resDatetime, String status, String code, String resBody) {

		String msg = "#Side: " + serverSide;
		msg += "\n#Service: " + service;
		msg += "\n#Function: " + function;
		msg += "\n#ReqTime: " + reqDatetime;
		msg += "\n#ReqID: " + reqID;
		msg += "\n#RequestBody: In attached file";
		msg += "\n#ResTime: " + resDatetime;
		msg += "\n#Duration: " + CurrentDateTime.getDuration(reqDatetime, resDatetime);
		msg += "\n#ResponseStatus: " + status;
		msg += "\n#ResponseHttpCode: " + code;
		msg += "\n#ResponseBody: \n<pre>" + resBody + "</pre>";
        
		// Prepare URL with parametre
        UriComponentsBuilder fullUrl = UriComponentsBuilder.fromHttpUrl(telegramUrl);
		fullUrl.queryParam("parse_mode", "HTML");
		fullUrl.queryParam("chat_id", chatId);
		fullUrl.queryParam("message_thread_id", threadId);
        
        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        // Convert the reqBody string to a byte array
        byte[] reqBodyBytes = reqBody.getBytes();

        // Prepare the file content as a ByteArrayResource
        ByteArrayResource fileResource = new ByteArrayResource(reqBodyBytes) {
            @Override
            public String getFilename() {
                return "req.json";
            }
        };

        // Prepare the file to be sent
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("document", fileResource);
        body.add("caption", msg);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        // Send the file to the Telegram API using RestTemplate
        try {
        	log.info("=======> Telegram Request (RequestID=" + reqID + "): Sent to Telegram API" + "\r\n");
        	ResponseEntity resEntity = restTemplate.exchange(fullUrl.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
        	log.info("=======> Telegram Response (RequestID=" + reqID + "): " + resEntity.getBody() + "\r\n");
		} catch (Exception e) {
			log.error("=======> Telegram Error Response (RequestID=" + reqID + "): " + e.getMessage() + "\r\n");
		}

	}
}
