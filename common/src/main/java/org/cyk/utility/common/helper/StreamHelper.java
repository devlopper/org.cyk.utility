package org.cyk.utility.common.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;

@Singleton
public class StreamHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static StreamHelper INSTANCE;
	
	public static StreamHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StreamHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public byte[] read(InputStream inputStream){
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public byte[] readFile(String name){
		try {
			return read(new FileInputStream(name));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void write(byte[] bytes,String name,String extension){
		try {
			IOUtils.write(bytes, new FileOutputStream(name+"."+extension));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
