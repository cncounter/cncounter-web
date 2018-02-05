package com.cncounter.online.service.impl;

import com.cncounter.online.dao.mysql.OnlineUserMapper;
import com.cncounter.online.entity.OnlineUser;
import com.cncounter.online.service.OnlineUserService;
import com.cncounter.online.vo.PagerVO;
import com.cncounter.util.string.StringNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018-02-05.
 */
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OnlineUserMapper onlineUserMapper;

    @Override
    public int insert(OnlineUser onlineUser) {
        if (null == onlineUser) {
            return 0;
        }
        int rows = onlineUserMapper.insert(onlineUser);
        return rows;
    }

    @Override
    public int update(OnlineUser onlineUser) {
        if (null == onlineUser) {
            return 0;
        }
        int rows = onlineUserMapper.insert(onlineUser);
        return rows;
    }

    @Override
    public int delete(OnlineUser onlineUser) {
        if (null == onlineUser) {
            return 0;
        }
        int rows = onlineUserMapper.insert(onlineUser);
        return rows;
    }

    @Override
    public OnlineUser getById(Long id) {
        if (null == id) {
            return null;
        }
        OnlineUser onlineUser = onlineUserMapper.getById(id);
        return onlineUser;
    }

    @Override
    public int countByCondition(OnlineUser condition) {
        if (null == condition) {
            condition = new OnlineUser();
        }
        int rows = onlineUserMapper.countByCondition(condition);
        return rows;
    }

    @Override
    public List<OnlineUser> listByCondition(OnlineUser condition) {
        List<OnlineUser> result = new ArrayList<OnlineUser>();
        if(null == condition){
            return result;
        }
        result = onlineUserMapper.listByCondition(condition);
        return result;
    }

    @Override
    public List<OnlineUser> listByPager(PagerVO<OnlineUser> pager) {
        List<OnlineUser> result = new ArrayList<OnlineUser>();
        if(null == pager){
            pager = new PagerVO<OnlineUser>();
        }
        OnlineUser condition = pager.getCondition();
        if(null == condition){
            condition = new OnlineUser();
        }
        // 待实现
        result = onlineUserMapper.listByCondition(condition);
        return result;
    }

    /**
     * 用户注册
     *
     * @param onlineUser
     * @return
     */
    @Override
    public int register(OnlineUser onlineUser) {
        // 判断是否重复
        Long id = onlineUser.getId();
        if(null != id){
            logger.error("用户注册:id不能存在:{}", id);
            onlineUser.setValidReason("错误:id已存在!");
            return 0;
        }
        //
        boolean exists = existsUserAccount(onlineUser);
        if(exists){
            return 0;
        }
        //
        logger.info("用户注册: {}", onlineUser);
        return insert(onlineUser);
    }

    //
    private boolean existsUserAccount(OnlineUser onlineUser){
        boolean exists = false;
        boolean hasIdentify = false; // 有标识: 用户名; 手机号; email
        OnlineUser condition = null;
        //
        String username = onlineUser.getUsername();
        if(StringNumberUtil.notEmpty(username)){
            username = username.trim();
            condition = new OnlineUser();
            condition.setUsername(username);
            //
            List<OnlineUser> existsList = onlineUserMapper.listByCondition(condition);
            if(null != existsList && false == existsList.isEmpty()){
                onlineUser.setValidReason("用户名已注册!");
                return true;
            }
            hasIdentify = true;
        }
        //
        String email = onlineUser.getEmail();
        if(StringNumberUtil.notEmpty(email)){
            email = email.trim();
            condition = new OnlineUser();
            condition.setUsername(email);
            //
            List<OnlineUser> existsList = onlineUserMapper.listByCondition(condition);
            if(null != existsList && false == existsList.isEmpty()){
                onlineUser.setValidReason("Email已注册!");
                return true;
            }
            hasIdentify = true;
        }
        //
        String mobile = onlineUser.getMobile();
        if(StringNumberUtil.notEmpty(mobile)){
            mobile = mobile.trim();
            condition = new OnlineUser();
            condition.setUsername(mobile);
            //
            List<OnlineUser> existsList = onlineUserMapper.listByCondition(condition);
            if(null != existsList && false == existsList.isEmpty()){
                onlineUser.setValidReason("手机号已注册!");
                return true;
            }
            hasIdentify = true;
        }
        //
        if(!hasIdentify){
            onlineUser.setValidReason("缺少用户名/邮箱/手机号!");
            return true;
        }

        //
        return exists;
    }

    /**
     * 用户验证
     *
     * @param onlineUser
     * @return
     */
    @Override
    public int verify(OnlineUser onlineUser) {
        logger.info("用户验证: {}", onlineUser);
        return update(onlineUser);
    }

    /**
     * @param onlineUser
     * @return
     */
    @Override
    public int remove(OnlineUser onlineUser) {
        logger.info("删除用户: {}", onlineUser);
        return delete(onlineUser);
    }
}
