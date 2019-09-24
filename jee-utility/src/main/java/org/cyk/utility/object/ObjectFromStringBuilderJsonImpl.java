package org.cyk.utility.object;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.field.FieldInstances;

@Dependent @JavaScriptObjectNotation @Deprecated
public class ObjectFromStringBuilderJsonImpl extends AbstractObjectFromStringBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__(String string, Class<?> klass,FieldInstances fieldInstances) throws Exception {
		Object object=null;
		/*ObjectMapper objectMapper = new ObjectMapper();
		if(Boolean.TRUE.equals(ClassHelper.isBelongsToJavaPackages(klass))) {
			object = objectMapper.readValue(string, klass);
		}else {
			SimpleModule module = new SimpleModule();
			if(CollectionHelper.isNotEmpty(fieldInstances))
				module.addDeserializer(Object.class, new Deserializer().setKlass(klass).setFieldInstances(fieldInstances));
			objectMapper.registerModule(module);
			object =  objectMapper.readValue(string, Object.class);	
		}*/
		return object;
	}

	/**/
	/*
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
					if (Boolean.TRUE.equals(ClassHelper.isInstanceOf(index.getType(), String.class))) {
						value = fieldNode.asText();
					}else if (Boolean.TRUE.equals(ClassHelper.isInstanceOf(index.getType(), CollectionInstance.class))) {
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
					}else if (Boolean.TRUE.equals(ClassHelper.isInstanceOf(index.getType(), Map.class))) {
						value = new ObjectMapper().readValue(fieldNode.asText(), Map.class);
					}else if (Boolean.TRUE.equals(ClassHelper.isInstanceOf(index.getType(), Enum.class))) {
						value = new ObjectMapper().readValue(fieldNode.asText(), index.getType());
					}else
						__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("objectfy field of type " + index.getType());
					
					__inject__(FieldValueSetter.class).execute(object, index.getPath(), value);
				}
			}
			return object;
		}
	}
	*/
}
