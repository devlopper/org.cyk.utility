package org.cyk.utility.__kernel__.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

public interface PersistenceUnitHelper {

	static Collection<Field> getAssociationsFields(Class<?> klass) {
		return getFields(klass, ASSOCIATIONS_FIELDS, GetFieldsListener.AbstractImpl.AssociationImpl.INSTANCE);
	}
	
	static Collection<Field> getLazyAssociationsFields(Class<?> klass) {
		return getFields(klass, LAZY_ASSOCIATIONS_FIELDS, GetFieldsListener.AbstractImpl.LazyAssociationImpl.INSTANCE);
	}
	
	static Collection<Field> getTransientFields(Class<?> klass) {
		return getFields(klass, TRANSIENT_FIELDS, GetFieldsListener.AbstractImpl.AbstractTransientImpl.Impl.INSTANCE);
	}
	/**/
	
	static Collection<Field> getFields(Class<?> klass,Map<Class<?>,Collection<Field>> map,GetFieldsListener listener) {
		if(klass == null)
			throw new IllegalArgumentException("class is required");
		if(map == null)
			throw new IllegalArgumentException("map is required");
		if(listener == null)
			throw new IllegalArgumentException("listener is required");
		if(map.containsKey(klass))
			return map.get(klass);
		Collection<Field> result = null;
		Collection<Field> fields = FieldHelper.get(klass);
		if(CollectionHelper.isNotEmpty(fields)) {
			for(Field field : fields) {
				if(Boolean.TRUE.equals(listener.accept(field))) {
					if(result == null)
						result = new ArrayList<>();
					result.add(field);	
				}
			}				
		}
		map.put(klass, result);	
		return result;
	}
	
	public static interface GetFieldsListener {
		Boolean accept(Field field);
		
		/**/
		
		public static abstract class AbstractImpl implements GetFieldsListener {
			
			/**/
			
			public abstract static class AbstractTransientImpl extends AbstractImpl {
				@Override
				public Boolean accept(Field field) {
					return field.isAnnotationPresent(Transient.class);
				}
				
				public static class Impl extends AbstractTransientImpl {				
					public static final Impl INSTANCE = new Impl();
				}
			}
			
			public abstract static class AbstractAssociationImpl extends AbstractImpl {
				@Override
				public Boolean accept(Field field) {
					return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToMany.class);
				}
			}
			
			public static class AssociationImpl extends AbstractAssociationImpl {				
				public static final AssociationImpl INSTANCE = new AssociationImpl();
			}
			
			public static class LazyAssociationImpl extends AbstractAssociationImpl {	
				@Override
				public Boolean accept(Field field) {
					Boolean isAssociation = super.accept(field);
					if(Boolean.TRUE.equals(isAssociation)) {
						FetchType fetchType = null;
						if(field.isAnnotationPresent(ManyToOne.class))
							fetchType = ((ManyToOne)field.getAnnotation(ManyToOne.class)).fetch();
						else if(field.isAnnotationPresent(OneToMany.class))
							fetchType = ((OneToMany)field.getAnnotation(OneToMany.class)).fetch();
						if(fetchType == null)
							return Boolean.FALSE;
						return FetchType.LAZY.equals(fetchType);
					}else
						return Boolean.FALSE;
					
				}
				public static final LazyAssociationImpl INSTANCE = new LazyAssociationImpl();
			}
		}
	}
	
	/**/
	
	Map<Class<?>,Collection<Field>> ASSOCIATIONS_FIELDS = new HashMap<>();
	Map<Class<?>,Collection<Field>> LAZY_ASSOCIATIONS_FIELDS = new HashMap<>();
	Map<Class<?>,Collection<Field>> TRANSIENT_FIELDS = new HashMap<>();
}
