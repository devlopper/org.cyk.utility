package org.cyk.utility.__kernel__.protocol.http;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class HttpHelperGetParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	private URI uri;
	private BodyHandler<?> bodyHandler;
	
}
