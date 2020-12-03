package com.eye.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eye.db.dao.EyeTopicCategoryMapper;
import com.eye.db.domain.EyeTopicCategory;
import com.eye.db.domain.EyeTopicCategoryExample;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeTopicCategoryService {

    @Autowired
    private EyeTopicCategoryMapper eyeTopicCategoryMapper;

    /**
     * 查询所有的父级专题文章类别
     * @param parentId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */

    public List<EyeTopicCategory> querySelective(Integer parentId, Integer page, Integer limit, String sort, String order) {
        //创建条件
        EyeTopicCategoryExample eyeTopicCategoryExample = new EyeTopicCategoryExample();
        EyeTopicCategoryExample.Criteria criteria = eyeTopicCategoryExample.createCriteria();
        //根据父id,查询所有父级专题类别
        criteria.andPidEqualTo(parentId);
        //查询没有逻辑删除的分类
        criteria.andDeletedEqualTo(false);
        //判断排序是否为空，不为空条件增加排序
        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)){
        eyeTopicCategoryExample.setOrderByClause(sort + " " + order);
        }

        //查询
        return this.eyeTopicCategoryMapper.selectByExample(eyeTopicCategoryExample);
    }


    public List<EyeTopicCategory> queryByPid(int pid) {
    	EyeTopicCategoryExample example = new EyeTopicCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return this.eyeTopicCategoryMapper.selectByExample(example);
    }

    /**
     * 添加节点
     * @param topicCategory
     */
    public void add(EyeTopicCategory topicCategory) {
        //补齐数据
        topicCategory.setAddTime(LocalDateTime.now());
        topicCategory.setUpdateTime(LocalDateTime.now());
         this.eyeTopicCategoryMapper.insertSelective(topicCategory);
    }


    @Transactional
    public int deleteById(Integer topicCategoryId) {

      return eyeTopicCategoryMapper.logicalDeleteByPrimaryKey(topicCategoryId);
    }



    public EyeTopicCategory findById(Integer id) {
        return eyeTopicCategoryMapper.selectByPrimaryKey(id);
    }

    public int updateById(EyeTopicCategory topicCategory) {
        topicCategory.setUpdateTime(LocalDateTime.now());
        return eyeTopicCategoryMapper.updateByPrimaryKey(topicCategory);
    }

    public List<EyeTopicCategory> queryL1() {
    	EyeTopicCategoryExample example = new EyeTopicCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return eyeTopicCategoryMapper.selectByExample(example);
    }
}
