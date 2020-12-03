package com.eye.db.service;

import com.eye.db.dao.EyeArticleAttributeMapper;
import com.eye.db.domain.EyeArticleAttribute;
import com.eye.db.domain.EyeArticleAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeArticleAttributeService {
    @Resource
    private EyeArticleAttributeMapper attributeMapper;

    public void add(EyeArticleAttribute attribute) {
        attribute.setAddTime(LocalDateTime.now());
        attribute.setUpdateTime(LocalDateTime.now());
        attributeMapper.insertSelective(attribute);
    }

    public void deleteById(Integer id) {
        attributeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(EyeArticleAttribute attribute) {
        attribute.setUpdateTime(LocalDateTime.now());
        attributeMapper.updateByPrimaryKeySelective(attribute);
    }

    public List<EyeArticleAttribute> queryByAid(Integer articleId) {
        EyeArticleAttributeExample example = new EyeArticleAttributeExample();
        example.or().andArticleIdEqualTo(articleId).andDeletedEqualTo(false);
        return attributeMapper.selectByExample(example);
    }

    public void deleteByAid(Integer articleId) {
        EyeArticleAttributeExample example = new EyeArticleAttributeExample();
        example.or().andArticleIdEqualTo(articleId);
        attributeMapper.logicalDeleteByExample(example);
    }
}
