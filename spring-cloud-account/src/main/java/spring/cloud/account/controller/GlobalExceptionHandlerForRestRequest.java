package spring.cloud.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.cloud.demo.model.ResultModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice(basePackages={"spring.cloud.account"})
public class GlobalExceptionHandlerForRestRequest extends ResponseEntityExceptionHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger( GlobalExceptionHandlerForRestRequest.class );
	
	@ExceptionHandler(value={Exception.class, Throwable.class})
	@ResponseBody
	ResultModel handlerAllException(HttpServletRequest request, Throwable ex, HttpServletResponse response){
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		LOGGER.error( sw.toString() );
//		response.setStatus(500);
		return ResultModel.createFail("internalErr");
	}
	
}