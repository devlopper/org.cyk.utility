package org.cyk.utility.persistence;

public interface MaterializedViewSpecificPersistence<ENTITY> extends SpecificPersistence<ENTITY> {

	void create();
	void update();
	void actualize();
	void delete();
	
}