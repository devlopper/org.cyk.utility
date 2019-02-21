package org.cyk.utility.server.representation.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestRepresentationUpdateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationUpdateIntegration {
	private static final long serialVersionUID = 1L;

	private Map<Object, Map<String, Object>> fieldValuesMap;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void ____perform____(Object object) throws Exception {
		Map<String,Object> map = getFieldValuesMap(object);
		
		String identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object).toString();
		String type = ValueUsageType.BUSINESS.name();
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(getObjectClass());
		object = representation.getOne(identifier, type).getEntity();
		
		Collection<String> fieldNames = new ArrayList<>();
		if(map!=null)
			for(Map.Entry<String, Object> index : map.entrySet()) {
				__inject__(FieldValueSetter.class).execute(object, index.getKey(), index.getValue());
				fieldNames.add(index.getKey());
			}
		__response__ = representation.updateOne(object,__injectStringHelper__().concatenate(fieldNames, CharacterConstant.COMA.toString()));
		
		//Object updated = representation.getOne(identifier, type).getEntity();
		
	}
	
	@Override
	public TestRepresentationUpdateIntegration setFieldValuesMap(Map<Object, Map<String, Object>> fieldValuesMap) {
		this.fieldValuesMap = fieldValuesMap;
		return this;
	}
	
	@Override
	public Map<Object, Map<String, Object>> getFieldValuesMap() {
		return fieldValuesMap;
	}
	
	@Override
	public TestRepresentationUpdateIntegration setFieldValuesMap(Object object, Map<String, Object> fieldValuesMap) {
		Map<Object, Map<String, Object>> map = getFieldValuesMap();
		if(map == null) {
			setFieldValuesMap(map = new HashMap<>());
		}
		map.put(object, fieldValuesMap);
		return this;
	}
	
	@Override
	public Map<String, Object> getFieldValuesMap(Object object) {
		Map<Object, Map<String, Object>> map = getFieldValuesMap();
		return map == null ? null : map.get(object);
	}
	
}
