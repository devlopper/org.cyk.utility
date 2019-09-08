package org.cyk.utility.internationalization;

import java.net.ConnectException;
import java.net.UnknownHostException;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Case;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class InternalizationStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isType_de_xxx_whenKeyXxxDotType(){
		assertionHelper.assertEquals("type de ##??xxx??##", __inject__(InternalizationStringBuilder.class).setKey("xxx.type").execute().getOutput());		
	}
	
	@Test
	public void isSalut_whenKeyIsHi(){
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKey("hi").execute().getOutput());	
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKeyValue("hi").execute().getOutput());		
	}
	
	@Test
	public void isH1H2_whenKeyIsH1H2(){
		assertionHelper.assertEquals("##??h1H2??##", __inject__(InternalizationStringBuilder.class).setKey("h1H2").execute().getOutput());	
		assertionHelper.assertEquals("##??h.1.h.2??##", __inject__(InternalizationStringBuilder.class).setKeyValue("h1H2").execute().getOutput());		
	}
	
	@Test
	public void isNom_whenKeyIsFirstName(){
		assertionHelper.assertEquals("nom", __inject__(InternalizationStringBuilder.class).setKeyValue("firstName").execute().getOutput());	
		assertionHelper.assertEquals("Nom", __inject__(InternalizationStringBuilder.class).setKeyValue("firstName").setCase(Case.FIRST_CHARACTER_UPPER).execute().getOutput());	
	}
	
	@Test
	public void isPrenoms_whenKeyIsLastName(){
		assertionHelper.assertEquals("prénoms", __inject__(InternalizationStringBuilder.class).setKeyValue("lastNames").execute().getOutput());		
	}
	
	@Test
	public void isCreer_whenKeyIsCreate(){
		assertionHelper.assertEquals("créer", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).execute().getOutput());		
	}
	
	@Test
	public void isCreer_whenKeyIsCreateVerb(){
		assertionHelper.assertEquals("créer", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isTraitement_whenKeyIsProcessNoun(){
		assertionHelper.assertEquals("traitement", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionProcess.class).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void isTraiter_whenKeyIsProcessVerb(){
		assertionHelper.assertEquals("traiter", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionProcess.class).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isValidation_whenKeyIsProcessValidateNoun(){
		assertionHelper.assertEquals("validation", __inject__(InternalizationStringBuilder.class).setKeyValue(__inject__(SystemActionProcess.class).setIdentifier("validate")).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void isValider_whenKeyIsProcessValidateVerb(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("valider", __inject__(InternalizationStringBuilder.class).setKeyValue(__inject__(SystemActionProcess.class).setIdentifier("validate")).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput());		
	}
	
	@Test
	public void isCreation_whenKeyIsCreateNoun(){
		assertionHelper.assertEquals("création", __inject__(InternalizationStringBuilder.class).setKeyValue(SystemActionCreate.class).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());		
	}
	
	@Test
	public void is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		assertionHelper.assertEquals("l'opération a été exécutée avec succès.", __inject__(InternalizationStringBuilder.class).setKeyValue("operation.execution.success.summary").execute().getOutput());	
		assertionHelper.assertEquals("L'opération a été exécutée avec succès.", __inject__(InternalizationStringBuilder.class).setKeyValue("operation.execution.success.summary").setCase(Case.FIRST_CHARACTER_UPPER).execute().getOutput());	
	}
	
	@Test
	public void is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		assertionHelper.assertEquals("le service de create de person est introuvable", __inject__(InternalizationStringBuilder.class).setKeyValue("service.of.act.not.found")
				.setParameters(__inject__(CollectionHelper.class).instanciate("create","person")).execute().getOutput());	
	}
	
	@Test
	public void is_l_hote_host001_est_inconnu_whenKeyIsThrowableJavaNetUnknownHostException(){
		assertionHelper.assertEquals("l'hôte host001 est inconnu", __inject__(InternalizationStringBuilder.class).setKeyValue(new UnknownHostException("host001"))
				.execute().getOutput());	
	}
	
	@Test
	public void is_la_connexion_a_ete_refusee_whenKeyIsThrowableJavaNetConnectException(){
		assertionHelper.assertEquals("la connexion a été refusée", __inject__(InternalizationStringBuilder.class).setKeyValue(new ConnectException("refusé"))
				.execute().getOutput());	
	}
	
	@Test
	public void is_mon_message_whenKeyIsThrowableJavaLangRuntimeException(){
		assertionHelper.assertEquals("mon message", __inject__(InternalizationStringBuilder.class).setKeyValue(new RuntimeException("mon message"))
				.execute().getOutput());	
	}
	
	@Test
	public void is_le_service_de_lecture_de_personne_est_introuvable_whenKeyIsThrowableServiceNotFoundException(){
		ServiceNotFoundException exception = __inject__(ServiceNotFoundException.class);
		exception.setSystemAction(__inject__(SystemActionRead.class).setEntityClass(Person.class));
		assertionHelper.assertEquals("le service de lecture de personne est introuvable", __inject__(InternalizationStringBuilder.class).setKeyValue(exception)
				.execute().getOutput());	
	}
	
	@Test
	public void is_l_entite_personne_ayant_le_code_abc_n_existe_pas_whenKeyIsThrowableServiceNotFoundException(){
		EntityNotFoundException exception = __inject__(EntityNotFoundException.class);
		SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
		systemActionRead.setEntityClass(Person.class);
		systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add("abc");
		exception.setSystemAction(systemActionRead);
		assertionHelper.assertEquals("l'entité personne ayant le code abc n'existe pas", __inject__(InternalizationStringBuilder.class).setKeyValue(exception)
				.execute().getOutput());	
	}
	
	/**/
	
	public static class Person {
		
	}
}
