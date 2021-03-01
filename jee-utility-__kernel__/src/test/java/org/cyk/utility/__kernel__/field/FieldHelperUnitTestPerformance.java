package org.cyk.utility.__kernel__.field;

import static org.cyk.utility.__kernel__.field.FieldHelper.getByName;
import static org.cyk.utility.__kernel__.field.FieldHelper.getSystemIdentifier;
import static org.cyk.utility.__kernel__.field.FieldHelper.getBusinessIdentifier;
import static org.cyk.utility.__kernel__.field.FieldHelper.getIdentifiers;
import static org.cyk.utility.__kernel__.field.FieldHelper.getPersistablesSingleValueAssociation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		FieldHelper.clear();
	}
	
	@Test
	public void getByName_1000000(){
		execute("Get field by name",1000000,1000,new Runnable() {
			@Override
			public void run() {
				getByName(Class.class, "intField");
			}
		});
	}
	
	@Test
	public void getSystemIdentifier_1000000(){
		execute("Get field system identifier",1000000,1000,new Runnable() {
			@Override
			public void run() {
				getSystemIdentifier(Class.class);
			}
		});
	}
	
	@Test
	public void getBusinessIdentifier_1000000(){
		execute("Get field business identifier",1000000,1000,new Runnable() {
			@Override
			public void run() {
				getBusinessIdentifier(Class.class);
			}
		});
	}
	
	@Test
	public void getIdentifiers_1000000(){
		execute("Get field identifiers",1000000,1000,new Runnable() {
			@Override
			public void run() {
				getIdentifiers(Class.class);
			}
		});
	}
	
	@Test
	public void getPersistablesSingleValueAssociation_1000000(){
		execute("Get field persistables single value association",1000000,1000,new Runnable() {
			@Override
			public void run() {
				getPersistablesSingleValueAssociation(Class.class);
			}
		});
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
		private Object x;
		@ManyToOne private ClassEntity entity;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class ClassEntity {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
		private Object x;
	}
}
