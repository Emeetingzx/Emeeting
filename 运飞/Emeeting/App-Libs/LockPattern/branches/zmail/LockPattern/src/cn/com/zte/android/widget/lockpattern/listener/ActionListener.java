package cn.com.zte.android.widget.lockpattern.listener;

public interface ActionListener {
	/**
	 * 手势密码验证成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * 手势密码验证失败
	 */
	public static final int FAIL = 0;
	/**
	 * 回退
	 */
	public static final int BACK = 2;
	/**
	 * 结果是否成功
	 * @param result
	 */
	void callback(int result);

}
