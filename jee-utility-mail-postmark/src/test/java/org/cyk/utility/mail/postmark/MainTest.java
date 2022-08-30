package org.cyk.utility.mail.postmark;

import java.io.IOException;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;

public class MainTest {

	public static void main(String[] args) throws PostmarkException, IOException {	
		ApiClient client = Postmark.getApiClient("fa2f17e3-b4ef-492e-87c5-37793f9105ca");
		Message message = new Message("votre-bulletin-de-solde@sigobe.dgbf.ci", "c.komenan@budget.gouv.ci", "Hello from Postmark!", "Hello message body");
		MessageResponse response = client.deliverMessage(message);
		System.out.println(response.getErrorCode());
		System.out.println("DONE");
	}

}