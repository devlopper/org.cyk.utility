package org.cyk.utility.common.userinterface;

import java.lang.reflect.Field;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.userinterface.Component.Visible;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UserInterfaceFormUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;

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
	public void assertAdaptiveLayout01(){
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
	public void assertAdaptiveLayout02(){
		Form.Detail formDetail = new Form.Detail(null,Layout.Type.ADAPTIVE);
		InputText c1 = new InputText();
		InputText c2 = new InputText();
		InputText c3 = new InputText();
		InputText c4 = new InputText();
		InputText c5 = new InputText();
		InputText c6 = new InputText();
		InputText c7 = (InputText) new InputText().setWidth(2);
		InputText c8 = new InputText();
		
		formDetail.add(c1).addBreak().add(c2,c3).addBreak().add(c4,c5).addBreak().add(c6,c7).addBreak().add(c8).addBreak();
		
		assertArea(c1,0,2,0,1);
		assertArea(c2,0,1,1,2);
		assertArea(c3,1,2,1,2);
		assertArea(c4,0,1,2,3);
		assertArea(c5,1,2,2,3);
		assertArea(c6,0,1,3,4);
		assertArea(c7,1,2,3,5);
		assertArea(c8,0,1,4,5);
		
		assertArea(formDetail.getLayout().getArea(),0,2,0,5);
	}
	
	@Test
	public void assertAdaptiveLayout03(){
		FormModel model = new FormModel();
		Form.Detail formDetail = new Form.Detail(new Form.Master().setObject(model),Layout.Type.ADAPTIVE);
		
		formDetail.add(model,"f1").addBreak().add(model,"f2").add(model,"f3").addBreak().add(model,"f4").add(model,"f5").addBreak()
		.add(model,"f6").add(model,"f7",1,2).addBreak().add(model,"f8").addBreak();
		
		assertArea(formDetail.getInputByFieldName(model,"f1"),0,2,0,1);
		assertArea(formDetail.getInputByFieldName(model,"f2"),0,1,1,2);
		assertArea(formDetail.getInputByFieldName(model,"f3"),1,2,1,2);
		assertArea(formDetail.getInputByFieldName(model,"f4"),0,1,2,3);
		assertArea(formDetail.getInputByFieldName(model,"f5"),1,2,2,3);
		assertArea(formDetail.getInputByFieldName(model,"f6"),0,1,3,4);
		assertArea(formDetail.getInputByFieldName(model,"f7"),1,2,3,5);
		assertArea(formDetail.getInputByFieldName(model,"f8"),0,1,4,5);
		
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
	
	@Test
	public void getInputByFieldName(){
		FormModel model = new FormModel();
		
		Form.Master master = new Form.Master();
		master.setObject(model);
		Form.Detail formDetail = new Form.Detail(master);
		formDetail.add("f1");
		formDetail.add("sub","f1");
		
		assertEquals(model, formDetail.getInputByFieldName(model,"f1").getObject());
		assertEquals(model.getSub(), formDetail.getInputByFieldName(model.getSub(),"f1").getObject());
	}
	
	@Test
	public void assertInputIdentifierAsStyleClass(){
		FormModel model = new FormModel();
		
		Form.Master master = new Form.Master();
		master.setObject(model);
		Form.Detail formDetail = new Form.Detail(master);
		formDetail.add("f1");
		formDetail.add("sub","f1");
		
		assertEquals("formmodel_f1", formDetail.getInputByFieldName(model,"f1").getPropertiesMap().getIdentifierAsStyleClass());
		assertEquals("formmodel_sub_f1", formDetail.getInputByFieldName(model.getSub(),"f1").getPropertiesMap().getIdentifierAsStyleClass());
	}
	
	@Test
	public void assertInputInstanceof(){
		FormModel model = new FormModel();
		
		Form.Master master = new Form.Master();
		master.setObject(model);
		Form.Detail formDetail = new Form.Detail(master);
		formDetail.add("f1");
		formDetail.add("sub","f1").add("sub1","f1").add("sub2","f1");
		
		assertEquals(InputText.class, formDetail.getInputByFieldName(model,"f1").getClass());
		assertEquals(InputTextarea.class, formDetail.getInputByFieldName(model.getSub(),"f1").getClass());
		assertEquals(InputText.class, formDetail.getInputByFieldName(model.getSub1(),"f1").getClass());
		assertEquals(InputText.class, formDetail.getInputByFieldName(model.getSub2(),"f1").getClass());
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
		
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText 
		private String f1,f2,f3,f4,f5,f6,f7,f8;
		private SubFormModel sub = new SubFormModel();
		private SubFormModel sub1 = new SubFormModel();
		private SubFormModel sub2 = new SubFormModel();
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class SubFormModel {
		
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String f1,sf2,f3;
		
	}

	@Getter @Setter @Accessors(chain=true)
	public static class Entity {
		
		private String code,name,description;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class EntityForm extends Form.Master {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class EntityControls {
		
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String code;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String name;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputTextarea private String description;
		
	}
	
	public static class InputAdapter extends Input.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<? extends Input<?>> getClass(Form.Detail form,Object object, Field field) {
			if(isField(SubFormModel.class, object, ((FormModel)form.getMaster().getObject()).getSub(), field, "f1"))
				return InputTextarea.class;
			return super.getClass(form,object, field);
		}
		
	}
	
	static {
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
	}

	
}
