package org.cyk.utility.__kernel__.field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.field.FieldHelper.disjoin;
import static org.cyk.utility.__kernel__.field.FieldHelper.filter;
import static org.cyk.utility.__kernel__.field.FieldHelper.get;
import static org.cyk.utility.__kernel__.field.FieldHelper.getBusinessIdentifier;
import static org.cyk.utility.__kernel__.field.FieldHelper.getByName;
import static org.cyk.utility.__kernel__.field.FieldHelper.getIdentifiers;
import static org.cyk.utility.__kernel__.field.FieldHelper.getName;
import static org.cyk.utility.__kernel__.field.FieldHelper.getNames;
import static org.cyk.utility.__kernel__.field.FieldHelper.getNamesMap;
import static org.cyk.utility.__kernel__.field.FieldHelper.getPersistablesSingleValueAssociation;
import static org.cyk.utility.__kernel__.field.FieldHelper.getSimpleNames;
import static org.cyk.utility.__kernel__.field.FieldHelper.getSystemIdentifier;
import static org.cyk.utility.__kernel__.field.FieldHelper.getType;
import static org.cyk.utility.__kernel__.field.FieldHelper.join;
import static org.cyk.utility.__kernel__.field.FieldHelper.nullify;
import static org.cyk.utility.__kernel__.field.FieldHelper.read;
import static org.cyk.utility.__kernel__.field.FieldHelper.setName;
import static org.cyk.utility.__kernel__.field.FieldHelper.write;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		FieldHelper.clear();
		ConfigurationHelper.clear();
	}
	
	@Test
	public void join_(){
		assertThat(join("f1","f2.f3")).isEqualTo("f1.f2.f3");
	}	
	
	@Test
	public void disjoin_(){
		assertThat(disjoin("f1","f2.f3")).containsExactly("f1","f2","f3");
	}	
	
	@Test
	public void getName_identifier_system() {
		assertThat(getName(Name.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM)).isEqualTo("identifier");
	}
	
	@Test
	public void getName_identifier_system_isId() {
		setName(Name.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		assertThat(getName(Name.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM)).isEqualTo("id");
	}
	
	@Test
	public void getName_identifier_business() {
		assertThat(getName(Name.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS)).isEqualTo("code");
	}
	
	@Test
	public void getName_identifier_business_isCod() {
		setName(Name.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, "cod");
		assertThat(getName(Name.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS)).isEqualTo("cod");
	}
	
	@Test
	public void getSimpleNames_isGetFromNestedTrue() {
		assertThat(getSimpleNames(List.of("user","account.identifier","function.type.name"),Boolean.TRUE)).containsExactlyInAnyOrder("user","account","function");
	}
	
	@Test
	public void getSimpleNames_isGetFromNestedFalse() {
		assertThat(getSimpleNames(List.of("user","account.identifier","function.type.name"),Boolean.FALSE)).containsExactlyInAnyOrder("user");
	}
	
	@Test
	public void getSimpleNames_isGetFromNestedNull() {
		assertThat(getSimpleNames(List.of("user","account.identifier","function.type.name"),null)).containsExactlyInAnyOrder("user");
	}
	
	@Test
	public void getNamesMap_() {
		assertThat(getNamesMap(List.of("user","user.functions","function.type.name","account")).entrySet())
			.containsExactlyInAnyOrder(Map.entry("user",List.of("functions")),Map.entry("function",List.of("type.name")));
	}
	
	@Test
	public void get_classIsNull() {
		Collection<Field> fields = get(null);
		assertThat(fields).isNull();
	}
	
	@Test
	public void get_classParent() {
		Collection<Field> fields = get(ClassParent.class);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("parent_string_f01");
	}
	
	@Test
	public void get_class() {
		Collection<Field> fields = get(Class.class);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("parent_string_f01","string_f01");
	}
	
	@Test
	public void get_classChild() {
		Collection<Field> fields = get(ClassChild.class);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("parent_string_f01","string_f01","child_string_f01");
	}
	
	@Test
	public void get_Interface() {
		Collection<Field> fields = get(Interface.class);
		assertThat(fields).isNull();
		//assertThat(getNames(fields)).containsExactly("PROPERTY_F01","PROPERTY_F02","PROPERTY_F03","NOT_PROPERTY_F01");
	}
	
	@Test
	public void get_I01() {
		Collection<Field> fields = get(I01.class);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("PROPERTY_F01","PROPERTY_F02","PROPERTY_F03","NOT_PROPERTY_F01");
	}
	
	@Test
	public void get_I01Child() {
		Collection<Field> fields = get(I01Child.class);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("PROPERTY_F01","PROPERTY_F02","PROPERTY_F03","NOT_PROPERTY_F01","PROPERTY_F04","NOT_PROPERTY_F02");
	}
	
	@Test
	public void getByName_classIsNull() {
		Field field = getByName(null,(List<String>)null);
		assertThat(field).isNull();
	}
	
	@Test
	public void getByName_classParent() {
		Field field = getByName(ClassParent.class,"parent_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("parent_string_f01");
	}
	
	@Test
	public void getByName_class() {
		Field field = getByName(Class.class,"string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("string_f01");
	}
	
	@Test
	public void getByName_class_parent_string_f01() {
		Field field = getByName(Class.class,"parent_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("parent_string_f01");
	}
	
	@Test
	public void getByName_classChild() {
		Field field = getByName(ClassChild.class,"child_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("child_string_f01");
	}
	
	@Test
	public void getByName_classChild_parent_string_f01() {
		Field field = getByName(ClassChild.class,"parent_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("parent_string_f01");
	}
	
	@Test
	public void getByName_classChild_string_f01() {
		Field field = getByName(ClassChild.class,"string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("string_f01");
	}
	
	@Test
	public void getByName_classComposite() {
		Field field = getByName(ClassCompsite.class,"string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("string_f01");
	}
	
	@Test
	public void getByName_classComposite_parent_parent_string_f01() {
		Field field = getByName(ClassCompsite.class,"parent.parent_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getDeclaringClass()).isEqualTo(ClassParent.class);
		assertThat(field.getName()).isEqualTo("parent_string_f01");
	}
	
	@Test
	public void getByName_classCompositeComposite() {
		Field field = getByName(ClassCompsiteComposite.class,"string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getName()).isEqualTo("string_f01");
	}
	
	@Test
	public void getByName_classCompositeComposite_composite_parent() {
		Field field = getByName(ClassCompsiteComposite.class,"composite.parent");
		assertThat(field).isNotNull();
		assertThat(field.getDeclaringClass()).isEqualTo(ClassCompsite.class);
		assertThat(field.getName()).isEqualTo("parent");
	}
	
	@Test
	public void getByName_classCompositeComposite_composite_parent_parent_string_f01() {
		Field field = getByName(ClassCompsiteComposite.class,"composite.parent.parent_string_f01");
		assertThat(field).isNotNull();
		assertThat(field.getDeclaringClass()).isEqualTo(ClassParent.class);
		assertThat(field.getName()).isEqualTo("parent_string_f01");
	}
	
	@Test
	public void getSystemIdentifier_null() {
		Field field = getSystemIdentifier(null);
		assertThat(field).isNull();
	}
	
	@Test
	public void getSystemIdentifier_HasSystemIdentifier() {
		Field field = getSystemIdentifier(HasSystemIdentifier.class);
		assertThat(field).isEqualTo(FieldUtils.getField(HasSystemIdentifier.class, "identifier", Boolean.TRUE));
	}
	
	@Test
	public void getSystemIdentifier_HasBusinessIdentifier() {
		Field field = getSystemIdentifier(HasBusinessIdentifier.class);
		assertThat(field).isEqualTo(null);
	}
	
	@Test
	public void getSystemIdentifier_HasNoIdentifiers() {
		Field field = getSystemIdentifier(HasNoIdentifiers.class);
		assertThat(field).isEqualTo(null);
	}
	
	@Test
	public void getSystemIdentifier_HasSystemAndBusinessIdentifiers() {
		Field field = getSystemIdentifier(HasSystemAndBusinessIdentifiers.class);
		assertThat(field).isEqualTo(FieldUtils.getField(HasSystemAndBusinessIdentifiers.class, "identifier", Boolean.TRUE));
	}
	
	@Test
	public void getBusinessIdentifier_null() {
		Field field = getBusinessIdentifier(null);
		assertThat(field).isNull();
	}
	
	@Test
	public void getBusinessIdentifier_HasSystemIdentifier() {
		Field field = getBusinessIdentifier(HasSystemIdentifier.class);
		assertThat(field).isEqualTo(null);
	}
	
	@Test
	public void getBusinessIdentifier_HasBusinessIdentifier() {
		Field field = getBusinessIdentifier(HasBusinessIdentifier.class);
		assertThat(field).isEqualTo(FieldUtils.getField(HasBusinessIdentifier.class, "code", Boolean.TRUE));
	}
	
	@Test
	public void getBusinessIdentifier_HasNoIdentifiers() {
		Field field = getBusinessIdentifier(HasNoIdentifiers.class);
		assertThat(field).isEqualTo(null);
	}
	
	@Test
	public void getBusinessIdentifier_HasSystemAndBusinessIdentifiers() {
		Field field = getBusinessIdentifier(HasSystemAndBusinessIdentifiers.class);
		assertThat(field).isEqualTo(FieldUtils.getField(HasSystemAndBusinessIdentifiers.class, "code", Boolean.TRUE));
	}
	
	@Test
	public void getIdentifiers_null() {
		Collection<Field> fields = getIdentifiers(null);
		assertThat(fields).isEqualTo(null);
	}
	
	@Test
	public void getIdentifiers_HasSystemIdentifier() {
		Collection<Field> fields = getIdentifiers(HasSystemIdentifier.class);
		assertThat(fields.stream().map(Field::getName).collect(Collectors.toList())).containsExactlyInAnyOrder("identifier");
	}
	
	@Test
	public void getIdentifiers_HasBusinessIdentifier() {
		Collection<Field> fields = getIdentifiers(HasBusinessIdentifier.class);
		assertThat(fields.stream().map(Field::getName).collect(Collectors.toList())).containsExactlyInAnyOrder("code");
	}
	
	@Test
	public void getIdentifiers_HasNoIdentifiers() {
		Collection<Field> fields = getIdentifiers(HasNoIdentifiers.class);
		assertThat(fields).isNull();
	}
	
	@Test
	public void getIdentifiers_HasSystemAndBusinessIdentifiers() {
		Collection<Field> fields = getIdentifiers(HasSystemAndBusinessIdentifiers.class);
		assertThat(fields.stream().map(Field::getName).collect(Collectors.toList())).containsExactlyInAnyOrder("identifier","code");
	}
	
	@Test
	public void getPersistablesSingleValueAssociation_Persistables() {
		Collection<Field> fields = getPersistablesSingleValueAssociation(Persistables.class);
		assertThat(fields.stream().map(Field::getName).collect(Collectors.toList())).containsExactlyInAnyOrder("classWithEntityWithManyToOne");
	}
	
	@Test
	public void write_byName() {
		Name name = new Name();
		assertThat(name.getCode()).isNull();
		write(name,"code","c01");
		assertThat(name.getCode()).isEqualTo("c01");
	}
	
	@Test
	public void write_byName_unkonwnFieldName() {
		Name name = new Name();
		assertThat(name.getCode()).isNull();
		write(name,"xxx","c01");
		assertThat(name.getCode()).isNull();
	}
	
	@Test
	public void write_byField() throws NoSuchFieldException, SecurityException {
		Name name = new Name();
		assertThat(name.getCode()).isNull();
		write(name,Name.class.getDeclaredField("code"),"c01");
		assertThat(name.getCode()).isEqualTo("c01");
	}
	
	@Test
	public void write_nested() {
		ClassCompsite compsite = new ClassCompsite();
		compsite.setSelf(new Class());
		assertThat(compsite.getSelf().getString_f01()).isNull();
		write(compsite,"self.string_f01","c01");
		assertThat(compsite.getSelf().getString_f01()).isEqualTo("c01");
	}
	
	@Test
	public void write_static() {
		Name.STATIC_STRING = null;
		assertThat(Name.STATIC_STRING).isNull();
		write(Name.class,"STATIC_STRING","c01");
		assertThat(Name.STATIC_STRING).isEqualTo("c01");
	}
	
	@Test
	public void read_byName() {
		Name name = new Name();
		assertThat(read(name,"code")).isEqualTo(name.getCode());
		name.setCode("h01");
		assertThat(read(name,"code")).isEqualTo(name.getCode()).isEqualTo("h01");
	}
	
	@Test
	public void read_byField() throws NoSuchFieldException, SecurityException {
		Name name = new Name();
		assertThat(read(name,Name.class.getDeclaredField("code"))).isEqualTo(name.getCode());
		name.setCode("h01");
		assertThat(read(name,Name.class.getDeclaredField("code"))).isEqualTo(name.getCode()).isEqualTo("h01");
	}
	
	@Test
	public void read_nested() {
		ClassCompsite compsite = new ClassCompsite();
		compsite.setSelf(new Class());
		assertThat(read(compsite,"self.string_f01")).isNull();
		compsite.getSelf().setString_f01("h01");
		assertThat(read(compsite,"self.string_f01")).isEqualTo("h01");
	}
	
	@Test
	public void read_static() {
		Name.STATIC_STRING = "hello";
		assertThat(read(Name.class,"STATIC_STRING")).isEqualTo("hello");
	}
	
	/* type */
	
	@Test
	public void getType_field_simple(){
		Type type = getType(TypeClass.class, "f01");
		assertThat(type).isEqualTo(Integer.class);
	}
	
	@Test
	public void getType_field_typeVariable_f021(){
		Type type = getType(StringClass.class, "f021");
		assertThat(type).isEqualTo(String.class);
	}
	
	@Test
	public void getType_field_typeVariable_f022(){
		Type type = getType(StringClass.class, "f022");
		assertThat(type).isEqualTo(Integer.class);
	}
	
	@Test
	public void getType_field_parameterized_static(){
		Type type = getType(TypeClass.class, "f05");
		Type expectedType = new ParameterizedType() {
			@Override
			public Type getRawType() {
				return Collection.class;
			}
			
			@Override
			public Type getOwnerType() {
				return null;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[]{Integer.class};
			}
		};
		assertThat(type).isEqualTo(expectedType);	
	}
	
	@Test
	public void getType_field_parameterized_dynamic(){
		ParameterizedType type = (ParameterizedType) getType(LongClass.class, "f03");
		ParameterizedType expectedType = new ParameterizedType() {
			@Override
			public Type getRawType() {
				return Collection.class;
			}
			
			@Override
			public Type getOwnerType() {
				return null;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[]{Long.class};
			}
		};
		assertThat(type.getRawType()).isEqualTo(expectedType.getRawType());	
		assertThat(type.getActualTypeArguments()[0]).isEqualTo(expectedType.getActualTypeArguments()[0]);	
	}
	
	@Test
	public void filter_interface_getFieldsOfInterfaceI01(){
		Collection<Field> fields = filter(I01.class, null, null);
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "NOT_PROPERTY_F01",Boolean.TRUE)
				);
	}
	
	@Test
	public void filter_interface_getFieldsWhereNameStartWithPropertyOfInterfaceI01(){
		Collection<Field> fields = filter(I01.class,"^PROPERTY_",null);
		assertThat(fields).hasSize(3).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				);
	}
	
	@Test
	public void filter_interface_getFieldsOfInterfaceI01Child(){
		Collection<Field> fields = filter(I01Child.class,null,null);
		assertThat(fields).hasSize(6).contains(
				FieldUtils.getField(I01Child.class, "PROPERTY_F04",Boolean.TRUE)
				,FieldUtils.getField(I01Child.class, "NOT_PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "NOT_PROPERTY_F01",Boolean.TRUE)
				
				);
	}
	
	@Test
	public void filter_interface_getFieldsWhereNameStartWithPropertyOfInterfaceI01Child(){
		Collection<Field> fields = filter(I01Child.class,"^PROPERTY_",null);
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01Child.class, "PROPERTY_F04",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				
				);
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_null_modifiers_null(){
		Collection<Field> fields = filter(ClassFilter.class,null,null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("serialVersionUID","identifier","code");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_null_modifiers_static(){
		Collection<Field> fields = filter(ClassFilter.class,null,Modifier.STATIC);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("serialVersionUID");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_null_modifiers_notStatic(){
		Collection<Field> fields = filter(ClassFilter.class,null,null,List.of(Modifier.STATIC));
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier","code");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_xxx(){
		Collection<Field> fields = filter(ClassFilter.class,RegularExpressionHelper.buildIsExactly("xxx"),null);
		assertThat(fields).isNull();
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_identifier(){
		Collection<Field> fields = filter(ClassFilter.class,RegularExpressionHelper.buildIsExactly("identifier"),null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_identifierOrCode(){
		Collection<Field> fields = filter(ClassFilter.class,RegularExpressionHelper.buildIsExactly("identifier","code"),null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier","code");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_identifierOrXxx(){
		Collection<Field> fields = filter(ClassFilter.class,RegularExpressionHelper.buildIsExactly("identifier","xxx"),null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier");
	}
	
	@Test
	public void filter_ClassFilter_nameRegularExpression_identifierOrName(){
		Collection<Field> fields = filter(ClassFilter.class,RegularExpressionHelper.buildIsExactly("identifier","name"),null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier");
	}
	
	@Test
	public void filter_ClassFilterSub_nameRegularExpression_identifierOrName(){
		Collection<Field> fields = filter(ClassFilterSub.class,RegularExpressionHelper.buildIsExactly("identifier","name"),null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("identifier","name");
	}
	
	@Test
	public void filter_FilterStartsWith_nameRegularExpression_PROPERTY_(){
		Collection<Field> fields = filter(FilterStartsWith.class,"^PROPERTY_",null);
		assertThat(fields).isNotNull();
		assertThat(getNames(fields)).containsExactly("PROPERTY_CODE");
	}
	
	@Test
	public void nullify_ClassFilter_identifier(){
		ClassFilter object = new ClassFilter().setIdentifier("i01");
		nullify(object, RegularExpressionHelper.buildIsExactly("identifier"), null);
		assertThat(object.getIdentifier()).isNull();
	}
	
	@Test
	public void nullify_ClassFilter_identifierOnly(){
		ClassFilter object = new ClassFilter().setIdentifier("i01").setCode("c01");
		nullify(object, RegularExpressionHelper.buildIsExactly("identifier"), null);
		assertThat(object.getIdentifier()).isNull();
		assertThat(object.getCode()).isEqualTo("c01");
	}	
	
	//@Test
	public void nullify_ClassFilter_notIdentifier(){
		ClassFilter object = new ClassFilter().setIdentifier("i01").setCode("c01");
		nullify(object, RegularExpressionHelper.buildIsNotExactly("identifier"), Modifier.STATIC);
		assertThat(object.getIdentifier()).isEqualTo("i01");
		assertThat(object.getCode()).isNull();
	}	
	
	/* copy */
	
	@Test
	public void copy_fromMap_string(){
		CopyFromMap object = new CopyFromMap();
		assertThat(object.getString()).isNull();
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
		FieldHelper.copy(Map.of("string","my string","integer","12","long_","159"), "string", object);
		assertThat(object.getString()).isEqualTo("my string");
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
	}
	
	@Test
	public void copy_fromMap_string_keyDifferentToFieldName(){
		VariableHelper.writeFieldName(CopyFromMap.class, "the_string", "string");
		CopyFromMap object = new CopyFromMap();
		assertThat(object.getString()).isNull();
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
		FieldHelper.copy(Map.of("the_string","my string","integer","12","long_","159"), "the_string", object);
		assertThat(object.getString()).isEqualTo("my string");
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
	}
	
	@Test
	public void getFieldsNamesMapping_(){
		//ConfigurationHelper.setFieldName(CopyFromMap.class, "the_string", "string");
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(CopyFromMap.class, "the_string");
		assertThat(map).isNotNull();
		assertThat(map).containsExactly(Map.entry("the_string", "the_string"));
	}
	
	@Test
	public void getFieldsNamesMapping_setConfigurationVariable(){
		VariableHelper.writeFieldName(CopyFromMap.class, "the_string", "string");
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(CopyFromMap.class, "the_string");
		assertThat(map).isNotNull();
		assertThat(map).containsExactly(Map.entry("the_string", "string"));
	}
	
	@Test
	public void getByAnnotationClass_annotationIsNotNull(){
		Collection<String> names = FieldHelper.getNames(FieldHelper.getByAnnotationClass(HavingAnnotation01.class,NotNull.class));
		assertThat(names).isNotNull();
		assertThat(names).containsExactlyInAnyOrder("f01","f03");
	}
	
	@Test
	public void getByAnnotationsClasses_annotationIsNotNullAndEmail(){
		Collection<String> names = FieldHelper.getNames(FieldHelper.getByAnnotationsClasses(HavingAnnotation01.class,NotNull.class,Email.class));
		assertThat(names).isNotNull();
		assertThat(names).containsExactlyInAnyOrder("f01","f02","f03");
	}
	
	@Test
	public void getByAnnotationClass_annotationIsEmail(){
		Collection<String> names = FieldHelper.getNames(FieldHelper.getByAnnotationClass(HavingAnnotation01.class,Email.class));
		assertThat(names).isNotNull();
		assertThat(names).containsExactlyInAnyOrder("f02");
	}
	
	@Test
	public void getByAnnotationClass_annotationIsNotNull_(){
		FieldHelper.getNames(FieldHelper.getByAnnotationClass(HavingAnnotation02.class,NotNull.class));
		Collection<String> names = FieldHelper.getNames(FieldHelper.getByAnnotationClass(HavingAnnotation01.class,NotNull.class));
		assertThat(names).isNotNull();
		assertThat(names).containsExactlyInAnyOrder("f01","f03");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassParent {
		private String parent_string_f01;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class extends ClassParent {
		private String string_f01;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassChild extends Class {
		private String child_string_f01;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassCompsite {		
		private String string_f01;
		private ClassParent parent;
		private Class self;
		private ClassParent child;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassCompsiteComposite {
		private String string_f01;
		private ClassCompsite composite;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Name {
		private String identifier;
		private String code;
		public static String STATIC_STRING;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class TypeClass<T1,T2> {
		private Integer f01;
		private T1 f021;
		private T2 f022;
		private Collection<T1> f03;
		private Map<String,String> f04;
		private Collection<Integer> f05;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class StringClass extends TypeClass<String,Integer> {
	
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class LongClass extends TypeClass<Long,String> {
	
	}
	
	public static interface I01 {
		String PROPERTY_F01 = "v01";
		String PROPERTY_F02 = "v02";
		String PROPERTY_F03 = "v03";
		
		String NOT_PROPERTY_F01 = "notproperty";
	}
	
	public static interface I01Child extends I01 {
		String PROPERTY_F04 = "v04";
		
		String NOT_PROPERTY_F02 = "notproperty";
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassFilter implements Serializable {
		private static final long serialVersionUID = 1L;
		private String identifier;
		private String code;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassFilterSub extends ClassFilter {
		private static final long serialVersionUID = 1L;
		private String name;
	}

	public static interface FilterStartsWith {
		public static final String ID = "id";
		public static final String PROPERTY_CODE = "code";
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HasSystemIdentifier implements Serializable {
		private static final long serialVersionUID = 1L;
		private String identifier;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HasBusinessIdentifier implements Serializable {
		private static final long serialVersionUID = 1L;
		private String code;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HasSystemAndBusinessIdentifiers implements Serializable {
		private static final long serialVersionUID = 1L;
		private String identifier;
		private String code;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HasNoIdentifiers implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class PersistablesClassWithEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class PersistablesClassWithNoEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Persistables implements Serializable {
		private static final long serialVersionUID = 1L;
		@ManyToOne private PersistablesClassWithEntity classWithEntityWithManyToOne;
		private PersistablesClassWithEntity classWithEntityWithNoManyToOne;
		private PersistablesClassWithNoEntity classWithNoEntity;
		private Integer integer;
	}

	public static interface Interface {
		String getIdentifier();
		Interface setIdentifier(String identifier);
		
		String getCode();
		Interface setCode(String code);
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Implementation implements Interface {
		private String identifier;
		private String code;
	}

	@Getter @Setter @Accessors(chain=true)
	public static class CopyFromMap {
		private String string;
		private Integer integer;
		private Long long_;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HavingAnnotation01 {
		@NotNull private String f01;
		@Email private Integer f02;
		@NotNull private Long f03;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class HavingAnnotation02 {
		@NotNull private String z01;
		@Email private Integer z02;
		@NotNull private Long z03;
	}
}
