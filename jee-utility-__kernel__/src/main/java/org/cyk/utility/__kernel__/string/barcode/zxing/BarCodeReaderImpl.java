package org.cyk.utility.__kernel__.string.barcode.zxing;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Google;
import org.cyk.utility.__kernel__.string.barcode.AbstractBarCodeReaderImpl;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Google
public class BarCodeReaderImpl extends AbstractBarCodeReaderImpl implements Serializable {

	@Override
	protected String __read__(byte[] bytes,BufferedImage bufferedImage) {
		Result result = null;
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
	    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try {			
		    result = new MultiFormatReader().decode(bitmap);
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		if(result == null)
			return null;
	    return result.getText();
	}
	
}
