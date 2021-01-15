package org.cyk.utility.report.jasper.server;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.report.ReportGetter;
import org.junit.jupiter.api.Test;

public class JasperServerUnitTestProdProduireNotificationGestionnaire extends AbstractWeldUnitTest {

	//@Test
	public void produce() throws IOException{
		VariableHelper.write(VariableName.ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.URL, "http://10.3.4.24:8080/jasperserver/");
		VariableHelper.write(VariableName.CREDENTIAL_USERNAME, "jasperadmin");
		VariableHelper.write(VariableName.CREDENTIAL_PASSWORD, "jasperadmin");
		String uri = "/reports/sigobe/Notifications_Budgetaires/Par_Service_Gestionnaire/notification_gestionnaire_recap_et_detail";
		Collection<String> strings = IOUtils.readLines(getClass().getResourceAsStream("sigobe/gestionnaires.txt"));
		for(String string : strings) {
			String gc = StringUtils.substringBefore(string, ",");
			String section = StringUtils.substringAfter(string, ",");
			Map<Object,Object> parameters = Map.of("exercice","2021","code_section",section,"code_gestionnaire",gc);
			try {
				System.out.print("Producing : "+gc+" , "+section+"...");
				FileUtils.writeByteArrayToFile(new File("E:/Notifications 2021/"+gc+"_"+section+".pdf"), ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
				System.out.println(" : OK");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void produce_thread() throws IOException{
		VariableHelper.write(VariableName.ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.URL, "http://10.3.4.24:8080/jasperserver/");
		VariableHelper.write(VariableName.CREDENTIAL_USERNAME, "jasperadmin");
		VariableHelper.write(VariableName.CREDENTIAL_PASSWORD, "jasperadmin");
		String uri = "/reports/sigobe/Notifications_Budgetaires/Par_Service_Gestionnaire/notification_gestionnaire_recap_et_detail_duplicate";
		List<String> strings = IOUtils.readLines(getClass().getResourceAsStream("sigobe/gestionnaires.txt"));
		strings = strings.subList(0, 1);
		strings.parallelStream().forEach(string -> {
			String gc = StringUtils.substringBefore(string, ",");
			String section = StringUtils.substringAfter(string, ",");
			String fileName = "E:/Notifications 2021 TEST/"+section+"_"+gc+".pdf";
			File file = new File(fileName);
			if(file.exists())
				return;
			Map<Object,Object> parameters = Map.of("exercice","2021","code_section",section,"code_gestionnaire",gc,"reportLocale",Locale.FRENCH.toString());
			try {
				//System.out.print("Producing : "+gc+" , "+section+"...");
				FileUtils.writeByteArrayToFile(file, ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
				//System.out.println(" : OK");
				System.out.println("Produce : "+gc+" , "+section);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	/*
	//@Test
	public void produce_thread_pool() throws IOException{
		//System.out.println("JasperServerUnitTestProdProduireNotificationGestionnaire.produce_thread_pool()");
		VariableHelper.write(VariableName.ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.URL, "http://10.3.4.24:8080/jasperserver/");
		VariableHelper.write(VariableName.CREDENTIAL_USERNAME, "jasperadmin");
		VariableHelper.write(VariableName.CREDENTIAL_PASSWORD, "jasperadmin");
		String uri = "/reports/sigobe/Notifications_Budgetaires/Par_Service_Gestionnaire/notification_gestionnaire_recap_et_detail";
		Collection<String> strings = IOUtils.readLines(getClass().getResourceAsStream("sigobe/gestionnaires.txt"));
		
		Executor executor =  Executors.newFixedThreadPool(5,
              (Runnable r) -> {
                  Thread t = new Thread(r);
                  t.setDaemon(true);return t;
                  });
 
      strings.stream()
              .map(string -> CompletableFuture.supplyAsync(
                      () -> build(uri,string), executor))
              .collect(Collectors.toList());

      
      //CompletableFuture.allOf(futuresLignesEnv).join();
      
      try {
		Thread.sleep(5000 * 60l);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      System.out.println("Done!");
	}
	/*
	public static Boolean build(String uri,String string) {
		String gc = StringUtils.substringBefore(string, ",");
		String section = StringUtils.substringAfter(string, ",");
		String fileName = "E:/Notifications 2021/"+section+"_"+gc+".pdf";
		File file = new File(fileName);
		if(file.exists())
			return Boolean.FALSE;
		Map<Object,Object> parameters = Map.of("exercice","2021","code_section",section,"code_gestionnaire",gc);
		try {
			System.out.println("Producing : "+gc+" , "+section+"...");
			//ReportGetter.getInstance().get(uri,parameters,FileType.PDF);
			FileUtils.writeByteArrayToFile(file, ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
			//System.out.println(" : OK");
			System.out.println("Produce : "+section+" , "+gc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}*/
}