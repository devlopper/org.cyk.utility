package org.cyk.utility.file;

import org.cyk.utility.collection.CollectionInstance;

public interface Files extends CollectionInstance<File> {

	Boolean getIsDuplicateChecksumAllowed();
	Files setIsDuplicateChecksumAllowed(Boolean isDuplicateChecksumAllowed);
	
	Files computeChecksum(Boolean isOverridable);
	Files removeDuplicateByChecksum();
}
