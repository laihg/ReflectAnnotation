package dh.hongyi.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 自定义业务异常基类，继承Exception，可以在业务层抛出。必须要捕获处理 
 */
@SuppressWarnings("all")
public class GenericBusinessException extends Exception {

	// 错误编码
	private String errorCode = "0";
	// 错误基本描述，面向操作人员可理解，必须为中文
	private String errorDescription = "";
	
	private String errorDetail = "";
	/**
	 * 空构造方法
	 */
	public GenericBusinessException() {
	}
	/**
	 * 构造函数
	 * @param errorDescription 异常描述信息
	 */
	public GenericBusinessException(String errorDescription) {
		super(errorDescription);
		this.errorDescription = errorDescription;
	}
	/**
	 * 构造函数
	 * @param errorDescription 异常描述信息
	 * @param errorDetail 异常明细信息
	 */
	public GenericBusinessException(String errorDescription, String errorDetail) {
		super(errorDescription);
		this.errorDescription = errorDescription;
		this.errorDetail = errorDetail;
	}
	/**
	 * 构造函数，不要轻易使用
	 * @param cause
	 */
	public GenericBusinessException(Throwable cause) {
		super(cause);
	}
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param cause Throwable 对象
	 */
	public GenericBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * 构造函数	
	 * @param cause Throwable 对象
	 * @param errorCode 异常编码
	 */
	public GenericBusinessException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}
	/**
	 * 控制台打印堆栈错误码和错误信息
	 */
	public void printStackTrace() {
		System.err.println(this.getErrorCode());
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
	/**
	 * 控制台打印堆栈错误码和错误信息
	 * @param s PrintStream 打印字节流对象
	 */
	public void printStackTrace(PrintStream s) {
		s.println(this.getErrorCode());
		s.println(this.getMessage());
		super.printStackTrace(s);
	}
	/**
	 * 控制台打印堆栈错误码和错误信息
	 * @param s PrintWriter 打印字符流对象
	 */
	public void printStackTrace(PrintWriter s) {
		s.println(this.getErrorCode());
		s.println(this.getMessage());
		super.printStackTrace(s);
	}
	
	/**
	 * 获取异常编码
	 * @return 异常编码
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * 设置异常编码
	 * @param errorCode  异常编码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * 获取异常描述信息
	 * @return 异常描述
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * 设置异常描述信息
	 * @param errorDescription
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * 获取异常明细信息
	 * @return  异常处理信息
	 */
	public String getErrorDetail() {
		return errorDetail;
	}
	/**
	 * 设置异常明细信息
	 * @param errorDetail 异常处理信息
	 */
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
}