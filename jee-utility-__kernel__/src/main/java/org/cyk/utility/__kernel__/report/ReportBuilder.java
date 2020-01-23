package org.cyk.utility.__kernel__.report;

import java.io.OutputStream;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface ReportBuilder {

	OutputStream build(Template template,Object dataSource,Object exporter);
	
	/**/
	
	static ReportBuilder getInstance() {
		return Helper.getInstance(ReportBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
