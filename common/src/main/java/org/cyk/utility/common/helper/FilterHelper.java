package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CriteriaHelper.Criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class FilterHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static FilterHelper INSTANCE;
	
	public static FilterHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FilterHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Filter<T> extends AbstractBean implements Serializable{
		private static final long serialVersionUID = 1L;
		
		protected List<Object> masters;
		
		protected List<Criteria<?>> criterias = new ArrayList<Criteria<?>>();
		protected Collection<T> excluded;
		
		public Filter(Filter<T> filter){
			criterias.addAll(filter.criterias);
			excluded = CollectionHelper.getInstance().add(filter.excluded, Boolean.FALSE, excluded);
		}
		
		public Filter<T> addMasters(Collection<? extends Object> masters){
			if(CollectionHelper.getInstance().isNotEmpty(masters)){
				if(this.masters==null)
					this.masters = new ArrayList<>();
				for(Object index : masters)
					if(index!=null)
						this.masters.add(index);	
			}
			return this;
		}
		
		public Filter<T> addMaster(Object master){
			if(master!=null)
				addMasters(Arrays.asList(master));
			return this;
		}
		
		public Filter<T> addMaster(Class<?> aClass,Object identifier,ClassHelper.Listener.IdentifierType identifierType){
			return addMaster(InstanceHelper.getInstance().getByIdentifier(aClass, identifier, identifierType));
		}
		
		public Filter<T> addMaster(Class<?> aClass,Object identifier){
			return addMaster(aClass, identifier, ClassHelper.Listener.IdentifierType.BUSINESS);
		}
		
		@SuppressWarnings("unchecked")
		public <E> Collection<E> filterMasters(Class<E> aClass){
			return (Collection<E>) CollectionHelper.getInstance().filter(masters, aClass);
		}
		
		public Collection<T> getExcluded(){
			if(excluded==null)
				excluded = new ArrayList<>();
			return excluded;
		}
		
		public Filter<T> addExcluded(@SuppressWarnings("unchecked") T...elements){
			excluded = CollectionHelper.getInstance().add(getExcluded(), elements);
			return this;
		}
		
		public Filter<T> addCriterias(Criteria<?>...criterias){
			this.criterias = (List<Criteria<?>>) CollectionHelper.getInstance().add(List.class,this.criterias, criterias);
			return this;
		}
		
		public Filter<T> addCriterias(Collection<Criteria<?>> criterias){
			this.criterias = (List<Criteria<?>>) CollectionHelper.getInstance().add(List.class,this.criterias,Boolean.TRUE, criterias);
			return this;
		}
		
		public Filter<T> addCriterias(Filter<?> filter){
			addCriterias(filter.criterias);
			return this;
		}
		
		public java.lang.Boolean isNull(){
			if(CollectionHelper.getInstance().isNotEmpty(masters))
				return Boolean.FALSE;
			for(Criteria<?> criteria : criterias)
				if(!java.lang.Boolean.TRUE.equals(criteria.isNull()))
					return java.lang.Boolean.FALSE;
			return java.lang.Boolean.TRUE;
		}
		
		public Filter<T> set(java.lang.String string){
			for(Criteria<?> criteria : criterias)
				criteria.set(string);
			return this;
		}
		
		public <E extends CriteriaHelper.Criteria<?>> E instanciateCriteria(Class<E> aClass){
			E criteria = ClassHelper.getInstance().instanciateOne(aClass);
			addCriterias(criteria);
			return criteria;
		}
		
		@Override
		public String toString() {
			return criterias+" , "+excluded;
		}
		/*
		private static ClassLocator CLASS_LOCATOR;
		public static ClassLocator getClassLocator(){
			if(CLASS_LOCATOR == null)
				CLASS_LOCATOR = ClassHelper.getInstance().instanciateOne(ClassLocator.class);
			return CLASS_LOCATOR;
		}
		
		public static void map(Class<?> aClass){
			ClassHelper.getInstance().map(ClassLocator.class, aClass);
			CLASS_LOCATOR = null;
		}*/
		
		/**/
		
		public static class ClassLocator extends org.cyk.utility.common.helper.ClassHelper.Locator implements Serializable {
			private static final long serialVersionUID = -1L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
				setClassType("Filter");
				nameBuilder = ClassHelper.getInstance().instanciateOne(ClassHelper.NameBuilder.class);
				nameBuilder.getPropertiesMap().setSuffixSet(new LinkedHashSet<String>(Arrays.asList("Filter","$Filter")));
			}
			
			@Override
			protected void initialisation() {
				INSTANCE = this;
				super.initialisation();
			}
			
			@Override
			protected Boolean isLocatable(Class<?> aClass) {
				return Boolean.TRUE.equals(ClassHelper.getInstance().isModel(aClass)) && Boolean.TRUE.equals(ClassHelper.getInstance().isPersisted(aClass));
			}
				
			public static ClassLocator getInstance() {
				if(INSTANCE == null)
					INSTANCE = ClassHelper.getInstance().instanciateOne(ClassLocator.class);
				return INSTANCE;
			}
			
			public static void map(Class<?> aClass){
				ClassHelper.getInstance().map(ClassLocator.class, aClass);
				INSTANCE = null;
			}
		}

	}
	
}
