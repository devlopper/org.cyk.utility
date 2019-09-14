package org.cyk.utility.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.time.DurationStringBuilder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.file.FileHelperImpl.__get__;
import static org.cyk.utility.file.FileHelperImpl.__getPaths__;
import static org.cyk.utility.file.FileHelperImpl.__extractText__;

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
		Collection<Path> paths = FileHelperImpl.__getPaths__(Arrays.asList(root.toString()), Boolean.TRUE, Boolean.TRUE, null);
		for(Path index : paths)
			System.out.println(index);
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(39)
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
		Collection<Path> paths = FileHelperImpl.__getPaths__(Arrays.asList(root.toString()), Boolean.FALSE, Boolean.TRUE, null);
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()))
		.hasSize(24)
		.containsExactly(files.stream().map(x -> StringUtils.substringAfter(x.toString(), root.toString()+"\\")).collect(Collectors.toList()).toArray(new String[] {}));
	}
	
	@Test
	public void isTxt_whenNameDotTxt() {
		assertionHelper.assertEquals("txt", __inject__(FileHelper.class).getExtension("name.txt"));
	}
	
	@Test
	public void isTxt_whenDotTxt() {
		assertionHelper.assertEquals("txt", __inject__(FileHelper.class).getExtension(".txt"));
	}
	
	@Test
	public void isTxt_whenTxt() {
		assertionHelper.assertEquals(null, __inject__(FileHelper.class).getExtension("txt"));
	}
	
	@Test
	public void isNameDotTxt_whenNameIsNameAndExtensionIsTxt() {
		assertionHelper.assertEquals("name.txt", __inject__(FileHelper.class).concatenateNameAndExtension("name", "txt"));
	}
	
	@Test
	public void isDotTxt_whenExtensionIsTxt() {
		assertionHelper.assertEquals(".txt", __inject__(FileHelper.class).concatenateNameAndExtension(null, "txt"));
	}
	
	@Test
	public void isName_whenNameIsName() {
		assertionHelper.assertEquals("name", __inject__(FileHelper.class).concatenateNameAndExtension("name", null));
	}
	
	@Test
	public void get_sequantially() {
		Files files = __get__(__getPaths__(Arrays.asList(System.getProperty("user.dir")+"\\src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"), Boolean.FALSE, Boolean.TRUE, null), null, null);
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
		files.removeDuplicateByChecksum();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(3).contains("f01.txt");
	}
	
	@Test
	public void get() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.getFirst().getBytes()).withFailMessage("Bytes found").isNull();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
	}
	
	@Test
	public void getWithBytes() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFileBytesComputable(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.getFirst().getBytes()).withFailMessage("Bytes not found").isNotNull();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
	}
	
	@Test
	public void getFilterByChecksum() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFilterByFileChecksum(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(3).contains("f01.txt");
	}
	
	@Test
	public void extractText() {
		System.out.println(__extractText__(new java.io.File(System.getProperty("user.dir")+"\\src\\test\\resources\\org\\cyk\\utility\\file\\pdf\\ALLONS TOUS AU FESTIN.pdf")));
	}
	
}
