package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.PersistenceHelper;

public interface IdentifierExecutor extends SpecificFieldExecutor<String> {
	
	public static abstract class AbstractImpl extends SpecificFieldExecutor.AbstractImpl<String> implements IdentifierExecutor,Serializable {

		public AbstractImpl() {
			super(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER, String.class);
		}
	}
	
	static String getByCode(Class<?> klass,String code) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("get by code class", klass);
		if(StringHelper.isBlank(code))
			return null;
		try {
			return (String) EntityManagerGetter.getInstance().get().createQuery(String.format("SELECT t.identifier FROM %s t WHERE t.code = :code"
					,PersistenceHelper.getEntityName(klass))).setParameter("code", code).getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	/**/
	
	static IdentifierExecutor getInstance() {
		return Helper.getInstance(IdentifierExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}