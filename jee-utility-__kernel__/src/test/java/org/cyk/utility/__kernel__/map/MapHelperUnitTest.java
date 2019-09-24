package org.cyk.utility.__kernel__.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.map.MapHelper.getKeys;

import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class MapHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getKeys_null(){
		assertThat(getKeys(null,null,null,null)).isNull();
	}
	
	@Test
	public void getKeys_empty(){
		assertThat(getKeys(Map.of(),null,null,null)).isNull();
	}
	
	@Test
	public void getKeys_notEmpty(){
		assertThat(getKeys(Map.of(Klass.class,"class"),null,null,null)).containsExactlyInAnyOrder(Klass.class);
	}
	
	@Test
	public void getKeys_class_action(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action"),null,null,null)).containsExactlyInAnyOrder(Klass.class,Action.class);
	}
	
	@Test
	public void getKeys_class_action_string(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action","p","n"),null,null,null)).containsExactlyInAnyOrder(Klass.class,Action.class,"p");
	}
	
	@Test
	public void getKeys_class_action_string_filter_string(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action","p","n"),List.of(String.class),null,null)).containsExactlyInAnyOrder("p");
	}
	
	@Test
	public void getKeys_class_action_string_filter_notString(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action","p","n"),List.of(Class.class.getClass()),null,null)).containsExactlyInAnyOrder(Klass.class,Action.class);
	}
	
	@Test
	public void getKeys_class_action_string_filter_klass(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action","p","n"),null,List.of(Klass.class),null)).containsExactlyInAnyOrder(Klass.class);
	}
	
	@Test
	public void getKeys_class_action_string_filter_action(){
		assertThat(getKeys(Map.of(Klass.class,"class",Action.class,"action","p","n",Action01.class,"a01"),null,List.of(Action.class),null))
		.containsExactlyInAnyOrder(Action.class,Action01.class);
	}
	
	/**/
	
	public static class Klass {
		
	}
	
	public static class ClassSub extends Klass {
		
	}
	
	public static class ClassSubSub extends ClassSub {
		
	}
	
	public static interface Action {
		
	}
	
	public static interface Action01 extends Action {
		
	}
	
	public static interface Action02 extends Action {
		
	}
}
