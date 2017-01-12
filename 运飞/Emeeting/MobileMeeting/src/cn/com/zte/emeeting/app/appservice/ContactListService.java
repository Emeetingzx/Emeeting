package cn.com.zte.emeeting.app.appservice;


import java.util.ArrayList;
import java.util.List;

import com.ab.util.AbStrUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.emeeting.app.entity.system.ContactInfo;
import cn.com.zte.emeeting.app.util.Pinyin4jUtil;


/**
 * 获取手机联系人处理类
 * @author Administrator
 *
 */
public class ContactListService extends BaseAppService {

	/**上下文环境*/
	private Context mContext;
	
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	
	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,
			Phone.CONTACT_ID };

	
	public ContactListService(Context context) {
		super(context);
		this.mContext = context;
	}

	  /** 
     * 获取系统联系人信息 
     * @return 
     */  
    public  List<ContactInfo> getSystemContactInfos(){  
        List<ContactInfo> infos=new ArrayList<ContactInfo>();  
          
        // 使用ContentResolver查找联系人数据  
        Cursor cursor = mContext.getContentResolver().query(  
            ContactsContract.Contacts.CONTENT_URI, null, null,  
            null, null);  
          
        // 遍历查询结果，获取系统中所有联系人  
        while (cursor.moveToNext())  
        {  
            ContactInfo info=new ContactInfo();  
            // 获取联系人ID  
            String contactId = cursor.getString(cursor  
                .getColumnIndex(ContactsContract.Contacts._ID));  
            // 获取联系人的名字  
            String name = cursor.getString(cursor.getColumnIndex(  
                ContactsContract.Contacts.DISPLAY_NAME));  
            info.setContactName(name);  
              
            // 使用ContentResolver查找联系人的电话号码  
            Cursor phones = mContext.getContentResolver().query(  
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  
                null,  
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID  
                    + " = " + contactId, null, null);  
              
            // 遍历查询结果，获取该联系人的多个电话号码  
            while (phones.moveToNext())  
            {  
                // 获取查询结果中电话号码列中数据。  
                String phoneNumber = phones.getString(phones  
                    .getColumnIndex(ContactsContract  
                    .CommonDataKinds.Phone.NUMBER));  
                info.setPhoneNumber(phoneNumber);  
            }  
            phones.close();  
              
            infos.add(info);  
//            info=null;  
        }  
        cursor.close();  
          
        return infos;  
          
    }  
      
    /** 
     * 分页查询系统联系人信息 
     * @param pageSize 每页最大的数目 
     * @param currentOffset 当前的偏移量 
     * @return 
     */  
    public List<ContactInfo> getContactsByPage(int pageSize,int currentOffset) {  
          
        List<ContactInfo> infos=new ArrayList<ContactInfo>();  
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;   
                String[] projection = { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,  
                                        ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key"};  
                Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, "sort_key COLLATE LOCALIZED asc limit " + pageSize + " offset " + currentOffset);  
                if (cursor != null) {  
                      
                    while (cursor.moveToNext()) {  
                          
                        ContactInfo info=new ContactInfo();  
                        String contactName = cursor.getString(0);  
                        String phoneNumber = cursor.getString(1);  
                        info.setContactName(contactName);  
                        info.setPhoneNumber(phoneNumber);  
                        infos.add(info);  
                        info=null;  
                    }  
                    cursor.close();  
                  
              
    }  
                return infos;  
    }  
      
	/**
	 * 获得系统联系人的所有记录数目
	 * 
	 * @return
	 */
	public int getAllCounts() {
		int num = 0;
		// 使用ContentResolver查找联系人数据
		Cursor cursor = mContext.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		// 遍历查询结果，获取系统中所有联系人
		while (cursor.moveToNext()) {
			num++;
		}
		cursor.close();

		return num;
	}
	
	
	
	/**
	 * 得到手机通讯录联系人信息
	 */
	public List<ContactInfo> getPhoneContacts() {

		List<ContactInfo> infos = new ArrayList<ContactInfo>();

		ContentResolver resolver = mContext.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				ContactInfo contactInfo = new ContactInfo(); 
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				contactInfo.setPhoneNumber(phoneNumber);
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                contactInfo.setContactName(contactName);
//				// 得到联系人ID
//				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
//
//				// 得到联系人头像ID
//				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
//
//				// 得到联系人头像Bitamp
//				Bitmap contactPhoto = null;
//
//				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
//				if (photoid > 0) {
//					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
//					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//					contactPhoto = BitmapFactory.decodeStream(input);
//				} else {
////					contactPhoto = AbImageUtil.drawableToBitmap(getResourceDrawable(R.drawable.icon_default_user));
//					contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_default_user);
//
//				}
//                contactInfo.setContactPhoto(contactPhoto);
                infos.add(contactInfo);
			}

			phoneCursor.close();
		}

		return infos;

	}
	
	/**
	 * 获取联系人姓名首字母
	 * 
	 * @param list
	 * @return
	 */
	public List<ContactInfo> getFristPinYin(List<ContactInfo> list) {
		
		List<ContactInfo> all = new ArrayList<ContactInfo>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (!AbStrUtil.isEmpty(list.get(i).getContactName())) {
					String strFrist = Pinyin4jUtil.converterToFirstSpell(list.get(i).getContactName());
					list.get(i).setPinyin(strFrist);
				}
			}
			all.addAll(list);
		}
		return all;
	}
	
	
	
	
	
}
