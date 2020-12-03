package com.eye.tool.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eye.db.dao.EyeToolAccountMapper;
import com.eye.db.dao.EyeToolAdminMapper;
import com.eye.db.domain.EyeToolAdmin;
import com.eye.db.domain.EyeToolAdminExample;
import com.github.pagehelper.PageHelper;


@Service
public class EyeToolAdminService {

	
	@Resource
	private EyeToolAccountMapper accountMapper;	
	
	@Resource
	private EyeToolAdminMapper adminMapper;	
	
	public List<EyeToolAdmin> querySelective(Integer page, Integer limit, String sort, String order) {
		EyeToolAdminExample example = new EyeToolAdminExample();
		EyeToolAdminExample.Criteria criteria = example.createCriteria();
		PageHelper.startPage(page, limit);
		return adminMapper.selectByExample(example);
	}



}
