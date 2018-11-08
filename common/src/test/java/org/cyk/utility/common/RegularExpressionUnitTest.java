package org.cyk.utility.common;

import org.cyk.utility.common.string.RegularExpressionHelper;
import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class RegularExpressionUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Test
	public void getMatches(){
		TestCase testCase = new TestCase();
		testCase.assertCollection(getList("org"), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "^org"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "^arg"));
		testCase.assertCollection(getList("org.cyk."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "^org.cyk."));
		testCase.assertCollection(getList("org.cyk.","p1",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(getList("org.cyk.","p1.p11",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.p11.model.p2", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.p11", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(getList("org.cyk.","system.root",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.utility.common.helper.FilterHelper$Filter", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertEquals(Boolean.TRUE, RegularExpressionHelper.getInstance().hasMatch("org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertEquals(Boolean.FALSE, RegularExpressionHelper.getInstance().hasMatch("org.cyk.utility.common.helper.FilterHelper$Filter", "(^org.cyk.)(.+)(.model.)"));
		testCase.clean();
	}
	
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
		//assertEquals("<b>", RegularExpressionHelper.getInstance().getMatchMarkupLanguageTagStart("<a><b attribute1=value1 attribute2=value2>content","b"));
	}
	
	/*@Test
	public void uniformResourceIdentifier_getMatches(){
		TestCase testCase = new TestCase();
		testCase.assertCollection(getList("org"), RegularExpressionHelper.getInstance().getMatches("http://localhost:8080/context/path", "(.+)://(.+)(:){0,1}(.*)(/){0,1}(context/path)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "^arg"));
		testCase.assertCollection(getList("org.cyk."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "^org.cyk."));
		testCase.assertCollection(getList("org.cyk.","p1",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.model.p2", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(getList("org.cyk.","p1.p11",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.p11.model.p2", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.p1.p11", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(getList("org.cyk.","system.root",".model."), RegularExpressionHelper.getInstance().getMatches("org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertCollection(null, RegularExpressionHelper.getInstance().getMatches("org.cyk.utility.common.helper.FilterHelper$Filter", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertEquals(Boolean.TRUE, RegularExpressionHelper.getInstance().hasMatch("org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory", "(^org.cyk.)(.+)(.model.)"));
		testCase.assertEquals(Boolean.FALSE, RegularExpressionHelper.getInstance().hasMatch("org.cyk.utility.common.helper.FilterHelper$Filter", "(^org.cyk.)(.+)(.model.)"));
		testCase.clean();
	}*/
	
}
