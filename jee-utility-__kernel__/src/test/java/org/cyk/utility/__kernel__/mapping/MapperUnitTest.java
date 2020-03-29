package org.cyk.utility.__kernel__.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.__kernel__.instance.InstanceGetterImpl;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class MapperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__(); 
		InstanceGetterImpl.clear();
	}
	
	@Test
	public void map_mapStruct_INSTANCE_representationEntity_to_persistenceEntity() {		
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		PersistenceEntity persistenceEntity = RepresentationEntityMapper.INSTANCE.getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
	}
	
	@Test
	public void map_mapStruct_inject_representationEntity_to_persistenceEntity_detail_identifier_system() {
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		PersistenceEntity persistenceEntity = __inject__(RepresentationEntityMapper.class).getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
		assertThat(persistenceEntity.getDetail()).isNull();
	}
	
	@Test
	public void map_mapStruct_inject_persistenceEntity_to_representationEntity() {
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
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
		InstanceGetterImpl.add(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));		
		RepresentationEntity representationEntity = new RepresentationEntity().setString01("s01").setInteger01("12");
		MapperSourceDestination<RepresentationEntity,PersistenceEntity> mapper = MapperGetter.getInstance().getBySourceByDestinationClass(representationEntity,PersistenceEntity.class);
		PersistenceEntity persistenceEntity = mapper.getDestination(representationEntity);
		assertThat(persistenceEntity.getString01()).isEqualTo("s01");
		assertThat(persistenceEntity.getInteger01()).isEqualTo(12);
	}
	
	//@Test
	public void map_getter_persistenceEntity_to_representationEntity_integer01_and_detail() {
		PersistenceEntity persistenceEntity = new PersistenceEntity().setString01("s01").setInteger01(12).setDetail(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		MapperSourceDestination<RepresentationEntity,PersistenceEntity> mapper = MapperGetter.getInstance().getBySourceClassByDestinationClass(RepresentationEntity.class,PersistenceEntity.class);
		RepresentationEntity representationEntity = mapper.getSource(persistenceEntity,new MapperSourceDestination.Arguments().setFieldsNames(List.of("integer01","detail")));
		assertThat(representationEntity.getString01()).isNull();
		assertThat(representationEntity.getInteger01()).isEqualTo("12");
		assertThat(representationEntity.getDetail()).isNotNull();
		assertThat(representationEntity.getDetail().getIdentifier()).isEqualTo("159");
		assertThat(representationEntity.getDetail().getCode()).isEqualTo("a01");
		assertThat(representationEntity.getDetail().getInteger01()).isEqualTo("123456");
	}
	
	//@Test
	public void map_getter_persistenceEntity_to_representationEntity_integer01_and_detailCode() {
		PersistenceEntity persistenceEntity = new PersistenceEntity().setString01("s01").setInteger01(12).setDetail(new PersistenceEntityDetail().setIdentifier("159").setCode("a01").setInteger01(123456));
		MapperSourceDestination<RepresentationEntity,PersistenceEntity> mapper = MapperGetter.getInstance().getBySourceClassByDestinationClass(RepresentationEntity.class,PersistenceEntity.class);
		RepresentationEntity representationEntity = mapper.getSource(persistenceEntity,new MapperSourceDestination.Arguments().setFieldsNames(List.of("integer01","detail.code")));
		assertThat(representationEntity.getString01()).isNull();
		assertThat(representationEntity.getInteger01()).isEqualTo("12");
		assertThat(representationEntity.getDetail()).isNotNull();
		assertThat(representationEntity.getDetail().getIdentifier()).isNull();
		assertThat(representationEntity.getDetail().getCode()).isEqualTo("a01");
		assertThat(representationEntity.getDetail().getInteger01()).isNull();
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
	public static abstract class RepresentationEntityMapper extends MapperSourceDestination.AbstractImpl<RepresentationEntity, PersistenceEntity> {
		private static final long serialVersionUID = 1L;		
		public static final RepresentationEntityMapper INSTANCE = Mappers.getMapper(RepresentationEntityMapper.class);
		
		@Override
		protected void __listenPostConstruct__() {
			__sourceClass__ = RepresentationEntity.class;
			__destinationClass__ = PersistenceEntity.class;
			super.__listenPostConstruct__();
		}
		
		
	}
	
	@Mapper
	public static abstract class RepresentationEntityDetailMapper extends MapperSourceDestination.AbstractImpl<RepresentationEntityDetail, PersistenceEntityDetail> {
		private static final long serialVersionUID = 1L;
		
	}
}
