package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.persistence.server.query.Clause;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Replacer {

	String replace(Arguments arguments);	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements Replacer,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String replace(Arguments arguments) {
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			String queryValue = arguments.queryValue;
			String queryIdentifier = null;
			if(StringHelper.isBlank(queryValue) && arguments.query != null) {
				queryValue = arguments.query.getValue();
				if(StringHelper.isBlank(queryValue))
					queryIdentifier = arguments.query.getIdentifier();
			}
			if(StringHelper.isBlank(queryValue)) {
				if(StringHelper.isNotBlank(arguments.queryIdentifier))
					queryIdentifier = arguments.queryIdentifier;
				if(StringHelper.isNotBlank(queryIdentifier)) {
					Query query = QueryGetter.getInstance().get(queryIdentifier);
					if(query != null)
						queryValue = query.getValue();
				}
			}
			if(StringHelper.isBlank(queryValue))
				throw new RuntimeException("query value for replacement is required");
			if(StringHelper.isBlank(arguments.replacement))
				throw new RuntimeException("query replacement is required");
			String token = arguments.token;
			if(StringHelper.isBlank(token)) {
				if(arguments.clause == null)
					throw new RuntimeException("clause is required");
				if(Clause.ORDER_BY.equals(arguments.clause)) {
					token = StringUtils.substringAfter(queryValue, (" "+Clause.ORDER_BY.getValue()+" "));
				}
			}			
			return replace(queryValue, token, arguments.replacement);
		}
		
		protected String replace(String queryValue,String token,String replacement) {
			return StringUtils.replace(queryValue, token, replacement);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String queryValue;
		private String token;
		private String replacement;
		
		private Query query;
		private String queryIdentifier;
		private Clause clause;
	}
	
	/**/
	
	static Replacer getInstance() {
		return Helper.getInstance(Replacer.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}