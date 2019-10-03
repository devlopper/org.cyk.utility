package org.cyk.utility.__kernel__.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.value.ValueHelper.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.test.InstanceGetterImpl;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValueHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, InstanceGetter.class);
		InstanceHelper.setInstanceGetter(DependencyInjection.inject(InstanceGetter.class));
		InstanceGetterImpl.clear();
	}
	
	@Test
	public void convert_2_int_to_string() {
		assertThat(convert(2, String.class)).isInstanceOf(String.class).isEqualTo("2");
	}
	
	@Test
	public void convert_2_string_to_int() {
		assertThat(convert("2", Integer.class)).isInstanceOf(Integer.class).isEqualTo(2);
	}
	
	@Test
	public void convert_field_string_to_field_string() {
		assertThat(convert(FieldHelper.getByName(ConvertSource.class, "string"),"2", FieldHelper.getByName(ConvertDestination.class, "string")))
			.isInstanceOf(String.class).isEqualTo("2");
	}
	
	@Test
	public void convert_field_string_to_field_integer() {
		assertThat(convert(FieldHelper.getByName(ConvertSource.class, "string"),"2", FieldHelper.getByName(ConvertDestination.class, "integer")))
			.isInstanceOf(Integer.class).isEqualTo(2);
	}
	
	@Test
	public void convert_field_integer_to_field_string() {
		assertThat(convert(FieldHelper.getByName(ConvertSource.class, "integer"),2, FieldHelper.getByName(ConvertDestination.class, "string")))
			.isInstanceOf(String.class).isEqualTo("2");
	}
	
	@Test
	public void convert_field_representation_identifier_system_to_field_persistence() {
		InstanceGetterImpl.add(PersistenceEntityType.class, new PersistenceEntityType().setIdentifier("i01").setCode("c01").setString("s01").setInteger(159));
		RepresentationEntityType representationEntityType = new RepresentationEntityType().setIdentifier("i01");
		Object value = convert(FieldHelper.getByName(RepresentationEntity.class, "type"),representationEntityType, FieldHelper.getByName(PersistenceEntity.class, "type"));
		assertThat(value).isInstanceOf(PersistenceEntityType.class);
		PersistenceEntityType persistenceEntityType = (PersistenceEntityType) value;
		assertThat(persistenceEntityType.getIdentifier()).isEqualTo("i01");
		assertThat(persistenceEntityType.getCode()).isEqualTo("c01");
		assertThat(persistenceEntityType.getString()).isEqualTo("s01");
		assertThat(persistenceEntityType.getInteger()).isEqualTo(159);
	}
	
	@Test
	public void convert_field_representation_identifier_business_to_field_persistence() {
		InstanceGetterImpl.add(PersistenceEntityType.class, new PersistenceEntityType().setIdentifier("i01").setCode("c01").setString("s01").setInteger(159));
		RepresentationEntityType representationEntityType = new RepresentationEntityType().setCode("c01");
		Object value = convert(FieldHelper.getByName(RepresentationEntity.class, "type"),representationEntityType, FieldHelper.getByName(PersistenceEntity.class, "type"));
		assertThat(value).isInstanceOf(PersistenceEntityType.class);
		PersistenceEntityType persistenceEntityType = (PersistenceEntityType) value;
		assertThat(persistenceEntityType.getIdentifier()).isEqualTo("i01");
		assertThat(persistenceEntityType.getCode()).isEqualTo("c01");
		assertThat(persistenceEntityType.getString()).isEqualTo("s01");
		assertThat(persistenceEntityType.getInteger()).isEqualTo(159);
	}
	
	@Test
	public void convert_field_representation_identifier_system_arraylist_to_field_persistence_collection() {
		InstanceGetterImpl.add(PersistenceEntityType.class, new PersistenceEntityType().setIdentifier("i01").setCode("c01").setString("s01").setInteger(159));
		RepresentationEntityType representationEntityType = new RepresentationEntityType().setIdentifier("i01");
		Object value = convert(FieldHelper.getByName(RepresentationEntity.class, "types"),List.of(representationEntityType), FieldHelper.getByName(PersistenceEntity.class, "types"));
		assertThat(value).isInstanceOf(Collection.class);
		@SuppressWarnings("unchecked")
		Collection<PersistenceEntityType> persistenceEntityTypes = (Collection<PersistenceEntityType>) value;
		System.out.println(persistenceEntityTypes.getClass());
		PersistenceEntityType persistenceEntityType = persistenceEntityTypes.iterator().next();
		assertThat(persistenceEntityType.getIdentifier()).isEqualTo("i01");
		assertThat(persistenceEntityType.getCode()).isEqualTo("c01");
		assertThat(persistenceEntityType.getString()).isEqualTo("s01");
		assertThat(persistenceEntityType.getInteger()).isEqualTo(159);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		
		private String f01;
		private Sub sub;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sub {
		
		private String f01;
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class ConvertBase {
		private String string;
		private Integer integer;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ConvertSource extends ConvertBase {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ConvertDestination extends ConvertBase{
		
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class PersistenceEntity {
		private String identifier;
		private String code;
		private String string;
		private Integer integer;
		@ManyToOne private PersistenceEntityType type;
		@Transient private Collection<PersistenceEntityType> types;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class PersistenceEntityType {
		private String identifier;
		private String code;
		private String string;
		private Integer integer;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class RepresentationEntity {
		private String identifier;
		private String code;
		private String string;
		private Integer integer;
		private RepresentationEntityType type;
		@Transient private ArrayList<RepresentationEntityType> types;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class RepresentationEntityType {
		private String identifier;
		private String code;
		private String string;
		private Integer integer;
	}
}
