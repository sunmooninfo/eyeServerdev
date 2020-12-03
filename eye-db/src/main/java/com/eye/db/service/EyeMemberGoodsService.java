package com.eye.db.service;

import com.eye.db.dao.EyeMemberGoodsMapper;
import com.eye.db.domain.EyeIntegralGoodsExample;
import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.domain.EyeMemberGoodsExample;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeMemberGoodsService {

    @Resource
    private EyeMemberGoodsMapper memberGoodsMapper;

    public List<EyeMemberGoods> querySelective(Integer goodsId, Integer page, Integer limit, String sort, String order) {
        EyeMemberGoodsExample memberGoodsExample = new EyeMemberGoodsExample();

        memberGoodsExample.setOrderByClause(sort + " " + order);

        EyeMemberGoodsExample.Criteria criteria = memberGoodsExample.createCriteria();

        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andStatusEqualTo(MemberConstant.GOODS_STATUS_ON);
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return memberGoodsMapper.selectByExample(memberGoodsExample);
    }

    public int countByGoodsId(Integer goodsId) {
        EyeMemberGoodsExample example = new EyeMemberGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(MemberConstant.GOODS_STATUS_ON).andDeletedEqualTo(false);
        return (int) memberGoodsMapper.countByExample(example);
    }

    public int createRules(EyeMemberGoods memberGoods) {
        memberGoods.setAddTime(LocalDateTime.now());
        memberGoods.setUpdateTime(LocalDateTime.now());
        return memberGoodsMapper.insertSelective(memberGoods);
    }

    public EyeMemberGoods findById(Integer id) {
        EyeMemberGoods EyeMemberGoods = memberGoodsMapper.selectByPrimaryKey(id);
        return EyeMemberGoods;
    }

    public int updateById(EyeMemberGoods memberGoods) {
        memberGoods.setUpdateTime(LocalDateTime.now());
        return memberGoodsMapper.updateByPrimaryKeySelective(memberGoods);
    }

    public void delete(Integer id) {
        memberGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public List<EyeMemberGoods> queryByStatus(Short status) {
        EyeMemberGoodsExample example = new EyeMemberGoodsExample();
        example.or().andStatusEqualTo(status).andDeletedEqualTo(false);
        return memberGoodsMapper.selectByExample(example);
    }

    public EyeMemberGoods findByGoodsId(Integer goodsId) {
        EyeMemberGoodsExample example = new EyeMemberGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(MemberConstant.GOODS_STATUS_ON).andDeletedEqualTo(false);
        return memberGoodsMapper.selectOneByExample(example);
    }
    public void deleteByGid(Integer gid) {
		EyeMemberGoodsExample example = new EyeMemberGoodsExample();
		example.or().andGoodsIdEqualTo(gid);
		memberGoodsMapper.logicalDeleteByExample(example);
	}
}
