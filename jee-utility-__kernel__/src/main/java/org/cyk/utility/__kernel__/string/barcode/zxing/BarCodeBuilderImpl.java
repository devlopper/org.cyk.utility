package org.cyk.utility.__kernel__.string.barcode.zxing;

import java.io.Serializable;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.cyk.utility.__kernel__.annotation.Google;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.barcode.AbstractBarCodeBuilderImpl;
import org.cyk.utility.__kernel__.value.ValueHelper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Google
public class BarCodeBuilderImpl extends AbstractBarCodeBuilderImpl implements Serializable {

	@Override
	protected byte[] __build__(String string,Parameters parameters) {
		ByteArrayOutputStream byteArrayOutputStream;
		if(BarcodeFormat.QR_CODE.equals(parameters.getFormat())) {
			byteArrayOutputStream = new ByteArrayOutputStream();
			__buildCodeQR__(string, parameters,byteArrayOutputStream);
		}else
			throw new RuntimeException("Bar code algorithm not yet handled : "+parameters.getFormat());
		return byteArrayOutputStream.toByteArray();
	}
	
	protected void __buildCodeQR__(String string,Parameters parameters,ByteArrayOutputStream byteArrayOutputStream) {
		QRCodeWriter writer = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = writer.encode(string, BarcodeFormat.QR_CODE, ValueHelper.defaultToIfBlank(parameters.getWidth(),100)
					, ValueHelper.defaultToIfBlank(parameters.getHeight(),100));
			String extension = parameters.getExtension();
			if(StringHelper.isBlank(extension))
				extension = "png";
			MatrixToImageWriter.writeToStream(bitMatrix, extension, byteArrayOutputStream);
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
}