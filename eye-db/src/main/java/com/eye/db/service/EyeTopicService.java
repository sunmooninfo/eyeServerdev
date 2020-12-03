package com.eye.db.service;

import com.github.pagehelper.PageHelper;
import com.eye.db.dao.EyeTopicMapper;
import com.eye.db.domain.EyeTopic;
import com.eye.db.domain.EyeTopic.Column;
import com.eye.db.domain.EyeTopicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeTopicService {
    @Resource
    private EyeTopicMapper topicMapper;
    private Column[] columns = new Column[]{Column.id, Column.title, Column.subtitle, Column.price, Column.picUrl, Column.readCount};

    public List<EyeTopic> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public List<EyeTopic> queryList(int offset, int limit, String sort, String order) {
        EyeTopicExample example = new EyeTopicExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleSelective(example, columns);
    }

    public int queryTotal() {
        EyeTopicExample example = new EyeTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int) topicMapper.countByExample(example);
    }

    public EyeTopic findById(Integer id) {
        EyeTopicExample example = new EyeTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<EyeTopic> queryRelatedList(Integer id, int offset, int limit) {
        EyeTopicExample example = new EyeTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<EyeTopic> topics = topicMapper.selectByExample(example);
        if (topics.size() == 0) {
            return queryList(offset, limit, "add_time", "desc");
        }
        EyeTopic topic = topics.get(0);

        example = new EyeTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<EyeTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if (relateds.size() != 0) {
            return relateds;
        }

        return queryList(offset, limit, "add_time", "desc");
    }

    public List<EyeTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        EyeTopicExample example = new EyeTopicExample();
        EyeTopicExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int updateById(EyeTopic topic) {
        topic.setUpdateTime(LocalDateTime.now());
        EyeTopicExample example = new EyeTopicExample();
        example.or().andIdEqualTo(topic.getId());
        return topicMapper.updateByExampleSelective(topic, example);
    }

    public void deleteById(Integer id) {
        topicMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeTopic topic) {
        topic.setAddTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.insertSelective(topic);
    }


    public void deleteByIds(List<Integer> ids) {
        EyeTopicExample example = new EyeTopicExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        EyeTopic topic = new EyeTopic();
        topic.setUpdateTime(LocalDateTime.now());
        topic.setDeleted(true);
        topicMapper.updateByExampleSelective(topic, example);
    }

    public EyeTopic queryById(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }
}
