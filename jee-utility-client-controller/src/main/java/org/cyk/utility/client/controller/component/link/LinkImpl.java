package org.cyk.utility.client.controller.component.link;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.text.Text;

public class LinkImpl extends AbstractVisibleComponentImpl implements Link,Serializable {
	private static final long serialVersionUID = 1L;

	private Text text;
	private String uniformResourceLocator;
	
	@Override
	public Text getText() {
		return text;
	}

	@Override
	public Link setText(Text text) {
		this.text = text;
		return this;
	}

	@Override
	public String getUniformResourceLocator() {
		return uniformResourceLocator;
	}

	@Override
	public Link setUniformResourceLocator(String uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}

}
