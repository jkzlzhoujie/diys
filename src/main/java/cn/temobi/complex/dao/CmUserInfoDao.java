package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.CmUserInfoUpdateDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface CmUserInfoDao extends SimpleDao<CmUserInfo, Long> {

	public List<CmUserInfoListDto> findByList(Map<String, Object> map);
	
	public List<CmUserInfo> findLoginUser(Map<String, Object> map);
	
	public List<CmUserInfo> findRand(Map<String, Object> map);
	
	public List<Long> findCircleId(Map<String, Object> map);
	
	public List<CmUserInfo> findByPage(Map<String, Object> map);

	public List<CmUserInfo> findPriCmUser(Map<String, String> map);

	public void updatePartCmUser(CmUserInfoUpdateDto updateUser);

	public Page<CmUserInfo> findByPageTwo(Page page, Object map);
	
}
