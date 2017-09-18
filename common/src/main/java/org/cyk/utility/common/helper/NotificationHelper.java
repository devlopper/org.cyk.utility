package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.helper.StringHelper.ToStringMapping;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class NotificationHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static NotificationHelper INSTANCE;
	public static enum SeverityType{
		ERROR
		,WARNING
		,INFO
		;
		
		public static SeverityType DEFAULT = INFO;
	}
	
	public static NotificationHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new NotificationHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public NotificationHelper.Notification getNotification(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(NotificationHelper.Notification.DEFAULT_CLASS , NotificationHelper.Notification.class));
	}
	
	public NotificationHelper.Notification getNotification(String summaryIdentifier,Object[] parameters){
		NotificationHelper.Notification.Builder notificationBuilder = new NotificationHelper.Notification.Builder.Adapter.Default();
		notificationBuilder.getSummaryStringMapping(Boolean.TRUE).setInput(summaryIdentifier).addParameters(parameters);
		notificationBuilder.getDetailsStringMapping(Boolean.TRUE).setInput(summaryIdentifier).addParameters(parameters);
		Notification notification = notificationBuilder.execute();
		return notification;
	}
	
	public NotificationHelper.Notification getNotification(String summaryIdentifier){
		return getNotification(summaryIdentifier, (Object[])null);
	}
	
	public NotificationHelper.Notification.Viewer getViewer(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS , NotificationHelper.Notification.Viewer.Adapter.Default.class)); 
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Notification extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
	
		@SuppressWarnings("unchecked")
		public static Class<Notification> DEFAULT_CLASS = (Class<Notification>) ClassHelper.getInstance().getByName(Notification.class);
		
		private SeverityType severityType;
		private String summary;
		private String details;
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Notification> {
			
			SeverityType getSeverityType();
			NotificationHelper.Notification.Builder setSeverityType(SeverityType severityType);
			
			StringHelper.ToStringMapping getSummaryStringMapping(Boolean createIfNull);
			StringHelper.ToStringMapping getSummaryStringMapping();
			Builder setSummaryStringMapping(StringHelper.ToStringMapping summaryStringMapping);
			
			StringHelper.ToStringMapping getDetailsStringMapping(Boolean createIfNull);
			StringHelper.ToStringMapping getDetailsStringMapping();
			Builder setDetailsStringMapping(StringHelper.ToStringMapping detailsStringMapping);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter<Notification> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;

				protected SeverityType severityType;
				protected StringHelper.ToStringMapping summaryStringMapping,detailsStringMapping;
				
				public Adapter() {
					super(Notification.class);
				}
				
				@Override
				public ToStringMapping getSummaryStringMapping(Boolean createIfNull) {
					return null;
				}
				
				@Override
				public ToStringMapping getDetailsStringMapping(Boolean createIfNull) {
					return null;
				}
				
				@Override
				public Builder setSeverityType(SeverityType severityType) {
					return null;
				}
				
				@Override
				public Builder setSummaryStringMapping(ToStringMapping summaryStringMapping) {
					return null;
				}
				
				@Override
				public Builder setDetailsStringMapping(ToStringMapping detailsStringMapping) {
					return null;
				}
				
				/**/
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					{
						setIsInputRequired(Boolean.FALSE);
					}
					
					@Override
					public Builder setSummaryStringMapping(ToStringMapping summaryStringMapping) {
						this.summaryStringMapping = summaryStringMapping;
						return this;
					}
					
					@Override
					public Builder setDetailsStringMapping(ToStringMapping detailsStringMapping) {
						this.detailsStringMapping = detailsStringMapping;
						return this;
					}
					
					@Override
					public ToStringMapping getSummaryStringMapping(Boolean createIfNull) {
						if(this.summaryStringMapping == null){
							this.summaryStringMapping = new ToStringMapping.Adapter.Default();
							summaryStringMapping.setCaseType(CaseType.FU);
						}
						return summaryStringMapping;
					}
					
					@Override
					public ToStringMapping getDetailsStringMapping(Boolean createIfNull) {
						if(this.detailsStringMapping == null){
							this.detailsStringMapping = new ToStringMapping.Adapter.Default();
							detailsStringMapping.setCaseType(CaseType.FU);
						}
						return detailsStringMapping;
					}
					
					@Override
					public Builder setSeverityType(SeverityType severityType) {
						this.severityType = severityType;
						return this;
					}
					
					@Override
					protected Notification __execute__() {
						Notification notification = getInstance().getNotification();
						notification.setSeverityType(InstanceHelper.getInstance().getIfNotNullElseDefault(getSeverityType(), SeverityType.DEFAULT));
						ToStringMapping summary = getSummaryStringMapping();
						if(summary!=null)
							notification.setSummary(summary.execute());
						ToStringMapping details = getSummaryStringMapping();
						if(details!=null)
							notification.setDetails(details.execute());
						return notification;
					}
					
				}	
			}
		}
		
		/**/
		
		public static interface Viewer extends Action<Notification, Void> {
			
			public static enum Type {
				INLINE
				,DIALOG
				,GROWL
				; 
				public static Type DEFAULT = Type.INLINE;
			}
			
			Type getType();
			Viewer setType(Type type);
			
			@Override Viewer setInput(Notification notification);
			
			@Getter @Setter
			public static class Adapter extends Action.Adapter.Default<Notification, Void> implements Viewer,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Type type;
				
				public Adapter(Notification input) {
					super("view", Notification.class, input, Void.class);
				}
				
				@Override
				public Viewer setType(Type type) {
					return null;
				}
				
				@Override
				public Viewer setInput(Notification input) {
					return (Viewer) super.setInput(input);
				}
				
				public static class Default extends Viewer.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					public static Class<Viewer> DEFAULT_CLASS = (Class<Viewer>) ClassHelper.getInstance().getByName(Default.class);
					
					public Default(Notification notification) {
						super(notification);
					}
					
					public Default() {
						super(null);
					}
				
					@Override
					public Viewer setType(Type type) {
						this.type = type;
						return this;
					}
					
					@Override
					protected Void __execute__() {
						Notification notification = getInput();
						Type type = InstanceHelper.getInstance().getIfNotNullElseDefault(getType(), Type.DEFAULT);
						__execute__(notification, type);
						return null;
					}
					
					protected void __execute__(Notification notification,Type type) {
						switch(type){
						default : System.out.println(notification.getSummary()+" : "+notification.getDetails());
						}
					}
					
				}	
			}
			
		}
	}
	
}
