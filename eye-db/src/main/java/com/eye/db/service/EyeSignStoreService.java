package com.eye.db.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.eye.db.dao.EyeAddressMapper;
import com.eye.db.dao.EyeSignStoreMapper;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeSignStore;
import com.eye.db.domain.EyeSignStoreExample;
import com.github.pagehelper.PageHelper;
@Service
public class EyeSignStoreService {
	@Resource
	private EyeSignStoreMapper signstoreMapper;
	@Resource
	private EyeAddressMapper addressMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;


	/**
	 * 增加门店管理信息
	 * 增加门店管理信息的同时将门店的地址等主要信息同时保存到地址表（Eye_address）中
	 * @param signstore
	 */
	public void add(EyeSignStore signstore) {
		//获取门店编号最大值加1，确保门店编号不重复使用，如没有门店编号，直接将门店编号为1号
		String sql01="SELECT IFNULL(MAX(CAST(store_sn AS UNSIGNED)),0) + 1 FROM Eye_sign_store";
		int maxstoresn=jdbcTemplate.queryForObject(sql01,Integer.class);
		//保存门店信息
		signstore.setStoreSn(maxstoresn);
		signstore.setAddTime(LocalDateTime.now());
		signstore.setUpdateTime(LocalDateTime.now());
		signstoreMapper.insertSelective(signstore);
	}

	/**
	 * 删除门店管理信息
	 * @param id
	 */
	public void deleteById(Integer id) {

		signstoreMapper.logicalDeleteByPrimaryKey(id);
	}

	/**
	 * 修改门店管理信息
	 * @param signstore
	 * @return
	 */
	public int updateById(EyeSignStore signstore) {

		signstore.setUpdateTime(LocalDateTime.now());
		return signstoreMapper.updateByPrimaryKeySelective(signstore);

	}

	/**
	 * 根据门店号查询门店信息
	 * @param storeSn
	 * @return
	 */
	public  EyeSignStore queryByStoreSn(Integer storeSn) {
		EyeSignStoreExample example = new EyeSignStoreExample();
		example.or().andStoreSnEqualTo(storeSn).andDeletedEqualTo(false);
		return  this.signstoreMapper.selectOneByExample(example);
	}

	/**
	 * 查询门店管理信息
	 * @param managerMobile
	 * @param name
	 * @param page
	 * @param limit
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<EyeSignStore> querySelective(String managerMobile, String name, Integer page, Integer limit,
												  String sort, String order) {
		EyeSignStoreExample example = new EyeSignStoreExample();
		EyeSignStoreExample.Criteria criteria = example.createCriteria();

		//获取当前登录用户的用户信息
		EyeAdmin admin = (EyeAdmin)SecurityUtils.getSubject().getPrincipal();
		//获取用户手机号信息
		managerMobile=admin.getManagerMobile();
		if (managerMobile != null) {
			//根据获取到的手机号查询相应门店信息
			criteria.andManagerMobileEqualTo(managerMobile);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		//筛选门店信息删除状态，已删除状态，过滤掉（因为数据为逻辑删除，只修改数据库中数据的状态）
		criteria.andDeletedEqualTo(false);

		PageHelper.startPage(page, limit);

		return signstoreMapper.selectByExample(example);
	}

	public EyeSignStore selectById(Integer storeId){
		return this.signstoreMapper.selectByPrimaryKey(storeId);
	}

}
