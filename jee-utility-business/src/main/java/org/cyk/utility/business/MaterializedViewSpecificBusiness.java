package org.cyk.utility.business;

public interface MaterializedViewSpecificBusiness<ENTITY> extends SpecificBusiness<ENTITY> {

	Result create();
	Result update();
	Result actualize();
	Result delete();
}