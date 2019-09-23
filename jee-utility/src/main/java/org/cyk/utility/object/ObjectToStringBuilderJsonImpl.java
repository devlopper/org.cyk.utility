package org.cyk.utility.object;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.field.FieldInstanceValues;
/*
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
*/
@Dependent @JavaScriptObjectNotation @Deprecated
public class ObjectToStringBuilderJsonImpl extends AbstractObjectToStringBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__(Object object, FieldInstanceValues fieldInstanceValues) throws Exception {
		/*ObjectMapper mapper = new ObjectMapper();
		if(Boolean.TRUE.equals(__inject__(ClassHelper.class).isBelongsToJavaPackages(object.getClass()))) {
			return mapper.writeValueAsString(object);
		}else {
			if(CollectionHelper.isEmpty(fieldInstanceValues)) {
				__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("jsonify object without specified fields");
				return null;
			}else { 
				SimpleModule module = new SimpleModule();
				module.addSerializer(Object.class,new Serializer());
				mapper.registerModule(module);
				 
				String serialized = mapper.writeValueAsString(fieldInstanceValues);
				return serialized;
			}
		}*/
		return null;
	}
	
	/**/
	/*
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
					else if(index.getValue() instanceof CollectionInstance<?>) {
						Strings strings = __inject__(Strings.class);
						for(Object indexCollectionInstance : ((CollectionInstance<?>)index.getValue()).get()) {
							String string = null;
							if(indexCollectionInstance instanceof Objectable) {
								Object identifier = ((Objectable)indexCollectionInstance).getIdentifier();
								if(identifier!=null)
									string = identifier.toString();
							}else {
								if(indexCollectionInstance!=null)
									string = indexCollectionInstance.toString();
							} 
							if(string == null)
								__inject__(ThrowableHelper.class).throwRuntimeException("collection value cannot be deduced to string");
							strings.add(string);
						}
						String string = __inject__(StringHelper.class).concatenate(strings.get(), COMA);
						generator.writeStringField(index.getFieldInstance().getPath(), string );
					}else if(index.getValue() instanceof Map<?, ?>) {
						generator.writeStringField(index.getFieldInstance().getPath(), new ObjectMapper().writeValueAsString(index.getValue()) );
					}else if(index.getValue() instanceof Enum<?>) {
						generator.writeStringField(index.getFieldInstance().getPath(), new ObjectMapper().writeValueAsString(index.getValue()) );
					}else
						__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("jsonify field of type "+index.getFieldInstance().getType());	
				}
			}
	        generator.writeEndObject();
	    }
	    
	    public static final String COMA = ConstantCharacter.COMA.toString();
	}
	*/
}
