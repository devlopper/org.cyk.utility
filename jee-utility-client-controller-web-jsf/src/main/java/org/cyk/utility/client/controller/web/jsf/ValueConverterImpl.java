package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.RegExUtils;
import org.cyk.utility.__kernel__.annotation.JavaServerFaces;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.value.AbstractValueConverterImpl;

@JavaServerFaces
public class ValueConverterImpl extends AbstractValueConverterImpl implements Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Object value, Class<T> klass) {
		if(value instanceof Message && FacesMessage.class.equals(klass)) {
			FacesMessage.Severity severity = null;
			if(Severity.INFORMATION.equals(((Message)value).getSeverity()))
				severity = FacesMessage.SEVERITY_INFO;
			else if(Severity.WARNING.equals(((Message)value).getSeverity()))
				severity = FacesMessage.SEVERITY_WARN;
			else if(Severity.ERROR.equals(((Message)value).getSeverity()))
				severity = FacesMessage.SEVERITY_ERROR;
			else if(Severity.FATAL.equals(((Message)value).getSeverity()))
				severity = FacesMessage.SEVERITY_FATAL;
			else
				severity = FacesMessage.SEVERITY_INFO;
			String summary = ((Message)value).getSummary();
			summary = RegExUtils.replaceAll(summary, "\r\n", "<br/>");
			//summary = StringEscapeUtils.escapeHtml4(summary);
			String details = ((Message)value).getDetails();
			details = RegExUtils.replaceAll(details, "\r\n", "<br/>");
			//details = StringEscapeUtils.escapeHtml4(details);
			return (T) new FacesMessage(severity, summary, details);
		}
		return super.convert(value, klass);
	}
	
}
