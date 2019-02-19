package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.Files;
import org.cyk.utility.client.controller.component.image.Image;
import org.cyk.utility.client.controller.component.image.ImageMap;
import org.cyk.utility.client.controller.component.view.ViewMap;

public abstract class AbstractThemeImpl extends AbstractObject implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	private Files cascadeStyleSheetFiles;
	private Files javaScriptFiles;
	private ImageMap imageMap;
	private ViewMap viewMap;
	private ThemeTemplate template;
	
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
	public ImageMap getImageMap() {
		return imageMap;
	}
	
	@Override
	public ImageMap getImageMap(Boolean injectIfNull) {
		return (ImageMap) __getInjectIfNull__(FIELD_IMAGE_MAP, injectIfNull);
	}
	
	@Override
	public Theme setImageMap(ImageMap imageMap) {
		this.imageMap = imageMap;
		return this;
	}
	
	@Override
	public Theme mapImages(Object... objects) {
		getImageMap(Boolean.TRUE).set(objects);
		return this;
	}
	
	@Override
	public Image getIcon() {
		ImageMap map = getImageMap();
		return map == null ? null : map.get(IMAGE_ICON);
	}
	
	@Override
	public Image getIcon(Boolean injectIfNull) {
		ImageMap map = getImageMap(injectIfNull);
		return map == null ? null : map.get(IMAGE_ICON,injectIfNull);
	}
	
	@Override
	public Theme setIcon(Image icon) {
		getImageMap(Boolean.TRUE).set(IMAGE_ICON,icon);
		return this;
	}
	
	@Override
	public Image getLogo() {
		ImageMap map = getImageMap();
		return map == null ? null : map.get(IMAGE_LOGO);
	}
	
	@Override
	public Image getLogo(Boolean injectIfNull) {
		ImageMap map = getImageMap(injectIfNull);
		return map == null ? null : map.get(IMAGE_LOGO,injectIfNull);
	}
	
	@Override
	public Theme setLogo(Image logo) {
		getImageMap(Boolean.TRUE).set(IMAGE_LOGO,logo);
		return this;
	}

	public static final String FIELD_VIEW_MAP = "viewMap";
	public static final String FIELD_CASCADE_STYLE_SHEET_FILES = "cascadeStyleSheetFiles";
	public static final String FIELD_JAVA_SCRIPT_FILES = "javaScriptFiles";
	public static final String FIELD_IMAGE_MAP = "imageMap";
	
	private static final String IMAGE_ICON = "icon";
	private static final String IMAGE_LOGO = "logo";
}
