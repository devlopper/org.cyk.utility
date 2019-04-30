package org.cyk.utility.file;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

@Singleton
public class FileHelperImpl extends AbstractHelper implements FileHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getName(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	@Override
	public String getExtension(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	@Override
	public String getMimeTypeByExtension(String extension) {
		return __inject__(MimeTypeGetter.class).setExtension(extension).execute().getOutput();
	}
	
	@Override
	public String getMimeTypeByNameAndExtension(String nameAndExtension) {
		return getMimeTypeByExtension(getExtension(nameAndExtension));
	}
	
	@Override
	public String concatenateNameAndExtension(String name, String extension) {
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(__inject__(StringHelper.class).isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
}
