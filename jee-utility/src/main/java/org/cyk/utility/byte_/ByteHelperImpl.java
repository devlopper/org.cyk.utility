package org.cyk.utility.byte_;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped @Deprecated
public class ByteHelperImpl extends AbstractHelper implements ByteHelper,Serializable {
	private static final long serialVersionUID = 1L;

	public static String __buildMessageDigest__(byte[] bytes,String algorithm) {
		if(bytes == null || bytes.length == 0)
			return null;
		if(StringHelper.isBlank(algorithm))
			algorithm = "SHA-1";
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException exception) {
			throw new RuntimeException(exception);
		}
		messageDigest.update(bytes);
		return new HexBinaryAdapter().marshal(messageDigest.digest());
	}
	
}
