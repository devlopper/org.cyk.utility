package org.cyk.utility.common;

import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;


public class FileUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void extension(){
		assertEquals(FileExtension.BMP,FileExtension.getByValue("bmp"));
		assertEquals(FileExtension.PDF,FileExtension.getByValue("pDf"));
		assertEquals(FileExtension.JPG,FileExtension.getByValue("JPg"));
		Assert.assertNull(FileExtension.getByValue("iamnull"));
	}
	
	@Test
	public void contentType(){
		assertEquals(FileContentType.APPLICATION,FileContentType.getByValue("application"));
		assertEquals(FileContentType.IMAGE,FileContentType.getByValue("ImAge"));
		Assert.assertNull(FileContentType.getByValue("iamnull"));
	}
	
	@Test
	public void mime(){
		assertEquals("application/pdf",FileExtension.PDF.getMime());
		assertEquals("image/bmp",FileExtension.BMP.getMime());
		assertEquals("image/jpg",FileExtension.JPG.getMime());
	}
}
