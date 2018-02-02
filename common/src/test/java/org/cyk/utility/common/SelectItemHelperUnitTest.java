package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.SelectItemHelper;
import org.cyk.utility.common.helper.SelectItemHelper.Builder.One;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SelectItemHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;

	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		ClassHelper.getInstance().map(InstanceHelper.Stringifier.Label.class, InstanceLabel.class);
		SelectItemHelper.Builder.One.Adapter.Default.DEFAULT_CLASS = MySelectItem.Builder.class;
	}
	
	@Test
	public void selectItem(){
		Sex masculin = new Sex("M", "Masculin");
		MySelectItem selectItem = new MySelectItem.Builder().setInstance(masculin).execute();
		assertEquals("Masculin", selectItem.getLabel());
	}
	
	@Test
	public void selectItems(){
		Collection<Sex> sexs = new ArrayList<>();
		sexs.add(new Sex("M", "Masculin"));
		sexs.add(new Sex("F", "Feminin"));
		List<MySelectItem> selectItems = new org.cyk.utility.common.helper.SelectItemHelper.Builder.Many.Adapter.Default<MySelectItem>()
				.setInstances(sexs).execute();
		assertEquals("Masculin", selectItems.get(0).getLabel());
		assertEquals("Feminin", selectItems.get(1).getLabel());
	}
	
	@Test
	public void selectItemsWithNull(){
		Collection<Sex> sexs = new ArrayList<>();
		sexs.add(new Sex("M", "Masculin"));
		sexs.add(new Sex("F", "Feminin"));
		List<MySelectItem> selectItems = new org.cyk.utility.common.helper.SelectItemHelper.Builder.Many.Adapter.Default<MySelectItem>().setNullable(Boolean.TRUE)
				.setInstances(sexs).execute();
		assertEquals("--Choisir--", selectItems.get(0).getLabel());
		assertEquals("Masculin", selectItems.get(1).getLabel());
		assertEquals("Feminin", selectItems.get(2).getLabel());
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor
	public static class Sex {
		private String code,name;
		
	}
	
	/**/
	
	@Getter @Setter
	public static class MySelectItem {
		private String label,description;
		private Object value;
		
		public static class Builder extends org.cyk.utility.common.helper.SelectItemHelper.Builder.One.Adapter.Default<MySelectItem> implements Serializable{
			private static final long serialVersionUID = 1L;

			public Builder() {
				super(MySelectItem.class);
			}
			
			@Override
			public One<MySelectItem> setFieldValue(MySelectItem item, String fieldName, Object value) {
				if(org.cyk.utility.common.helper.SelectItemHelper.Builder.One.FIELD_NAME_LABEL.equals(fieldName))
					item.setLabel(value == null ? null : value.toString());
				if(org.cyk.utility.common.helper.SelectItemHelper.Builder.One.FIELD_NAME_DESCRIPTION.equals(fieldName))
					item.setDescription(value == null ? null : value.toString());
				return super.setFieldValue(item, fieldName, value);
			}
		}
	}
	
	public static class InstanceLabel extends InstanceHelper.Stringifier.Label.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected String __execute__() {
			if( getInput() instanceof Sex )
				return ((Sex)getInput()).getName();
			return super.__execute__();
		}
		
	}
}
