package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class DatePeriodStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	private DateStringBuilder dateStringBuilder1 = new DateStringBuilder();
	private DateStringBuilder dateStringBuilder2 = new DateStringBuilder();
	
	{
		setSeparator(Constant.CHARACTER_MINUS.toString());
	}
	
	public DatePeriodStringBuilder(Date date1,Date date2,Constant.Date.Part part){
		setDates(date1, date2, part);
	}
	
	public DatePeriodStringBuilder(DateBuilder dateBuilder1,DateBuilder dateBuilder2,Constant.Date.Part part){
		setDates(dateBuilder1, dateBuilder2, part);
	}
	
	@Override
	protected String buildWhenBlank() {
		return dateStringBuilder1.build()+buildSeparator()+dateStringBuilder2.build();
	}
	
	public DatePeriodStringBuilder setDates(Date date1,Date date2,Constant.Date.Part part){
		setDate1(date1);
		setDate2(date2);
		getDateStringBuilder1().setPart(part);
		getDateStringBuilder2().setPart(part);
		return this;
	}
	
	public DatePeriodStringBuilder setDates(DateBuilder dateBuilder1,DateBuilder dateBuilder2,Constant.Date.Part part){
		return setDates(dateBuilder1.build(), dateBuilder2.build(), part);
	}
	
	public DatePeriodStringBuilder setDate1(Date date){
		dateStringBuilder1.setDate(date);
		return this;
	}
	
	public DatePeriodStringBuilder setDate2(Date date){
		dateStringBuilder2.setDate(date);
		return this;
	}
	
	@Override
	protected Collection<Listener> getListeners() {
		return Listener.COLLECTION;
	}
		
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}