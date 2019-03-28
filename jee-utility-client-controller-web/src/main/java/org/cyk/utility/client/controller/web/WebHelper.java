package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.client.controller.component.output.OutputFile;
import org.cyk.utility.file.File;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.repository.RepositoryTypeDatabase;
import org.cyk.utility.repository.RepositoryTypeFolder;
import org.cyk.utility.repository.RepositoryTypeSession;
import org.cyk.utility.string.StringHelper;

@Singleton
public class WebHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public String buildFileUrl(String identifier,RepositoryType repositoryType,Object request) {
		String location = null;
		if(repositoryType == null || repositoryType instanceof RepositoryTypeSession)
			location = "session";
		else if(repositoryType instanceof RepositoryTypeDatabase)
			location = "database";
		else if(repositoryType instanceof RepositoryTypeFolder)
			location = "folder";
		return __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).setPath(String.format(FILE_URL_PATH_FORMAT
				, identifier,location)).execute().getOutput();
	}
	
	public String buildFileUrl(OutputFile outputFile,Object request) {
		String url = null;
		File file = outputFile.getValue();
		if(file!=null && file.getIdentifier()!=null)
			url = buildFileUrl(__inject__(StringHelper.class).getString(file.getIdentifier()), outputFile.getRepositoryType(), request);
		return url;
	}
	
	/**/
	
	private static final String FILE_URL_PATH_FORMAT = Constant.FILE_URL_PATTERN_PREFIX + "?identifier=%s&location=%s";
}
