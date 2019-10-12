package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.Constant;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.file.FileImageMap;
import org.cyk.utility.client.controller.component.file.Files;
import org.cyk.utility.client.controller.component.script.Script;
import org.cyk.utility.client.controller.component.script.Scripts;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.tag.TagLink;
import org.cyk.utility.client.controller.tag.TagLinks;
import org.cyk.utility.client.controller.tag.TagMap;
import org.cyk.utility.client.controller.tag.TagMeta;
import org.cyk.utility.client.controller.tag.TagMetas;
import org.cyk.utility.client.controller.tag.TagScript;
import org.cyk.utility.client.controller.tag.TagScripts;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractThemeImpl extends AbstractObject implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	private TagMetas tagMetas;
	private TagLinks tagLinks;
	private TagScripts tagScripts;
	private Files cascadeStyleSheetFiles;
	private Files javaScriptFiles;
	private FileImageMap imageMap;
	private ViewMap viewMap;
	private ThemeTemplate template;
	private TagMap tagMap;
	private Scripts scripts;
	private Object request;
	
	
	@Override
	public Theme build() {
		setIdentifier(__getIdentifier__());
		setTemplate(__inject__(ThemeTemplate.class));
		getTemplate().setIdentifier(__getTemplateIdentifier__());
		
		String logoFileName = __getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME,null);
		if(StringHelper.isBlank(logoFileName))
			logoFileName = FileHelper.concatenateNameAndExtension(__getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME_PREFIX,"logo")
					, __getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME_EXTENSION,"png"));
		FileImageBuilder fileImageBuilder = __inject__(FileImageBuilder.class);
		
		fileImageBuilder.setRequest(getRequest());
		fileImageBuilder.setResourcesFolderName(__getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_RESOURCES_FOLDER,null));
		fileImageBuilder.getFile(Boolean.TRUE)
		.setValuePath(__getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_FOLDER,"image"))
		.setValueName(logoFileName);
		setLogo(fileImageBuilder.execute().getOutput());
		
		return this;
	}
	
	protected abstract String __getIdentifier__();
	protected abstract String __getTemplateIdentifier__();
	protected abstract Object __getContext__(Object request);
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public Theme setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}
	
	@Override
	public ViewMap getViewMap(Boolean injectIfNull) {
		if(viewMap == null && Boolean.TRUE.equals(injectIfNull))
			viewMap = __inject__(ViewMap.class);
		return viewMap;
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
		if(cascadeStyleSheetFiles == null && Boolean.TRUE.equals(injectIfNull))
			cascadeStyleSheetFiles = __inject__(Files.class);
		return cascadeStyleSheetFiles;
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
		if(javaScriptFiles == null && Boolean.TRUE.equals(injectIfNull))
			javaScriptFiles = __inject__(Files.class);
		return javaScriptFiles;
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
		if(imageMap == null && Boolean.TRUE.equals(injectIfNull))
			imageMap = __inject__(FileImageMap.class);
		return imageMap;
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
		if(tagMap == null && Boolean.TRUE.equals(injectIfNull))
			tagMap = __inject__(TagMap.class);
		return tagMap;
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
	
	@Override
	public TagLinks getTagLinks() {
		return tagLinks;
	}
	
	@Override
	public TagLinks getTagLinks(Boolean injectIfNull) {
		if(tagLinks == null && Boolean.TRUE.equals(injectIfNull))
			tagLinks = __inject__(TagLinks.class);
		return tagLinks;
	}
	
	@Override
	public Theme setTagLinks(TagLinks tagLinks) {
		this.tagLinks = tagLinks;
		return this;
	}
	
	@Override
	public Theme addTagLinks(Collection<TagLink> tagLinks) {
		getTagLinks(Boolean.TRUE).add(tagLinks);
		return this;
	}
	
	@Override
	public Theme addTagLinks(TagLink... tagLinks) {
		getTagLinks(Boolean.TRUE).add(tagLinks);
		return this;
	}
	
	@Override
	public TagMetas getTagMetas() {
		return tagMetas;
	}
	
	@Override
	public TagMetas getTagMetas(Boolean injectIfNull) {
		if(tagMetas == null && Boolean.TRUE.equals(injectIfNull))
			tagMetas = __inject__(TagMetas.class);
		return tagMetas;
	}
	
	@Override
	public Theme setTagMetas(TagMetas tagMetas) {
		this.tagMetas = tagMetas;
		return this;
	}
	
	@Override
	public Theme addTagMetas(Collection<TagMeta> tagMetas) {
		getTagMetas(Boolean.TRUE).add(tagMetas);
		return this;
	}
	
	@Override
	public Theme addTagMetas(TagMeta... tagMetas) {
		getTagMetas(Boolean.TRUE).add(tagMetas);
		return this;
	}
	
	@Override
	public TagScripts getTagScripts() {
		return tagScripts;
	}
	
	@Override
	public TagScripts getTagScripts(Boolean injectIfNull) {
		if(tagScripts == null && Boolean.TRUE.equals(injectIfNull))
			tagScripts = __inject__(TagScripts.class);
		return tagScripts;
	}
	
	@Override
	public Theme setTagScripts(TagScripts tagScripts) {
		this.tagScripts = tagScripts;
		return this;
	}
	
	@Override
	public Theme addTagScripts(Collection<TagScript> tagScripts) {
		getTagScripts(Boolean.TRUE).add(tagScripts);
		return this;
	}
	
	@Override
	public Theme addTagScripts(TagScript... tagScripts) {
		getTagScripts(Boolean.TRUE).add(tagScripts);
		return this;
	}
	
	@Override
	public Scripts getScripts() {
		return scripts;
	}
	
	@Override
	public Scripts getScripts(Boolean injectIfNull) {
		if(scripts == null && Boolean.TRUE.equals(injectIfNull))
			scripts = __inject__(Scripts.class);
		return scripts;
	}
	
	@Override
	public Theme setScripts(Scripts scripts) {
		this.scripts = scripts;
		return this;
	}
	
	@Override
	public Theme addScripts(Collection<Script> scripts) {
		getScripts(Boolean.TRUE).add(scripts);
		return this;
	}
	
	@Override
	public Theme addScripts(Script... scripts) {
		getScripts(Boolean.TRUE).add(scripts);
		return this;
	}
	
	/**/
	
	protected String __getConfigurationParameterValue__(String name,String nullValue) {
		Object request = getRequest();
		return Constant.getConfigurationParameterValue(name, __getContext__(request), request, nullValue);
	}
	
	/**/

	private static final String IMAGE_ICON = "icon";
	private static final String IMAGE_LOGO = "logo";
	
}
