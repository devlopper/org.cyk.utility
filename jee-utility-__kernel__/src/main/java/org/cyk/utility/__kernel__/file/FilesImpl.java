package org.cyk.utility.__kernel__.file;

import java.io.Serializable;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class FilesImpl extends AbstractCollectionInstanceImpl<File> implements Files,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isDuplicateChecksumAllowed;
	
	@Override
	protected Boolean __isAddable__(File file) {
		Boolean isDuplicateChecksumAllowed = getIsDuplicateChecksumAllowed();
		return Boolean.TRUE.equals(super.__isAddable__(file)) && (isDuplicateChecksumAllowed==null || Boolean.TRUE.equals(isDuplicateChecksumAllowed) || 
				!collection.stream().map(x -> x.getChecksum()).collect(Collectors.toList()).contains(file.getChecksum()));
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
	
	@Override
	public Files computeBytes(Boolean isOverridable) {
		
		return this;
	}
	
	@Override
	public Files computeChecksum(Boolean isOverridable) {
		
		return this;
	}
	
	@Override
	public Files removeDuplicateByChecksum() {
		computeChecksum(Boolean.FALSE);
		removeDuplicate(File::getChecksum);
		return this;
	}
	
	
}
