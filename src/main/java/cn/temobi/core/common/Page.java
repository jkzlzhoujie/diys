package cn.temobi.core.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.IteratorUtils;

import cn.temobi.complex.entity.Count;




/**
 * @author yingx
 * @created 2012-2-15
 * @package com.plaminfo.core.common
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Page<T> implements Iterable, Serializable {
    private static final long serialVersionUID = 8467728651599309285L;
     
    public final static int DEFAULT_PAGE_SIZE = 20;
    //-- 公共变量 --//
    public static final String ASC = "asc";
    
    public static final String DESC = "desc";
    //-- 分页查询参数 --//
    protected int pageNo = 1;
    protected int pageSize = 20;
    protected String orderBy = null;
    protected String order = null;
    //-- 返回结果 --//
    protected List<T> result = new ArrayList();
  /*  protected List<Long> pageNumList = Lists.newArrayList();*/
    protected long totalItems = 0;

    //-- 构造函数 --//
    public Page() {
    }

    public Page(int pageSize) {
        setPageSize(pageSize);
    }

    public Page(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }
   public static int pageNoToInt(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}
    //-- 分页参数访问函数 --//

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量, 默认为-1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        if(pageSize>0)
        this.pageSize = pageSize;
    }

    /**
     * 获得排序字段,无默认值. 多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 获得排序方向, 无默认值.
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方式向.
     *
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrder(final String order) {
        String lowcaseOrder = StringUtils.lowerCase(order);
        //检查order字符串的合法值
        String[] orders = StringUtils.split(lowcaseOrder, ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }
        this.order = lowcaseOrder;
    }

    /**
     * 是否已设置排序字段,无默认值.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     * 用于Mysql,Hibernate.
     */
    public int getOffset() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     * 用于Oracle.
     */
    public int getStartRow() {
        return getOffset() + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置, 序号从1开始.
     * 用于Oracle.
     */
    public int getEndRow() {
        return pageSize * pageNo;
    }

    //-- 访问查询结果函数 --//

    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 实现Iterable接口,可以for(Object item : page)遍历使用
     */
    public Iterator<T> iterator() {
        return result == null ? IteratorUtils.EMPTY_ITERATOR : result.iterator();
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalItems(final long totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * 是否最后一页.
     */
    public boolean isLastPage() {
        return pageNo == getTotalPages();
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNextPage() {
        return (pageNo <getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNextPage()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否第一页.
     */
    public boolean isFirstPage() {
        return pageNo == 1;
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPrePage() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPrePage()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 根据pageSize与totalItems计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalItems <= 0) {
            return 1;
        }

        long count = totalItems / pageSize;
        if (totalItems % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
     *
     * @param count 需要计算的列表大小
     * @return pageNo列表
     */
    public List<Long> getSlider(int count) {
        int halfSize = count / 2;
        long totalPage = getTotalPages();

        long startPageNumber = Math.max(pageNo - halfSize, 1);
        long endPageNumber = Math.min(startPageNumber + count - 1, totalPage);

        if (endPageNumber - startPageNumber < count) {
            startPageNumber = Math.max(endPageNumber - count, 1);
        }

        List<Long> result = new ArrayList<Long>();
        for (long i = startPageNumber; i <= endPageNumber; i++) {
            result.add(new Long(i));
        }
        return result;
    }
    
    public Page getPage(String pageNo,String pageSize){
		int pageS=0;
		if (StringUtils.isEmpty(pageNo))
			pageNo = "1";
		if(StringUtils.isEmpty(pageSize)){
			pageS=15;
		}else{
			pageS=Integer.parseInt(pageSize);
		}
		Page page = new Page(Integer.parseInt(pageNo), pageS);
		return page;
	}
    
    public Page<T> getListPage(Page page,List<T> s){
    	List<T> list = new ArrayList<T>();
    	page.setTotalItems(s.size());
    	//起始、终止行
		int star = page.getOffset();
		int end = page.getEndRow();
		
		if(page.isHasNextPage()){
			list = s.subList(star, end);			
		}else{
			list = s.subList(star, (int)page.getTotalItems());
		}
    	page.setResult(list);
    	return page;
    }
}