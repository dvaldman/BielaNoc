package sk.nuit.blanche.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	// FIXME 
	public static final String SALT = "f54ds6f4sd";

	public static String sha1(String s) {
        MessageDigest digest = null;
                try {
                        digest = MessageDigest.getInstance("SHA-1");
                } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        digest.reset();
        byte[] data = digest.digest(s.getBytes());
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
	}
	
	public static String sha1(boolean bool) {
		String s = Boolean.toString(bool);
        MessageDigest digest = null;
                try {
                        digest = MessageDigest.getInstance("SHA-1");
                } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        digest.reset();
        byte[] data = digest.digest(s.getBytes());
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
	}
}
