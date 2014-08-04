package sk.nuit.blanche.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseResponse implements Serializable {
	private static final long serialVersionUID = -6575967416387683625L;
	
	private List<ResponseError> errors;
	private boolean success;
	private ResponseBody responseBody;
	
	public void setSuccess(boolean success){
		this.success = success;
	}
	
	public boolean isSuccess(){
		return success;
	}
	
	public void addError(ResponseError error){
		if(errors == null)
			errors = new ArrayList<ResponseError>();
		errors.add(error);
	}
	
	public ResponseError getError(int index){
		return errors.get(index);
	}
	
	public List<ResponseError> getErrorList(){
		return errors;
	}
	
	public ResponseBody getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}
	
	
}
