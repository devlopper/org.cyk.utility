 package org.cyk.utility.client.controller.component.file;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface File extends VisibleComponent {

	org.cyk.utility.__kernel__.file.File getValue();
	File setValue(org.cyk.utility.__kernel__.file.File value);
	
	Boolean getIsEmbedded();
	File setIsEmbedded(Boolean isEmbedded);
	
}
