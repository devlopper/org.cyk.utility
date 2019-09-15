package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.file.FileHelperImpl.__concatenateNameAndExtension__;
import static org.cyk.utility.file.FileHelperImpl.__getExtension__;
import static org.cyk.utility.file.FileHelperImpl.__getPaths__;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.regularexpression.RegularExpressionHelperImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FileHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPaths_directoryTrue_fileTrue() throws Exception {
		java.io.File root = new java.io.File(System.getProperty("user.dir")+"\\target\\filehelper_getpaths_files_true_true");
		if(root.exists())
			root.delete();
		root.mkdir();
		Collection<java.io.File> files = new ArrayList<>();
		for(Integer index = 0 ; index < 3 ; index = index + 1) {
			java.io.File __file__ = new java.io.File(root,"d"+index);
			if(__file__.exists())
				__file__.delete();
			__file__.mkdir();
			files.add(__file__);
			for(Integer index01 = 0 ; index01 < 4 ; index01 = index01 + 1) {
				java.io.File __file01__ = new java.io.File(__file__,"d"+index01);
				if(__file01__.exists())
					__file01__.delete();
				__file01__.mkdir();
				files.add(__file01__);
				for(Integer index02 = 0 ; index02 < 2 ; index02 = index02 + 1) {
					java.io.File __file02__ = new java.io.File(__file01__,"f"+index02+".txt");
					if(__file02__.exists())
						__file02__.delete();
					__file02__.createNewFile();
					files.add(__file02__);
				}
			}
		}
		Paths paths = __getPaths__(Arrays.asList(root.toString()),null, Boolean.TRUE, Boolean.TRUE, null);
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(files.size())
		.containsExactlyInAnyOrder(files.stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()).toArray(new String[] {}));
	}
	
	@Test
	public void getPaths_directoryFalse_fileTrue() throws Exception {
		java.io.File root = new java.io.File(System.getProperty("user.dir")+"\\target\\filehelper_getpaths_files_false_true");
		if(root.exists())
			root.delete();
		root.mkdir();
		Collection<java.io.File> files = new ArrayList<>();
		for(Integer index = 0 ; index < 3 ; index = index + 1) {
			java.io.File __file__ = new java.io.File(root,"d"+index);
			if(__file__.exists())
				__file__.delete();
			__file__.mkdir();
			for(Integer index01 = 0 ; index01 < 4 ; index01 = index01 + 1) {
				java.io.File __file01__ = new java.io.File(__file__,"d"+index01);
				if(__file01__.exists())
					__file01__.delete();
				__file01__.mkdir();
				for(Integer index02 = 0 ; index02 < 2 ; index02 = index02 + 1) {
					java.io.File __file02__ = new java.io.File(__file01__,"f"+index02+".txt");
					if(__file02__.exists())
						__file02__.delete();
					__file02__.createNewFile();
					files.add(__file02__);
				}
			}
		}
		Paths paths = __getPaths__(Arrays.asList(root.toString()),null, Boolean.FALSE, Boolean.TRUE, null);
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(files.size())
		.containsExactly(files.stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()).toArray(new String[] {}));
	}
	
	@Test
	public void getPaths_removeByUniformResourceIdentifier() throws Exception {
		java.io.File root = new java.io.File(System.getProperty("user.dir")+"\\target\\filehelper_getpaths_removeByUniformResourceIdentifiere");
		if(root.exists())
			root.delete();
		root.mkdir();
		Collection<java.io.File> files = new ArrayList<>();
		for(Integer index = 0 ; index < 5 ; index = index + 1) {
			java.io.File __file__ = new java.io.File(root,"f"+index+".txt");
			if(__file__.exists())
				__file__.delete();
			__file__.createNewFile();
			files.add(__file__);
		}
		Paths paths = __getPaths__(Arrays.asList(root.toString()),null, Boolean.TRUE, Boolean.TRUE, null);
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(files.size())
		.containsExactlyInAnyOrder("f0.txt","f1.txt","f2.txt","f3.txt","f4.txt");
		paths.removeByUniformResourceIdentifiers(new java.io.File(root,"f1.txt").toURI().toString()
				,new java.io.File(root,"f4.txt").toURI().toString());
		
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(files.size()-2)
		.containsExactlyInAnyOrder("f0.txt","f2.txt","f3.txt");
	}
	
	@Test
	public void getPaths_extension() throws Exception {
		java.io.File root = new java.io.File(System.getProperty("user.dir")+"\\target\\filehelper_getpaths_extension");
		if(root.exists())
			root.delete();
		root.mkdir();
		Collection<java.io.File> files = new ArrayList<>();
		for(Integer index = 0 ; index < 5 ; index = index + 1) {
			String extension = null;
			if(index % 2 == 0)
				extension = "txt";
			else
				extension = "png";
			java.io.File __file__ = new java.io.File(root,"f"+index+"."+extension);
			if(__file__.exists())
				__file__.delete();
			__file__.createNewFile();
			files.add(__file__);
		}
		Paths paths = __getPaths__(Arrays.asList(root.toString()),null, Boolean.TRUE, Boolean.TRUE, null);
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(files.size())
		.containsExactlyInAnyOrder("f0.txt","f1.png","f2.txt","f3.png","f4.txt");
		
		paths = __getPaths__(Arrays.asList(root.toString()),RegularExpressionHelperImpl.__formatFileNameHavingExtensions__("png","jpeg","pdf"), Boolean.TRUE, Boolean.TRUE, null);
		assertThat(paths).isNotNull();
		assertThat(paths.get()).isNotNull();
		assertThat(paths.get().stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(2)
		.containsExactlyInAnyOrder("f1.png","f3.png");
	}
	
	@Test
	public void getExtension_name_dot_txt() {
		assertThat(__getExtension__("name.txt")).isEqualTo("txt");
	}
	
	@Test
	public void getExtension_dot_txt() {
		assertThat(__getExtension__(".txt")).isEqualTo("txt");
	}
	
	@Test
	public void getExtension_txt() {
		assertThat(__getExtension__("txt")).isNull();
	}
	
	@Test
	public void concatenateNameAndExtension_name_txt() {
		assertThat(__concatenateNameAndExtension__("name", "txt")).isEqualTo("name.txt");
	}
	
	@Test
	public void concatenateNameAndExtension_null_txt() {
		assertThat(__concatenateNameAndExtension__(null, "txt")).isEqualTo(".txt");
	}
	
	@Test
	public void concatenateNameAndExtension_name_null() {
		assertThat(__concatenateNameAndExtension__("name", null)).isEqualTo("name");
	}
	
	//@Test
	public void extractText() {
		//java.io.File file = new java.io.File(System.getProperty("user.dir")+"\\src\\test\\resources\\org\\cyk\\utility\\file\\pdf\\ALLONS TOUS AU FESTIN.pdf");
		//String text = __extractText__(file);
	}
	
}
