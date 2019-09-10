package org.cyk.utility.internationalization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildKeyCacheEntryIdentifier__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildKey__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildPhraseFromKeysValues__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildString__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__deriveKeys__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__processStrings__;

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
	public void buildKeyCacheEntryIdentifier_string_hi(){
		assertThat(__buildKeyCacheEntryIdentifier__("hi")).isEqualTo("String-hi-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_class_Person(){
		assertThat(__buildKeyCacheEntryIdentifier__(Person.class)).isEqualTo("Class-"+Person.class.getName()+"-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_class_SystemActionCreate(){
		assertThat(__buildKeyCacheEntryIdentifier__(SystemActionCreate.class)).isEqualTo("Class-"+SystemActionCreate.class.getName()+"-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNull(){
		assertThat(__buildKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class))).isEqualTo("SystemAction-Create-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNotNull(){
		assertThat(__buildKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class).setIdentifier("do"))).isEqualTo("SystemAction-do-");
	}
	
	@Test
	public void buildKey_none(){
		assertThat(__buildKey__(null)).isNull();
	}
	
	@Test
	public void buildKey_empty(){
		assertThat(__buildKey__("")).isNull();
	}
	
	@Test
	public void buildKey_hi(){
		assertThat(__buildKey__("hi").getValue()).isEqualTo("hi");
	}
	
	@Test
	public void buildKey__hi(){
		assertThat(__buildKey__("_hi").getValue()).isEqualTo("_.hi");
	}
	
	@Test
	public void buildKey_hi_(){
		assertThat(__buildKey__("hi_").getValue()).isEqualTo("hi._");
	}
	
	@Test
	public void buildKey_camelCase(){
		assertThat(__buildKey__("camelCase").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void buildKey_camelCase01(){
		assertThat(__buildKey__("camelCase01").getValue()).isEqualTo("camel.case.01");
	}
	
	@Test
	public void buildKey_camelCase01ABC(){
		assertThat(__buildKey__("camelCase01ABC").getValue()).isEqualTo("camel.case.01.abc");
	}
	
	@Test
	public void buildKey_camelCase01ABCOk(){
		assertThat(__buildKey__("camelCase01ABCOk").getValue()).isEqualTo("camel.case.01.abc.ok");
	}
	
	@Test
	public void buildKey_camel_dot_case(){
		assertThat(__buildKey__("camel.case").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void deriveKeys_person_type(){
		List<Strings> keys = __deriveKeys__("person.type");
		assertThat(keys.get(0).get()).contains("person","of","type");
	}
	
	@Test
	public void buildKey_systemActionCreate(){
		assertThat(__buildKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildKey__(SystemActionCreate.class,InternationalizationKeyStringType.NOUN).getValue()).isEqualTo("create.__noun__");
		assertThat(__buildKey__(SystemActionCreate.class,InternationalizationKeyStringType.VERB).getValue()).isEqualTo("create.__verb__");
	}
	
	@Test
	public void buildKey_systemActionRead(){
		assertThat(__buildKey__(SystemActionRead.class).getValue()).isEqualTo("read");
		assertThat(__buildKey__(__inject__(SystemActionRead.class)).getValue()).isEqualTo("read");
		assertThat(__buildKey__(__inject__(SystemActionRead.class).setIdentifier("consult")).getValue()).isEqualTo("consult");
	}
	
	@Test
	public void buildKey_myEntity(){
		assertThat(__buildKey__(MyEntity.class).getValue()).isEqualTo("my.entity");
		assertThat(__buildKey__(MyEntityImpl.class).getValue()).isEqualTo("my.entity");
	}
	
	@Test
	public void buildKey_throwableJavaNetUnknownHostException(){
		UnknownHostException exception = new UnknownHostException("host001");
		InternationalizationKey key = __buildKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.unknown.host.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildKey_throwableJavaNetConnectException(){
		ConnectException exception = new ConnectException("host001");
		InternationalizationKey key = __buildKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.connect.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildString_isType_de_xxx_whenKeyXxxDotType(){
		assertThat(__buildString__("xxx.type")).isEqualTo("type de ##??xxx??##");	
	}
	
	@Test
	public void buildString_isSalut_whenKeyIsHi(){
		assertThat(__buildString__("hi")).isEqualTo("salut");
	}
	
	@Test
	public void buildString_isH1H2_whenKeyIsH1H2(){
		assertThat(__buildString__("h1H2")).isEqualTo("##??h1H2??##");
		assertThat(__buildString__(__buildKey__("h1H2"))).isEqualTo("##??h.1.h.2??##");	
	}
	
	@Test
	public void buildString_isNom_whenKeyIsFirstName(){
		assertThat(__buildString__(__buildKey__("firstName"))).isEqualTo("nom");
		assertThat(__buildString__(__buildKey__("firstName"),null,null,Case.FIRST_CHARACTER_UPPER)).isEqualTo("Nom");
	}
	
	@Test
	public void buildString_isPrenoms_whenKeyIsLastName(){
		assertThat(__buildString__(__buildKey__("lastNames"))).isEqualTo("prénoms");
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreate(){
		assertThat(__buildString__(__buildKey__(SystemActionCreate.class, null))).isEqualTo("créer");
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreateVerb(){
		assertThat(__buildString__(__buildKey__(SystemActionCreate.class, InternationalizationKeyStringType.VERB))).isEqualTo("créer");		
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreateNoun(){
		assertThat(__buildString__(__buildKey__(SystemActionCreate.class, InternationalizationKeyStringType.NOUN))).isEqualTo("création");		
	}
		
	@Test
	public void buildString_isValidation_whenKeyIsProcessValidateNoun(){
		assertThat(__buildString__(__buildKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.NOUN))).isEqualTo("validation");		
	}
	
	@Test
	public void buildString_isValider_whenKeyIsProcessValidateVerb(){
		assertThat(__buildString__(__buildKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.VERB))).isEqualTo("valider");		
	}
	
	@Test
	public void buildString_is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		assertThat(__buildString__("operation.execution.success.summary")).isEqualTo("l'opération a été exécutée avec succès.");	
	}
	
	@Test
	public void buildString_is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		assertThat(__buildString__("service.of.act.not.found",new Object[] {"create","person"},null,null)).isEqualTo("le service de create de person est introuvable");
	}
	
	@Test
	public void buildString_is_l_hote_host001_est_inconnu_whenKeyIsThrowableJavaNetUnknownHostException(){
		assertThat(__buildString__(__buildKey__(new UnknownHostException("host001")))).isEqualTo("l'hôte host001 est inconnu");
	}
	
	@Test
	public void buildString_is_la_connexion_a_ete_refusee_whenKeyIsThrowableJavaNetConnectException(){
		assertThat(__buildString__(__buildKey__(new ConnectException("refusé")))).isEqualTo("la connexion a été refusée");
	}
	
	@Test
	public void buildString_is_mon_message_whenKeyIsThrowableJavaLangRuntimeException(){
		assertThat(__buildString__(__buildKey__(new RuntimeException("mon message")))).isEqualTo("mon message");
	}
	
	@Test
	public void buildString_is_le_service_de_lecture_de_personne_est_introuvable_whenKeyIsThrowableServiceNotFoundException(){
		ServiceNotFoundException exception = __inject__(ServiceNotFoundException.class);
		exception.setSystemAction(__inject__(SystemActionRead.class).setEntityClass(Person.class));
		assertThat(__buildString__(__buildKey__(exception))).isEqualTo("le service de lecture de personne est introuvable");
	}
	
	@Test
	public void buildString_is_l_entite_personne_ayant_le_code_abc_n_existe_pas_whenKeyIsThrowableServiceNotFoundException(){
		EntityNotFoundException exception = __inject__(EntityNotFoundException.class);
		SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
		systemActionRead.setEntityClass(Person.class);
		systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add("abc");
		exception.setSystemAction(systemActionRead);
		assertThat(__buildString__(__buildKey__(exception))).isEqualTo("l'entité personne ayant le code abc n'existe pas");
	}
	
	@Test
	public void processString(){
		InternationalizationString internalizationString = new InternationalizationString();
		internalizationString.setKey(new InternationalizationKey().setValue("hi"));
		assertThat(internalizationString.getValue()).isNull();;
		__processStrings__(internalizationString);
		assertThat(internalizationString.getValue()).isEqualTo("salut");
	}
	
	@Test
	public void buildPhrase(){
		assertThat(__buildPhraseFromKeysValues__("type","of","person")).isEqualTo("type de personne");
	}
	
	/**/
	
	public static class Person {
		
	}
	
	public static interface MyEntity {
		
	}
	
	public static class MyEntityImpl implements MyEntity {
		
	}
}
