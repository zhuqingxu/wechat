package com.fangxin365.core.commons;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RuleBase {

	/**
	 *  验证数据是否为空
	 */
	public static boolean isBlank(String data) {
		boolean isBlank = false;
		if (StringUtils.isBlank(data) || "null".equals(data)) {
			isBlank = true;
		}
		return isBlank;
	}

	/**
	 * 验证数据的内容为"是"或"否"
	 */
	public static boolean isEqualsYesOrNo(String data) {
		boolean isEqualsYesOrNo = false;
		String[] characters = { "是", "否" };
		for (int i = 0; i < characters.length; i++) {
			if (data.equals(characters[i])) {
				isEqualsYesOrNo = true;
				break;
			}
		}
		return isEqualsYesOrNo;
	}

	/**
	 * 验证数据的内容不是"是"或"否"
	 */
	public static boolean isNotEqualsYesOrNo(String data) {
		boolean isNotEqualsYesOrNo = true;
		String[] characters = { "是", "否" };
		for (int i = 0; i < characters.length; i++) {
			if (data.equals(characters[i])) {
				isNotEqualsYesOrNo = false;
				break;
			}
		}
		return isNotEqualsYesOrNo;
	}

	/**
	 * 验证数据的内容为"是"
	 */
	public static boolean isEqualsYes(String data) {
		boolean isEqualsYes = false;
		if ("是".equals(data)) {
			isEqualsYes = true;
		}
		return isEqualsYes;
	}

	/**
	 * 验证数据的内容在指定字符集中
	 */
	public static boolean isInCharacters(String data, List<String> characters) {
		boolean isInCharacters = false;
		if (characters != null) {
			for (int i = 0; i < characters.size(); i++) {
				if (data.equals(characters.get(i))) {
					isInCharacters = true;
					break;
				}
			}
		}
		return isInCharacters;
	}
	
	/**
	 * 验证数据的内容在指定字符集中
	 */
	public static boolean isInCharacters(String data, String[] characters) {
		boolean isInCharacters = false;
		if (characters != null) {
			for (int i = 0; i < characters.length; i++) {
				if (data.equals(characters[i])) {
					isInCharacters = true;
					break;
				}
			}
		}
		return isInCharacters;
	}

	/**
	 * 验证数据的内容不在指定字符集中
	 */
	public static boolean isNotInCharacters(String data, List<String> characters) {
		boolean isNotEqualsCharacters = true;
		if (characters != null) {
			for (int i = 0; i < characters.size(); i++) {
				if (data.equals(characters.get(i))) {
					isNotEqualsCharacters = false;
					break;
				}
			}
		}
		return isNotEqualsCharacters;
	}

	/**
	 * 验证数据的内容不在指定字符集中
	 */
	public static boolean isNotInCharacters(String data, String[] characters) {
		boolean isNotEqualsCharacters = true;
		if (characters != null) {
			for (int i = 0; i < characters.length; i++) {
				if (data.equals(characters[i])) {
					isNotEqualsCharacters = false;
					break;
				}
			}
		}
		return isNotEqualsCharacters;
	}

	/**
	 * 验证数据1比数据2大
	 */
	public static boolean isMoreThan(double data1, double data2) {
		boolean isMoreThan = false;
		if (data1 >= data2) {
			isMoreThan = true;
		}
		return isMoreThan;
	}

	/**
	 * 验证数据1比数据2小
	 */
	public static boolean isLessThan(Long data1, Long data2) {
		boolean isLessThan = false;
		if (data1 < data2) {
			isLessThan = true;
		}
		return isLessThan;
	}
	
	/**
	 * 验证数据1比数据2小
	 */
	public static boolean isLessThan(Double data1, Double data2) {
		boolean isLessThan = false;
		if (data1 < data2) {
			isLessThan = true;
		}
		return isLessThan;
	}

	/**
	 * 验证日期1在日期2前
	 */
	public static boolean before(Date date1, Date date2) {
		boolean before = false;
		if (date1.getTime() <= date2.getTime()) {
			before = true;
		}
		return before;
	}

	/**
	 * 验证日期1在日期2后
	 */
	public static boolean after(Date date1, Date date2) {
		boolean after = false;
		if (date1.getTime() > date2.getTime()) {
			after = true;
		}
		return after;
	}

	/**
	 * 是否数字
	 */
	public static boolean isNumber(String str) {
		String regEx = "[0-9.]{1,}?";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean isNumber = false;
		if (matcher.find()) {
			isNumber = true;
		}
		return isNumber;
	}

}