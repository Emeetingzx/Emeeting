package cn.com.zte.mobilebasedata.request;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.model.BaseHttpRequest;
import cn.com.zte.mobilebasedata.entity.BaseRequestEntity;
import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.dialog.DlgToast;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.MyToast;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.emeeting.app.views.CustomToast;

/**
 * HTTP请求处理类父类
 * 
 * @author sun.li
 * */
public class AppHttpRequest extends BaseHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = -2746671583409220809L;

	@Expose(serialize = false, deserialize = false)
	private WelComeService welComeService;
	/**
	 * HTTP请求处理类父类构造方法
	 * 
	 * @param context 上下文
	 * @param httpsFlag 选择HTTPS请求标识
	 *            true 启用 false 不启用
	 * */
	protected AppHttpRequest(Context context, boolean httpsFlag) {
		super(httpsFlag, HttpUtil.getInstance().getSERVEREIP(),
				HttpUtil.getInstance().getSERVEREPORT());
		HttpManager.setTimeout(HttpUtil.TimeOut);
		welComeService = new WelComeService(context);
		setWebServicePath(HttpUtil.HTTPJSONREQUESTPATH);
		setL(HttpUtil.CHINESELANG);

		if (welComeService.getUserInfo()!=null) {
			if (welComeService.getUserInfo().getUID()!=null) {
				setU(welComeService.getUserInfo().getUID());
			}else {
				CustomToast.show(context, "MOA账号已退出，请重新登陆MOA.");
				handler.sendEmptyMessageDelayed(0, 2000);
				
			}
			try {
				if (welComeService.getToken()!=null) {
					setT(welComeService.getToken());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			CustomToast.show(context, "MOA账号已退出，请重新登陆MOA.");
			handler.sendEmptyMessageDelayed(0, 1500);
		}
		/* 测试账号*/
//		setU(HttpUtil.UID);
	}
	
	@Expose(serialize = false, deserialize = false)
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			MyApplication.GetApp().exit();
		};
	};

	/** 用户登陆后的授权码Token，通过我们的解密程序获取用户的8位工号 */
	private String T;
	
	/** Token类型：01：账号密码登陆类型产生的token02：公司统一的单点登陆组件产生的Token */
	private String TTP;

	/** 语言参数2052中文1033英文 */
	private String L;

	/** 命令CommandName */
	private String C;

	/** 分页提交参数对象 */
	private PageInput P;

	/** 其他参数，采用jsonString */
	private String D;

	/** 用户账号 */
	private String U;

	/** 用户账号类型  */
	private String UT;

	/** 过滤对象数组 */
	private List<FilterInfo> F;

	/**
	 * 获取用户登陆后的授权码Token，通过我们的解密程序获取用户的8位工号
	 * 
	 * @return t t
	 */
	public String getT() {
		return T;
	}

	/**
	 * 设置用户登陆后的授权码Token，通过我们的解密程序获取用户的8位工号
	 * 
	 * @param t
	 *            t
	 */
	public void setT(String t) {
		T = t;
	}

	/**  
	 * 获取Token类型：01：账号密码登陆类型产生的token02：公司统一的单点登陆组件产生的Token 
	 */
	public String getTTP() {
		return TTP;
	}

	/**  
	 * 设置Token类型：01：账号密码登陆类型产生的token02：公司统一的单点登陆组件产生的Token 
	 */
	public void setTTP(String tTP) {
		TTP = tTP;
	}

	/**
	 * 获取语言参数2052中文、1033英文
	 * 
	 * @return l l
	 */
	public String getL() {
		return L;
	}

	/**
	 * 设置语言参数2052中文、1033英文
	 * 
	 * @param l
	 *            l
	 */
	public void setL(String l) {
		L = l;
	}

	/**
	 * 获取命令CommandName
	 * 
	 * @return c c
	 */
	public String getC() {
		return C;
	}

	/**
	 * 设置命令CommandName
	 * 
	 * @param c
	 *            c
	 */
	public void setC(String c) {
		C = c;
	}

	/**
	 * 获取分页提交参数对象
	 * 
	 * @return p p
	 */
	public PageInput getP() {
		return P;
	}

	/**
	 * 设置分页提交参数对象
	 * 
	 * @param p
	 *            p
	 */
	public void setP(PageInput p) {
		P = p;
	}

	/**
	 * 获取其他参数，采用jsonString
	 * 
	 * @return d d
	 */
	public Object getD() {
		return D;
	}

	/**
	 * 设置其他参数，采用jsonString
	 * 
	 * @param d
	 *            d
	 */
	public void setD(BaseRequestEntity d) {
		D = JsonUtil.toJson(d);
	}

	/**
	 * 设置d
	 * 
	 * @param d
	 *            the d to set
	 */
	public void setD(String d) {
		D = d;
	}

	/**  
	 * 获取用户账号  
	 */
	public String getU() {
		return U;
	}

	/**  
	 * 设置用户账号
	 */
	public void setU(String u) {
		U = u;
	}

	/**  
	 * 获取用户账号类型 
	 */
	public String getUT() {
		return UT;
	}

	/**  
	 * 设置用户账号类型 
	 */
	public void setUT(String uT) {
		UT = uT;
	}

	/**
	 * 取得过滤对象数组
	 * 
	 * @return the f
	 */
	public List<FilterInfo> getF() {
		return F;
	}

	/**
	 * 设置过滤对象数组
	 * 
	 * @param f
	 *            the f to set
	 */
	public void setF(List<FilterInfo> f) {
		F = f;
	}

}
