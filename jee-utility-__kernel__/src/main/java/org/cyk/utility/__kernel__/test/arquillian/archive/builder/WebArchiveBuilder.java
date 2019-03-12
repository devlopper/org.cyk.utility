package org.cyk.utility.__kernel__.test.arquillian.archive.builder;

import java.io.Serializable;

import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WebArchiveBuilder extends AbstractArchiveBuilder<WebArchive> implements Serializable {
	private static final long serialVersionUID = 1L;

	public WebArchiveBuilder() {
		super(WebArchive.class);
	}

}
