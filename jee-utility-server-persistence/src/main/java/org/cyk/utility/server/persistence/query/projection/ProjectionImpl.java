package org.cyk.utility.server.persistence.query.projection;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.field.FieldInstancesRuntime;

@Dependent
public class ProjectionImpl extends AbstractObject implements Projection,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> klass;
	private Fields fields;
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}
	
	@Override
	public Projection setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
	@Override
	public Boolean hasFieldWithPath(String... paths) {
		return fields == null ? Boolean.FALSE : fields.hasPath(paths);
	}
	
	@Override
	public Field getFieldByPath(String... paths) {
		return fields == null ? null : fields.getByPath(paths);
	}
	
	@Override
	public Fields getFields() {
		return fields;
	}

	@Override
	public Fields getFields(Boolean injectIfNull) {
		if(fields == null && Boolean.TRUE.equals(injectIfNull))
			fields = __inject__(Fields.class);
		return fields;
	}

	@Override
	public Projection setFields(Fields fields) {
		this.fields = fields;
		return this;
	}

	@Override
	public Projection addFields(Collection<Field> fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

	@Override
	public Projection addFields(Field... fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

	@Override
	public Projection addField(String fieldName) {
		Class<?> klass = getKlass();
		Field field = __inject__(Field.class).setInstance(__inject__(FieldInstancesRuntime.class).get(klass, fieldName));
		return addFields(field);
	}
	
}
