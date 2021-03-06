package org.cyk.utility.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class FieldSorterUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Override
	protected void _execute_() {
		super._execute_();
		
	}
	
	@Test
	public void field(){
		List<Field> fields = new ArrayList<Field>(FieldHelper.getInstance().get(MyClass.class));
		assertSortFields(new FieldSorter(fields, MyClass.class), fields, "A","B","C","D","E","F","G");
		
		fields = new ArrayList<Field>(FieldHelper.getInstance().get(MyClass.class));
		assertSortFields(new FieldSorter(fields, MyClass.class), fields, "B","A","C","E","F","G","D");
		
		fields = new ArrayList<Field>(FieldHelper.getInstance().get(MyClass.class));
		assertSortFields(new FieldSorter(fields, MyClass.class), fields, "B","C","E","F","G","D");
	}
	
	private void assertSortFields(FieldSorter fieldSorter,List<Field> fields,String...expected){
		fieldSorter.setUseExpectedFieldNames(Boolean.TRUE);
		Integer originalSize = fields.size();
		fieldSorter.setExpectedFieldNames(expected);
		//System.out.println("Before sort : "+getAsString(fields));
		fieldSorter.sort();
		//System.out.println("After sort : "+getAsString(fields));
		assertEquals(expected.length, originalSize-fieldSorter.getNotSorted().size());
		int i = 0;
		for(Field field : fields)
			assertEquals(expected[i++], field.getName());
	}
	
	/*private String getAsString(Collection<Field> fields){
		List<String> names = new ArrayList<>();
		for(Field field : fields)
			names.add(field.getName());
		return names.toString();
	}*/
	
	/**/
	
	@Getter @Setter
	public static class MyClass {
		
		private String C,B,A,D,E,G,F;
		
	}
}
