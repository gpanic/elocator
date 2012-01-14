package feri.rvir.elocator.android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
	
	public static String hash(String password, String algorithm) {
		
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] passwordBytes=password.getBytes();
			md.update(passwordBytes);
			byte[] resultBytes=md.digest();
			
			return new String(resultBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
