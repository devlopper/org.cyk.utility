package org.cyk.utility.test;


import java.sql.SQLException;

import javax.transaction.UserTransaction;

import lombok.extern.java.Log;

@Log
public abstract class Transaction {
	
	private AbstractIntegrationTestJpaBased persistenceIT;
	private UserTransaction transaction;
	private Class<? extends SQLException> exceptionClassExpected;
	 
	public Transaction(AbstractIntegrationTestJpaBased persistenceIT, UserTransaction transaction, Class<? extends SQLException> exceptionClassExpected) {
		super();
		this.persistenceIT = persistenceIT;
		this.transaction = transaction;
		this.exceptionClassExpected = exceptionClassExpected;
	}

	public void run(){ 
		if(transaction==null){
			log.warning("Null transaction will be skipped");
			return;
		}
		try {
			transaction.begin();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		persistenceIT.getEntityManager().joinTransaction();
		_execute_();
		try {
			transaction.commit();
			//Assert.assertTrue(true);
		} catch (Exception exception) {
			if(exceptionClassExpected==null)
				throw new RuntimeException(exception);
			if(exception(exception)!=null)
				throw new RuntimeException();
		}
	}
	
	private Exception exception(Exception exception){
		Exception e = exception;
		while(e!=null && !e.getClass().equals(exceptionClassExpected))
			e = (Exception) e.getCause();
		return e;
	}
	
	public abstract void _execute_();
}
