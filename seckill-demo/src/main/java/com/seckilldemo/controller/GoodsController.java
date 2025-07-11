package com.seckilldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tortoise
 * @Date 2025/7/11 12:00
 * @PackageName:com.seckilldemo.controller
 * @ClassName: GoodsController
 * @Description: 商品控制器
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 跳转到商品列表页面
     */
    @RequestMapping("/toList")
    public String toList() {
        return "goods_list";
    }
} 