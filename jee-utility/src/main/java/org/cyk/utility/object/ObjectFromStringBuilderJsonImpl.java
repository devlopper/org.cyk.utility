package org.cyk.utility.object;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstances;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.network.message.Receiver;
import org.cyk.utility.network.message.Receivers;
import org.cyk.utility.string.Strings;
import org.cyk.utility.throwable.ThrowableHelper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Dependent @JavaScriptObjectNotation
public class ObjectFromStringBuilderJsonImpl extends AbstractObjectFromStringBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__(String string, Class<?> klass,FieldInstances fieldInstances) throws Exception {
		Object object;
		ObjectMapper objectMapper = new ObjectMapper();
		if(Boolean.TRUE.equals(__inject__(ClassHelper.class).isBelongsToJavaPackages(klass))) {
			object = objectMapper.readValue(string, klass);
		}else {
			SimpleModule module = new SimpleModule();
			//module.addDeserializer(Object.class, new Deserializer().setKlass(klass).setFieldInstances(fieldInstances));
			objectMapper.registerModule(module);
			object =  objectMapper.readValue(string, Object.class);	
		}
		return object;
	}

	/**/
	
	public static class Deserializer extends StdDeserializer<Object> implements Serializable {
		private static final long serialVersionUID = 1L;

		@Getter @Setter @Accessors(chain = true)
		private FieldInstances fieldInstances;

		@Getter @Setter @Accessors(chain = true)
		private Class<?> klass;

		public Deserializer() {
			this(null);
		}

		public Deserializer(Class<?> klass) {
			super(klass);
		}

		@Override
		public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
			JsonNode node = parser.getCodec().readTree(parser);
			Object object = __inject__(klass);
			for (FieldInstance index : fieldInstances.get()) {
				JsonNode fieldNode = node.get(index.getPath());
				if (fieldNode != null) {
					Object value = null;
					if (Boolean.TRUE.equals(__inject__(ClassHelper.class).isInstanceOf(index.getType(), String.class))) {
						value = fieldNode.asText();
					}else if (Boolean.TRUE.equals(__inject__(ClassHelper.class).isInstanceOf(index.getType(), CollectionInstance.class))) {
						String[] tokens = ((String)fieldNode.asText()).split(ObjectToStringBuilderJsonImpl.Serializer.COMA);
						value = __inject__(index.getType());
						for(String indexToken : tokens) {
							if(value instanceof Strings)
								((Strings)value).add(indexToken);
							else if(value instanceof Receivers) {
								((Receivers)value).add(__inject__(Receiver.class).setIdentifier(indexToken));
							} else {
								__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("objectfy field of type " + index.getType());
							}
						}						
					}else if (Boolean.TRUE.equals(__inject__(ClassHelper.class).isInstanceOf(index.getType(), Map.class))) {
						value = new ObjectMapper().readValue(fieldNode.asText(), Map.class);
					}else if (Boolean.TRUE.equals(__inject__(ClassHelper.class).isInstanceOf(index.getType(), Enum.class))) {
						value = new ObjectMapper().readValue(fieldNode.asText(), index.getType());
					}else
						__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("objectfy field of type " + index.getType());
					
					__inject__(FieldValueSetter.class).execute(object, index.getPath(), value);
				}
			}
			return object;
		}
	}
	
}
