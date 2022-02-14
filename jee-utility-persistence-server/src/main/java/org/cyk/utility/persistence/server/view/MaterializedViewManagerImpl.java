package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApplicationScoped @Getter @Setter @Accessors(chain=true)
public class MaterializedViewManagerImpl extends MaterializedViewManager.AbstractImpl implements Serializable {

}