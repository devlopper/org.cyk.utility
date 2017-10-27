package org.cyk.utility.common.utility.userinterface;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.userinterface.Component.Visible;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UserInterfaceFormUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;

	@Test
	public void getForm(){
		Form.Master.setClass(Entity.class, Constant.Action.CREATE, EntityForm.class);
		assertEquals(EntityForm.class, Form.Master.getClass(Entity.class, Constant.Action.CREATE));
	}
	
	@Test
	public void assertFromModel(){
		Form form = new Form();
		form.getLayout().setType(Layout.Type.VERTICAL);
		
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
		
		form.layOut(formDetail);
		
		Container commands = new Container();
		commands.getLayout().setType(Layout.Type.HORIZONTAL);
		form.layOut(commands);
		
		assertArea(formDetail,0,1,0,1);
		assertArea(commands,0,1,1,2);
	}
	
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
		Input<String> c1 = new Input<String>().setLabelFromIdentifier("a");
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
	
	private void assertArea(Visible visible,Integer x1,Integer x2,Integer y1,Integer y2){
		assertArea(visible.getArea(), x1, x2, y1, y2);;
	}
	
	private void assertArea(Area area,Integer x1,Integer x2,Integer y1,Integer y2){
		assertEquals("x1 not correct",x1,area.getLength().getFrom().intValue());
		assertEquals("x2 not correct",x2,area.getLength().getTo().intValue());
		assertEquals("y1 not correct",y1,area.getWidth().getFrom().intValue());
		assertEquals("y2 not correct",y2,area.getWidth().getTo().intValue());
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class FormModel {
		
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String f1,f2,f3,f4;
		
	}

	public static class Entity {
		
	}
	
	public static class EntityForm extends Form.Master {
		
	}
}
