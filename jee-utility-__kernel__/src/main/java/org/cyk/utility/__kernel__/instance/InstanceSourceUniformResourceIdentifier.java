package org.cyk.utility.__kernel__.instance;

import java.net.URI;

public interface InstanceSourceUniformResourceIdentifier extends InstanceSource {

	URI getUniformResourceIdentifier();
	InstanceSourceUniformResourceIdentifier setUniformResourceIdentifier(URI uniformResourceIdentifier);
	InstanceSourceUniformResourceIdentifier setUniformResourceIdentifier(String uniformResourceIdentifier);
	
}
