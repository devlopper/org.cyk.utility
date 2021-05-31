package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TransactionResult implements Serializable {
	
	private String name;
	private Long startingTime = System.currentTimeMillis(),stoppingTime;
	private Long numberOfCreation;
	private Long numberOfUpdate;
	private Long numberOfDeletion;
	private String tupleName;
	private Boolean isTupleNameFeminine;
	private SuccessMessageBuilder successMessageBuilder;
	
	public TransactionResult incrementNumberOfCreation(Long numberOfCreation) {
		this.numberOfCreation = NumberHelper.getLong(NumberHelper.add(this.numberOfCreation,numberOfCreation));
		return this;
	}
	
	public TransactionResult incrementNumberOfUpdate(Long numberOfUpdate) {
		this.numberOfUpdate = NumberHelper.getLong(NumberHelper.add(this.numberOfUpdate,numberOfUpdate));
		return this;
	}
	
	public TransactionResult incrementNumberOfDeletion(Long numberOfDeletion) {
		this.numberOfDeletion = NumberHelper.getLong(NumberHelper.add(this.numberOfDeletion,numberOfDeletion));
		return this;
	}
	
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
		if(result == null)
			return this;
		numberOfCreation = NumberHelper.getLong(NumberHelper.add(numberOfCreation,result.numberOfCreation));
		numberOfUpdate = NumberHelper.getLong(NumberHelper.add(numberOfUpdate,result.numberOfUpdate));
		numberOfDeletion = NumberHelper.getLong(NumberHelper.add(numberOfDeletion,result.numberOfDeletion));
		return this;
	}
	
	/**/
	
	public TransactionResult log(Class<?> klass,Long duration) {
		LogHelper.logInfo(String.format("%s exécuté(e) en %s. %s => %s(C) %s(U) %s(D)."
				, name,TimeHelper.formatDuration(duration),tupleName,ValueHelper.defaultToIfNull(numberOfCreation,0),ValueHelper.defaultToIfNull(numberOfUpdate,0)
				,ValueHelper.defaultToIfNull(numberOfDeletion,0)), klass);
		return this;
	}
	
	public TransactionResult log(Class<?> klass) {
		Long duration = System.currentTimeMillis() - startingTime;
		log(klass,duration);
		return this;
	}
	
	public String buildSuccessMessage() {
		return (successMessageBuilder == null ? SuccessMessageBuilder.AbstractImpl.Default.INSTANCE : successMessageBuilder).build(this);
	}
	
	/**/
	
	public static interface SuccessMessageBuilder {
		String build(TransactionResult transactionResult);
		
		public static abstract class AbstractImpl extends AbstractObject implements SuccessMessageBuilder,Serializable {
			@Override
			public String build(TransactionResult transactionResult) {
				if(transactionResult == null)
					return null;
				Collection<String> strings = null;
				strings = add(transactionResult, Action.CREATE, strings);
				strings = add(transactionResult, Action.UPDATE, strings);
				strings = add(transactionResult, Action.DELETE, strings);
				return CollectionHelper.isEmpty(strings) ? null : StringHelper.concatenate(strings, "\n\r");
			}
			
			protected Collection<String> add(TransactionResult transactionResult,Action action,Collection<String> strings) {
				if(transactionResult == null || action == null)
					return strings;
				Long number = Action.CREATE.equals(action) ? transactionResult.getNumberOfCreation() : (Action.UPDATE.equals(action) ? transactionResult.getNumberOfUpdate() : transactionResult.getNumberOfDeletion());
				if(!NumberHelper.isGreaterThanZero(number))
					return strings;				
				if(strings == null)
					strings = new ArrayList<>();
				strings.add(String.format(getFormat(), number,getSubject(transactionResult.getTupleName(),number),getVerb(action, transactionResult.isTupleNameFeminine, number)));
				return strings;
			}
			
			protected String getSubject(String name,Long count) {
				String subject = name;
				if(NumberHelper.isGreaterThanOne(count))
					subject = subject + "s";
				return subject;
			}
			
			protected String getVerb(Action action,Boolean isFeminine,Long count) {
				String verb = null;
				if(Action.CREATE.equals(action))
					verb = "créé";
				else if(Action.UPDATE.equals(action))
					verb = "modifié";
				else if(Action.DELETE.equals(action))
					verb = "supprimé";
				if(Boolean.TRUE.equals(isFeminine))
					verb = verb + "e";
				if(NumberHelper.isGreaterThanOne(count))
					verb = verb + "s";
				return verb;
			}
			
			protected String getFormat() {
				return FORMAT;
			}
			
			private static final String FORMAT = "%s %s %s";
			
			/**/
			
			public static class Default extends AbstractImpl implements Serializable {
				public static final SuccessMessageBuilder INSTANCE = new Default();
			}
		}
	}
}