package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Message extends AbstractObject implements Serializable {

	private String summary;
	private String details;
	private Severity severity;
	
	@Override
	public String toString() {
		Collection<String> strings = null;
		if(severity != null) {
			if(strings == null)
				strings = new ArrayList<>();
			strings.add(severity.name());
		}
		if(StringHelper.isNotBlank(summary)) {
			if(strings == null)
				strings = new ArrayList<>();
			strings.add(summary);
		}
		if(StringHelper.isNotBlank(details)) {
			if(strings == null)
				strings = new ArrayList<>();
			strings.add(details);
		}
		return StringHelper.concatenate(strings, "|");
	}
}
