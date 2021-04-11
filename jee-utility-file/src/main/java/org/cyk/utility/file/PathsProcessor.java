package org.cyk.utility.file;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionProcessor;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface PathsProcessor {

	void process(Arguments arguments);
	void process(Collection<Path> paths,CollectionProcessor.Arguments.Processing<Path> processing);
	
	public static abstract class AbstractImpl extends AbstractObject implements PathsProcessor,Serializable {
		
		@Override
		public void process(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			CollectionProcessor.getInstance().process(Path.class,arguments.collectionProcessorArguments);
		}
		
		@Override
		public void process(Collection<Path> paths,CollectionProcessor.Arguments.Processing<Path> processing) {
			Arguments arguments = new Arguments().addPaths(paths);
			arguments.getCollectionProcessorArguments().setProcessing(processing);
			process(arguments);
		}
	}
	
	/**/
	
	static PathsProcessor getInstance() {
		return Helper.getInstance(PathsProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private CollectionProcessor.Arguments<Path> collectionProcessorArguments = new CollectionProcessor.Arguments<Path>();
		
		public Arguments addPaths(Collection<Path> paths) {
			if(CollectionHelper.isEmpty(paths))
				return this;
			collectionProcessorArguments.getList(Boolean.TRUE).addAll(paths);
			return this;
		}
		
		public Arguments addPaths(Path...paths) {
			if(ArrayHelper.isEmpty(paths))
				return this;
			return addPaths(CollectionHelper.listOf(paths));
		}
		
		public Arguments addPathsFromNames(Collection<String> names) {
			if(CollectionHelper.isEmpty(names))
				return this;
			for(String name : names) {
				File file = new File(name);
				if(!file.exists())
					continue;
				addPaths(file.toPath());
			}
			return this;
		}
		
		public Arguments addPathsFromNames(String...names) {
			if(ArrayHelper.isEmpty(names))
				return this;
			return addPathsFromNames(CollectionHelper.listOf(names));
		}
	}
}