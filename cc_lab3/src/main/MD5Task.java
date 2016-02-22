package main;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class MD5Task implements Runnable{
	
	private String hash;
	private String start;
	private String end;

	private String result = null;
	
	public String getResult(){
		return result;
	}
	
	public MD5Task(String hash, String start, String end) {
		this.hash = hash;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			md = null;
		}

		if (md == null){
			result = null;
			return;
		}

		while (!start.equals(end)){
			String hex = md5(start);
			if (hex.equals(hash)){
				result = start;
				return;
			}
			start = increment(start);
			if (start.equals("m")){
				System.out.println("ok!");
			}
		}
		result = null;		
	}
	
	private String md5(String str){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Failed to find MD5 algorithm implementation");
			e.printStackTrace();
			return null;
		}
		md.update(str.getBytes());
		
		return (new BigInteger(1,md.digest())).toString(16);
	}
	
	private static int value(char c){
		if (c >= 'a' && c <= 'z')
			return c - 'a';
		else
			return 0;
	}
	
	private static int distance(char c0, char c1){
		int val0 = value(c0);
		int val1 = value(c1);
		return val0 - val1;
	}
		
	public static String increment(String val){
		final String PADZ = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		final String PADA = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		if (val.equals(PADZ.substring(0, val.length()))){
			return PADA.substring(0, val.length() + 1);
		}
		char[] strs = val.toCharArray();
		for (int p = val.length() - 1; p >= 0; p--){
			if (strs[p] != 'z'){
				strs[p] = (char) (strs[p] + 1);
				break;
			}
			else{
				strs[p] = 'a';
			}
		}
		return new String(strs);
	}
	
	public static Vector<String> slice(String start, String end, int count){
		if (count > 20)
			return null;
		
		Vector<String> result = new Vector<String>();
		int diff = 0;
		if (start.length() < end.length())
			diff = value(end.charAt(0));			
		else if (start.length() == end.length()){
			if (start.compareTo(end) < 0)
				diff = distance(end.charAt(0), start.charAt(0));
		}
		if (diff == 0)
			return null;
		
		int d = diff / count;
		char[] strs = end.toCharArray();
		if (start.length() == end.length())
			strs[0] = start.charAt(0);
		else
			strs[0] = 'a';
		
		for (int i = 0; i < count - 1; i++){
			strs[0] += d;
			String nod = new String(strs);
			result.add(nod);
		}
		result.add(end);
		return result;
	}

}
