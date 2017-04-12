package org.cyk.utility.common.message;

import java.io.Serializable;
import java.util.Date;

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

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage.Builder;
import org.cyk.utility.common.message.Message.Attachement;

public interface SendMail extends Send<InternetAddress> {
	
	Session getSession();
	SendMail setSession(Session session);
	
	@Getter @Setter
	public static class Adapter extends Send.Adapter.Default<InternetAddress> implements SendMail,Serializable {
		private static final long serialVersionUID = 1L;

		private Session session;
		
		public Adapter(Message message,Builder logMessageBuilder) {
			super(message, logMessageBuilder);
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

			public Default(Message message,Builder logMessageBuilder) {
				super(message, logMessageBuilder);
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
				
				if(getInput().getReceiverIdentifiers()==null || getInput().getReceiverIdentifiers().isEmpty())
					getInput().getReceiverIdentifiers().add(getProperties().getProperty(Constant.SimpleMailTransferProtocol.PROPERTY_USERNAME));
				
				if(getInput().getReceiverIdentifiers()==null || getInput().getReceiverIdentifiers().isEmpty())
					throw new RuntimeException("receiver identifier(s) required");
				
				if(getSession()==null){
					if(getProperties()==null)
						throw new RuntimeException("Properties are required to create a session");
					addLogMessageBuilderParameters(getLogMessageBuilder(),"properties", getProperties());
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
					message.setFrom(getAddress(StringUtils.defaultIfBlank(getInput().getSenderIdentifier(),getProperties().getProperty(Constant.SimpleMailTransferProtocol.PROPERTY_USERNAME))));
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
		                			logMessageBuilder.addParameters("mime file empty , id",attachement);
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
			public void setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password) {
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
			}
			
			private void addProperty(String name,Object value){
		    	getProperties().put(Constant.SimpleMailTransferProtocol.getProperty(name,Boolean.FALSE), value);
		    	getProperties().put(Constant.SimpleMailTransferProtocol.getProperty(name,Boolean.TRUE), value);
		    }
			
		}
		
	}
	
}
