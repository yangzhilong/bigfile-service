package com.longge.bigfile.common;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author roger yang
 * @date 6/13/2019
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse<T> implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8753813726294333866L;
	private Boolean success;
	private Integer errorCode;
	private String errorMsg;
	private T data;
	// if errorDetail is not null, errorMsg is a template String, use like {0}
	private Set<? extends Object> errorDetail;

	public void setError(Integer code, String msg, Object... formatArguments) {
		success = Boolean.FALSE;
		errorCode = code;
		if (formatArguments.length > 0) {
			errorMsg = MessageFormat.format(msg, formatArguments);
		} else {
			errorMsg = msg;
		}
	}

	public void setError(BaseConstant<Integer> responseCode, Object... formatArguments) {
		setError(responseCode.getCode(), responseCode.getDesc(), formatArguments);
	}

	public void setError(Integer code, String msg, Set<? extends Object> detail) {
		setError(code, msg);
		errorDetail = detail;
	}

	public void setError(BaseConstant<Integer> responseCode, Set<? extends Object> detail) {
		setError(responseCode.getCode(), responseCode.getDesc());
		errorDetail = detail;
	}
	
	public static <T> GlobalResponse<T> buildSuccess() {
        return new GlobalResponse<T>(true, null, null, null, null);
    }

	public static <T> GlobalResponse<T> buildSuccess(T data) {
		return new GlobalResponse<T>(true, null, null, data, null);
	}

	public static <T> GlobalResponse<T> buildFail(BaseConstant<Integer> responseCode) {
		return new GlobalResponse<T>(false, responseCode.getCode(), responseCode.getDesc(), null, null);
	}

	public static <T> GlobalResponse<T> buildFail(BaseConstant<Integer> responseCode, Set<? extends Object> detail) {
		return new GlobalResponse<T>(false, responseCode.getCode(), responseCode.getDesc(), null, detail);
	}

	public static <T> GlobalResponse<T> buildFail(BaseConstant<Integer> responseCode, String errorMsg) {
		return new GlobalResponse<T>(false, responseCode.getCode(), errorMsg, null, null);
	}

	public static <T> GlobalResponse<T> buildFail(BaseConstant<Integer> responseCode, String errorMsg,
			Set<? extends Object> detail) {
		return new GlobalResponse<T>(false, responseCode.getCode(), errorMsg, null, detail);
	}

	public static <T> GlobalResponse<T> buildFail(Integer errorCode, String errorMsg) {
		return new GlobalResponse<T>(false, errorCode, errorMsg, null, null);
	}

	public static <T> GlobalResponse<T> buildFail(Integer errorCode, String errorMsg, Set<? extends Object> detail) {
		return new GlobalResponse<T>(false, errorCode, errorMsg, null, detail);
	}

	public static <T> GlobalResponse<T> buildAll(Boolean success, Integer code, String message, T data) {
		return new GlobalResponse<T>(success, code, message, data, null);
	}

	public static <T> GlobalResponse<T> buildFail(BaseConstant<Integer> responseCode, T data) {
		return new GlobalResponse<T>(false, responseCode.getCode(), null, data, null);
	}

}
