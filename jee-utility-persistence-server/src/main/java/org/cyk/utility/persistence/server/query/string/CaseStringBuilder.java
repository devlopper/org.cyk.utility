package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.server.query.string.CaseStringBuilder.Case.When;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface CaseStringBuilder {

	String build(Case kase);
	String build(Collection<String> strings);
	String build(String...strings);
	
	String buildWhenFieldIsNullThenZeroElseField(String fieldName);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements CaseStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Case kase) {
			if(kase == null)
				throw new RuntimeException("Case is required");
			if(CollectionHelper.isEmpty(kase.getStrings()) && CollectionHelper.isEmpty(kase.whens))
				throw new RuntimeException("When is required");
			Collection<String> whens = kase.getStrings();
			if(CollectionHelper.isNotEmpty(kase.whens)) {
				for(When when : kase.whens) {
					if(CollectionHelper.isEmpty(when.getStrings()))
						continue;
					if(whens == null)
						whens = new ArrayList<>();
					whens.addAll(when.getStrings());
				}			
			}
			String string = __build__(whens);
			return string;
		}
		
		private String __build__(Collection<String> whens) {
			String when =  StringHelper.concatenate(whens, " ");
			return String.format(FORMAT, when);
		}
		
		@Override
		public String build(Collection<String> strings) {
			if(CollectionHelper.isEmpty(strings))
				return null;
			return build(new Case().add(strings));
		}
		
		@Override
		public String build(String... strings) {
			if(ArrayHelper.isEmpty(strings))
				return null;
			return build(CollectionHelper.listOf(strings));
		}
		
		@Override
		public String buildWhenFieldIsNullThenZeroElseField(String fieldName) {
			return build(Case.instantiateWhenFieldIsNullThenZeroElseField(fieldName));
		}
		
		private static final String FORMAT = "CASE %s END";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Case extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		private Collection<When> whens;
		
		@Override
		public Case add(Collection<String> strings) {
			return (Case) super.add(strings);
		}
		
		@Override
		public Case add(String... strings) {
			return (Case) super.add(strings);
		}
		
		public Collection<When> getWhens(Boolean injectIfNull) {
			if(whens == null && Boolean.TRUE.equals(injectIfNull))
				whens = new ArrayList<>();
			return whens;
		}
		
		public Case when(String condition,String result) {
			if(StringHelper.isBlank(condition))
				return this;
			getWhens(Boolean.TRUE).add(When.instantiateIfConditionThenResult(condition, result));
			return this;
		}
		
		public Case else_(String result) {
			getWhens(Boolean.TRUE).add(When.instantiateElse(result));
			return this;
		}
		
		// Is null
		
		public static Case instantiateWhenFieldIsNullThenZeroElseField(String fieldName,String zero) {
			return new Case().when(String.format("%s IS NULL",fieldName),ValueHelper.defaultToIfBlank(zero, "0")).else_(fieldName);
		}
		
		public static Case instantiateWhenFieldIsNullThenZeroElseField(String fieldName) {
			return instantiateWhenFieldIsNullThenZeroElseField(fieldName, "0");
		}
		
		public static String instantiateWhenFieldIsNullThenZeroElseFieldAndBuild(String fieldName,String zero) {
			return CaseStringBuilder.getInstance().build(instantiateWhenFieldIsNullThenZeroElseField(fieldName,zero));
		}
		
		public static String instantiateWhenFieldIsNullThenZeroElseFieldAndBuild(String fieldName) {
			return CaseStringBuilder.getInstance().build(instantiateWhenFieldIsNullThenZeroElseField(fieldName));
		}
		
		// Is less than zero
		
		public static Case instantiateWhenIsNotGreaterThanOrEqualZeroThenZero(String projection,String zero) {
			zero = ValueHelper.defaultToIfBlank(zero, "0");
			return new Case().when(String.format("%s < %s",projection,zero),projection).else_(zero);
		}
		
		public static Case instantiateWhenIsNotGreaterThanOrEqualZeroThenZero(String projection) {
			return instantiateWhenIsNotGreaterThanOrEqualZeroThenZero(projection, "0");
		}
		
		public static String instantiateWhenIsNotGreaterThanOrEqualZeroThenZeroAndBuild(String projection,String zero) {
			return CaseStringBuilder.getInstance().build(instantiateWhenIsNotGreaterThanOrEqualZeroThenZero(projection,zero));
		}
		
		public static String instantiateWhenIsNotGreaterThanOrEqualZeroThenZeroAndBuild(String projection) {
			return CaseStringBuilder.getInstance().build(instantiateWhenIsNotGreaterThanOrEqualZeroThenZero(projection));
		}
		
		// Is greater than zero
		
		public static Case instantiateWhenIsNotLessThanOrEqualZeroThenZero(String projection,String zero) {
			zero = ValueHelper.defaultToIfBlank(zero, "0");
			return new Case().when(String.format("%s > %s",projection,zero),projection).else_(zero);
		}
		
		public static Case instantiateWhenIsNotLessThanOrEqualZeroThenZero(String projection) {
			return instantiateWhenIsNotLessThanOrEqualZeroThenZero(projection, "0");
		}
		
		public static String instantiateWhenIsNotLessThanOrEqualZeroThenZeroAndBuild(String projection,String zero) {
			return CaseStringBuilder.getInstance().build(instantiateWhenIsNotLessThanOrEqualZeroThenZero(projection,zero));
		}
		
		public static String instantiateWhenIsNotLessThanOrEqualZeroThenZeroAndBuild(String projection) {
			return CaseStringBuilder.getInstance().build(instantiateWhenIsNotLessThanOrEqualZeroThenZero(projection));
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class When extends org.cyk.utility.__kernel__.string.List implements Serializable {
			
			@Override
			public When add(Collection<String> whens) {
				return (When) super.add(whens);
			}
			
			@Override
			public When add(String... whens) {
				return (When) super.add(whens);
			}
			
			public static When instantiateIfConditionThenResult(String condition,String result) {
				return new When().add(String.format(When.FORMAT, condition,result));
			}
			
			public static When instantiateElse(String result) {
				return new When().add(String.format(When.ELSE_FORMAT,result));
			}
			
			public static final String FORMAT = "WHEN %s THEN %s";
			public static final String ELSE_FORMAT = "ELSE %s";
		}
	}
	
	/**/
	
	static CaseStringBuilder getInstance() {
		return Helper.getInstance(CaseStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}