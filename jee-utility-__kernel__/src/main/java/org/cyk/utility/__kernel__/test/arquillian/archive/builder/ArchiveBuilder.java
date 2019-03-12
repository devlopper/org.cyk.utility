package org.cyk.utility.__kernel__.test.arquillian.archive.builder;

import java.io.Serializable;

import org.jboss.shrinkwrap.api.Archive;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ArchiveBuilder<ARCHIVE extends Archive<?>> extends AbstractArchiveBuilder<ARCHIVE> implements Serializable {
	private static final long serialVersionUID = 1L;

	public ArchiveBuilder(Class<ARCHIVE> clazz) {
		super(clazz);
	}

}
