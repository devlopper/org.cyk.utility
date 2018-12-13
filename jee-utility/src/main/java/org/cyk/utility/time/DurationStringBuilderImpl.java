package org.cyk.utility.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.AbstractStringFunctionImpl;

public class DurationStringBuilderImpl extends AbstractStringFunctionImpl implements DurationStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Duration duration;
	
	@Override
	protected String __execute__() throws Exception {
		Duration duration = getDuration();
		Collection<String> strings = null;
		if(duration == null) {
			
		}else {
			strings = new ArrayList<>();	
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfMinute()))
				strings.add(duration.getNumberOfMinute()+" min");
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfSecond()))
				strings.add(duration.getNumberOfSecond()+" sec");
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfMillisecond()))
				strings.add(duration.getNumberOfMillisecond()+"");
		}
		String string = __injectStringHelper__().concatenate(strings,CharacterConstant.COMA.toString());
		return string;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public DurationStringBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
	
}
