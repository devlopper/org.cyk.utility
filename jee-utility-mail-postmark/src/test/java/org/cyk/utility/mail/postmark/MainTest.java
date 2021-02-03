package org.cyk.utility.mail.postmark;

import java.io.IOException;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;

public class MainTest {

	public static void main(String[] args) throws PostmarkException, IOException {		
		ApiClient client = Postmark.getApiClient("33e65919-b008-4072-8712-074cb8dbaa37");
		Message message = new Message("no-reply@sigobe.dgbf.ci", "kycdev@gmail.com", "Hello from Postmark!", "Hello message body");
		MessageResponse response = client.deliverMessage(message);
		System.out.println(response.getErrorCode());
		System.out.println("DONE");
	}

}