package cn.com.zte.mobilebasedata.request;

import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.http.model.BaseWebServiceRequest;

/** WebService请求处理类父类
 * @author sun.li
 * */
public abstract class AppWebServiceRequest extends BaseWebServiceRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 796906016749590371L;

	@Expose(serialize = false, deserialize = false)
	private Context mContext;
	
	private String protocolName = "http";
	
	/** WebService请求处理类父类构造方法
	 *  @param context上下文
	 *  @param httpsFlag选择HTTPS请求标识 true 启用 false 不启用
	 * */
	public AppWebServiceRequest(Context context,boolean httpsFlag){
		this.mContext = context;
		setmNameSpace(HttpUtil.WSNAMESPACE);
		setmMethodName(HttpUtil.WSMETHODNAME);
		setDotNetFlag(false);
		if(httpsFlag){
			protocolName = "https";
		}
	}
	
	@Override
	public abstract PropertyInfo[] genPropertyInfo();

	protected Context getContext(){
		return mContext;
	}
	
	public void setUrl(String commonName){
		setmWebserviceURL(protocolName+"://" + HttpUtil.getInstance().getSERVEREIP()
				+ ":" + HttpUtil.JSONSERVERPORT + "/"
				+ HttpUtil.HTTPWSREQUESTPATH + commonName);
	};
}
