package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Map;

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProperty(String key,String nullValue){
		return new MapHelper.GetValue.String.Adapter.Default<java.lang.String>((Map)System.getProperties(),java.lang.String.class)
				.setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, key)
				.setProperty(MapHelper.GetValue.PROPERTY_NAME_NULL_VALUE, nullValue)
				.execute();
	}
	
	public String getProperty(String key){
		return getProperty(key, (String)null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <VALUE> VALUE getPropertyAs(Class<VALUE> valueClass,String key,String nullValue){
		return (VALUE) MapHelper.getInstance().getStringValueAs(valueClass, (Map)System.getProperties(), key, nullValue); 
	}
	
	public <VALUE> VALUE getPropertyAs(Class<VALUE> valueClass,String key){
		return getPropertyAs(valueClass, key, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getEnvironmentVariable(String key){
		return new MapHelper.GetValue.String.Adapter.Default<java.lang.String>((Map)System.getenv(),java.lang.String.class)
				.setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, key)
				.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_CASE_SENSITIVE, Boolean.FALSE)
				.execute();
	}
	
}
