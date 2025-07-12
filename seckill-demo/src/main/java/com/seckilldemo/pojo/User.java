package com.seckilldemo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:26
 * @PackageName:com.seckilldemo.pojo
 * @ClassName: User
 * @Description:
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")  // 指定数据库表名
public class User {
    private Long id;                // 用户ID（手机号）
    private String nickname;        // 用户昵称
    private String password;        // 加密密码
    private String salt;            // 随机盐值
    private String head;            // 头像URL
    private Date registerDate;      // 注册时间
    private Date lastLoginDate;     // 最后一次登录时间
    private Integer loginCount;     // 登录次数

    /**
     * MD5双重加密（需配合commons-codec依赖）
     * @param plainPassword 明文密码
     * @param fixedSalt     系统固定盐值（从配置读取）
     */
    public static String encryptPassword(String plainPassword, String fixedSalt, String randomSalt) {
        // 第一次加密：MD5(明文+固定盐)
        String firstEncrypt = DigestUtils.md5Hex(plainPassword + fixedSalt);
        // 第二次加密：MD5(第一次结果+随机盐)
        return DigestUtils.md5Hex(firstEncrypt + randomSalt);
    }
    
    // 手动添加getter方法以确保兼容性
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

}
