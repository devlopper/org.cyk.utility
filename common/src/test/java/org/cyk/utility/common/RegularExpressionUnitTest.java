package org.cyk.utility.common;

import org.cyk.utility.common.helper.RegularExpressionHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class RegularExpressionUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Test
	public void getMatchTagName(){
		assertEquals("b", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEndName("</b>"));
		assertEquals("b", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEndName("</ b>"));
		assertEquals("b", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEndName("</b >"));
		assertEquals("b", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEndName("</ b >"));
	}
	
	@Test
	public void getMatch(){
		assertEquals("the data i want", RegularExpressionHelper.getInstance().getMatch("some string with 'the data i want' inside", "'(.*?)'"));
		
		assertEquals("</a>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a>content</a>"));
		assertEquals("</ a>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a>content</ a>"));
		assertEquals("</a >", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a>content</a >"));
		assertEquals("</ a >", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a>content</ a >"));
		
		assertEquals("</b>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a><b>content</b></a>"));
		assertEquals("</ b  >", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a><b>content</ b  ></a>"));
		assertEquals("</b>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagEnd("<a><b attribute1=value1 attribute2=value2>content</b></a>"));
		
		assertEquals("<b>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagStart("<a><b>content","b"));
		assertEquals("<b>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagStart("<a><b attribute1=value1 attribute2=value2>content","b"));
	}
	
	
	
}
