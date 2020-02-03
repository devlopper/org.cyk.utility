package org.cyk.utility.__kernel__.string.barcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Apache;
import org.cyk.utility.__kernel__.annotation.Google;
import org.cyk.utility.__kernel__.string.barcode.BarCodeBuilder.Parameters;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.krysalis.barcode4j.impl.code128.Code128Bean;

import com.google.zxing.BarcodeFormat;

public class BarCodeUnitTest extends AbstractWeldUnitTest {

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		BarCodeBuilder.INSTANCE.set(null);
		BarCodeReader.INSTANCE.set(null);
	}
	
	@Test
	public void code128Bean_barcode4j(){
		DependencyInjection.setQualifierClassTo(Apache.class, BarCodeBuilder.class);
		String string = "Hello";
		byte[] bytes = BarCodeBuilder.getInstance().build(string, new Parameters().setFormat(new Code128Bean()));
		assertThat(bytes).as("bytes can not be built").isNotNull();
		assertThat(BarCodeReader.getInstance().read(bytes)).isEqualTo(string);
	}
	
	@Test
	public void qr_zxing(){
		DependencyInjection.setQualifierClassTo(Google.class, BarCodeBuilder.class, BarCodeReader.class);
		String string = "Hello";
		byte[] bytes = BarCodeBuilder.getInstance().build(string, new Parameters().setFormat(BarcodeFormat.QR_CODE));
		assertThat(bytes).as("bytes can not be built").isNotNull();
		assertThat(BarCodeReader.getInstance().read(bytes)).isEqualTo(string);
	}
}
