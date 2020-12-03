package com.eye.db.service;

import com.github.pagehelper.PageHelper;
import com.eye.db.dao.EyeNoticeMapper;
import com.eye.db.domain.EyeNotice;
import com.eye.db.domain.EyeNoticeAdmin;
import com.eye.db.domain.EyeNoticeAdminExample;
import com.eye.db.domain.EyeNoticeExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeNoticeService {
    @Resource
    private EyeNoticeMapper noticeMapper;


    public List<EyeNotice> querySelective(String title, String content, Integer page, Integer limit, String sort, String order) {
        EyeNoticeExample example = new EyeNoticeExample();
        EyeNoticeExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return noticeMapper.selectByExample(example);
    }

    public int updateById(EyeNotice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    public void deleteById(Integer id) {
        noticeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeNotice notice) {
        notice.setAddTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        noticeMapper.insertSelective(notice);
    }

    public EyeNotice findById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    public void deleteByIds(List<Integer> ids) {
        EyeNoticeExample example = new EyeNoticeExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        EyeNotice notice = new EyeNotice();
        notice.setUpdateTime(LocalDateTime.now());
        notice.setDeleted(true);
        noticeMapper.updateByExampleSelective(notice, example);
    }
}
