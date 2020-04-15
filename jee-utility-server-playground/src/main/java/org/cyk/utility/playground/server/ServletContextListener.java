package org.cyk.utility.playground.server;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.EntityReader;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryGetter;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.playground.server.persistence.api.NamableQuerier;
import org.cyk.utility.playground.server.persistence.entities.Namable;
import org.cyk.utility.server.deployment.AbstractServletContextListener;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		
		QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,NamableQuerier.QUERY_IDENTIFIER_READ_VIEW_01
				,Query.FIELD_TUPLE_CLASS,Namable.class,Query.FIELD_RESULT_CLASS,Namable.class
				,Query.FIELD_VALUE,NamableQuerier.QUERY_VALUE_READ_VIEW_01
				));
		
		QueryHelper.addQueries(Query.buildCountFromSelect(QueryGetter.getInstance().get(NamableQuerier.QUERY_IDENTIFIER_READ_VIEW_01)));
		
		DependencyInjection.setQualifierClassTo(System.class, EntityReader.class,EntityCounter.class);
	}
	
}
