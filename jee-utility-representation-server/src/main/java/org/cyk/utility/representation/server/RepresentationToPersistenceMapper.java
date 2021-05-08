package org.cyk.utility.representation.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.business.server.EntityReader;
import org.cyk.utility.representation.server.RepresentationToPersistenceMapper.Arguments.Processor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface RepresentationToPersistenceMapper {

	<R,P> Collection<P> map(Class<P> persistenceClass,Class<R> representationClass,Arguments<P,R> arguments);
	<R,P> Collection<P> map(Class<P> persistenceClass,Class<R> representationClass,Collection<R> representationEntities,String actionIdentifier,Processor<P,R> processor);
	
	public static abstract class AbstractImpl extends AbstractObject implements RepresentationToPersistenceMapper,Serializable {
		
		@Override
		public <R, P> Collection<P> map(Class<P> persistenceClass,Class<R> representationClass,Arguments<P,R> arguments) {
			if(persistenceClass == null || representationClass == null || arguments == null || CollectionHelper.isEmpty(arguments.representationEntities))
				return null;
			if(CollectionHelper.isEmpty(arguments.representationEntities))
				throw new RuntimeException(ValueHelper.defaultToIfBlank(arguments.representationEntitiesEmptyMessage
						, String.format("Liste de %s requise",representationClass.getSimpleName())));
			Collection<String> identifiers = FieldHelper.readSystemIdentifiersAsStrings(arguments.representationEntities);
			if(CollectionHelper.isEmpty(identifiers))
				throw new RuntimeException(ValueHelper.defaultToIfBlank(arguments.representationEntitiesIdentifiersEmptyMessage
						, String.format("Liste des identifiants de %s requise",representationClass.getSimpleName())));
			Collection<P> persistences = EntityReader.getInstance().readByIdentifiersForProcessing(persistenceClass, CollectionHelper.cast(Object.class, identifiers)
					, arguments.actionIdentifier);
			if(CollectionHelper.isEmpty(persistences))
				throw new RuntimeException(ValueHelper.defaultToIfBlank(arguments.representationEntitiesEmptyMessage
						, String.format("Liste de %s introuvables",representationClass.getSimpleName())));
			for(P persistence : persistences) {
				for(R representation : arguments.representationEntities) {
					if(FieldHelper.readSystemIdentifier(persistence).equals(FieldHelper.readSystemIdentifier(representation))) {
						arguments.processor.process(persistence, representation);
						break;
					}
				}
			}
			return persistences;
		}
		
		@Override
		public <R, P> Collection<P> map(Class<P> persistenceClass, Class<R> representationClass,Collection<R> representationEntities,String actionIdentifier,Processor<P,R> processor) {
			return map(persistenceClass,representationClass,new Arguments<P,R>().setPersistenceClass(persistenceClass).setRepresentationClass(representationClass)
					.setRepresentationEntities(representationEntities).setActionIdentifier(actionIdentifier).setProcessor(processor));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<P,R> {
		private Class<R> representationClass;
		private Collection<R> representationEntities;
		private String representationEntitiesEmptyMessage;
		private String representationEntitiesIdentifiersEmptyMessage;
		private Class<P> persistenceClass;
		private String persistenceEntitiesEmptyMessage;
		private String actionIdentifier;
		private Processor<P,R> processor;
		
		/**/
		
		public static interface Processor<P,R> {
			void process(P persistenceEntity,R representationEntity);
		}
	}
	
	/**/
	
	static RepresentationToPersistenceMapper getInstance() {
		return Helper.getInstance(RepresentationToPersistenceMapper.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}