package com.eye.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EyeGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EyeGoodsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public EyeGoodsExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public EyeGoodsExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public static Criteria newAndCreateCriteria() {
        EyeGoodsExample example = new EyeGoodsExample();
        return example.createCriteria();
    }

    public EyeGoodsExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public EyeGoodsExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> galleryCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            galleryCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getGalleryCriteria() {
            return galleryCriteria;
        }

        protected void addGalleryCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            galleryCriteria.add(new Criterion(condition, value, "com.eye.db.JsonStringArrayTypeHandler"));
            allCriteria = null;
        }

        protected void addGalleryCriterion(String condition, String[] value1, String[] value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            galleryCriteria.add(new Criterion(condition, value1, value2, "com.eye.db.JsonStringArrayTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || galleryCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(galleryCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGoodsSnIsNull() {
            addCriterion("goods_sn is null");
            return (Criteria) this;
        }

        public Criteria andGoodsSnIsNotNull() {
            addCriterion("goods_sn is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsSnEqualTo(String value) {
            addCriterion("goods_sn =", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnNotEqualTo(String value) {
            addCriterion("goods_sn <>", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnGreaterThan(String value) {
            addCriterion("goods_sn >", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnGreaterThanOrEqualTo(String value) {
            addCriterion("goods_sn >=", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnLessThan(String value) {
            addCriterion("goods_sn <", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnLessThanOrEqualTo(String value) {
            addCriterion("goods_sn <=", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("goods_sn <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsSnLike(String value) {
            addCriterion("goods_sn like", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnNotLike(String value) {
            addCriterion("goods_sn not like", value, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnIn(List<String> values) {
            addCriterion("goods_sn in", values, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnNotIn(List<String> values) {
            addCriterion("goods_sn not in", values, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnBetween(String value1, String value2) {
            addCriterion("goods_sn between", value1, value2, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andGoodsSnNotBetween(String value1, String value2) {
            addCriterion("goods_sn not between", value1, value2, "goodsSn");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("`name` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(Integer value) {
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(Integer value) {
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(Integer value) {
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(Integer value) {
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("category_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<Integer> values) {
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<Integer> values) {
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNull() {
            addCriterion("brand_id is null");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNotNull() {
            addCriterion("brand_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrandIdEqualTo(Integer value) {
            addCriterion("brand_id =", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdNotEqualTo(Integer value) {
            addCriterion("brand_id <>", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThan(Integer value) {
            addCriterion("brand_id >", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand_id >=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThan(Integer value) {
            addCriterion("brand_id <", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThanOrEqualTo(Integer value) {
            addCriterion("brand_id <=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brand_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBrandIdIn(List<Integer> values) {
            addCriterion("brand_id in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotIn(List<Integer> values) {
            addCriterion("brand_id not in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdBetween(Integer value1, Integer value2) {
            addCriterion("brand_id between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotBetween(Integer value1, Integer value2) {
            addCriterion("brand_id not between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andGalleryIsNull() {
            addCriterion("gallery is null");
            return (Criteria) this;
        }

        public Criteria andGalleryIsNotNull() {
            addCriterion("gallery is not null");
            return (Criteria) this;
        }

        public Criteria andGalleryEqualTo(String[] value) {
            addGalleryCriterion("gallery =", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryNotEqualTo(String[] value) {
            addGalleryCriterion("gallery <>", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryGreaterThan(String[] value) {
            addGalleryCriterion("gallery >", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryGreaterThanOrEqualTo(String[] value) {
            addGalleryCriterion("gallery >=", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryLessThan(String[] value) {
            addGalleryCriterion("gallery <", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryLessThanOrEqualTo(String[] value) {
            addGalleryCriterion("gallery <=", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("gallery <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGalleryLike(String[] value) {
            addGalleryCriterion("gallery like", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryNotLike(String[] value) {
            addGalleryCriterion("gallery not like", value, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryIn(List<String[]> values) {
            addGalleryCriterion("gallery in", values, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryNotIn(List<String[]> values) {
            addGalleryCriterion("gallery not in", values, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryBetween(String[] value1, String[] value2) {
            addGalleryCriterion("gallery between", value1, value2, "gallery");
            return (Criteria) this;
        }

        public Criteria andGalleryNotBetween(String[] value1, String[] value2) {
            addGalleryCriterion("gallery not between", value1, value2, "gallery");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("keywords <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andBriefIsNull() {
            addCriterion("brief is null");
            return (Criteria) this;
        }

        public Criteria andBriefIsNotNull() {
            addCriterion("brief is not null");
            return (Criteria) this;
        }

        public Criteria andBriefEqualTo(String value) {
            addCriterion("brief =", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefNotEqualTo(String value) {
            addCriterion("brief <>", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThan(String value) {
            addCriterion("brief >", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThanOrEqualTo(String value) {
            addCriterion("brief >=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefLessThan(String value) {
            addCriterion("brief <", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefLessThanOrEqualTo(String value) {
            addCriterion("brief <=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("brief <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBriefLike(String value) {
            addCriterion("brief like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotLike(String value) {
            addCriterion("brief not like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefIn(List<String> values) {
            addCriterion("brief in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotIn(List<String> values) {
            addCriterion("brief not in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefBetween(String value1, String value2) {
            addCriterion("brief between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotBetween(String value1, String value2) {
            addCriterion("brief not between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleIsNull() {
            addCriterion("is_on_sale is null");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleIsNotNull() {
            addCriterion("is_on_sale is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleEqualTo(Boolean value) {
            addCriterion("is_on_sale =", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleNotEqualTo(Boolean value) {
            addCriterion("is_on_sale <>", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleGreaterThan(Boolean value) {
            addCriterion("is_on_sale >", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_on_sale >=", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleLessThan(Boolean value) {
            addCriterion("is_on_sale <", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_on_sale <=", value, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_on_sale <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsOnSaleIn(List<Boolean> values) {
            addCriterion("is_on_sale in", values, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleNotIn(List<Boolean> values) {
            addCriterion("is_on_sale not in", values, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on_sale between", value1, value2, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andIsOnSaleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on_sale not between", value1, value2, "isOnSale");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Short value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Short value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Short value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Short value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Short value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Short value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("sort_order <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Short> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Short> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Short value1, Short value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Short value1, Short value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andPicUrlIsNull() {
            addCriterion("pic_url is null");
            return (Criteria) this;
        }

        public Criteria andPicUrlIsNotNull() {
            addCriterion("pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andPicUrlEqualTo(String value) {
            addCriterion("pic_url =", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlNotEqualTo(String value) {
            addCriterion("pic_url <>", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThan(String value) {
            addCriterion("pic_url >", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pic_url >=", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThan(String value) {
            addCriterion("pic_url <", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThanOrEqualTo(String value) {
            addCriterion("pic_url <=", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("pic_url <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPicUrlLike(String value) {
            addCriterion("pic_url like", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotLike(String value) {
            addCriterion("pic_url not like", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlIn(List<String> values) {
            addCriterion("pic_url in", values, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotIn(List<String> values) {
            addCriterion("pic_url not in", values, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlBetween(String value1, String value2) {
            addCriterion("pic_url between", value1, value2, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotBetween(String value1, String value2) {
            addCriterion("pic_url not between", value1, value2, "picUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlIsNull() {
            addCriterion("share_url is null");
            return (Criteria) this;
        }

        public Criteria andShareUrlIsNotNull() {
            addCriterion("share_url is not null");
            return (Criteria) this;
        }

        public Criteria andShareUrlEqualTo(String value) {
            addCriterion("share_url =", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlNotEqualTo(String value) {
            addCriterion("share_url <>", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThan(String value) {
            addCriterion("share_url >", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThanOrEqualTo(String value) {
            addCriterion("share_url >=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThan(String value) {
            addCriterion("share_url <", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThanOrEqualTo(String value) {
            addCriterion("share_url <=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("share_url <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShareUrlLike(String value) {
            addCriterion("share_url like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotLike(String value) {
            addCriterion("share_url not like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlIn(List<String> values) {
            addCriterion("share_url in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotIn(List<String> values) {
            addCriterion("share_url not in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlBetween(String value1, String value2) {
            addCriterion("share_url between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotBetween(String value1, String value2) {
            addCriterion("share_url not between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNull() {
            addCriterion("is_new is null");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNotNull() {
            addCriterion("is_new is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualTo(Boolean value) {
            addCriterion("is_new =", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualTo(Boolean value) {
            addCriterion("is_new <>", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThan(Boolean value) {
            addCriterion("is_new >", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_new >=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewLessThan(Boolean value) {
            addCriterion("is_new <", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualTo(Boolean value) {
            addCriterion("is_new <=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_new <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsNewIn(List<Boolean> values) {
            addCriterion("is_new in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotIn(List<Boolean> values) {
            addCriterion("is_new not in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new not between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsHotIsNull() {
            addCriterion("is_hot is null");
            return (Criteria) this;
        }

        public Criteria andIsHotIsNotNull() {
            addCriterion("is_hot is not null");
            return (Criteria) this;
        }

        public Criteria andIsHotEqualTo(Boolean value) {
            addCriterion("is_hot =", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotNotEqualTo(Boolean value) {
            addCriterion("is_hot <>", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThan(Boolean value) {
            addCriterion("is_hot >", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_hot >=", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotLessThan(Boolean value) {
            addCriterion("is_hot <", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotLessThanOrEqualTo(Boolean value) {
            addCriterion("is_hot <=", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_hot <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsHotIn(List<Boolean> values) {
            addCriterion("is_hot in", values, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotIn(List<Boolean> values) {
            addCriterion("is_hot not in", values, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_hot between", value1, value2, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_hot not between", value1, value2, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsKillIsNull() {
            addCriterion("is_kill is null");
            return (Criteria) this;
        }

        public Criteria andIsKillIsNotNull() {
            addCriterion("is_kill is not null");
            return (Criteria) this;
        }

        public Criteria andIsKillEqualTo(Boolean value) {
            addCriterion("is_kill =", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillNotEqualTo(Boolean value) {
            addCriterion("is_kill <>", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThan(Boolean value) {
            addCriterion("is_kill >", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_kill >=", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillLessThan(Boolean value) {
            addCriterion("is_kill <", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanOrEqualTo(Boolean value) {
            addCriterion("is_kill <=", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_kill <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillIn(List<Boolean> values) {
            addCriterion("is_kill in", values, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillNotIn(List<Boolean> values) {
            addCriterion("is_kill not in", values, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillBetween(Boolean value1, Boolean value2) {
            addCriterion("is_kill between", value1, value2, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_kill not between", value1, value2, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsShownIsNull() {
            addCriterion("is_shown is null");
            return (Criteria) this;
        }

        public Criteria andIsShownIsNotNull() {
            addCriterion("is_shown is not null");
            return (Criteria) this;
        }

        public Criteria andIsShownEqualTo(Boolean value) {
            addCriterion("is_shown =", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownNotEqualTo(Boolean value) {
            addCriterion("is_shown <>", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownGreaterThan(Boolean value) {
            addCriterion("is_shown >", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_shown >=", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownLessThan(Boolean value) {
            addCriterion("is_shown <", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownLessThanOrEqualTo(Boolean value) {
            addCriterion("is_shown <=", value, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("is_shown <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsShownIn(List<Boolean> values) {
            addCriterion("is_shown in", values, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownNotIn(List<Boolean> values) {
            addCriterion("is_shown not in", values, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownBetween(Boolean value1, Boolean value2) {
            addCriterion("is_shown between", value1, value2, "isShown");
            return (Criteria) this;
        }

        public Criteria andIsShownNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_shown not between", value1, value2, "isShown");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("unit <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andCounterPriceIsNull() {
            addCriterion("counter_price is null");
            return (Criteria) this;
        }

        public Criteria andCounterPriceIsNotNull() {
            addCriterion("counter_price is not null");
            return (Criteria) this;
        }

        public Criteria andCounterPriceEqualTo(BigDecimal value) {
            addCriterion("counter_price =", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceNotEqualTo(BigDecimal value) {
            addCriterion("counter_price <>", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceGreaterThan(BigDecimal value) {
            addCriterion("counter_price >", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("counter_price >=", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceLessThan(BigDecimal value) {
            addCriterion("counter_price <", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("counter_price <=", value, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("counter_price <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCounterPriceIn(List<BigDecimal> values) {
            addCriterion("counter_price in", values, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceNotIn(List<BigDecimal> values) {
            addCriterion("counter_price not in", values, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("counter_price between", value1, value2, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andCounterPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("counter_price not between", value1, value2, "counterPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceIsNull() {
            addCriterion("retail_price is null");
            return (Criteria) this;
        }

        public Criteria andRetailPriceIsNotNull() {
            addCriterion("retail_price is not null");
            return (Criteria) this;
        }

        public Criteria andRetailPriceEqualTo(BigDecimal value) {
            addCriterion("retail_price =", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceNotEqualTo(BigDecimal value) {
            addCriterion("retail_price <>", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceGreaterThan(BigDecimal value) {
            addCriterion("retail_price >", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("retail_price >=", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceLessThan(BigDecimal value) {
            addCriterion("retail_price <", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("retail_price <=", value, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("retail_price <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRetailPriceIn(List<BigDecimal> values) {
            addCriterion("retail_price in", values, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceNotIn(List<BigDecimal> values) {
            addCriterion("retail_price not in", values, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retail_price between", value1, value2, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andRetailPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retail_price not between", value1, value2, "retailPrice");
            return (Criteria) this;
        }

        public Criteria andCommissionRateIsNull() {
            addCriterion("commission_rate is null");
            return (Criteria) this;
        }

        public Criteria andCommissionRateIsNotNull() {
            addCriterion("commission_rate is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionRateEqualTo(BigDecimal value) {
            addCriterion("commission_rate =", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateNotEqualTo(BigDecimal value) {
            addCriterion("commission_rate <>", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateGreaterThan(BigDecimal value) {
            addCriterion("commission_rate >", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("commission_rate >=", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateLessThan(BigDecimal value) {
            addCriterion("commission_rate <", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("commission_rate <=", value, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("commission_rate <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRateIn(List<BigDecimal> values) {
            addCriterion("commission_rate in", values, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateNotIn(List<BigDecimal> values) {
            addCriterion("commission_rate not in", values, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission_rate between", value1, value2, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andCommissionRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission_rate not between", value1, value2, "commissionRate");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyIsNull() {
            addCriterion("accessory_key is null");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyIsNotNull() {
            addCriterion("accessory_key is not null");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyEqualTo(String value) {
            addCriterion("accessory_key =", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyNotEqualTo(String value) {
            addCriterion("accessory_key <>", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyGreaterThan(String value) {
            addCriterion("accessory_key >", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyGreaterThanOrEqualTo(String value) {
            addCriterion("accessory_key >=", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyLessThan(String value) {
            addCriterion("accessory_key <", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyLessThanOrEqualTo(String value) {
            addCriterion("accessory_key <=", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_key <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyLike(String value) {
            addCriterion("accessory_key like", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyNotLike(String value) {
            addCriterion("accessory_key not like", value, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyIn(List<String> values) {
            addCriterion("accessory_key in", values, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyNotIn(List<String> values) {
            addCriterion("accessory_key not in", values, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyBetween(String value1, String value2) {
            addCriterion("accessory_key between", value1, value2, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryKeyNotBetween(String value1, String value2) {
            addCriterion("accessory_key not between", value1, value2, "accessoryKey");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkIsNull() {
            addCriterion("accessory_link is null");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkIsNotNull() {
            addCriterion("accessory_link is not null");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkEqualTo(String value) {
            addCriterion("accessory_link =", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkNotEqualTo(String value) {
            addCriterion("accessory_link <>", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkGreaterThan(String value) {
            addCriterion("accessory_link >", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkGreaterThanOrEqualTo(String value) {
            addCriterion("accessory_link >=", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkLessThan(String value) {
            addCriterion("accessory_link <", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkLessThanOrEqualTo(String value) {
            addCriterion("accessory_link <=", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_link <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkLike(String value) {
            addCriterion("accessory_link like", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkNotLike(String value) {
            addCriterion("accessory_link not like", value, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkIn(List<String> values) {
            addCriterion("accessory_link in", values, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkNotIn(List<String> values) {
            addCriterion("accessory_link not in", values, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkBetween(String value1, String value2) {
            addCriterion("accessory_link between", value1, value2, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryLinkNotBetween(String value1, String value2) {
            addCriterion("accessory_link not between", value1, value2, "accessoryLink");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeIsNull() {
            addCriterion("accessory_code is null");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeIsNotNull() {
            addCriterion("accessory_code is not null");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeEqualTo(String value) {
            addCriterion("accessory_code =", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeNotEqualTo(String value) {
            addCriterion("accessory_code <>", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeGreaterThan(String value) {
            addCriterion("accessory_code >", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("accessory_code >=", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeLessThan(String value) {
            addCriterion("accessory_code <", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeLessThanOrEqualTo(String value) {
            addCriterion("accessory_code <=", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("accessory_code <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeLike(String value) {
            addCriterion("accessory_code like", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeNotLike(String value) {
            addCriterion("accessory_code not like", value, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeIn(List<String> values) {
            addCriterion("accessory_code in", values, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeNotIn(List<String> values) {
            addCriterion("accessory_code not in", values, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeBetween(String value1, String value2) {
            addCriterion("accessory_code between", value1, value2, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAccessoryCodeNotBetween(String value1, String value2) {
            addCriterion("accessory_code not between", value1, value2, "accessoryCode");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(LocalDateTime value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(LocalDateTime value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(LocalDateTime value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(LocalDateTime value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("add_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<LocalDateTime> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<LocalDateTime> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("update_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualToColumn(EyeGoods.Column column) {
            addCriterion(new StringBuilder("deleted <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        private EyeGoodsExample example;

        protected Criteria(EyeGoodsExample example) {
            super();
            this.example = example;
        }

        public EyeGoodsExample example() {
            return this.example;
        }

        @Deprecated
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then, ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            } else {
                otherwise.criteria(this);
            }
            return this;
        }

        public Criteria andLogicalDeleted(boolean deleted) {
            return deleted ? andDeletedEqualTo(EyeGoods.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(EyeGoods.Deleted.IS_DELETED.value());
        }

        @Deprecated
        public interface ICriteriaAdd {
            Criteria add(Criteria add);
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    public interface ICriteriaWhen {
        void criteria(Criteria criteria);
    }

    public interface IExampleWhen {
        void example(com.eye.db.domain.EyeGoodsExample example);
    }
}