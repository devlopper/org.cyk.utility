package org.cyk.utility.css;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;
import org.cyk.utility.device.DeviceTelevision;
import org.cyk.utility.helper.Helper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface CascadeStyleSheetHelper extends Helper {

	Collection<String> getStyleClassesFromRoles(Collection<?> roles);
	Collection<String> getStyleClassesFromRoles(Object...roles);
	
	String buildStyleClassProportion(Class<? extends Device> deviceClass,Integer proportion);
	
	StyleClassProportionGrid getStyleClassProportionGrid();
	CascadeStyleSheetHelper setStyleClassProportionGrid(StyleClassProportionGrid styleClassProportionGrid);
	
	String PREFIX = "cyk_";
	
	@Getter @Accessors(chain=true)
	public static enum StyleClassProportionGrid {
		
		PRIMEFACES_GRID_CSS("ui-%s-%s",12,null,null)
		
		;
		
		@Setter private String format;
		@Setter private Integer maximal;
		@Setter private Map<Class<Device>,Integer> defaultProportions = new HashMap<>();
		@Setter private Map<Class<Device>,String> tokens = new HashMap<>();
		
		private StyleClassProportionGrid(String format,Integer maximal,Object[] defaultProportions,Object[] tokens) {
			this.format = format;
			this.maximal = maximal;
			if(defaultProportions == null)
				defaultProportions = new Object[] {Device.class,12,DeviceTelevision.class,12,DeviceDesktop.class,12,DeviceTablet.class,12,DevicePhone.class,12};
			MapUtils.putAll(this.defaultProportions, defaultProportions);
			if(tokens == null)
				tokens = new Object[] {Device.class,"g",DeviceTelevision.class,"xl",DeviceDesktop.class,"lg",DeviceTablet.class,"md",DevicePhone.class,"sm"};
			MapUtils.putAll(this.tokens, tokens);
		}
		
		public String getFormat(Class<? extends Device> deviceClass) {
			if(deviceClass == null)
				deviceClass = Device.class;
			return StringUtils.replaceOnce(format, "%s", tokens.get(deviceClass));
		}
		
		public String get(Class<? extends Device> deviceClass,Integer proportion) {
			if(deviceClass == null)
				deviceClass = Device.class;
			return StringUtils.replaceOnce(getFormat(deviceClass), "%s", proportion == null ? defaultProportions.get(deviceClass).toString() : proportion.toString());
		}
		
		/**/
		
		public static StyleClassProportionGrid DEFAULT = PRIMEFACES_GRID_CSS;
		
		public static Integer MAXIMAL_WIDTH = 12;
		
	}
}