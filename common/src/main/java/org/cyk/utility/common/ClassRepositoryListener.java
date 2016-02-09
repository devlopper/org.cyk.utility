package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface ClassRepositoryListener {

	Collection<ClassRepositoryListener> COLLECTION = new ArrayList<>();
	
	Boolean canGetOwnedAndDependentFields(ClassField parent,Field field);
	
	public static class Adapter extends BeanAdapter implements Serializable,ClassRepositoryListener {
		private static final long serialVersionUID = -4122987497921232243L;
		
		@Override
		public Boolean canGetOwnedAndDependentFields(ClassField parent,Field field) {return null;}
		
		/**/
		
		public static class Default extends Adapter implements Serializable {
			private static final long serialVersionUID = -5446765876489940336L;
			@Override
			public Boolean canGetOwnedAndDependentFields(ClassField parent,Field field) {
				ClassField previous = parent;
				while(previous!=null && previous.getParent()!=null)
					previous = previous.getParent();
				return !(previous!=null
						|| field.getType().isPrimitive()
						|| field.getType().getName().startsWith("java.") 
						|| (parent!=null && parent.getParent()!=null && field.equals(parent.getParent().getField()))
						);
			}
		}
		
	}
	
}
