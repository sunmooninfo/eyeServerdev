package com.eye.db.service;


import com.eye.db.dao.EyeContactMapper;
import com.eye.db.domain.EyeContact;
import com.eye.db.domain.EyeContactExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class EyeContactService {

    @Resource
    private EyeContactMapper contactMapper;

    public void add(EyeContact contact){
        contact.setAddTime(LocalDateTime.now());
        contact.setUpdateTime(LocalDateTime.now());
        contactMapper.insertSelective(contact);
    }

    public EyeContact findById(Integer id){
        EyeContactExample example = new EyeContactExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return contactMapper.selectOneByExample(example);
    }
}
