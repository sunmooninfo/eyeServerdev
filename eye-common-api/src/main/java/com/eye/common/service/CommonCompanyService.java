package com.eye.common.service;

import com.eye.db.service.EyeCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author edc
 * @date 2020/7/21 14:27
 * @description
 */
@Service
public class CommonCompanyService {

    @Autowired
    private EyeCompanyService eyeCompanyService;
//    @Autowired
//    private EyeUserService eyeUserService;

    //删除
    public void deleteById(Integer id) {

    }

//    //查询公司信息（包含logo，sloga）
//    public Object querymessage(){
//        //EyeCompany company = null;
//
////        if (id == null ){
////            ResponseUtil.unlogin();
////        }
//
//        //展示公司信息（信息包含公司名字，logo，sloga与时间）
//        Map<String,Object> map = new HashMap<>();
//        //查询公司信息（包含logo，sloga）
//        EyeCompany companies = eyeCompanyService.querymessage();
//        map.put("companies",companies);
//        return ResponseUtil.ok();
//    }
}
