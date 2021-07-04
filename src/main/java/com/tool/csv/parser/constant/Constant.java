package com.tool.csv.parser.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Constant variables
 * 
 * @author thaint
 *
 */
public class Constant {

	/** UTF-8 */
	public static final String UTF_8 = "UTF-8";

	/** Comma character */
	public static final String COMMA_CHARACTER = ",";

	/** Name of properties file */
	public static final String PROPERITES_FILE_NAME = "application.properties";

	/** Name of File path key properties */
	public static final String FILEPATH_PROP_KEY = "csv.tool.prop.csvFilePath";

	/** Name of encoding key properties */
	public static final String ENCODING_PROP_KEY = "csv.tool.prop.encoding";

	/** Name of exclude header key properties */
	public static final String HAS_EXCLUDE_HEADER_PROP_KEY = "csv.tool.prop.exlcudeHeader";

	/** Name of split symbol key properties */
	public static final String SPLIT_SYMBOL_PROP_KEY = "csv.tool.prop.splitSymbol";

	/** Name of split symbol key properties */
	public static final String REMOVE_DOUBLE_QUOTE_PROP_KEY = "csv.tool.prop.removeDoubleQuote";

	/** Flag of excluding header */
	public static final String ENABLE_FLG = "1";

	/** Over allowance limit size message */
	public static final String OVER_ALLOWANCE_LIMIT_SIZE_MSG = "Only suppor for CSV file smaller than {0} MB";

	/** Limit size default, using unit of Kb (Kilobytes) */
	public static final long LIMIT_SIZE_DEFAULT = 1024; // using unit of Kb (Kilobytes)

	/** Allowance extension list */
	public static final List<String> EXTENSION_ALLOWANCE_LIST = Arrays.asList("csv", "CSV");

	/** Invalid extension file message */
	public static final String INVALID_EXTENSION_FILE_MSG = "Only support file with extension such as: {0}";
}
