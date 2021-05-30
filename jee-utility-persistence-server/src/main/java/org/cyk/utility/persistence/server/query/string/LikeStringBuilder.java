package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Language.Where.Like;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface LikeStringBuilder {

	String build(Arguments arguments);	
	String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator,Boolean isCaseSensitive);
	String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator);
	String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters);
	String build(String tupleName,String fieldName,String parameterName,Integer numberOfAdditionalParameters);
	String build(String tupleName,String fieldName,String parameterName);
	
	public static abstract class AbstractImpl extends AbstractObject implements LikeStringBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("like string builder arguments", arguments);
			if(StringHelper.isBlank(arguments.tupleName) || StringHelper.isBlank(arguments.fieldName) || StringHelper.isBlank(arguments.parameterName))
				throw new RuntimeException(String.format("Illegal parameters. tuple name %s , field name : %s , parameter name : %s."
						,arguments.tupleName,arguments.fieldName,arguments.parameterName));
			String like = format(arguments.tupleName, arguments.fieldName, arguments.parameterName, arguments.isCaseSensitive);
			if(arguments.numberOfAdditionalParameters == null || arguments.numberOfAdditionalParameters <= 0)
				return like;
			if(arguments.operator == null)
				throw new RuntimeException("Operator is required.");
			if(arguments.numberOfAdditionalParameters > 1 && arguments.additionalParametersOperator == null)
				throw new RuntimeException("Operator of additional parameters is required.");	
			Collection<String> additionalParametersNames = new ArrayList<String>();			
			for(Integer index = 0; index < arguments.numberOfAdditionalParameters; index++)
				additionalParametersNames.add(arguments.parameterName+index);
			Collection<String> likes = new ArrayList<String>();
			additionalParametersNames.forEach(parameterName -> {
				likes.add(format(arguments.tupleName, arguments.fieldName, parameterName, arguments.isCaseSensitive));
			});
			String additionalParametersLike;
			if(likes.size() == 1)
				additionalParametersLike = likes.iterator().next();
			else
				additionalParametersLike = "("+StringHelper.concatenate(likes, " "+arguments.additionalParametersOperator.name()+" ")+")";				
			return "("+like+" "+arguments.operator.name()+" "+additionalParametersLike+")";
		}
		
		@Override
		public String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator,Boolean isCaseSensitive) {
			return new Like().setTupleName(tupleName).setFieldName(fieldName).setParameterName(parameterName).setNumberOfAdditionalParameters(numberOfAdditionalParameters)
					.setOperator(operator).setIsCaseSensitive(isCaseSensitive).setAdditionalParametersOperator(additionalParametersOperator).generate();
		}
		
		@Override
		public String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator) {
			return build(tupleName, fieldName, parameterName, operator, numberOfAdditionalParameters, additionalParametersOperator, null);
		}
		
		@Override
		public String build(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters) {
			return build(tupleName, fieldName, parameterName, operator, numberOfAdditionalParameters, null);
		}
		
		@Override
		public String build(String tupleName,String fieldName,String parameterName,Integer numberOfAdditionalParameters) {
			return build(tupleName, fieldName, parameterName, LogicalOperator.OR, numberOfAdditionalParameters, LogicalOperator.AND, null);
		}
		
		@Override
		public String build(String tupleName,String fieldName,String parameterName) {
			return build(tupleName, fieldName, parameterName, null, null, null);
		}
		
		private static String format(String tupleName,String fieldName,String parameterName,Boolean isCaseSensitive) {
			return String.format(Boolean.TRUE.equals(isCaseSensitive) ? FORMAT_CASE_SENSITIVE : FORMAT,tupleName, fieldName, parameterName);
		}
		
		private static final String FORMAT = "LOWER(%s.%s) LIKE LOWER(:%s)";
		private static final String FORMAT_CASE_SENSITIVE = "%s.%s LIKE :%s";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private String tupleName;
		private String fieldName;
		private String parameterName;
		private LogicalOperator operator;
		private Boolean isCaseSensitive;
		private Integer numberOfAdditionalParameters;
		private LogicalOperator additionalParametersOperator;
	}
	
	/**/
	
	static LikeStringBuilder getInstance() {
		return Helper.getInstance(LikeStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}