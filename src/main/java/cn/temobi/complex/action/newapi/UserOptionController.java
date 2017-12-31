package cn.temobi.complex.action.newapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.salim.cache.CacheHelper;
import com.sms.SmsMessageUtil;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.service.UserOptionService;
import cn.temobi.complex.service.WeixinVoteRecordService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/netreduser")
public class UserOptionController extends ClientApiBaseController {
    String  host = PropertiesHelper.getProperty( Constant.SERVER_INFORMATION,"host_url");
    @Resource(name = "userOptionService")
    private UserOptionService       service;
    @Resource(name = "weixinVoteRecordService")
    private WeixinVoteRecordService recordService;

    /**
     * 分页查询列表
     * @param request
     * @param model
     * @return 分页查询
     */
    @RequestMapping(value = "/findPage", method = { RequestMethod.GET,
            RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> findPage(HttpServletRequest request,
            ModelMap model) {
        Map<String, Object> map = new HashMap<String, Object>();
        ResponseObject object = initResponseObject();
        String id = request.getParameter("id");
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if (StringUtil.isEmpty(pageNo))
            pageNo = "1";
        if (StringUtil.isEmpty(pageSize))
            pageSize = "10";
        Page page = new Page(Integer.parseInt(pageNo),
                Integer.parseInt(pageSize));
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("id", id);
        searchMap.put("limit", page.getPageSize());
        searchMap.put("offset", page.getOffset());
        List<NetRedUser> list = new ArrayList<NetRedUser>();
        try {
            Page<NetRedUser> result = service.findByPage(page, searchMap);
            list = result.getResult();
            if (StringUtil.isNotEmpty(list)) {
                map.put("list", list);
                map.put("pageNo", page.getPageNo());
                map.put("pageSize", page.getPageSize());
                map.put("totalItems", page.getTotalItems());
                map.put("totalPages", page.getTotalPages());
            }
            for (NetRedUser user : list) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("netRedUserId", user.getId());
                param.put("type", 1);
                int count = recordService.getCount(param).intValue();
                param.put("netRedUserId", user.getId());
                param.put("type", 2);
                user.setFirstImage(host+user.getFirstImage());
                int callCount = recordService.getCount(param).intValue();
                user.setCount(count + callCount * 10);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        map.put("list", list);
        map.put("responseObject", object);
        return map;
    }

    /*
	 * 查询个人信息
	 */
    @RequestMapping(value = "/netRedUserEdit", method = { RequestMethod.GET,
    		
            RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> netRedUserEdit(HttpServletRequest request,
            ModelMap model) {
        ResponseObject object = initResponseObject();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = request.getParameter("id");
            if (StringUtil.isNotEmpty(id)) {
                NetRedUser netRedUser = service.getById(Long.parseLong(id));
                map.put("NetRedUser", netRedUser);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        map.put("ResponseObject", object);
        return map;
    }
    
   
    
    
    /**
     * 报名/完善个人中心
     * @param netRedUsers
     * @return
     */
    @RequestMapping(value = "/netRedUserEditSave", method = {
            RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResponseObject netRedUserEditSave(HttpServletRequest request) {
        ResponseObject object = initResponseObject();
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String lables = request.getParameter("lables");
            String images = request.getParameter("images");
            String province = request.getParameter("images");
            String city = request.getParameter("city");
            String phone = request.getParameter("phone");
            String town = request.getParameter("town");
            String selfIntroduction = request.getParameter("selfIntroduction");
            String liveExperience = request.getParameter("liveExperience");
            String fansAmount = request.getParameter("fansAmount");
            String webSit = request.getParameter("webSit");
            String firstImage = request.getParameter("firstImage");
            String welcomeWord = request.getParameter("welcomeWord");
            String thanksWord = request.getParameter("thanksWord");
            String callTanksWord = request.getParameter("callTanksWord");
            String gameRounds = request.getParameter("gameRounds");
            String weichatUserId = request.getParameter("weichatUserId");
            String code = request.getParameter("code");
            NetRedUser user = new NetRedUser();
            if(StringUtils.isNotBlank(id))
                user.setId(Long.valueOf(id));
            user.setName(name);
            user.setCallTanksWord(callTanksWord);
            
            user.setCity(city);
            user.setCreateTime(new Date());
            if(StringUtils.isNotBlank(fansAmount))
            user.setFansAmount(Integer.valueOf(fansAmount));
            user.setFirstImage(firstImage);
            if(StringUtils.isNotBlank(gameRounds))
            user.setGameRounds(Integer.valueOf(gameRounds));
//            if(StringUtils.isNotBlank(images))
//            user.setImages(Arrays.asList(images.split(",")));
//            if(StringUtils.isNotBlank(lables))
//            user.setLables(Arrays.asList(lables.split(",")));
            user.setPhone(phone);
            user.setProvince(province);
            user.setSelfIntroduction(selfIntroduction);
            user.setThanksWord(thanksWord);
            user.setTown(town);
            user.setWebSit(webSit);
            if(StringUtils.isNotBlank(liveExperience))
            user.setLiveExperience(Integer.valueOf(liveExperience));
            if(StringUtils.isNotBlank(weichatUserId))
                user.setWeichatUserId(Long.valueOf(weichatUserId));
            user.setWelcomeWord(welcomeWord);
            if (StringUtil.isNotEmpty(user.getId())) {
                service.update(user);
            } else {
                String oldCode = (String) CacheHelper.getInstance().get(user.getPhone());
                if(StringUtils.equals(code, oldCode))
                    service.save(user);
                else{
                    object.setCode(Constant.RESPONSE_ERROR_CODE);
                    object.setDesc("验证码错误,请重新输入");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }

    

    /**
     * 参选选手 投票 访问量
     * 
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/suppercount", method = { RequestMethod.GET, RequestMethod.POST })
    public Map<String,Object> supperCount(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        ResponseObject object = initResponseObject();
        try {
            int supperCount =  service.supperCount();
            int personCount = service.getCount(new HashMap<String, Object>()).intValue();
            int visitCount = service.accessRecordCount();
            map.put("supperCount", supperCount);
            map.put("personCount", personCount);
            map.put("visitCount", visitCount);
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        map.put("ResponseObject", object);
        return map;
    }

    /**
     * 图片上传
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = { RequestMethod.GET,
            RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        ResponseObject object = initResponseObject();
        List<Map<String, String>> returnMapList = new ArrayList<Map<String, String>>();
        Map<String, Object> resultmap = uploadImage(request, returnMapList,
                object);
        return resultmap;
    }

    /**
     * 获取验证码
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getCode", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody
    ResponseObject getCode(HttpServletRequest request) {
        ResponseObject object = initResponseObject();
        object.setCode(Constant.RESPONSE_PARAMS_ERROR);
        object.setDesc("参数错误");
        try {
            String phone = request.getParameter("phone");
            int num = (int)((Math.random()*9+1)*100000);
            long time = System.currentTimeMillis();
            //String oldCode = (String) CacheHelper.getInstance().get(mobile);
            SmsMessageUtil util = new SmsMessageUtil();
            if(util.sendMessage(phone, String.valueOf(num), "")){
                object.setCode(Constant.RESPONSE_SUCCESS_CODE);
                CachedValueAndTime(phone, num+"|"+time, 5*60);
                object.setDesc("发送成功");
            }else{
                object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
                object.setDesc("发送失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }

    /**
     * 校验验证码
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/checkMsgCode", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody
    ResponseObject checkMsgCode(HttpServletRequest request) {
        ResponseObject object = initResponseObject();
        object.setCode(Constant.RESPONSE_PARAMS_ERROR);
        object.setDesc("参数错误");
        try {
            String msgCode = request.getParameter("msgCode");
            if (StringUtil.isEmpty(msgCode)) {
                return object;
            }
            String mobile = request.getParameter("mobile");
            if (StringUtil.isEmpty(mobile)) {
                return object;
            }

            String oldCode = (String) CacheHelper.getInstance().get(mobile);
            if (StringUtil.isEmpty(oldCode)) {
                object.setCode(Constant.RESPONSE_ERROR_10004);
                object.setDesc("验证码过期");
                return object;
            }

            if (oldCode.split("\\|")[0].equals(msgCode)) {
                object.setDesc("成功");
                object.setCode(Constant.RESPONSE_SUCCESS_CODE);
                return object;
            } else {
                object.setCode(Constant.RESPONSE_ERROR_10003);
                object.setDesc("验证码错误");
                return object;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }

    static Map<String, Object> uploadImage(HttpServletRequest request,
            List<Map<String, String>> returnMapList, ResponseObject object) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 上传图片
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map fileMap = multipartRequest.getFileMap();
            if (fileMap != null && fileMap.size() > 0) {
                FileOperationController fileOperationController = new FileOperationController();
                returnMapList = fileOperationController
                        .uploadDesignerImage(request);
                if (returnMapList != null && returnMapList.size() > 0) {
                    for (Map<String, String> returnMap : returnMapList) {
                        if (returnMap.get("error") != null) {
                            object.setCode(Constant.RESPONSE_ERROR_10012);
                            object.setDesc("图片上传失败");
                        }
                    }
                } else {
                    object.setCode(Constant.RESPONSE_ERROR_10012);
                    object.setDesc("图片上传失败");
                }
            } else {
                object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
                object.setDesc("请选择图片");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("ResponseObject", object);
        map.put("returnMapList", returnMapList);
        return map;
    }

}
