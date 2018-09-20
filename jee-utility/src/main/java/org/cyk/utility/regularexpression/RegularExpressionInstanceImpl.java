package org.cyk.utility.regularexpression;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionInstanceString;

public class RegularExpressionInstanceImpl extends AbstractObject implements RegularExpressionInstance,Serializable {
	private static final long serialVersionUID = 1L;

	private String expression;
	private Pattern pattern;
	
	private CollectionInstanceString startTokens,middleTokens,endTokens;
	
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
	public CollectionInstanceString getStartTokens() {
		return startTokens;
	}
	
	@Override
	public RegularExpressionInstance setStartTokens(CollectionInstanceString startTokens) {
		this.startTokens = startTokens;
		return this;
	}
	
	@Override
	public CollectionInstanceString getMiddleTokens() {
		return middleTokens;
	}
	
	@Override
	public RegularExpressionInstance setMiddleTokens(CollectionInstanceString middleTokens) {
		this.middleTokens = middleTokens;
		return this;
	}
	
	@Override
	public CollectionInstanceString getMiddleTokens(Boolean injectIfNull) {
		CollectionInstanceString collectionInstanceString = getMiddleTokens();
		if(collectionInstanceString == null)
			setMiddleTokens(collectionInstanceString = __inject__(CollectionInstanceString.class));
		return collectionInstanceString;
	}
	
	@Override
	public CollectionInstanceString getEndTokens() {
		return endTokens;
	}
	
	@Override
	public RegularExpressionInstance setEndTokens(CollectionInstanceString endTokens) {
		this.endTokens = endTokens;
		return this;
	}
	
	@Override
	public CollectionInstanceString getEndTokens(Boolean injectIfNull) {
		CollectionInstanceString collectionInstanceString = getEndTokens();
		if(collectionInstanceString == null)
			setEndTokens(collectionInstanceString = __inject__(CollectionInstanceString.class));
		return collectionInstanceString;
	}
	
	@Override
	public String toString() {
		return getExpression();
	}
}
