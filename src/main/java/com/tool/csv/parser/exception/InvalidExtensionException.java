package com.tool.csv.parser.exception;

import java.text.MessageFormat;
import java.util.List;

import com.tool.csv.parser.constant.Constant;

public class InvalidExtensionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * AllowLimitSizeException
	 * 
	 * @param limitKb
	 *            Limit size with unit of Kilobyte
	 * 
	 * @author thaint
	 */
	public InvalidExtensionException(List<String> allowanceExtensions) {
		super(MessageFormat.format(Constant.INVALID_EXTENSION_FILE_MSG,
				new Object[] { String.join(Constant.COMMA_CHARACTER, allowanceExtensions) }));
	}
}
