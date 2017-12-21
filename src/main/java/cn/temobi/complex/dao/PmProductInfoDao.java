package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.ThemeUsedByPdtDto;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;


public interface PmProductInfoDao extends SimpleDao<PmProductInfo, Long> {
	
	public List<Long> findIdList(Map<String, Object> map);
	
	public List<Long> findProductList(Map<String, Object> map);
	
	public Page<PmProductInfoDto> findByPageDtoTo(Page<PmProductInfoDto> page, Object parameter);
	
	public Page<ThemeUsedByPdtDto> findByPageDtoTo2(Page<ThemeUsedByPdtDto> page, Object parameter);
	
	public List<PmProductInfoDto> findNew(Map<String, Object> map);
	
	public List<PmProductInfo> findNotPraises(Map<String, Object> map);
	
	public List<Long> findCircleId(Map<String, Object> map);
	
	public List<Long> findProductIdList(Map<String, Object> map);
	
	public List<Long> findNewProductId(Map<String, Object> map);
	
	public void updateAll(Map<String,Object> map);
	
	public List<PmProductInfoDto> findDtoMap(Map<String, Object> map);

	public void saveBatch(List<PmProductInfo> pmProductInfoList);
}
