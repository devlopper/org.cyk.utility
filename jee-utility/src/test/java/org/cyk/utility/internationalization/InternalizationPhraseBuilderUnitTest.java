package org.cyk.utility.internationalization;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.string.Case;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationPhraseBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void phrases(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals(null, __inject__(InternalizationPhraseBuilder.class).execute().getOutput());	
		assertionHelper.assertEquals("##??hi.2??##", __inject__(InternalizationPhraseBuilder.class).addStringsByKeys("hi2").execute().getOutput());	
		assertionHelper.assertEquals("##??hi.2??## ##??goood??##", __inject__(InternalizationPhraseBuilder.class).addStringsByKeys("hi2","goood").execute().getOutput());	
		assertionHelper.assertEquals("salut monsieur", __inject__(InternalizationPhraseBuilder.class).addStringsByKeys("hi","mister").execute().getOutput());	
		assertionHelper.assertEquals("Salut monsieur", __inject__(InternalizationPhraseBuilder.class).setCase(Case.FIRST_CHARACTER_UPPER).addStringsByKeys("hi","mister").execute().getOutput());	
	}
	
}
