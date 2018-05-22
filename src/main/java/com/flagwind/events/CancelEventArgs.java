package com.flagwind.events;

/**
 * 
* 取消事件参数 
* author：chendb
* date：2015年10月5日 下午4:34:12 
*
 */
public class CancelEventArgs extends EventArgs {
	
	private boolean cancel;

	public CancelEventArgs(String type, Object data) {
		super(type,data);
	}


	public boolean getCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
}
