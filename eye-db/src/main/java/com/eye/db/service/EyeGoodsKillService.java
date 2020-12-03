package com.eye.db.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;

import com.eye.db.dao.EyeGoodsKillMapper;
import com.eye.db.dao.GoodsKillMapper;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.domain.EyeGoodsKillExample;
import com.eye.db.util.KillConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;


@Service
public class EyeGoodsKillService {

	@Resource
	private EyeGoodsKillMapper eyeGoodsKillMapper;
	@Resource
	private GoodsKillMapper goodsKillMapper;

	public List<EyeGoodsKill> queryByGid(Integer goodsId) {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return eyeGoodsKillMapper.selectByExample(example);
	}


	public void add(EyeGoodsKill goodsKill) {
		goodsKill.setAddTime(LocalDateTime.now());
		goodsKill.setUpdateTime(LocalDateTime.now());
		eyeGoodsKillMapper.insertSelective(goodsKill);
	}

	public EyeGoodsKill findById(Integer id) {
		return eyeGoodsKillMapper.selectByPrimaryKey(id);
	}

	public void deleteByGid(Integer gid) {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andGoodsIdEqualTo(gid);
		eyeGoodsKillMapper.logicalDeleteByExample(example);
	}

	public void deleteById(Integer id) {
		eyeGoodsKillMapper.logicalDeleteByPrimaryKey(id);
	}

	public void updateById(EyeGoodsKill goodsKill) {
		goodsKill.setUpdateTime(LocalDateTime.now());
		eyeGoodsKillMapper.updateByPrimaryKeySelective(goodsKill);
	}


	public List<EyeGoodsKill> queryByStatus(Short killStatusNotAtThe) {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andKillStatusEqualTo(killStatusNotAtThe).andIsKillEqualTo(true).andDeletedEqualTo(false);
		return eyeGoodsKillMapper.selectByExample(example);
	}


	public List<EyeGoodsKill> queryExpired() {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andKillStatusEqualTo(KillConstant.KILL_STATUS_ONGOING).andEndDateLessThan(LocalDateTime.now()).andDeletedEqualTo(false);
		return eyeGoodsKillMapper.selectByExample(example);
	}


	public List<EyeGoodsKill> queryTo() {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		LocalDateTime now = LocalDateTime.now();
		example.or().andKillStatusEqualTo(KillConstant.KILL_STATUS_NOT_AT_THE).andStartDateLessThan(now).andEndDateGreaterThan(now).andDeletedEqualTo(false);
		return eyeGoodsKillMapper.selectByExample(example);
	}

	public EyeGoodsKill findByGoodsId(Integer goodsId) {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return eyeGoodsKillMapper.selectOneByExample(example);
	}

	public void thoroughDeleteByGid(Integer id) {
		this.eyeGoodsKillMapper.deleteByPrimaryKey(id);
	}

	public int reduceStock(Integer id, Short number) {
		return goodsKillMapper.reduceStock(id,number);
	}

	public int addStock(Integer id, Short number) {
		return goodsKillMapper.addStock(id,number);
	}

	public List<EyeGoodsKill> queryByKill(int offset, int limit) {
		EyeGoodsKillExample example = new EyeGoodsKillExample();
		example.or().andIsKillEqualTo(true).andKillStatusBetween(KillConstant.KILL_STATUS_NOT_AT_THE,KillConstant.KILL_STATUS_ONGOING).andDeletedEqualTo(false);
		PageHelper.startPage(offset,limit);
		return  this.eyeGoodsKillMapper.selectByExample(example);
	}
}
