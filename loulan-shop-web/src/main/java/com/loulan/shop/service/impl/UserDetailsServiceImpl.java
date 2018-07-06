package com.loulan.shop.service.impl;

import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    /**
     * 配置用户认证逻辑
     *
     * @param username 由用户输入的账户
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        * 1. 通过用户名获取商家信息
        * 2. 如果商家存在，并且审核已通过，status为1，通过认证通过，返回User对象交给security作为校验参考
        * 2. 否则返回null，告知security此用户认证失败
        * */

        // 商户只要登陆就可以
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("IS_AUTHENTICATED_FULLY"));
        authorities.add(new SimpleGrantedAuthority("IS_AUTHENTICATED_REMEMBERED"));

        TbSeller seller = sellerService.findOne(username);
        if(seller != null && "1".equals(seller.getStatus())) {
            return new User(username, seller.getPassword(), authorities); // 商品没有具体权限
        }

        return null;
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
