package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Dimension extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Collection<Cell> cells;
	
	public Dimension addOneCell(Cell cell){
		if(cells == null)
			cells = new ArrayList<>();
		cells.add(cell);
		return this;
	}
	
	public Cell getCellByIndex(Integer index){
		if(index==null)
			return null;
		
		return CollectionHelper.getInstance().getElementAt(cells, index);
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Column> extends Component.Visible.BuilderBase<OUTPUT> {

		
		
		public static class Adapter<OUTPUT extends Column> extends Component.Visible.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Column> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Column> {

		public static class Adapter extends BuilderBase.Adapter.Default<Column> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Column.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected Column __execute__() {
					Column column = new Column();
					return column;
				}
				
			}
		}
	}
}