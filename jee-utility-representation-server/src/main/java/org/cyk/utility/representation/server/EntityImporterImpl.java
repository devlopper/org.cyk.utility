package org.cyk.utility.representation.server;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.representation.Arguments;
import org.cyk.utility.representation.Arguments.Internal;
import org.cyk.utility.representation.EntityImporter;

@ApplicationScoped
public class EntityImporterImpl extends EntityImporter.AbstractImpl implements Serializable {
	
	@Override
	protected Internal instantiateInternal(Arguments arguments) {
		return new Arguments.Internal(arguments, EntityImporter.class);
	}
	
	@Override
	protected void __execute__(Arguments arguments, Internal internal,QueryExecutorArguments queryExecutorArguments,LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments) {
		logMessages.add("import");
		
	}
	
	@Override
	protected Boolean getLoggable() {
		return LOGGABLE;
	}
	
	protected Level getLogLevel() {
		return LOG_LEVEL;
	}
	
	@Override
	public Response import_(Arguments arguments) {
		return execute(arguments);
	}
	
	@Override
	public Response import_(String representationEntityClassName) {
		Arguments arguments = new Arguments();
		arguments.setRepresentationEntityClassName(representationEntityClassName);
		
		return import_(arguments);
	}
	
	public static Boolean LOGGABLE = Boolean.FALSE;
	public static Level LOG_LEVEL = Level.FINEST;
}