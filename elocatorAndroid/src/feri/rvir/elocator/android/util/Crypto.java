package feri.rvir.elocator.android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.restlet.engine.util.Base64;

public class Crypto {
	
	public static String hash(String password, String algorithm) {
		
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] passwordBytes=password.getBytes();
			md.update(passwordBytes);
			byte[] resultBytes=md.digest();
			
			return Base64.encode(resultBytes, false);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
