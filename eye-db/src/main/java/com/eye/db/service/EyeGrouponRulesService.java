package com.eye.db.service;

import com.alibaba.druid.util.StringUtils;
import com.eye.db.dao.EyeGoodsMapper;
import com.eye.db.dao.EyeGrouponRulesMapper;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.domain.EyeGrouponRulesExample;
import com.eye.db.domain.EyeMemberGoodsExample;
import com.eye.db.util.GrouponConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EyeGrouponRulesService {
    @Resource
    private EyeGrouponRulesMapper mapper;
    @Resource
    private EyeGoodsMapper goodsMapper;
    private EyeGoods.Column[] goodsColumns = new EyeGoods.Column[]{EyeGoods.Column.id, EyeGoods.Column.name, EyeGoods.Column.brief, EyeGoods.Column.picUrl, EyeGoods.Column.counterPrice, EyeGoods.Column.retailPrice};

    public int createRules(EyeGrouponRules rules) {
        rules.setAddTime(LocalDateTime.now());
        rules.setUpdateTime(LocalDateTime.now());
        return mapper.insertSelective(rules);
    }

    /**
     * 根据ID查找对应团购项
     *
     * @param id
     * @return
     */
    public EyeGrouponRules findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询某个商品关联的团购规则
     *
     * @param goodsId
     * @return
     */
    public List<EyeGrouponRules> queryByGoodsId(Integer goodsId) {
        EyeGrouponRulesExample example = new EyeGrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    public int countByGoodsId(Integer goodsId) {
        EyeGrouponRulesExample example = new EyeGrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return (int)mapper.countByExample(example);
    }

    public List<EyeGrouponRules> queryByStatus(Short status) {
        EyeGrouponRulesExample example = new EyeGrouponRulesExample();
        example.or().andStatusEqualTo(status).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    /**
     * 获取首页团购规则列表
     *
     * @param page
     * @param limit
     * @return
     */
    public List<EyeGrouponRules> queryList(Integer page, Integer limit) {
        return queryList(page, limit, "add_time", "desc");
    }

    public List<EyeGrouponRules> queryList(Integer page, Integer limit, String sort, String order) {
        EyeGrouponRulesExample example = new EyeGrouponRulesExample();
        example.or().andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    /**
     * 判断某个团购规则是否已经过期
     *
     * @return
     */
    public boolean isExpired(EyeGrouponRules rules) {
        return (rules == null || rules.getExpireTime().isBefore(LocalDateTime.now()));
    }

    /**
     * 获取团购规则列表
     *
     * @param goodsId
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    public List<EyeGrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order) {
        EyeGrouponRulesExample example = new EyeGrouponRulesExample();
        example.setOrderByClause(sort + " " + order);

        EyeGrouponRulesExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.parseInt(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return mapper.selectByExample(example);
    }

    public void delete(Integer id) {
        mapper.logicalDeleteByPrimaryKey(id);
    }

    public int updateById(EyeGrouponRules grouponRules) {
        grouponRules.setUpdateTime(LocalDateTime.now());
        return mapper.updateByPrimaryKeySelective(grouponRules);
    }
    public void deleteByGid(Integer gid) {
		EyeGrouponRulesExample example = new EyeGrouponRulesExample();
		example.or().andGoodsIdEqualTo(gid);
		mapper.logicalDeleteByExample(example);
	}
}