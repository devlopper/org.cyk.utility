package org.cyk.utility.byte_;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.imageio.ImageIO;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Dependent
public class BytesOfBarCodeToStringImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements BytesOfBarCodeToString,Serializable {
	private static final long serialVersionUID = 1L;
	
	private byte[] bytes;
	
	@Override
	protected String __execute__() throws Exception {
		byte[] bytes = ValueHelper.returnOrThrowIfBlank("bar code bytes", getBytes());
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
	    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
	    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	    Result result = new MultiFormatReader().decode(bitmap);
	    return result.getText();
	}
	
	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public BytesOfBarCodeToString setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}

}
