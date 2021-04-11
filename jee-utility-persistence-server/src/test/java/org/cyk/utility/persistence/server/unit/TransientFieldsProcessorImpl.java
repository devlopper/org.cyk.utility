package org.cyk.utility.persistence.server.unit;
import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.DataType;

@Test
public class TransientFieldsProcessorImpl extends org.cyk.utility.persistence.server.TransientFieldsProcessorImpl implements Serializable {

	@Override
	protected void __process__(Class<?> klass,Collection<?> objects, Collection<String> fieldsNames) {
		if(DataType.class.equals(klass))
			processFiles(CollectionHelper.cast(DataType.class, objects),fieldsNames);
		else
			super.__process__(klass,objects, fieldsNames);
	}
	
	public void processFiles(Collection<DataType> collection,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(DataType.FIELD_CODE_AND_NAME.equals(fieldName)) {
				Collection<Object[]> arrays = EntityManagerGetter.getInstance().get().createQuery("select t.identifier,t.code,t.name from DataType t where t.identifier in :identifiers")
						.setParameter("identifiers", FieldHelper.readSystemIdentifiersAsStrings(collection)).getResultList();
				if(CollectionHelper.isNotEmpty(arrays)) {
					for(DataType dataType : collection) {
						for(Object[] array : arrays) {
							if(array[0].equals(dataType.getIdentifier()))
								dataType.setCodeAndName(array[1]+" "+array[2]);
						}
					}
				}
			}
		}		
	}
}