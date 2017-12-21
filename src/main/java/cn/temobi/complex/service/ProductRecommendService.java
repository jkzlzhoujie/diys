package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ProductRecommendDao;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@Transactional
@Service("productRecommendService")
public class ProductRecommendService extends ServiceBase{

	@Resource(name = "productRecommendDao")
	private ProductRecommendDao productRecommendDao;
	
	public int update(ProductRecommend productRecommend){
		return productRecommendDao.update(productRecommend);
	}
	
	public Page<ProductRecommend> findByPage(Page page,Object map){
		return productRecommendDao.findByPage(page, map);
	}
	
	public Page<ProductRecommend> findByPageTwo(Page page, Map<String, String> map) {
		return productRecommendDao.findByPageTwo(page, map);
	}
	
	public List<ProductRecommend> findAll(){
		return productRecommendDao.findAll();
	}
	
	public List<ProductRecommend> findByMap(Map map){
		return productRecommendDao.findByMap(map);
	}
	
	public Number getCount(Map map){
		return productRecommendDao.getCount(map);
	}
	
	public ProductRecommend getById(Long id){
		return productRecommendDao.getById(id);
	}
	
	public int save(ProductRecommend productRecommend){
		return productRecommendDao.save(productRecommend);
	}
	
	public int delete(Object id){
		return productRecommendDao.delete(id);
	}

	
}
