package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
		
		protected List<Criteria<?>> criterias = new ArrayList<Criteria<?>>();
		protected Collection<T> excluded;
		
		public Filter(Filter<T> filter){
			criterias.addAll(filter.criterias);
			excluded = CollectionHelper.getInstance().add(filter.excluded, Boolean.FALSE, excluded);
		}
		
		public Collection<T> getExcluded(){
			if(excluded==null)
				excluded = new ArrayList<>();
			return excluded;
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
		
	}
	
}
