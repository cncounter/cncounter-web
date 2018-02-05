package com.cncounter.online.dao.mysql;

import com.cncounter.online.entity.OnlineUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
public interface OnlineUserMapper {

    public int insert(OnlineUser onlineUser);

    public int update(OnlineUser onlineUser);

    public int delete(OnlineUser onlineUser);

    public OnlineUser getById(Long id);

    public int countByCondition(OnlineUser condition);

    public List<OnlineUser> listByCondition(OnlineUser condition);
}
