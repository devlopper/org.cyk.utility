package org.cyk.utility.persistence.server.procedure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ProcedureExecutorArguments extends AbstractObject implements Serializable {

	private Class<?> klass;
	private ProcedureName procedureName;
	private String name;
	private Map<String,Object> parameters = new HashMap<>();
	private EntityManager entityManager;
	private Level logLevel;
	private Boolean transactionable;
}