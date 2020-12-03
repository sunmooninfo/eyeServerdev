package com.eye.db.service;

import com.eye.db.dao.EyeIntegralGoodsMapper;
import com.eye.db.domain.EyeGoodsKillExample;
import com.eye.db.domain.EyeIntegralGoods;
import com.eye.db.domain.EyeIntegralGoodsExample;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeIntegralGoodsService {

    @Resource
    private EyeIntegralGoodsMapper mapper;

    public List<EyeIntegralGoods> querySelective(Integer page, Integer limit, String sort, String order,Boolean isShown){
        EyeIntegralGoodsExample example = new EyeIntegralGoodsExample();
        EyeIntegralGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        example.setOrderByClause(sort + " " + order);
        criteria.andStatusEqualTo(MemberConstant.INTEGRAL_GOODS_STATUS_ON).andDeletedEqualTo(false);
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    public List<EyeIntegralGoods> querySelective(Integer goodsId,Integer page, Integer limit, String sort, String order) {
        EyeIntegralGoodsExample example = new EyeIntegralGoodsExample();

        example.setOrderByClause(sort + " " + order);

        EyeIntegralGoodsExample.Criteria criteria = example.createCriteria();

        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andStatusEqualTo(MemberConstant.INTEGRAL_GOODS_STATUS_ON);
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    public EyeIntegralGoods findById(int Id) {
        return mapper.selectByPrimaryKey(Id);
    }

    public int updateById(EyeIntegralGoods integral) {
        integral.setUpdateTime(LocalDateTime.now());
        return mapper.updateByPrimaryKeySelective(integral);
    }

    public List<EyeIntegralGoods> queryByStatus(Short status){
        EyeIntegralGoodsExample example = new EyeIntegralGoodsExample();
        example.or().andStatusEqualTo(status).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    public int countByGoodsId(Integer goodsId) {
        EyeIntegralGoodsExample example = new EyeIntegralGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(MemberConstant.INTEGRAL_GOODS_STATUS_ON).andDeletedEqualTo(false);
        return (int) mapper.countByExample(example);
    }

    public int create(EyeIntegralGoods EyeIntegral) {
        EyeIntegral.setAddTime(LocalDateTime.now());
        EyeIntegral.setUpdateTime(LocalDateTime.now());
        return mapper.insertSelective(EyeIntegral);
    }

    public void delete(Integer id) {
        mapper.logicalDeleteByPrimaryKey(id);
    }
    public void deleteByGid(Integer gid) {
		EyeIntegralGoodsExample example = new EyeIntegralGoodsExample();
		example.or().andGoodsIdEqualTo(gid);
		mapper.logicalDeleteByExample(example);
	}
}
