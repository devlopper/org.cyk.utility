package org.cyk.utility.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl extends AbstractFunctionWithPropertiesAsInputImpl<String>
		implements FunctionWithPropertiesAsInputAndStringAsOutput, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		Collection<Object> children = getChildren();
		if(__inject__(CollectionHelper.class).isNotEmpty(children)){
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
					strings.add(string);
				}
			}
			return __inject__(StringHelper.class).concatenate(strings,CharacterConstant.SPACE.toString());
		}else {
			Boolean isFormatRequired = __getIsFormatRequired__(Boolean.TRUE.equals(getProperties().getFromPath(Properties.IS,Properties.FORMAT,Properties.REQUIRED)));
			String format = __getFormat__(getFormat());
			if(__inject__(StringHelper.class).isBlank(format) && isFormatRequired)
				__inject__(ThrowableHelper.class).throwRuntimeException("Function to output string : format is required");
			Collection<String> formatArguments = isFormatRequired ? __getFormatArguments__(isFormatRequired,getFormatArguments()) : null;
			if(__inject__(CollectionHelper.class).isEmpty(formatArguments) && isFormatRequired)
				__inject__(ThrowableHelper.class).throwRuntimeException("Function to output string : format arguments are required");
			if(__inject__(StringHelper.class).isNotBlank(format) && __inject__(CollectionHelper.class).isNotEmpty(formatArguments))
				return __execute__(format,formatArguments);	
		}
		return super.__execute__();
	}
	
	protected String __execute__(String format,Collection<String> formatArguments) throws Exception {
		return String.format(format, formatArguments.toArray());
	}
	
	protected String __getFormat__(String format){
		return format;
	}
	
	protected Collection<String> __getFormatArguments__(Boolean isFormatRequired,Collection<String> formatArguments){
		return formatArguments;
	}
	
	protected Boolean __getIsFormatRequired__(Boolean value){
		return value;
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
	public Collection<String> getFormatArguments() {
		return (Collection<String>) getProperties().getFromPath(Properties.FORMAT,Properties.ARGUMENTS);
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput setFormatArguments(Collection<String> formatArguments) {
		getProperties().setFromPath(new Object[]{Properties.FORMAT,Properties.ARGUMENTS}, formatArguments);
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(Collection<String> formatArguments) {
		if(__inject__(CollectionHelper.class).isNotEmpty(formatArguments)){
			Collection<String> collection = getFormatArguments();
			if(collection == null)
				setFormatArguments(collection = new ArrayList<String>());
			collection.addAll(formatArguments);
		}
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(String... formatArguments) {
		addFormatArguments(__inject__(CollectionHelper.class).instanciate(formatArguments));
		return this;
	}
	
	@Override
	public FunctionWithPropertiesAsInputAndStringAsOutput getParent() {
		return (FunctionWithPropertiesAsInputAndStringAsOutput) super.getParent();
	}
}
