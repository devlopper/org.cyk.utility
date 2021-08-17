package org.cyk.utility.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class TransactionResultUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildSuccessMessage() {
		assertBuildSuccessMessage(new TransactionResult(), "Aucune modification");
		assertBuildSuccessMessage(new TransactionResult().setNumberOfCreation(0l), "Aucune modification");
		assertBuildSuccessMessage(new TransactionResult().setTupleName("mesure").setIsTupleNameFeminine(Boolean.TRUE).setNumberOfCreation(1l), "1 mesure créée");
		assertBuildSuccessMessage(new TransactionResult().setTupleName("mesure").setIsTupleNameFeminine(Boolean.TRUE).setNumberOfCreation(2l), "2 mesures créées");
	}
	
	/**/
	
	private static void assertBuildSuccessMessage(TransactionResult transactionResult,String expectedSuccessMessage) {
		assertThat(transactionResult.buildSuccessMessage()).isEqualTo(expectedSuccessMessage);
	}
}
