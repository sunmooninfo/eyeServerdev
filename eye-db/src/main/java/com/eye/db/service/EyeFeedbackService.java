package com.eye.db.service;

import com.eye.db.dao.EyeFeedbackMapper;
import com.eye.db.domain.EyeFeedback;
import com.eye.db.domain.EyeFeedbackExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yogeek
 * @date 2018/8/27 11:39
 */
@Service
public class EyeFeedbackService {
    @Autowired
    private EyeFeedbackMapper feedbackMapper;

    public Integer add(EyeFeedback feedback) {
        feedback.setAddTime(LocalDateTime.now());
        feedback.setUpdateTime(LocalDateTime.now());
        return feedbackMapper.insertSelective(feedback);
    }

    public List<EyeFeedback> querySelective(Integer userId, String username, Integer page, Integer limit, String sort, String order) {
        EyeFeedbackExample example = new EyeFeedbackExample();
        EyeFeedbackExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return feedbackMapper.selectByExample(example);
    }
}
