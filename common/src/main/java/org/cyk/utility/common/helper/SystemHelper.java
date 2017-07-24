package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import javax.inject.Singleton;

@Singleton
public class SystemHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static SystemHelper INSTANCE;
	
	public static SystemHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new SystemHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getProperty(String key){
		Properties properties = System.getProperties();
		if(properties.containsKey(key))
			return System.getProperty(key);
		logWarning("system property <<{}>> not defined", key);
		return null;
	}
	
	public String getEnvironmentVariable(String key){
		Map<String, String> map = System.getenv();
		MapHelper.ContainsKey.String containsKey = new MapHelper.ContainsKey.String.Adapter.Default(key);
		containsKey.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map).setProperty(MapHelper.ContainsKey.PROPERTY_NAME_CASE_SENSITIVE, Boolean.FALSE);
		if(containsKey.execute())
			return map.get(containsKey.getProperty(MapHelper.ContainsKey.PROPERTY_NAME_KEY));
		logWarning("system environment variable <<{}>> not defined", key);
		return null;
	}
	
}
