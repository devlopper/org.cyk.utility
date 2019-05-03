package org.cyk.utility.file;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class FilesImpl extends AbstractCollectionInstanceImpl<File> implements Files,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isDuplicateChecksumAllowed;
	
	@Override
	protected void ____add____(Collection<File> __collection__, File file) {
		Boolean isDuplicateChecksumAllowed = getIsDuplicateChecksumAllowed();
		if(isDuplicateChecksumAllowed==null || Boolean.TRUE.equals(isDuplicateChecksumAllowed) || 
				!__collection__.stream().map(x -> x.getChecksum()).collect(Collectors.toList()).contains(file.getChecksum()))
			super.____add____(__collection__, file);
	}
	
	@Override
	public Boolean getIsDuplicateChecksumAllowed() {
		return isDuplicateChecksumAllowed;
	}
	
	@Override
	public Files setIsDuplicateChecksumAllowed(Boolean isDuplicateChecksumAllowed) {
		this.isDuplicateChecksumAllowed = isDuplicateChecksumAllowed;
		return this;
	}
	
}
