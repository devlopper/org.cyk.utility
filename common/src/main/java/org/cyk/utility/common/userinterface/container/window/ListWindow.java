package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;

public class ListWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean getIsAutomaticallySetForm() {
		return Boolean.FALSE;
	}
	
	@Override
	protected Boolean getIsAutomaticallySetDataTable() {
		return !Boolean.TRUE.equals(ClassHelper.getInstance().isHierarchy(actionOnClass));
	}
	
	@Override
	protected Boolean getIsAutomaticallySetHierarchy() {
		return Boolean.TRUE.equals(ClassHelper.getInstance().isHierarchy(actionOnClass));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> getDataTableClass(){
		return (Class<org.cyk.utility.common.userinterface.collection.DataTable>) ListWindow.DataTable.ClassLocator.getInstance().locate(actionOnClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy> getHierarchyClass(){
		return (Class<org.cyk.utility.common.userinterface.hierarchy.Hierarchy>) ListWindow.Hierarchy.ClassLocator.getInstance().locate(actionOnClass);
	}
	
	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setLayoutCardinalPointCenterSouthRendered(Boolean.FALSE);
	}
	
	/**/
	
	public static class DataTable extends org.cyk.utility.common.userinterface.collection.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class ClassLocator extends org.cyk.utility.common.userinterface.collection.DataTable.ClassLocator implements Serializable {
			private static final long serialVersionUID = 1L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
				super(Constant.Action.LIST);
			}
				
			public static ClassLocator getInstance() {
				if(INSTANCE == null){
					INSTANCE = ClassHelper.getInstance().instanciateOne(ClassLocator.class);
				}
				return INSTANCE;
			}
		}
	}
	
	public static class Hierarchy extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Hierarchy prepare() {
			if(getRenderType()==null)
				setRenderType(org.cyk.utility.common.userinterface.hierarchy.Hierarchy.RenderType.TABLE);
			getPropertiesMap().setIfNull(Properties.FILTERABLE, Boolean.FALSE);
			getPropertiesMap().setIfNull(Properties.SORTABLE, Boolean.FALSE);
			return (Hierarchy) super.prepare();
		}
		
		/**/
		
		public static class ClassLocator extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy.ClassLocator implements Serializable {
			private static final long serialVersionUID = 1L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
				super(Constant.Action.LIST);
			}
				
			public static ClassLocator getInstance() {
				if(INSTANCE == null){
					INSTANCE = ClassHelper.getInstance().instanciateOne(ClassLocator.class);
				}
				return INSTANCE;
			}
		}
	}

}
