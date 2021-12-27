package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utility {

	public static Date formatStringToDate(String data) {
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		 try {
			 return sdf.parse(data);  
		 } catch(ParseException e) {}
		 return null;
	}
	
	
	public static java.sql.Date toSqlDate(Date data) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR, 1); 
		calendar.set(Calendar.MINUTE, 0); 
		calendar.set(Calendar.SECOND, 0); 
		calendar.set(Calendar.MILLISECOND, 0); 

		return new java.sql.Date(calendar.getTimeInMillis());
	}
	
	
	public static void printSQLException(SQLException ex) {
		System.out.println("Info sulla SQLException:\n");
		while (ex != null) {
			System.out.println("Message: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("ErrorCode: " + ex.getErrorCode());
			ex.printStackTrace();
			ex = ex.getNextException();
			
		}
	}
	
	
	public static String encryptMD5(String message) {
	    try{
	        MessageDigest m = MessageDigest.getInstance("MD5");
	        m.update(message.getBytes());
	        return String.format("%032x",new BigInteger(1,m.digest()));
	    }
	    catch(Exception e){
	        return null;
	    }
	}

	
	public static String filter(String input) {
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
				
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '<') {
				filtered.append("&lt;");
			}
			else if (c == '>') {
				filtered.append("&gt;");
			}
			else if (c == '&') {
				filtered.append("&amp;");
			} else if (c == '"') {
				filtered.append("&quot;");
			}
			else {
				filtered.append(c);
			}
		} 	
		return filtered.toString();
	}
	
	
	public static boolean checkNomeCognome(String s) {
		if(s.length() == 0)
			return false;
		
		int i = 0;
		for (; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)))
				return false;
		}
		return true;
	}
	
	
	public static boolean checkNomeCompleto(String s) {
		if(s.length() == 0)
			return false;
		
		int i = 0;
		for (; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && !Character.isWhitespace(s.charAt(i)))
				return false;
		}
		return true;
	}
	
	
	public static int checkEta(String e) {
		if (e.equals(""))
			return -1;
		
		int i = 0;
		for (; i < e.length(); i++) {
			if (!Character.isDigit(e.charAt(i)))
				return -1;
		}
		
		int eta = Integer.parseInt(e);
		if (eta < 18 || eta > 130) 
			return -1;
		
		return eta;
	}
	
	
	public static boolean checkCf(String cf) {
		int i = 0;
		for (; (i < cf.length()) && (i < 6); i++ ) {
			if (Character.isDigit(cf.charAt(i)))
				return false;
		}
		if (i >= cf.length()) 
			return false;
		
		for (; (i < cf.length()) && (i < 8); i++ ) {
			if (Character.isLetter(cf.charAt(i)))
				return false;
		}
		if (i >= cf.length()) return false;
		
		if (Character.isDigit(cf.charAt(i++)))
			return false;
		
		for (; (i < cf.length()) && (i < 11); i++ ) {
			if (Character.isLetter(cf.charAt(i)))
				return false;
		}
		if (i >= cf.length()) return false;

		if (Character.isDigit(cf.charAt(i++)))
			return false;

		for (; (i < cf.length()) && (i < 15); i++ ) {
			if (Character.isLetter(cf.charAt(i)))
				return false;
		}
		if (i >= cf.length()) return false;

		if (Character.isDigit(cf.charAt(i++))) {
			return false;
		}

		return true;
		//6 lettere 2 numeri 1 lettera 2 numeri 1 lettera 3 numeri 1 lettera ignore case

	}
	
	
	public static boolean checkCAP(String s) {
		if (s.length() != 5) 
			return false;
		
		int i = 0;
		for (; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}
}

