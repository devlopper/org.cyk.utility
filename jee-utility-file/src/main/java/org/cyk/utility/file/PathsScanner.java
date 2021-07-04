package org.cyk.utility.file;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface PathsScanner {

	Collection<Path> scan(Arguments arguments);
	
	public static abstract class AbstractImpl extends AbstractObject implements PathsScanner,Serializable {
		
		@Override
		public Collection<Path> scan(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			Collection<Path> inputs = arguments.getPaths();
			LogHelper.logInfo(String.format("%s paths to be scanned : %s",CollectionHelper.getSize(inputs),inputs), getClass());
			if(CollectionHelper.isEmpty(inputs))
				return null;
			Collection<Path> outputs = null;
			String acceptedPathNameRegularExpression = arguments.acceptedPathNameRegularExpression;
			Boolean isDiretoryReturnable = ValueHelper.defaultToIfNull(arguments.isDiretoryReturnable, Boolean.TRUE);
			Boolean isFileReturnable = ValueHelper.defaultToIfNull(arguments.isFileReturnable, Boolean.TRUE);
			Long minimalSize = ValueHelper.defaultToIfNull(arguments.minimalSize, Long.MIN_VALUE);
			Long maximalSize = ValueHelper.defaultToIfNull(arguments.maximalSize, Long.MAX_VALUE);
			for(Path index : inputs) {
				if(index == null)
					continue;
				if(index.toFile().isFile() && isFileReturnable && isMatchingAcceptedPathNameRegularExpression(index, acceptedPathNameRegularExpression)
						&& isBetweenSizes(index, minimalSize, maximalSize)) {
					if(outputs == null)
						outputs = new ArrayList<>();
					outputs.add(index);
					continue;
				}
				Collection<Path> result = scanDirectory(index,isDiretoryReturnable,isFileReturnable,acceptedPathNameRegularExpression,minimalSize,maximalSize);
				if(CollectionHelper.isNotEmpty(result)) {
					if(outputs == null)
						outputs = new ArrayList<>();
					outputs.addAll(result);
				}
			}
			LogHelper.logInfo(String.format("%s paths found",CollectionHelper.getSize(outputs)), getClass());
			return outputs;
		}
		
		private Collection<Path> scanDirectory(Path path,Boolean isDiretoryReturnable,Boolean isFileReturnable,String acceptedPathNameRegularExpression,Long minimalSize,Long maximalSize) {
			if(path == null || path.toFile().isFile())
				return null;
			Collection<Path> paths = null;
			try {
				DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
				for(Path child : directoryStream) {
					if((child.toFile().isDirectory() && isDiretoryReturnable || child.toFile().isFile() && isFileReturnable) 
							&& isMatchingAcceptedPathNameRegularExpression(child, acceptedPathNameRegularExpression) && isBetweenSizes(child, minimalSize, maximalSize)) {
						if(paths == null)
							paths = new ArrayList<>();
						paths.add(child);
					}					
					if(child.toFile().isDirectory()) {
						Collection<Path> childPaths = scanDirectory(child,isDiretoryReturnable,isFileReturnable,acceptedPathNameRegularExpression,minimalSize, maximalSize);
						if(CollectionHelper.isNotEmpty(childPaths)) {
							if(paths == null)
								paths = new ArrayList<>();
							paths.addAll(childPaths);
						}							
					}
				}
				directoryStream.close();
			} catch (IOException exception) {
				LogHelper.log(exception, getClass());
			}
			return paths;
		}
		
		protected Boolean isMatchingAcceptedPathNameRegularExpression(Path path,String expression) {
			if(path == null)
				return Boolean.FALSE;
			if(StringHelper.isBlank(expression))
				return Boolean.TRUE;
			return RegularExpressionHelper.match(path.toFile().getName(), expression);
		}
		
		protected Boolean isBetweenSizes(Path path,Long minimalSize,Long maximalSize) {
			if(path == null)
				return Boolean.FALSE;
			Long size = path.toFile().length();
			return NumberHelper.compare(size, minimalSize, ComparisonOperator.GTE) && NumberHelper.compare(size, maximalSize, ComparisonOperator.LTE);
		}
	}
	
	/**/
	
	static PathsScanner getInstance() {
		return Helper.getInstance(PathsScanner.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private Collection<Path> paths;
		private String acceptedPathNameRegularExpression;
		private Long minimalSize,maximalSize;
		private Boolean isDiretoryReturnable,isFileReturnable;
		
		public Collection<Path> getPaths(Boolean injectIfNull) {
			if(paths == null && Boolean.TRUE.equals(injectIfNull))
				paths = new ArrayList<>();
			return paths;
		}
		
		public Arguments addPaths(Collection<Path> paths) {
			if(CollectionHelper.isEmpty(paths))
				return this;
			getPaths(Boolean.TRUE).addAll(paths);
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
				if(!file.exists()) {
					LogHelper.logWarning(String.format("Path with name <<%s>> will not be added because it does not exist", name), getClass());
					continue;
				}
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