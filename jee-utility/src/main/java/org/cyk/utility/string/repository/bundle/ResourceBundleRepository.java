package org.cyk.utility.string.repository.bundle;

import org.cyk.utility.string.repository.StringRepository;

public interface ResourceBundleRepository extends StringRepository {

	ResourceBundleRepository addBundle(String baseName,ClassLoader classLoader);
	ResourceBundleRepository addBundle(String baseName);
	
}
