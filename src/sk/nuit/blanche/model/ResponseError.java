package sk.nuit.blanche.model;

import java.io.Serializable;

public class ResponseError implements Serializable {
	private static final long serialVersionUID = -6575967416387683625L;
	
	private int errorCode;
	private String errorMessage;
	private String parameterName;
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	
}
