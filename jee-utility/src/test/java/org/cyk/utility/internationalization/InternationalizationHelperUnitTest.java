package org.cyk.utility.internationalization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationKeyCacheEntryIdentifier__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationKey__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationPhraseFromKeysValues__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationString__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__deriveInternationalizationKeys__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__processInternationalizationStrings__;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import org.cyk.utility.string.Case;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;


public class InternationalizationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void buildInternationalizationKeyCacheEntryIdentifier_string_hi(){
		assertThat(__buildInternationalizationKeyCacheEntryIdentifier__("hi")).isEqualTo("String-hi-");
	}
	
	@Test
	public void buildInternationalizationKeyCacheEntryIdentifier_class_Person(){
		assertThat(__buildInternationalizationKeyCacheEntryIdentifier__(Person.class)).isEqualTo("Class-"+Person.class.getName()+"-");
	}
	
	@Test
	public void buildInternationalizationKeyCacheEntryIdentifier_class_SystemActionCreate(){
		assertThat(__buildInternationalizationKeyCacheEntryIdentifier__(SystemActionCreate.class)).isEqualTo("Class-"+SystemActionCreate.class.getName()+"-");
	}
	
	@Test
	public void buildInternationalizationKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNull(){
		assertThat(__buildInternationalizationKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class))).isEqualTo("SystemAction-Create-");
	}
	
	@Test
	public void buildInternationalizationKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNotNull(){
		assertThat(__buildInternationalizationKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class).setIdentifier("do"))).isEqualTo("SystemAction-do-");
	}
	
	@Test
	public void buildInternationalizationKey_none(){
		assertThat(__buildInternationalizationKey__(null)).isNull();
	}
	
	@Test
	public void buildInternationalizationKey_empty(){
		assertThat(__buildInternationalizationKey__("")).isNull();
	}
	
	@Test
	public void buildInternationalizationKey_hi(){
		assertThat(__buildInternationalizationKey__("hi").getValue()).isEqualTo("hi");
	}
	
	@Test
	public void buildInternationalizationKey__hi(){
		assertThat(__buildInternationalizationKey__("_hi").getValue()).isEqualTo("_.hi");
	}
	
	@Test
	public void buildInternationalizationKey_hi_(){
		assertThat(__buildInternationalizationKey__("hi_").getValue()).isEqualTo("hi._");
	}
	
	@Test
	public void buildInternationalizationKey_camelCase(){
		assertThat(__buildInternationalizationKey__("camelCase").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void buildInternationalizationKey_camelCase01(){
		assertThat(__buildInternationalizationKey__("camelCase01").getValue()).isEqualTo("camel.case.01");
	}
	
	@Test
	public void buildInternationalizationKey_camelCase01ABC(){
		assertThat(__buildInternationalizationKey__("camelCase01ABC").getValue()).isEqualTo("camel.case.01.abc");
	}
	
	@Test
	public void buildInternationalizationKey_camelCase01ABCOk(){
		assertThat(__buildInternationalizationKey__("camelCase01ABCOk").getValue()).isEqualTo("camel.case.01.abc.ok");
	}
	
	@Test
	public void buildInternationalizationKey_camel_dot_case(){
		assertThat(__buildInternationalizationKey__("camel.case").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void deriveInternationalizationKeys_person_type(){
		List<Strings> keys = __deriveInternationalizationKeys__("person.type");
		assertThat(keys.get(0).get()).contains("person","of","type");
	}
	
	@Test
	public void buildInternationalizationKey_systemActionCreate(){
		assertThat(__buildInternationalizationKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildInternationalizationKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildInternationalizationKey__(SystemActionCreate.class,InternationalizationKeyStringType.NOUN).getValue()).isEqualTo("create.__noun__");
		assertThat(__buildInternationalizationKey__(SystemActionCreate.class,InternationalizationKeyStringType.VERB).getValue()).isEqualTo("create.__verb__");
	}
	
	@Test
	public void buildInternationalizationKey_systemActionRead(){
		assertThat(__buildInternationalizationKey__(SystemActionRead.class).getValue()).isEqualTo("read");
		assertThat(__buildInternationalizationKey__(__inject__(SystemActionRead.class)).getValue()).isEqualTo("read");
		assertThat(__buildInternationalizationKey__(__inject__(SystemActionRead.class).setIdentifier("consult")).getValue()).isEqualTo("consult");
	}
	
	@Test
	public void buildInternationalizationKey_myEntity(){
		assertThat(__buildInternationalizationKey__(MyEntity.class).getValue()).isEqualTo("my.entity");
		assertThat(__buildInternationalizationKey__(MyEntityImpl.class).getValue()).isEqualTo("my.entity");
	}
	
	@Test
	public void buildInternationalizationKey_throwableJavaNetUnknownHostException(){
		UnknownHostException exception = new UnknownHostException("host001");
		InternationalizationKey key = __buildInternationalizationKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.unknown.host.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildInternationalizationKey_throwableJavaNetConnectException(){
		ConnectException exception = new ConnectException("host001");
		InternationalizationKey key = __buildInternationalizationKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.connect.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildInternationalizationString_isType_de_xxx_whenKeyXxxDotType(){
		assertThat(__buildInternationalizationString__("xxx.type")).isEqualTo("type de ##??xxx??##");	
	}
	
	@Test
	public void buildInternationalizationString_isSalut_whenKeyIsHi(){
		assertThat(__buildInternationalizationString__("hi")).isEqualTo("salut");
	}
	
	@Test
	public void buildInternationalizationString_isH1H2_whenKeyIsH1H2(){
		assertThat(__buildInternationalizationString__("h1H2")).isEqualTo("##??h1H2??##");
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__("h1H2"))).isEqualTo("##??h.1.h.2??##");	
	}
	
	@Test
	public void buildInternationalizationString_isNom_whenKeyIsFirstName(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__("firstName"))).isEqualTo("nom");
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__("firstName"),null,null,Case.FIRST_CHARACTER_UPPER)).isEqualTo("Nom");
	}
	
	@Test
	public void buildInternationalizationString_isPrenoms_whenKeyIsLastName(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__("lastNames"))).isEqualTo("prénoms");
	}
	
	@Test
	public void buildInternationalizationString_isCreer_whenKeyIsCreate(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(SystemActionCreate.class, null))).isEqualTo("créer");
	}
	
	@Test
	public void buildInternationalizationString_isCreer_whenKeyIsCreateVerb(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(SystemActionCreate.class, InternationalizationKeyStringType.VERB))).isEqualTo("créer");		
	}
	
	@Test
	public void buildInternationalizationString_isCreer_whenKeyIsCreateNoun(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(SystemActionCreate.class, InternationalizationKeyStringType.NOUN))).isEqualTo("création");		
	}
		
	@Test
	public void buildInternationalizationString_isValidation_whenKeyIsProcessValidateNoun(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.NOUN))).isEqualTo("validation");		
	}
	
	@Test
	public void buildInternationalizationString_isValider_whenKeyIsProcessValidateVerb(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.VERB))).isEqualTo("valider");		
	}
	
	@Test
	public void buildInternationalizationString_is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		assertThat(__buildInternationalizationString__("operation.execution.success.summary")).isEqualTo("l'opération a été exécutée avec succès.");	
	}
	
	@Test
	public void buildInternationalizationString_is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		assertThat(__buildInternationalizationString__("service.of.act.not.found",new Object[] {"create","person"},null,null)).isEqualTo("le service de create de person est introuvable");
	}
	
	@Test
	public void buildInternationalizationString_is_l_hote_host001_est_inconnu_whenKeyIsThrowableJavaNetUnknownHostException(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(new UnknownHostException("host001")))).isEqualTo("l'hôte host001 est inconnu");
	}
	
	@Test
	public void buildInternationalizationString_is_la_connexion_a_ete_refusee_whenKeyIsThrowableJavaNetConnectException(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(new ConnectException("refusé")))).isEqualTo("la connexion a été refusée");
	}
	
	@Test
	public void buildInternationalizationString_is_mon_message_whenKeyIsThrowableJavaLangRuntimeException(){
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(new RuntimeException("mon message")))).isEqualTo("mon message");
	}
	
	@Test
	public void buildInternationalizationString_is_le_service_de_lecture_de_personne_est_introuvable_whenKeyIsThrowableServiceNotFoundException(){
		ServiceNotFoundException exception = __inject__(ServiceNotFoundException.class);
		exception.setSystemAction(__inject__(SystemActionRead.class).setEntityClass(Person.class));
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(exception))).isEqualTo("le service de lecture de personne est introuvable");
	}
	
	@Test
	public void buildInternationalizationString_is_l_entite_personne_ayant_le_code_abc_n_existe_pas_whenKeyIsThrowableServiceNotFoundException(){
		EntityNotFoundException exception = __inject__(EntityNotFoundException.class);
		SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
		systemActionRead.setEntityClass(Person.class);
		systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add("abc");
		exception.setSystemAction(systemActionRead);
		assertThat(__buildInternationalizationString__(__buildInternationalizationKey__(exception))).isEqualTo("l'entité personne ayant le code abc n'existe pas");
	}
	
	@Test
	public void processInternationalizationString(){
		InternationalizationString internalizationString = new InternationalizationString();
		internalizationString.setKey(new InternationalizationKey().setValue("hi"));
		assertThat(internalizationString.getValue()).isNull();;
		__processInternationalizationStrings__(internalizationString);
		assertThat(internalizationString.getValue()).isEqualTo("salut");
	}
	
	@Test
	public void buildInternationalizationPhrase(){
		assertThat(__buildInternationalizationPhraseFromKeysValues__("type","of","person")).isEqualTo("type de personne");
	}
	
	/**/
	
	public static class Person {
		
	}
	
	public static interface MyEntity {
		
	}
	
	public static class MyEntityImpl implements MyEntity {
		
	}
}
