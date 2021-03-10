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
import lombok.NoArgsConstructor;
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
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Tuple extends org.cyk.utility.__kernel__.string.List implements Serializable {
		private String variableName;
		private Join join;
		
		public Tuple(String name,String variableName) {
			this.variableName = variableName;
			add(name+" "+variableName);
		}
		
		public Tuple(String name) {
			this(name,StringHelper.getVariableNameFrom(name));
		}
		
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
		
		public Tuple leftJoinTuple(String tupleName) {
			getJoin(Boolean.TRUE).addFromTupleLeft(tupleName, variableName);
			return this;
		}
		
		public Tuple leftJoinTuple(String tupleName,String variableName) {
			getJoin(Boolean.TRUE).addFromTupleLeft(tupleName, variableName);
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
			
			public Join addFromTuple(String tupleName,String variableName,String fieldName,JoinType type) {
				if(StringHelper.isBlank(fieldName))
					fieldName = StringHelper.getVariableNameFrom(tupleName);
				if(type == null)
					type = JoinType.LEFT;
				return add(String.format(FORMAT, type,tupleName,fieldName,variableName));
			}
			
			public Join addFromTupleLeft(String tupleName,String variableName,String fieldName) {
				return addFromTuple(tupleName, variableName, fieldName, JoinType.LEFT);
			}
			
			public Join addFromTupleLeft(String tupleName,String variableName) {
				return addFromTupleLeft(tupleName, variableName, null);
			}

			private static final String FORMAT = "%1$s JOIN %2$s %3$s ON %3$s = %4$s.%3$s";
		}
	}
	
	/**/
	
	static FromStringBuilder getInstance() {
		return Helper.getInstance(FromStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "FROM %s";
}