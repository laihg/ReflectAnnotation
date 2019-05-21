package dh.hongyi.pojo;

/**
 * 记录字段错误信息
 * @author HelloLight
 *
 */
public class ErrorEntity {

	/**
	 * 错误字段
	 */
	private String errorField;
	/**
	 * 错误消息
	 */
	private String errorMsg;
	
	public ErrorEntity(String errorField, String errorMsg) {
		this.errorField = errorField;
		this.errorMsg = errorMsg;
	}
	public String getErrorField() {
		return errorField;
	}
	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Override
	public String toString() {
		return "ErrorEntity [errorField=" + errorField + ", errorMsg="
				+ errorMsg + "]";
	}
}
