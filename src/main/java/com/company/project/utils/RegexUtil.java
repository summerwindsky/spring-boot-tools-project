package com.company.project.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title RegexUtil
 * @Description TODO
 * 
 * @author gaojun
 * @date 2014-12-26 下午6:40:31
 * @Company 北京华宇信息技术有限公司
 */

public class RegexUtil {

	public static boolean find(Pattern pattern, String source) {
		if (pattern == null || StringUtils.isBlank(source)) {
			return false;
		}
		Matcher matcher = pattern.matcher(source);
		return matcher.find();
	}

	public static boolean find(String regex, String source) {
		if (StringUtils.isBlank(regex) || StringUtils.isBlank(source)) {
			return false;
		}
		Matcher matcher = getMatcher(regex, source);
		if (matcher == null) {
			return false;
		}
		return matcher.find();
	}

	public static Matcher getMatcher(String regex, String source) {
		if (StringUtils.isBlank(regex)) {
			return null;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		return matcher;
	}

	public static Matcher getMatcher(Pattern pattern, String source) {
		if (pattern == null) {
			return null;
		}
		Matcher matcher = pattern.matcher(source);
		return matcher;
	}

	public static String getMatchResult(String regex, String source, int group) {
		Matcher matcher = getMatcher(regex, source);
		if (null == matcher) {
			return null;
		}
		if (matcher.find()) {
			return matcher.group(group);
		} else {
			return null;
		}
	}

	public static String getMatchResult(String regex, String source, int group,
			boolean self) {
		String result = getMatchResult(regex, source, group);
		if (self && null == result) {
			result = source;
		}
		return result;
	}

	public static String getMatchResult(Pattern pattern, String source,
			int group) {
		Matcher matcher = getMatcher(pattern, source);
		if (null == matcher) {
			return null;
		}
		if (matcher.find()) {
			return matcher.group(group);
		} else {
			return null;
		}
	}

	public static String getMatchResult(Pattern pattern, String source,
			int group, boolean self) {
		String result = getMatchResult(pattern, source, group);
		if (self && null == result) {
			result = source;
		}
		return result;
	}

	public static String getMatchResult(String regex, String source,
			String group) {
		Matcher matcher = getMatcher(regex, source);
		if (null == matcher) {
			return null;
		}
		if (matcher.find()) {
			return matcher.group(group);
		} else {
			return null;
		}
	}

	public static String getMatchResult(String regex, String source,
			String group, boolean self) {
		String result = getMatchResult(regex, source, group);
		if (self && null == result) {
			result = source;
		}
		return result;
	}

	public static String getMatchResult(Pattern pattern, String source,
			String group) {
		Matcher matcher = getMatcher(pattern, source);
		if (null == matcher) {
			return null;
		}
		if (matcher.find()) {
			return matcher.group(group);
		} else {
			return null;
		}
	}

	public static String getMatchResult(Pattern pattern, String source,
			String group, boolean self) {
		String result = getMatchResult(pattern, source, group);
		if (self && null == result) {
			result = source;
		}
		return result;
	}

}
