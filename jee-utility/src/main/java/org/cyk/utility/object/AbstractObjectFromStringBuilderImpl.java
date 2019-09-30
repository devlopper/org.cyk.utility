package org.cyk.utility.object;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.field.FieldInstances;
import org.cyk.utility.__kernel__.string.Strings;

@Deprecated
public abstract class AbstractObjectFromStringBuilderImpl extends AbstractObjectToOrFromStringBuilderImpl<Object> implements ObjectFromStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String string;
	private Class<?> klass;
	
	@Override
	protected Object __execute__(Strings fieldNamesStrings) throws Exception {
		throw new RuntimeException(getClass()+" is deprecated");
		/*String string = ValueHelper.returnOrThrowIfBlank("string", getString());
		Class<?> klass = ValueHelper.returnOrThrowIfBlank("class", getKlass());
		FieldInstances fieldInstances = null;
		if(org.cyk.utility.__kernel__.klass.ClassHelper.isBelongsToJavaPackages(klass)) {
			
		}else {
			ValueHelper.returnOrThrowIfBlank("field names", fieldNamesStrings);
			if(CollectionHelper.isNotEmpty(fieldNamesStrings)) {
				fieldInstances = __inject__(FieldInstances.class);
				for(String index : fieldNamesStrings.get())
					fieldInstances.add(__inject__(FieldInstancesRuntime.class).get(klass, index));
			}
				
		}
		return __execute__(string,klass,fieldInstances);
		*/
	}
	
	protected abstract Object __execute__(String string,Class<?> klass,FieldInstances fieldInstances) throws Exception;
	
	@Override
	public String getString() {
		return string;
	}
	
	@Override
	public ObjectFromStringBuilder setString(String string) {
		this.string = string;
		return this;
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}
	@Override
	public ObjectFromStringBuilder setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
	@Override
	public ObjectFromStringBuilder addFieldNamesStrings(Collection<String> fieldNamesStrings) {
		return (ObjectFromStringBuilder) super.addFieldNamesStrings(fieldNamesStrings);
	}
	
	@Override
	public ObjectFromStringBuilder addFieldNamesStrings(String... fieldNamesStrings) {
		return (ObjectFromStringBuilder) super.addFieldNamesStrings(fieldNamesStrings);
	}
	
}
