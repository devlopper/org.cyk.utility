package org.cyk.utility.file;

import java.io.Serializable;
import java.nio.file.Path;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class PathsImpl extends AbstractCollectionInstanceImpl<Path> implements Paths,Serializable {
	private static final long serialVersionUID = 1L;

}
