package cn.temobi.core.action;


import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import cn.temobi.core.common.Page;

@SuppressWarnings("serial")
public class BoBaseController extends SimpleController {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected int PAGE_SIZE = 15;
	
	public Page getPage(String pageNo,String pageSize){
		int pageS=0;
		if (StringUtils.isEmpty(pageNo))
			pageNo = "1";
		if(StringUtils.isEmpty(pageSize)){
			pageS= PAGE_SIZE;
		}else{
			pageS=Integer.parseInt(pageSize);
		}
		Page page = new Page(Integer.parseInt(pageNo), pageS);
		return page;
	}
	
	public void setReturnValue(Page pages, Model model) {
		model.addAttribute("list", pages.getResult());
		model.addAttribute("pageNo", pages.getPageNo());
		model.addAttribute("pageSize", pages.getPageSize());
		model.addAttribute("totalItems", pages.getTotalItems());
		model.addAttribute("totalPages", pages.getTotalPages());
	}
    
	public static String getIpAddr(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
