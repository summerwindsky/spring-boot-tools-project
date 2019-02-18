package com.company.project.other.ft.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * @Title FlftUtil
 * @Description TODO
 * 
 * @author gaojun
 * @date 2014-12-26 下午6:08:30
 * @Company 北京华宇信息技术有限公司
 */

public class FlftUtil {

	private static final Pattern  P_FLFT = Pattern
			.compile("([《<]?(.*?)[>》]?)(?:第[(（]?(?:[一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)[)）]?(条(?:之[一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)?|[款项目])|$)");
	private static final Pattern P_T = Pattern.compile("第[(（]?([一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)[)）]?条(?:之([一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+))?");
	private static final Pattern P_K = Pattern.compile("第[(（]?([一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)[)）]?款");
	private static final Pattern P_X = Pattern.compile("第[(（]?([一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)[)）]?项");
	private static final Pattern P_M = Pattern.compile("第[(（]?([一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾零〇○百佰千仟万亿1234567890]+)[)）]?目");
	private static final Pattern P_FLFT_MC = Pattern.compile("^[《<](.+)[>》]$");
	private static final Pattern P_FLFT_MC_INDEX = Pattern.compile("[<>《》（）()、,，]|(&lt;)|(&gt;)");

	private static final String R_XF = "^(刑法|中华人民共和国刑法|中国刑法)$";
	private static final Pattern P_XF = Pattern.compile(R_XF);

	public static final String KIND_FLFT_OTHER = "其他";

	// 刑法法条对应细节
	private String kind;
	// 是否刑法
	private boolean isXF;
	// 原始法律法条
	private String original;
	// 法条名称
	private String mc;
	// 法条_条：数字
	private String t;
	//法条_条：之n
	private String t_z;
	// 法条_款：数字
	private String k;
	// 法条_项：数字
	private String x;
	// 法条_目：数字
	private String m;
	// 经过规整后的完整法律法条
	private String trans;
	// 经过处理后，没有名称
	private String transTKXM;
	private String transTKX;
	private String transTK;
	private String transT;

	private String transTKXM_Show;
	private String transTKXM_original;
	// 对应法条内容
	private String content;

	public FlftUtil(String original) {
		this.original = original;
		init(false);
	}

	public FlftUtil(String original, boolean getContent) {
		this.original = original;
		init(getContent);
	}

	public static String removeSymbol(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return RegexUtil.getMatcher(P_FLFT_MC_INDEX, name).replaceAll("");
	}

	private void init(boolean getContent) {
		mc = RegexUtil.getMatchResult(P_FLFT, original, 1, true);
		String mc_or = RegexUtil.getMatchResult(P_FLFT, original, 1, true);
		transTKXM_original = original.replaceFirst("\\Q" + mc_or + "\\E", "");
		if (RegexUtil.find(P_XF, mc)) {
			isXF = true;
		}
		setTKXM();
		trans = parseTrans();
	}

	private String parseTrans() {
		StringBuilder sb = new StringBuilder();
		sb.append(mc);
		if (StringUtils.isBlank(transTKXM_Show)) {
			transTKXM_Show = parseTransTKXM();
		}
		sb.append(transTKXM_Show);
		return sb.toString();
	}

	private String parseTransTKXM() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isBlank(t)) {
			return sb.toString();
		}
		sb.append("第").append(TransUtil.tranArab2ZH(t)).append("条");
		transT = sb.toString();
		
		if (StringUtils.isNotBlank(t_z)) {
		    sb.append("之").append(TransUtil.tranArab2ZH(t_z));
		    transT = sb.toString();
        }

		if (StringUtils.isNotBlank(k)) {
			sb.append("第").append(TransUtil.tranArab2ZH(k)).append("款");
		}
		transTK = sb.toString();

		if (StringUtils.isBlank(x)) {
			return sb.toString();
		}
		sb.append("第（").append(TransUtil.tranArab2ZH(x)).append("）项");
		transTKX = sb.toString();

		if (StringUtils.isBlank(m)) {
			return sb.toString();
		}
		sb.append("第").append(TransUtil.tranArab2ZH(m)).append("目");
		transTKXM = sb.toString();

		return sb.toString();
	}

 

 

	private void setTKXM() {
		String t = RegexUtil.getMatchResult(P_T, original, 1);
		this.t = parseTkx(t);
		this.t = this.t == null ? "" : this.t;
		
		String t_z = RegexUtil.getMatchResult(P_T, original, 2);
        this.t_z = parseTkx(t_z);
        this.t_z = this.t_z == null ? "" : this.t_z;

		String k = RegexUtil.getMatchResult(P_K, original, 1);
		this.k = parseTkx(k);
		this.k = this.k == null ? "" : this.k;

		String x = RegexUtil.getMatchResult(P_X, original, 1);
		this.x = parseTkx(x);
		this.x = this.x == null ? "" : this.x;

		String m = RegexUtil.getMatchResult(P_M, original, 1);
		this.m = parseTkx(m);
		this.m = this.m == null ? "" : this.m;
	}

	private String parseTkx(String tkx) {
		if (StringUtils.isNotEmpty(tkx)) {
			return TransUtil.transZH2Arab(tkx);
		}
		return null;
	}

	public boolean isXF() {
		return isXF;
	}

	public String getOriginal() {
		return original;
	}

	// public String getMc(boolean withSMH) {
	// if (withSMH && StringUtils.isNotBlank(mc)) {
	// return "《" + mc + "》";
	// } else {
	// return mc;
	// }
	// }

	public String getMc() {
		// return getMc(false);
		return mc;
	}
	public String getMcNoSMH()
	{
	    String mc_noSMH = RegexUtil.getMatchResult(P_FLFT_MC, mc, 1, true);
        mc_noSMH = mc_noSMH.replaceAll("\\(", "（").replaceAll("\\)", "）");
        return mc_noSMH;
	}
	public String getT() {
		return t;
	}

	public String getK() {
		return k;
	}

	public String getX() {
		return x;
	}

	public String getM() {
		return m;
	}

	public String getContent() {
		return content;
	}

	public String getKind() {
		return kind;
	}

	public String getTrans() {
		return trans;
	}

	public String getTransTKXM_Show() {
		return transTKXM_Show;
	}

	public String getTransTKX() {
		return transTKX;
	}

	public String getTransTKXM() {
		return transTKXM;
	}

	public String getTransTKXM_original() {
		return transTKXM_original;
	}

	public String getTransTK() {
		return transTK;
	}

	public String getTransT() {
		return transT;
	}
	
	public String getTransK() {
		String tk = getTransTK();
		//minus T
		String t = getTransT();
		if(null != tk && null != t) {
			return tk.substring(t.length());
		}
		return null;
	}
	
	public String getTransX() {
		String tkx = getTransTKX();
		//minus TK
		String tk = getTransTK();
		if(null != tkx && null != tk) {
			return tkx.substring(tk.length());
		}
		return null;
	}
	
	public String getTransM() {
		String tkxm = getTransTKXM();
		//minus TKX
		String tkx = getTransTKX();
		if(null != tkxm && null != tkx) {
			return tkxm.substring(tkx.length());
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trans == null) ? 0 : trans.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlftUtil other = (FlftUtil) obj;
		if (trans == null) {
			if (other.trans != null)
				return false;
		} else if (!trans.equals(other.trans))
			return false;
		return true;
	}
	
	public static void main(String [] args) {
//		FlftUtil flftUtil = new FlftUtil("《中华人民共和国民事诉讼法》第一百五十四条第1款第（十一）项");
//		FlftUtil flftUtil = new FlftUtil("《最高人民法院关于审理抢劫、抢夺刑事案件适用法律若干问题的意见》第十一条");
		FlftUtil flftUtil = new FlftUtil("《最高人民法院关于审理抢劫、抢夺刑事案件适用法律若干问题的意见》第十一条第二、三款");
		String name = flftUtil.getMc();
		String Trans = flftUtil.getTrans();
		String rightName = "";
//		if (FtConfig.getBzft().containsKey(name)) {
//            rightName = Trans.replaceAll(name, FtConfig.getBzft().get(name));
//        }
		System.out.println(rightName);
		System.out.println(name);
		System.out.println(flftUtil.getMc());
		System.out.println(flftUtil.getT());
		System.out.println(flftUtil.getK());
		System.out.println(flftUtil.getX());
		System.out.println(flftUtil.getTrans());
		System.out.println(flftUtil.getTransT());
		System.out.println(flftUtil.getTransTK());
		System.out.println(flftUtil.getTransTKX());
		System.out.println(flftUtil.getTransTKXM());
		System.out.println(flftUtil.getTransT());
		System.out.println(flftUtil.getTransK());
		System.out.println(flftUtil.getTransX());
		System.out.println(flftUtil.getTransM());
	}

}
