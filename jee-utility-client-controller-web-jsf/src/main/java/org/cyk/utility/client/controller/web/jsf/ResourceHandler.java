package org.cyk.utility.client.controller.web.jsf;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;

public class ResourceHandler extends javax.faces.application.ResourceHandlerWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Set<String> PATHS = new LinkedHashSet<String>();

	static {
		addPath("/META-INF/resources");//images , css , ...
		
		//contracts
		addPath("/META-INF/contracts");
		
		addPath("/META-INF/pages");//web pages
	}

	private javax.faces.application.ResourceHandler wrapped;

	public ResourceHandler(javax.faces.application.ResourceHandler wrapped) {
		this.wrapped = wrapped;
	}

	public static void addPath(String path) {
		PATHS.add(path);
	}

	@Override
	public ViewResource createViewResource(FacesContext context, String resourceName) {
		// Resolves from WAR.
		ViewResource resource = super.createViewResource(context, resourceName);
		if (resource == null)
			// Resolves from JAR.
			for (String customBasePath : PATHS) {
				final URL url = getClass().getResource(customBasePath + resourceName);
				if (url == null) {
					// System.out.println("NOT IN : "+customBasePath+path);
				} else{
					resource = new ViewResource() {
						@Override
						public URL getURL() {
							return url;
						}
					};
					break;
				}
			}
		if(resource == null)
			//throw new RuntimeException();
			System.out.println("Resource named "+resourceName+" not found");
		return resource;
	}

	@Override
	public javax.faces.application.ResourceHandler getWrapped() {
		return wrapped;
	}

}