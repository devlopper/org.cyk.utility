package org.cyk.utility.stream.distributed.kafka.network;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

}
