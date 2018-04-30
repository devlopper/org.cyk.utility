package org.cyk.utility.common.apache.tika;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ExtractText {

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		//detecting the file type
	      BodyContentHandler handler = new BodyContentHandler();
	      Metadata metadata = new Metadata();
	      ByteArrayInputStream inputstream = new ByteArrayInputStream("this is my text!!!".getBytes());
	      ParseContext pcontext=new ParseContext();
	      
	      //Text document parser
	      TXTParser  TexTParser = new TXTParser();
	      TexTParser.parse(inputstream, handler, metadata,pcontext);
	      System.out.println("Contents of the document:" + handler.toString());
	      System.out.println("Metadata of the document:");
	      String[] metadataNames = metadata.names();
	      
	      for(String name : metadataNames) {
	         System.out.println(name + " : " + metadata.get(name));
	      }
	}

}
