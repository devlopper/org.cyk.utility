package org.cyk.utility.internationalization;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Case;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		//DependencyInjection.inject(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		//__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		//__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);//FIXME To solve different object reference used , second one not initialzed
	}
	
	@Test
	public void isSalut_whenKeyIsHi(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKey("hi").execute().getOutput());	
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKeyValue("hi").execute().getOutput());		
	}
	
	@Test
	public void isH1H2_whenKeyIsH1H2(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("##??h1H2??##", __inject__(InternalizationStringBuilder.class).setKey("h1H2").execute().getOutput());	
		assertionHelper.assertEquals("##??h.1.h.2??##", __inject__(InternalizationStringBuilder.class).setKeyValue("h1H2").execute().getOutput());		
	}
	
	@Test
	public void isNom_whenKeyIsFirstName(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("nom", __inject__(InternalizationStringBuilder.class).setKeyValue("firstName").execute().getOutput());	
		assertionHelper.assertEquals("Nom", __inject__(InternalizationStringBuilder.class).setKeyValue("firstName").setCase(Case.FIRST_CHARACTER_UPPER).execute().getOutput());	
	}
	
	@Test
	public void isPrenoms_whenKeyIsLastName(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("prénoms", __inject__(InternalizationStringBuilder.class).setKeyValue("lastNames").execute().getOutput());		
	}
	
	@Test
	public void isCreer_whenKeyIsCreate(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("créer", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).execute().getOutput());		
	}
	
	@Test
	public void isCreer_whenKeyIsCreateVerb(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("créer", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isTraitement_whenKeyIsProcessNoun(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("traitement", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionProcess.class).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void isTraiter_whenKeyIsProcessVerb(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("traiter", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionProcess.class).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isValidation_whenKeyIsProcessValidateNoun(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("validation", __inject__(InternalizationStringBuilder.class).setKeyValue(__inject__(SystemActionProcess.class).setIdentifier("validate")).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void isValider_whenKeyIsProcessValidateVerb(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("valider", __inject__(InternalizationStringBuilder.class).setKeyValue(__inject__(SystemActionProcess.class).setIdentifier("validate")).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isCreation_whenKeyIsCreateNoun(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("création", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("l'opération a été exécutée avec succès.", __inject__(InternalizationStringBuilder.class).setKeyValue("operation.execution.success.summary").execute().getOutput());	
		assertionHelper.assertEquals("L'opération a été exécutée avec succès.", __inject__(InternalizationStringBuilder.class).setKeyValue("operation.execution.success.summary").setCase(Case.FIRST_CHARACTER_UPPER).execute().getOutput());	
	}
	
	@Test
	public void is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("le service de create de person est introuvable", __inject__(InternalizationStringBuilder.class).setKeyValue("service.of.act.not.found")
				.setParameters(__inject__(CollectionHelper.class).instanciate("create","person")).execute().getOutput());	
	}
}
