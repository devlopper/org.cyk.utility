package org.cyk.utility.__kernel__.number;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface ByteHelper {

	public static String buildMessageDigest(byte[] bytes,String algorithm) {
		if(bytes == null || bytes.length == 0)
			return null;
		if(StringHelper.isBlank(algorithm))
			algorithm = ALOGRITHM_SHA_1;
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException exception) {
			throw new RuntimeException(exception);
		}
		messageDigest.update(bytes);
		return new HexBinaryAdapter().marshal(messageDigest.digest());
	}
	
	public static String buildMessageDigest(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		return buildMessageDigest(bytes,null);
	}
	
	String ALOGRITHM_SHA_1 = "SHA-1";
}
