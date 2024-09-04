package kh.com.acledabank.utility;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {
//	public static void main(String[] args) {
//		System.out.println(getDuration("2024-02-29 14:50:44.157", "2024-02-29 14:50:55.168"));
//	}
	
	public static String getUniqueId() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");  
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		
		return date;
	}
	
	public static String getCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");  
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		
		return date;
	}
	
	public static String getDuration(String reqDatetime, String resDatetime) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startTime = LocalDateTime.parse(reqDatetime, dtf);
        LocalDateTime endTime = LocalDateTime.parse(resDatetime, dtf);
        Duration duration = Duration.between(startTime, endTime);
		
		return String.valueOf(duration.toMillis()) + "ms";
	}
}
