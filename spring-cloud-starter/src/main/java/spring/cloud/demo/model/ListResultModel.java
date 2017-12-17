package spring.cloud.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaguang on 7/11/16.
 */
public class ListResultModel<T> implements Serializable{

	private static final long serialVersionUID = -8409667412682157085L;

	public static final String CODE_SUCCESS = "200";

    private String code = CODE_SUCCESS;
	private List<T> data = new ArrayList<T>();
//    private Boolean success = true;
    private String msg;
    private String traceId;
    private Long totalCount;
    private Integer totalPages;
    private Integer pageSize;
    private Integer pageNo;
    private String cursor;

    private ListResultModel() {
//        this.success = false;
    }

//    public ListResultModel(Boolean success, String msg) {
////        this.success = success;
//        this.msg = msg;
//    }

//    public ListResultModel(List<T> data, Boolean success, String msg, Long totalCount, Integer totalPages, Integer pageSize, Integer pageNo) {
//        this.data = data;
////        this.success = success;
//        this.msg = msg;
//        this.totalCount = totalCount;
//        this.totalPages = totalPages;
//        this.pageSize = pageSize;
//        this.pageNo = pageNo;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

//    public Boolean getSuccess() {
//        return success;
//    }
//
//    public Boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(Boolean success) {
//        this.success = success;
//    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public static <T extends Object> ListResultModel<T> createSuccess() {
        ListResultModel<T> result = new ListResultModel<T>();
//        result.setSuccess(true);
        result.setCode( CODE_SUCCESS );
        result.setTraceId( TraceIdHelper.getTraceId() );
        return result;
    }

    public static <T extends Object> ListResultModel<T> createSuccess(List<T> data) {
        ListResultModel<T> result = createSuccess();
        result.setData(data);
        return result;
    }

    public static <T extends Object> ListResultModel<T> createFail( String code ) {
        return createFail(code, ErrorCodeMap.getValueByCode(code));
    }

    public static <T extends Object> ListResultModel<T> createFail(String code, String msg ) {
        ListResultModel<T> result = new ListResultModel<T>();
//        result.setSuccess(false);
        result.setCode( code );
        result.setMsg( msg );
        result.setTraceId( TraceIdHelper.getTraceId() );
        return result;
    }
}
