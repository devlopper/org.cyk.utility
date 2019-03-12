package org.cyk.utility.__kernel__.test.arquillian.archive.builder;

import java.io.Serializable;

import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class JavaArchiveBuilder extends AbstractArchiveBuilder<JavaArchive> implements Serializable {
	private static final long serialVersionUID = 1L;

	public JavaArchiveBuilder() {
		super(JavaArchive.class);
	}

}
