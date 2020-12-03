package com.eye.common.service;

import com.eye.common.vo.GrouponRuleVo;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeGrouponService;
import com.github.pagehelper.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonGrouponRuleService {
    private final Log logger = LogFactory.getLog(CommonGrouponRuleService.class);

    @Autowired
    private EyeGrouponRulesService grouponRulesService;
    @Autowired
    private EyeGrouponService grouponService;
    @Autowired
    private EyeGoodsService goodsService;


    public List<GrouponRuleVo> queryList(Integer page, Integer size) {
        return queryList(page, size, "add_time", "desc");
    }


    public List<GrouponRuleVo> queryList(Integer page, Integer size, String sort, String order) {
        Page<EyeGrouponRules> grouponRulesList = (Page<EyeGrouponRules>)grouponRulesService.queryList(page, size, sort, order);

        Page<GrouponRuleVo> grouponList = new Page<GrouponRuleVo>();
        grouponList.setPages(grouponRulesList.getPages());
        grouponList.setPageNum(grouponRulesList.getPageNum());
        grouponList.setPageSize(grouponRulesList.getPageSize());
        grouponList.setTotal(grouponRulesList.getTotal());

        for (EyeGrouponRules rule : grouponRulesList) {
            Integer goodsId = rule.getGoodsId();
            EyeGoods goods = goodsService.findById(goodsId);
            if (goods == null)
                continue;

            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setId(goods.getId());
            grouponRuleVo.setName(goods.getName());
            grouponRuleVo.setBrief(goods.getBrief());
            grouponRuleVo.setPicUrl(goods.getPicUrl());
            grouponRuleVo.setCounterPrice(goods.getCounterPrice());
            grouponRuleVo.setRetailPrice(goods.getRetailPrice());
            grouponRuleVo.setGrouponPrice(goods.getRetailPrice().subtract(rule.getDiscount()));
            grouponRuleVo.setGrouponDiscount(rule.getDiscount());
            grouponRuleVo.setGrouponMember(rule.getDiscountMember());
            grouponRuleVo.setExpireTime(rule.getExpireTime());
            grouponList.add(grouponRuleVo);
        }

        return grouponList;
    }
}