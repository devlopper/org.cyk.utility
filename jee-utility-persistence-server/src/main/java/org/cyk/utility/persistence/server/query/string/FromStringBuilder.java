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

public interface FromStringBuilder {

	String build(Tuple tuple);	
	String build(Collection<String> tuples);
	String build(String...tuples);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements FromStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Tuple tuple) {
			if(tuple == null)
				throw new RuntimeException("From tuple is required");
			if(CollectionHelper.isEmpty(tuple.getStrings()))
				throw new RuntimeException("From tuple is required");
			Collection<String> tuples = tuple.getStrings();
			Collection<String> joins = tuple.join == null ? null : tuple.join.getStrings();
			String string = __build__(tuples,joins);
			return string;
		}
		
		private String __build__(Collection<String> tuples,Collection<String> joins) {
			java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(StringHelper.concatenate(tuples, ","));
			if(CollectionHelper.isNotEmpty(joins)) {
				stringBuilder.append(" ");
				stringBuilder.append(StringHelper.concatenate(joins, " "));
			}
			return String.format(FORMAT, stringBuilder.toString());
		}
		
		@Override
		public String build(Collection<String> tuples) {
			if(CollectionHelper.isEmpty(tuples))
				throw new RuntimeException("From tuples are required");
			return __build__(tuples,null);
		}
		
		@Override
		public String build(String... tuples) {
			if(ArrayHelper.isEmpty(tuples))
				throw new RuntimeException("From tuples are required");
			return build(CollectionHelper.listOf(tuples));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Tuple extends org.cyk.utility.__kernel__.string.List implements Serializable {
		private Join join;
		
		public Join getJoin(Boolean injectIfNull) {
			if(join == null && Boolean.TRUE.equals(injectIfNull))
				join = new Join();
			return join;
		}
		
		@Override
		public Tuple add(Collection<String> strings) {
			return (Tuple) super.add(strings);
		}
		
		@Override
		public Tuple add(String... strings) {
			return (Tuple) super.add(strings);
		}
		
		public Tuple addJoins(Collection<String> strings) {
			getJoin(Boolean.TRUE).add(strings);
			return this;
		}
		
		public Tuple addJoins(String... strings) {
			getJoin(Boolean.TRUE).add(strings);
			return this;
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Join extends org.cyk.utility.__kernel__.string.List implements Serializable {
			
			@Override
			public Join add(Collection<String> strings) {
				return (Join) super.add(strings);
			}
			
			@Override
			public Join add(String... strings) {
				return (Join) super.add(strings);
			}
		}
	}
	
	/**/
	
	static FromStringBuilder getInstance() {
		return Helper.getInstance(FromStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "FROM %s";
}