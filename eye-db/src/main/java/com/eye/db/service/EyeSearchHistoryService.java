package com.eye.db.service;

import com.eye.db.dao.EyeSearchHistoryMapper;
import com.eye.db.domain.EyeSearchHistory;
import com.eye.db.domain.EyeSearchHistoryExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeSearchHistoryService {
    @Resource
    private EyeSearchHistoryMapper searchHistoryMapper;

    public void save(EyeSearchHistory searchHistory) {
        searchHistory.setAddTime(LocalDateTime.now());
        searchHistory.setUpdateTime(LocalDateTime.now());
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<EyeSearchHistory> queryByUid(int uid) {
        EyeSearchHistoryExample example = new EyeSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, EyeSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        EyeSearchHistoryExample example = new EyeSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        searchHistoryMapper.logicalDeleteByExample(example);
    }

    public List<EyeSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        EyeSearchHistoryExample example = new EyeSearchHistoryExample();
        EyeSearchHistoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }
}
