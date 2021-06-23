package org.cyk.utility.representation.server;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

@ApplicationPath(Application.PATH) @Path("/")
@ApplicationScoped
public class Application extends AbstractApplication implements Serializable {

}