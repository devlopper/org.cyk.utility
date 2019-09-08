package org.cyk.utility.internationalization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.internationalization.InternalizationHelperImpl.__buildInternalizationKeyCacheEntryIdentifier__;
import static org.cyk.utility.internationalization.InternalizationHelperImpl.__buildInternalizationPhraseFromKeysValues__;
import static org.cyk.utility.internationalization.InternalizationHelperImpl.__buildInternalizationKey__;
import static org.cyk.utility.internationalization.InternalizationHelperImpl.__buildInternalizationString__;
import static org.cyk.utility.internationalization.InternalizationHelperImpl.__deriveInternalizationKeys__;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import org.cyk.utility.internationalization.InternalizationHelperImpl.InternalizationKey;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;


public class InternalizationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void buildInternalizationKeyCacheEntryIdentifier_string_hi(){
		assertThat(__buildInternalizationKeyCacheEntryIdentifier__("hi")).isEqualTo("String-hi-");
	}
	
	@Test
	public void buildInternalizationKeyCacheEntryIdentifier_class_Person(){
		assertThat(__buildInternalizationKeyCacheEntryIdentifier__(Person.class)).isEqualTo("Class-"+Person.class.getName()+"-");
	}
	
	@Test
	public void buildInternalizationKeyCacheEntryIdentifier_class_SystemActionCreate(){
		assertThat(__buildInternalizationKeyCacheEntryIdentifier__(SystemActionCreate.class)).isEqualTo("Class-"+SystemActionCreate.class.getName()+"-");
	}
	
	@Test
	public void buildInternalizationKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNull(){
		assertThat(__buildInternalizationKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class))).isEqualTo("SystemAction-Create-");
	}
	
	@Test
	public void buildInternalizationKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNotNull(){
		assertThat(__buildInternalizationKeyCacheEntryIdentifier__(__inject__(SystemActionCreate.class).setIdentifier("do"))).isEqualTo("SystemAction-do-");
	}
	
	@Test
	public void buildInternalizationKey_none(){
		assertThat(__buildInternalizationKey__(null)).isNull();
	}
	
	@Test
	public void buildInternalizationKey_empty(){
		assertThat(__buildInternalizationKey__("")).isNull();
	}
	
	@Test
	public void buildInternalizationKey_hi(){
		assertThat(__buildInternalizationKey__("hi").getValue()).isEqualTo("hi");
	}
	
	@Test
	public void buildInternalizationKey__hi(){
		assertThat(__buildInternalizationKey__("_hi").getValue()).isEqualTo("_.hi");
	}
	
	@Test
	public void buildInternalizationKey_hi_(){
		assertThat(__buildInternalizationKey__("hi_").getValue()).isEqualTo("hi._");
	}
	
	@Test
	public void buildInternalizationKey_camelCase(){
		assertThat(__buildInternalizationKey__("camelCase").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void buildInternalizationKey_camelCase01(){
		assertThat(__buildInternalizationKey__("camelCase01").getValue()).isEqualTo("camel.case.01");
	}
	
	@Test
	public void buildInternalizationKey_camelCase01ABC(){
		assertThat(__buildInternalizationKey__("camelCase01ABC").getValue()).isEqualTo("camel.case.01.abc");
	}
	
	@Test
	public void buildInternalizationKey_camelCase01ABCOk(){
		assertThat(__buildInternalizationKey__("camelCase01ABCOk").getValue()).isEqualTo("camel.case.01.abc.ok");
	}
	
	@Test
	public void buildInternalizationKey_camel_dot_case(){
		assertThat(__buildInternalizationKey__("camel.case").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void deriveInternalizationKeys_person_type(){
		List<Strings> keys = __deriveInternalizationKeys__("person.type");
		assertThat(keys.get(0).get()).contains("person","of","type");
	}
	
	@Test
	public void buildInternalizationKey_systemActionCreate(){
		assertThat(__buildInternalizationKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildInternalizationKey__(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(__buildInternalizationKey__(SystemActionCreate.class,InternalizationKeyStringType.NOUN).getValue()).isEqualTo("create.__noun__");
		assertThat(__buildInternalizationKey__(SystemActionCreate.class,InternalizationKeyStringType.VERB).getValue()).isEqualTo("create.__verb__");
	}
	
	@Test
	public void buildInternalizationKey_systemActionRead(){
		assertThat(__buildInternalizationKey__(SystemActionRead.class).getValue()).isEqualTo("read");
		assertThat(__buildInternalizationKey__(__inject__(SystemActionRead.class)).getValue()).isEqualTo("read");
		assertThat(__buildInternalizationKey__(__inject__(SystemActionRead.class).setIdentifier("consult")).getValue()).isEqualTo("consult");
	}
	
	@Test
	public void buildInternalizationKey_myEntity(){
		assertThat(__buildInternalizationKey__(MyEntity.class).getValue()).isEqualTo("my.entity");
		assertThat(__buildInternalizationKey__(MyEntityImpl.class).getValue()).isEqualTo("my.entity");
	}
	
	@Test
	public void buildInternalizationKey_throwableJavaNetUnknownHostException(){
		UnknownHostException exception = new UnknownHostException("host001");
		InternalizationKey key = __buildInternalizationKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.unknown.host.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildInternalizationKey_throwableJavaNetConnectException(){
		ConnectException exception = new ConnectException("host001");
		InternalizationKey key = __buildInternalizationKey__(exception);
		assertThat(key.getValue()).isEqualTo("java.net.connect.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildInternalizationString_isType_de_xxx_whenKeyXxxDotType(){
		assertThat(__buildInternalizationString__("xxx.type")).isEqualTo("type de ##??xxx??##");	
	}
	
	@Test
	public void buildInternalizationString_isSalut_whenKeyIsHi(){
		assertThat(__buildInternalizationString__("hi")).isEqualTo("salut");
	}
	
	@Test
	public void buildInternalizationString_isH1H2_whenKeyIsH1H2(){
		assertThat(__buildInternalizationString__("h1H2")).isEqualTo("##??h1H2??##");
		assertThat(__buildInternalizationString__(__buildInternalizationKey__("h1H2"))).isEqualTo("##??h.1.h.2??##");	
	}
	
	@Test
	public void buildInternalizationString_isNom_whenKeyIsFirstName(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__("firstName"))).isEqualTo("nom");
		assertThat(__buildInternalizationString__(__buildInternalizationKey__("firstName"),null,null,Case.FIRST_CHARACTER_UPPER)).isEqualTo("Nom");
	}
	
	@Test
	public void buildInternalizationString_isPrenoms_whenKeyIsLastName(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__("lastNames"))).isEqualTo("prénoms");
	}
	
	@Test
	public void buildInternalizationString_isCreer_whenKeyIsCreate(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(SystemActionCreate.class, null))).isEqualTo("créer");
	}
	
	@Test
	public void buildInternalizationString_isCreer_whenKeyIsCreateVerb(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(SystemActionCreate.class, InternalizationKeyStringType.VERB))).isEqualTo("créer");		
	}
	
	@Test
	public void buildInternalizationString_isCreer_whenKeyIsCreateNoun(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(SystemActionCreate.class, InternalizationKeyStringType.NOUN))).isEqualTo("création");		
	}
		
	@Test
	public void buildInternalizationString_isValidation_whenKeyIsProcessValidateNoun(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternalizationKeyStringType.NOUN))).isEqualTo("validation");		
	}
	
	@Test
	public void buildInternalizationString_isValider_whenKeyIsProcessValidateVerb(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(__inject__(SystemActionProcess.class).setIdentifier("validate"), InternalizationKeyStringType.VERB))).isEqualTo("valider");		
	}
	
	@Test
	public void buildInternalizationString_is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		assertThat(__buildInternalizationString__("operation.execution.success.summary")).isEqualTo("l'opération a été exécutée avec succès.");	
	}
	
	@Test
	public void buildInternalizationString_is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		assertThat(__buildInternalizationString__("service.of.act.not.found",new Object[] {"create","person"},null,null)).isEqualTo("le service de create de person est introuvable");
	}
	
	@Test
	public void buildInternalizationString_is_l_hote_host001_est_inconnu_whenKeyIsThrowableJavaNetUnknownHostException(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(new UnknownHostException("host001")))).isEqualTo("l'hôte host001 est inconnu");
	}
	
	@Test
	public void buildInternalizationString_is_la_connexion_a_ete_refusee_whenKeyIsThrowableJavaNetConnectException(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(new ConnectException("refusé")))).isEqualTo("la connexion a été refusée");
	}
	
	@Test
	public void buildInternalizationString_is_mon_message_whenKeyIsThrowableJavaLangRuntimeException(){
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(new RuntimeException("mon message")))).isEqualTo("mon message");
	}
	
	@Test
	public void buildInternalizationString_is_le_service_de_lecture_de_personne_est_introuvable_whenKeyIsThrowableServiceNotFoundException(){
		ServiceNotFoundException exception = __inject__(ServiceNotFoundException.class);
		exception.setSystemAction(__inject__(SystemActionRead.class).setEntityClass(Person.class));
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(exception))).isEqualTo("le service de lecture de personne est introuvable");
	}
	
	@Test
	public void buildInternalizationString_is_l_entite_personne_ayant_le_code_abc_n_existe_pas_whenKeyIsThrowableServiceNotFoundException(){
		EntityNotFoundException exception = __inject__(EntityNotFoundException.class);
		SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
		systemActionRead.setEntityClass(Person.class);
		systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add("abc");
		exception.setSystemAction(systemActionRead);
		assertThat(__buildInternalizationString__(__buildInternalizationKey__(exception))).isEqualTo("l'entité personne ayant le code abc n'existe pas");
	}
	
	@Test
	public void buildInternalizationPhrase(){
		assertThat(__buildInternalizationPhraseFromKeysValues__("type","of","person")).isEqualTo("type de personne");
	}
	
	/**/
	
	public static class Person {
		
	}
	
	public static interface MyEntity {
		
	}
	
	public static class MyEntityImpl implements MyEntity {
		
	}
}
