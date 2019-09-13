package org.cyk.utility.string.repository;

@Deprecated
public interface StringRepositoryResourceBundle extends StringRepository {

	StringRepositoryResourceBundle addBundleAt(String baseName,ClassLoader classLoader,Integer index);
	StringRepositoryResourceBundle addBundle(String baseName,ClassLoader classLoader);
	StringRepositoryResourceBundle addBundleAt(String baseName,Integer index);
	StringRepositoryResourceBundle addBundle(String baseName);
	StringRepositoryResourceBundle addBundle(String...baseNames);
	
}
