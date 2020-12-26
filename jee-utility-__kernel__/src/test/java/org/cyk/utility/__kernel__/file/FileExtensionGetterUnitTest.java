package org.cyk.utility.__kernel__.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FileExtensionGetterUnitTest extends AbstractWeldUnitTest {

	@Test
	public void get() throws IOException {
		//DependencyInjection.setQualifierClass(FileExtensionGetter.class, org.cyk.utility.__kernel__.annotation.Test.class);
		assertGet("extension/jpg.jpg","jpeg");
		assertGet("extension/png.png");
		assertGet("extension/pdf.pdf");
		assertGet("extension/xlsx.xlsx");		
	}
	
	/**/
	
	private void assertGet(String resourceName,String expectedExtension) throws IOException {
		byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream(resourceName));
		assertThat(FileExtensionGetter.getInstance().get(bytes)).isEqualTo(expectedExtension);
	}
	
	private void assertGet(String resourceName) throws IOException {
		assertGet(resourceName, StringUtils.substringAfter(resourceName, "."));
	}
	
	/**/
	@org.cyk.utility.__kernel__.annotation.Test
	public static class FileExtensionGetterImpl extends FileExtensionGetter.AbstractImpl implements Serializable {
		
		@Override
		protected String getFromBytes(byte[] bytes) {
			Detector detector = new DefaultDetector();	
			Metadata metadata = new Metadata();
		    MediaType mediaType;
			try {
				mediaType = detector.detect(new ByteArrayInputStream(bytes), metadata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		    return mediaType.getSubtype();
		}
		
	}
}
