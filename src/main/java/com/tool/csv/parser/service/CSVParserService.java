package com.tool.csv.parser.service;

import java.io.File;
import java.util.List;

/**
 * CSV parser service
 * 
 * @author thaint
 *
 */
public interface CSVParserService {

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
	 */
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, String encoding, boolean excludeHeader,
			long limitKilobyte) throws Exception;

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
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, boolean excludeHeader,
			long limitKilobyte) throws Exception;

	/**
	 * Read each row and push to list string
	 * 
	 * @param csvFile
	 * @param splitSymbol
	 * @param excludeHeader
	 * @return
	 * @throws Exception
	 */
	public List<String[]> readAllToListString(File csvFile, String splitSymbol, boolean excludeHeader) throws Exception;

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
	public List<String[]> readAllToListString(File csvFile, String splitSymbol) throws Exception;
}
