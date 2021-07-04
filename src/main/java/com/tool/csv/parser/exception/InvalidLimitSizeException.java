package com.tool.csv.parser.exception;

import java.text.MessageFormat;

import com.tool.csv.parser.constant.Constant;

/**
 * Exceed allowance litmit size exception
 * 
 * @author thaint
 *
 */
public class InvalidLimitSizeException extends Exception {

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
	public InvalidLimitSizeException(long limitKb) {
		super(MessageFormat.format(Constant.OVER_ALLOWANCE_LIMIT_SIZE_MSG,
				new Object[] { String.valueOf((double) limitKb / 1024) }));
	}
}
