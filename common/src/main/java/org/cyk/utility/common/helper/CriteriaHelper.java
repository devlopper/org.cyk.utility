package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class CriteriaHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static CriteriaHelper INSTANCE;
	
	public static CriteriaHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CriteriaHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
	public static class Criteria<VALUE_TYPE> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 2055293289197179106L;

		protected java.lang.Boolean isMulptipleValues = java.lang.Boolean.FALSE;
		protected Collection<VALUE_TYPE> values;
		protected VALUE_TYPE value,nullValue;
		protected java.lang.Boolean ascendingOrdered=java.lang.Boolean.FALSE;
		protected Collection<VALUE_TYPE> excluded;
		protected Collection<VALUE_TYPE> required;
		
		public Criteria(VALUE_TYPE value) {
			super();
			this.value = value;
		}
		
		public Criteria(Criteria<VALUE_TYPE> criteria) {
			super();
			set(criteria);
		}
		
		public void set(Criteria<VALUE_TYPE> criteria){
			this.value = criteria.value;
			this.nullValue = criteria.nullValue;
			this.ascendingOrdered = criteria.ascendingOrdered;
			if(criteria.excluded!=null)
				this.excluded = new ArrayList<>(criteria.excluded);
			if(criteria.required!=null)
				this.required = new ArrayList<>(criteria.required);
			if(criteria.values!=null)
				this.values = new ArrayList<>(criteria.values);
		}
		
		public Collection<VALUE_TYPE> getExcluded(){
			if(excluded == null)
				excluded = new ArrayList<>();
			return excluded;
		}
		
		public Criteria<VALUE_TYPE> addExcluded(@SuppressWarnings("unchecked") VALUE_TYPE...elements){
			excluded = CollectionHelper.getInstance().add(getExcluded(), elements);
			return this;
		}
		
		public Collection<VALUE_TYPE> getRequired(){
			if(required == null)
				required = new ArrayList<>();
			return required;
		}
		
		public Criteria<VALUE_TYPE> addRequired(Collection<VALUE_TYPE> elements){
			if(CollectionHelper.getInstance().isNotEmpty(elements)){
				getRequired().addAll(elements);
			}
			return this;
		}
		
		public Criteria<VALUE_TYPE> addRequired(@SuppressWarnings("unchecked") VALUE_TYPE...elements){
			if(ArrayHelper.getInstance().isNotEmpty(elements))
				addRequired(Arrays.asList(elements));
			return this;
		}
		
		public Collection<VALUE_TYPE> getValues(){
			if(values == null)
				values = new ArrayList<>();
			return values;
		}
		
		public Criteria<VALUE_TYPE> addValues(Collection<VALUE_TYPE> values){
			if(CollectionHelper.getInstance().isNotEmpty(values)){
				getValues().addAll(values);
			}
			return this;
		}
		
		public Criteria<VALUE_TYPE> addValues(@SuppressWarnings("unchecked") VALUE_TYPE...values){
			if(ArrayHelper.getInstance().isNotEmpty(values))
				addValues(Arrays.asList(values));
			return this;
		}
		
		public Criteria<VALUE_TYPE> setValues(Collection<VALUE_TYPE> values){
			if(CollectionHelper.getInstance().isNotEmpty(values)){
				getValues().clear();
				addValues(values);
			}
			return this;
		}
		
		public Criteria<VALUE_TYPE> setValues(@SuppressWarnings("unchecked") VALUE_TYPE...values){
			if(ArrayHelper.getInstance().isNotEmpty(values))
				setValues(Arrays.asList(values));
			return this;
		}
		
		public VALUE_TYPE getPreparedValue(){
			return value==null?nullValue:value;
		}
		
		public java.lang.Boolean isNull(){
			return value == null;
		}
		
		protected VALUE_TYPE get(java.lang.String string){
			ThrowableHelper.getInstance().throwNotYetImplemented();
			return null;
		}
		
		public Criteria<VALUE_TYPE> set(java.lang.String string){
			setValue(get(string));
			return this;
		}
		
		@Override
		public java.lang.String toString() {
			Object o = getPreparedValue();
			if(o==null)
				return Constant.EMPTY_STRING;
			else
				return o.toString();
		}

		/**/
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
		public static class String extends Criteria<java.lang.String> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private static final java.lang.String MATCH_ZERO_OR_MANY_CHARACTERS = Constant.CHARACTER_PERCENT.toString(); 
			//private static final String MATCH_ONE_AND_ONLY_ONE_CHARACTER = Constant.CHARACTER_UNDESCORE.toString(); 
			
			private StringHelper.Location location = StringHelper.Location.EXAT;
			
			{
				nullValue = Constant.EMPTY_STRING;
			}
			
			public String(java.lang.String value,StringHelper.Location location) {
				super(value);
				this.location = location;
			}
			
			public String(java.lang.String value) {
				this(value,StringHelper.Location.INSIDE);
			}
			
			public String(String criteria) {
				super(criteria);
				this.location = criteria.location;
			}
			
			@Override
			public void set(Criteria<java.lang.String> criteria) {
				super.set(criteria);
				this.location = ((String)criteria).location;
			}
			
			@Override
			public java.lang.Boolean isNull() {
				return StringHelper.getInstance().isBlank(value);
			}
			
			@Override
			public java.lang.String getPreparedValue() {
				return value==null?nullValue:value;
			}
			
			public java.lang.String getLikeValue(){
				switch(location){
				case START:return MATCH_ZERO_OR_MANY_CHARACTERS+getPreparedValue();
				case INSIDE:return MATCH_ZERO_OR_MANY_CHARACTERS+getPreparedValue()+MATCH_ZERO_OR_MANY_CHARACTERS;
				case END:return getPreparedValue()+MATCH_ZERO_OR_MANY_CHARACTERS;
				case EXAT:return getPreparedValue();
				}
				return getPreparedValue();
			}
			
			@Override
			protected java.lang.String get(java.lang.String string) {
				return string;
			}
			
			/*public void excludeCode(Collection<? extends AbstractIdentifiable> excludedIdentifiables){
				if(excludedIdentifiables!=null)
					for(AbstractIdentifiable excluded : excludedIdentifiables)
						getExcluded().add(excluded.getCode());
			}*/
			
		}
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
		public static class Number<NUMBER extends java.lang.Number> extends Criteria<NUMBER> implements Serializable {
			private static final long serialVersionUID = -1648133246443265214L;

			protected NUMBER lowest;
			protected NUMBER highest;
			
			public Number(NUMBER value) {
				super(value);
			}
			
			public Number(Number<NUMBER> criteria) {
				super(criteria);
				lowest = criteria.lowest;
				highest = criteria.highest;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			protected NUMBER get(java.lang.String string) {
				if(NumberHelper.getInstance().isNotNumber(string))
					return null;
				return (NUMBER) NumberHelper.getInstance().get(ClassHelper.getInstance().getParameterAt(getClass(), 0, java.lang.Number.class), string,null);
			}
			
			/**/
			
			@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
			public static class BigDecimal extends Number<java.math.BigDecimal> implements Serializable {
				private static final long serialVersionUID = -1648133246443265214L;
				
				public BigDecimal(java.math.BigDecimal value) {
					super(value);
				}
				
				public BigDecimal(BigDecimal criteria) {
					super(criteria);
				}

			}
			
			@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
			public static class Byte extends Number<java.lang.Byte> implements Serializable {
				private static final long serialVersionUID = -1648133246443265214L;
				
				public Byte(java.lang.Byte value) {
					super(value);
				}
				
				public Byte(Byte criteria) {
					super(criteria);
				}

			}
			
			@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
			public static class Short extends Number<java.lang.Short> implements Serializable {
				private static final long serialVersionUID = -1648133246443265214L;
				
				public Short(java.lang.Short value) {
					super(value);
				}
				
				public Short(Short criteria) {
					super(criteria);
				}

			}
			
			@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
			public static class Long extends Number<java.lang.Long> implements Serializable {
				private static final long serialVersionUID = -1648133246443265214L;
				
				public Long(java.lang.Long value) {
					super(value);
				}
				
				public Long(Long criteria) {
					super(criteria);
				}
				
			}
			
			@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
			public static class Integer extends Number<java.lang.Integer> implements Serializable {
				private static final long serialVersionUID = -1648133246443265214L;
				
				public Integer(java.lang.Integer value) {
					super(value);
				}
				
				public Integer(Integer criteria) {
					super(criteria);
				}

			}


		}

		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
		public static class Boolean extends Criteria<java.lang.Boolean> implements Serializable {
			private static final long serialVersionUID = -1648133246443265214L;
			
			{
				setIsMulptipleValues(java.lang.Boolean.TRUE);
			}
			
			public Boolean(java.lang.Boolean value) {
				super(value);
			}
			
			public Boolean(Boolean criteria) {
				super(criteria);
			}
			
			@Override
			protected java.lang.Boolean get(java.lang.String string) {
				return new BooleanHelper.Builder.String.Adapter.Default(string).execute();
			}

		}
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor 
		public static class Date extends Criteria<java.util.Date> implements Serializable {
			private static final long serialVersionUID = -1648133246443265214L;
			
			public static java.util.Date DATE_MOST_PAST;
			public static java.util.Date DATE_MOST_FUTURE;
			
			static {
				try {
					DATE_MOST_PAST = DateUtils.parseDate("01/01/1800", "dd/MM/yyyy");
					DATE_MOST_FUTURE = DateUtils.parseDate("01/01/9000", "dd/MM/yyyy");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			{
				//nullValue = java.lang.Boolean.FALSE;
			}
			
			public Date(java.util.Date value) {
				super(value);
			}
			
			public Date(Date criteria) {
				super(criteria);
			}

		}
		/**/
		
	}
	
}
