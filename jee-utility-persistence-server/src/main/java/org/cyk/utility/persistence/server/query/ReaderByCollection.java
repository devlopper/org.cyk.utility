package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;

public interface ReaderByCollection<I,O> {

	Collection<O> read(Collection<I> values);	
	
	/**/
	
	public abstract class AbstractImpl<I,O> extends AbstractObject implements ReaderByCollection<I,O>,Serializable {
		private static final long serialVersionUID = 1L;		
		
		public Collection<O> read(Collection<I> values) {
			if(CollectionHelper.isEmpty(values))
				return null;
			Collection<O> collection = null;
			for(List<I> list : CollectionHelper.getBatches((List<I>) values, 1000)) {
				Collection<O> result = __read__(list);
				if(CollectionHelper.isNotEmpty(result)) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.addAll(result);
				}
			}
			return collection;
		}
		
		protected abstract Collection<O> __read__(Collection<I> values);
	}
}