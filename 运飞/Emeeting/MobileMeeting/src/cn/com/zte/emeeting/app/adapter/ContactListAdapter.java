package cn.com.zte.emeeting.app.adapter;

import java.io.InputStream;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.base.adapter.AppAdapter;
import cn.com.zte.emeeting.app.entity.system.ContactInfo;
import cn.com.zte.mobileemeeting.R;

import com.ab.util.AbImageUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbViewHolder;

/**
 * 获取手机联系人适配器
 * 
 * @author Administrator
 * 
 */
public class ContactListAdapter extends AppAdapter<ContactInfo> {

	/** 回调接口 */
	private OnClickListenerJoin onClickListenerJoin;
	
	private Context mContext;
	
	public ContactListAdapter(Context context, List<ContactInfo> list, OnClickListenerJoin onClickListenerJoin) {
		super(context, list);
		this.onClickListenerJoin = onClickListenerJoin;
		this.mContext = context;
	}

	@Override
	public int getContentView() {
		return R.layout.item_contactlist;
	}

	@Override
	public void onInitView(View convertView, final int position, final ContactInfo item) {

		/** 联系人姓名 */
		TextView tvName = AbViewHolder.get(convertView, R.id.contacts_tv_name);

		/** 联系人电话 */
		TextView tvPhone = AbViewHolder.get(convertView, R.id.contacts_tv_phone);

		/** 联系人头像 */
		ImageView ivIcon = AbViewHolder.get(convertView, R.id.contacts_iv_pople);

		/** 邀请按钮 */
		Button btnInvite = AbViewHolder.get(convertView, R.id.contacts_btn_ask);
		
		if (item != null) {
			// 联系人姓名
			if (!AbStrUtil.isEmpty(item.getContactName())) {
				tvName.setText(item.getContactName());
			} else {
				tvName.setText("");
			}

			if (!AbStrUtil.isEmpty(item.getPhoneNumber())){
				tvPhone.setText(item.getPhoneNumber());
			} else {
				tvPhone.setText("");
			}
			
			if (item.isSelected()){
				btnInvite.setSelected(true);
			} else {
				btnInvite.setSelected(false);
			}
			
			if (!AbStrUtil.isEmpty(item.getPhoneNumber())) {
				Bitmap bitmap = getPhote(item.getPhoneNumber());
				if (bitmap != null) {
					ivIcon.setImageBitmap(AbImageUtil.toRoundBitmap(bitmap));
				} else {
					ivIcon.setImageResource(R.drawable.icon_default_user);
				}
			} else {
				ivIcon.setImageResource(R.drawable.icon_default_user);
			}
			
		}
		
		/**
		 * 邀请按钮回调
		 */
		btnInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (onClickListenerJoin != null) {
					onClickListenerJoin.OnOnClickListenerJoinBack(v, position, item);
				}
			}
		});
		
	}

	/**
	 * 根据电话号码查询头像
	 * @param number
	 * @return
	 */
	private Bitmap getPhote(String number) {
		// 获得Uri
		Uri uriNumber2Contacts = Uri.parse("content://com.android.contacts/"
				+ "data/phones/filter/" + number);
		// 查询Uri，返回数据集
		Cursor cursorCantacts = mContext.getContentResolver().query(
				uriNumber2Contacts, null, null, null, null);
		// 如果该联系人存在
		if (cursorCantacts.getCount() > 0) {
			// 移动到第一条数据
			cursorCantacts.moveToFirst();
			Long contactID = cursorCantacts.getLong(cursorCantacts
					.getColumnIndex("contact_id"));
			// 获得contact_id的Uri
			Uri uri = ContentUris.withAppendedId(
					ContactsContract.Contacts.CONTENT_URI, contactID);
			// 打开头像图片的InputStream
			InputStream input = ContactsContract.Contacts
					.openContactPhotoInputStream(mContext.getContentResolver(),
							uri);
			// 从InputStream获得bitmap
			Bitmap bmp_head = BitmapFactory.decodeStream(input);
			return bmp_head;

		}
		return null;
	}
	
	
	/**
	 * 回调接口
	 * @author Administrator
	 *
	 */
	public interface OnClickListenerJoin {

		public void OnOnClickListenerJoinBack(View view, int position, ContactInfo contactInfo);
	}
	
}
