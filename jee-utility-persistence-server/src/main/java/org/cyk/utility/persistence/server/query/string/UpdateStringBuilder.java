package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface UpdateStringBuilder {

	String build(Tuple tuple);	
	String build(String tuple);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements UpdateStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Tuple tuple) {
			if(tuple == null)
				throw new RuntimeException("Update tuple is required");
			if(CollectionHelper.isEmpty(tuple.getStrings()))
				throw new RuntimeException("Update tuple is required");
			Collection<String> strings = tuple.getStrings();
			String string = __build__(strings);
			return string;
		}
		
		private String __build__(Collection<String> strings) {
			java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(StringHelper.concatenate(strings, ","));
			return String.format(FORMAT, stringBuilder.toString());
		}
		
		@Override
		public String build(String tuple) {
			if(StringHelper.isBlank(tuple))
				throw new RuntimeException("Update tuple is required");
			return __build__(List.of(tuple));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Tuple extends org.cyk.utility.__kernel__.string.List implements Serializable {
		private String variableName;
		
		public Tuple(String name,String variableName) {
			this.variableName = variableName;
			add(name+" "+variableName);
		}
		
		public Tuple(String name) {
			this(name,StringHelper.getVariableNameFrom(name));
		}
		
		@Override
		public Tuple add(Collection<String> strings) {
			return (Tuple) super.add(strings);
		}
		
		@Override
		public Tuple add(String... strings) {
			return (Tuple) super.add(strings);
		}
	}
	
	/**/
	
	static UpdateStringBuilder getInstance() {
		return Helper.getInstance(UpdateStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "UPDATE %s";
}