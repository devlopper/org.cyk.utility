package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

public abstract class AbstractOutputStringBuilderImpl<OUTPUT extends OutputString> extends AbstractOutputBuilderImpl<OUTPUT,String> implements OutputStringBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

}
