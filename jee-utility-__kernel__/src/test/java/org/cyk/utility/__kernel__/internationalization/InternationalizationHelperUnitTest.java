package org.cyk.utility.__kernel__.internationalization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildKeyCacheEntryIdentifier;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildKey;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildPhraseFromKeysValues;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildPhrase;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildString;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.deriveKeys;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.processPhrases;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.processStrings;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKey;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionProcess;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.throwable.EntityNotFoundException;
import org.cyk.utility.__kernel__.throwable.ServiceNotFoundException;
import org.junit.jupiter.api.Test;

public class InternationalizationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void buildKeyCacheEntryIdentifier_string_hi(){
		assertThat(buildKeyCacheEntryIdentifier("hi")).isEqualTo("String-hi-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_class_Person(){
		assertThat(buildKeyCacheEntryIdentifier(Person.class)).isEqualTo("Class-"+Person.class.getName()+"-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_class_SystemActionCreate(){
		assertThat(buildKeyCacheEntryIdentifier(SystemActionCreate.class)).isEqualTo("Class-"+SystemActionCreate.class.getName()+"-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNull(){
		assertThat(buildKeyCacheEntryIdentifier(DependencyInjection.inject(SystemActionCreate.class))).isEqualTo("SystemAction-Create-");
	}
	
	@Test
	public void buildKeyCacheEntryIdentifier_instance_SystemActionCreate_identifierIsNotNull(){
		assertThat(buildKeyCacheEntryIdentifier(DependencyInjection.inject(SystemActionCreate.class).setIdentifier("do"))).isEqualTo("SystemAction-do-");
	}
	
	@Test
	public void buildKey_none(){
		assertThat(buildKey(null)).isNull();
	}
	
	@Test
	public void buildKey_empty(){
		assertThat(buildKey("")).isNull();
	}
	
	@Test
	public void buildKey_blank(){
		assertThat(buildKey("   ")).isNull();
	}
	
	@Test
	public void buildKey_hi(){
		assertThat(buildKey("hi").getValue()).isEqualTo("hi");
	}
	
	@Test
	public void buildKey__hi(){
		assertThat(buildKey("_hi").getValue()).isEqualTo("_.hi");
	}
	
	@Test
	public void buildKey_hi_(){
		assertThat(buildKey("hi_").getValue()).isEqualTo("hi._");
	}
	
	@Test
	public void buildKey_camelCase(){
		assertThat(buildKey("camelCase").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void buildKey_camelCase01(){
		assertThat(buildKey("camelCase01").getValue()).isEqualTo("camel.case.01");
	}
	
	@Test
	public void buildKey_camelCase01ABC(){
		assertThat(buildKey("camelCase01ABC").getValue()).isEqualTo("camel.case.01.abc");
	}
	
	@Test
	public void buildKey_camelCase01ABCOk(){
		assertThat(buildKey("camelCase01ABCOk").getValue()).isEqualTo("camel.case.01.abc.ok");
	}
	
	@Test
	public void buildKey_camel_dot_case(){
		assertThat(buildKey("camel.case").getValue()).isEqualTo("camel.case");
	}
	
	@Test
	public void deriveKeys_person_type(){
		List<Strings> keys = deriveKeys("person.type");
		assertThat(keys.get(0).get()).contains("person","of","type");
	}
	
	@Test
	public void buildKey_systemActionCreate(){
		assertThat(buildKey(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(buildKey(SystemActionCreate.class).getValue()).isEqualTo("create");
		assertThat(buildKey(SystemActionCreate.class,InternationalizationKeyStringType.NOUN).getValue()).isEqualTo("create.__noun__");
		assertThat(buildKey(SystemActionCreate.class,InternationalizationKeyStringType.VERB).getValue()).isEqualTo("create.__verb__");
	}
	
	@Test
	public void buildKey_systemActionRead(){
		assertThat(buildKey(SystemActionRead.class).getValue()).isEqualTo("read");
		assertThat(buildKey(DependencyInjection.inject(SystemActionRead.class)).getValue()).isEqualTo("read");
		assertThat(buildKey(DependencyInjection.inject(SystemActionRead.class).setIdentifier("consult")).getValue()).isEqualTo("consult");
	}
	
	@Test
	public void buildKey_myEntity(){
		assertThat(buildKey(MyEntity.class).getValue()).isEqualTo("my.entity");
		assertThat(buildKey(MyEntityImpl.class).getValue()).isEqualTo("my.entity");
	}
	
	@Test
	public void buildKey_throwableJavaNetUnknownHostException(){
		UnknownHostException exception = new UnknownHostException("host001");
		InternationalizationKey key = buildKey(exception);
		assertThat(key.getValue()).isEqualTo("java.net.unknown.host.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildKey_throwableJavaNetConnectException(){
		ConnectException exception = new ConnectException("host001");
		InternationalizationKey key = buildKey(exception);
		assertThat(key.getValue()).isEqualTo("java.net.connect.exception");
		assertThat(key.getArguments()).contains("host001");
	}
	
	@Test
	public void buildString_isType_de_xxx_whenKeyXxxDotType(){
		assertThat(buildString("xxx.type")).isEqualTo("type de ##??xxx??##");	
	}
	
	@Test
	public void buildString_isSalut_whenKeyIsHi(){
		assertThat(buildString("hi")).isEqualTo("salut");
	}
	
	@Test
	public void buildString_isH1H2_whenKeyIsH1H2(){
		assertThat(buildString("h1H2")).isEqualTo("##??h1H2??##");
		assertThat(buildString(buildKey("h1H2"))).isEqualTo("##??h.1.h.2??##");	
	}
	
	@Test
	public void buildString_isNom_whenKeyIsFirstName(){
		assertThat(buildString(buildKey("firstName"))).isEqualTo("nom");
		assertThat(buildString(buildKey("firstName"),null,null,Case.FIRST_CHARACTER_UPPER)).isEqualTo("Nom");
	}
	
	@Test
	public void buildString_isPrenoms_whenKeyIsLastName(){
		assertThat(buildString(buildKey("lastNames"))).isEqualTo("prénoms");
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreate(){
		assertThat(buildString(buildKey(SystemActionCreate.class, null))).isEqualTo("créer");
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreateVerb(){
		assertThat(buildString(buildKey(SystemActionCreate.class, InternationalizationKeyStringType.VERB))).isEqualTo("créer");		
	}
	
	@Test
	public void buildString_isCreer_whenKeyIsCreateNoun(){
		assertThat(buildString(buildKey(SystemActionCreate.class, InternationalizationKeyStringType.NOUN))).isEqualTo("création");		
	}
		
	@Test
	public void buildString_isValidation_whenKeyIsProcessValidateNoun(){
		assertThat(buildString(buildKey(DependencyInjection.inject(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.NOUN))).isEqualTo("validation");		
	}
	
	@Test
	public void buildString_isValider_whenKeyIsProcessValidateVerb(){
		assertThat(buildString(buildKey(DependencyInjection.inject(SystemActionProcess.class).setIdentifier("validate"), InternationalizationKeyStringType.VERB))).isEqualTo("valider");		
	}
	
	@Test
	public void buildString_is_l_operation_a_ete_executee_avec_succes_whenKeyIsOperationExecutionSuccessSummary(){
		assertThat(buildString("operation.execution.success.summary")).isEqualTo("l'opération a été exécutée avec succès.");	
	}
	
	@Test
	public void buildString_is_le_service_de_creation_de_personne_est_introuvable_whenKeyIsServiceOfActNotFound(){
		assertThat(buildString("service.of.act.not.found",new Object[] {"create","person"},null,null)).isEqualTo("le service de create de person est introuvable");
	}
	
	@Test
	public void buildString_is_l_hote_host001_est_inconnu_whenKeyIsThrowableJavaNetUnknownHostException(){
		assertThat(buildString(buildKey(new UnknownHostException("host001")))).isEqualTo("l'hôte host001 est inconnu");
	}
	
	@Test
	public void buildString_is_la_connexion_a_ete_refusee_whenKeyIsThrowableJavaNetConnectException(){
		assertThat(buildString(buildKey(new ConnectException("refusé")))).isEqualTo("la connexion a été refusée");
	}
	
	@Test
	public void buildString_is_mon_message_whenKeyIsThrowableJavaLangRuntimeException(){
		assertThat(buildString(buildKey(new RuntimeException("mon message")))).isEqualTo("mon message");
	}
	
	@Test
	public void buildString_is_le_service_de_lecture_de_personne_est_introuvable_whenKeyIsThrowableServiceNotFoundException(){
		ServiceNotFoundException exception = DependencyInjection.inject(ServiceNotFoundException.class);
		exception.setSystemAction(DependencyInjection.inject(SystemActionRead.class).setEntityClass(Person.class));
		assertThat(buildString(buildKey(exception))).isEqualTo("le service de lecture de personne est introuvable");
	}
	
	@Test
	public void buildString_is_l_entite_personne_ayant_le_code_abc_n_existe_pas_whenKeyIsThrowableServiceNotFoundException(){
		EntityNotFoundException exception = DependencyInjection.inject(EntityNotFoundException.class);
		SystemActionRead systemActionRead = DependencyInjection.inject(SystemActionRead.class);
		systemActionRead.setEntityClass(Person.class);
		systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add("abc");
		exception.setSystemAction(systemActionRead);
		assertThat(buildString(buildKey(exception))).isEqualTo("l'entité personne ayant le code abc n'existe pas");
	}
	
	@Test
	public void processString(){
		InternationalizationString internalizationString = new InternationalizationString();
		internalizationString.setKey(new InternationalizationKey().setValue("hi"));
		assertThat(internalizationString.getValue()).isNull();
		assertThat(internalizationString.getIsHasBeenProcessed()).isNull();
		processStrings(internalizationString);
		assertThat(internalizationString.getValue()).isEqualTo("salut");
		assertThat(internalizationString.getIsHasBeenProcessed()).isTrue();
	}
	
	@Test
	public void processString_noKey_butValueSet(){
		InternationalizationString internalizationString = new InternationalizationString();
		assertThat(internalizationString.getValue()).isNull();
		assertThat(internalizationString.getIsHasBeenProcessed()).isNull();
		internalizationString.setValue("my custom value");
		assertThat(internalizationString.getValue()).isEqualTo("my custom value");
		assertThat(internalizationString.getIsHasBeenProcessed()).isTrue();
		processStrings(internalizationString);
		assertThat(internalizationString.getValue()).isEqualTo("my custom value");
		assertThat(internalizationString.getIsHasBeenProcessed()).isTrue();
	}
	
	@Test
	public void buildPhrase_null(){
		assertThat(buildPhrase(null)).isEqualTo(null);
	}
	
	@Test
	public void buildPhrase_empty(){
		assertThat(buildPhrase(Arrays.asList())).isEqualTo(null);
	}
	
	@Test
	public void buildPhrase_oneWord(){
		assertThat(buildPhrase(Arrays.asList(new InternationalizationKey().setValue("hi")))).isEqualTo("Salut");
	}
	
	@Test
	public void buildPhrase_twoWords(){
		assertThat(buildPhrase(Arrays.asList(new InternationalizationKey().setValue("hi")
				,new InternationalizationKey().setValue("good")))).isEqualTo("Salut bon");
	}
	
	@Test
	public void buildPhrase_fromKeysValues(){
		assertThat(buildPhraseFromKeysValues("type","of","person")).isEqualTo("Type de personne");
	}
	
	@Test
	public void processPhrase(){
		InternationalizationPhrase phrase = new InternationalizationPhrase();
		phrase.addString("hi");
		phrase.addString("good");
		phrase.addNoun(DependencyInjection.inject(SystemActionCreate.class));
		processPhrases(phrase);
		assertThat(phrase.getValue()).isEqualTo("salut bon création");
	}
	
	@Test
	public void processPhrase_caseFIRST_CHARACTER_UPPER_REMAINDER_LOWER(){
		InternationalizationPhrase phrase = new InternationalizationPhrase().setKase(Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER);
		phrase.addString("hi");
		phrase.addString("good");
		phrase.addNoun(DependencyInjection.inject(SystemActionCreate.class));
		processPhrases(phrase);
		assertThat(phrase.getValue()).isEqualTo("Salut bon création");
	}
	
	@Test
	public void processPhrase_noKeys(){
		InternationalizationPhrase phrase = new InternationalizationPhrase();
		phrase.getStrings(Boolean.TRUE).add(new InternationalizationString().setValue("bonjour"));
		phrase.getStrings(Boolean.TRUE).add(new InternationalizationString().setValue("TO 2019"));
		processPhrases(phrase);
		assertThat(phrase.getValue()).isEqualTo("bonjour TO 2019");
	}
	
	/**/
	
	public static class Person {
		
	}
	
	public static interface MyEntity {
		
	}
	
	public static class MyEntityImpl implements MyEntity {
		
	}
}
