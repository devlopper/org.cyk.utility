package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.Files;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.file.FileImageMap;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.tag.TagMap;

public abstract class AbstractThemeImpl extends AbstractObject implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	private Files cascadeStyleSheetFiles;
	private Files javaScriptFiles;
	private FileImageMap imageMap;
	private ViewMap viewMap;
	private ThemeTemplate template;
	private TagMap tagMap;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifier(__getIdentifier__());
		setTemplate(__inject__(ThemeTemplate.class));
		getTemplate().setIdentifier(__getTemplateIdentifier__());
		
		getIcon(Boolean.TRUE).getProperties().setContracts(__getIdentifier__());
		getIcon(Boolean.TRUE).getProperties().setName("icon.png");
		getIcon(Boolean.TRUE).getProperties().setLibrary("image");
		
		getLogo(Boolean.TRUE).getProperties().setContracts(__getIdentifier__());
		getLogo(Boolean.TRUE).getProperties().setName("logo.png");
		getLogo(Boolean.TRUE).getProperties().setLibrary("image");
	}
	
	protected abstract String __getIdentifier__();
	protected abstract String __getTemplateIdentifier__();
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}
	
	@Override
	public ViewMap getViewMap(Boolean injectIfNull) {
		return (ViewMap) __getInjectIfNull__(FIELD_VIEW_MAP, injectIfNull);
	}

	@Override
	public Theme setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

	@Override
	public Theme mapViews(Object...objects) {
		getViewMap(Boolean.TRUE).set(objects);
		return this;
	}
	
	@Override
	public ThemeTemplate getTemplate() {
		return template;
	}

	@Override
	public Theme setTemplate(ThemeTemplate template) {
		this.template = template;
		return this;
	}
	
	@Override
	public Files getCascadeStyleSheetFiles() {
		return cascadeStyleSheetFiles;
	}
	
	@Override
	public Files getCascadeStyleSheetFiles(Boolean injectIfNull) {
		return (Files) __getInjectIfNull__(FIELD_CASCADE_STYLE_SHEET_FILES, injectIfNull);
	}
	
	@Override
	public Theme setCascadeStyleSheetFiles(Files cascadeStyleSheetFiles) {
		this.cascadeStyleSheetFiles = cascadeStyleSheetFiles;
		return this;
	}
	
	@Override
	public Theme addCascadeStyleSheetFiles(Collection<File> cascadeStyleSheetFiles) {
		getCascadeStyleSheetFiles(Boolean.TRUE).add(cascadeStyleSheetFiles);
		return this;
	}
	
	@Override
	public Theme addCascadeStyleSheetFiles(File... cascadeStyleSheetFiles) {
		getCascadeStyleSheetFiles(Boolean.TRUE).add(cascadeStyleSheetFiles);
		return this;
	}
	
	@Override
	public Files getJavaScriptFiles() {
		return javaScriptFiles;
	}
	
	@Override
	public Files getJavaScriptFiles(Boolean injectIfNull) {
		return (Files) __getInjectIfNull__(FIELD_JAVA_SCRIPT_FILES, injectIfNull);
	}
	
	@Override
	public Theme setJavaScriptFiles(Files javaScriptFiles) {
		this.javaScriptFiles = javaScriptFiles;
		return this;
	}
	
	@Override
	public Theme addJavaScriptFiles(Collection<File> javaScriptFiles) {
		getJavaScriptFiles(Boolean.TRUE).add(javaScriptFiles);
		return this;
	}
	
	@Override
	public Theme addJavaScriptFiles(File... objects) {
		getJavaScriptFiles(Boolean.TRUE).add(javaScriptFiles);
		return this;
	}
	
	@Override
	public FileImageMap getImageMap() {
		return imageMap;
	}
	
	@Override
	public FileImageMap getImageMap(Boolean injectIfNull) {
		return (FileImageMap) __getInjectIfNull__(FIELD_IMAGE_MAP, injectIfNull);
	}
	
	@Override
	public Theme setImageMap(FileImageMap imageMap) {
		this.imageMap = imageMap;
		return this;
	}
	
	@Override
	public Theme mapImages(Object... objects) {
		getImageMap(Boolean.TRUE).set(objects);
		return this;
	}
	
	@Override
	public FileImage getIcon() {
		FileImageMap map = getImageMap();
		return map == null ? null : map.get(IMAGE_ICON);
	}
	
	@Override
	public FileImage getIcon(Boolean injectIfNull) {
		FileImageMap map = getImageMap(injectIfNull);
		return map == null ? null : map.get(IMAGE_ICON,injectIfNull);
	}
	
	@Override
	public Theme setIcon(FileImage icon) {
		getImageMap(Boolean.TRUE).set(IMAGE_ICON,icon);
		return this;
	}
	
	@Override
	public FileImage getLogo() {
		FileImageMap map = getImageMap();
		return map == null ? null : map.get(IMAGE_LOGO);
	}
	
	@Override
	public FileImage getLogo(Boolean injectIfNull) {
		FileImageMap map = getImageMap(injectIfNull);
		return map == null ? null : map.get(IMAGE_LOGO,injectIfNull);
	}
	
	@Override
	public Theme setLogo(FileImage logo) {
		getImageMap(Boolean.TRUE).set(IMAGE_LOGO,logo);
		return this;
	}
	
	@Override
	public TagMap getTagMap() {
		return tagMap;
	}
	
	@Override
	public TagMap getTagMap(Boolean injectIfNull) {
		return (TagMap) __getInjectIfNull__(FIELD_TAG_MAP, injectIfNull);
	}
	
	@Override
	public Theme setTagMap(TagMap tagMap) {
		this.tagMap = tagMap;
		return this;
	}
	
	@Override
	public Theme mapTags(Object... keyValues) {
		getTagMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	/**/

	public static final String FIELD_VIEW_MAP = "viewMap";
	public static final String FIELD_CASCADE_STYLE_SHEET_FILES = "cascadeStyleSheetFiles";
	public static final String FIELD_JAVA_SCRIPT_FILES = "javaScriptFiles";
	public static final String FIELD_IMAGE_MAP = "imageMap";
	public static final String FIELD_TAG_MAP = "tagMap";
	
	private static final String IMAGE_ICON = "icon";
	private static final String IMAGE_LOGO = "logo";
	
}
