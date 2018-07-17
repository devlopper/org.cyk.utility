package org.cyk.utility.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.computation.LogicalOperator;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl extends AbstractFunctionWithPropertiesAsInputImpl<String>
		implements FunctionWithPropertiesAsInputAndStringAsOutput, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		Collection<Object> children = getChildren();
		if(__inject__(CollectionHelper.class).isNotEmpty(children)){
			if(Boolean.TRUE.equals(getIsSurroundedWithParentheses())){
				__inject__(CollectionHelper.class).addElementAt(children, 0, CharacterConstant.LEFT_PARENTHESIS);
				rp();
			}
			Collection<String> strings = new ArrayList<>();
			for(Object index : children){
				String string = null;
				if(index == null){
					
				}else
					if(index instanceof FunctionWithPropertiesAsInputAndStringAsOutput)
						string = ((FunctionWithPropertiesAsInputAndStringAsOutput)index).execute().getOutput();
					else
						string = index.toString();
				if(__inject__(StringHelper.class).isNotBlank(string)){
					strings.add(string +( __executeIsAppendSpaceToChildString__(index, string) ? CharacterConstant.SPACE : StringConstant.EMPTY) );
				}
			}
			String string = __inject__(StringHelper.class).concatenate(strings);
			if(StringUtils.endsWith(string, CharacterConstant.SPACE.toString())){
				string = string.substring(0, string.length()-1);
			}
			return string;
		}else {
			Boolean isFormatRequired = __getIsFormatRequired__(Boolean.TRUE.equals(getProperties().getFromPath(Properties.IS,Properties.FORMAT,Properties.REQUIRED)));
			String format = __getFormat__(getFormat());
			if(__inject__(StringHelper.class).isBlank(format) && isFormatRequired)
				__inject__(ThrowableHelper.class).throwRuntimeException(getClass().getName()+" : format is required");
			Collection<Object> formatArguments = isFormatRequired ? __getFormatArguments__(isFormatRequired,getFormatArguments()) : null;
			if(__inject__(CollectionHelper.class).isEmpty(formatArguments) && isFormatRequired)
				__inject__(ThrowableHelper.class).throwRuntimeException(getClass().getName()+" : format arguments are required");
			if(__inject__(StringHelper.class).isNotBlank(format) && __inject__(CollectionHelper.class).isNotEmpty(formatArguments))
				return __execute__(format,formatArguments);	
		}
		return super.__execute__();
	}
	
	protected String __execute__(String format,Collection<Object> formatArguments) throws Exception {
		return String.format(format, __executeConvertFormatArguments__(formatArguments));
	}
	
	protected String __getFormat__(String format){
		return format;
	}
	
	protected Collection<Object> __getFormatArguments__(Boolean isFormatRequired,Collection<Object> formatArguments){
		return formatArguments;
	}
	
	protected Object[] __executeConvertFormatArguments__(Collection<Object> formatArguments){
		Collection<Object> collection = new ArrayList<>();
		for(Object index : formatArguments){
			Object object;
			if(index instanceof FunctionWithPropertiesAsInputAndStringAsOutput)
				object = ((FunctionWithPropertiesAsInputAndStringAsOutput)index).execute().getOutput();
			else
				object = index;
			//if(object != null)
				collection.add(object);
		}
		return collection.toArray();
	}
	
	protected Boolean __getIsFormatRequired__(Boolean value){
		return value;
	}
	
	protected Boolean __executeIsAppendSpaceToChildString__(Object child,String string){
		return Boolean.TRUE;
	}
	
	@Override
	public String getFormat() {
		return (String) getProperties().getFromPath(Properties.FORMAT,Properties.__THIS__);
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput setFormat(String format) {
		getProperties().setFromPath(new Object[]{Properties.FORMAT,Properties.__THIS__}, format);
		return this;
	}
	
	@Override
	public Collection<Object> getFormatArguments() {
		return (Collection<Object>) getProperties().getFromPath(Properties.FORMAT,Properties.ARGUMENTS);
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput setFormatArguments(Collection<Object> formatArguments) {
		getProperties().setFromPath(new Object[]{Properties.FORMAT,Properties.ARGUMENTS}, formatArguments);
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(Collection<Object> formatArguments) {
		if(__inject__(CollectionHelper.class).isNotEmpty(formatArguments)){
			Collection<Object> collection = getFormatArguments();
			if(collection == null)
				setFormatArguments(collection = new ArrayList<Object>());
			collection.addAll(formatArguments);
		}
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput addFormatArgumentObjects(Object... formatArguments) {
		addFormatArguments(__inject__(CollectionHelper.class).instanciate(formatArguments));
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput getParent() {
		return (FunctionWithPropertiesAsInputAndStringAsOutput) super.getParent();
	}

	
	@Override
	public Boolean getIsSurroundedWithParentheses() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.PARENTHESIS);
	}
	

	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput setIsSurroundedWithParentheses(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.PARENTHESIS}, value);
		return this;
	}
	

	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput surroundedWithParentheses() {
		setIsSurroundedWithParentheses(Boolean.TRUE);
		return this;
	}
	

	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput lp() {
		addChild(CharacterConstant.LEFT_PARENTHESIS);
		return this;
	}
	

	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput and() {
		addChild(LogicalOperator.AND.name());
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput or() {
		addChild(LogicalOperator.OR.name());
		return this;
	}
	

	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput rp() {
		addChild(CharacterConstant.RIGHT_PARENTHESIS);
		return this;
	}

	/**/
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput addChild(Object... child) {
		return (FunctionWithPropertiesAsInputAndStringAsOutput) super.addChild(child);
	}
}
