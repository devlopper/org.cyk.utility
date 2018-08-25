package org.cyk.utility.string.repository;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;

@Singleton
public class StringRepositoryDatabaseImpl extends AbstractStringRepositoryImpl implements StringRepositoryDatabase,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getOne(Properties properties) {
		return null;
	}

}
