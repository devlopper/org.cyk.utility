package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Resource implements Serializable {

	private String identifier;
	private String name;
	private Collection<String> uniformResourceIdentifiers;

	public Collection<String> getUniformResourceIdentifiers(Boolean injectIfNull) {
		if(uniformResourceIdentifiers == null && Boolean.TRUE.equals(injectIfNull))
			uniformResourceIdentifiers = new ArrayList<>();
		return uniformResourceIdentifiers;
	}
	
	public Resource addUniformResourceIdentifiers(Collection<String> uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(uniformResourceIdentifiers))
			return this;
		getUniformResourceIdentifiers(Boolean.TRUE).addAll(uniformResourceIdentifiers);
		return this;
	}
	
	public Resource addUniformResourceIdentifiers(String...uniformResourceIdentifiers) {
		if(ArrayHelper.isEmpty(uniformResourceIdentifiers))
			return this;
		return addUniformResourceIdentifiers(CollectionHelper.listOf(uniformResourceIdentifiers));
	}
	
	/**/
	
	public static Resource build(String name,String...uniformResourceIdentifiers) {
		return new Resource().setName(name).addUniformResourceIdentifiers(uniformResourceIdentifiers);
	}
	
	@Override
	public String toString() {
		return name+"|"+uniformResourceIdentifiers;
	}
}