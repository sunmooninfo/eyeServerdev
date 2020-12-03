package com.eye.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EyeGoodsKillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EyeGoodsKillExample() {
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

    public EyeGoodsKillExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public EyeGoodsKillExample orderBy(String ... orderByClauses) {
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
        EyeGoodsKillExample example = new EyeGoodsKillExample();
        return example.createCriteria();
    }

    public EyeGoodsKillExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public EyeGoodsKillExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> killDateCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            killDateCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getKillDateCriteria() {
            return killDateCriteria;
        }

        protected void addKillDateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            killDateCriteria.add(new Criterion(condition, value, "com.eye.db.mybatis.JsonStringArrayTypeHandler"));
            allCriteria = null;
        }

        protected void addKillDateCriterion(String condition, String[] value1, String[] value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            killDateCriteria.add(new Criterion(condition, value1, value2, "com.eye.db.mybatis.JsonStringArrayTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || killDateCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(killDateCriteria);
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

        public Criteria andIdEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
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

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("goods_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andKillPriceIsNull() {
            addCriterion("kill_price is null");
            return (Criteria) this;
        }

        public Criteria andKillPriceIsNotNull() {
            addCriterion("kill_price is not null");
            return (Criteria) this;
        }

        public Criteria andKillPriceEqualTo(BigDecimal value) {
            addCriterion("kill_price =", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceNotEqualTo(BigDecimal value) {
            addCriterion("kill_price <>", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceGreaterThan(BigDecimal value) {
            addCriterion("kill_price >", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kill_price >=", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceLessThan(BigDecimal value) {
            addCriterion("kill_price <", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kill_price <=", value, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_price <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillPriceIn(List<BigDecimal> values) {
            addCriterion("kill_price in", values, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceNotIn(List<BigDecimal> values) {
            addCriterion("kill_price not in", values, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kill_price between", value1, value2, "killPrice");
            return (Criteria) this;
        }

        public Criteria andKillPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kill_price not between", value1, value2, "killPrice");
            return (Criteria) this;
        }

        public Criteria andStockCountIsNull() {
            addCriterion("stock_count is null");
            return (Criteria) this;
        }

        public Criteria andStockCountIsNotNull() {
            addCriterion("stock_count is not null");
            return (Criteria) this;
        }

        public Criteria andStockCountEqualTo(Integer value) {
            addCriterion("stock_count =", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountNotEqualTo(Integer value) {
            addCriterion("stock_count <>", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountGreaterThan(Integer value) {
            addCriterion("stock_count >", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_count >=", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountLessThan(Integer value) {
            addCriterion("stock_count <", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountLessThanOrEqualTo(Integer value) {
            addCriterion("stock_count <=", value, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("stock_count <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStockCountIn(List<Integer> values) {
            addCriterion("stock_count in", values, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountNotIn(List<Integer> values) {
            addCriterion("stock_count not in", values, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountBetween(Integer value1, Integer value2) {
            addCriterion("stock_count between", value1, value2, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStockCountNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_count not between", value1, value2, "stockCount");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(LocalDateTime value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(LocalDateTime value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(LocalDateTime value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(LocalDateTime value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("start_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<LocalDateTime> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<LocalDateTime> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(LocalDateTime value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(LocalDateTime value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(LocalDateTime value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(LocalDateTime value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("end_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<LocalDateTime> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<LocalDateTime> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andKillDateIsNull() {
            addCriterion("kill_date is null");
            return (Criteria) this;
        }

        public Criteria andKillDateIsNotNull() {
            addCriterion("kill_date is not null");
            return (Criteria) this;
        }

        public Criteria andKillDateEqualTo(String[] value) {
            addKillDateCriterion("kill_date =", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateNotEqualTo(String[] value) {
            addKillDateCriterion("kill_date <>", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateGreaterThan(String[] value) {
            addKillDateCriterion("kill_date >", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateGreaterThanOrEqualTo(String[] value) {
            addKillDateCriterion("kill_date >=", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateLessThan(String[] value) {
            addKillDateCriterion("kill_date <", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateLessThanOrEqualTo(String[] value) {
            addKillDateCriterion("kill_date <=", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillDateLike(String[] value) {
            addKillDateCriterion("kill_date like", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateNotLike(String[] value) {
            addKillDateCriterion("kill_date not like", value, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateIn(List<String[]> values) {
            addKillDateCriterion("kill_date in", values, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateNotIn(List<String[]> values) {
            addKillDateCriterion("kill_date not in", values, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateBetween(String[] value1, String[] value2) {
            addKillDateCriterion("kill_date between", value1, value2, "killDate");
            return (Criteria) this;
        }

        public Criteria andKillDateNotBetween(String[] value1, String[] value2) {
            addKillDateCriterion("kill_date not between", value1, value2, "killDate");
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

        public Criteria andIsKillEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("is_kill = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillNotEqualTo(Boolean value) {
            addCriterion("is_kill <>", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("is_kill <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThan(Boolean value) {
            addCriterion("is_kill >", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("is_kill > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_kill >=", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("is_kill >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillLessThan(Boolean value) {
            addCriterion("is_kill <", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("is_kill < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanOrEqualTo(Boolean value) {
            addCriterion("is_kill <=", value, "isKill");
            return (Criteria) this;
        }

        public Criteria andIsKillLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
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

        public Criteria andKillStatusIsNull() {
            addCriterion("kill_status is null");
            return (Criteria) this;
        }

        public Criteria andKillStatusIsNotNull() {
            addCriterion("kill_status is not null");
            return (Criteria) this;
        }

        public Criteria andKillStatusEqualTo(Short value) {
            addCriterion("kill_status =", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusNotEqualTo(Short value) {
            addCriterion("kill_status <>", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusGreaterThan(Short value) {
            addCriterion("kill_status >", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("kill_status >=", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusLessThan(Short value) {
            addCriterion("kill_status <", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusLessThanOrEqualTo(Short value) {
            addCriterion("kill_status <=", value, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("kill_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKillStatusIn(List<Short> values) {
            addCriterion("kill_status in", values, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusNotIn(List<Short> values) {
            addCriterion("kill_status not in", values, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusBetween(Short value1, Short value2) {
            addCriterion("kill_status between", value1, value2, "killStatus");
            return (Criteria) this;
        }

        public Criteria andKillStatusNotBetween(Short value1, Short value2) {
            addCriterion("kill_status not between", value1, value2, "killStatus");
            return (Criteria) this;
        }

        public Criteria andNowTimeIsNull() {
            addCriterion("now_time is null");
            return (Criteria) this;
        }

        public Criteria andNowTimeIsNotNull() {
            addCriterion("now_time is not null");
            return (Criteria) this;
        }

        public Criteria andNowTimeEqualTo(LocalDateTime value) {
            addCriterion("now_time =", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeNotEqualTo(LocalDateTime value) {
            addCriterion("now_time <>", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeGreaterThan(LocalDateTime value) {
            addCriterion("now_time >", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("now_time >=", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeLessThan(LocalDateTime value) {
            addCriterion("now_time <", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("now_time <=", value, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("now_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNowTimeIn(List<LocalDateTime> values) {
            addCriterion("now_time in", values, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeNotIn(List<LocalDateTime> values) {
            addCriterion("now_time not in", values, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("now_time between", value1, value2, "nowTime");
            return (Criteria) this;
        }

        public Criteria andNowTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("now_time not between", value1, value2, "nowTime");
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

        public Criteria andAddTimeEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("add_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(LocalDateTime value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("add_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(LocalDateTime value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("add_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("add_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(LocalDateTime value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("add_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
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

        public Criteria andUpdateTimeEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("update_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("update_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("update_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("update_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("update_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
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

        public Criteria andDeletedEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualToColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanColumn(EyeGoodsKill.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualToColumn(EyeGoodsKill.Column column) {
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
        private EyeGoodsKillExample example;

        protected Criteria(EyeGoodsKillExample example) {
            super();
            this.example = example;
        }

        public EyeGoodsKillExample example() {
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
            return deleted ? andDeletedEqualTo(EyeGoodsKill.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(EyeGoodsKill.Deleted.IS_DELETED.value());
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
        void example(com.eye.db.domain.EyeGoodsKillExample example);
    }
}