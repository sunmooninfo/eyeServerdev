package com.eye.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EyeAdminExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EyeAdminExample() {
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

    public EyeAdminExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public EyeAdminExample orderBy(String ... orderByClauses) {
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
        EyeAdminExample example = new EyeAdminExample();
        return example.createCriteria();
    }

    public EyeAdminExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public EyeAdminExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> roleIdsCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            roleIdsCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getRoleIdsCriteria() {
            return roleIdsCriteria;
        }

        protected void addRoleIdsCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            roleIdsCriteria.add(new Criterion(condition, value, "com.eye.db.JsonIntegerArrayTypeHandler"));
            allCriteria = null;
        }

        protected void addRoleIdsCriterion(String condition, Integer[] value1, Integer[] value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            roleIdsCriteria.add(new Criterion(condition, value1, value2, "com.eye.db.JsonIntegerArrayTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || roleIdsCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(roleIdsCriteria);
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

        public Criteria andIdEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(EyeAdmin.Column column) {
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("username <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("`password` is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("`password` is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("`password` =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("`password` <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("`password` >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("`password` >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("`password` <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("`password` <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("`password` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("`password` like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("`password` not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("`password` in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("`password` not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("`password` between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("`password` not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNull() {
            addCriterion("last_login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNotNull() {
            addCriterion("last_login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpEqualTo(String value) {
            addCriterion("last_login_ip =", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotEqualTo(String value) {
            addCriterion("last_login_ip <>", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThan(String value) {
            addCriterion("last_login_ip >", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_ip >=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThan(String value) {
            addCriterion("last_login_ip <", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanOrEqualTo(String value) {
            addCriterion("last_login_ip <=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_ip <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLike(String value) {
            addCriterion("last_login_ip like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotLike(String value) {
            addCriterion("last_login_ip not like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIn(List<String> values) {
            addCriterion("last_login_ip in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotIn(List<String> values) {
            addCriterion("last_login_ip not in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpBetween(String value1, String value2) {
            addCriterion("last_login_ip between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotBetween(String value1, String value2) {
            addCriterion("last_login_ip not between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(LocalDateTime value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(LocalDateTime value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(LocalDateTime value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(LocalDateTime value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("last_login_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<LocalDateTime> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<LocalDateTime> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
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

        public Criteria andManagerMobileEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("manager_mobile = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualTo(String value) {
            addCriterion("manager_mobile <>", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("manager_mobile <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThan(String value) {
            addCriterion("manager_mobile >", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("manager_mobile > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualTo(String value) {
            addCriterion("manager_mobile >=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("manager_mobile >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThan(String value) {
            addCriterion("manager_mobile <", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("manager_mobile < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualTo(String value) {
            addCriterion("manager_mobile <=", value, "managerMobile");
            return (Criteria) this;
        }

        public Criteria andManagerMobileLessThanOrEqualToColumn(EyeAdmin.Column column) {
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

        public Criteria andAvatarIsNull() {
            addCriterion("avatar is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("avatar is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("avatar =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("avatar <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("avatar >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("avatar >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("avatar <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("avatar <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("avatar <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("avatar like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("avatar not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("avatar in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("avatar not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("avatar between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("avatar not between", value1, value2, "avatar");
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

        public Criteria andAddTimeEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("add_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(LocalDateTime value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("add_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(LocalDateTime value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("add_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("add_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(LocalDateTime value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("add_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualToColumn(EyeAdmin.Column column) {
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

        public Criteria andUpdateTimeEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("update_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("update_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("update_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("update_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("update_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToColumn(EyeAdmin.Column column) {
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

        public Criteria andDeletedEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualToColumn(EyeAdmin.Column column) {
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

        public Criteria andRoleIdsIsNull() {
            addCriterion("role_ids is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdsIsNotNull() {
            addCriterion("role_ids is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdsEqualTo(Integer[] value) {
            addRoleIdsCriterion("role_ids =", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsNotEqualTo(Integer[] value) {
            addRoleIdsCriterion("role_ids <>", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsNotEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsGreaterThan(Integer[] value) {
            addRoleIdsCriterion("role_ids >", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsGreaterThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsGreaterThanOrEqualTo(Integer[] value) {
            addRoleIdsCriterion("role_ids >=", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsGreaterThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsLessThan(Integer[] value) {
            addRoleIdsCriterion("role_ids <", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsLessThanColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsLessThanOrEqualTo(Integer[] value) {
            addRoleIdsCriterion("role_ids <=", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsLessThanOrEqualToColumn(EyeAdmin.Column column) {
            addCriterion(new StringBuilder("role_ids <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRoleIdsLike(Integer[] value) {
            addRoleIdsCriterion("role_ids like", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsNotLike(Integer[] value) {
            addRoleIdsCriterion("role_ids not like", value, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsIn(List<Integer[]> values) {
            addRoleIdsCriterion("role_ids in", values, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsNotIn(List<Integer[]> values) {
            addRoleIdsCriterion("role_ids not in", values, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsBetween(Integer[] value1, Integer[] value2) {
            addRoleIdsCriterion("role_ids between", value1, value2, "roleIds");
            return (Criteria) this;
        }

        public Criteria andRoleIdsNotBetween(Integer[] value1, Integer[] value2) {
            addRoleIdsCriterion("role_ids not between", value1, value2, "roleIds");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        private EyeAdminExample example;

        protected Criteria(EyeAdminExample example) {
            super();
            this.example = example;
        }

        public EyeAdminExample example() {
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
            return deleted ? andDeletedEqualTo(EyeAdmin.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(EyeAdmin.Deleted.IS_DELETED.value());
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
        void example(com.eye.db.domain.EyeAdminExample example);
    }
}