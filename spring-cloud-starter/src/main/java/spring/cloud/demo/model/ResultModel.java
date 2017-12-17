package spring.cloud.demo.model;

import java.io.Serializable;

/**
 * Created by jiaguang on 7/11/16.
 */
public class ResultModel<T> implements Serializable{
	private static final long serialVersionUID = 642651043174947736L;

	public static final String CODE_SUCCESS = "200";

	private String code = CODE_SUCCESS;
	private T data;
//    private Boolean success = true;
    private String msg;
    private String traceId;

    private ResultModel() {
//        this.success = false;
    }

//    public ResultModel(Boolean success, String msg) {
////        this.success = success;
//        this.msg = msg;
//    }
//
//    public ResultModel(T data, Boolean success, String msg) {
//        this.data = data;
////        this.success = success;
//        this.msg = msg;
//    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

//	public Boolean getSuccess() {
//		return success;
//	}
//	
//	public Boolean isSuccess() {
//		return success;
//	}
//
//	public void setSuccess(Boolean success) {
//		this.success = success;
//	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public static <T extends Object> ResultModel<T> createSuccess() {
		ResultModel<T> result = new ResultModel<T>();
		result.setCode( CODE_SUCCESS );
//		result.setSuccess(true);
		result.setTraceId( TraceIdHelper.getTraceId() );
		return result;
	}
	
	public static <T extends Object> ResultModel<T> createSuccess(T data) {
		ResultModel<T> result = ResultModel.createSuccess();
		result.setData(data);
		return result;
	}
	
	public static <T extends Object> ResultModel<T> createFail(String code ) {
		return createFail(code, ErrorCodeMap.getValueByCode(code));
	}

	public static <T extends Object> ResultModel<T> createFail(String code, String msg ) {
		ResultModel<T> result = new ResultModel<T>();
		result.setCode( code );
//		result.setSuccess(false);
		result.setMsg( msg );
		result.setTraceId( TraceIdHelper.getTraceId() );
		return result;
	}
	
	
}
