package com.southgis.ibase.cas.service;

import java.util.List;

/**
 * 用户认证识别器
 *
 * @author simon
 * @create 2018/5/30 15:05
 */
public interface IUserIdObtainService {

    /**
     * 通过登录方式查询其他的id
     *
     * @param clientName
     *              登录方式
     * @param id
     *              用户id
     * @return
     *              所有用户id
     */
    List<String> obtain(String clientName, String id);
}
