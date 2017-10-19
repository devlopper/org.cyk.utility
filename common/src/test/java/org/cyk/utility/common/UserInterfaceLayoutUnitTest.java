package org.cyk.utility.common;

import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceLayoutUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertVerticalLayout(){
		Layout layout = new Layout().setType(Layout.Type.VERTICAL);
		Component.Visible c1 = new Component.Visible();
		Component.Visible c2 = new Component.Visible();
		Component.Visible c3 = new Component.Visible();
		Component.Visible c4 = new Component.Visible();
		
		layout.addOneChild(c1).addOneChild(c2).addOneChild(c3).addOneChild(c4);
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,0,1,1,2);
		assertArea(c3,0,1,2,3);
		assertArea(c4,0,1,3,4);
		
		assertArea(layout.getArea(),0,1,0,4);
	}
	
	@Test
	public void assertHorizontalLayout(){
		Layout layout = new Layout().setType(Layout.Type.HORIZONTAL);
		Component.Visible c1 = new Component.Visible();
		Component.Visible c2 = new Component.Visible();
		Component.Visible c3 = new Component.Visible();
		Component.Visible c4 = new Component.Visible();
		
		layout.addOneChild(c1).addOneChild(c2).addOneChild(c3).addOneChild(c4);
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,2,3,0,1);
		assertArea(c4,3,4,0,1);
	
		assertArea(layout.getArea(),0,4,0,1);
	}
	
	@Test
	public void assertAdaptiveLayout(){
		Layout layout = new Layout().setType(Layout.Type.ADAPTIVE);
		Component.Visible c1 = new Component.Visible();
		Component.Visible c2 = new Component.Visible();
		Component.Visible c3 = new Component.Visible();
		Component.Visible c4 = new Component.Visible();
		Component.Visible c5 = new Component.Visible();
		Component.Visible c6 = new Component.Visible();
		Component.Visible c7 = new Component.Visible().setWidth(2);
		Component.Visible c8 = new Component.Visible();
		
		layout.addOneChild(c1).addOneChild(c2).end().addOneChild(c3).end().addOneChild(c4).addOneChild(c5).end().addOneChild(c6).addOneChild(c7).end().addOneChild(c8).end();
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,0,2,1,2);
		assertArea(c4,0,1,2,3);
		assertArea(c5,1,2,2,3);
		assertArea(c6,0,1,3,4);
		assertArea(c7,1,2,3,5);
		assertArea(c8,0,1,4,5);
		
		assertArea(layout.getArea(),0,2,0,5);
	}
	
	@Test
	public void assertWithLabelAdaptiveLayout(){
		Layout layout = new Layout().setType(Layout.Type.ADAPTIVE);
		Component.Visible c1 = new Component.Visible();
		Component.Visible c2 = new Component.Visible();
		Component.Visible c3 = new Component.Visible();
		Component.Visible c4 = new Component.Visible();
		Component.Visible c5 = new Component.Visible();
		Component.Visible c6 = new Component.Visible();
		Component.Visible c7 = new Component.Visible().setWidth(2);
		Component.Visible c8 = new Component.Visible();
		
		layout.addManyChild(c1,c2).end().addManyChild(c3).end().addManyChild(c4,c5).end().addManyChild(c6,c7).end().addManyChild(c8).end();
		
		assertArea(c1,0,1,0,1);
		assertArea(c2,1,2,0,1);
		assertArea(c3,0,2,1,2);
		assertArea(c4,0,1,2,3);
		assertArea(c5,1,2,2,3);
		assertArea(c6,0,1,3,4);
		assertArea(c7,1,2,3,5);
		assertArea(c8,0,1,4,5);
		
		assertArea(layout.getArea(),0,2,0,5);
	}
	
	private void assertArea(Component.Visible visible,Integer x1,Integer x2,Integer y1,Integer y2){
		assertArea(visible.getArea(), x1, x2, y1, y2);;
	}
	
	private void assertArea(Area area,Integer x1,Integer x2,Integer y1,Integer y2){
		assertEquals("x1 not correct",x1,area.getLength().getFrom().intValue());
		assertEquals("x2 not correct",x2,area.getLength().getTo().intValue());
		assertEquals("y1 not correct",y1,area.getWidth().getFrom().intValue());
		assertEquals("y2 not correct",y2,area.getWidth().getTo().intValue());
	}
}
