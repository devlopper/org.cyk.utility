package org.cyk.utility.__kernel__.persistence.query;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class EntityManagerUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void getByName_1000000(){
		execute("Get field by name",1000000,1000,new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
	
	@Test
	public void getSystemIdentifier_1000000(){
		execute("Get field system identifier",1000000,1000,new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
	
	@Test
	public void getBusinessIdentifier_1000000(){
		execute("Get field business identifier",1000000,1000,new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
	
	@Test
	public void getIdentifiers_1000000(){
		execute("Get field identifiers",1000000,1000,new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
	
	@Test
	public void getPersistablesSingleValueAssociation_1000000(){
		execute("Get field persistables single value association",1000000,1000,new Runnable() {
			@Override
			public void run() {
				
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
