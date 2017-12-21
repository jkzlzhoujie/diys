package cn.temobi.complex.action.def;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.temobi.complex.schedule.CircleJob;
import cn.temobi.complex.schedule.CircleUserJob;
import cn.temobi.complex.schedule.RankingJob;
import cn.temobi.core.action.SimpleController;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings("serial")
@Controller
public class IndexController extends SimpleController {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	@Resource(name = "sendMailService")
	private SendMailService sendMailService;
	**/
	 
	@RequestMapping(value = "/")
	public String index(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws Exception{
		model.addAttribute("entity", "a1");
		model.addAttribute("current", "a1");
	    String agent = request.getHeader("User-Agent");
    	if(StringUtil.isNotEmpty(agent) && agent.toLowerCase().indexOf("android") != -1)
    	{
    		return "redirect:http://m.temobi.cn/";
    	}
		return "/web/index";
	}
	
	@RequestMapping(value = "/testsms")
	public 
	@ResponseBody
	ResponseObject sms(HttpServletRequest request, HttpSession session,ModelMap model) throws Exception{
		ResponseObject object = new ResponseObject();
		CircleJob circleJob = new CircleJob();
		circleJob.execute();
	    return object;
	}
	
	@RequestMapping(value = "/testsms2")
	public 
	@ResponseBody
	ResponseObject sms2(HttpServletRequest request, HttpSession session,ModelMap model) throws Exception{
		ResponseObject object = new ResponseObject();
		CircleUserJob circleUserJob = new CircleUserJob();
		circleUserJob.execute();
	    return object;
	}
	
	
	
	@RequestMapping(value = "/connetShow")
	public 
	@ResponseBody
	Map<String,Object> connetShow(HttpServletRequest request, HttpSession session,ModelMap model) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		RankingJob rankingJob = new RankingJob();
		rankingJob.execute();
		return map;
	}
}
