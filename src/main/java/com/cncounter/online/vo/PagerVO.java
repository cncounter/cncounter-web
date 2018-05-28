package com.cncounter.online.vo;

import com.cncounter.util.string.StringNumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 */
public class PagerVO<T> {
    // 当前页
    private Integer page;
    // 每页条数
    private Integer pageSize;
    // 跳过数量; 仅作标记
    private int skip;
    // 排序
    private List<String> orderByList;
    //
    private T condition;

    public PagerVO() {
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getSkip() {
        if (null == getPage() || null == getPageSize()) {
            return 0;
        }
        int skipNum = getPage() * getPageSize();
        if (skipNum < 0) {
            skipNum = 0;
        }

        return skipNum;
    }

    public List<String> getOrderByList() {
        return this.orderByList;
    }

    public void setOrderByList(List<String> orderByList) {
        this.orderByList = orderByList;
    }

    public void addOrderBy(String orderByClause) {
        if (null == this.orderByList) {
            this.orderByList = new ArrayList<String>();
        }
        if (StringNumberUtil.isEmpty(orderByClause)) {
            return;
        }
        orderByClause = orderByClause.trim();

        this.orderByList.add(orderByClause);
    }

    public T getCondition() {
        return this.condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

}
