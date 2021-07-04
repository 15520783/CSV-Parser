package com.tool.csv.parser.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.tool.csv.parser.Application;
import com.tool.csv.parser.constant.Constant;
import com.tool.csv.parser.exception.InvalidExtensionException;
import com.tool.csv.parser.exception.InvalidLimitSizeException;

/**
 * CSV ParserServiceImp Test
 * 
 * @author thaint
 *
 */
public class CSVParserServiceImpTest {

	/** Properties */
	final static Properties properties = Application.getProperties();

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	File csvFile;
	String encoding;
	String excludeHeaderStr;
	boolean excludeHeader;
	String splitSymbol;
	long limitKilobyte;

	CSVParserService csvParserService = new CSVParserServiceImp();

	@Before
	public void init() {
		initConfiguration();
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run success
	 * <dt>[Condition test]</dt>
	 * <dd>valid params</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Return List<String[]></dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_001() throws Exception {
		// 【init】 -----------------------------------------------------------
		// 【start】 MockUp
		// 【end】 MockUp
		// Execute function
		List<String[]> actualRows = csvParserService.readAllToListString(csvFile, splitSymbol, encoding, excludeHeader,
				limitKilobyte);
		// Expect
		int expectLineCnt = countLineTotal(csvFile);
		// 【Assert】 -----------------------------------------------------------
		assertEquals(expectLineCnt, actualRows.size());
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run success, with config without header
	 * <dt>[Condition test]</dt>
	 * <dd>Configuration of excluding header</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Return List<String[]></dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_002() throws Exception {
		// 【init】 -----------------------------------------------------------
		excludeHeader = true;// Configuration of excluding header
		// 【start】 MockUp
		// 【end】 MockUp
		// Execute function
		List<String[]> actualRows = csvParserService.readAllToListString(csvFile, splitSymbol, encoding, excludeHeader,
				limitKilobyte);
		// Expect
		int expectLineCnt = countLineTotal(csvFile) - 1;
		// 【Assert】 -----------------------------------------------------------
		assertEquals(expectLineCnt, actualRows.size());
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run success with empty file
	 * <dt>[Condition test]</dt>
	 * <dd>valid params with empty file</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Return List<String[]></dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_003() throws Exception {
		// 【init】 -----------------------------------------------------------
		File emptyFile = folder.newFile("empty_file.csv");
		// 【start】 MockUp
		// 【end】 MockUp
		// Execute function
		List<String[]> actualRows = csvParserService.readAllToListString(emptyFile, splitSymbol, encoding,
				excludeHeader, limitKilobyte);
		// 【Assert】 -----------------------------------------------------------
		assertEquals(actualRows.size(), 0);
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run fail when file not found
	 * <dt>[Condition test]</dt>
	 * <dd>Setup path file not found</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Throw FileNotFoundException</dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_004() throws Exception {
		// 【init】 -----------------------------------------------------------
		String fakeFilePathNotExist = "tmp\\FakeNameFile.csv";
		csvFile = new File(fakeFilePathNotExist);
		// 【start】 MockUp
		// 【end】 MockUp
		// Execute function
		try {
			csvParserService.readAllToListString(csvFile, splitSymbol, encoding, excludeHeader, limitKilobyte);
			assert false;
		} catch (FileNotFoundException e) {
			// 【Assert】 -----------------------------------------------------------
			assert true;
		}
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run fail when file over limit allowance size
	 * <dt>[Condition test]</dt>
	 * <dd>Setup file size > 1MB</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Throw InvalidLimitSizeException</dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_005() throws Exception {
		// 【init】 -----------------------------------------------------------
		// 【start】 MockUp
		OverLimitSizeFile mockFile = new OverLimitSizeFile(csvFile.getPath());
		// 【end】 MockUp
		// Execute function
		try {
			csvParserService.readAllToListString(mockFile, splitSymbol, encoding, excludeHeader, limitKilobyte);
			assert false;
		} catch (InvalidLimitSizeException e) {
			// 【Assert】 -----------------------------------------------------------
			assertEquals(e.getMessage(), MessageFormat.format(Constant.OVER_ALLOWANCE_LIMIT_SIZE_MSG,
					String.valueOf((double) Constant.LIMIT_SIZE_DEFAULT / 1024)));
			assert true;
		}
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run fail when file has invalid extension
	 * <dt>[Condition test]</dt>
	 * <dd>Setup file size > 1MB</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Throw InvalidExtensionException</dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_006() throws Exception {
		// 【init】 -----------------------------------------------------------
		// 【start】 MockUp
		String fileNameWithInvalidExtension = "FakeFile.xlxs";
		File invalidExtensionFile = folder.newFile(fileNameWithInvalidExtension);
		// 【end】 MockUp
		// Execute function
		try {
			csvParserService.readAllToListString(invalidExtensionFile, splitSymbol, encoding, excludeHeader,
					limitKilobyte);
			assert false;
		} catch (InvalidExtensionException e) {
			// 【Assert】 -----------------------------------------------------------
			assertEquals(e.getMessage(), MessageFormat.format(Constant.INVALID_EXTENSION_FILE_MSG,
					new Object[] { String.join(Constant.COMMA_CHARACTER, Constant.EXTENSION_ALLOWANCE_LIST) }));
			assert true;
		}
	}

	/**
	 * * {@link CSVParserServiceImp#readAllToListString} テスト
	 * <dl>
	 * <dt>[Target test]</dt>
	 * <dd>Test readAllToListString run success with file include empty row
	 * <dt>[Condition test]</dt>
	 * <dd>valid params with empty file</dd>
	 * <dt>[Expected result]</dt>
	 * <dd>Return List<String[]></dd>
	 * </dl>
	 *
	 * @author thaint
	 * @throws Exception
	 */
	@Test()
	public void test_readAllToListString_007() throws Exception {
		// 【init】 -----------------------------------------------------------
		File fileTest = folder.newFile("file_test.csv");
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\r\n");
		strBuffer.append("a,b,c,d");
		strBuffer.append("\r\n");
		strBuffer.append("\r\n");
		strBuffer.append("aaa,bb,cccc,eeee");
		if (fileTest.canWrite()) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileTest));
			writer.write(strBuffer.toString());
			writer.close();
		}
		// 【start】 MockUp
		// 【end】 MockUp
		// Execute function
		List<String[]> actualRows = csvParserService.readAllToListString(fileTest, splitSymbol, encoding, excludeHeader,
				limitKilobyte);
		// 【Assert】 -----------------------------------------------------------
		assertEquals(actualRows.size(), 2);
	}

	private void initConfiguration() {
		final String csvFilePath = properties.getProperty(Constant.FILEPATH_PROP_KEY);
		csvFile = new File(csvFilePath);
		/** Encoding */
		encoding = properties.getProperty(Constant.ENCODING_PROP_KEY);
		/** Check exclude header */
		String excludeHeaderStr = properties.getProperty(Constant.HAS_EXCLUDE_HEADER_PROP_KEY);
		excludeHeader = Constant.ENABLE_FLG.equals(excludeHeaderStr);
		/** Symbol split */
		splitSymbol = properties.getProperty(Constant.SPLIT_SYMBOL_PROP_KEY);
		/** Size limit */
		limitKilobyte = Constant.LIMIT_SIZE_DEFAULT;
	}

	/**
	 * Count total line from file
	 * 
	 * @param csvFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 
	 * @author thaint
	 */
	private int countLineTotal(File csvFile) throws FileNotFoundException, IOException {
		try (//
				// Read file with specific path
				FileInputStream is = new FileInputStream(csvFile.getPath());
				InputStreamReader isr = new InputStreamReader(is, Constant.UTF_8);
				BufferedReader buff = new BufferedReader(isr);//
		) {
			int count = 0;
			while (buff.ready()) {
				String s = buff.readLine();
				count++;
			}
			return count;
		}
	}

	/**
	 * Mock class: OverLimitSizeFile
	 * 
	 * @author thaint
	 *
	 */
	public class OverLimitSizeFile extends File {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public OverLimitSizeFile(String pathname) {
			super(pathname);
		}

		@Override
		public long length() {
			return Constant.LIMIT_SIZE_DEFAULT * 1024 + 1024;
		}
	}
}
