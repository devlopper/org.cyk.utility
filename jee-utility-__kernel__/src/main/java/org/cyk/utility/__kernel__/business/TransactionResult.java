package org.cyk.utility.__kernel__.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TransactionResult implements Serializable {
	
	private Long startingTime = System.currentTimeMillis(),stoppingTime;
	private Long numberOfCreation;
	private Long numberOfUpdate;
	private Long numberOfDeletion;
	private String tupleName;
	
	public TransactionResult setNumberOfCreationFromSavables(Collection<?> savables) {
		if(CollectionHelper.isEmpty(savables))
			this.numberOfCreation = 0l;
		else
			this.numberOfCreation = savables.stream().filter(x -> FieldHelper.readSystemIdentifier(x) == null).count();
		return this;
	}
	
	public TransactionResult setNumberOfUpdateFromSavables(Collection<?> savables) {
		if(CollectionHelper.isEmpty(savables))
			this.numberOfUpdate = 0l;
		else
			this.numberOfUpdate = savables.stream().filter(x -> FieldHelper.readSystemIdentifier(x) != null).count();
		return this;
	}
	
	public TransactionResult setFromSavables(Collection<?> savables) {
		setNumberOfCreationFromSavables(savables);
		setNumberOfUpdateFromSavables(savables);
		return this;
	}
	
	public TransactionResult setNumberOfDeletionFromCollection(Collection<?> collection) {
		this.numberOfDeletion = Long.valueOf(CollectionHelper.getSize(collection));
		return this;
	}

	/**/
	
	public TransactionResult add(TransactionResult result) {
		numberOfCreation = NumberHelper.getLong(NumberHelper.add(numberOfCreation,result.numberOfCreation));
		numberOfUpdate = NumberHelper.getLong(NumberHelper.add(numberOfUpdate,result.numberOfUpdate));
		numberOfDeletion = NumberHelper.getLong(NumberHelper.add(numberOfDeletion,result.numberOfDeletion));
		return this;
	}
	
	/**/
	
	public TransactionResult log(Class<?> klass,Long duration) {
		LogHelper.logInfo(String.format("%s , %s et %s enregistrement(s) respectivement créé(s) , mis à jour et supprimé(s) en %s", numberOfCreation,numberOfUpdate,numberOfDeletion
				,duration), klass);
		return this;
	}
	
	public TransactionResult log(Class<?> klass) {
		Long duration = System.currentTimeMillis() - startingTime;
		log(klass,duration);
		return this;
	}
}