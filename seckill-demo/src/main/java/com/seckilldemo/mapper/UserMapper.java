package com.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckilldemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:46
 * @PackageName:com.seckilldemo.mapper
 * @ClassName: UserMapper
 * @Description:
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {}
