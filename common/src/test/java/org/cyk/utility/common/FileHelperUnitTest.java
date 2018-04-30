package org.cyk.utility.common;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.FileHelper.NameTransformer;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class FileHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void getTextFromFileTxt(){
		assertEquals("Hello World!!!",FileHelper.getInstance().getText(FileHelper.getInstance().getBytes(Action.class, "files/text/hello world.txt"),Boolean.TRUE,Boolean.TRUE));
	}
	
	@Test
	public void getTextFromFilePdf(){
		assertEquals("This is a text from pdf file.",FileHelper.getInstance().getText(FileHelper.getInstance().getBytes(Action.class, "files/pdf/0.pdf"),Boolean.TRUE,Boolean.TRUE));
		//assertEquals("This is a text from pdf file.",FileHelper.getInstance().getText(FileHelper.getInstance().getBytes(Action.class, "files/pdf/1.pdf"),Boolean.TRUE,Boolean.TRUE));
	}
	
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
	public void getContentTypeByBytes(){
		assertEquals(FileContentType.TEXT,FileContentType.getByBytes("application".getBytes()));
	}
	
	@Test
	public void mime(){
		assertEquals("application/pdf",FileExtension.PDF.getMime());
		assertEquals("image/bmp",FileExtension.BMP.getMime());
		assertEquals("image/jpg",FileExtension.JPG.getMime());
	}
	
	@Test
	public void transform(){
		assertEquals("aBcDe", new NameTransformer.Adapter.Default().addSequenceReplacement("1", "").addSequenceReplacement("2", "")
				.addSequenceReplacement("3", "").addSequenceReplacement("4", "").addTokens("a1B1c2D3e4").execute());
		assertEquals("a__B__cDeZZ", new NameTransformer.Adapter.Default().addSequenceReplacement("1", "__").addSequenceReplacement("2", "")
				.addSequenceReplacement("3", "").addSequenceReplacement("4", "ZZ").addTokens("a1B1c2D3e4").execute());
		assertEquals("Report sheet PREMIER TERM _ G1 REPORT SHEET1483202759651", new NameTransformer.Adapter.Default()
				.addTokens("Report sheet PREMIER TERM , G1 REPORT SHEET1483202759651").execute());
	}
	
	@Test
	public void getResourceAsStream(){
		assertNotNull(FileHelper.getInstance().getBytes(Action.class, "image001.png"));	
	}
	
	@Test
	public void get(){
		FileHelper.File file = FileHelper.getInstance().get(Action.class, "image001.png");
		assertEquals("image001", file.getName());	
		assertEquals("png", file.getExtension());	
		assertEquals("image/png", file.getMime());	
	}
	
	@Test
	public void getMime(){
		assertEquals("text/plain", FileHelper.getInstance().getMime("txt"));
		assertEquals("image/png", FileHelper.getInstance().getMime("png"));
	}

	@Test
	public void write(){
		//StreamHelper.getInstance().w FileHelper.getInstance().get
	}
}
