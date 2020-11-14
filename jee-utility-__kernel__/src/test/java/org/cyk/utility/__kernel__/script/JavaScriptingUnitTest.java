package org.cyk.utility.__kernel__.script;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class JavaScriptingUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void print_infos() throws ScriptException{
		ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
	    for (ScriptEngineFactory factory : factories) {
	      System.out.println(factory.getEngineName());
	      System.out.println(factory.getEngineVersion());
	      System.out.println(factory.getLanguageName());
	      System.out.println(factory.getLanguageVersion());
	      System.out.println(factory.getExtensions());
	      System.out.println(factory.getMimeTypes());
	      System.out.println(factory.getNames());
	    }
	}
	
	@Test
	public void print_hello_world() throws ScriptException{
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        // evaluate JavaScript code
        Object result = engine.eval("print('Hello, World')");
		assertThat(result).isEqualTo(null);
	}
	
	@Test
	public void variable() throws ScriptException{
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.put("functionCode", "GC");
        engine.put("scopeCode", "13010222");      
        assertThat(engine.eval("functionCode+scopeCode")).isEqualTo("GC13010222");
	}
	
	@Test
	public void setField() throws ScriptException{
		MyClass o = new MyClass();
		o.setId("OV");
	        
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		engine.put("o", o);
  
        String script = "o.setId('NV');";
        assertThat(o.getId()).isEqualTo("OV");
        engine.eval(script);
        assertThat(o.getId()).isEqualTo("NV");
	}
	
	@Test
	public void setField_loop() throws ScriptException{
		MyClass o = new MyClass();
		o.setId("OV");
	        
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		engine.put("l", List.of(o));
  
        String script = "var i; for (i = 0; i < l.length; i++) {l.get(i).setId('NV');}";
        assertThat(o.getId()).isEqualTo("OV");
        engine.eval(script);
        assertThat(o.getId()).isEqualTo("NV");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {
		private String id;
	}
}