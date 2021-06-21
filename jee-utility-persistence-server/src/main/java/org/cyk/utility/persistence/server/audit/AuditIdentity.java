package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of = "identifier")
public class AuditIdentity implements Serializable {

	private String identifier;
	private String names;

	/**/
	
	public static AuditIdentity getByIdentifier(Collection<AuditIdentity> identities,String identifier) {
		if(CollectionHelper.isEmpty(identities) || StringHelper.isBlank(identifier))
			return null;
		for(AuditIdentity identity : identities)
			if(identity.getIdentifier().equals(identifier))
				return identity;
		return null;
	}
	
	@Override
	public String toString() {
		if(StringHelper.isBlank(names))
			return identifier;
		return String.format("%s(%s)", names,identifier);
	}
}