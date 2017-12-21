package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CmUserCollectDao;
import cn.temobi.complex.dao.PmProductLabelDao;
import cn.temobi.complex.dao.PmProductPraisesDao;
import cn.temobi.complex.dao.PmTopicPsProductsDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.CmUserCollect;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserCollectService")
public class CmUserCollectService extends ServiceBase{

	@Resource(name = "cmUserCollectDao")
	private CmUserCollectDao cmUserCollectDao;
	
	@Resource(name = "pmProductPraisesDao")
	private PmProductPraisesDao pmProductPraisesDao;

	@Resource(name = "pmTopicPsProductsDao")
	private PmTopicPsProductsDao pmTopicPsProductsDao;
	
	@Resource(name = "pmProductLabelDao")
	private PmProductLabelDao pmProductLabelDao;
	
	
	public int update(CmUserCollect cmUserCollect){
		return cmUserCollectDao.update(cmUserCollect);
	}
	
	public Page<CmUserCollect> findByPage(Page page,Object map){
		return cmUserCollectDao.findByPage(page, map);
	}
	
	public List<CmUserCollect> findAll(){
		return cmUserCollectDao.findAll();
	}
	
	public List<CmUserCollect> findByMap(Map param){
		return cmUserCollectDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmUserCollectDao.getCount(map);
	}
	
	public CmUserCollect getById(Long id){
		return cmUserCollectDao.getById(id);
	}
	
	public int save(CmUserCollect cmUserCollect){
		return cmUserCollectDao.save(cmUserCollect);
	}
	
	public int delete(Object id){
		return cmUserCollectDao.delete(id);
	}

	public void batchDelCollect(String collectIds) {
		String collectId [] = collectIds.split(",");
		for (int i = 0; i < collectId.length; i++) {
			cmUserCollectDao.delete(Long.valueOf(collectId[i] ));
		}
		
	}

	public Page<CmUserInfoListDto> findAuthorDto(Page page,Map<String, Object> map) {
		return cmUserCollectDao.findByPage(page, "findAuthorDto", map);
	}

	public Page<PmProductInfoDto> findProductDtoByPage(Page page,Map<String, Object> map) {
		return cmUserCollectDao.findByPage(page, "findProductDtoByPage", map);
	}
	
	public Page<PmTopicJoinProducts> findPmTopicJoinByPage(Page page,Map<String, Object> map) {
		return cmUserCollectDao.findByPage(page, "findPmTopicJoinByPage", map);
	}
	
	//查询对应P图 作品
	public List<PIndexDto> findPtuProuctDto(List<PmTopicJoinProducts> joinList){
		List<PIndexDto> list = new ArrayList<PIndexDto>();
		if(joinList != null && joinList.size() > 0)
		{
			Map<String,Object> allMap = new HashMap<String, Object>();
			Map<String,Object> labelMap = new HashMap<String, Object>();
			Map<String,Object> searchMap = new HashMap<String, Object>();
			//查询每个作品的相关信息 及 其他用户ps过的三张作品图
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
				pIndexDto.setJoinId(pmTopicJoinProducts.getId());
				pIndexDto.setUserId(pmTopicJoinProducts.getUserId());
				pIndexDto.setIsPri(pmTopicJoinProducts.getIsPri());
				pIndexDto.setPrice(pmTopicJoinProducts.getPrice()+"");
				pIndexDto.setIsGet(pmTopicJoinProducts.getIsGet());
				pIndexDto.setCollectId(pmTopicJoinProducts.getCollectId());
				
				list.add(pIndexDto);
				
				labelMap.clear();
				searchMap.clear();
			}
		}
		return list;
	}

	public void cancelCollect(Map<String, Object> param) {
		cmUserCollectDao.delete("cancelCollect", param);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
