package kh.com.crossroads.utility;

public class DataManipulation {
//	public static void main(String[] args) {
//		System.out.println(replaceSingleQuote("Love God's. Love Others'. Make Disciples"));
//	}

	public static String replaceSingleQuote(String data) {
		return data.replaceAll("'", "''").replaceAll(";", ",");
	}
}
