package cn.temobi.complex.action.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jpush.api.report.ReceivedsResult.Received;
import cn.temobi.complex.dto.MessageDto;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.Message;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.service.ClassifyService;
import cn.temobi.complex.service.MaterialService;
import cn.temobi.complex.service.MessageService;
import cn.temobi.complex.service.PushService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.complex.service.ThemeService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

/**
 * 消息管理
 * @author hjf
 * 2014 三月 18 09:25:57
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/message")
public class SystemMessageController extends BoBaseController {
	
	
	@Resource(name="messageService")
	public MessageService messageService;
	
	
	@Resource(name="pushService")
	public PushService pushService;
	
	@Resource(name="themeService")
	public ThemeService themeService;
	
	@Resource(name="materialService")
	public MaterialService materialService;
	
	/**
	 * 消息列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Message> pageResult = new Page<Message>();
			pageResult = messageService.findByPage(page, map);
			List<Message> list = pageResult.getResult();
			List<MessageDto> rlist = new ArrayList<MessageDto>();
			if(list != null && list.size() > 0)
			{
				MessageDto messageDto;
				for(Message message:list)
				{
					messageDto = new MessageDto();
					messageDto.setTitle(message.getTitle());
					messageDto.setType(message.getType());
					messageDto.setCreatedWhen(message.getCreatedWhen());
					messageDto.setContent(message.getContent());
					messageDto.setId(message.getId());
					rlist.add(messageDto);
				}
			}
			model.addAttribute("list",rlist);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/message/list";
	}
	
	/**
	 * 消息编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("status", "1");
			List<Theme> themeList = themeService.findByMap(map);
			model.addAttribute("themeList", themeList);
			map.put("type", "3");
			List<Material> materialList1 = materialService.findByMap(map);
			model.addAttribute("materialList1", materialList1);
			map.put("type", "1");
			List<Material> materialList2 = materialService.findByMap(map);
			model.addAttribute("materialList2", materialList2);
			map.put("type", "2");
			List<Material> materialList3 = materialService.findByMap(map);
			model.addAttribute("materialList3", materialList3);
			
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Message message = messageService.getById(Long.parseLong(id));
				model.addAttribute("entity", message);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/message/edit";
	}
	
	/**
	 * 消息新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,Message message) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				messageService.update(message);
			}else{
				PushUtil.pullAllMessage(message.getTitle(), message.getContent(), message.getType(), message.getMaterialId(),message.getUrl());
				messageService.save(message);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/message/list";
	}
	
	/**
	 * 消息删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Message message = messageService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(message)) {
					messageService.delete(message);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/message/list";
	}
}
