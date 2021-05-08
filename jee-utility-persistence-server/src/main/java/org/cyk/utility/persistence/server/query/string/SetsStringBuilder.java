package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SetsStringBuilder {

	String build(Arguments arguments);
	String build(Collection<SetStringBuilder.Arguments> setArgumentsCollection);
	String build(SetStringBuilder.Arguments...setArgumentsCollection);
	
	public static abstract class AbstractImpl extends AbstractObject implements SetsStringBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("sets string builder arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("set string builder collection", arguments.setArgumentsCollection);
			return String.format(FORMAT, arguments.setArgumentsCollection.stream().map(x -> SetStringBuilder.getInstance().build(x)).collect(Collectors.joining(",")));
		}
		
		@Override
		public String build(Collection<SetStringBuilder.Arguments> setArgumentsCollection) {
			return build(new Arguments().addSetArgumentsCollection(setArgumentsCollection));
		}
		
		@Override
		public String build(SetStringBuilder.Arguments... setArgumentsCollection) {
			return build(CollectionHelper.listOf(setArgumentsCollection));
		}
		
		private static final String FORMAT = "SET %s";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private Collection<SetStringBuilder.Arguments> setArgumentsCollection;
		
		public Collection<SetStringBuilder.Arguments> getSetArgumentsCollection(Boolean injectIfNull) {
			if(setArgumentsCollection == null && Boolean.TRUE.equals(injectIfNull))
				setArgumentsCollection = new ArrayList<>();
			return setArgumentsCollection;
		}
		
		public Arguments addSetArgumentsCollection(Collection<SetStringBuilder.Arguments> setArgumentsCollection) {
			if(setArgumentsCollection == null)
				return this;
			getSetArgumentsCollection(Boolean.TRUE).addAll(setArgumentsCollection);
			return this;
		}
		
		public Arguments addSetArgumentsCollection(SetStringBuilder.Arguments...setArgumentsArray) {
			if(ArrayHelper.isEmpty(setArgumentsArray))
				return this;
			return addSetArgumentsCollection(CollectionHelper.listOf(setArgumentsArray));
		}
	}
	
	/**/
	
	static SetsStringBuilder getInstance() {
		return Helper.getInstance(SetsStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}