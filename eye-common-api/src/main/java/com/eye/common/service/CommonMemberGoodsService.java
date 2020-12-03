package com.eye.common.service;

import com.eye.common.vo.MemberGoodsVo;
import com.eye.db.dao.EyeMemberGoodsMapper;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.domain.EyeMemberGoodsExample;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonMemberGoodsService {

    @Resource
    private EyeMemberGoodsMapper memberGoodsMapper;

    @Resource
    private EyeGoodsService goodsService;


    public List<MemberGoodsVo> list(Integer page, Integer limit, String sort, String order, Boolean isShown) {
        EyeMemberGoodsExample memberGoodsExample = new EyeMemberGoodsExample();
        EyeMemberGoodsExample.Criteria criteria = memberGoodsExample.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        memberGoodsExample.or().andStatusEqualTo(MemberConstant.GOODS_STATUS_ON).andDeletedEqualTo(false);
        memberGoodsExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        Page<EyeMemberGoods> uloveMemberGoodsPage = (Page<EyeMemberGoods>)memberGoodsMapper.selectByExample(memberGoodsExample);


        Page<MemberGoodsVo> memberGoodsVoList = new Page<>();
        memberGoodsVoList.setPages(uloveMemberGoodsPage.getPages());
        memberGoodsVoList.setPageNum(uloveMemberGoodsPage.getPageNum());
        memberGoodsVoList.setPageSize(uloveMemberGoodsPage.getPageSize());
        memberGoodsVoList.setTotal(uloveMemberGoodsPage.getTotal());

        for (EyeMemberGoods uloveMemberGoods : uloveMemberGoodsPage) {
            Integer goodsId = uloveMemberGoods.getGoodsId();
            EyeGoods goods = goodsService.findById(goodsId);
            if (goods == null){
                continue;
            }
            MemberGoodsVo memberGoodsVo = new MemberGoodsVo();
            memberGoodsVo.setId(uloveMemberGoods.getId());//会员商品id
            memberGoodsVo.setName(goods.getName());//商品名称
            memberGoodsVo.setBrief(goods.getBrief());//商品简介
            memberGoodsVo.setPicUrl(goods.getPicUrl());//商品页面商品图片
            memberGoodsVo.setCounterPrice(uloveMemberGoods.getRetailPrice());//专柜价格
            memberGoodsVo.setRetailPrice(goods.getRetailPrice());//零售价格
            memberGoodsVo.setMemberPrice(uloveMemberGoods.getDiscountPrice());//优惠后的价格
            memberGoodsVo.setDiscount(uloveMemberGoods.getDiscount());//折扣力度
            memberGoodsVo.setExpireTime(uloveMemberGoods.getExpireTime());//到期时间
            memberGoodsVoList.add(memberGoodsVo);
        }
        return memberGoodsVoList;
    }

    public EyeMemberGoods findById(Integer id) {
        EyeMemberGoodsExample example = new EyeMemberGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return memberGoodsMapper.selectOneByExample(example);
    }
}
