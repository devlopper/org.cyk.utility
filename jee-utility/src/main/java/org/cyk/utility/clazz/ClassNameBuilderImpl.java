package org.cyk.utility.clazz;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class ClassNameBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ClassNameBuilder , Serializable {
	private static final long serialVersionUID = 1L;

	private NamingModel sourceNamingModel,destinationNamingModel;
	private String packageName,simpleName;
	private Class<?> klass;

	@Override
	protected String __execute__() {
		String name = null;
		Class<?> klass = getKlass();
		String packageName = getPackageName();
		if(StringHelper.isBlank(packageName))
			packageName = klass.getPackage().getName();
		String simpleName = getSimpleName();
		if(StringHelper.isBlank(simpleName)) {
			simpleName = StringUtils.substringBeforeLast(klass.getSimpleName(),"$Proxy$_$$_WeldSubclass");
		}
		
		NamingModel sourceNamingModel = getSourceNamingModel();
		NamingModel destinationNamingModel = getDestinationNamingModel();
		if(sourceNamingModel!=null && destinationNamingModel!=null) {
			name = StringUtils.replaceOnce(packageName, sourceNamingModel.getNode(), destinationNamingModel.getNode());
			name = StringUtils.replaceOnce(name, sourceNamingModel.getLayer(), destinationNamingModel.getLayer());
			name = StringUtils.replaceOnce(name, sourceNamingModel.getSubLayer(), destinationNamingModel.getSubLayer());
			if(!name.endsWith("."))
				name = name + ".";
			
			if(StringUtils.isNotBlank(sourceNamingModel.getSuffix()))
				simpleName = StringUtils.replaceOnce(simpleName, sourceNamingModel.getSuffix(), "");
			String suffix = destinationNamingModel.getSuffix();
			if(StringHelper.isBlank(suffix)) {
				Boolean isSuffixedByLayer = destinationNamingModel.getIsSuffixedByLayer();
				if(isSuffixedByLayer == null || isSuffixedByLayer) {
					if("representation".equals(destinationNamingModel.getLayer()) && "entities".equals(destinationNamingModel.getSubLayer()))
						suffix = "Dto";
					else if(("persistence".equals(destinationNamingModel.getLayer()) || "controller".equals(destinationNamingModel.getLayer())) 
							&& "entities".equals(destinationNamingModel.getSubLayer()))
						suffix = "";
					else if("impl".equals(destinationNamingModel.getSubLayer()))
						suffix = StringHelper.applyCase(destinationNamingModel.getLayer(),Case.FIRST_CHARACTER_UPPER)+"Impl";
					else
						suffix = StringHelper.applyCase(destinationNamingModel.getLayer(),Case.FIRST_CHARACTER_UPPER);
				}
			}
			if(StringHelper.isNotBlank(suffix))
				simpleName = simpleName + suffix;
			name = name + simpleName;
		}
		return name;
	}
	
	@Override
	public String getPackageName() {
		return packageName;
	}
	
	@Override
	public ClassNameBuilder setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}
	
	@Override
	public String getSimpleName() {
		return simpleName;
	}
	
	@Override
	public ClassNameBuilder setSimpleName(String simpleName) {
		this.simpleName = simpleName;
		return this;
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}
	
	@Override
	public ClassNameBuilder setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
	public NamingModel getSourceNamingModel() {
		return sourceNamingModel;
	}
	
	@Override
	public NamingModel getSourceNamingModel(Boolean injectIfNull) {
		if(sourceNamingModel == null && Boolean.TRUE.equals(injectIfNull))
			sourceNamingModel = DependencyInjection.inject(NamingModel.class);
		return sourceNamingModel;
	}
	
	@Override
	public ClassNameBuilder setSourceNamingModel(NamingModel sourceNamingModel) {
		this.sourceNamingModel = sourceNamingModel;
		return this;
	}
	
	public NamingModel getDestinationNamingModel() {
		return destinationNamingModel;
	}
	
	@Override
	public NamingModel getDestinationNamingModel(Boolean injectIfNull) {
		if(destinationNamingModel == null && Boolean.TRUE.equals(injectIfNull))
			destinationNamingModel = DependencyInjection.inject(NamingModel.class);
		return destinationNamingModel;
	}
	
	@Override
	public ClassNameBuilder setDestinationNamingModel(NamingModel destinationNamingModel) {
		this.destinationNamingModel = destinationNamingModel;
		return this;
	}

}
