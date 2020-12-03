package com.eye.db.service;

import org.springframework.stereotype.Service;
import com.eye.db.dao.EyeGoodsProductMapper;
import com.eye.db.dao.GoodsProductMapper;
import com.eye.db.domain.EyeGoodsProduct;
import com.eye.db.domain.EyeGoodsProductExample;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeGoodsProductService {
    @Resource
    private EyeGoodsProductMapper EyeGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<EyeGoodsProduct> queryByGid(Integer gid) {
        EyeGoodsProductExample example = new EyeGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return EyeGoodsProductMapper.selectByExample(example);
    }

    public EyeGoodsProduct findById(Integer id) {
        return EyeGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        EyeGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        EyeGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        EyeGoodsProductExample example = new EyeGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) EyeGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        EyeGoodsProductExample example = new EyeGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        EyeGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public void updateById(EyeGoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        EyeGoodsProductMapper.updateByPrimaryKeySelective(product);
    }
}