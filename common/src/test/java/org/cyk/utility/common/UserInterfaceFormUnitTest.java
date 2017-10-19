package org.cyk.utility.common;

import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.userinterface.Form;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceFormUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertVerticalLayout(){
		Form.Detail formDetail = new Form.Detail();
		formDetail.getLayout().setType(Layout.Type.VERTICAL);
		InputText c1 = new InputText();
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		
		formDetail.add(c1,c2,c3,c4);
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,0,1,1,2);
		assertArea(c3,0,1,2,3);
		assertArea(c4,0,1,3,4);
		
		assertArea(formDetail.getLayout().getArea(),0,1,0,4);
	}
	
	@Test
	public void assertWithLabelVerticalLayout(){
		Form.Detail formDetail = new Form.Detail();
		formDetail.getLayout().setType(Layout.Type.VERTICAL);
		InputText c1 = new InputText();
		c1.setLabelString("a");
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		
		formDetail.add(c1,c2,c3,c4);
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,0,1,1,2);
		assertArea(c3,0,1,2,3);
		assertArea(c4,0,1,3,4);
		
		assertArea(formDetail.getLayout().getArea(),0,1,0,4);
	}
	
	@Test
	public void assertHorizontalLayout(){
		Form.Detail formDetail = new Form.Detail();
		formDetail.getLayout().setType(Layout.Type.HORIZONTAL);
		InputText c1 = new InputText();
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		
		formDetail.add(c1,c2,c3,c4);
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,2,3,0,1);
		assertArea(c4,3,4,0,1);
	
		assertArea(formDetail.getLayout().getArea(),0,4,0,1);
	}
	
	@Test
	public void assertAdaptiveLayout(){
		Form.Detail formDetail = new Form.Detail();
		formDetail.getLayout().setType(Layout.Type.ADAPTIVE);
		InputText c1 = new InputText();
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		InputText c5 = new InputText();
		InputText c6 = new InputText();
		InputText c7 = (InputText) new InputText().setWidth(2);
		InputText c8 = new InputText();
		
		formDetail.add(c1,c2).addBreak().add(c3).addBreak().add(c4,c5).addBreak().add(c6,c7).addBreak().add(c8).addBreak();
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,0,2,1,2);
		assertArea(c4,0,1,2,3);
		assertArea(c5,1,2,2,3);
		assertArea(c6,0,1,3,4);
		assertArea(c7,1,2,3,5);
		assertArea(c8,0,1,4,5);
		
		assertArea(formDetail.getLayout().getArea(),0,2,0,5);
	}
	
	@Test
	public void assertWithLabelAdaptiveLayout(){
		/*
		Form.Detail formDetail = new Form.Detail();
		formDetail.getLayout().setType(Layout.Type.ADAPTIVE);
		InputText c1 = new InputText();
		c1.setLabel(new OutputText());
	
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		InputText c5 = new InputText();
		InputText c6 = new InputText();
		InputText c7 = (InputText) new InputText().setWidth(2);
		InputText c8 = new InputText();
		
		formDetail.add(c1,c2).addBreak().add(c3).addBreak().add(c4,c5).addBreak().add(c6,c7).addBreak().add(c8).addBreak();
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,0,2,1,2);
		assertArea(c4,0,1,2,3);
		assertArea(c5,1,2,2,3);
		assertArea(c6,0,1,3,4);
		assertArea(c7,1,2,3,5);
		assertArea(c8,0,1,4,5);
		
		assertArea(formDetail.getLayout().getArea(),0,2,0,5);
		*/
	}
	
	private void assertArea(InputText visible,Integer x1,Integer x2,Integer y1,Integer y2){
		assertArea(visible.getArea(), x1, x2, y1, y2);;
	}
	
	private void assertArea(Area area,Integer x1,Integer x2,Integer y1,Integer y2){
		assertEquals("x1 not correct",x1,area.getLength().getFrom().intValue());
		assertEquals("x2 not correct",x2,area.getLength().getTo().intValue());
		assertEquals("y1 not correct",y1,area.getWidth().getFrom().intValue());
		assertEquals("y2 not correct",y2,area.getWidth().getTo().intValue());
	}
}
