package org.cyk.utility.client.controller.component.theme;

import java.util.Collection;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.file.FileImageMap;
import org.cyk.utility.client.controller.component.file.Files;
import org.cyk.utility.client.controller.component.script.Script;
import org.cyk.utility.client.controller.component.script.Scripts;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.Window;
import org.cyk.utility.client.controller.tag.TagLink;
import org.cyk.utility.client.controller.tag.TagLinks;
import org.cyk.utility.client.controller.tag.TagMap;
import org.cyk.utility.client.controller.tag.TagMeta;
import org.cyk.utility.client.controller.tag.TagMetas;
import org.cyk.utility.client.controller.tag.TagScript;
import org.cyk.utility.client.controller.tag.TagScripts;

public interface Theme extends Objectable {

	Object getRequest();
	Theme setRequest(Object request);
	
	Object getColor();
	Theme setColor(Object color);
	
	ViewMap getViewMap();
	ViewMap getViewMap(Boolean injectIfNull);
	Theme setViewMap(ViewMap viewMap);
	Theme mapViews(Object...objects);
	
	ThemeTemplate getTemplate();
	Theme setTemplate(ThemeTemplate template);
	
	/* Meta */
	
	TagMetas getTagMetas();
	TagMetas getTagMetas(Boolean injectIfNull);
	Theme setTagMetas(TagMetas tagMetas);
	Theme addTagMetas(Collection<TagMeta> tagMetas);
	Theme addTagMetas(TagMeta...tagMetas);
	
	/*_________________________________ Files ____________________________*/
	
	TagLinks getTagLinks();
	TagLinks getTagLinks(Boolean injectIfNull);
	Theme setTagLinks(TagLinks tagLinks);
	Theme addTagLinks(Collection<TagLink> tagLinks);
	Theme addTagLinks(TagLink...tagLinks);
	
	TagScripts getTagScripts();
	TagScripts getTagScripts(Boolean injectIfNull);
	Theme setTagScripts(TagScripts tagScripts);
	Theme addTagScripts(Collection<TagScript> tagScripts);
	Theme addTagScripts(TagScript...tagScripts);
	
	Scripts getScripts();
	Scripts getScripts(Boolean injectIfNull);
	Theme setScripts(Scripts scripts);
	Theme addScripts(Collection<Script> scripts);
	Theme addScripts(Script...scripts);
	
	Files getCascadeStyleSheetFiles();
	Files getCascadeStyleSheetFiles(Boolean injectIfNull);
	Theme setCascadeStyleSheetFiles(Files cascadeStyleSheetFiles);
	Theme addCascadeStyleSheetFiles(Collection<File> cascadeStyleSheetFiles);
	Theme addCascadeStyleSheetFiles(File...cascadeStyleSheetFiles);
	
	Files getJavaScriptFiles();
	Files getJavaScriptFiles(Boolean injectIfNull);
	Theme setJavaScriptFiles(Files javaScriptFiles);
	Theme addJavaScriptFiles(Collection<File> javaScriptFiles);
	Theme addJavaScriptFiles(File...objects);
	
	/* Images */
	
	FileImageMap getImageMap();
	FileImageMap getImageMap(Boolean injectIfNull);
	Theme setImageMap(FileImageMap imageMap);
	Theme mapImages(Object...objects);
	
	FileImage getIcon();
	FileImage getIcon(Boolean injectIfNull);
	Theme setIcon(FileImage icon);
	
	FileImage getLogo();
	FileImage getLogo(Boolean injectIfNull);
	Theme setLogo(FileImage logo);
	
	/*_________________________________ Tags ____________________________*/
	
	TagMap getTagMap();
	TagMap getTagMap(Boolean injectIfNull);
	Theme setTagMap(TagMap tagMap);
	Theme mapTags(Object...keyValues);
	
	/**/
	
	Theme build();
	
	Theme process(Window window);
}
