package org.cyk.utility.common.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class DataCleaner {

	public static void processLines(String filePath,Boolean loweCase) throws Exception{
		File file = new File(RandomDataProvider.class.getResource(filePath).toURI());
		@SuppressWarnings("unchecked")
		List<String> lines = IOUtils.readLines(new FileInputStream(file));
		if(Boolean.TRUE.equals(loweCase))
			for(int i=0;i<lines.size();i++)
				lines.set(i, StringUtils.lowerCase(lines.get(i)));
		//remove duplicate
		Set<String> set = new LinkedHashSet<String>(lines);
		//sorting
		List<String> sorted = new ArrayList<String>(set);
		Collections.sort(sorted);
		FileUtils.writeLines(file, sorted);
		
	}
	
	public static void processImages(String filePath) throws Exception{
		File listFile = new File(RandomDataProvider.class.getResource(filePath+"/_list.txt").toURI());
		List<String> lines = new ArrayList<String>();
		for(File file : listFile.getParentFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return FilenameUtils.isExtension(name, new String[]{"png","jpeg","jpg","bmp"});
			}
		}))
			lines.add(file.getName());

		Collections.sort(lines);
		FileUtils.writeLines(listFile, lines);
		
	}
	
	public static void main(String[] args) {
		try {
			processLines("/META-INF/generator/name/company.txt",null);
			processLines("/META-INF/generator/name/first.txt",Boolean.TRUE);
			processLines("/META-INF/generator/name/male/last.txt",Boolean.TRUE);
			processLines("/META-INF/generator/name/female/last.txt",Boolean.TRUE);
			
			processImages("/META-INF/generator/image/company/logo");
			processImages("/META-INF/generator/image/male/head");
			processImages("/META-INF/generator/image/female/head");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
