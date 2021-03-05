package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface WhereStringBuilder {

	String build(Predicate predicate);
	String build(Collection<String> predicates);
	String build(String...predicates);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements WhereStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Predicate predicate) {
			if(predicate == null)
				throw new RuntimeException("Where predicate is required");
			if(CollectionHelper.isEmpty(predicate.getStrings()))
				throw new RuntimeException("Where predicate is required");
			Collection<String> predicates = predicate.getStrings();
			String string = __build__(predicates);
			return string;
		}
		
		private String __build__(Collection<String> predicates) {
			java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(StringHelper.concatenate(predicates, " "));
			return String.format(FORMAT, stringBuilder.toString());
		}
		
		@Override
		public String build(Collection<String> predicates) {
			if(CollectionHelper.isEmpty(predicates))
				throw new RuntimeException("Where predicates are required");
			return __build__(predicates);
		}
		
		@Override
		public String build(String... predicates) {
			if(ArrayHelper.isEmpty(predicates))
				throw new RuntimeException("Where predicates are required");
			return build(CollectionHelper.listOf(predicates));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Predicate extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		@Override
		public Predicate add(Collection<String> strings) {
			return (Predicate) super.add(strings);
		}
		
		@Override
		public Predicate add(String... strings) {
			return (Predicate) super.add(strings);
		}
		
		public Predicate and() {
			return add("AND");
		}
		
		public Predicate or() {
			return add("OR");
		}
		
		public Predicate leftParenthesis() {
			return add("(");
		}
		
		public Predicate rightParenthesis() {
			return add(")");
		}
	}
	
	/**/
	
	static WhereStringBuilder getInstance() {
		return Helper.getInstance(WhereStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "WHERE %s";
}