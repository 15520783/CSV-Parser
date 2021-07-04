package com.tool.csv.parser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.tool.csv.parser.constant.Constant;
import com.tool.csv.parser.exception.InvalidExtensionException;
import com.tool.csv.parser.exception.InvalidLimitSizeException;
import com.tool.csv.parser.util.CommonUtils;

/**
 * CSV ParserServiceImp
 * 
 * @author thaint
 */
public class CSVParserServiceImp implements CSVParserService {

	/**
	 * Read each row and push to list string
	 * 
	 * @param csvFile
	 * @param splitSymbol
	 * @param encoding
	 * @param removeDoubleQuote
	 * @param limitKilobyte
	 * @return List<String[]>
	 * @throws Exception
	 * 
	 * @author thaint
	 * @throws InvalidLimitSizeException
	 * @throws InvalidExtensionException
	 */
	@Override
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, String encoding, boolean excludeHeader,
			long limitKilobyte)
			throws FileNotFoundException, IOException, InvalidLimitSizeException, InvalidExtensionException {
		// Check extension of file
		String extension = CommonUtils.getExtension(csvFile.getName());
		if (!Constant.EXTENSION_ALLOWANCE_LIST.contains(extension)) {
			throw new InvalidExtensionException(Constant.EXTENSION_ALLOWANCE_LIST);
		}
		// Check size of file
		long sizeKb = csvFile.length() / 1024;
		if (limitKilobyte != 0 && sizeKb > limitKilobyte) {
			throw new InvalidLimitSizeException(limitKilobyte);
		}
		List<String[]> results = new ArrayList<String[]>();
		try (//
				// Read file with specific path
				FileInputStream is = new FileInputStream(csvFile.getPath());
				InputStreamReader isr = new InputStreamReader(is,
						CommonUtils.nullOrBlank(encoding) ? Constant.UTF_8 : encoding);
				BufferedReader buff = new BufferedReader(isr);//
		) {
			boolean first = excludeHeader;
			while (buff.ready()) {
				String s = buff.readLine();
				// Skip when exclude header or first line is null or space
				if (first || CommonUtils.nullBlankSpace(s)) {
					if (first) {
						first = false;
					}
					continue;
				}
				String[] cols = parseLine(s);
				results.add(cols);
			}
		}
		return results;
	}

	/**
	 * Read each row and push to list string
	 * 
	 * @param csvFile
	 * @param splitSymbol
	 * @param limitKilobyte
	 * @return List<String[]>
	 * @throws Exception
	 * 
	 * @author thaint
	 */
	@Override
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, boolean excludeHeader,
			long limitKilobyte) throws Exception {
		return readAllToListString(csvFile, splitSymbol, null, excludeHeader, limitKilobyte);
	}

	/**
	 * Read each row and push to list string
	 * 
	 * @param csvFile
	 * @param splitSymbol
	 * @param excludeHeader
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, boolean excludeHeader)
			throws Exception {
		return readAllToListString(csvFile, splitSymbol, null, excludeHeader, 0);
	}

	/**
	 * Read each row and push to list string
	 * 
	 * @param csvFile
	 * @param splitSymbol
	 * @return
	 * @throws Exception
	 * 
	 * @author thaint
	 */
	@Override
	public List<String[]> readAllToListString(File csvFile, String splitSymbol) throws Exception {
		return readAllToListString(csvFile, splitSymbol, null, true, 0);
	}

	/**
	 * Parse line CSV
	 * 
	 * @param line
	 * @return String[]
	 * 
	 * @author thaint
	 */
	private String[] parseLine(String line) {
		if (CommonUtils.nullOrBlank(line)) {
			return new String[0];
		}
		/** List containing results */
		List<String> cellValueList = new ArrayList<String>();
		int countQuote = 0;
		int index = 0;
		int sourceSize = line.length();

		StringBuffer strBuffer = new StringBuffer();
		while (index < sourceSize) {
			char charAt = line.charAt(index);
			// Count quotation number
			if ('"' == charAt) {
				strBuffer.append(charAt);
				countQuote++;
			}
			// If encounter ',', end group
			else if (',' == charAt) {
				if (countQuote % 2 == 0) {
					cellValueList.add(CommonUtils.removeDoubleQuote(strBuffer.toString()));
					countQuote = 0;
					strBuffer = new StringBuffer();
				} else {
					strBuffer.append(charAt);
				}
			} else {
				strBuffer.append(charAt);
			}
			index++;
		}
		// Append the last group buffer
		cellValueList.add(CommonUtils.removeDoubleQuote(strBuffer.toString()));
		return cellValueList.toArray(new String[cellValueList.size()]);
	}
}
