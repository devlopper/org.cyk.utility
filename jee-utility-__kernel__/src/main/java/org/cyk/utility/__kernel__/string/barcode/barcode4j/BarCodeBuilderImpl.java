package org.cyk.utility.__kernel__.string.barcode.barcode4j;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.cyk.utility.__kernel__.annotation.Apache;
import org.cyk.utility.__kernel__.string.barcode.AbstractBarCodeBuilderImpl;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

@Apache
public class BarCodeBuilderImpl extends AbstractBarCodeBuilderImpl implements Serializable {

	@Override
	protected byte[] __build__(String string,Parameters parameters) {
		ByteArrayOutputStream byteArrayOutputStream;
		if(parameters.getFormat() instanceof Code128Bean) {
			byteArrayOutputStream = new ByteArrayOutputStream();
			__buildCode128Bean__(string, parameters, byteArrayOutputStream);
		}else
			throw new RuntimeException("Bar code algorithm not yet handled : "+parameters.getFormat());
		return byteArrayOutputStream.toByteArray();
	}
	
	protected void __buildCode128Bean__(String string,Parameters parameters,ByteArrayOutputStream byteArrayOutputStream) {
		Code128Bean bean = (Code128Bean) parameters.getFormat();
		final int dpi = 160;
		//Configure the bar code generator
		bean.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
		bean.doQuietZone(false);
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(byteArrayOutputStream, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		bean.generateBarcode(canvas, string);
		try {
			canvas.finish();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
}