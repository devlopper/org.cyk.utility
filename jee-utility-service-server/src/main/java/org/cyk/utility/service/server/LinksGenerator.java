package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.service.Link;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface LinksGenerator {
	
	Collection<Link> generate(Arguments arguments);
	/*
	Collection<Link> generate(Collection<?> owners,String identifier,String valueFormat);
	Collection<Link> generate(Collection<?> owners,String identifier);
	*/
	Collection<Link> generate(Collection<?> owners,Collection<String> identifiers,Map<String,String> valueFormatMap);
	Collection<Link> generate(Collection<?> owners,Collection<String> identifiers);
	
	public static abstract class AbstractImpl extends AbstractObject implements LinksGenerator,Serializable {
		
		@Override
		public Collection<Link> generate(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("links generator arguments", arguments);
			Map<String,String> valueFormatMap = arguments.valueFormatMap;
			Collection<Link> links = null;
			if(CollectionHelper.isNotEmpty(arguments.owners) && CollectionHelper.isNotEmpty(arguments.identifiers)) {
				Collection<Link> result = __generate__(arguments.owners, arguments.identifiers,valueFormatMap);
				if(CollectionHelper.isNotEmpty(result)) {
					if(links == null)
						links = new ArrayList<>();
					links.addAll(result);
				}
			}
			
			return links;
		}
		
		protected Collection<Link> __generate__(Collection<Object> owners,Collection<String> identifiers,Map<String,String> valueFormatMap) {
			Collection<Link> links = null;
			for(Object owner : owners) {
				if(owner == null)
					continue;
				Collection<Link> result = __generate__(owner, identifiers,valueFormatMap);
				if(CollectionHelper.isNotEmpty(result)) {
					if(links == null)
						links = new ArrayList<>();
					links.addAll(result);
				}
			}
			return links;
		}
		
		protected Collection<Link> __generate__(Object owner,Collection<String> identifiers,Map<String,String> valueFormatMap) {
			Collection<Link> links = null;
			for(String identifier : identifiers) {
				if(StringHelper.isBlank(identifier))
					continue;
				Link link = __generate__(owner, identifier,valueFormatMap == null ? null : valueFormatMap.get(identifier));
				if(link == null || StringHelper.isBlank(link.getIdentifier()) || StringHelper.isBlank(link.getValue()))
					continue;			
				if(links == null)
					links = new ArrayList<>();
				links.add(link);				
			}
			if(CollectionHelper.isNotEmpty(links)) {
				if(owner instanceof org.cyk.utility.service.entity.AbstractObject)
					((org.cyk.utility.service.entity.AbstractObject)owner).add__links__(links);
			}
			return links;
		}
		
		protected Link __generate__(Object owner,String identifier,String valueFormat) {
			Link link = new Link().setIdentifier(identifier);
			link.setValue(__generateValue__(owner, link,valueFormat));
			if(StringHelper.isBlank(link.getValue()))
				return null;
			return link;
		}
		
		protected String getDefaultValueFormat(Object owner,String identifier) {
			return null;
		}
		
		protected String __generateValue__(Object owner,Link link,String valueFormat) {
			if(StringHelper.isBlank(valueFormat))
				valueFormat = getDefaultValueFormat(owner, link.getIdentifier());
			if(StringHelper.isNotBlank(valueFormat))
				return String.format(valueFormat, FieldHelper.readSystemIdentifier(owner));
			throw new RuntimeException(String.format("Generate <<%s>> link of <<%s.%s>> not yet implemented",link.getIdentifier(),owner.getClass().getSimpleName(),owner));
		}
		
		@Override
		public Collection<Link> generate(Collection<?> owners, Collection<String> identifiers,Map<String,String> valueFormatMap) {
			if(CollectionHelper.isEmpty(owners) || CollectionHelper.isEmpty(identifiers))
				return null;
			return generate(new Arguments().addOwners(owners).addIdentifiers(identifiers).setValueFormatMap(valueFormatMap));
		}
		
		@Override
		public Collection<Link> generate(Collection<?> owners, Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(owners) || CollectionHelper.isEmpty(identifiers))
				return null;
			return generate(new Arguments().addOwners(owners).addIdentifiers(identifiers));
		}
		
		/*
		protected Collection<Link> generateFromIdentifiers(Class<?> klass,Collection<String> identifiers,String linkIdentifier) {
			return null;
		}
		
		protected Link generateFromIdentifier(Class<?> klass,String identifier,String linkIdentifier) {
			return new Link().setIdentifier(linkIdentifier).setValue(generateValueFromIdentifier(klass, linkIdentifier));
		}
		
		protected String generateValueFromIdentifier(Class<?> klass,String identifier) {
			throw new RuntimeException("Not yet implemented");
		}
		
		/*
		@Override
		public <ENTITY> Collection<Link> generate(Class<ENTITY> klass,Collection<ENTITY> entities) {
			if(CollectionHelper.isEmpty(entities))
				return null;
			return __generate__(klass,entities);
		}
		
		protected <ENTITY> Collection<Link> __generate__(Class<ENTITY> klass,Collection<ENTITY> entities) {
			return entities.stream().map(entity -> __generate__(entity)).filter(link -> link != null).collect(Collectors.toList());
		}
		
		protected <ENTITY> Link __generate__(ENTITY entity) {
			return null;
		}
		
		@Override
		public <ENTITY> Collection<Link> generate(Class<ENTITY> klass,ENTITY... entities) {
			return generate(klass,ArrayHelper.isEmpty(entities) ? null : CollectionHelper.listOf(Boolean.TRUE, entities));
		}
		*/	
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Collection<Object> owners;
		private Collection<String> identifiers;
		private Map<String,String> valueFormatMap;
		/*
		private Class<?> klass;
		private Collection<String> identifiers;
		private Collection<?> identifiables;
		*/
		
		public Collection<Object> getOwners(Boolean injectIfNull) {
			if(owners == null && Boolean.TRUE.equals(injectIfNull))
				owners = new ArrayList<>();
			return owners;
		}
		
		public Collection<String> getIdentifiers(Boolean injectIfNull) {
			if(identifiers == null && Boolean.TRUE.equals(injectIfNull))
				identifiers = new ArrayList<>();
			return identifiers;
		}
		
		public Map<String,String> getValueFormatMap(Boolean injectIfNull) {
			if(valueFormatMap == null && Boolean.TRUE.equals(injectIfNull))
				valueFormatMap = new HashMap<>();
			return valueFormatMap;
		}
		
		public Arguments addOwners(Collection<?> owners) {
			if(CollectionHelper.isEmpty(owners))
				return this;
			getOwners(Boolean.TRUE).addAll(owners);
			return this;
		}
		
		public Arguments addIdentifiers(Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return this;
			getIdentifiers(Boolean.TRUE).addAll(identifiers);
			return this;
		}
		
		public Arguments addValueFormat(String identifier,String valueFormat) {
			if(StringHelper.isBlank(identifier) || StringHelper.isBlank(valueFormat))
				return this;
			getValueFormatMap(Boolean.TRUE).put(identifier, valueFormat);
			return this;
		}
	}
	
	static LinksGenerator getInstance() {
		return Helper.getInstance(LinksGenerator.class, INSTANCE);
	}
	Value INSTANCE = new Value();
}