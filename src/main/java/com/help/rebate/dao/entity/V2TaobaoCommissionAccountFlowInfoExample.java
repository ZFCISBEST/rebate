package com.help.rebate.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class V2TaobaoCommissionAccountFlowInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public V2TaobaoCommissionAccountFlowInfoExample() {
        oredCriteria = new ArrayList<>();
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

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
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

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedEqualTo(LocalDateTime value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(LocalDateTime value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(LocalDateTime value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(LocalDateTime value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<LocalDateTime> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<LocalDateTime> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(LocalDateTime value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(LocalDateTime value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(LocalDateTime value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(LocalDateTime value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<LocalDateTime> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<LocalDateTime> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNull() {
            addCriterion("open_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNotNull() {
            addCriterion("open_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenIdEqualTo(String value) {
            addCriterion("open_id =", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotEqualTo(String value) {
            addCriterion("open_id <>", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThan(String value) {
            addCriterion("open_id >", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_id >=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThan(String value) {
            addCriterion("open_id <", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThanOrEqualTo(String value) {
            addCriterion("open_id <=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLike(String value) {
            addCriterion("open_id like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotLike(String value) {
            addCriterion("open_id not like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdIn(List<String> values) {
            addCriterion("open_id in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotIn(List<String> values) {
            addCriterion("open_id not in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdBetween(String value1, String value2) {
            addCriterion("open_id between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotBetween(String value1, String value2) {
            addCriterion("open_id not between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIsNull() {
            addCriterion("total_commission is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIsNotNull() {
            addCriterion("total_commission is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionEqualTo(BigDecimal value) {
            addCriterion("total_commission =", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotEqualTo(BigDecimal value) {
            addCriterion("total_commission <>", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThan(BigDecimal value) {
            addCriterion("total_commission >", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_commission >=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThan(BigDecimal value) {
            addCriterion("total_commission <", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_commission <=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIn(List<BigDecimal> values) {
            addCriterion("total_commission in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotIn(List<BigDecimal> values) {
            addCriterion("total_commission not in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_commission between", value1, value2, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_commission not between", value1, value2, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionIsNull() {
            addCriterion("remain_commission is null");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionIsNotNull() {
            addCriterion("remain_commission is not null");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionEqualTo(BigDecimal value) {
            addCriterion("remain_commission =", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotEqualTo(BigDecimal value) {
            addCriterion("remain_commission <>", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionGreaterThan(BigDecimal value) {
            addCriterion("remain_commission >", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_commission >=", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionLessThan(BigDecimal value) {
            addCriterion("remain_commission <", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_commission <=", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionIn(List<BigDecimal> values) {
            addCriterion("remain_commission in", values, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotIn(List<BigDecimal> values) {
            addCriterion("remain_commission not in", values, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_commission between", value1, value2, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_commission not between", value1, value2, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionIsNull() {
            addCriterion("frozen_commission is null");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionIsNotNull() {
            addCriterion("frozen_commission is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionEqualTo(BigDecimal value) {
            addCriterion("frozen_commission =", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionNotEqualTo(BigDecimal value) {
            addCriterion("frozen_commission <>", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionGreaterThan(BigDecimal value) {
            addCriterion("frozen_commission >", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_commission >=", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionLessThan(BigDecimal value) {
            addCriterion("frozen_commission <", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_commission <=", value, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionIn(List<BigDecimal> values) {
            addCriterion("frozen_commission in", values, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionNotIn(List<BigDecimal> values) {
            addCriterion("frozen_commission not in", values, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_commission between", value1, value2, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFrozenCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_commission not between", value1, value2, "frozenCommission");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIsNull() {
            addCriterion("flow_amount is null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIsNotNull() {
            addCriterion("flow_amount is not null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountEqualTo(BigDecimal value) {
            addCriterion("flow_amount =", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotEqualTo(BigDecimal value) {
            addCriterion("flow_amount <>", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountGreaterThan(BigDecimal value) {
            addCriterion("flow_amount >", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("flow_amount >=", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountLessThan(BigDecimal value) {
            addCriterion("flow_amount <", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("flow_amount <=", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIn(List<BigDecimal> values) {
            addCriterion("flow_amount in", values, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotIn(List<BigDecimal> values) {
            addCriterion("flow_amount not in", values, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flow_amount between", value1, value2, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flow_amount not between", value1, value2, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeIsNull() {
            addCriterion("flow_amount_type is null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeIsNotNull() {
            addCriterion("flow_amount_type is not null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeEqualTo(Byte value) {
            addCriterion("flow_amount_type =", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeNotEqualTo(Byte value) {
            addCriterion("flow_amount_type <>", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeGreaterThan(Byte value) {
            addCriterion("flow_amount_type >", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("flow_amount_type >=", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeLessThan(Byte value) {
            addCriterion("flow_amount_type <", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeLessThanOrEqualTo(Byte value) {
            addCriterion("flow_amount_type <=", value, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeIn(List<Byte> values) {
            addCriterion("flow_amount_type in", values, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeNotIn(List<Byte> values) {
            addCriterion("flow_amount_type not in", values, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeBetween(Byte value1, Byte value2) {
            addCriterion("flow_amount_type between", value1, value2, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("flow_amount_type not between", value1, value2, "flowAmountType");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgIsNull() {
            addCriterion("flow_amount_type_msg is null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgIsNotNull() {
            addCriterion("flow_amount_type_msg is not null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgEqualTo(String value) {
            addCriterion("flow_amount_type_msg =", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgNotEqualTo(String value) {
            addCriterion("flow_amount_type_msg <>", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgGreaterThan(String value) {
            addCriterion("flow_amount_type_msg >", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgGreaterThanOrEqualTo(String value) {
            addCriterion("flow_amount_type_msg >=", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgLessThan(String value) {
            addCriterion("flow_amount_type_msg <", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgLessThanOrEqualTo(String value) {
            addCriterion("flow_amount_type_msg <=", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgLike(String value) {
            addCriterion("flow_amount_type_msg like", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgNotLike(String value) {
            addCriterion("flow_amount_type_msg not like", value, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgIn(List<String> values) {
            addCriterion("flow_amount_type_msg in", values, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgNotIn(List<String> values) {
            addCriterion("flow_amount_type_msg not in", values, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgBetween(String value1, String value2) {
            addCriterion("flow_amount_type_msg between", value1, value2, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andFlowAmountTypeMsgNotBetween(String value1, String value2) {
            addCriterion("flow_amount_type_msg not between", value1, value2, "flowAmountTypeMsg");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdIsNull() {
            addCriterion("commission_trade_id is null");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdIsNotNull() {
            addCriterion("commission_trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdEqualTo(String value) {
            addCriterion("commission_trade_id =", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdNotEqualTo(String value) {
            addCriterion("commission_trade_id <>", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdGreaterThan(String value) {
            addCriterion("commission_trade_id >", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdGreaterThanOrEqualTo(String value) {
            addCriterion("commission_trade_id >=", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdLessThan(String value) {
            addCriterion("commission_trade_id <", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdLessThanOrEqualTo(String value) {
            addCriterion("commission_trade_id <=", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdLike(String value) {
            addCriterion("commission_trade_id like", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdNotLike(String value) {
            addCriterion("commission_trade_id not like", value, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdIn(List<String> values) {
            addCriterion("commission_trade_id in", values, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdNotIn(List<String> values) {
            addCriterion("commission_trade_id not in", values, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdBetween(String value1, String value2) {
            addCriterion("commission_trade_id between", value1, value2, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andCommissionTradeIdNotBetween(String value1, String value2) {
            addCriterion("commission_trade_id not between", value1, value2, "commissionTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdIsNull() {
            addCriterion("refund_trade_id is null");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdIsNotNull() {
            addCriterion("refund_trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdEqualTo(String value) {
            addCriterion("refund_trade_id =", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdNotEqualTo(String value) {
            addCriterion("refund_trade_id <>", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdGreaterThan(String value) {
            addCriterion("refund_trade_id >", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdGreaterThanOrEqualTo(String value) {
            addCriterion("refund_trade_id >=", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdLessThan(String value) {
            addCriterion("refund_trade_id <", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdLessThanOrEqualTo(String value) {
            addCriterion("refund_trade_id <=", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdLike(String value) {
            addCriterion("refund_trade_id like", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdNotLike(String value) {
            addCriterion("refund_trade_id not like", value, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdIn(List<String> values) {
            addCriterion("refund_trade_id in", values, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdNotIn(List<String> values) {
            addCriterion("refund_trade_id not in", values, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdBetween(String value1, String value2) {
            addCriterion("refund_trade_id between", value1, value2, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andRefundTradeIdNotBetween(String value1, String value2) {
            addCriterion("refund_trade_id not between", value1, value2, "refundTradeId");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusIsNull() {
            addCriterion("account_flow_status is null");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusIsNotNull() {
            addCriterion("account_flow_status is not null");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusEqualTo(Byte value) {
            addCriterion("account_flow_status =", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusNotEqualTo(Byte value) {
            addCriterion("account_flow_status <>", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusGreaterThan(Byte value) {
            addCriterion("account_flow_status >", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("account_flow_status >=", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusLessThan(Byte value) {
            addCriterion("account_flow_status <", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusLessThanOrEqualTo(Byte value) {
            addCriterion("account_flow_status <=", value, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusIn(List<Byte> values) {
            addCriterion("account_flow_status in", values, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusNotIn(List<Byte> values) {
            addCriterion("account_flow_status not in", values, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusBetween(Byte value1, Byte value2) {
            addCriterion("account_flow_status between", value1, value2, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("account_flow_status not between", value1, value2, "accountFlowStatus");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgIsNull() {
            addCriterion("account_flow_status_msg is null");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgIsNotNull() {
            addCriterion("account_flow_status_msg is not null");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgEqualTo(String value) {
            addCriterion("account_flow_status_msg =", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgNotEqualTo(String value) {
            addCriterion("account_flow_status_msg <>", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgGreaterThan(String value) {
            addCriterion("account_flow_status_msg >", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgGreaterThanOrEqualTo(String value) {
            addCriterion("account_flow_status_msg >=", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgLessThan(String value) {
            addCriterion("account_flow_status_msg <", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgLessThanOrEqualTo(String value) {
            addCriterion("account_flow_status_msg <=", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgLike(String value) {
            addCriterion("account_flow_status_msg like", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgNotLike(String value) {
            addCriterion("account_flow_status_msg not like", value, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgIn(List<String> values) {
            addCriterion("account_flow_status_msg in", values, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgNotIn(List<String> values) {
            addCriterion("account_flow_status_msg not in", values, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgBetween(String value1, String value2) {
            addCriterion("account_flow_status_msg between", value1, value2, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andAccountFlowStatusMsgNotBetween(String value1, String value2) {
            addCriterion("account_flow_status_msg not between", value1, value2, "accountFlowStatusMsg");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
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
}