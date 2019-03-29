package org.cyk.utility.client.controller.component.link;

import java.io.Serializable;

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
		if(text!=null)
			link.setText(text.execute().getOutput());
		UniformResourceLocatorStringBuilder uniformResourceLocator = getUniformResourceLocator();
		if(uniformResourceLocator!=null)
			link.setUniformResourceLocator(uniformResourceLocator.execute().getOutput());
	}
	
	@Override
	public TextBuilder getText() {
		return text;
	}

	@Override
	public TextBuilder getText(Boolean injectIfNull) {
		return (TextBuilder) __getInjectIfNull__(FIELD_TEXT, injectIfNull);
	}

	@Override
	public LinkBuilder setText(TextBuilder text) {
		this.text = text;
		return this;
	}

	@Override
	public UniformResourceLocatorStringBuilder getUniformResourceLocator() {
		return uniformResourceLocator;
	}

	@Override
	public UniformResourceLocatorStringBuilder getUniformResourceLocator(Boolean injectIfNull) {
		return (UniformResourceLocatorStringBuilder) __getInjectIfNull__(FIELD_UNIFORM_RESOURCE_LOCATOR, injectIfNull);
	}

	@Override
	public LinkBuilder setUniformResourceLocator(UniformResourceLocatorStringBuilder uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}
	
	/**/
	
	private static final String FIELD_TEXT = "text";
	private static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	
}
