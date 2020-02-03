package org.cyk.utility.string;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

@Dependent @Deprecated
public class StringToBarCodeBytesBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<byte[]> implements StringToBarCodeBytesBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String string;
	
	@Override
	protected byte[] __execute__() throws Exception {
		String string = ValueHelper.returnOrThrowIfBlank("String to bar code bytes builder", getString()).toString();
		Code128Bean bean = new Code128Bean();
		final int dpi = 160;
		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
		bean.doQuietZone(false);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();		    
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(byteArrayOutputStream, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		bean.generateBarcode(canvas, string);
		canvas.finish();		    
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public String getString() {
		return string;
	}

	@Override
	public StringToBarCodeBytesBuilder setString(String string) {
		this.string = string;
		return this;
	}
	
	
	
}
