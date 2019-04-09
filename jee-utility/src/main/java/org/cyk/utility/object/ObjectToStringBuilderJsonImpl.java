package org.cyk.utility.object;

import java.io.IOException;
import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Json;
import org.cyk.utility.field.FieldInstanceValue;
import org.cyk.utility.field.FieldInstanceValues;
import org.cyk.utility.throwable.ThrowableHelper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Json
public class ObjectToStringBuilderJsonImpl extends AbstractObjectToStringBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__(Object object, FieldInstanceValues fieldInstanceValues) throws Exception {
		if(__injectCollectionHelper__().isEmpty(fieldInstanceValues)) {
			__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("jsonify object without specified fields");
			return null;
		}else {
			ObjectMapper mapper = new ObjectMapper();
			 
			SimpleModule module = new SimpleModule();
			module.addSerializer(Object.class,new Serializer());
			mapper.registerModule(module);
			 
			String serialized = mapper.writeValueAsString(fieldInstanceValues);
			return serialized;
		}
	}
	
	/**/
	
	public static class Serializer extends StdSerializer<Object> {
	    private static final long serialVersionUID = 1L;

		public Serializer() {
	        this(null);
	    }
	   
	    public Serializer(Class<Object> t) {
	        super(t);
	    }
	 
	    @Override
	    public void serialize(Object object, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
	    	FieldInstanceValues fieldInstanceValues = (FieldInstanceValues) object;
	    	generator.writeStartObject();
	    	for(FieldInstanceValue index : fieldInstanceValues.get()) {
				if(index.getValue() != null) {
					if(index.getValue() instanceof String)
						generator.writeStringField(index.getFieldInstance().getPath(), (String) index.getValue());
					else
						__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("jsonify field of type "+index.getFieldInstance().getType());	
				}
			}
	        generator.writeEndObject();
	    }
	}
	
}
