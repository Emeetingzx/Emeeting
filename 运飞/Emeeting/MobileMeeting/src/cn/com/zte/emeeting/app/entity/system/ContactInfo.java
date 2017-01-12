package cn.com.zte.emeeting.app.entity.system;

import java.io.Serializable;

/**
 * 手机联系人信息对象
 * 
 * @author Administrator
 * 
 */
public class ContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人姓名
	 */
	private String ContactName;

	/**
	 * 联系人电话
	 */
	private String PhoneNumber;

	
	/**
	 * 是否被邀请
	 */
	private boolean isSelected = false;

	/**
	 * 联系人拼音
	 */
	private String pinyin;
	
	
	/**
	 * 获取联系人姓名
	 * 
	 * @return
	 */
	public String getContactName() {
		return ContactName;
	}

	/**
	 * 设置联系人姓名
	 * 
	 * @param contactName
	 */
	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	/**
	 * 获取联系人电话
	 * 
	 * @return
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}

	/**
	 * 设置联系人电话
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	/**
	 * 设置联系人是否被选中
	 * 
	 * @return
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * 设置联系人是否被选择
	 * 
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取联系人拼音
	 * @return
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * 设置联系人拼音
	 * @param pinyin
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}


}
