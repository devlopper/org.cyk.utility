package org.cyk.utility.client.controller.component.link;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.text.TextBuilder;
import org.cyk.utility.resource.locator.UniformResourceLocatorStringBuilder;

public class LinkBuilderImpl extends AbstractVisibleComponentBuilderImpl<Link> implements LinkBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private TextBuilder text;
	private UniformResourceLocatorStringBuilder uniformResourceLocator;

	@Override
	protected void __execute__(Link link) {
		super.__execute__(link);
		TextBuilder text = getText();
		if(text!=null) {
			text.getProperties().copyFrom(getProperties(), Properties.CONTEXT,Properties.UNIFORM_RESOURCE_LOCATOR_MAP,Properties.REQUEST);
			link.setText(text.execute().getOutput());
		}
		UniformResourceLocatorStringBuilder uniformResourceLocator = getUniformResourceLocator();
		if(uniformResourceLocator!=null) {
			if(uniformResourceLocator.getUniformResourceIdentifierString()!=null)
				uniformResourceLocator.getUniformResourceIdentifierString().setRequest(getRequest());
			link.setUniformResourceLocator(uniformResourceLocator.execute().getOutput());
		}
	}
	
	@Override
	public TextBuilder getText() {
		return text;
	}

	@Override
	public TextBuilder getText(Boolean injectIfNull) {
		if(text == null && Boolean.TRUE.equals(injectIfNull))
			text = __inject__(TextBuilder.class);
		return text;
	}

	@Override
	public LinkBuilder setText(TextBuilder text) {
		this.text = text;
		return this;
	}
	
	@Override
	public LinkBuilder setTextCharacters(String characters) {
		getText(Boolean.TRUE).setCharacters(characters);
		return this;
	}

	@Override
	public UniformResourceLocatorStringBuilder getUniformResourceLocator() {
		return uniformResourceLocator;
	}

	@Override
	public UniformResourceLocatorStringBuilder getUniformResourceLocator(Boolean injectIfNull) {
		if(uniformResourceLocator == null && Boolean.TRUE.equals(injectIfNull))
			uniformResourceLocator = __inject__(UniformResourceLocatorStringBuilder.class);
		return uniformResourceLocator;
	}

	@Override
	public LinkBuilder setUniformResourceLocator(UniformResourceLocatorStringBuilder uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}
	
	/**/
	
}
