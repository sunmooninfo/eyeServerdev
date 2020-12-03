package com.eye.db.service;

import com.eye.db.dao.EyeGoodsMapper;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoods.Column;
import com.eye.db.domain.EyeGoodsExample;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.util.KillConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EyeGoodsService {
    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isShown, Column.isNew, Column.isKill, Column.counterPrice, Column.retailPrice};
    @Resource
    private EyeGoodsMapper goodsMapper;
    
    @Resource
    private EyeGoodsKillService goodsKillService;


    /**
     * 判断商品是否在小程序展示
     */
    public List<EyeGoods> queryByisShown(int offset,int limit,Boolean isShown){
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andIsShownEqualTo(false).andDeletedEqualTo(false);
        example.setOrderByClause("is_shown asc");
        PageHelper.startPage(offset,limit);
        return goodsMapper.selectByExampleSelective(example,columns);
    }

    /**
     * 获取热卖商品
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<EyeGoods> queryByHot(int offset, int limit,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order asc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取新品上市
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<EyeGoods> queryByNew(int offset, int limit,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andIsNewEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }
    /**
     * 获取秒杀商品
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<EyeGoods> queryByKill(int offset, int limit,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andIsKillEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<EyeGoods> EyeGoodsList = goodsMapper.selectByExample(example);

        List<EyeGoods> goodsList = new ArrayList<>();
        for (EyeGoods goods : EyeGoodsList) {
            EyeGoodsKill goodsKill = goodsKillService.findByGoodsId(goods.getId());
            goods.setRetailPrice(goodsKill.getKillPrice());
            goods.setStartDate(goodsKill.getStartDate());
            goods.setEndDate(goodsKill.getEndDate());
            goodsList.add(goods);
        }

        return goodsList;
    }

    public List<EyeGoods> common(List<EyeGoods> EyeGoodsList,Boolean isKill){
        List<EyeGoods> goodsList = new ArrayList<>();
        List<EyeGoods> goodsKillList = new ArrayList<>();
        for (EyeGoods EyeGoods : EyeGoodsList) {
            EyeGoodsKill goodsKill = goodsKillService.findByGoodsId(EyeGoods.getId());
            if (goodsKill != null && goodsKill.getKillStatus() == KillConstant.KILL_STATUS_ONGOING ||goodsKill.getKillStatus() == KillConstant.KILL_STATUS_NOT_AT_THE) {
                EyeGoods.setStartDate(goodsKill.getStartDate());
                EyeGoods.setEndDate(goodsKill.getEndDate());
                EyeGoods.setRetailPrice(goodsKill.getKillPrice());
              goodsKillList.add(EyeGoods);
            }
          goodsList.add(EyeGoods);
        }
        if(isKill == null){
          isKill = false;
        }
        return isKill?goodsKillList:goodsList;
    }
    
    /**
     * 获取分类下的商品
     *
     * @param catList
     * @param offset
     * @param limit
     * @return
     */
    public List<EyeGoods> queryByCategory(List<Integer> catList, int offset, int limit,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andCategoryIdIn(catList).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time  desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }
    
   

    /**
     * 获取分类下的商品
     *
     * @param catId
     * @param offset
     * @param limit
     * @return
     */
    public List<EyeGoods> queryByCategory(Integer catId, int offset, int limit,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }
        criteria.andCategoryIdEqualTo(catId).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }


    public List<EyeGoods> querySelective(Integer catId, Integer brandId, String keywords, Boolean isHot, Boolean isShown, Boolean isNew, Boolean isKill,Integer offset, Integer limit, String sort, String order) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria1 = example.or();
        EyeGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(isShown)){
            criteria1.andIsShownEqualTo(isShown);
            criteria2.andIsShownEqualTo(isShown);
        }
        if (!StringUtils.isEmpty(isKill)) {
            criteria1.andIsKillEqualTo(isKill);
            criteria2.andIsKillEqualTo(isKill);
        }
        
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause( "sort_order ASC,"+sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    public List<EyeGoods> querySelective(Integer goodsId, String goodsSn, String name, Integer categoryId, Integer page, Integer size, String sort, String order) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria = example.createCriteria();

        if (goodsId != null) {
            criteria.andIdEqualTo(goodsId);
        }
        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (categoryId != null) {
            criteria.andCategoryIdEqualTo(categoryId);
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause("sort_order ASC,"+sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 获取某个商品信息,包含完整信息
     *
     * @param id
     * @return
     */
    public EyeGoods findById(Integer id) {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }

    /**
     * 获取某个商品信息，仅展示相关内容
     *
     * @param id
     * @return
     */
    public EyeGoods findByIdVO(Integer id) {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andIdEqualTo(id).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleSelective(example, columns);
    }


    /**
     * 获取所有在售物品总数
     *
     * @return
     */
    public Integer queryOnSale() {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) goodsMapper.countByExample(example);
    }

    public int updateById(EyeGoods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        goodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeGoods goods) {
        goods.setAddTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.insertSelective(goods);
    }

    /**
     * 获取所有物品总数，包括在售的和下架的，但是不包括已删除的商品
     *
     * @return
     */
    public int count() {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int) goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew,Boolean isKill,Boolean isShown) {
        EyeGoodsExample example = new EyeGoodsExample();
        EyeGoodsExample.Criteria criteria1 = example.or();
        EyeGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(isKill)) {
            criteria1.andIsKillEqualTo(isKill);
            criteria2.andIsKillEqualTo(isKill);
        }
        if (!StringUtils.isEmpty(isShown)) {
            criteria1.andIsShownEqualTo(isShown);
            criteria2.andIsShownEqualTo(isShown);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<EyeGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (EyeGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andNameEqualTo(name).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example) != 0;
    }

    public List<EyeGoods> queryByIds(Integer[] ids) {
        EyeGoodsExample example = new EyeGoodsExample();
        example.or().andIdIn(Arrays.asList(ids)).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.selectByExampleSelective(example, columns);
    }

    public EyeGoods queryByiId(Integer Id) {
        return goodsMapper.selectByPrimaryKey(Id);
    }
}
