package org.cyk.utility.common.test;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

@Getter @Setter
public class ArchiveBuilder {
	
	private String packageModelInfix = "model";
	private String packageApiInfix = "api";
	private String packageImplInfix = "impl";
	
	private String serviceImplSuffix = "Impl";
	
	private ServiceLayer persistenceServiceLayer = new ServiceLayer("persistence", "Dao");
	private ServiceLayer businessServiceLayer = new ServiceLayer("business", "Business");
	
	private JavaArchive archive;
	
	private String jpaPersistenceXml="test-persistence.xml";
	
	public ArchiveBuilder create() {
		archive = ShrinkWrap.create(JavaArchive.class).addAsResource(jpaPersistenceXml,"META-INF/persistence.xml").addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return this;
	}
	
	public ArchiveBuilder model(Class<?>[] theModelClasses,ServiceLayer...layers){
		Set<String> set = new HashSet<>();
		String model = "."+packageModelInfix+".";
		for(Class<?> aModelClass : theModelClasses){
			set.add(aModelClass.getName());
			if(layers!=null){
				if(StringUtils.contains(aModelClass.getName(), packageModelInfix)){
					for(ServiceLayer layer : layers){
						set.add(StringUtils.replaceOnce(aModelClass.getName(),model,"."+layer.packageInfix+"."+packageApiInfix+".")+layer.metaClassSuffix);
						set.add(StringUtils.replaceOnce(aModelClass.getName(),model,"."+layer.packageInfix+"."+packageImplInfix+".")+layer.metaClassSuffix+serviceImplSuffix);
					}		
				}
			}
		}
		for(String className : set)
			try {
				archive.addClass(className);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		return this;
	}
	
	public ArchiveBuilder persistence(Class<?>[] aModelClass){
		return model(aModelClass, persistenceServiceLayer);
	}
	
	public ArchiveBuilder business(Class<?>[] aModelClass){
		return model(aModelClass, persistenceServiceLayer,businessServiceLayer);
	}
	
	public ArchiveBuilder addClasses(Class<?>... classes) throws IllegalArgumentException {
		archive.addClasses(classes);
		return this;
	}

	public ArchiveBuilder addApi(Class<?>... classes){
		for(Class<?> clazz : classes){
			archive.addClass(clazz);
			archive.addClass(StringUtils.replaceOnce(clazz.getName(),"."+packageApiInfix+".","."+packageImplInfix+".")+serviceImplSuffix);
		}
		
		return this;
	}
	

	@Getter @AllArgsConstructor
	public static class ServiceLayer{
		private String packageInfix;
		private String metaClassSuffix;
	}
}
