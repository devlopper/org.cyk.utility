package org.cyk.utility.css;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.device.Device;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.throwable.ThrowableHelperImpl;
import org.cyk.utility.__kernel__.value.ValueHelper;

@ApplicationScoped
public class CascadeStyleSheetHelperImpl extends AbstractHelper implements CascadeStyleSheetHelper, Serializable {
	private static final long serialVersionUID = 1L;

	private static CascadeStyleSheetHelper INSTANCE;
	public static CascadeStyleSheetHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(CascadeStyleSheetHelper.class);
		return INSTANCE;
	}
	public static CascadeStyleSheetHelper getInstance() {
		return getInstance(null);
	}
	
	private StyleClassProportionGrid styleClassProportionGrid;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setStyleClassProportionGrid(StyleClassProportionGrid.DEFAULT);
	}
	
	@Override
	public StyleClassProportionGrid getStyleClassProportionGrid() {
		return styleClassProportionGrid;
	}
	
	@Override
	public CascadeStyleSheetHelper setStyleClassProportionGrid(StyleClassProportionGrid styleClassProportionGrid) {
		this.styleClassProportionGrid = styleClassProportionGrid;
		return this;
	}
	
	@Override
	public Collection<String> getStyleClassesFromRoles(Collection<?> roles) {
		return __getStyleClassesFromRoles__(roles);
	}
	
	@Override
	public Collection<String> getStyleClassesFromRoles(Object... roles) {
		return __getStyleClassesFromRoles__(List.of(roles));
	}
	
	@Override
	public String buildStyleClassProportion(Class<? extends Device> deviceClass,Integer proportion) {
		StyleClassProportionGrid styleClassProportionGrid = getStyleClassProportionGrid();
		ValueHelper.throwIfBlank("style class proportion grid",styleClassProportionGrid);
		return styleClassProportionGrid.get(deviceClass, proportion);
	}
	
	/**/
	
	public static Collection<String> __getStyleClassesFromRoles__(Collection<?> roles) {
		if(roles != null && !roles.isEmpty())
			return roles.stream().map(x -> PREFIX+x.toString().toLowerCase()).collect(Collectors.toSet());
		return null;
	}
	
	public static String __buildStyleClassProportion__(Integer proportion,Integer defaultProportion,Integer maximalProportion,String format) {
		proportion = ValueHelper.defaultToIfNull(proportion, defaultProportion);
		ValueHelper.throwIfBlank("style class proportion",proportion);
		ValueHelper.throwIfBlank("style class maximal proportion",maximalProportion);
		if(proportion > maximalProportion)
			ThrowableHelperImpl.__throwRuntimeException__(String.format("style class proportion (%s) must be less than or equal to maximal proportion (%s)",proportion,maximalProportion));
		ValueHelper.throwIfBlank("style class proportion format",format);
		return String.format(format, proportion);
	}
	
	public static String __buildStyleClassProportion__(Integer proportion,StyleClassProportionGrid styleClassProportion,Class<? extends Device> deviceClass) {
		return __buildStyleClassProportion__(proportion, styleClassProportion.getMaximal(), styleClassProportion.getMaximal(), styleClassProportion.getFormat());
	}
	
	/**/
	
}
