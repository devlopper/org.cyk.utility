package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SelectStringBuilder {

	String build(Projection projection);	
	String build(Collection<String> projections);
	String build(String...projections);
	
	String buildFromTuple(String variableName,Collection<String> fieldsNames);
	String buildFromTuple(String variableName,String...fieldsNames);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements SelectStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Projection projection) {
			if(projection == null)
				throw new RuntimeException("Select projection is required");
			if(CollectionHelper.isEmpty(projection.getStrings()))
				throw new RuntimeException("Select projection is required");
			Collection<String> projections = projection.getStrings();
			String string = __build__(projections);
			return string;
		}
		
		private String __build__(Collection<String> projections) {
			String string =  StringHelper.concatenate(projections, ",");
			return String.format(FORMAT, string);
		}
		
		@Override
		public String build(Collection<String> projections) {
			if(CollectionHelper.isEmpty(projections))
				throw new RuntimeException("Select projections are required");
			return __build__(projections);
		}
		
		@Override
		public String build(String... projections) {
			if(ArrayHelper.isEmpty(projections))
				throw new RuntimeException("Select projections are required");
			return build(CollectionHelper.listOf(projections));
		}
		
		public String buildFromTuple(String variableName,Collection<String> fieldsNames) {
			if(CollectionHelper.isEmpty(fieldsNames))
				throw new RuntimeException("Select fields names are required");
			return build(new Projection().addFromTuple(variableName, fieldsNames));
		}
		
		@Override
		public String buildFromTuple(String variableName, String... fieldsNames) {
			if(ArrayHelper.isEmpty(fieldsNames))
				throw new RuntimeException("Select fields names are required");
			return build(new Projection().addFromTuple(variableName, fieldsNames));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Projection extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		@Override
		public Projection add(Collection<String> strings) {
			return (Projection) super.add(strings);
		}
		
		@Override
		public Projection add(String... strings) {
			return (Projection) super.add(strings);
		}
		
		public Projection addFromTuple(String variableName,Collection<String> fieldsNames) {
			if(StringHelper.isBlank(variableName) || CollectionHelper.isEmpty(fieldsNames))
				return this;
			for(String fieldName : fieldsNames)
				add(variableName+"."+fieldName);
			return this;
		}
		
		public Projection addFromTuple(String variableName,String...fieldsNames) {
			if(StringHelper.isBlank(variableName) || ArrayHelper.isEmpty(fieldsNames))
				return this;
			return addFromTuple(variableName, CollectionHelper.listOf(fieldsNames));
		}
		
		public Projection addFromQueryProjections(String variableName,Collection<org.cyk.utility.persistence.query.Projection> projections) {
			if(CollectionHelper.isEmpty(projections))
				return this;
			addFromTuple(variableName,projections.stream().map(x -> x.getFieldName()).collect(Collectors.toList()));
			return this;
		}
		
		public Projection addFromQueryProjections(String variableName,org.cyk.utility.persistence.query.Projection...projections) {
			if(ArrayHelper.isEmpty(projections))
				return this;
			addFromQueryProjections(variableName,CollectionHelper.listOf(projections));
			return this;
		}
	}
	
	/**/
	
	static SelectStringBuilder getInstance() {
		return Helper.getInstance(SelectStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "SELECT %s";
}