package com.tool.csv.parser.util;

/**
 * String Utilities
 * 
 * @author thaint
 *
 */
public class CommonUtils {

	/** Double quote character */
	public static final String DOUBLE_QUOTE_CHARACTER = "\"\"";
	/** Blank character */
	public static final String EMPTY_CHARACTER = "";
	/** Space character */
	public static final String SPACE_CHARACTER = " ";
	/** Quote character */
	public static final String QUOTE_CHARACTER = "\"";

	/**
	 * Confirm object being null or blank
	 * 
	 * @param object
	 *            Object
	 * @return true if object has value that is null or blank
	 * @author thaint
	 */
	public static boolean nullOrBlank(Object object) {
		if (object == null) {
			return true;
		}
		if (EMPTY_CHARACTER.equals(object.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * Confirm object being null or space character
	 * 
	 * @param text
	 *            String
	 * @return true if object has value that is null or space
	 * @author thaint
	 */
	public static boolean nullBlankSpace(String text) {
		if (text == null) {
			return true;
		}
		return nullOrBlank(
				text.replaceAll(SPACE_CHARACTER, EMPTY_CHARACTER).replaceAll(SPACE_CHARACTER, EMPTY_CHARACTER));
	}

	/**
	 * Remove all double quote
	 *
	 * @param original
	 *            String
	 * @return removed Double Quote string
	 */
	public static String removeDoubleQuote(String original) {
		if (original == null) {
			return original;
		}
		// Remove start and end double quote
		if (original.length() >= 2 && original.startsWith(QUOTE_CHARACTER) && original.endsWith(QUOTE_CHARACTER)) {
			original = original.substring(1, original.length() - 1);
		}
		// Remove double quote in content (escaped from exporting CSV)
		if (!CommonUtils.nullOrBlank(original)) {
			original = original.replaceAll(DOUBLE_QUOTE_CHARACTER, QUOTE_CHARACTER);
		}
		return original;
	}

	/**
	 * Get extension of file
	 * 
	 * @param String
	 *            fileName
	 * @return String Extension name
	 * 
	 * @author thaint
	 */
	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index > 0) {
			return fileName.substring(index + 1);
		}
		return null;
	}
}
