package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.MessagingHelper.Message.Attachement;

import lombok.Getter;
import lombok.Setter;

public class MessagingHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static MessagingHelper INSTANCE;
	
	public static MessagingHelper getInstance() {
		if(INSTANCE == null) 
			INSTANCE = new MessagingHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Transfer extends Action<Object, Void> {
		
		public static class Adapter extends Action.Adapter.Default<Object, Void> implements Transfer , Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super("transfer", Object.class, null, Void.class);
			}
			
			public static class Default extends Transfer.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}			
		}		
	}
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class Message implements Serializable {

		private static final long serialVersionUID = 1L;

		private String subject,content,mime="text/html";
		private Date date;
		private Collection<Attachement> attachments;
		
		private String senderIdentifier;
		private Set<String> receiverIdentifiers = new LinkedHashSet<>();
		
		/**/
		
		public Message addReceiverIdentifiers(String...identifiers){
			for(String identifier : identifiers)
				receiverIdentifiers.add(identifier);
			return this;
		}
		
		public Message addAttachement(byte[] bytes,String mime,String name){
			if(attachments==null)
				attachments = new ArrayList<>();
			Attachement attachement = new Attachement();
			attachement.setBytes(bytes);
			attachement.setMime(mime);
			attachement.setName(name);
			attachments.add(attachement);
			return this;
		}
		
		/**/
		
		@Getter @Setter
		public static class Attachement implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private String mime;
			private byte[] bytes;
			private String name;
			
		}
	}
	
	public interface Send<ADDRESS> extends Action<Message, Void> {

		Send<ADDRESS> setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password);
		Send<ADDRESS> setHostAndUserProperties(String host, Integer port, String username, String password);
		
		void ping();
		
		/**/
		
		@Getter @Setter
		public static class Adapter<ADDRESS> extends Action.Adapter.Default<Message, Void> implements Send<ADDRESS>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Message message) {
				super("send", Message.class, message, Void.class);
			}		
			
			protected ADDRESS getAddress(String identifier) throws Exception{
				throw new RuntimeException("get address from identifier "+identifier+" Not yet implemented");
			}
			
			@Override
			public Send<ADDRESS> setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password) {
				return this;
			}
			
			@Override
			public Send<ADDRESS> setHostAndUserProperties(String host, Integer port,String username, String password) {
				return setHostAndUserProperties(host, port,Boolean.TRUE, username, password);
			}
			
			@Override
			public void ping() {
				Message message = new Message();
				message.setSubject("Ping");
				message.setContent("This is a ping!");
				setInput(message);
				execute();
			}
			
			protected Collection<ADDRESS> getAddresses(){
				Collection<ADDRESS> addresses = new ArrayList<>();
				for(String identifier : getInput().getReceiverIdentifiers())
					try {
						addresses.add(getAddress(identifier));
					} catch (Exception e) {
						e.printStackTrace();
					}
				return addresses;
			}
			
			/**/
			@Getter @Setter
			public static class Default<ADDRESS> extends Send.Adapter<ADDRESS> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Message message) {
					super(message);
				}	
			}	
		}

		/**/
		
		public interface SendMail extends Send<InternetAddress> {
			
			Session getSession();
			SendMail setSession(Session session);
			
			@Getter @Setter
			public static class Adapter extends Send.Adapter.Default<InternetAddress> implements SendMail,Serializable {
				private static final long serialVersionUID = 1L;

				private Session session;
				
				public Adapter(Message message) {
					super(message);
				}
				
				public SendMail setSession(Session session){
					this.session = session;
					return this;
				}
				
				/**/
				
				/**/
				
				@Getter @Setter
				public static class Default extends SendMail.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default() {
						this(null);
					}
					
					public Default(Message message) {
						super(message);
					}
					
					@Override
					protected InternetAddress getAddress(String identifier) throws Exception {
						return new InternetAddress(identifier);
					}
					
					@Override
					protected Void __execute__() {
						if(StringUtils.isBlank(getInput().getSubject()))
							throw new RuntimeException("Subject is required");
						if(StringUtils.isBlank(getInput().getContent()) && (getInput().getAttachments()==null || getInput().getAttachments().isEmpty()))
							throw new RuntimeException("content and/or attachement(s) are/is required");
						String from = StringUtils.defaultIfBlank(getInput().getSenderIdentifier(),getProperties().getProperty(Constant.SimpleMailTransferProtocol.PROPERTY_USERNAME));
						if(getInput().getReceiverIdentifiers()==null || getInput().getReceiverIdentifiers().isEmpty())
							getInput().addReceiverIdentifiers(from);
						
						if(getInput().getReceiverIdentifiers()==null || getInput().getReceiverIdentifiers().isEmpty())
							throw new RuntimeException("receiver identifier(s) required");
						
						if(getSession()==null){
							if(getProperties()==null)
								throw new RuntimeException("Properties are required to create a session");
							addLoggingMessageBuilderParameters("properties", getProperties());
							setSession(Session.getInstance(getProperties(),new Authenticator() {
					    		@Override
					    		protected PasswordAuthentication getPasswordAuthentication() {
					    			return new PasswordAuthentication(getProperties().getProperty(Constant.SimpleMailTransferProtocol.PROPERTY_USERNAME)
					    					, getProperties().getProperty(Constant.SimpleMailTransferProtocol.PROPERTY_PASSWORD));
					    		}
							}));
					    	getSession().setDebug(getAutomaticallyLogMessage());
						}
						
						InternetAddress[] addresses = getAddresses().toArray(new InternetAddress[]{});
						MimeMessage message = new MimeMessage(getSession());
			            try {
							message.setFrom(getAddress(from));
							message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
				            message.setSubject(StringUtils.defaultIfEmpty(getInput().getSubject(),"No subject"));
				            message.setSentDate(getValueIfNotNullElseDefault(Date.class, getInput().getDate(), new Date()));
				            
				            if(getInput().getAttachments()==null || getInput().getAttachments().isEmpty()){
				            	message.setContent(getInput().getContent(), getInput().getMime()+"; charset=utf-8");
				            }else{ 
				            	Multipart multipart = new MimeMultipart();
				                if(getInput().getAttachments()!=null) 
				                	for(Attachement attachement : getInput().getAttachments()){
				                		if(StringUtils.isBlank(attachement.getMime())){
				                			addLoggingMessageBuilderParameters("mime file empty , id",attachement);
				                		}else{
				                			MimeBodyPart bodyPart = new MimeBodyPart();
				                            bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachement.getBytes(), attachement.getMime())));
				                            bodyPart.setFileName(attachement.getName());
				                            multipart.addBodyPart(bodyPart);
				                		}
				                	}
				                if(StringUtils.isNotBlank(getInput().getContent())){
				                    BodyPart htmlBodyPart = new MimeBodyPart();
				                    htmlBodyPart.setContent(getInput().getContent() , getInput().getMime());
				                    multipart.addBodyPart(htmlBodyPart);
				                }
				                
				                message.setContent(multipart);
				            }
				            System.out.println("sending message to "+getInput().getReceiverIdentifiers());
				            Transport.send(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
					
					@Override
					public SendMail setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password) {
						super.setHostAndUserProperties(host, port, secured, username, password);
						addProperty(Constant.SimpleMailTransferProtocol.HOST, host);
						addProperty(Constant.SimpleMailTransferProtocol.FROM, username);
						addProperty(Constant.SimpleMailTransferProtocol.USER, username);
						addProperty(Constant.SimpleMailTransferProtocol.PASSWORD, password);
						
						//addProperty(properties, "socketFactory.port", smtpProperties.getSocketFactory().getPort());
						addProperty(Constant.SimpleMailTransferProtocol.PORT, port);
						//addProperty(properties, "socketFactory.fallback", smtpProperties.getSocketFactory().getFallback());
						addProperty(Constant.SimpleMailTransferProtocol.AUTH, StringUtils.isNotBlank(password));
						//addProperty(properties, "socketFactory.class", smtpProperties.getSocketFactory().getClazz());
						
						if(Boolean.TRUE.equals(secured)){
							addProperty(Constant.SimpleMailTransferProtocol.STARTTLS_ENABLE, secured);
							addProperty(Constant.SimpleMailTransferProtocol.SSL_ENABLE, secured);
						}
						return this;
					}
					
					private void addProperty(String name,Object value){
				    	getProperties().put(Constant.SimpleMailTransferProtocol.getProperty(name,Boolean.FALSE), value);
				    	getProperties().put(Constant.SimpleMailTransferProtocol.getProperty(name,Boolean.TRUE), value);
				    }	
				}	
			}	
		}		
	}
	
}
