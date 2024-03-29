package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.client.controller.component.output.OutputFile;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.repository.RepositoryTypeDatabase;
import org.cyk.utility.repository.RepositoryTypeFolder;
import org.cyk.utility.repository.RepositoryTypeSession;

@ApplicationScoped
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
		return UniformResourceIdentifierHelper.build(null, null, null, String.format(FILE_URL_PATH_FORMAT, identifier,location), null, null, null);
	}
	
	public String buildFileUrl(OutputFile outputFile,Object request) {
		String url = null;
		/*File file = outputFile.getValue();
		if(file!=null && file.getIdentifier()!=null)
			url = buildFileUrl(file.getIdentifier().toString(), outputFile.getRepositoryType(), request);
		*/
		return url;
	}
	
	/**/
	
	public static WebHelper getInstance() {
		return __inject__(WebHelper.class);
	}
	
	/**/
	
	private static final String FILE_URL_PATH_FORMAT = Constant.FILE_URL_PATTERN_PREFIX + "?identifier=%s&location=%s";
	
}
