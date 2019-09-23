package org.cyk.utility.file;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Files extends CollectionInstance<File> {

	Boolean getIsDuplicateChecksumAllowed();
	Files setIsDuplicateChecksumAllowed(Boolean isDuplicateChecksumAllowed);
	
	Files computeBytes(Boolean isOverridable);
	Files computeChecksum(Boolean isOverridable);
	Files removeDuplicateByChecksum();
}
