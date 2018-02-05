package com.cncounter.online.vo;

import com.cncounter.online.entity.OnlineUser;
import com.cncounter.util.string.StringNumberUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created on 2018-02-05.
 */
public class OnlineUserVO {

    // 自增ID
    private Long id;
    // 用户名
    private String username;
    // 密码,加密后的
    private String password;
    // 邮箱
    private String email;
    // email验证状态
    private Integer emailCheckState;
    // 手机号
    private String mobile;
    // 手机号验证状态
    private Integer mobileCheckState;
    // 昵称
    private String nickName;
    // 真实姓名
    private String realName;
    // 真实姓名验证状态
    private Integer realNameCheckState;
    // 真实SID
    private String sid;
    // SID验证状态
    private Integer sidCheckState;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 乐观锁版本号
    private Integer version;
    // 有效状态
    private Integer valid;
    // 失效原因
    private String validReason;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailCheckState() {
        return this.emailCheckState;
    }

    public void setEmailCheckState(Integer emailCheckState) {
        this.emailCheckState = emailCheckState;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMobileCheckState() {
        return this.mobileCheckState;
    }

    public void setMobileCheckState(Integer mobileCheckState) {
        this.mobileCheckState = mobileCheckState;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRealNameCheckState() {
        return this.realNameCheckState;
    }

    public void setRealNameCheckState(Integer realNameCheckState) {
        this.realNameCheckState = realNameCheckState;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getSidCheckState() {
        return this.sidCheckState;
    }

    public void setSidCheckState(Integer sidCheckState) {
        this.sidCheckState = sidCheckState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getValid() {
        return this.valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getValidReason() {
        return this.validReason;
    }

    public void setValidReason(String validReason) {
        this.validReason = validReason;
    }

    // 更具PO转换
    public static OnlineUserVO fromPO(OnlineUser entity){
        //
        OnlineUserVO onlineUserVO = new OnlineUserVO();
        //
        BeanUtils.copyProperties(entity, onlineUserVO);
        // 屏蔽特定字段
        onlineUserVO.setPassword("");
        //
        return onlineUserVO;
    }

    // 转换为PO
    public static OnlineUser toPO(OnlineUserVO onlineUserVO){
        //
        OnlineUser entity = new OnlineUser();
        //
        BeanUtils.copyProperties(onlineUserVO, entity);
        if(StringNumberUtil.isEmpty(entity.getPassword())){
            entity.setPassword(null);
        }
        //
        return entity;
    }
}
