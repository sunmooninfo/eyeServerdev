package com.eye.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EyeSignStoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EyeSignStoreExample() {
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

    public EyeSignStoreExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public EyeSignStoreExample orderBy(String ... orderByClauses) {
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
        EyeSignStoreExample example = new EyeSignStoreExample();
        return example.createCriteria();
    }

    public EyeSignStoreExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public EyeSignStoreExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
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

        public Criteria andIdEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andManagerIdIsNull() {
            addCriterion("manager_id is null");
            return (Criteria) this;
        }

        public Criteria andManagerIdIsNotNull() {
            addCriterion("manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andManagerIdEqualTo(Integer value) {
            addCriterion("manager_id =", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdNotEqualTo(Integer value) {
            addCriterion("manager_id <>", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThan(Integer value) {
            addCriterion("manager_id >", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("manager_id >=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThan(Integer value) {
            addCriterion("manager_id <", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanOrEqualTo(Integer value) {
            addCriterion("manager_id <=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerIdIn(List<Integer> values) {
            addCriterion("manager_id in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotIn(List<Integer> values) {
            addCriterion("manager_id not in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdBetween(Integer value1, Integer value2) {
            addCriterion("manager_id between", value1, value2, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("manager_id not between", value1, value2, "managerId");
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

        public Criteria andNameEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("`name` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("`name` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("`name` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("`name` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("`name` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andStoreSnIsNull() {
            addCriterion("store_sn is null");
            return (Criteria) this;
        }

        public Criteria andStoreSnIsNotNull() {
            addCriterion("store_sn is not null");
            return (Criteria) this;
        }

        public Criteria andStoreSnEqualTo(Integer value) {
            addCriterion("store_sn =", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnNotEqualTo(Integer value) {
            addCriterion("store_sn <>", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnGreaterThan(Integer value) {
            addCriterion("store_sn >", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_sn >=", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnLessThan(Integer value) {
            addCriterion("store_sn <", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnLessThanOrEqualTo(Integer value) {
            addCriterion("store_sn <=", value, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_sn <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreSnIn(List<Integer> values) {
            addCriterion("store_sn in", values, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnNotIn(List<Integer> values) {
            addCriterion("store_sn not in", values, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnBetween(Integer value1, Integer value2) {
            addCriterion("store_sn between", value1, value2, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreSnNotBetween(Integer value1, Integer value2) {
            addCriterion("store_sn not between", value1, value2, "storeSn");
            return (Criteria) this;
        }

        public Criteria andStoreMobileIsNull() {
            addCriterion("store_mobile is null");
            return (Criteria) this;
        }

        public Criteria andStoreMobileIsNotNull() {
            addCriterion("store_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andStoreMobileEqualTo(String value) {
            addCriterion("store_mobile =", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileNotEqualTo(String value) {
            addCriterion("store_mobile <>", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileGreaterThan(String value) {
            addCriterion("store_mobile >", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileGreaterThanOrEqualTo(String value) {
            addCriterion("store_mobile >=", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileLessThan(String value) {
            addCriterion("store_mobile <", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileLessThanOrEqualTo(String value) {
            addCriterion("store_mobile <=", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_mobile <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreMobileLike(String value) {
            addCriterion("store_mobile like", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileNotLike(String value) {
            addCriterion("store_mobile not like", value, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileIn(List<String> values) {
            addCriterion("store_mobile in", values, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileNotIn(List<String> values) {
            addCriterion("store_mobile not in", values, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileBetween(String value1, String value2) {
            addCriterion("store_mobile between", value1, value2, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andStoreMobileNotBetween(String value1, String value2) {
            addCriterion("store_mobile not between", value1, value2, "storeMobile");
            return (Criteria) this;
        }

        public Criteria andManagerIsNull() {
            addCriterion("manager is null");
            return (Criteria) this;
        }

        public Criteria andManagerIsNotNull() {
            addCriterion("manager is not null");
            return (Criteria) this;
        }

        public Criteria andManagerEqualTo(String value) {
            addCriterion("manager =", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerNotEqualTo(String value) {
            addCriterion("manager <>", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerGreaterThan(String value) {
            addCriterion("manager >", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerGreaterThanOrEqualTo(String value) {
            addCriterion("manager >=", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerLessThan(String value) {
            addCriterion("manager <", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerLessThanOrEqualTo(String value) {
            addCriterion("manager <=", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerLike(String value) {
            addCriterion("manager like", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerNotLike(String value) {
            addCriterion("manager not like", value, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerIn(List<String> values) {
            addCriterion("manager in", values, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerNotIn(List<String> values) {
            addCriterion("manager not in", values, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerBetween(String value1, String value2) {
            addCriterion("manager between", value1, value2, "manager");
            return (Criteria) this;
        }

        public Criteria andManagerNotBetween(String value1, String value2) {
            addCriterion("manager not between", value1, value2, "manager");
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

        public Criteria andManagerMobileEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_mobile = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualTo(String value) {
            addCriterion("manager_mobile <>", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_mobile <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThan(String value) {
            addCriterion("manager_mobile >", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_mobile > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualTo(String value) {
            addCriterion("manager_mobile >=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_mobile >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThan(String value) {
            addCriterion("manager_mobile <", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("manager_mobile < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualTo(String value) {
            addCriterion("manager_mobile <=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("province <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("city <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCountyIsNull() {
            addCriterion("county is null");
            return (Criteria) this;
        }

        public Criteria andCountyIsNotNull() {
            addCriterion("county is not null");
            return (Criteria) this;
        }

        public Criteria andCountyEqualTo(String value) {
            addCriterion("county =", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyNotEqualTo(String value) {
            addCriterion("county <>", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThan(String value) {
            addCriterion("county >", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThanOrEqualTo(String value) {
            addCriterion("county >=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyLessThan(String value) {
            addCriterion("county <", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyLessThanOrEqualTo(String value) {
            addCriterion("county <=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("county <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCountyLike(String value) {
            addCriterion("county like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotLike(String value) {
            addCriterion("county not like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyIn(List<String> values) {
            addCriterion("county in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotIn(List<String> values) {
            addCriterion("county not in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyBetween(String value1, String value2) {
            addCriterion("county between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotBetween(String value1, String value2) {
            addCriterion("county not between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andAddressDetailIsNull() {
            addCriterion("address_detail is null");
            return (Criteria) this;
        }

        public Criteria andAddressDetailIsNotNull() {
            addCriterion("address_detail is not null");
            return (Criteria) this;
        }

        public Criteria andAddressDetailEqualTo(String value) {
            addCriterion("address_detail =", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotEqualTo(String value) {
            addCriterion("address_detail <>", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThan(String value) {
            addCriterion("address_detail >", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThanOrEqualTo(String value) {
            addCriterion("address_detail >=", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThan(String value) {
            addCriterion("address_detail <", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThanOrEqualTo(String value) {
            addCriterion("address_detail <=", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("address_detail <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressDetailLike(String value) {
            addCriterion("address_detail like", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotLike(String value) {
            addCriterion("address_detail not like", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailIn(List<String> values) {
            addCriterion("address_detail in", values, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotIn(List<String> values) {
            addCriterion("address_detail not in", values, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailBetween(String value1, String value2) {
            addCriterion("address_detail between", value1, value2, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotBetween(String value1, String value2) {
            addCriterion("address_detail not between", value1, value2, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAppidIsNull() {
            addCriterion("appid is null");
            return (Criteria) this;
        }

        public Criteria andAppidIsNotNull() {
            addCriterion("appid is not null");
            return (Criteria) this;
        }

        public Criteria andAppidEqualTo(String value) {
            addCriterion("appid =", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidNotEqualTo(String value) {
            addCriterion("appid <>", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThan(String value) {
            addCriterion("appid >", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanOrEqualTo(String value) {
            addCriterion("appid >=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidLessThan(String value) {
            addCriterion("appid <", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidLessThanOrEqualTo(String value) {
            addCriterion("appid <=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("appid <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAppidLike(String value) {
            addCriterion("appid like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotLike(String value) {
            addCriterion("appid not like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidIn(List<String> values) {
            addCriterion("appid in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotIn(List<String> values) {
            addCriterion("appid not in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidBetween(String value1, String value2) {
            addCriterion("appid between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotBetween(String value1, String value2) {
            addCriterion("appid not between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andStoreImageIsNull() {
            addCriterion("store_image is null");
            return (Criteria) this;
        }

        public Criteria andStoreImageIsNotNull() {
            addCriterion("store_image is not null");
            return (Criteria) this;
        }

        public Criteria andStoreImageEqualTo(String value) {
            addCriterion("store_image =", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageNotEqualTo(String value) {
            addCriterion("store_image <>", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageGreaterThan(String value) {
            addCriterion("store_image >", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageGreaterThanOrEqualTo(String value) {
            addCriterion("store_image >=", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageLessThan(String value) {
            addCriterion("store_image <", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageLessThanOrEqualTo(String value) {
            addCriterion("store_image <=", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("store_image <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStoreImageLike(String value) {
            addCriterion("store_image like", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageNotLike(String value) {
            addCriterion("store_image not like", value, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageIn(List<String> values) {
            addCriterion("store_image in", values, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageNotIn(List<String> values) {
            addCriterion("store_image not in", values, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageBetween(String value1, String value2) {
            addCriterion("store_image between", value1, value2, "storeImage");
            return (Criteria) this;
        }

        public Criteria andStoreImageNotBetween(String value1, String value2) {
            addCriterion("store_image not between", value1, value2, "storeImage");
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

        public Criteria andAddTimeEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("add_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(LocalDateTime value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("add_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(LocalDateTime value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("add_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("add_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(LocalDateTime value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("add_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andUpdateTimeEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("update_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("update_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("update_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("update_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("update_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andDeletedEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualToColumn(EyeSignStore.Column column) {
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

        public Criteria andCommisionRateIsNull() {
            addCriterion("commision_rate is null");
            return (Criteria) this;
        }

        public Criteria andCommisionRateIsNotNull() {
            addCriterion("commision_rate is not null");
            return (Criteria) this;
        }

        public Criteria andCommisionRateEqualTo(BigDecimal value) {
            addCriterion("commision_rate =", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateNotEqualTo(BigDecimal value) {
            addCriterion("commision_rate <>", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateNotEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateGreaterThan(BigDecimal value) {
            addCriterion("commision_rate >", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateGreaterThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("commision_rate >=", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateGreaterThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateLessThan(BigDecimal value) {
            addCriterion("commision_rate <", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateLessThanColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("commision_rate <=", value, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateLessThanOrEqualToColumn(EyeSignStore.Column column) {
            addCriterion(new StringBuilder("commision_rate <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCommisionRateIn(List<BigDecimal> values) {
            addCriterion("commision_rate in", values, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateNotIn(List<BigDecimal> values) {
            addCriterion("commision_rate not in", values, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commision_rate between", value1, value2, "commisionRate");
            return (Criteria) this;
        }

        public Criteria andCommisionRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commision_rate not between", value1, value2, "commisionRate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        private EyeSignStoreExample example;

        protected Criteria(EyeSignStoreExample example) {
            super();
            this.example = example;
        }

        public EyeSignStoreExample example() {
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
            return deleted ? andDeletedEqualTo(EyeSignStore.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(EyeSignStore.Deleted.IS_DELETED.value());
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
        void example(com.eye.db.domain.EyeSignStoreExample example);
    }
}