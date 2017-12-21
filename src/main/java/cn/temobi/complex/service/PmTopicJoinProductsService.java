package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.AccountRedPacketDao;
import cn.temobi.complex.dao.AccountRedPacketLogDao;
import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.dao.PmCommodityInfoDao;
import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.dao.PmProductLabelDao;
import cn.temobi.complex.dao.PmProductPraisesDao;
import cn.temobi.complex.dao.PmTopicJoinProductsDao;
import cn.temobi.complex.dao.PmTopicPsProductsDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.QiangHongBaoUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmTopicJoinProductsService")
public class PmTopicJoinProductsService extends ServiceBase{
	
	@Resource(name = "pmTopicJoinProductsDao")
	private PmTopicJoinProductsDao pmTopicJoinProductsDao;
	
	@Resource(name = "accountRedPacketLogDao")
	private AccountRedPacketLogDao accountRedPacketLogDao;
	
	@Resource(name = "accountRedPacketDao")
	private AccountRedPacketDao accountRedPacketDao;
	
	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name = "pmTopicPsProductsDao")
	private PmTopicPsProductsDao pmTopicPsProductsDao;
	
	@Resource(name = "pmProductPraisesDao")
	private PmProductPraisesDao pmProductPraisesDao;
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "pmProductInfoDao")
	private PmProductInfoDao pmProductInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	@Resource(name = "pmCommodityInfoDao")
	private PmCommodityInfoDao pmCommodityInfoDao;
	
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	
	@Resource(name = "pmProductLabelDao")
	private PmProductLabelDao pmProductLabelDao;
	
	@Resource(name = "sysParameterService")
	private SysParameterService sysParameterService;
	

	public Page<PmTopicJoinProducts> findByPageDto(Page page,Object map){
		return pmTopicJoinProductsDao.findByPage(page, "findByPageDto", map);
	}
	
	public Page<PmTopicJoinProducts> findByPageDtoTo(Page page,Object map){
		return pmTopicJoinProductsDao.findByPageDtoTo(page, map);
	}
	
	public int update(PmTopicJoinProducts entity){
		return pmTopicJoinProductsDao.update(entity);
	}
	
	public Page<PmTopicJoinProducts> findByPage(Page page,Object map){
		return pmTopicJoinProductsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmTopicJoinProductsDao.getCount(map);
	}
	
	public PmTopicJoinProducts getById(Long id){
		return pmTopicJoinProductsDao.getById(id);
	}
	
	public int save(PmTopicJoinProducts entity){
		return pmTopicJoinProductsDao.save(entity);
	}
	
	public int delete(Object id){
		return pmTopicJoinProductsDao.delete(id);
	}
	
	public int delete(PmTopicJoinProducts entity){
		return pmTopicJoinProductsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmTopicJoinProductsDao.findByMap(map);
	}
    
	public List<PmTopicJoinProducts> findAll(){
		return pmTopicJoinProductsDao.findAll();
	}
	
	public List<PmTopicJoinProducts> findAll(PmTopicJoinProducts entity){
		return pmTopicJoinProductsDao.findAll(entity);
	}
	
	public ResponseObject myP(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicId","3");
		searchMap.put("status","1");
		searchMap.put("audit","1");
		searchMap.put("userId",cmUserInfo.getId());
		searchMap.put("appCheck","yes");
		Page<PmTopicJoinProducts> pageResult = pmTopicJoinProductsDao.findByPageAppCheck(page, searchMap);
		List<PmTopicJoinProducts> joinList = pageResult.getResult();
		Map<String,Object> allMap = new HashMap<String, Object>();
		List<PIndexDto> list = new ArrayList<PIndexDto>();
		if(joinList != null && joinList.size() > 0)
		{
			for(PmTopicJoinProducts pmTopicJoinProducts:joinList){
				String joinId = pmTopicJoinProducts.getId().toString();
				PmTopicPsProducts firstPsProducts = null;
				String key = "pmTopicPsProducts@firstPs"+joinId;
		     	PmTopicPsProducts firstPsProductTemp = (PmTopicPsProducts) CacheHelper.getInstance().get(key);
				if(firstPsProductTemp == null){
					searchMap.clear();
					searchMap.put("joinId",pmTopicJoinProducts.getId());
					searchMap.put("fristObj","fristObj");
					searchMap.put("audit", "1");
					List<PmTopicPsProducts> fristObjList = pmTopicPsProductsDao.findByMap(searchMap);
					if(fristObjList != null && fristObjList.size() > 0){
						firstPsProducts = fristObjList.get(0);
						firstPsProducts.setUrl(host+firstPsProducts.getUrl());
						firstPsProducts.setThumbnail(host+firstPsProducts.getThumbnail());
					}
					CacheHelper.getInstance().set(60*60*2, key, firstPsProducts);
				}else{
					firstPsProducts = firstPsProductTemp;
		     	}
				
				allMap.clear();
				allMap.put("joinId",pmTopicJoinProducts.getId());
				allMap.put("limit",2);
				allMap.put("offset",0);
				allMap.put("audit", "1");
				
				List<PmTopicPsProducts> allList = new ArrayList<PmTopicPsProducts>();
				String key2 = "pmTopicPsProducts@allList2"+joinId;
				List<PmTopicPsProducts> allListTemp = (List<PmTopicPsProducts>) CacheHelper.getInstance().get(key2);
				if(allListTemp != null && allListTemp.size() > 0){
					allList = allListTemp;
				}else{
					allList = pmTopicPsProductsDao.findByMap(allMap);
					if(allList != null && allList.size() > 0){
						for(PmTopicPsProducts ps:allList){
							ps.setUrl(host+ps.getUrl());
							ps.setThumbnail(host+ps.getThumbnail());
						}
					}
					CacheHelper.getInstance().set(60*60*2, key2, allList);
				}
				
				String key3 = "pIndexDto@countPs"+joinId;
				PIndexDto pIndexDto= new PIndexDto();
				PIndexDto pIndexDtoTemp = (PIndexDto) CacheHelper.getInstance().get(key3);
				if(pIndexDtoTemp != null ){
					pIndexDto = pIndexDtoTemp;
				}else{
					pIndexDto = pmTopicPsProductsDao.countPs(allMap);
					CacheHelper.getInstance().set(60*60*1, key3, pIndexDto);
				}
				
				pIndexDto.setFristObj(firstPsProducts);
				pIndexDto.setAllList(allList);
				pIndexDto.setAddTime(pmTopicJoinProducts.getJoinTime());
				pIndexDto.setDepict(pmTopicJoinProducts.getDescription());
				pIndexDto.setHeadImageUrl(pmTopicJoinProducts.getHeadImageUrl());
				pIndexDto.setJoinId(pmTopicJoinProducts.getId());
				pIndexDto.setNickName(pmTopicJoinProducts.getNickName());
				pIndexDto.setUrl(host+pmTopicJoinProducts.getUrl());
				pIndexDto.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
				pIndexDto.setUserId(pmTopicJoinProducts.getUserId());
				pIndexDto.setTotalDiscuss(pIndexDto.getTotalDiscuss()+pmTopicJoinProducts.getDiscussCount());
				pIndexDto.setTotalPraise(pIndexDto.getTotalPraise()+pmTopicJoinProducts.getPraiseCount());
				pIndexDto.setTotalShare(pIndexDto.getTotalShare()+pmTopicJoinProducts.getShareCount());
				pIndexDto.setProductId(pmTopicJoinProducts.getProductId());
				pIndexDto.setIsPri(pmTopicJoinProducts.getIsPri());
				pIndexDto.setPrice(pmTopicJoinProducts.getPrice()+"");
				pIndexDto.setIsGet(pmTopicJoinProducts.getIsGet());
				
				searchMap.put("productId", pmTopicJoinProducts.getProductId());
				if(StringUtil.isNotEmpty(cmUserInfo))
				{
					searchMap.put("userId", cmUserInfo.getId());
					searchMap.put("type", "0");
					List<PmProductPraises> praList = pmProductPraisesDao.findByMap(searchMap);
					if(praList != null && praList.size() > 0){
						pIndexDto.setIsZ(1);
					}else{
						pIndexDto.setIsZ(0);
					}
				}
				
				list.add(pIndexDto);
			}
		}
		
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	public ResponseObject pOther(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", cmUserInfo.getId());
		map.put("audit","1");
		Number totalNum = pmTopicPsProductsDao.getCount(map);
		page.setTotalItems(totalNum.intValue());
		map.put("offset", page.getOffset());
	    map.put("limit", page.getPageSize());
		List<Long> pslist = pmTopicPsProductsDao.findIds(map);
		
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicId","3");
		searchMap.put("audit","1");
		searchMap.put("status","1");
		searchMap.put("list",pslist);
		searchMap.put("ids",StringUtils.join(pslist.toArray(), ","));
		searchMap.put("offset", page.getOffset());
		searchMap.put("limit", page.getPageSize());
		List<PmTopicJoinProducts> joinList = pmTopicJoinProductsDao.findByMap(searchMap);
		Map<String,Object> allMap = new HashMap<String, Object>();
		List<PIndexDto> list = new ArrayList<PIndexDto>();
		if(pslist !=null && pslist.size() > 0  && joinList != null && joinList.size() > 0)
		{
			for(PmTopicJoinProducts pmTopicJoinProducts:joinList){
				
				String joinId = pmTopicJoinProducts.getId().toString();
				PmTopicPsProducts firstPsProducts = null;
				String key = "pmTopicPsProducts@firstPs"+joinId;
		     	PmTopicPsProducts firstPsProduct = (PmTopicPsProducts) CacheHelper.getInstance().get(key);
				if(firstPsProduct == null){
					searchMap.clear();
					searchMap.put("joinId",pmTopicJoinProducts.getId());
					searchMap.put("fristObj","fristObj");
					searchMap.put("audit", "1");
					List<PmTopicPsProducts> fristObjList = pmTopicPsProductsDao.findByMap(searchMap);
					if(fristObjList != null && fristObjList.size() > 0){
						firstPsProducts = fristObjList.get(0);
						firstPsProducts.setUrl(host+firstPsProducts.getUrl());
						firstPsProducts.setThumbnail(host+firstPsProducts.getThumbnail());
					}
					CacheHelper.getInstance().set(60*60*2, key, firstPsProducts);
				}else{
					firstPsProducts = firstPsProduct;
		     	}
				
				allMap.clear();
				allMap.put("joinId",pmTopicJoinProducts.getId());
				allMap.put("limit",2);
				allMap.put("offset",0);
				allMap.put("audit", "1");
				
				List<PmTopicPsProducts> allList = new ArrayList<PmTopicPsProducts>();
				String key2 = "pmTopicPsProducts@allList2"+joinId;
				List<PmTopicPsProducts> allListTemp = (List<PmTopicPsProducts>) CacheHelper.getInstance().get(key2);
				if(allListTemp != null && allListTemp.size() > 0){
					allList = allListTemp;
				}else{
					allList = pmTopicPsProductsDao.findByMap(allMap);
					if(allList != null && allList.size() > 0){
						for(PmTopicPsProducts ps:allList){
							ps.setUrl(host+ps.getUrl());
							ps.setThumbnail(host+ps.getThumbnail());
						}
					}
					CacheHelper.getInstance().set(60*60*2, key2, allList);
				}
				
				
				PIndexDto pIndexDto= new PIndexDto();
				String key3 = "pIndexDto@countPs"+joinId;
				PIndexDto pIndexDtoTemp = (PIndexDto) CacheHelper.getInstance().get(key3);
				if(pIndexDtoTemp != null ){
					pIndexDto = pIndexDtoTemp;
				}else{
					pIndexDto = pmTopicPsProductsDao.countPs(allMap);
					CacheHelper.getInstance().set(60*60*1, key3, pIndexDto);
				}
				
				pIndexDto.setFristObj(firstPsProducts);
				pIndexDto.setAllList(allList);
				pIndexDto.setAddTime(pmTopicJoinProducts.getJoinTime());
				pIndexDto.setDepict(pmTopicJoinProducts.getDescription());
				pIndexDto.setHeadImageUrl(pmTopicJoinProducts.getHeadImageUrl());
				pIndexDto.setJoinId(pmTopicJoinProducts.getId());
				pIndexDto.setNickName(pmTopicJoinProducts.getNickName());
				pIndexDto.setUrl(host+pmTopicJoinProducts.getUrl());
				pIndexDto.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
				pIndexDto.setUserId(pmTopicJoinProducts.getUserId());
				pIndexDto.setTotalDiscuss(pIndexDto.getTotalDiscuss()+pmTopicJoinProducts.getDiscussCount());
				pIndexDto.setTotalPraise(pIndexDto.getTotalPraise()+pmTopicJoinProducts.getPraiseCount());
				pIndexDto.setTotalShare(pIndexDto.getTotalShare()+pmTopicJoinProducts.getShareCount());
				pIndexDto.setProductId(pmTopicJoinProducts.getProductId());
				pIndexDto.setIsPri(pmTopicJoinProducts.getIsPri());
				pIndexDto.setPrice(pmTopicJoinProducts.getPrice()+"");
				pIndexDto.setIsGet(pmTopicJoinProducts.getIsGet());
				
				searchMap.put("productId", pmTopicJoinProducts.getProductId());
				if(StringUtil.isNotEmpty(cmUserInfo))
				{
					searchMap.put("userId", cmUserInfo.getId());
					searchMap.put("type", "0");
					List<PmProductPraises> praList = pmProductPraisesDao.findByMap(searchMap);
					if(praList != null && praList.size() > 0){
						pIndexDto.setIsZ(1);
					}else{
						pIndexDto.setIsZ(0);
					}
				}
				
				list.add(pIndexDto);
			}
		}
		
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	public List<PIndexDto> findPIndexDto(Map<String,Object> searchMap,Page page,CmUserInfo cmUserInfo){
		//查询对应P图 作品
		searchMap.put("appCheck", "yes");
		Page<PmTopicJoinProducts> pageResult = pmTopicJoinProductsDao.findByPageAppCheck(page, searchMap);
		
		List<PmTopicJoinProducts> joinList = pageResult.getResult();
		List<PIndexDto> list = new ArrayList<PIndexDto>();
		if(joinList != null && joinList.size() > 0)
		{
			Map<String,Object> allMap = new HashMap<String, Object>();
			Map<String,Object> zMap = new HashMap<String, Object>();
			Map<String,Object> labelMap = new HashMap<String, Object>();
			//查询每个作品的相关信息 及 其他用户ps过的三张作品图
			for(PmTopicJoinProducts pmTopicJoinProducts:joinList){
				String joinId = pmTopicJoinProducts.getId().toString();
				PmTopicPsProducts firstPsProducts = null;
				String key = "pmTopicPsProducts@firstPs"+joinId ;
		     	PmTopicPsProducts firstPsProduct = (PmTopicPsProducts) CacheHelper.getInstance().get(key);
				if(firstPsProduct == null){
					searchMap.clear();
					searchMap.put("joinId",pmTopicJoinProducts.getId());
					searchMap.put("fristObj","fristObj");
					searchMap.put("audit", "1");
					List<PmTopicPsProducts> fristObjList = pmTopicPsProductsDao.findByMap(searchMap);
					if(fristObjList != null && fristObjList.size() > 0){
						firstPsProducts = fristObjList.get(0);
						firstPsProducts.setUrl(host+firstPsProducts.getUrl());
						firstPsProducts.setThumbnail(host+firstPsProducts.getThumbnail());
					}
					CacheHelper.getInstance().set(60*60*2, key, firstPsProducts);
				}else{
					firstPsProducts = firstPsProduct;
		     	}
				
				allMap.clear();
				allMap.put("joinId",pmTopicJoinProducts.getId());
				allMap.put("limit",2);
				allMap.put("offset",0);
				allMap.put("audit", "1");
				
				List<PmTopicPsProducts> allList = new ArrayList<PmTopicPsProducts>();
				String key2 = "pmTopicPsProducts@allList2"+joinId;
				List<PmTopicPsProducts> allListTemp = (List<PmTopicPsProducts>) CacheHelper.getInstance().get(key2);
				if(allListTemp != null && allListTemp.size() > 0){
					allList = allListTemp;
				}else{
					allList = pmTopicPsProductsDao.findByMap(allMap);
					if(allList != null && allList.size() > 0){
						for(PmTopicPsProducts ps:allList){
							ps.setUrl(host+ps.getUrl());
							ps.setThumbnail(host+ps.getThumbnail());
						}
					}
					CacheHelper.getInstance().set(60*60*2, key2, allList);
				}
				
				PIndexDto pIndexDto= new PIndexDto();
				String key3 = "pIndexDto@countPs"+joinId;
				PIndexDto pIndexDtoTemp = (PIndexDto) CacheHelper.getInstance().get(key3);
				if(pIndexDtoTemp != null ){
					pIndexDto = pIndexDtoTemp;
				}else{
					pIndexDto = pmTopicPsProductsDao.countPs(allMap);
					CacheHelper.getInstance().set(60*60*1, key3, pIndexDto);
				}
				
				List<PmProductLabel> labelList = new ArrayList<PmProductLabel>();
				String key4 = "pmProductLabel@productId"+pmTopicJoinProducts.getProductId();
				List<PmProductLabel> tempLabelList = (List<PmProductLabel>) CacheHelper.getInstance().get(key4);
				if(tempLabelList != null && tempLabelList.size() > 0){
					labelList = tempLabelList;
				}else{
					labelMap.put("productId",pmTopicJoinProducts.getProductId());
					labelList = pmProductLabelDao.findByMap(labelMap);
					CacheHelper.getInstance().set(60*60*24, key4, labelList);
				}
				if(labelList != null && labelList.size()>0){
					pIndexDto.setLabelList(labelList);
				}
				
				pIndexDto.setFristObj(firstPsProducts);
				pIndexDto.setAllList(allList);
				pIndexDto.setAddTime(pmTopicJoinProducts.getJoinTime());
				pIndexDto.setDepict(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
				pIndexDto.setHeadImageUrl(pmTopicJoinProducts.getHeadImageUrl());
				pIndexDto.setJoinId(pmTopicJoinProducts.getId());
				pIndexDto.setNickName(pmTopicJoinProducts.getNickName());
				pIndexDto.setUrl(host+pmTopicJoinProducts.getUrl());
				pIndexDto.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
				pIndexDto.setUserId(pmTopicJoinProducts.getUserId());
				pIndexDto.setTotalDiscuss(pIndexDto.getTotalDiscuss()+pmTopicJoinProducts.getDiscussCount());
				pIndexDto.setTotalPraise(pIndexDto.getTotalPraise()+pmTopicJoinProducts.getPraiseCount());
				pIndexDto.setTotalShare(pIndexDto.getTotalShare()+pmTopicJoinProducts.getShareCount());
				pIndexDto.setProductId(pmTopicJoinProducts.getProductId());
				pIndexDto.setIsPri(pmTopicJoinProducts.getIsPri());
				pIndexDto.setPrice(pmTopicJoinProducts.getPrice()+"");
				pIndexDto.setIsGet(pmTopicJoinProducts.getIsGet());
				
				if(StringUtil.isNotEmpty(cmUserInfo)){
					zMap.put("productId", pmTopicJoinProducts.getProductId());
					zMap.put("userId", cmUserInfo.getId());
					zMap.put("type", "0");
					int count = pmProductPraisesDao.getCount(zMap).intValue();
		    		if(count > 0){
		    			pIndexDto.setIsZ(1);
		    		}else{
		    			pIndexDto.setIsZ(0);
		    		}
				}
				
				list.add(pIndexDto);
				zMap.clear();
				labelMap.clear();
				searchMap.clear();
			}
		}
		return list;
	}
	
	public ResponseObject allDemandP(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String pageNo =  CommonUtil.nvl(passMap.get("pageNo")).trim();
		String pageSize =  CommonUtil.nvl(passMap.get("pageSize"));
		String orderFried =  CommonUtil.nvl(passMap.get("orderFried"));
		String sequence =  CommonUtil.nvl(passMap.get("sequence"));
		String type =  CommonUtil.nvl(passMap.get("type"));
		int no = Integer.valueOf(pageNo);
		
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicId","3");
		searchMap.put("status","1");
		searchMap.put("sequence",sequence);
		searchMap.put("audit","1");
		searchMap.put("orderFried",orderFried);
		List<PIndexDto> list = null;
		long t1 = System.currentTimeMillis();
		if("index".equals(type))//P图悬赏（ type=index P图悬赏首页） 
		{
			searchMap.put("isGet","0");
			searchMap.put("name","name");
			//缓存 3页
			String key = "demandPIndex@page"+pageNo;
			if( Integer.valueOf(pageNo)<=3 ){
				list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
				if((list == null || list.size() <= 0)){
					list = findPIndexDto(searchMap,page,cmUserInfo);
					if(list != null && list.size()>0)
						CacheHelper.getInstance().set(60*5*no,key, list);
				}
			}else{
				list = findPIndexDto(searchMap,page,cmUserInfo);
			}
		}else{ // （ type=all 更多P图 ）
			String checkType =  CommonUtil.nvl(passMap.get("checkType")).trim();//查询排序类型
			String isFefresh =  CommonUtil.nvl(passMap.get("isFefresh")).trim();//是否是刷新动作  1 是 0 不是
			if(checkType.equals("hot")){//最热
				if(Integer.valueOf(pageNo)<=5 ){
					String key = "morePIndex@hot@page"+pageNo;
					list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
					if( list == null || list.size() <= 0 ){
						list = findPIndexDto(searchMap,page,cmUserInfo);
						if(list != null && list.size()>0){
							CacheHelper.getInstance().set(60*60*12*no,key, list);
						}
					}
				}else{
					list = findPIndexDto(searchMap,page,cmUserInfo);
				}
			}else if(checkType.equals("price")){//价格
				if(Integer.valueOf(pageNo)<=5 ){
					String key = "morePIndex@price"+pageNo;
					list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
					if(list == null || list.size() <= 0 ){
						list = findPIndexDto(searchMap,page,cmUserInfo);
						if(list != null && list.size()>0){
							CacheHelper.getInstance().set(60*60*24*no,key, list);
						}
					}
				}else{
					list = findPIndexDto(searchMap,page,cmUserInfo);
				}
			}else if(checkType.equals("priceAsc")){//价格
				if(Integer.valueOf(pageNo)<=5 ){
					String key = "morePIndex@priceAsc"+pageNo;
					list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
					if(list == null || list.size() <= 0 ){
						list = findPIndexDto(searchMap,page,cmUserInfo);
						if(list != null && list.size()>0){
							CacheHelper.getInstance().set(60*60*24*no,key, list);
						}
					}
				}else{
					list = findPIndexDto(searchMap,page,cmUserInfo);
				}
			}else  if(checkType.equals("time")){//时间
				if(Integer.valueOf(pageNo)<=5 && isFefresh.trim().equals("0") ){
					String key = "morePIndex@time"+pageNo;
					list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
					if((list == null || list.size() <= 0) && isFefresh.trim().equals("0") ){
						list = findPIndexDto(searchMap,page,cmUserInfo);
						if(list != null && list.size()>0){
							CacheHelper.getInstance().set(60*10*no,key, list);
						}
					}
				}else{
					list = findPIndexDto(searchMap,page,cmUserInfo);
				}
			}
			long t2 = System.currentTimeMillis();
			log.error("P图悬赏更多 checkType = " + checkType + ",isFefresh=" +isFefresh + ",(t2-t1)" + (t2-t1) );
		}
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	public ResponseObject findDemandP(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String pageNo =  CommonUtil.nvl(passMap.get("pageNo"));
		String pageSize =  CommonUtil.nvl(passMap.get("pageSize"));
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicId","3");//3 为P图悬赏
		searchMap.put("status","1");//已支付
		searchMap.put("audit","1");//审核通过
		searchMap.put("name","name");//彩绘或手绘除外
		searchMap.put("isGet","0");//悬赏中
		searchMap.put("sequence","desc");
		
		if(StringUtil.isNotEmpty(passMap.get("seContent"))){
    		searchMap.put("seContent", passMap.get("seContent") );
    	}
		if(StringUtil.isNotEmpty(passMap.get("label"))){
			searchMap.put("label", passMap.get("label"));
		}
		if(StringUtil.isNotEmpty(passMap.get("timeCount"))){
			searchMap.put("timeCount", passMap.get("timeCount"));
		}
		if(StringUtil.isNotEmpty(passMap.get("startPrice"))){
			searchMap.put("startPrice", passMap.get("startPrice"));	
		}
		if(StringUtil.isNotEmpty(passMap.get("endPrice"))){
			searchMap.put("endPrice", passMap.get("endPrice"));
		}
		if(StringUtil.isNotEmpty(passMap.get("isInvitation"))){
			searchMap.put("isInvitation", passMap.get("isInvitation"));
		}
		if(StringUtil.isNotEmpty(passMap.get("isGet"))){
			searchMap.put("isGet", passMap.get("isGet"));
		}
		List<PIndexDto> list = findPIndexDto(searchMap,page,cmUserInfo);
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	

	
	public ResponseObject refund(Map<String,Object> passMap,ResponseObject object){
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		String remark = CommonUtil.nvl(passMap.get("remark"));
		
		Map<String,Object> searchMap = new HashMap<String, Object>();
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isNotEmpty(accountUserBuy) && "1".equals(accountUserBuy.getType()) && ("1".equals(accountUserBuy.getStatus()) || "4".equals(accountUserBuy.getStatus())))
		{
			searchMap.put("accountBuyId", accountBuyId);
			
			accountUserBuy.setStatus("5");
			accountUserBuy.setRemark(remark);
			accountUserBuyDao.update(accountUserBuy);
			
			List<Order> orderList = orderDao.findByMap(searchMap);
			Order order = orderList.get(0);
			order.setStatus("5");
			order.setRemark(remark);
			orderDao.update(order);
			
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(accountUserBuy.getRetionId());
			pmTopicJoinProducts.setStatus("5");
			pmTopicJoinProducts.setAcceptRemark(remark);
			pmTopicJoinProductsDao.update(pmTopicJoinProducts);
			
			searchMap.put("joinId", accountUserBuy.getRetionId());
			List<AccountRedPacket> redPalist = accountRedPacketDao.findByMap(searchMap);
			double totalPrice = 0;
			for(AccountRedPacket accountRedPacket:redPalist){
				accountRedPacket.setFlag("1");
				accountRedPacketDao.update(accountRedPacket);
				
				if("1".equals(accountRedPacket.getStatus()))
				{
					totalPrice = BigDecimalUtil.sum(totalPrice, accountRedPacket.getPrice());
				}
			}
			AccountUser accountUser = accountUserDao.getById(accountUserBuy.getUserId());
			
			AccountRedPacketLog accountRedPacketLog = new AccountRedPacketLog();
			accountRedPacketLog.setUserId(accountUserBuy.getUserId());
			accountRedPacketLog.setPrice(totalPrice);
			accountRedPacketLog.setType("2");
			accountRedPacketLog.setStartAccountPrice(accountUser.getRedPacket());
			
			double userRedPacket = BigDecimalUtil.sub(accountUser.getRedPacket(),totalPrice);
			accountRedPacketLog.setEndAccountPrice(userRedPacket);
			accountRedPacketLog.setRedType("1");
			accountRedPacketLogDao.save(accountRedPacketLog);
			
			Map<String,Object> searchMapTo = new HashMap<String, Object>();
			searchMapTo.put("userId", accountUser.getUserId());
			List<Long> redlist = accountRedPacketDao.findAllJoin(searchMapTo);
			if(redlist == null || redlist.size() <= 0)
			{
				accountUser.setType("0");
			}
			accountUser.setRedPacket(userRedPacket);
			accountUserDao.update(accountUser);
		}
		return object;
	}
	
	public ResponseObject confirmP(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long joinId = CommonUtil.nvlLong(passMap.get("joinId"));
		Long psId = CommonUtil.nvlLong(passMap.get("psId"));
		
		PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(joinId);
		if(StringUtil.isEmpty(pmTopicJoinProducts))
		{
			return object;
		}
		if(pmTopicJoinProducts.getUserId()-cmUserInfo.getId() != 0)
		{
			return object;
		}
		if(!"0".equals(pmTopicJoinProducts.getIsGet()))
		{
			return object;
		}
		double price = pmTopicJoinProducts.getPrice()*0.8;
		double encouragePirce = pmTopicJoinProducts.getPrice()*0.1;//鼓励 设计师 
		if(price <= 0)
		{
			return object;
		}
		PmTopicPsProducts pmTopicPsProducts = pmTopicPsProductsDao.getById(psId);
		if(StringUtil.isEmpty(pmTopicPsProducts))
		{
			return object;
		}
		
		Long psUserId = pmTopicPsProducts.getPsUserId();
		AccountUser accountUser = accountUserDao.getById(psUserId);
		if(psUserId-cmUserInfo.getId() == 0)
		{
			object.setDesc("不能发赏给自己");
			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			return object;
		}
		
		if((accountUser.getClientId() - cmUserInfo.getClientId() == 0) )
		{
			object.setDesc("亲，不要自娱自乐哦！");
			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			return object;
		}
		
		if(StringUtil.isNotEmpty(accountUser.getImei()) && StringUtil.isNotEmpty(accountUser.getImei()) ){
			if( accountUser.getImei().trim().equals(cmUserInfo.getImei().trim()) )
			{
				object.setDesc("亲，不要自娱自乐哦！");
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				return object;
			}
		}
		
		pmTopicJoinProducts.setPsId(psId);
		pmTopicJoinProducts.setUpdateTime(DateUtils.getCurrDateTimeStr());
		pmTopicJoinProducts.setAcceptUserId(psUserId);
		pmTopicJoinProducts.setIsGet("1");
		pmTopicJoinProducts.setGetType("1");
		pmTopicJoinProducts.setIsPri(0);
		pmTopicJoinProductsDao.update(pmTopicJoinProducts);
		
		pmTopicPsProducts.setIsGet("1");
		pmTopicPsProducts.setPrice(price);
		pmTopicPsProductsDao.update(pmTopicPsProducts);
		
		String month = pmTopicPsProducts.getPsTime().substring(5, 7);
		String day =  pmTopicPsProducts.getPsTime().substring(8, 10);
		PushUtil.pullOneMessage(pmTopicPsProducts.getPsUserId()+"", "亲，你于"+month+"月"+day+"日的图因为太帅，已经被采纳，奖金已充值至钱包账户", "23", pmTopicJoinProducts.getId()+"", null);
		
		AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		
    	if(StringUtil.isEmpty(accountUser))
    	{
    		accountUser = createOtherAccount(psUserId);
    	}
    	
    	double wallet = BigDecimalUtil.sum(price, accountUser.getWallet());
    	
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(psUserId);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("2");
    	accountWalletLog.setPrice(price);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(wallet);
		accountWalletLog.setJoinId(joinId);
		accountWalletLog.setStatus("1");
		accountWalletLogDao.save(accountWalletLog);
		accountUserLog.setOtherId(accountWalletLog.getId());
		accountUserLog.setApiName(ApiNameEnum.getPPrice.getCode());
		accountUserLogDao.save(accountUserLog);
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(wallet);
    	accountUserDao.update(accountUser);
    	log.error("奖励金额：" + encouragePirce );
    	
    	//计算鼓励奖  10 块钱以内奖励 10人以下，大于10块钱奖励10个人 
    	if(encouragePirce > 0.3 ){
    		//奖励人数
    		int encourageCount = (int)(encouragePirce*10);
    		if(encouragePirce > 1){
    			encourageCount = 10; 
    		}
    		
    		//查询参与P图设计师
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("joinId", joinId);
    		map.put("psUserIdNotIn", pmTopicPsProducts.getPsUserId());
    		map.put("userType", 1);//设计师
    		map.put("limit", encourageCount);
    		map.put("offset", 0);
    		map.put("pXuanShang", "pXuanShang");
    		List<PmTopicPsProducts> pslistSheji = pmTopicPsProductsDao.findByMap(map);
    		
    		if( encourageCount > pslistSheji.size() ){//设计师数量小于奖励人数
    			//查询参与P图非设计师的用户
    			map.put("userType", 0);//普通用户
    			map.put("limit", encourageCount-pslistSheji.size());
    			List<PmTopicPsProducts> pslistPuTong = pmTopicPsProductsDao.findByMap(map);
    			for (PmTopicPsProducts puTong : pslistPuTong) {
    				pslistSheji.add(puTong);
				}
    		}
    		
    		
    		if(pslistSheji!=null && pslistSheji.size()>0){
    			double encouragePrice = encouragePirce/pslistSheji.size();
    			log.error("奖励人数：" + pslistSheji.size() );
    			for (PmTopicPsProducts psProduct : pslistSheji) {
    				updateAccount(psProduct.getPsUserId(), joinId, encouragePrice);
    				String monthTo = psProduct.getPsTime().substring(5, 7);
    				String dayTo =  psProduct.getPsTime().substring(8, 10);
    				PushUtil.pullOneMessage(psProduct.getPsUserId()+"", "亲，你于"+monthTo+"月"+dayTo+"日P的图，虽然未被采纳，但也很优秀，给予部分奖励，奖金已充值至钱包账户", "23", pmTopicJoinProducts.getId()+"", null);
    			}
    		}
    	}
    	
    	//采纳完更新缓存
    	for (int i = 0; i <= 5; i++) {
    		int pageNo = i;
    		String key = "demandPIndex@page"+pageNo;
    		List<PIndexDto>	list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
    		if( list!=null && list.size() > 0){
    			for (PIndexDto pIndexDto : list) {
					if(pIndexDto.getJoinId().equals(joinId)){
						CacheHelper.getInstance().remove(key);
						break;
					}
				}
    		}else{
    			key = "morePIndex@time"+pageNo;
    			list = (List<PIndexDto>) CacheHelper.getInstance().get(key);
        		if( list!=null && list.size() > 0){
        			for (PIndexDto pIndexDto : list) {
    					if(pIndexDto.getJoinId().equals(joinId)){
    						CacheHelper.getInstance().remove(key);
    						break;
    					}
    				}
        		}
    		}
		}
    	
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public void sysConfirmP(Map<String,Object> passMap){
		Long joinId = CommonUtil.nvlLong(passMap.get("joinId"));
		Long psId = CommonUtil.nvlLong(passMap.get("psId"));
		
		PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(joinId);
		if(StringUtil.isEmpty(pmTopicJoinProducts)){
			return;
		}
		if(!"0".equals(pmTopicJoinProducts.getIsGet())){
			return;
		}
		double price = pmTopicJoinProducts.getPrice()*0.8;//20160816   改 80% 给采纳用户 10%给设计师 10%平台
		double encouragePirce = pmTopicJoinProducts.getPrice()*0.1;//鼓励 设计师 
		if(price <= 0){
			return;
		}
		PmTopicPsProducts pmTopicPsProducts = pmTopicPsProductsDao.getById(psId);
		if(StringUtil.isEmpty(pmTopicPsProducts)){
			return;
		}
		
		Long psUserId = pmTopicPsProducts.getPsUserId();
		
		pmTopicJoinProducts.setPsId(psId);
		pmTopicJoinProducts.setUpdateTime(DateUtils.getCurrDateTimeStr());
		pmTopicJoinProducts.setAcceptUserId(psUserId);
		pmTopicJoinProducts.setIsGet("1");
		pmTopicJoinProducts.setGetType("2");
		pmTopicJoinProducts.setIsPri(0);
		pmTopicJoinProductsDao.update(pmTopicJoinProducts);
		
		pmTopicPsProducts.setIsGet("1");
		pmTopicPsProducts.setPrice(price);
		pmTopicPsProductsDao.update(pmTopicPsProducts);
		
		AccountUser accountUser = accountUserDao.getById(psUserId);
    	if(StringUtil.isEmpty(accountUser))
    	{
    		accountUser = createOtherAccount(psUserId);
    	}
    	
    	double wallet = BigDecimalUtil.sum(price, accountUser.getWallet());
    	
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(psUserId);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("2");
    	accountWalletLog.setPrice(price);
    	accountWalletLog.setStatus("1");
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(wallet);
		accountWalletLog.setJoinId(joinId);
		accountWalletLogDao.save(accountWalletLog);
		
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(wallet);
    	accountUserDao.update(accountUser);
    	
    	//计算鼓励奖  10 块钱以内奖励 10人以下，大于10块钱奖励10个人 
    	if(encouragePirce > 0.3 ){
    		//奖励人数
    		int encourageCount = (int)(encouragePirce*10);
    		if(encouragePirce > 1){
    			encourageCount = 10; 
    		}
    		//查询参与P图设计师
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("joinId", joinId);
    		map.put("psUserIdNotIn", pmTopicPsProducts.getPsUserId());
    		map.put("userType", 1);//设计师
    		map.put("limit", encourageCount);
    		map.put("offset", 0);
    		map.put("pXuanShang", "pXuanShang");
    		List<PmTopicPsProducts> pslistSheji = pmTopicPsProductsDao.findByMap(map);
    		
    		if( encourageCount > pslistSheji.size() ){//设计师数量小于奖励人数
    			//查询参与P图非设计师的用户
    			map.put("userType", 0);//普通用户
    			map.put("limit", encourageCount-pslistSheji.size());
    			List<PmTopicPsProducts> pslistPuTong = pmTopicPsProductsDao.findByMap(map);
    			for (PmTopicPsProducts puTong : pslistPuTong) {
    				pslistSheji.add(puTong);
				}
    		}
    		
    		
    		if(pslistSheji!=null && pslistSheji.size()>0){
    			double encouragePrice = encouragePirce/pslistSheji.size();
    			for (PmTopicPsProducts psProduct : pslistSheji) {
    				updateAccount(psProduct.getPsUserId(), joinId, encouragePrice);
    				String monthTo = psProduct.getPsTime().substring(5, 7);
    				String dayTo =  psProduct.getPsTime().substring(8, 10);
    				PushUtil.pullOneMessage(psProduct.getPsUserId()+"", "亲，你于"+monthTo+"月"+dayTo+"日P的图，虽然未被采纳，但也很优秀，给予部分奖励，奖金已充值至钱包账户", "23", pmTopicJoinProducts.getId()+"", null);
    			}
    		}
    	}
    	
		return;
	}
	
	
	public void updateAccount(Long psUserId,long joinId ,double price){
		AccountUser accountUser = accountUserDao.getById(psUserId);
    	if(StringUtil.isEmpty(accountUser)){
    		accountUser = createOtherAccount(psUserId);
    	}
    	double wallet = BigDecimalUtil.sum(price, accountUser.getWallet());
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(psUserId);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("2");
    	accountWalletLog.setPrice(price);
    	accountWalletLog.setStatus("1");
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(wallet);
		accountWalletLog.setJoinId(joinId);
		accountWalletLogDao.save(accountWalletLog);
		
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(wallet);
    	accountUserDao.update(accountUser);
		
	}
	
	public void backP(Map<String,Object> passMap){
		Long joinId = CommonUtil.nvlLong(passMap.get("joinId"));
		
		PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(joinId);
		if(StringUtil.isEmpty(pmTopicJoinProducts))
		{
			return;
		}
		if("2".equals(pmTopicJoinProducts.getIsGet()))
		{
			return;
		}
		double price = pmTopicJoinProducts.getPrice();
		if(price <= 0)
		{
			return;
		}
		pmTopicJoinProducts.setIsGet("2");
		pmTopicJoinProducts.setGetType("2");
		pmTopicJoinProductsDao.update(pmTopicJoinProducts);
		
		AccountUser accountUser = accountUserDao.getById(pmTopicJoinProducts.getUserId());
		
		double wallet = BigDecimalUtil.sum(accountUser.getWallet(), price);
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(pmTopicJoinProducts.getUserId());
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("3");
    	accountWalletLog.setPrice(price);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(wallet);
		accountWalletLog.setJoinId(joinId);
		accountWalletLog.setStatus("1");
		accountWalletLogDao.save(accountWalletLog);
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(wallet);
    	accountUserDao.update(accountUser);
		return;
	}
	
	public ResponseObject getPayParameter(Map<String,Object> passMap,ResponseObject object){
		
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String payType =   passMap.get("payType").toString();
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		
		
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isEmpty(accountUserBuy))
		{
			return object;
		}
		if(accountUserBuy.getUserId()-cmUserInfo.getId() != 0)
		{
			return object;
		}
		if(!"0".equals(accountUserBuy.getStatus())){
			return object;
		}
		
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
		if(StringUtil.isEmpty(accountUser))
    	{
			accountUser = createAccount(passMap);
    	}
		
		double price = accountUserBuy.getPrice();
		
		double userPrice = accountUser.getWallet();
		
		if("3".equals(payType))
		{
			if(price == 0)
			{
				return object;
			}
			if(BigDecimalUtil.sub(userPrice, price) < 0)
			{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("余额不足");
				return object;
			}
			
			
		}else if("4".equals(payType) || "5".equals(payType)){
			if(price == 0)
			{
				return object;
			}
			if(userPrice <= 0)
			{
				return object;
			}
		}else if("1".equals(payType) || "2".equals(payType)){
			if(price == 0)
			{
				return object;
			}
		}else if("0".equals(payType)){
			if(price != 0)
			{
				return object;
			}
		}
		
		
		String type = accountUserBuy.getType();
		String useType = null;
		String apiName = null;
		String productDes = null;
		String productDetail = null;
		if("1".equals(type))
		{
			useType = "1";
			apiName = ApiNameEnum.colouredD.getCode();
			productDes = "彩绘订制";
			productDetail = "彩绘订制";
		}else if("2".equals(type)){
			
			useType = "2";
			apiName = ApiNameEnum.priceP.getCode();
			productDes = "悬赏求P";
			productDetail = "悬赏求P";
		}
		String orderNo = accountUserBuy.getOrderNo();
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("accountBuyId", accountUserBuy.getId());
		List<Order> orderList = orderDao.findByMap(searchMap);
		Order order = null;
		if(orderList != null && orderList.size() > 0)
		{
			order = orderList.get(0);
			orderNo = order.getOrderNo();
			if(StringUtil.isNotEmpty(accountUserBuy.getId()))
			{
				accountUserBuyDao.deleteByAccountBuyId(searchMap);
			}
		}
    	
		passMap.put("accountUser", accountUser);
		passMap.put("price", price);
		passMap.put("useType", useType);
		passMap.put("apiName", apiName);
		passMap.put("productDes", productDes);
		passMap.put("productDetail", productDetail);
		passMap.put("orderNo", orderNo);
		
		PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(accountUserBuy.getRetionId());
    	Map<String,Object> returnMap = pay(accountUserBuy, pmTopicJoinProducts, passMap);
		
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	

	public ResponseObject colouredDSubmit(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long userId = cmUserInfo.getId();
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		
		//确保10s 不重复提交操作
    	String userIdStr = (String) CacheHelper.getInstance().get("colouredDSubmit@userId"+userId);
		if(userIdStr == null || !userId.toString().equals(userIdStr) ){
			CacheHelper.getInstance().set(15,"colouredDSubmit@userId"+userId, userId);
		}else{
			object.setCode(Constant.RESPONSE_ERROR_10014);
	    	object.setDesc("亲，您已提交，请稍后！");
	    	return object;
		}
		
		String type =  passMap.get("type").toString();
		PmCommodityInfo pmCommodityInfo = pmCommodityInfoDao.getById(Long.parseLong(type));
		if (StringUtil.isEmpty(pmCommodityInfo)) {
			return object;
		}
		if(pmCommodityInfo.getMaxAmount() !=0 ){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("commodityInfoId", pmCommodityInfo.getId() );
			param.put("status",  "1");
			String startDate = DateUtils.format(new Date())+ " 00::00:00";
			param.put("startDateStr",  startDate);
			String endDate = DateUtils.format(new Date())+ " 23::59:00";
			param.put("endDateStr",  endDate );
			int num = accountUserBuyDao.getCount(param).intValue();
			
			if(num >= pmCommodityInfo.getMaxAmount()){
				object.setDesc("亲！今天的宝贝都被抢光了，明天有更多优惠哦！");
				return object;
			}
		}
		
		String contact =   CommonUtil.nvl(passMap.get("contact"));
		String qqContact =  CommonUtil.nvl(passMap.get("qqContact"));
		String depict =   CommonUtil.nvl(passMap.get("depict"));
		String url =   CommonUtil.nvl(passMap.get("url"));
		String thumbnail =   CommonUtil.nvl(passMap.get("thumbnail"));
		
		String phoneShell =   CommonUtil.nvl(passMap.get("phoneShell"));
		String receivePerson  =   CommonUtil.nvl(passMap.get("receivePerson"));
		String receiveAddress =   CommonUtil.nvl(passMap.get("receiveAddress"));
		
		PmProductInfo resource = new PmProductInfo();
		resource.setClientId(clientId);
		resource.setResourceId(UUIDGenerator.getUUID());
		resource.setUrl(url);
		resource.setThumbnail(thumbnail);
		resource.setIsPublic(3);
		resource.setAudit(0);
		resource.setCreateFrom("1");
		resource.setUserId(userId);
		pmProductInfoDao.save(resource);
		
		PmTopicJoinProducts pmTopicJoinProducts = new PmTopicJoinProducts();
		pmTopicJoinProducts.setContact(HtmlUtils.htmlEscape(contact));
		pmTopicJoinProducts.setQqContact(qqContact);
		pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
		pmTopicJoinProducts.setTopicId(1L);
		pmTopicJoinProducts.setProductId(resource.getId());
		pmTopicJoinProducts.setUserId(userId);
		pmTopicJoinProducts.setJoinType(type);
		pmTopicJoinProducts.setStatus("0");
		pmTopicJoinProducts.setPhoneShell(phoneShell);
		pmTopicJoinProducts.setReceiveAddress(receiveAddress);
		pmTopicJoinProducts.setReceivePerson(receivePerson);
		pmTopicJoinProductsDao.save(pmTopicJoinProducts);
		
		AccountUserBuy accountUserBuy = new AccountUserBuy();
		accountUserBuy.setRetionId(pmTopicJoinProducts.getId());
		accountUserBuy.setPrice(CommonUtil.nvlDouble(pmCommodityInfo.getPrice())/100);
		accountUserBuy.setCommodityId(pmCommodityInfo.getCommodityId());
		accountUserBuy.setCommodityInfoId(pmCommodityInfo.getId());
		accountUserBuy.setUserId(userId);
		accountUserBuy.setType("1");
		accountUserBuy.setOrderNo(RandomUtils.getRandomStr(1, 32, 1)[0]);
		accountUserBuyDao.save(accountUserBuy);
		
		pmTopicJoinProducts.setAccountBuyId(accountUserBuy.getId());
		pmTopicJoinProductsDao.update(pmTopicJoinProducts);
		
	
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("accountBuyId", accountUserBuy.getId());
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	public ResponseObject pricePSubmit(Map<String,Object> passMap,ResponseObject object){
		
		double price = CommonUtil.nvlDouble(passMap.get("price"));
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long userId = cmUserInfo.getId();
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		
		if(price >= 10000){
			object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("金额不合法");
			return object;
		}
		
		if(cmUserInfo.getIsAccusation() == 1)
    	{
    		object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("被举报不能使用");
			return object;
    	}
		
		String depict =  passMap.get("depict").toString();
		String url =  passMap.get("url").toString();
		String thumbnail =  passMap.get("thumbnail").toString();
		
		PmProductInfo resource = new PmProductInfo();
		resource.setClientId(clientId);
		resource.setResourceId(UUIDGenerator.getUUID());
		resource.setUrl(url);
		resource.setThumbnail(thumbnail);
		resource.setIsPublic(2);
		resource.setAudit(1);
		resource.setCreateType(0);
		resource.setCreateFrom("1");
		resource.setUserId(userId);
		pmProductInfoDao.save(resource);
		
		PmTopicJoinProducts pmTopicJoinProducts = new PmTopicJoinProducts();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("code",SysParamConstant.EXCLUSIVE_MEMBER_AMOUNT);
		List<SysParameter> sysParameterList = sysParameterService.findByMap(param);
		if(sysParameterList != null && sysParameterList.size() > 0){
			if( Double.valueOf(sysParameterList.get(0).getValue()) <= price ){
				pmTopicJoinProducts.setIsPri(1);
			}
		}else{
			log.error("专属会员悬赏单金额 未配置！");
			object.setCode(Constant.RESPONSE_ERROR_10003);
			return object;
		}
		
		
		pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
		pmTopicJoinProducts.setTopicId(3L);
		pmTopicJoinProducts.setProductId(resource.getId());
		pmTopicJoinProducts.setUserId(userId);
		pmTopicJoinProducts.setPrice(price);
		pmTopicJoinProductsDao.save(pmTopicJoinProducts);
		
		
		if(StringUtil.isNotEmpty(passMap.get("labelId"))){
			String labelIds = passMap.get("labelId").toString();
			if( StringUtil.isNotEmpty(labelIds)){
				String [] labelIdAry = labelIds.split(",");
				for (int i = 0; i < labelIdAry.length; i++) {
					String labelId = labelIdAry[i];
					PmProductLabel pmProductLabel = new PmProductLabel(); 
					pmProductLabel.setLabelId(Long.valueOf(labelId));
					pmProductLabel.setProductId(resource.getId());
					pmProductLabelDao.save(pmProductLabel);
				}
			}
		}
		
		
		AccountUserBuy accountUserBuy = new AccountUserBuy();
		accountUserBuy.setRetionId(pmTopicJoinProducts.getId());
		accountUserBuy.setPrice(price);
		accountUserBuy.setUserId(userId);
		accountUserBuy.setType("2");
		accountUserBuy.setOrderNo(RandomUtils.getRandomStr(1, 32, 1)[0]);
		accountUserBuyDao.save(accountUserBuy);
		
		pmTopicJoinProducts.setAccountBuyId(accountUserBuy.getId());
		pmTopicJoinProductsDao.update(pmTopicJoinProducts);
    	
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("accountBuyId", accountUserBuy.getId());
		returnMap.put("joinId", pmTopicJoinProducts.getId());
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	private Map<String, Object> pay(AccountUserBuy accountUserBuy,PmTopicJoinProducts pmTopicJoinProducts,Map<String,Object> passMap){
		String payType = CommonUtil.nvl(passMap.get("payType"));
		String apiName = CommonUtil.nvl(passMap.get("apiName"));
		double price = CommonUtil.nvlDouble(passMap.get("price"));
		AccountUserLog accountUserLog =  (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		
		accountUserBuy.setPayType(payType);
		if("3".equals(payType))
		{
			accountUserBuy.setStatus("1");
			accountUserBuy.setUpdateTime(DateUtils.getCurrDateTimeStr());
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(accountUser.getWallet(), price);
			balancePay(accountUserBuy,pmTopicJoinProducts, surplusPrice,passMap);
			
		}else if("4".equals(payType)){
			groupPay(accountUserBuy,pmTopicJoinProducts, accountUser.getWallet(),passMap);
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(price, accountUser.getWallet());
			return moneyPay(accountUserBuy,pmTopicJoinProducts,"2", surplusPrice,passMap);
		}else if("5".equals(payType)){
			groupPay(accountUserBuy,pmTopicJoinProducts, accountUser.getWallet(),passMap);
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(price, accountUser.getWallet());
			return moneyPay(accountUserBuy,pmTopicJoinProducts,"1", surplusPrice,passMap);
		}else if("1".equals(payType)){
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			return moneyPay(accountUserBuy,pmTopicJoinProducts,"1", price,passMap);
		}else if("2".equals(payType)){
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			return moneyPay(accountUserBuy,pmTopicJoinProducts,"2", price,passMap);
		}else if("0".equals(payType)){
			
			accountUserBuy.setUpdateTime(DateUtils.getCurrDateTimeStr());
			accountUserBuy.setStatus("1");
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
		}
		
		String type = accountUserBuy.getType();
		if("1".equals(type))
		{
			accountUser.setType("1");
			accountUserDao.update(accountUser);
			
			pmTopicJoinProducts.setStatus("1");
			pmTopicJoinProductsDao.update(pmTopicJoinProducts);
			
			pmScoreLogService.addScore("10", cmUserInfo, cmUserInfo.getClientId()+"", pmTopicJoinProducts.getProductId()+"",accountUserBuy.getPrice()+"");
			cmUserInfoDao.update(cmUserInfo);
			
			//  zhouj 5.3 五一过后暂停
//			List<Double> redlist = QiangHongBaoUtil.createRed(accountUserBuy.getPrice());
//			if(redlist != null && redlist.size() > 0)
//			{
//				for(int i=1;i<=redlist.size();i++){
//					double a = redlist.get(i-1);
//					AccountRedPacket accountRedPacket = new AccountRedPacket();
//					accountRedPacket.setUserId(accountUserBuy.getUserId());
//					accountRedPacket.setPrice(a);
//					accountRedPacket.setJoinId(pmTopicJoinProducts.getId());
//					accountRedPacket.setNum(i);
//					accountRedPacket.setType("1");
//					accountRedPacketDao.save(accountRedPacket);
//				}
//			}
		}else if("2".equals(type)){
			
			pmTopicJoinProducts.setStatus("1");
			pmTopicJoinProductsDao.update(pmTopicJoinProducts);
			
			pmScoreLogService.addScore("8", cmUserInfo, accountUserBuy.getId()+"", pmTopicJoinProducts.getProductId()+"","");
	    	pmScoreLogService.addScore("1019", cmUserInfo, accountUserBuy.getId()+"", pmTopicJoinProducts.getProductId()+"","");
	    	cmUserInfoDao.update(cmUserInfo);
		}
		return null;
	}
	
	private void balancePay(AccountUserBuy accountUserBuy,PmTopicJoinProducts pmTopicJoinProducts,double surplusPrice,Map<String,Object> passMap){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		double price = CommonUtil.nvlDouble(passMap.get("price"));
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
		accountWalletLog.setUserId(userId);
		accountWalletLog.setType("2");
		accountWalletLog.setUseType(useType);
		accountWalletLog.setPrice(price);
		accountWalletLog.setStatus("1");
		accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setJoinId(pmTopicJoinProducts.getId());
		accountWalletLog.setAccountBuyId(accountUserBuy.getId());
		accountWalletLog.setPayType(payType);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(surplusPrice);
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(surplusPrice);
		accountUserDao.update(accountUser);
		
		accountWalletLogDao.save(accountWalletLog);
	}
	
	
	private void groupPay(AccountUserBuy accountUserBuy,PmTopicJoinProducts pmTopicJoinProducts,double surplusPrice,Map<String,Object> passMap){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String orderNo =  CommonUtil.nvl(passMap.get("orderNo"));
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
		accountWalletLog.setUserId(userId);
		accountWalletLog.setType("2");
		accountWalletLog.setUseType(useType);
		accountWalletLog.setPrice(surplusPrice);
		accountWalletLog.setStatus("0");
		accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setJoinId(pmTopicJoinProducts.getId());
		accountWalletLog.setAccountBuyId(accountUserBuy.getId());
		accountWalletLog.setPayType(payType);
		accountWalletLog.setOrderNo(orderNo);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(0);
		
		accountWalletLogDao.save(accountWalletLog);
		
	}
	
	private Map<String, Object> moneyPay(AccountUserBuy accountUserBuy,PmTopicJoinProducts pmTopicJoinProducts,String orderType,double orderPrice,Map<String,Object> passMap){
		
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String orderNo =  CommonUtil.nvl(passMap.get("orderNo"));
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		
		
    	Map<String, Object> returnMap = null;
    	if("1".equals(orderType))
    	{
    		returnMap = OrderUtil.getWeixinInfo(passMap,orderPrice);
    	}else if("2".equals(orderType))
    	{
    		returnMap = OrderUtil.getAliInfo(passMap,orderPrice);
    	}
    	String code = CommonUtil.nvl(returnMap.get("code"));
		if(StringUtil.isNotEmpty(code) && "1".equals(code))
		{
			try {
				throw new Exception("订单获取失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		Long accountBuyId = accountUserBuy.getId();
    	Order order = new Order();
    	order.setClientId(clientId);
    	order.setUserId(userId);
    	order.setPayType(orderType);
    	order.setAmount(orderPrice+"");
    	order.setOrderNo(orderNo);
    	order.setType(useType);
    	order.setAccountBuyId(accountBuyId);
    	orderDao.save(order);
    	
    	double tempPrice = BigDecimalUtil.sum(accountUser.getWallet(), orderPrice);
    	
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(userId);
    	accountWalletLog.setOrderNo(orderNo);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("1");
    	accountWalletLog.setPrice(orderPrice);
    	accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(tempPrice);
		accountWalletLog.setJoinId(pmTopicJoinProducts.getId());
		accountWalletLog.setAccountBuyId(accountBuyId);
		accountWalletLog.setPayType(payType);
		accountWalletLogDao.save(accountWalletLog);
		
		
		AccountWalletLog consumeWalletLog = new AccountWalletLog();
		consumeWalletLog.setUserId(userId);
		consumeWalletLog.setOrderNo(orderNo);
		consumeWalletLog.setType("2");
		consumeWalletLog.setUseType(useType);
		consumeWalletLog.setPrice(orderPrice);
		consumeWalletLog.setClientId(clientId);
		consumeWalletLog.setImei(imei);
		consumeWalletLog.setImsi(imsi);
		consumeWalletLog.setStartAccountPrice(tempPrice);
		consumeWalletLog.setEndAccountPrice(BigDecimalUtil.sub(tempPrice,orderPrice));
		consumeWalletLog.setJoinId(pmTopicJoinProducts.getId());
		consumeWalletLog.setAccountBuyId(accountUserBuy.getId());
		consumeWalletLog.setPayType(payType);
		accountWalletLogDao.save(consumeWalletLog);
		
		return returnMap;
		
	}
	
	public static void main(String[] args) {
	}

	public void batchDelMyp(String joinIds) {
		String joinId [] = joinIds.split(",");
		for (int i = 0; i < joinId.length; i++) {
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(Long.valueOf(joinId[i]));
			pmTopicJoinProducts.setStatus("4");
			pmTopicJoinProductsDao.update(pmTopicJoinProducts);
		}
		
	}
}
