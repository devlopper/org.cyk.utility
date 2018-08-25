package org.cyk.utility.string.repository;

public interface StringRepositoryResourceBundle extends StringRepository {

	StringRepositoryResourceBundle addBundle(String baseName,ClassLoader classLoader);
	StringRepositoryResourceBundle addBundle(String baseName);
	StringRepositoryResourceBundle addBundle(String...baseNames);
	
}
