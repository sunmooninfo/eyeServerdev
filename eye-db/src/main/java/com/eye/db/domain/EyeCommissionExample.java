package com.eye.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EyeCommissionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EyeCommissionExample() {
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

    public EyeCommissionExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public EyeCommissionExample orderBy(String ... orderByClauses) {
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
        EyeCommissionExample example = new EyeCommissionExample();
        return example.createCriteria();
    }

    public EyeCommissionExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public EyeCommissionExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
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

        public Criteria andIdEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(EyeCommission.Column column) {
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

        public Criteria andOrderSnIsNull() {
            addCriterion("order_sn is null");
            return (Criteria) this;
        }

        public Criteria andOrderSnIsNotNull() {
            addCriterion("order_sn is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSnEqualTo(String value) {
            addCriterion("order_sn =", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnNotEqualTo(String value) {
            addCriterion("order_sn <>", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThan(String value) {
            addCriterion("order_sn >", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThanOrEqualTo(String value) {
            addCriterion("order_sn >=", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThan(String value) {
            addCriterion("order_sn <", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThanOrEqualTo(String value) {
            addCriterion("order_sn <=", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_sn <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderSnLike(String value) {
            addCriterion("order_sn like", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotLike(String value) {
            addCriterion("order_sn not like", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnIn(List<String> values) {
            addCriterion("order_sn in", values, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotIn(List<String> values) {
            addCriterion("order_sn not in", values, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnBetween(String value1, String value2) {
            addCriterion("order_sn between", value1, value2, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotBetween(String value1, String value2) {
            addCriterion("order_sn not between", value1, value2, "orderSn");
            return (Criteria) this;
        }

        public Criteria andManagerMobileIsNull() {
            addCriterion("manager_mobile is null");
            return (Criteria) this;
        }

        public Criteria andManagerMobileIsNotNull() {
            addCriterion("manager_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andManagerMobileEqualTo(String value) {
            addCriterion("manager_mobile =", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualTo(String value) {
            addCriterion("manager_mobile <>", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThan(String value) {
            addCriterion("manager_mobile >", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualTo(String value) {
            addCriterion("manager_mobile >=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThan(String value) {
            addCriterion("manager_mobile <", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualTo(String value) {
            addCriterion("manager_mobile <=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("manager_mobile <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLike(String value) {
            addCriterion("manager_mobile like", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotLike(String value) {
            addCriterion("manager_mobile not like", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileIn(List<String> values) {
            addCriterion("manager_mobile in", values, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotIn(List<String> values) {
            addCriterion("manager_mobile not in", values, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileBetween(String value1, String value2) {
            addCriterion("manager_mobile between", value1, value2, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotBetween(String value1, String value2) {
            addCriterion("manager_mobile not between", value1, value2, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceIsNull() {
            addCriterion("order_total_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceIsNotNull() {
            addCriterion("order_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceEqualTo(BigDecimal value) {
            addCriterion("order_total_price =", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("order_total_price <>", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("order_total_price >", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_total_price >=", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceLessThan(BigDecimal value) {
            addCriterion("order_total_price <", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_total_price <=", value, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceLessThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_total_price <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceIn(List<BigDecimal> values) {
            addCriterion("order_total_price in", values, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("order_total_price not in", values, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_total_price between", value1, value2, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andOrderTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_total_price not between", value1, value2, "orderTotalPrice");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIsNull() {
            addCriterion("commission_ratio is null");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIsNotNull() {
            addCriterion("commission_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioEqualTo(BigDecimal value) {
            addCriterion("commission_ratio =", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotEqualTo(BigDecimal value) {
            addCriterion("commission_ratio <>", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThan(BigDecimal value) {
            addCriterion("commission_ratio >", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("commission_ratio >=", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThan(BigDecimal value) {
            addCriterion("commission_ratio <", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("commission_ratio <=", value, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioLessThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("commission_ratio <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommissionRatioIn(List<BigDecimal> values) {
            addCriterion("commission_ratio in", values, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotIn(List<BigDecimal> values) {
            addCriterion("commission_ratio not in", values, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission_ratio between", value1, value2, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andCommissionRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission_ratio not between", value1, value2, "commissionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionIsNull() {
            addCriterion("order_commision is null");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionIsNotNull() {
            addCriterion("order_commision is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionEqualTo(BigDecimal value) {
            addCriterion("order_commision =", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionNotEqualTo(BigDecimal value) {
            addCriterion("order_commision <>", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionGreaterThan(BigDecimal value) {
            addCriterion("order_commision >", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_commision >=", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionLessThan(BigDecimal value) {
            addCriterion("order_commision <", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_commision <=", value, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionLessThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("order_commision <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderCommisionIn(List<BigDecimal> values) {
            addCriterion("order_commision in", values, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionNotIn(List<BigDecimal> values) {
            addCriterion("order_commision not in", values, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_commision between", value1, value2, "orderCommision");
            return (Criteria) this;
        }

        public Criteria andOrderCommisionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_commision not between", value1, value2, "orderCommision");
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

        public Criteria andAddTimeEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("add_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(LocalDateTime value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("add_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(LocalDateTime value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("add_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("add_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(LocalDateTime value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("add_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualToColumn(EyeCommission.Column column) {
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

        public Criteria andUpdateTimeEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("update_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("update_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("update_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("update_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("update_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToColumn(EyeCommission.Column column) {
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

        public Criteria andDeletedEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualToColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanColumn(EyeCommission.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualToColumn(EyeCommission.Column column) {
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
        private EyeCommissionExample example;

        protected Criteria(EyeCommissionExample example) {
            super();
            this.example = example;
        }

        public EyeCommissionExample example() {
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
            return deleted ? andDeletedEqualTo(EyeCommission.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(EyeCommission.Deleted.IS_DELETED.value());
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
        void example(com.eye.db.domain.EyeCommissionExample example);
    }
}