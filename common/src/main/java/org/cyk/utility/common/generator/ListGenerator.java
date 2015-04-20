package org.cyk.utility.common.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter @Setter
@Log
public class ListGenerator<T> extends AbstractValueGenerator<Collection<T>>{

	private static final long serialVersionUID = -6459148921135823612L;

	private List<T> data;
	private int min,max;
	private boolean allowDuplicate;

	public ListGenerator(List<T> data, int min, int max, boolean allowDuplicate) {
		super();
		this.data = data;
		this.min = min;
		if(!(this.allowDuplicate = allowDuplicate))
			this.max = data.size()>max?data.size():max;
		if(DEBUG)
			log.info("#elements="+data.size()+" min="+min+" max="+max);
	}

	@Override
	public Collection<T> generate() {
		List<T> out = new ArrayList<T>();
		int count = randomDataProvider.randomInt(min, max);
		if(DEBUG)
			log.info(count+" object(s) to generate in collection");
		Collection<Integer> indexes;
		if(allowDuplicate){
			indexes = new LinkedList<Integer>();
			if(DEBUG)
				log.info("collection is list");
		}else{
			indexes = new HashSet<Integer>();
			if(DEBUG)
				log.info("collection is set");
		}
		do{
			indexes.add(randomDataProvider.randomPositiveInt(data.size()-1));
		}while(indexes.size()<count);
		
		for(Integer index : indexes)
			out.add(data.get(index));
		
		/*
		while(out.size()<count){
			T element = data.get(RandomUtils.randomPositiveInt(data.size()-1));
			if(DEBUG)
				log.info("Random Element = "+element+" - List = "+out+" Remaining = "+count);
			if(out.contains(element))
				if(allowDuplicate){
					out.add(element);
					count--;
				}else
					;
			else{
				out.add(element);
				count--;
			}
		}*/
		return out;
	}
	
	
	/*
	 * 
	 * equals method not well defined 

out.contains(element) ???? wont work

	protected boolean equals(T o1,T o2){
		o1.equals(o2);
	}*/
	
	/*
	 * ANOTHER GEMIUS WAY
	 * TODO Generete a list of index -> ensure not dupluicate if needed
	 * */

	
}
