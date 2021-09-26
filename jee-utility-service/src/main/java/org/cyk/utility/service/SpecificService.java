package org.cyk.utility.service;

import java.util.List;

import javax.ws.rs.core.Response;

public interface SpecificService<ENTITY> extends Service {

	Response get(String filterAsString,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples);
	
	Response getOne(String identifier,List<String> projections);
	
}