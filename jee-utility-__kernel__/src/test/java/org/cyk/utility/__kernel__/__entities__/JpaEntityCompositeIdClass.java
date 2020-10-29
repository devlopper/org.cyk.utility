package org.cyk.utility.__kernel__.__entities__;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Table(name = "JPAENTITYCOMPOSITEID")
public class JpaEntityCompositeIdClass implements Serializable {

	@EmbeddedId private CompositeIdClass identifier;
}
