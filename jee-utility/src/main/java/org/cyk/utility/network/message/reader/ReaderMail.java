package org.cyk.utility.network.message.reader;

import javax.mail.search.SearchTerm;

public interface ReaderMail extends Reader {

	SearchTerm getSearchTerm();
	ReaderMail setSearchTerm(SearchTerm searchTerm);
	
}
