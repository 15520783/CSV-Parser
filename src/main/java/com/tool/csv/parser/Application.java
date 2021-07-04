package com.tool.csv.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.tool.csv.parser.constant.Constant;
import com.tool.csv.parser.service.CSVParserService;
import com.tool.csv.parser.service.CSVParserServiceImp;

/**
 * Appication runner
 * 
 * @author thaint
 *
 */
public class Application {

	/** Properties */
	final static Properties properties = getProperties();

	/**
	 * Main runner method
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 * @author thaint
	 */
	public static void main(String... args) throws Exception {
		CSVParserService csvParserService = new CSVParserServiceImp();
		// Init
		// File path target CSV
		final String csvFilePath = properties.getProperty(Constant.FILEPATH_PROP_KEY);
		File csvFile = new File(csvFilePath);
		// Encoding
		final String encoding = properties.getProperty(Constant.ENCODING_PROP_KEY);
		// Check exclude header
		String excludeHeaderStr = properties.getProperty(Constant.HAS_EXCLUDE_HEADER_PROP_KEY);
		final boolean excludeHeader = Constant.ENABLE_FLG.equals(excludeHeaderStr);
		// Symbol split
		final String splitSymbol = properties.getProperty(Constant.SPLIT_SYMBOL_PROP_KEY);
		// Execute CSV parse process
		List<String[]> rows = csvParserService.readAllToListString(//
				csvFile, //
				splitSymbol, //
				encoding, //
				excludeHeader, //
				Constant.LIMIT_SIZE_DEFAULT);
		// Output:
		for (String[] row : rows) {
			System.out.println(String.join(splitSymbol, row));
		}
	}

	/**
	 * Get properties from file
	 * 
	 * @return Properties
	 * @throws IOException
	 * 
	 * @author thaint
	 */
	public static Properties getProperties() {
		Properties properties = new Properties();
		try (InputStream input = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(Constant.PROPERITES_FILE_NAME)) {
			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
