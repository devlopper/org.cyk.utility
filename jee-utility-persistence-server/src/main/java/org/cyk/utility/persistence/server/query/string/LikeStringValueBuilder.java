package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface LikeStringValueBuilder {

	String build(Arguments arguments);
	String build(String string,Boolean startsWithAny,Boolean endsWithAny);
	
	public static abstract class AbstractImpl extends AbstractObject implements LikeStringValueBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			if(arguments == null)
				arguments = new Arguments();
			Collection<String> strings = new ArrayList<>();
			if(ValueHelper.defaultToIfNull(arguments.startsWithAny, Boolean.TRUE))
				strings.add(ANY);
			strings.add(ValueHelper.defaultToIfNull(arguments.string, ConstantEmpty.STRING));
			if(ValueHelper.defaultToIfNull(arguments.endsWithAny, Boolean.TRUE))
				strings.add(ANY);
			return StringHelper.concatenate(strings,ConstantEmpty.STRING);
		}
		
		@Override
		public String build(String string, Boolean startsWithAny, Boolean endsWithAny) {
			return build(new Arguments().setEndsWithAny(endsWithAny).setStartsWithAny(startsWithAny).setString(string));
		}
		
		private static final String ANY = "%";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private String string;
		private Boolean startsWithAny;
		private Boolean endsWithAny;
	}
	
	/**/
	
	static LikeStringValueBuilder getInstance() {
		return Helper.getInstance(LikeStringValueBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}