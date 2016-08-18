package org.cyk.utility.common;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;


public class CompressionUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	private static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes());
		    gzip.close();
		    return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
       
    }
	
	@Override
	protected void _execute_() {
		
		Integer sequenceLenght = 4;
		Integer numberOfSequence = 50 * 7 * 5;
		Collection<String> sequences = new ArrayList<>();
		for(int i = 0; i < numberOfSequence ; i++)
			sequences.add(RandomStringUtils.randomNumeric(sequenceLenght));
		
		sequences.clear();
		sequences.add("0123");
		sequences.add("0987");
		
		BigInteger bigInteger = new BigInteger(StringUtils.join(sequences,Constant.EMPTY_STRING));
		String requestParameterDecimalValue = new String(StringUtils.join(sequences,Constant.CHARACTER_COMA.toString()));
	
		System.out.println("Decimal value                              : "+bigInteger);
		System.out.println("Request parameter decimal value            : "+requestParameterDecimalValue);
		System.out.println("Request parameter decimal value lenght     : "+requestParameterDecimalValue.length());
		String requestParameterHexadecimalValue = bigInteger.toString(10 + 26);
		System.out.println("Hexadecimal value                          : "+requestParameterHexadecimalValue);
		System.out.println("Hexadecimal value lenght                   : "+requestParameterHexadecimalValue.length());

	}
	
}
