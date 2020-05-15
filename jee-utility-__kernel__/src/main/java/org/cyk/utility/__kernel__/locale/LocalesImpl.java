package org.cyk.utility.__kernel__.locale;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Deprecated
public class LocalesImpl extends AbstractCollectionInstanceImpl<Locale> implements Locales,Serializable {
	private static final long serialVersionUID = 1L;
	
}
