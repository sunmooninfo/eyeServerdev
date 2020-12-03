package com.eye.tool.service;


import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eye.db.dao.EyeToolAccountMapper;
import com.eye.db.dao.EyeToolAdminMapper;
import com.eye.db.domain.EyeToolAccount;
import com.eye.db.domain.EyeToolAccountExample;
import com.eye.db.domain.EyeToolAdmin;
import com.eye.mail.notify.MailNotifyService;
import com.github.pagehelper.PageHelper;

@Service
public class EyeToolService extends EyeToolAccount{

	@Resource
	private EyeToolAccountMapper accountMapper;	
	@Resource
	private EyeToolAdminMapper adminMapper;	
	@Autowired
	private MailNotifyService notifyService;

	//查询数据
	public List<EyeToolAccount> querySelective(Integer page, Integer limit, String sort, String order) {
		EyeToolAccountExample example = new EyeToolAccountExample();
		EyeToolAccountExample.Criteria criteria = example.createCriteria();
		PageHelper.startPage(page, limit);
		return accountMapper.selectByExample(example);
	}
	//添加数据
	public void add(EyeToolAccount toolAccount) {
		EyeToolAdmin toolAdmin=new EyeToolAdmin();
		toolAdmin.setAdminName(toolAccount.getAccountName());
		toolAdmin.setAdminMobile(toolAccount.getBindingMobile());
		toolAdmin.setAddTime(LocalDateTime.now());
		toolAdmin.setUodateTime(LocalDateTime.now());
		toolAccount.setAddTime(LocalDateTime.now());
		toolAccount.setUpdateTime(LocalDateTime.now());

		accountMapper.insertSelective(toolAccount);
		adminMapper.insertSelective(toolAdmin);

		//TODO 发送邮件和短信通知，这里采用异步发送
		// 有用户申请退款，邮件通知运营人员

		notifyService.notifyMail("用户信息", toolAccount.toString());	
	}
	//根据输入手机号查询数据
	public EyeToolAccount fingByMobile(String bindingMobile) {
		EyeToolAccountExample example = new EyeToolAccountExample();
		example.or().andBindingMobileEqualTo(bindingMobile).andDeletedEqualTo(false);
		return accountMapper.selectOneByExampleSelective(example);
	}
	//根据输入账号查询数据
	public EyeToolAccount fingByName(String accountName) {
		EyeToolAccountExample example = new EyeToolAccountExample();
		example.or().andAccountNameEqualTo(accountName).andDeletedEqualTo(false);
		return accountMapper.selectOneByExampleSelective(example);
	}
	//根据id更新数据
	public int updateById(EyeToolAccount toolAccount) {
		toolAccount.setUpdateTime(LocalDateTime.now());
		int tool = accountMapper.updateByPrimaryKeySelective(toolAccount);
		notifyService.notifyMail("用户信息",toolAccount.toString());
		return tool ;

	}
}
