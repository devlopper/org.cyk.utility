package org.cyk.utility.common.utility.userinterface;

import java.util.Date;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UserInterfaceInputUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	{
		ClassHelper.getInstance().map(FileHelper.Listener.class, FileAdapter.class);
		ClassHelper.getInstance().map(Output.Listener.class, OutputAdapter.class);
	}
	
	@Test
	public void inputClasses(){
		Model model = new Model();
		assertEquals(InputText.class, Input.getListener().getClass(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "string1")));
		assertEquals(InputFile.class, Input.getListener().getClass(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "myFile1")));
	}
	
	@Test
	public void outputClasses(){
		Model model = new Model();
		assertEquals(Output.class, Output.getListener().getClass(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "string1")));
		assertEquals(OutputText.class, Output.getListener().getClass(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "string2")));
		assertEquals(OutputFile.class, Output.getListener().getClass(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "myFile1")));
	}
	
	@Test
	public void outputText(){
		Model model = new Model();
		model.setString2("myval");
		OutputText outputText = (OutputText) Output.getListener().get(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "string2"));
		assertEquals("myval", outputText.getPropertiesMap().getValue());
	}
	
	@Test
	public void outputFile(){
		Model model = new Model();
		model.setMyFile1(new MyFile().setBytes("my text".getBytes()).setExtension("txt").setMime("text/plain").setName("infos"));
		OutputFile outputFile = (OutputFile) Output.getListener().get(new Form.Detail(), model, FieldHelper.getInstance().get(Model.class, "myFile1"));
		assertEquals("value is not an instance of PrimefacesFileOutput", Boolean.TRUE, outputFile.getPropertiesMap().getValue() instanceof PrimefacesFileOutput);
		PrimefacesFileOutput fileOutput = (PrimefacesFileOutput) outputFile.getPropertiesMap().getValue();
		assertEquals("text/plain", fileOutput.getMime());
	}
	
	@Test
	public void inputText(){
		Model model = new Model();
		InputText input = new InputText();
		assertInputText(input, model, "string1","myVal", new Values(null, null, null), new Values(null, null, null), new Values(null, "myVal", null), new Values("myVal", "myVal", null));
		
		model = new Model().setString1("is01");
		input = new InputText();
		assertInputText(input, model, "string1","myVal", new Values("is01", null, null), new Values("is01", "is01", "is01"), new Values("is01", "myVal", "is01"), new Values("myVal", "myVal", "is01"));
		
	}
	
	@Test
	public void inputTextFromField(){
		assertNotNull(Input.get(null,new Model(), "string1"));
		assertNotNull(Input.get(null,new Model(), "string2"));
		assertNotNull(Input.get(null,new Model(), "string3"));
		
		Model model = new Model();
		InputText input = (InputText) Input.get(null,model, "string3");
		assertInputText(input, model, "string3","myVal", new Values(null, null, null), new Values(null, null, null), new Values(null, "myVal", null), new Values("myVal", "myVal", null));
		
		model = new Model().setString3("is01");
		input = (InputText) Input.get(null,model, "string3");
		assertBuiltInput(input, "##__field__.string.3##", new Values("is01", "is01", "is01"));
		assertInputText(input, model, "string3","myVal", new Values("is01", "is01", "is01"), new Values("is01", "is01", "is01"), new Values("is01", "myVal", "is01"), new Values("myVal", "myVal", "is01"));
		
	}
	
	@Test
	public void uploadFileNoSelectionNoCurrent(){
		InputAdapter.FILE_CLASS = FileHelper.File.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile();
		InputFile input = (InputFile) Input.get(null,model, "file");
		assertNull(model.getFile());
		assertNull(input.getValue());
		input.write();
		assertNull(input.getValue());
		assertNull(model.getFile());
	}
	
	@Test
	public void uploadFileYesSelectionNoCurrent(){
		InputAdapter.FILE_CLASS = FileHelper.File.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile();
		InputFile input = (InputFile) Input.get(null,model, "file");
		assertNull(input.getValue());
		PrimefacesUploadFileInput primefacesUploadFileInput = new PrimefacesUploadFileInput().setName("f001.png").setContents(new byte[8]).setMime("image/png");
		input.setValueObject(primefacesUploadFileInput);
		input.write();
		assertNotNull(model.getFile());
		assertEquals("f001", model.getFile().getName());
		assertEquals("png", model.getFile().getExtension());
		assertEquals("image/png", model.getFile().getMime());
	}
	
	@Test
	public void uploadFileNoSelectionYesCurrent(){
		InputAdapter.FILE_CLASS = FileHelper.File.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile().setFile(new FileHelper.File().setName("c001").setExtension("png").setMime("image/png").setBytes(new byte[8]));
		InputFile input = (InputFile) Input.get(null,model, "file");
		assertNotNull(input.getValue());
		input.write();
		assertNotNull(model.getFile());
		assertEquals("c001", model.getFile().getName());
		assertEquals("png", model.getFile().getExtension());
		assertEquals("image/png", model.getFile().getMime());
	}
	
	@Test
	public void uploadFileYesSelectionYesCurrent(){
		InputAdapter.FILE_CLASS = FileHelper.File.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile().setFile(new FileHelper.File().setName("c001").setExtension("png").setMime("image/png").setBytes(new byte[8]));
		InputFile input = (InputFile) Input.get(null,model, "file");
		assertNotNull(input.getValue());
		PrimefacesUploadFileInput primefacesUploadFileInput = new PrimefacesUploadFileInput().setName("f001.png").setContents(new byte[8]).setMime("image/png");
		input.setValueObject(primefacesUploadFileInput);
		input.write();
		assertNotNull(model.getFile());
		assertEquals("f001", model.getFile().getName());
		assertEquals("png", model.getFile().getExtension());
		assertEquals("image/png", model.getFile().getMime());
	}
	
	@Test
	public void uploadFileNoSelectionNoCurrentCustomFile(){
		InputAdapter.FILE_CLASS = MyFile.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile();
		InputFile input = (InputFile) Input.get(null,model, "customFile");
		assertNull(model.getCustomFile());
		assertNull(input.getValue());
		input.write();
		assertNull(input.getValue());
		assertNull(model.getCustomFile());
	}
	
	@Test
	public void uploadFileYesSelectionNoCurrentCustomFile(){
		InputAdapter.FILE_CLASS = MyFile.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile();
		InputFile input = (InputFile) Input.get(null,model, "customFile");
		assertNull(input.getValue());
		PrimefacesUploadFileInput primefacesUploadFileInput = new PrimefacesUploadFileInput().setName("f001.png").setContents(new byte[8]).setMime("image/png");
		input.setValueObject(primefacesUploadFileInput);
		input.write();
		assertNotNull(model.getCustomFile());
		assertEquals("f001", model.getCustomFile().getName());
		assertEquals("png", model.getCustomFile().getExtension());
		assertEquals("image/png", model.getCustomFile().getMime());
	}
	
	@Test
	public void uploadFileNoSelectionYesCurrentCustomFile(){
		InputAdapter.FILE_CLASS = MyFile.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile().setCustomFile(new MyFile().setName("c001").setExtension("png").setMime("image/png").setBytes(new byte[8]));
		InputFile input = (InputFile) Input.get(null,model, "customFile");
		assertNotNull(input.getValue());
		input.write();
		assertNotNull(model.getCustomFile());
		assertEquals("c001", model.getCustomFile().getName());
		assertEquals("png", model.getCustomFile().getExtension());
		assertEquals("image/png", model.getCustomFile().getMime());
	}
	
	@Test
	public void uploadFileYesSelectionYesCurrentCustomFile(){
		InputAdapter.FILE_CLASS = MyFile.class;
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		MyModelWithFile model = new MyModelWithFile().setCustomFile(new MyFile().setName("c001").setExtension("png").setMime("image/png").setBytes(new byte[8]));
		InputFile input = (InputFile) Input.get(null,model, "customFile");
		assertNotNull(input.getValue());
		PrimefacesUploadFileInput primefacesUploadFileInput = new PrimefacesUploadFileInput().setName("f001.png").setContents(new byte[8]).setMime("image/png");
		input.setValueObject(primefacesUploadFileInput);
		input.write();
		assertNotNull(model.getCustomFile());
		assertEquals("f001", model.getCustomFile().getName());
		assertEquals("png", model.getCustomFile().getExtension());
		assertEquals("image/png", model.getCustomFile().getMime());
	}
	
	/**/
	
	private void assertBuiltInput(Input<?> input,String label,Values expectedValues){
		assertEquals("label is not correct",label, input.getLabel() == null ? null : input.getLabel().getPropertiesMap().getValue());
		assertValues(input, input.getObject(), input.getField().getName(), expectedValues);
	}
	
	private <T> void assertInput(Class<T> valueClass,Input<T> input,Model model,String fieldName,T valueToSet,Values expectedValuesRead,Values expectedValuesSetField,Values expectedValuesSetValue,Values expectedValuesWrite){
		assertValues(input, model, fieldName, expectedValuesRead);
		
		input.__setField__(model, fieldName);
		assertValues(input, model, fieldName, expectedValuesSetField);
		
		input.setValue(valueToSet);
		assertValues(input, model, fieldName, expectedValuesSetValue);
		
		input.write();
		assertValues(input, model, fieldName, expectedValuesWrite);
	}
	
	private void assertInputText(InputText input,Model model,String fieldName,String valueToSet,Values expectedValuesRead,Values expectedValuesSetField,Values expectedValuesSetValue,Values expectedValuesWrite){
		assertInput(String.class, input, model, fieldName, valueToSet, expectedValuesRead, expectedValuesSetField, expectedValuesSetValue, expectedValuesWrite);
	}
	
	private void assertValues(Input<?> input,Object model,String fieldName,Values expectedValues){
		assertEquals("model value not correct",expectedValues.getModelValue(), FieldHelper.getInstance().read(model, fieldName));
		assertEquals("input value not correct",expectedValues.getInputValue(), input.getValue());
		assertEquals("input initial value not correct",expectedValues.getInputInitialValue(), input.getInitialValue());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
	
		private String string1;
		@org.cyk.utility.common.annotation.user.interfaces.Input private String string2;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String string3;
		private Date date1,date2,date3;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputFile private MyFile myFile1;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Values {
		
		private Object modelValue,inputValue,inputInitialValue;
		
	}
	
	public static class FileAdapter extends FileHelper.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> getModelClass() {
			return MyFile.class;
		}
		
	}
	
	public static class InputAdapter extends Input.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		public static Class<?> FILE_CLASS = FileHelper.File.class;
		
		@Override
		public Class<?> getFileClass() {
			return FILE_CLASS;
		}
		
		@Override
		public Object getPreparedValue(Input<?> input) {
			if(input instanceof InputFile){
				PrimefacesUploadFileInput file = (PrimefacesUploadFileInput) ((InputFile)input).getValueObject();
				FileHelper.File value = (FileHelper.File) input.getValue();
				if(file==null || file.getContents()==null || file.getContents().length == 0){
					//value = null;
					value = (FileHelper.File) input.getValue();
				}else {
					if(value==null)
						value = new FileHelper.File();
					value.setName(FileHelper.getInstance().getName(file.getName()));
					value.setExtension(FileHelper.getInstance().getExtension(file.getName()));
					value.setMime(file.getMime());
					value.setBytes(file.getContents());
				}
				return value;
			}
			return super.getPreparedValue(input);
		}
		
		@Override
		public Object getReadableValue(Input<?> input) {
			Object value = super.getReadableValue(input);
			if(value instanceof MyFile){
				MyFile file = (MyFile) value;
				value = new FileHelper.File();
				((FileHelper.File)value).setBytes(file.getBytes());
				((FileHelper.File)value).setMime(file.getMime());
				((FileHelper.File)value).setName(file.getName());
				((FileHelper.File)value).setExtension(file.getExtension());
			}	
			return value;
		}
		
	}
	
	public static class OutputAdapter extends Output.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Object getReadableValueFile(Object value, String name, String extension, String mime, byte[] bytes) {
			return new PrimefacesFileOutput((MyFile)value);
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class PrimefacesUploadFileInput {
		
		private byte[] contents;
		private String name,mime;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class PrimefacesFileOutput {

		private byte[] contents;
		private String name,mime;
		
		public PrimefacesFileOutput(MyFile file) {
			contents = file.getBytes();
			name = file.getName();
			mime = file.getMime();
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyFile {
		
		private byte[] bytes;
		private String name,mime,extension;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyModelWithFile {
		
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputFile private FileHelper.File file;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputFile private MyFile customFile;
	}
}
