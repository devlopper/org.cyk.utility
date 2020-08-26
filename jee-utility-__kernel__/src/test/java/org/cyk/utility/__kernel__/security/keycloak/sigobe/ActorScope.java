package org.cyk.utility.__kernel__.security.keycloak.sigobe;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=ActorScope.TABLE_NAME) @Access(AccessType.FIELD)
@Cacheable(value = true)
@AttributeOverrides(value = {
		@AttributeOverride(name = Scope.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
	})
public class ActorScope extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name = COLUMN_ACTOR,nullable = false) @NotNull private Actor actor;
	@ManyToOne @JoinColumn(name = COLUMN_SCOPE,nullable = false) @NotNull private Scope scope;
	
	@Override
	public ActorScope setIdentifier(String identifier) {
		return (ActorScope) super.setIdentifier(identifier);
	}
	
	public static final String COLUMN_ACTOR = "acteur";
	public static final String COLUMN_SCOPE = "domaine";
	public static final String COLUMN_VISIBLE = "visible";
	
	public static final String TABLE_NAME = "ACTEUR_DOMAINE";	
}