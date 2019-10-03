package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionProfiles<PROFILE> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	default Class<PROFILE> getProfileClass() {
		return (Class<PROFILE>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionProfiles<PROFILE> setProfiles(Collection<PROFILE> profiles) {
		FieldHelper.write(this, FIELD_PROFILES, profiles);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<PROFILE> getProfiles() {
		return (Collection<PROFILE>) FieldHelper.read(this, FIELD_PROFILES);
	}
	
	default Collection<PROFILE> getProfiles(Boolean injectIfNull) {
		Collection<PROFILE> profiles = getProfiles();
		if(profiles == null && Boolean.TRUE.equals(injectIfNull))
			setProfiles(profiles = new ArrayList<>());
		return profiles;
	}
	
	default FieldContainerCollectionProfiles<PROFILE> addProfiles(Collection<PROFILE> profiles) {
		if(CollectionHelper.isEmpty(profiles))
			return this;
		getProfiles(Boolean.TRUE).addAll(profiles);
		return this;
	}
	
	default FieldContainerCollectionProfiles<PROFILE> addProfiles(@SuppressWarnings("unchecked") PROFILE...profiles) {
		if(ArrayHelper.isEmpty(profiles))
			return this;
		addProfiles(CollectionHelper.listOf(profiles));
		return this;
	}
	
	default FieldContainerCollectionProfiles<PROFILE> addProfilesByCodes(Collection<String> codes) {
		return addProfiles(fetchByBusinessIdentifiers(codes, getProfileClass()));
	}
	
	default FieldContainerCollectionProfiles<PROFILE> addProfilesByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addProfilesByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	String FIELD_PROFILES = "profiles";
	
}
