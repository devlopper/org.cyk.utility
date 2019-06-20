package org.cyk.utility.regularexpression;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.string.Strings;

@Dependent
public class RegularExpressionInstanceImpl extends AbstractObject implements RegularExpressionInstance,Serializable {
	private static final long serialVersionUID = 1L;

	private String expression;
	private Pattern pattern;
	
	private Strings startTokens,middleTokens,endTokens;
	
	@Override
	public String getExpression() {
		return expression;
	}

	@Override
	public RegularExpressionInstance setExpression(String expression) {
		this.expression = expression;
		this.pattern = null;
		return this;
	}

	@Override
	public Boolean match(String string) {
		if(pattern == null && string!=null)
			pattern = Pattern.compile(expression);
		return pattern == null ? Boolean.FALSE : __inject__(RegularExpressionHelper.class).match(string, pattern);
	}
	
	@Override
	public Strings getStartTokens() {
		return startTokens;
	}
	
	@Override
	public RegularExpressionInstance setStartTokens(Strings startTokens) {
		this.startTokens = startTokens;
		return this;
	}
	
	@Override
	public Strings getMiddleTokens() {
		return middleTokens;
	}
	
	@Override
	public RegularExpressionInstance setMiddleTokens(Strings middleTokens) {
		this.middleTokens = middleTokens;
		return this;
	}
	
	@Override
	public Strings getMiddleTokens(Boolean injectIfNull) {
		Strings collectionInstanceString = getMiddleTokens();
		if(collectionInstanceString == null)
			setMiddleTokens(collectionInstanceString = __inject__(Strings.class));
		return collectionInstanceString;
	}
	
	@Override
	public Strings getEndTokens() {
		return endTokens;
	}
	
	@Override
	public RegularExpressionInstance setEndTokens(Strings endTokens) {
		this.endTokens = endTokens;
		return this;
	}
	
	@Override
	public Strings getEndTokens(Boolean injectIfNull) {
		Strings collectionInstanceString = getEndTokens();
		if(collectionInstanceString == null)
			setEndTokens(collectionInstanceString = __inject__(Strings.class));
		return collectionInstanceString;
	}
	
	@Override
	public String toString() {
		return getExpression();
	}
}
