package cn.com.zte.emeeting.app.entity.system;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;

/**
 * 
 * @ClassName: ContactFreindInfo
 * @Description: TODO 【联系人实体对象】
 * @author Pan.Jianbo
 * @date 2016年4月7日 上午10:08:17
 *
 */
public class ContactFreindInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人头像
	 */
	private List<Bitmap> mContactsPhonto;

	/**
	 * 联系人电话
	 */
	private List<String> mContactsNumber;

	/**
	 * 联系人姓名
	 */
	private List<String> mContactsName;

	/**
	 * 获取联系人头像
	 * @return
	 */
	public List<Bitmap> getmContactsPhonto() {
		return mContactsPhonto;
	}

	/**
	 * 设置联系人头像
	 * @param mContactsPhonto
	 */
	public void setmContactsPhonto(List<Bitmap> mContactsPhonto) {
		this.mContactsPhonto = mContactsPhonto;
	}

	/**
	 * 获取联系人电话号码
	 * @return
	 */
	public List<String> getmContactsNumber() {
		return mContactsNumber;
	}

	/**
	 * 设置联系人电话号码
	 * @param mContactsNumber
	 */
	public void setmContactsNumber(List<String> mContactsNumber) {
		this.mContactsNumber = mContactsNumber;
	}

	/**
	 * 获取联系人姓名
	 * @return
	 */
	public List<String> getmContactsName() {
		return mContactsName;
	}

	/**
	 * 设置联系人姓名
	 * @param mContactsName
	 */
	public void setmContactsName(List<String> mContactsName) {
		this.mContactsName = mContactsName;
	}

}
