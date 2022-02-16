package main.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Utility {
	public static final long MILLIS_IN_YEAR = 1000L * 60 * 60 * 24 * 365;
	public static final long MILLIS_IN_DAY = 1000L * 60 * 60 * 24;
	
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
	
	public static boolean checkUsername(String s) {
		if(s.length() == 0)
			return false;
		
		int i = 0;
		for (; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}
	
	
	public static boolean checkEta(String n) {
		if(n.length() == 0)
			return false;
		
		Date nascita = Utility.formatStringToDate(n);
		return !nascita.after(new Date(System.currentTimeMillis()-(MILLIS_IN_YEAR * 14)));
	}
	
	public static boolean checkEmail(String e) {
		if(e.length() == 0)
			return false;
		String[] email = e.split("@");
		return email.length == 2 && !email[0].equals("");
	}
	
	private static boolean isSpecial(String c) {
		return c.equals("@") || c.equals("!") || c.equals("#") 
			|| c.equals("$") || c.equals("%") || c.equals("-") 
			|| c.equals("/") || c.equals("=") || c.equals("^") || c.equals("_") 
			|| c.equals("{") || c.equals("}") || c.equals("~") || c.equals("+");
	}
	
	public static boolean checkPassword(String pass) { //da modificare in futuro con regex
		if(pass.length() < 8) 
			return false;
		
		boolean lower = false;
		boolean upper = false;
		boolean num = false;
		boolean special = false;
		
		for (int i = 0; i < pass.length(); i++) {
			if (Pattern.matches("[0-9]", pass.substring(i, i+1))) {
				if (!num)
					num = true;
			} else if (Pattern.matches("[a-z]", pass.substring(i, i+1))) {
				if (!lower)
					lower = true;
			} else if (Pattern.matches("[A-Z]", pass.substring(i, i+1))) { 
				if (!upper)
					upper = true;
			} else if (isSpecial(pass.substring(i, i+1))) {
				if(!special) {
					special = true;
				}
			} else {
				return false;
			}
		}
		
		return lower && upper && num && special;
	}
	
	public static boolean checkCf(String cf) { //modificare in futuro con regex
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

}

