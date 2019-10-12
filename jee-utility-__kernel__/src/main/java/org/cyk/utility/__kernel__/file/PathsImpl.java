package org.cyk.utility.__kernel__.file;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.string.Strings;

@Dependent
public class PathsImpl extends AbstractCollectionInstanceImpl<Path> implements Paths,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Paths removeByUniformResourceIdentifiers(Collection<String> uniformResourceIdentifiers) {
		if(isNotEmpty() && CollectionHelper.isNotEmpty(uniformResourceIdentifiers)) {
			Collection<URI> uris = UniformResourceIdentifierHelper.getURIs(uniformResourceIdentifiers);
			Collection<Path> elements = null;
			for(Path path : collection)
				if(uris.contains(path.toFile().toURI())) {
					if(elements == null)
						elements = new ArrayList<>();
					elements.add(path);
				}
			if(elements != null)
				collection.removeAll(elements);
		}
		return this;
	}

	@Override
	public Paths removeByUniformResourceIdentifiers(String... uniformResourceIdentifiers) {
		if(ArrayHelper.isNotEmpty(uniformResourceIdentifiers))
			removeByUniformResourceIdentifiers(List.of(uniformResourceIdentifiers));
		return this;
	}

	@Override
	public Paths removeByUniformResourceIdentifiers(Strings uniformResourceIdentifiers) {
		if(CollectionHelper.isNotEmpty(uniformResourceIdentifiers))
			removeByUniformResourceIdentifiers(uniformResourceIdentifiers.get());
		return this;
	}

}
