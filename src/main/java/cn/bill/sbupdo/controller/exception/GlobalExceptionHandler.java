package cn.bill.sbupdo.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import cn.bill.sbupdo.util.ExceptionUtil;
import cn.bill.sbupdo.util.LoggerUtil;

/**
 * Description: <br/>
 * Date:2018年8月6日 下午3:43:02 <br/>
 *
 * @author fengminbiao@126.com
 * @version
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView processException(Exception ex)
	{
		String message = ExceptionUtil.getCrashContent(ex);
		LoggerUtil.getLogger().error(message);
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", message);
		mv.setViewName("error/error");
		return mv;
	}

}
