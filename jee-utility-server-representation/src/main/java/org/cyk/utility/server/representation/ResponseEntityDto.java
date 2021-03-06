package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.collection.CollectionHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
public class ResponseEntityDto extends AbstractEntityDto implements  Serializable {
	private static final long serialVersionUID = 1L;

	private String status;
	private MessageDtoCollection messageCollection;
	
	public ResponseEntityDto setStatusUsingEnumeration(Status status) {
		return setStatus(status == null ? null : status.name());
	}
	
	public ResponseEntityDto addMessage(Collection<MessageDto> messages) {
		if(__inject__(CollectionHelper.class).isNotEmpty(messages)) {
			if(this.messageCollection == null)
				this.messageCollection = new MessageDtoCollection();
			this.messageCollection.add(messages);
		}
		return this;
	}
	
	public ResponseEntityDto addMessage(MessageDto...messages) {
		return addMessage(__inject__(CollectionHelper.class).instanciate(messages));
	}
	
	public Collection<MessageDto> getMessages(){
		return messageCollection == null ? null : messageCollection.getCollection();
	}
	
	public MessageDto getMessageAt(Integer index) {
		return __inject__(CollectionHelper.class).getElementAt(getMessages(),index);
	}
	
	/**/
	
	public static enum Status {
		SUCCESS
		,FAILURE
	}
	
}
