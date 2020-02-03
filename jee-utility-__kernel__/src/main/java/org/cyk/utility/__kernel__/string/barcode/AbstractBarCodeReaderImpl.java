package org.cyk.utility.__kernel__.string.barcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractBarCodeReaderImpl extends AbstractObject implements BarCodeReader,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String read(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
			if(bufferedImage == null)
				return null;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		return __read__(bytes,bufferedImage);
	}
	
	protected abstract String __read__(byte[] bytes,BufferedImage bufferedImage);

}