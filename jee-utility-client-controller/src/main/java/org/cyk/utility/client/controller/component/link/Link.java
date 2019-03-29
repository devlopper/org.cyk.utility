package org.cyk.utility.client.controller.component.link;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.text.Text;

public interface Link extends VisibleComponent {

	Text getText();
	Link setText(Text text);
	
	String getUniformResourceLocator();
	Link setUniformResourceLocator(String uniformResourceLocator);
	
}
