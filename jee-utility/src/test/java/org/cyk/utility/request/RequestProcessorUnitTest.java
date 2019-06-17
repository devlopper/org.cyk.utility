package org.cyk.utility.request;

//import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RequestProcessorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void executeGetString(){
		/*
		String response = (String) __inject__(RequestProcessor.class)
				.setUniformResourceIdentifierString("http://10.3.4.20:32202/sib/classification-par-programme/api/v1/programmes/code/22060")
				.execute().getOutput();
		org.assertj.core.api.Assertions.assertThat(response)
			.contains("\"uuid\":\"2521444c-0058-4ab7-b18e-c5d27f785da3\"","\"code\":\"22060\"","\"libelleCourt\":\"TRANSPORT TERRESTRE\"");
		*/
	}
	
	@Test
	public void executeGetDto(){
		/*
		Dto dto = (Dto) __inject__(RequestProcessor.class)
				.setUniformResourceIdentifierString("http://10.3.4.20:32202/sib/classification-par-programme/api/v1/programmes/code/22060")
				.setResponseEntityClass(Dto.class)
				.execute().getOutput();
		assertThat(dto.getUuid()).isEqualTo("2521444c-0058-4ab7-b18e-c5d27f785da3");
		assertThat(dto.getCode()).isEqualTo("22060");
		assertThat(dto.getLibelleCourt()).isEqualTo("TRANSPORT TERRESTRE");
		*/
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Dto {
		private String uuid;
		private String code;
		private String codeMetier;
		private String libelleCourt;

	}
	
	
}
