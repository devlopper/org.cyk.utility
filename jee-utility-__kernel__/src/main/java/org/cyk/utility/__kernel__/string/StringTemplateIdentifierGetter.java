package org.cyk.utility.__kernel__.string;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.value.Value;

public interface StringTemplateIdentifierGetter {

	String get(NamingModel namingModel);
	
	default String getServerPersistenceEntitySystemIdentifiable() {
		return get(new NamingModel().server().persistence().entities().systemIdentifiable());
	}
	
	default String getServerPersistenceEntityBusinessIdentifiable() {
		return get(new NamingModel().server().persistence().entities().businessIdentifiable());
	}
	
	default String getServerPersistenceEntityNamable() {
		return get(new NamingModel().server().persistence().entities().namable());
	}
	
	default String getServerPersistenceApi() {
		return get(new NamingModel().server().persistence().api());
	}
	
	default String getServerPersistenceImpl() {
		return get(new NamingModel().server().persistence().impl());
	}
	
	default String getServerBusinessApi() {
		return get(new NamingModel().server().business().api());
	}
	
	default String getServerBusinessImpl() {
		return get(new NamingModel().server().business().impl());
	}
	
	default String getServerRepresentationEntitySystemIdentifiable() {
		return get(new NamingModel().server().representation().entities().systemIdentifiable().suffix());
	}
	
	default String getServerRepresentationEntityBusinessIdentifiable() {
		return get(new NamingModel().server().representation().entities().businessIdentifiable().suffix());
	}
	
	default String getServerRepresentationEntityNamable() {
		return get(new NamingModel().server().representation().entities().namable().suffix());
	}
	
	default String getServerRepresentationEntity() {
		return get(new NamingModel().server().representation().entities().suffix());
	}
	
	default String getServerRepresentationEntityMapper() {
		return get(new NamingModel().server().representation().entities().suffix().addSuffixMapper());
	}
	
	default String getServerRepresentationApi() {
		return get(new NamingModel().server().representation().api());
	}
	
	default String getServerRepresentationImpl() {
		return get(new NamingModel().server().representation().impl());
	}
	
	/* Controller */
	
	default String getClientControllerEntitySystemIdentifiable() {
		return get(new NamingModel().client().controller().entities().systemIdentifiable());
	}
	
	default String getClientControllerEntityBusinessIdentifiable() {
		return get(new NamingModel().client().controller().entities().businessIdentifiable());
	}
	
	default String getClientControllerEntityNamable() {
		return get(new NamingModel().client().controller().entities().namable());
	}
	
	default String getClientControllerEntityMapper() {
		return get(new NamingModel().client().controller().entities().setSuffix("Mapper"));
	}
	
	default String getClientControllerApi() {
		return get(new NamingModel().client().controller().api());
	}
	
	default String getClientControllerImpl() {
		return get(new NamingModel().client().controller().impl());
	}
	
	/**/
	
	static StringTemplateIdentifierGetter getInstance() {
		return Helper.getInstance(StringTemplateIdentifierGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
