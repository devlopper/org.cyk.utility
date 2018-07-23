package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractPersistenceServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements PersistenceServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__listenPostConstructPersistenceQueries__();
	}
	
	@Override
	protected void __listenAfterPostConstruct__() {
		super.__listenAfterPostConstruct__();
		__logInfo__(getIdentifier()+" : Number of queries : "+__inject__(CollectionHelper.class).getSize(getQueries()));
	}
	
	/**
	 * Query registration process : Query identifier is bound to attribute name
	 */
	protected void __listenPostConstructPersistenceQueries__(){
		__listenPostConstructPersistenceQueriesIdentifiers__();
	}
	
	/** 1 - get class fields matching query identifier rules</br>
	 * 2 - set fields value with a uniquely computed query identifier
	 */
	protected void __listenPostConstructPersistenceQueriesIdentifiers__(){
		Collection<Field> fields = __inject__(PersistenceQueryIdentifierFieldGetter.class).setClazz(getClass()).execute().getOutput();
		if(__inject__(CollectionHelper.class).isNotEmpty(fields)){
			for(Field index : fields){
				__inject__(FieldValueSetter.class).setObject(this).setField(index).setValue(__buildQueryIdentifierString__(index)).execute();
			}
		}
	}
	
	protected String __buildQueryIdentifierString__(Field field){
		return  __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(__getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(field))
				.setFieldName(field.getName()).execute().getOutput();
	}
	
	protected String __getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(Field field){
		return getClass().getSimpleName();
	}
	
	/**/
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(Object object,Properties properties) {
		__inject__(PersistenceFunctionCreator.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(Object object) {
		return create(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties) {
		__inject__(PersistenceFunctionCreator.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(Object object, Properties properties) {
		__inject__(PersistenceFunctionModifier.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(Object object) {
		update(object, null);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties) {
		__inject__(PersistenceFunctionModifier.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override
	public PersistenceServiceProvider<OBJECT> delete(Object object, Properties properties) {
		__inject__(PersistenceFunctionRemover.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> delete(Object object) {
		return delete(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		__inject__(PersistenceFunctionRemover.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PersistenceQuery> getQueries() {
		return (Collection<PersistenceQuery>) getProperties().getQueries();
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> setQueries(Collection<PersistenceQuery> queries) {
		getProperties().setQueries(queries);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueries(Collection<PersistenceQuery> queries) {
		setQueries(__inject__(CollectionHelper.class).add(HashSet.class, getQueries(), Boolean.TRUE, queries));
		__inject__(PersistenceQueryRepository.class).add(getQueries());//TODO is it the right place ? what if call multiple times ??? are the old ones overwritten ???
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueries(PersistenceQuery... queries) {
		return addQueries(__inject__(CollectionHelper.class).instanciate(queries));
	}
}
