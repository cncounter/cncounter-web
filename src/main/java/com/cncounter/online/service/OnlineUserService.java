package com.cncounter.online.service;

import com.cncounter.online.entity.OnlineUser;
import com.cncounter.online.vo.PagerVO;

import java.util.List;

/**
 * OnlineUser 服务类
 */
public interface OnlineUserService {

    public int insert(OnlineUser onlineUser);

    public int update(OnlineUser onlineUser);

    public int delete(OnlineUser onlineUser);

    // 根据ID查询
    public OnlineUser getById(Long id);

    // 根据简单条件查询数量
    public int countByCondition(OnlineUser condition);

    public List<OnlineUser> listByCondition(OnlineUser condition);

    // 根据简单条件查询
    public List<OnlineUser> listByPager(PagerVO<OnlineUser> pager);

    /**
     * 用户注册
     *
     * @param onlineUser
     * @return
     */
    public int register(OnlineUser onlineUser);

    /**
     * 用户验证
     *
     * @param onlineUser
     * @return
     */
    public int verify(OnlineUser onlineUser);

    /**
     * @param onlineUser
     * @return
     */
    public int remove(OnlineUser onlineUser);

}
