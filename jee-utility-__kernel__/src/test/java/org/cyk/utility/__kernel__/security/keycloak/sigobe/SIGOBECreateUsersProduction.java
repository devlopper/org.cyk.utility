package org.cyk.utility.__kernel__.security.keycloak.sigobe;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.security.keycloak.KeycloakClientGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractPersistenceUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class SIGOBECreateUsersProduction extends AbstractPersistenceUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		KeycloakClientGetter.INSTANCE.set(null);
	}
	
	@Override
	protected String getPersistenceUnitName() {
		return "sigobe_prod";
	}
	
	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://10.3.4.60:8080/auth/");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "SIIB");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "ac0749e3-14f8-4a14-a6e1-4edfb9375e69");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "mic-acteur-api");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "mic-@cteur-@pi@2O2o");
	}
	
	@Test
	public void run(){
		rffim();
	}
	
	private void rffim(){
		init();		
		Collection<Section> sections = EntityManagerGetter.getInstance().get().createQuery("SELECT s FROM Section s ORDER BY s.code ASC", Section.class).getResultList();	
		if(CollectionHelper.isNotEmpty(sections)) {
			for(Section section : sections) {
				String username = "RFFIM_"+section.getCode();
				/*String password = username;
				User user = UserManager.getInstance().readByUserName(username);
				if(user == null) {
					user = new User().setName(username).setElectronicMailAddress(username+"@mail.com").setPass(password).setFirstName("RFFIM")
							.setLastNames("Section "+section.getCode());
					UserManager.getInstance().create(user);
					user = UserManager.getInstance().readByUserName(username);
					if(user == null) {
						System.out.println("User <<"+username+">> not created");
					}else {
						System.out.println("User <<"+username+">> created");
					}
				}else {
					System.out.println("User <<"+username+">> already exists");
				}*/
				username = username.toLowerCase();
				String scopeIdentifier = section.getIdentifier();
				ActorScope actorScope = null;
				try {
					actorScope = EntityManagerGetter.getInstance().get().createQuery("SELECT ass FROM ActorScope ass WHERE ass.actor.code = '"
							+username+"' AND ass.scope.identifier = '"+scopeIdentifier+"'", ActorScope.class).getSingleResult();
				} catch (NoResultException exception) {}
				if(actorScope == null) {
					Actor actor = EntityManagerGetter.getInstance().get().createQuery("SELECT a FROM Actor a WHERE a.code = '"+username+"'", Actor.class).getSingleResult();
					Scope scope = EntityManagerGetter.getInstance().get().createQuery("SELECT s FROM Scope s WHERE s.identifier = '"+scopeIdentifier+"'", Scope.class).getSingleResult();
					if(actor != null && scope != null) {
						actorScope = new ActorScope().setIdentifier(actor.getCode()+"_SECTION_"+section.getCode()).setActor(actor).setScope(scope);						
						EntityManager entityManager = EntityManagerGetter.getInstance().get();
						entityManager.getTransaction().begin();
						entityManager.persist(actorScope);
						entityManager.getTransaction().commit();
						System.out.println(actor+" , "+scope+" - domaine créé.");
					}else {
						System.out.println(username+" , "+scopeIdentifier+" - acteur ou domaine inexistant.");
					}
				}else {
					System.out.println(actorScope.getActor().getCode()+" , "+actorScope.getScope().getCode()+" - domaine existant.");
				}
			}
		}	
	}
}