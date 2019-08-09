package org.cyk.utility.clazz;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Case;

@Dependent
public class ClassNameBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ClassNameBuilder , Serializable {
	private static final long serialVersionUID = 1L;

	private NamingModel sourceNamingModel,destinationNamingModel;
	private String packageName,simpleName;
	
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
	
	public NamingModel getSourceNamingModel() {
		return sourceNamingModel;
	}
	
	@Override
	public NamingModel getSourceNamingModel(Boolean injectIfNull) {
		return (NamingModel) __getInjectIfNull__(injectIfNull);
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
		return (NamingModel) __getInjectIfNull__(injectIfNull);
	}
	
	@Override
	public ClassNameBuilder setDestinationNamingModel(NamingModel destinationNamingModel) {
		this.destinationNamingModel = destinationNamingModel;
		return this;
	}

	@Override
	protected String __execute__() {
		String name = null;
		String packageName = getPackageName();
		NamingModel sourceNamingModel = getSourceNamingModel();
		NamingModel destinationNamingModel = getDestinationNamingModel();
		if(sourceNamingModel!=null && destinationNamingModel!=null) {
			name = StringUtils.replaceOnce(packageName, sourceNamingModel.getNode(), destinationNamingModel.getNode());
			name = StringUtils.replaceOnce(name, sourceNamingModel.getLayer(), destinationNamingModel.getLayer());
			name = StringUtils.replaceOnce(name, sourceNamingModel.getSubLayer(), destinationNamingModel.getSubLayer());
			if(!name.endsWith("."))
				name = name + ".";
			String simpleName = getSimpleName();
			if(StringUtils.isNotBlank(sourceNamingModel.getSuffix()))
					simpleName = StringUtils.replaceOnce(simpleName, sourceNamingModel.getSuffix(), "");
			String suffix = destinationNamingModel.getSuffix();
			if(__injectStringHelper__().isBlank(suffix)) {
				Boolean isSuffixedByLayer = destinationNamingModel.getIsSuffixedByLayer();
				if(isSuffixedByLayer == null || isSuffixedByLayer) {
					if("representation".equals(destinationNamingModel.getLayer()) && "entities".equals(destinationNamingModel.getSubLayer()))
						suffix = "Dto";
					else if(("persistence".equals(destinationNamingModel.getLayer()) || "controller".equals(destinationNamingModel.getLayer())) 
							&& "entities".equals(destinationNamingModel.getSubLayer()))
						suffix = "";
					else if("impl".equals(destinationNamingModel.getSubLayer()))
						suffix = __injectStringHelper__().applyCase(destinationNamingModel.getLayer(),Case.FIRST_CHARACTER_UPPER)+"Impl";
					else
						suffix = __injectStringHelper__().applyCase(destinationNamingModel.getLayer(),Case.FIRST_CHARACTER_UPPER);
				}
			}
			if(__injectStringHelper__().isNotBlank(suffix))
				simpleName = simpleName + suffix;
			name = name + simpleName;
		}
		return name;
	}

}
