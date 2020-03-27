package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MessageRenderer {

	MessageRenderer clear();
	
	void render(Collection<Message> messages,Collection<RenderType> renderTypes);
	
	default void render(Message message,Collection<RenderType> renderTypes) {
		if(message == null)
			return;
		render(CollectionHelper.listOf(message), renderTypes);
	}
	
	default void render(Message message,RenderType...renderTypes) {
		if(ArrayHelper.isEmpty(renderTypes))
			renderTypes = new RenderType[] {RenderType.DIALOG};
		render(message, CollectionHelper.listOf(renderTypes));
	}
	
	default void render(String summary,String details,Severity severity,Collection<RenderType> renderTypes) {
		render(new Message().setSummary(summary).setDetails(details).setSeverity(severity), renderTypes);
	}
	
	default void render(String summary,String details,Collection<RenderType> renderTypes) {
		render(new Message().setSummary(summary).setDetails(details).setSeverity(Severity.INFORMATION), renderTypes);
	}
	
	default void render(String summary,Severity severity,Collection<RenderType> renderTypes) {
		render(new Message().setSummary(summary).setSeverity(severity), renderTypes);
	}
	
	default void render(String summary,Severity severity,RenderType...renderTypes) {
		render(new Message().setSummary(summary).setSeverity(severity), renderTypes);
	}
	
	default void render(String summary,Collection<RenderType> renderTypes) {
		render(new Message().setSummary(summary).setSeverity(Severity.INFORMATION), renderTypes);
	}
	
	default void render(String summary,RenderType...renderTypes) {
		render(new Message().setSummary(summary).setSeverity(Severity.INFORMATION), renderTypes);
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements MessageRenderer,Serializable {

		@Override
		public void render(Collection<Message> messages, Collection<RenderType> renderTypes) {
			if(CollectionHelper.isEmpty(messages) || CollectionHelper.isEmpty(renderTypes))
				return;
			__render__(messages, renderTypes);
		}
		
		protected abstract void __render__(Collection<Message> messages, Collection<RenderType> renderTypes);
	}
	
	/**/
	
	static MessageRenderer getInstance() {
		return Helper.getInstance(MessageRenderer.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Arguments implements Serializable {
		
		private Severity severity;
		private Collection<RenderType> renderTypes;
		
		public Arguments(Arguments arguments) {
			if(arguments == null)
				return;
			this.severity = arguments.severity;
			if(CollectionHelper.isNotEmpty(arguments.renderTypes))
				this.renderTypes = new ArrayList<>(arguments.renderTypes);			
		}
		
		public Collection<RenderType> getRenderTypes(Boolean injectIfNull) {
			if(renderTypes == null && Boolean.TRUE.equals(injectIfNull))
				renderTypes = new ArrayList<>();
			return renderTypes;
		}
		
		public Arguments addRenderTypes(Collection<RenderType> renderTypes) {
			if(CollectionHelper.isEmpty(renderTypes))
				return this;
			getRenderTypes(Boolean.TRUE).addAll(renderTypes);
			return this;
		}
		
		public Arguments addRenderTypes(RenderType...renderTypes) {
			if(ArrayHelper.isEmpty(renderTypes))
				return this;
			addRenderTypes(CollectionHelper.listOf(renderTypes));
			return this;
		}
		
		/**/
		
		public static final Arguments INFORMATION_INLINE_DIALOG = new Arguments().setSeverity(Severity.INFORMATION).addRenderTypes(RenderType.INLINE,RenderType.DIALOG);
		public static final Arguments INFORMATION_INLINE = new Arguments().setSeverity(Severity.INFORMATION).addRenderTypes(RenderType.INLINE);
		public static final Arguments INFORMATION_GROWL = new Arguments().setSeverity(Severity.INFORMATION).addRenderTypes(RenderType.GROWL);
		
		public static final Arguments WARNING_INLINE_DIALOG = new Arguments().setSeverity(Severity.WARNING).addRenderTypes(RenderType.INLINE,RenderType.DIALOG);
		public static final Arguments WARNING_INLINE = new Arguments().setSeverity(Severity.WARNING).addRenderTypes(RenderType.INLINE);
		public static final Arguments WARNING_GROWL = new Arguments().setSeverity(Severity.WARNING).addRenderTypes(RenderType.GROWL);
		
		public static final Arguments ERROR_INLINE_DIALOG = new Arguments().setSeverity(Severity.ERROR).addRenderTypes(RenderType.INLINE,RenderType.DIALOG);
		public static final Arguments ERROR_INLINE = new Arguments().setSeverity(Severity.ERROR).addRenderTypes(RenderType.INLINE);
		public static final Arguments ERROR_GROWL = new Arguments().setSeverity(Severity.ERROR).addRenderTypes(RenderType.GROWL);
	}
}