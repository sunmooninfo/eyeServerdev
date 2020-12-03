package com.eye.db.service;

import com.eye.db.dao.EyeGrouponCartMapper;
import com.eye.db.domain.EyeGrouponCart;
import com.eye.db.domain.EyeGrouponCartExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeGrouponCartService {

    @Autowired
    private EyeGrouponCartMapper grouponCartMapper;

    public EyeGrouponCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        EyeGrouponCartExample example = new EyeGrouponCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return grouponCartMapper.selectOneByExample(example);


    }

    public void add(EyeGrouponCart cart) {
        cart.setAddTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        grouponCartMapper.insertSelective(cart);
    }

    public int updateById(EyeGrouponCart existCart) {
        existCart.setUpdateTime(LocalDateTime.now());
        return grouponCartMapper.updateByPrimaryKeySelective(existCart);

    }

    public List<EyeGrouponCart> queryByUidAndChecked(Integer userId) {
        EyeGrouponCartExample example = new EyeGrouponCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return grouponCartMapper.selectByExample(example);
    }

    public EyeGrouponCart findById(Integer userId, Integer cartId) {
        EyeGrouponCartExample example = new EyeGrouponCartExample();
        example.or().andUserIdEqualTo(userId).andIdEqualTo(cartId).andDeletedEqualTo(false);
        return grouponCartMapper.selectOneByExample(example);
    }

    public EyeGrouponCart findById(Integer id) {
        return grouponCartMapper.selectByPrimaryKey(id);
    }

    public void clearGoods(Integer userId) {
        EyeGrouponCartExample example = new EyeGrouponCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        EyeGrouponCart cart = new EyeGrouponCart();
        cart.setDeleted(true);
        grouponCartMapper.updateByExampleSelective(cart,example);

    }


    public void deleteById(Integer cartId) {
        grouponCartMapper.logicalDeleteByPrimaryKey(cartId);
    }
}
