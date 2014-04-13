package org.cyk.utility.common.test;


public class ArchiveBuilder {
/*
	public enum Layer{MODEL,DAO,SERVICE}
	
	private Collection<String> classes = new LinkedHashSet<>();
	
	public ArchiveBuilder model(Class<?> clazz,Layer layer){
		classes.add(clazz.getName());
		for(int i=layer.ordinal()-1;i>0;i--){
			switch(Layer.values()[i]){
			case MODEL:classes.add(clazz);break;
			}
		}
		
		
		return this;
	}
	
	public static Archive<?> createDeployment(Class<?>...classes){
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClass(PersistenceService.class).addClass(AbstractPersistenceService.class).addClass(GenericDao.class).addClass(GenericDaoImpl.class)
				.addClasses(classes)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}*/
	
}
