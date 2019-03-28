package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.repository.RepositoryTypeDatabase;
import org.cyk.utility.repository.RepositoryTypeSession;

public class OutputFileBuilderImpl extends AbstractOutputBuilderImpl<OutputFile,File> implements OutputFileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private RepositoryType repositoryType;
	
	@Override
	protected void __execute__(OutputFile outputFile, Object object, Field field) {
		super.__execute__(outputFile, object, field);
		RepositoryType repositoryType = getRepositoryType();
		if(repositoryType == null) {
			File file = getValue();
			if(file == null || file.getIdentifier() == null)
				repositoryType = __inject__(RepositoryTypeSession.class);
			else
				repositoryType = __inject__(RepositoryTypeDatabase.class);
		}
	}
	
	@Override
	protected File __getValue__(Object object, Field field, Object value) {
		return (File) value;
	}

	@Override
	public RepositoryType getRepositoryType() {
		return repositoryType;
	}

	@Override
	public OutputFileBuilder setRepositoryType(RepositoryType repositoryType) {
		this.repositoryType = repositoryType;
		return this;
	}
	
}
