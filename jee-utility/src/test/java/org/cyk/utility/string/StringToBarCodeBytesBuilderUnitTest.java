package org.cyk.utility.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.byte_.BytesOfBarCodeToString;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StringToBarCodeBytesBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test 
	public void build(){
		String string = "Hello";
		StringToBarCodeBytesBuilder stringToBarCodeBytesBuilder = __inject__(StringToBarCodeBytesBuilder.class).setString(string);
		byte[] bytes = stringToBarCodeBytesBuilder.execute().getOutput();
		assertThat(bytes).as("bytes can not be built").isNotNull();
		BytesOfBarCodeToString bytesOfBarCodeToString = __inject__(BytesOfBarCodeToString.class).setBytes(bytes);
		assertThat(bytesOfBarCodeToString.execute().getOutput()).isEqualTo(string);
	}
	
}
