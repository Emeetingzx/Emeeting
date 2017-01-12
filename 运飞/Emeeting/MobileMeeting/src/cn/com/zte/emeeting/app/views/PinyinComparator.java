package cn.com.zte.emeeting.app.views;

import java.util.Comparator;

import cn.com.zte.emeeting.app.entity.system.ContactInfo;

/**
 * 
 * @ClassName: PinyinComparator
 * @Description: TODO 【根据拼音排序】
 * @author Pan.Jianbo
 * @date 2016-5-26 下午2:51:02
 * 
 */
public class PinyinComparator implements Comparator<ContactInfo> {

	@Override
	public int compare(ContactInfo lhs, ContactInfo rhs) {
		String py1 = lhs.getPinyin();
		String py2 = rhs.getPinyin();
		// 判断是否为空""
		if (isEmpty(py1) && isEmpty(py2))
			return 0;
		if (isEmpty(py1))
			return -1;
		if (isEmpty(py2))
			return 1;
		String str1 = "";
		String str2 = "";
		try {
			str1 = ((lhs.getPinyin()).toUpperCase()).substring(0, 1);
			str2 = ((rhs.getPinyin()).toUpperCase()).substring(0, 1);
		} catch (Exception e) {
			System.out.println("某个str为\" \" 空");
		}
		return str1.compareTo(str2);
	}

	private boolean isEmpty(String str) {
		return "".equals(str.trim());
	}

}
