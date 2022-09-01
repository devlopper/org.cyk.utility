package org.cyk.utility.mail.postmark;

import java.io.IOException;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;

public class MainTest {

	public static void main(String[] args) throws PostmarkException, IOException {	
		/*
		ApiClient client = Postmark.getApiClient("ca8c7c8e-b623-43c0-bf0a-ba319878f63a");
		Message message = new Message("assistance.sigobe@budget.gouv.ci", "c.komenan@budget.gouv.ci", "Hello from Postmark!", "Hello message body");
		MessageResponse response = client.deliverMessage(message);
		System.out.println(response.getErrorCode());
		System.out.println("DONE");
		*/
		
		ApiClient client = Postmark.getApiClient("ca8c7c8e-b623-43c0-bf0a-ba319878f63a");
		Message message = new Message("c.komenan@budget.gouv.ci", "assistance.sigobe@budget.gouv.ci", "Hello from Postmark!", "Hello message body");
		message.setMessageStream("outbound");
		MessageResponse response = client.deliverMessage(message);
	}

}