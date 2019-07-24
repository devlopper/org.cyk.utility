package org.cyk.utility.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.AbstractInstanceGetterImpl;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class MapperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void map_mapStruct_INSTANCE_representationEntity_to_persistenceEntity() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		PersistenceEntity persistenceEntity = RepresentationEntityMapper.INSTANCE.getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifier_system() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12").setDetail(new RepresentationEntityDetail().setIdentifier("159").setInteger01("17"));
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNotNull();
		assertThat(persistenceEntity.getDetail().getIdentifier()).isEqualTo("159");
		assertThat(persistenceEntity.getDetail().getCode()).isEqualTo("a01");
		assertThat(persistenceEntity.getDetail().getInteger01()).isEqualTo(123456);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifier_system_not_exist() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12").setDetail(new RepresentationEntityDetail().setIdentifier("1159").setInteger01("17"));
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNotNull();
		assertThat(persistenceEntity.getDetail().getIdentifier()).isEqualTo("1159");
		assertThat(persistenceEntity.getDetail().getCode()).isNull();;
		assertThat(persistenceEntity.getDetail().getInteger01()).isEqualTo(17);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifier_business() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12").setDetail(new RepresentationEntityDetail().setCode("a01").setInteger01("17"));
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNotNull();
		assertThat(persistenceEntity.getDetail().getIdentifier()).isEqualTo("159");
		assertThat(persistenceEntity.getDetail().getCode()).isEqualTo("a01");
		assertThat(persistenceEntity.getDetail().getInteger01()).isEqualTo(123456);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifier_business_not_exist() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12").setDetail(new RepresentationEntityDetail().setCode("ba01").setInteger01("17"));
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNotNull();
		assertThat(persistenceEntity.getDetail().getIdentifier()).isNull();
		assertThat(persistenceEntity.getDetail().getCode()).isEqualTo("ba01");
		assertThat(persistenceEntity.getDetail().getInteger01()).isEqualTo(17);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifiers_null() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12").setDetail(new RepresentationEntityDetail().setInteger01("17"));
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNotNull();
		assertThat(persistenceEntity.getDetail().getIdentifier()).isNull();;
		assertThat(persistenceEntity.getDetail().getInteger01()).isEqualTo(17);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_null() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNull();
	}
	
	@Test
	public void map_mapStruct_inject_persistenceEntity_to_representationEntity() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		PersistenceEntity persistenceEntity = new PersistenceEntity().setString01("s01").setInteger01(15).setDetail(new PersistenceEntityDetail().setIdentifier("01").setInteger01(17));
		RepresentationEntity representationEntity = __inject__(RepresentationEntityMapper.class).getSource(persistenceEntity);
		assertThat(representationEntity.getString01()).isEqualTo("s01");
		assertThat(representationEntity.getInteger01()).isEqualTo("15");
		assertThat(representationEntity.getDetail()).isNotNull();
		assertThat(representationEntity.getDetail().getIdentifier()).isEqualTo("01");
		assertThat(representationEntity.getDetail().getInteger01()).isEqualTo("17");
	}
	
	@Test
	public void map_getter_representationEntity_to_persistenceEntity() {
		DependencyInjection.setQualifierClass(InstanceGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.clear();
		InstanceGetterImpl.PERSISTENCE_ENTITY_DETAILS.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		MapperSourceDestination<RepresentationEntity,PersistenceEntity> mapper = __inject__(MapperSourceDestinationGetter.class).setSource(representationEntity).setDestinationClass(PersistenceEntity.class).execute().getOutput();
		PersistenceEntity persistenceEntity = mapper.getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class RepresentationEntity {
		private String string01;
		private String integer01;
		private RepresentationEntityDetail detail;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class RepresentationEntityDetail {
		private String identifier;
		private String code;
		private String integer01;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class PersistenceEntity {
		private String string01;
		private Integer integer01;
		@ManyToOne private PersistenceEntityDetail detail;
	}
	
	@Entity
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class PersistenceEntityDetail {
		private String identifier;
		private String code;
		private Integer integer01;
	}
	
	@Mapper
	public static abstract class RepresentationEntityMapper extends AbstractMapperSourceDestinationImpl<RepresentationEntity, PersistenceEntity> {
		private static final long serialVersionUID = 1L;
		
		public static final RepresentationEntityMapper INSTANCE = Mappers.getMapper(RepresentationEntityMapper.class);
	 
	}

	@org.cyk.utility.__kernel__.annotation.Test
	public static class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		public static final Collection<PersistenceEntityDetail> PERSISTENCE_ENTITY_DETAILS = new ArrayList<PersistenceEntityDetail>();
		
		@Override
		protected Collection<Object> __execute__() throws Exception {
			Collection<Object> collection = new ArrayList<>();
			if(getClazz().equals(PersistenceEntityDetail.class) && FieldName.IDENTIFIER.equals(getFieldName())) {
				for(PersistenceEntityDetail index : PERSISTENCE_ENTITY_DETAILS)
					if(ValueUsageType.SYSTEM.equals(getValueUsageType()) && index.getIdentifier().equals(getValue())
							|| ValueUsageType.BUSINESS.equals(getValueUsageType()) && index.getCode().equals(getValue()))
						collection.add(index); 
			}
			return collection;
		}
		
	}
	
}
