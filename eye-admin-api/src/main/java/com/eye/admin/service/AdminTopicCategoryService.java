package com.eye.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eye.admin.vo.CatVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeTopicCategory;
import com.eye.db.service.EyeTopicCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminTopicCategoryService {

    @Autowired
    private EyeTopicCategoryService eyeTopicCategoryService;

    public Object category() {
        List<CatVo> l1TopicCategories = new ArrayList<>();
        List<EyeTopicCategory> topicCategories1 = eyeTopicCategoryService.queryL1();
        for(EyeTopicCategory l1 :topicCategories1){
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<CatVo> l2TopicCategories = new ArrayList<>();
            List<EyeTopicCategory> topicCategories2 = eyeTopicCategoryService.queryByPid(l1.getId());
            for(EyeTopicCategory l2 : topicCategories2){
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                l2TopicCategories.add(l2CatVo);
            }
            l1CatVo.setChildren(l2TopicCategories);
            l1TopicCategories.add(l1CatVo);
        }

        return ResponseUtil.okList(l1TopicCategories);
    }
}
