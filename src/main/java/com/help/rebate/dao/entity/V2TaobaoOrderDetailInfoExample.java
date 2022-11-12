package com.help.rebate.dao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class V2TaobaoOrderDetailInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public V2TaobaoOrderDetailInfoExample() {
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

        public Criteria andTradeIdIsNull() {
            addCriterion("trade_id is null");
            return (Criteria) this;
        }

        public Criteria andTradeIdIsNotNull() {
            addCriterion("trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andTradeIdEqualTo(String value) {
            addCriterion("trade_id =", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotEqualTo(String value) {
            addCriterion("trade_id <>", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThan(String value) {
            addCriterion("trade_id >", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThanOrEqualTo(String value) {
            addCriterion("trade_id >=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThan(String value) {
            addCriterion("trade_id <", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThanOrEqualTo(String value) {
            addCriterion("trade_id <=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLike(String value) {
            addCriterion("trade_id like", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotLike(String value) {
            addCriterion("trade_id not like", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdIn(List<String> values) {
            addCriterion("trade_id in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotIn(List<String> values) {
            addCriterion("trade_id not in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdBetween(String value1, String value2) {
            addCriterion("trade_id between", value1, value2, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotBetween(String value1, String value2) {
            addCriterion("trade_id not between", value1, value2, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdIsNull() {
            addCriterion("trade_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdIsNotNull() {
            addCriterion("trade_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdEqualTo(String value) {
            addCriterion("trade_parent_id =", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdNotEqualTo(String value) {
            addCriterion("trade_parent_id <>", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdGreaterThan(String value) {
            addCriterion("trade_parent_id >", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("trade_parent_id >=", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdLessThan(String value) {
            addCriterion("trade_parent_id <", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdLessThanOrEqualTo(String value) {
            addCriterion("trade_parent_id <=", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdLike(String value) {
            addCriterion("trade_parent_id like", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdNotLike(String value) {
            addCriterion("trade_parent_id not like", value, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdIn(List<String> values) {
            addCriterion("trade_parent_id in", values, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdNotIn(List<String> values) {
            addCriterion("trade_parent_id not in", values, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdBetween(String value1, String value2) {
            addCriterion("trade_parent_id between", value1, value2, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andTradeParentIdNotBetween(String value1, String value2) {
            addCriterion("trade_parent_id not between", value1, value2, "tradeParentId");
            return (Criteria) this;
        }

        public Criteria andClickTimeIsNull() {
            addCriterion("click_time is null");
            return (Criteria) this;
        }

        public Criteria andClickTimeIsNotNull() {
            addCriterion("click_time is not null");
            return (Criteria) this;
        }

        public Criteria andClickTimeEqualTo(LocalDateTime value) {
            addCriterion("click_time =", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeNotEqualTo(LocalDateTime value) {
            addCriterion("click_time <>", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeGreaterThan(LocalDateTime value) {
            addCriterion("click_time >", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("click_time >=", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeLessThan(LocalDateTime value) {
            addCriterion("click_time <", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("click_time <=", value, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeIn(List<LocalDateTime> values) {
            addCriterion("click_time in", values, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeNotIn(List<LocalDateTime> values) {
            addCriterion("click_time not in", values, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("click_time between", value1, value2, "clickTime");
            return (Criteria) this;
        }

        public Criteria andClickTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("click_time not between", value1, value2, "clickTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeIsNull() {
            addCriterion("tk_create_time is null");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeIsNotNull() {
            addCriterion("tk_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("tk_create_time =", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tk_create_time <>", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("tk_create_time >", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_create_time >=", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeLessThan(LocalDateTime value) {
            addCriterion("tk_create_time <", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_create_time <=", value, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("tk_create_time in", values, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tk_create_time not in", values, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_create_time between", value1, value2, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTkCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_create_time not between", value1, value2, "tkCreateTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeIsNull() {
            addCriterion("tb_paid_time is null");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeIsNotNull() {
            addCriterion("tb_paid_time is not null");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeEqualTo(LocalDateTime value) {
            addCriterion("tb_paid_time =", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tb_paid_time <>", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeGreaterThan(LocalDateTime value) {
            addCriterion("tb_paid_time >", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tb_paid_time >=", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeLessThan(LocalDateTime value) {
            addCriterion("tb_paid_time <", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tb_paid_time <=", value, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeIn(List<LocalDateTime> values) {
            addCriterion("tb_paid_time in", values, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tb_paid_time not in", values, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tb_paid_time between", value1, value2, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTbPaidTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tb_paid_time not between", value1, value2, "tbPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeIsNull() {
            addCriterion("tk_paid_time is null");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeIsNotNull() {
            addCriterion("tk_paid_time is not null");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeEqualTo(LocalDateTime value) {
            addCriterion("tk_paid_time =", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tk_paid_time <>", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeGreaterThan(LocalDateTime value) {
            addCriterion("tk_paid_time >", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_paid_time >=", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeLessThan(LocalDateTime value) {
            addCriterion("tk_paid_time <", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_paid_time <=", value, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeIn(List<LocalDateTime> values) {
            addCriterion("tk_paid_time in", values, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tk_paid_time not in", values, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_paid_time between", value1, value2, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andTkPaidTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_paid_time not between", value1, value2, "tkPaidTime");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceIsNull() {
            addCriterion("alipay_total_price is null");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceIsNotNull() {
            addCriterion("alipay_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceEqualTo(String value) {
            addCriterion("alipay_total_price =", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceNotEqualTo(String value) {
            addCriterion("alipay_total_price <>", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceGreaterThan(String value) {
            addCriterion("alipay_total_price >", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceGreaterThanOrEqualTo(String value) {
            addCriterion("alipay_total_price >=", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceLessThan(String value) {
            addCriterion("alipay_total_price <", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceLessThanOrEqualTo(String value) {
            addCriterion("alipay_total_price <=", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceLike(String value) {
            addCriterion("alipay_total_price like", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceNotLike(String value) {
            addCriterion("alipay_total_price not like", value, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceIn(List<String> values) {
            addCriterion("alipay_total_price in", values, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceNotIn(List<String> values) {
            addCriterion("alipay_total_price not in", values, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceBetween(String value1, String value2) {
            addCriterion("alipay_total_price between", value1, value2, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andAlipayTotalPriceNotBetween(String value1, String value2) {
            addCriterion("alipay_total_price not between", value1, value2, "alipayTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNull() {
            addCriterion("pay_price is null");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNotNull() {
            addCriterion("pay_price is not null");
            return (Criteria) this;
        }

        public Criteria andPayPriceEqualTo(String value) {
            addCriterion("pay_price =", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotEqualTo(String value) {
            addCriterion("pay_price <>", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThan(String value) {
            addCriterion("pay_price >", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThanOrEqualTo(String value) {
            addCriterion("pay_price >=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThan(String value) {
            addCriterion("pay_price <", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThanOrEqualTo(String value) {
            addCriterion("pay_price <=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLike(String value) {
            addCriterion("pay_price like", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotLike(String value) {
            addCriterion("pay_price not like", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIn(List<String> values) {
            addCriterion("pay_price in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotIn(List<String> values) {
            addCriterion("pay_price not in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceBetween(String value1, String value2) {
            addCriterion("pay_price between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotBetween(String value1, String value2) {
            addCriterion("pay_price not between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNull() {
            addCriterion("modified_time is null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNotNull() {
            addCriterion("modified_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeEqualTo(String value) {
            addCriterion("modified_time =", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotEqualTo(String value) {
            addCriterion("modified_time <>", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThan(String value) {
            addCriterion("modified_time >", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThanOrEqualTo(String value) {
            addCriterion("modified_time >=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThan(String value) {
            addCriterion("modified_time <", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThanOrEqualTo(String value) {
            addCriterion("modified_time <=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLike(String value) {
            addCriterion("modified_time like", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotLike(String value) {
            addCriterion("modified_time not like", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIn(List<String> values) {
            addCriterion("modified_time in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotIn(List<String> values) {
            addCriterion("modified_time not in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeBetween(String value1, String value2) {
            addCriterion("modified_time between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotBetween(String value1, String value2) {
            addCriterion("modified_time not between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andTkStatusIsNull() {
            addCriterion("tk_status is null");
            return (Criteria) this;
        }

        public Criteria andTkStatusIsNotNull() {
            addCriterion("tk_status is not null");
            return (Criteria) this;
        }

        public Criteria andTkStatusEqualTo(Integer value) {
            addCriterion("tk_status =", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusNotEqualTo(Integer value) {
            addCriterion("tk_status <>", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusGreaterThan(Integer value) {
            addCriterion("tk_status >", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("tk_status >=", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusLessThan(Integer value) {
            addCriterion("tk_status <", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusLessThanOrEqualTo(Integer value) {
            addCriterion("tk_status <=", value, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusIn(List<Integer> values) {
            addCriterion("tk_status in", values, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusNotIn(List<Integer> values) {
            addCriterion("tk_status not in", values, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusBetween(Integer value1, Integer value2) {
            addCriterion("tk_status between", value1, value2, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andTkStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("tk_status not between", value1, value2, "tkStatus");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andRefundTagIsNull() {
            addCriterion("refund_tag is null");
            return (Criteria) this;
        }

        public Criteria andRefundTagIsNotNull() {
            addCriterion("refund_tag is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTagEqualTo(Integer value) {
            addCriterion("refund_tag =", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagNotEqualTo(Integer value) {
            addCriterion("refund_tag <>", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagGreaterThan(Integer value) {
            addCriterion("refund_tag >", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_tag >=", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagLessThan(Integer value) {
            addCriterion("refund_tag <", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagLessThanOrEqualTo(Integer value) {
            addCriterion("refund_tag <=", value, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagIn(List<Integer> values) {
            addCriterion("refund_tag in", values, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagNotIn(List<Integer> values) {
            addCriterion("refund_tag not in", values, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagBetween(Integer value1, Integer value2) {
            addCriterion("refund_tag between", value1, value2, "refundTag");
            return (Criteria) this;
        }

        public Criteria andRefundTagNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_tag not between", value1, value2, "refundTag");
            return (Criteria) this;
        }

        public Criteria andFlowSourceIsNull() {
            addCriterion("flow_source is null");
            return (Criteria) this;
        }

        public Criteria andFlowSourceIsNotNull() {
            addCriterion("flow_source is not null");
            return (Criteria) this;
        }

        public Criteria andFlowSourceEqualTo(String value) {
            addCriterion("flow_source =", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceNotEqualTo(String value) {
            addCriterion("flow_source <>", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceGreaterThan(String value) {
            addCriterion("flow_source >", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceGreaterThanOrEqualTo(String value) {
            addCriterion("flow_source >=", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceLessThan(String value) {
            addCriterion("flow_source <", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceLessThanOrEqualTo(String value) {
            addCriterion("flow_source <=", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceLike(String value) {
            addCriterion("flow_source like", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceNotLike(String value) {
            addCriterion("flow_source not like", value, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceIn(List<String> values) {
            addCriterion("flow_source in", values, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceNotIn(List<String> values) {
            addCriterion("flow_source not in", values, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceBetween(String value1, String value2) {
            addCriterion("flow_source between", value1, value2, "flowSource");
            return (Criteria) this;
        }

        public Criteria andFlowSourceNotBetween(String value1, String value2) {
            addCriterion("flow_source not between", value1, value2, "flowSource");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeIsNull() {
            addCriterion("terminal_type is null");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeIsNotNull() {
            addCriterion("terminal_type is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeEqualTo(String value) {
            addCriterion("terminal_type =", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotEqualTo(String value) {
            addCriterion("terminal_type <>", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeGreaterThan(String value) {
            addCriterion("terminal_type >", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeGreaterThanOrEqualTo(String value) {
            addCriterion("terminal_type >=", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeLessThan(String value) {
            addCriterion("terminal_type <", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeLessThanOrEqualTo(String value) {
            addCriterion("terminal_type <=", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeLike(String value) {
            addCriterion("terminal_type like", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotLike(String value) {
            addCriterion("terminal_type not like", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeIn(List<String> values) {
            addCriterion("terminal_type in", values, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotIn(List<String> values) {
            addCriterion("terminal_type not in", values, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeBetween(String value1, String value2) {
            addCriterion("terminal_type between", value1, value2, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotBetween(String value1, String value2) {
            addCriterion("terminal_type not between", value1, value2, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeIsNull() {
            addCriterion("tk_earning_time is null");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeIsNotNull() {
            addCriterion("tk_earning_time is not null");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeEqualTo(LocalDateTime value) {
            addCriterion("tk_earning_time =", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tk_earning_time <>", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeGreaterThan(LocalDateTime value) {
            addCriterion("tk_earning_time >", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_earning_time >=", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeLessThan(LocalDateTime value) {
            addCriterion("tk_earning_time <", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_earning_time <=", value, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeIn(List<LocalDateTime> values) {
            addCriterion("tk_earning_time in", values, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tk_earning_time not in", values, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_earning_time between", value1, value2, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkEarningTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_earning_time not between", value1, value2, "tkEarningTime");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleIsNull() {
            addCriterion("tk_order_role is null");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleIsNotNull() {
            addCriterion("tk_order_role is not null");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleEqualTo(Integer value) {
            addCriterion("tk_order_role =", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleNotEqualTo(Integer value) {
            addCriterion("tk_order_role <>", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleGreaterThan(Integer value) {
            addCriterion("tk_order_role >", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleGreaterThanOrEqualTo(Integer value) {
            addCriterion("tk_order_role >=", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleLessThan(Integer value) {
            addCriterion("tk_order_role <", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleLessThanOrEqualTo(Integer value) {
            addCriterion("tk_order_role <=", value, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleIn(List<Integer> values) {
            addCriterion("tk_order_role in", values, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleNotIn(List<Integer> values) {
            addCriterion("tk_order_role not in", values, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleBetween(Integer value1, Integer value2) {
            addCriterion("tk_order_role between", value1, value2, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTkOrderRoleNotBetween(Integer value1, Integer value2) {
            addCriterion("tk_order_role not between", value1, value2, "tkOrderRole");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateIsNull() {
            addCriterion("total_commission_rate is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateIsNotNull() {
            addCriterion("total_commission_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateEqualTo(String value) {
            addCriterion("total_commission_rate =", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateNotEqualTo(String value) {
            addCriterion("total_commission_rate <>", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateGreaterThan(String value) {
            addCriterion("total_commission_rate >", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateGreaterThanOrEqualTo(String value) {
            addCriterion("total_commission_rate >=", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateLessThan(String value) {
            addCriterion("total_commission_rate <", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateLessThanOrEqualTo(String value) {
            addCriterion("total_commission_rate <=", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateLike(String value) {
            addCriterion("total_commission_rate like", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateNotLike(String value) {
            addCriterion("total_commission_rate not like", value, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateIn(List<String> values) {
            addCriterion("total_commission_rate in", values, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateNotIn(List<String> values) {
            addCriterion("total_commission_rate not in", values, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateBetween(String value1, String value2) {
            addCriterion("total_commission_rate between", value1, value2, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionRateNotBetween(String value1, String value2) {
            addCriterion("total_commission_rate not between", value1, value2, "totalCommissionRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateIsNull() {
            addCriterion("income_rate is null");
            return (Criteria) this;
        }

        public Criteria andIncomeRateIsNotNull() {
            addCriterion("income_rate is not null");
            return (Criteria) this;
        }

        public Criteria andIncomeRateEqualTo(String value) {
            addCriterion("income_rate =", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateNotEqualTo(String value) {
            addCriterion("income_rate <>", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateGreaterThan(String value) {
            addCriterion("income_rate >", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateGreaterThanOrEqualTo(String value) {
            addCriterion("income_rate >=", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateLessThan(String value) {
            addCriterion("income_rate <", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateLessThanOrEqualTo(String value) {
            addCriterion("income_rate <=", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateLike(String value) {
            addCriterion("income_rate like", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateNotLike(String value) {
            addCriterion("income_rate not like", value, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateIn(List<String> values) {
            addCriterion("income_rate in", values, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateNotIn(List<String> values) {
            addCriterion("income_rate not in", values, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateBetween(String value1, String value2) {
            addCriterion("income_rate between", value1, value2, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andIncomeRateNotBetween(String value1, String value2) {
            addCriterion("income_rate not between", value1, value2, "incomeRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateIsNull() {
            addCriterion("pub_share_rate is null");
            return (Criteria) this;
        }

        public Criteria andPubShareRateIsNotNull() {
            addCriterion("pub_share_rate is not null");
            return (Criteria) this;
        }

        public Criteria andPubShareRateEqualTo(String value) {
            addCriterion("pub_share_rate =", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateNotEqualTo(String value) {
            addCriterion("pub_share_rate <>", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateGreaterThan(String value) {
            addCriterion("pub_share_rate >", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateGreaterThanOrEqualTo(String value) {
            addCriterion("pub_share_rate >=", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateLessThan(String value) {
            addCriterion("pub_share_rate <", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateLessThanOrEqualTo(String value) {
            addCriterion("pub_share_rate <=", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateLike(String value) {
            addCriterion("pub_share_rate like", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateNotLike(String value) {
            addCriterion("pub_share_rate not like", value, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateIn(List<String> values) {
            addCriterion("pub_share_rate in", values, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateNotIn(List<String> values) {
            addCriterion("pub_share_rate not in", values, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateBetween(String value1, String value2) {
            addCriterion("pub_share_rate between", value1, value2, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andPubShareRateNotBetween(String value1, String value2) {
            addCriterion("pub_share_rate not between", value1, value2, "pubShareRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateIsNull() {
            addCriterion("tk_total_rate is null");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateIsNotNull() {
            addCriterion("tk_total_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateEqualTo(String value) {
            addCriterion("tk_total_rate =", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateNotEqualTo(String value) {
            addCriterion("tk_total_rate <>", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateGreaterThan(String value) {
            addCriterion("tk_total_rate >", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateGreaterThanOrEqualTo(String value) {
            addCriterion("tk_total_rate >=", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateLessThan(String value) {
            addCriterion("tk_total_rate <", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateLessThanOrEqualTo(String value) {
            addCriterion("tk_total_rate <=", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateLike(String value) {
            addCriterion("tk_total_rate like", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateNotLike(String value) {
            addCriterion("tk_total_rate not like", value, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateIn(List<String> values) {
            addCriterion("tk_total_rate in", values, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateNotIn(List<String> values) {
            addCriterion("tk_total_rate not in", values, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateBetween(String value1, String value2) {
            addCriterion("tk_total_rate between", value1, value2, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTkTotalRateNotBetween(String value1, String value2) {
            addCriterion("tk_total_rate not between", value1, value2, "tkTotalRate");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeIsNull() {
            addCriterion("total_commission_fee is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeIsNotNull() {
            addCriterion("total_commission_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeEqualTo(String value) {
            addCriterion("total_commission_fee =", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeNotEqualTo(String value) {
            addCriterion("total_commission_fee <>", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeGreaterThan(String value) {
            addCriterion("total_commission_fee >", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeGreaterThanOrEqualTo(String value) {
            addCriterion("total_commission_fee >=", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeLessThan(String value) {
            addCriterion("total_commission_fee <", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeLessThanOrEqualTo(String value) {
            addCriterion("total_commission_fee <=", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeLike(String value) {
            addCriterion("total_commission_fee like", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeNotLike(String value) {
            addCriterion("total_commission_fee not like", value, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeIn(List<String> values) {
            addCriterion("total_commission_fee in", values, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeNotIn(List<String> values) {
            addCriterion("total_commission_fee not in", values, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeBetween(String value1, String value2) {
            addCriterion("total_commission_fee between", value1, value2, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionFeeNotBetween(String value1, String value2) {
            addCriterion("total_commission_fee not between", value1, value2, "totalCommissionFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeIsNull() {
            addCriterion("pub_share_pre_fee is null");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeIsNotNull() {
            addCriterion("pub_share_pre_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeEqualTo(String value) {
            addCriterion("pub_share_pre_fee =", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeNotEqualTo(String value) {
            addCriterion("pub_share_pre_fee <>", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeGreaterThan(String value) {
            addCriterion("pub_share_pre_fee >", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeGreaterThanOrEqualTo(String value) {
            addCriterion("pub_share_pre_fee >=", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeLessThan(String value) {
            addCriterion("pub_share_pre_fee <", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeLessThanOrEqualTo(String value) {
            addCriterion("pub_share_pre_fee <=", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeLike(String value) {
            addCriterion("pub_share_pre_fee like", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeNotLike(String value) {
            addCriterion("pub_share_pre_fee not like", value, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeIn(List<String> values) {
            addCriterion("pub_share_pre_fee in", values, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeNotIn(List<String> values) {
            addCriterion("pub_share_pre_fee not in", values, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeBetween(String value1, String value2) {
            addCriterion("pub_share_pre_fee between", value1, value2, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubSharePreFeeNotBetween(String value1, String value2) {
            addCriterion("pub_share_pre_fee not between", value1, value2, "pubSharePreFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeIsNull() {
            addCriterion("pub_share_fee is null");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeIsNotNull() {
            addCriterion("pub_share_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeEqualTo(String value) {
            addCriterion("pub_share_fee =", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeNotEqualTo(String value) {
            addCriterion("pub_share_fee <>", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeGreaterThan(String value) {
            addCriterion("pub_share_fee >", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeGreaterThanOrEqualTo(String value) {
            addCriterion("pub_share_fee >=", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeLessThan(String value) {
            addCriterion("pub_share_fee <", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeLessThanOrEqualTo(String value) {
            addCriterion("pub_share_fee <=", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeLike(String value) {
            addCriterion("pub_share_fee like", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeNotLike(String value) {
            addCriterion("pub_share_fee not like", value, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeIn(List<String> values) {
            addCriterion("pub_share_fee in", values, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeNotIn(List<String> values) {
            addCriterion("pub_share_fee not in", values, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeBetween(String value1, String value2) {
            addCriterion("pub_share_fee between", value1, value2, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andPubShareFeeNotBetween(String value1, String value2) {
            addCriterion("pub_share_fee not between", value1, value2, "pubShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateIsNull() {
            addCriterion("alimama_rate is null");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateIsNotNull() {
            addCriterion("alimama_rate is not null");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateEqualTo(String value) {
            addCriterion("alimama_rate =", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateNotEqualTo(String value) {
            addCriterion("alimama_rate <>", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateGreaterThan(String value) {
            addCriterion("alimama_rate >", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateGreaterThanOrEqualTo(String value) {
            addCriterion("alimama_rate >=", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateLessThan(String value) {
            addCriterion("alimama_rate <", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateLessThanOrEqualTo(String value) {
            addCriterion("alimama_rate <=", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateLike(String value) {
            addCriterion("alimama_rate like", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateNotLike(String value) {
            addCriterion("alimama_rate not like", value, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateIn(List<String> values) {
            addCriterion("alimama_rate in", values, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateNotIn(List<String> values) {
            addCriterion("alimama_rate not in", values, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateBetween(String value1, String value2) {
            addCriterion("alimama_rate between", value1, value2, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaRateNotBetween(String value1, String value2) {
            addCriterion("alimama_rate not between", value1, value2, "alimamaRate");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeIsNull() {
            addCriterion("alimama_share_fee is null");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeIsNotNull() {
            addCriterion("alimama_share_fee is not null");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeEqualTo(String value) {
            addCriterion("alimama_share_fee =", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeNotEqualTo(String value) {
            addCriterion("alimama_share_fee <>", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeGreaterThan(String value) {
            addCriterion("alimama_share_fee >", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeGreaterThanOrEqualTo(String value) {
            addCriterion("alimama_share_fee >=", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeLessThan(String value) {
            addCriterion("alimama_share_fee <", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeLessThanOrEqualTo(String value) {
            addCriterion("alimama_share_fee <=", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeLike(String value) {
            addCriterion("alimama_share_fee like", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeNotLike(String value) {
            addCriterion("alimama_share_fee not like", value, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeIn(List<String> values) {
            addCriterion("alimama_share_fee in", values, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeNotIn(List<String> values) {
            addCriterion("alimama_share_fee not in", values, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeBetween(String value1, String value2) {
            addCriterion("alimama_share_fee between", value1, value2, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andAlimamaShareFeeNotBetween(String value1, String value2) {
            addCriterion("alimama_share_fee not between", value1, value2, "alimamaShareFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateIsNull() {
            addCriterion("subsidy_rate is null");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateIsNotNull() {
            addCriterion("subsidy_rate is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateEqualTo(String value) {
            addCriterion("subsidy_rate =", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateNotEqualTo(String value) {
            addCriterion("subsidy_rate <>", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateGreaterThan(String value) {
            addCriterion("subsidy_rate >", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateGreaterThanOrEqualTo(String value) {
            addCriterion("subsidy_rate >=", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateLessThan(String value) {
            addCriterion("subsidy_rate <", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateLessThanOrEqualTo(String value) {
            addCriterion("subsidy_rate <=", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateLike(String value) {
            addCriterion("subsidy_rate like", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateNotLike(String value) {
            addCriterion("subsidy_rate not like", value, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateIn(List<String> values) {
            addCriterion("subsidy_rate in", values, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateNotIn(List<String> values) {
            addCriterion("subsidy_rate not in", values, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateBetween(String value1, String value2) {
            addCriterion("subsidy_rate between", value1, value2, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyRateNotBetween(String value1, String value2) {
            addCriterion("subsidy_rate not between", value1, value2, "subsidyRate");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeIsNull() {
            addCriterion("subsidy_fee is null");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeIsNotNull() {
            addCriterion("subsidy_fee is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeEqualTo(String value) {
            addCriterion("subsidy_fee =", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeNotEqualTo(String value) {
            addCriterion("subsidy_fee <>", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeGreaterThan(String value) {
            addCriterion("subsidy_fee >", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeGreaterThanOrEqualTo(String value) {
            addCriterion("subsidy_fee >=", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeLessThan(String value) {
            addCriterion("subsidy_fee <", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeLessThanOrEqualTo(String value) {
            addCriterion("subsidy_fee <=", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeLike(String value) {
            addCriterion("subsidy_fee like", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeNotLike(String value) {
            addCriterion("subsidy_fee not like", value, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeIn(List<String> values) {
            addCriterion("subsidy_fee in", values, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeNotIn(List<String> values) {
            addCriterion("subsidy_fee not in", values, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeBetween(String value1, String value2) {
            addCriterion("subsidy_fee between", value1, value2, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyFeeNotBetween(String value1, String value2) {
            addCriterion("subsidy_fee not between", value1, value2, "subsidyFee");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeIsNull() {
            addCriterion("subsidy_type is null");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeIsNotNull() {
            addCriterion("subsidy_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeEqualTo(String value) {
            addCriterion("subsidy_type =", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeNotEqualTo(String value) {
            addCriterion("subsidy_type <>", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeGreaterThan(String value) {
            addCriterion("subsidy_type >", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("subsidy_type >=", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeLessThan(String value) {
            addCriterion("subsidy_type <", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeLessThanOrEqualTo(String value) {
            addCriterion("subsidy_type <=", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeLike(String value) {
            addCriterion("subsidy_type like", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeNotLike(String value) {
            addCriterion("subsidy_type not like", value, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeIn(List<String> values) {
            addCriterion("subsidy_type in", values, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeNotIn(List<String> values) {
            addCriterion("subsidy_type not in", values, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeBetween(String value1, String value2) {
            addCriterion("subsidy_type between", value1, value2, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andSubsidyTypeNotBetween(String value1, String value2) {
            addCriterion("subsidy_type not between", value1, value2, "subsidyType");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformIsNull() {
            addCriterion("tk_commission_pre_fee_for_media_platform is null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformIsNotNull() {
            addCriterion("tk_commission_pre_fee_for_media_platform is not null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformEqualTo(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform =", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformNotEqualTo(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform <>", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformGreaterThan(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform >", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform >=", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformLessThan(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform <", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformLessThanOrEqualTo(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform <=", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformLike(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform like", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformNotLike(String value) {
            addCriterion("tk_commission_pre_fee_for_media_platform not like", value, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformIn(List<String> values) {
            addCriterion("tk_commission_pre_fee_for_media_platform in", values, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformNotIn(List<String> values) {
            addCriterion("tk_commission_pre_fee_for_media_platform not in", values, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformBetween(String value1, String value2) {
            addCriterion("tk_commission_pre_fee_for_media_platform between", value1, value2, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionPreFeeForMediaPlatformNotBetween(String value1, String value2) {
            addCriterion("tk_commission_pre_fee_for_media_platform not between", value1, value2, "tkCommissionPreFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformIsNull() {
            addCriterion("tk_commission_fee_for_media_platform is null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformIsNotNull() {
            addCriterion("tk_commission_fee_for_media_platform is not null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformEqualTo(String value) {
            addCriterion("tk_commission_fee_for_media_platform =", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformNotEqualTo(String value) {
            addCriterion("tk_commission_fee_for_media_platform <>", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformGreaterThan(String value) {
            addCriterion("tk_commission_fee_for_media_platform >", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("tk_commission_fee_for_media_platform >=", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformLessThan(String value) {
            addCriterion("tk_commission_fee_for_media_platform <", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformLessThanOrEqualTo(String value) {
            addCriterion("tk_commission_fee_for_media_platform <=", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformLike(String value) {
            addCriterion("tk_commission_fee_for_media_platform like", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformNotLike(String value) {
            addCriterion("tk_commission_fee_for_media_platform not like", value, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformIn(List<String> values) {
            addCriterion("tk_commission_fee_for_media_platform in", values, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformNotIn(List<String> values) {
            addCriterion("tk_commission_fee_for_media_platform not in", values, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformBetween(String value1, String value2) {
            addCriterion("tk_commission_fee_for_media_platform between", value1, value2, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionFeeForMediaPlatformNotBetween(String value1, String value2) {
            addCriterion("tk_commission_fee_for_media_platform not between", value1, value2, "tkCommissionFeeForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformIsNull() {
            addCriterion("tk_commission_rate_for_media_platform is null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformIsNotNull() {
            addCriterion("tk_commission_rate_for_media_platform is not null");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformEqualTo(String value) {
            addCriterion("tk_commission_rate_for_media_platform =", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformNotEqualTo(String value) {
            addCriterion("tk_commission_rate_for_media_platform <>", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformGreaterThan(String value) {
            addCriterion("tk_commission_rate_for_media_platform >", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("tk_commission_rate_for_media_platform >=", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformLessThan(String value) {
            addCriterion("tk_commission_rate_for_media_platform <", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformLessThanOrEqualTo(String value) {
            addCriterion("tk_commission_rate_for_media_platform <=", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformLike(String value) {
            addCriterion("tk_commission_rate_for_media_platform like", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformNotLike(String value) {
            addCriterion("tk_commission_rate_for_media_platform not like", value, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformIn(List<String> values) {
            addCriterion("tk_commission_rate_for_media_platform in", values, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformNotIn(List<String> values) {
            addCriterion("tk_commission_rate_for_media_platform not in", values, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformBetween(String value1, String value2) {
            addCriterion("tk_commission_rate_for_media_platform between", value1, value2, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andTkCommissionRateForMediaPlatformNotBetween(String value1, String value2) {
            addCriterion("tk_commission_rate_for_media_platform not between", value1, value2, "tkCommissionRateForMediaPlatform");
            return (Criteria) this;
        }

        public Criteria andPubIdIsNull() {
            addCriterion("pub_id is null");
            return (Criteria) this;
        }

        public Criteria andPubIdIsNotNull() {
            addCriterion("pub_id is not null");
            return (Criteria) this;
        }

        public Criteria andPubIdEqualTo(String value) {
            addCriterion("pub_id =", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotEqualTo(String value) {
            addCriterion("pub_id <>", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdGreaterThan(String value) {
            addCriterion("pub_id >", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdGreaterThanOrEqualTo(String value) {
            addCriterion("pub_id >=", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdLessThan(String value) {
            addCriterion("pub_id <", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdLessThanOrEqualTo(String value) {
            addCriterion("pub_id <=", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdLike(String value) {
            addCriterion("pub_id like", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotLike(String value) {
            addCriterion("pub_id not like", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdIn(List<String> values) {
            addCriterion("pub_id in", values, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotIn(List<String> values) {
            addCriterion("pub_id not in", values, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdBetween(String value1, String value2) {
            addCriterion("pub_id between", value1, value2, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotBetween(String value1, String value2) {
            addCriterion("pub_id not between", value1, value2, "pubId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("site_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("site_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(String value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(String value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(String value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(String value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(String value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLike(String value) {
            addCriterion("site_id like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotLike(String value) {
            addCriterion("site_id not like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<String> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<String> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(String value1, String value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(String value1, String value2) {
            addCriterion("site_id not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdIsNull() {
            addCriterion("adzone_id is null");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdIsNotNull() {
            addCriterion("adzone_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdEqualTo(String value) {
            addCriterion("adzone_id =", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdNotEqualTo(String value) {
            addCriterion("adzone_id <>", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdGreaterThan(String value) {
            addCriterion("adzone_id >", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdGreaterThanOrEqualTo(String value) {
            addCriterion("adzone_id >=", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdLessThan(String value) {
            addCriterion("adzone_id <", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdLessThanOrEqualTo(String value) {
            addCriterion("adzone_id <=", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdLike(String value) {
            addCriterion("adzone_id like", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdNotLike(String value) {
            addCriterion("adzone_id not like", value, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdIn(List<String> values) {
            addCriterion("adzone_id in", values, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdNotIn(List<String> values) {
            addCriterion("adzone_id not in", values, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdBetween(String value1, String value2) {
            addCriterion("adzone_id between", value1, value2, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andAdzoneIdNotBetween(String value1, String value2) {
            addCriterion("adzone_id not between", value1, value2, "adzoneId");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("site_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("site_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("site_name =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("site_name <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("site_name >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_name >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("site_name <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("site_name <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("site_name like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("site_name not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("site_name in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("site_name not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("site_name between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("site_name not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameIsNull() {
            addCriterion("adzone_name is null");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameIsNotNull() {
            addCriterion("adzone_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameEqualTo(String value) {
            addCriterion("adzone_name =", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameNotEqualTo(String value) {
            addCriterion("adzone_name <>", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameGreaterThan(String value) {
            addCriterion("adzone_name >", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameGreaterThanOrEqualTo(String value) {
            addCriterion("adzone_name >=", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameLessThan(String value) {
            addCriterion("adzone_name <", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameLessThanOrEqualTo(String value) {
            addCriterion("adzone_name <=", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameLike(String value) {
            addCriterion("adzone_name like", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameNotLike(String value) {
            addCriterion("adzone_name not like", value, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameIn(List<String> values) {
            addCriterion("adzone_name in", values, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameNotIn(List<String> values) {
            addCriterion("adzone_name not in", values, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameBetween(String value1, String value2) {
            addCriterion("adzone_name between", value1, value2, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andAdzoneNameNotBetween(String value1, String value2) {
            addCriterion("adzone_name not between", value1, value2, "adzoneName");
            return (Criteria) this;
        }

        public Criteria andSpecialIdIsNull() {
            addCriterion("special_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecialIdIsNotNull() {
            addCriterion("special_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialIdEqualTo(String value) {
            addCriterion("special_id =", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotEqualTo(String value) {
            addCriterion("special_id <>", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdGreaterThan(String value) {
            addCriterion("special_id >", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdGreaterThanOrEqualTo(String value) {
            addCriterion("special_id >=", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdLessThan(String value) {
            addCriterion("special_id <", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdLessThanOrEqualTo(String value) {
            addCriterion("special_id <=", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdLike(String value) {
            addCriterion("special_id like", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotLike(String value) {
            addCriterion("special_id not like", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdIn(List<String> values) {
            addCriterion("special_id in", values, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotIn(List<String> values) {
            addCriterion("special_id not in", values, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdBetween(String value1, String value2) {
            addCriterion("special_id between", value1, value2, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotBetween(String value1, String value2) {
            addCriterion("special_id not between", value1, value2, "specialId");
            return (Criteria) this;
        }

        public Criteria andRelationIdIsNull() {
            addCriterion("relation_id is null");
            return (Criteria) this;
        }

        public Criteria andRelationIdIsNotNull() {
            addCriterion("relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelationIdEqualTo(String value) {
            addCriterion("relation_id =", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdNotEqualTo(String value) {
            addCriterion("relation_id <>", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdGreaterThan(String value) {
            addCriterion("relation_id >", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdGreaterThanOrEqualTo(String value) {
            addCriterion("relation_id >=", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdLessThan(String value) {
            addCriterion("relation_id <", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdLessThanOrEqualTo(String value) {
            addCriterion("relation_id <=", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdLike(String value) {
            addCriterion("relation_id like", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdNotLike(String value) {
            addCriterion("relation_id not like", value, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdIn(List<String> values) {
            addCriterion("relation_id in", values, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdNotIn(List<String> values) {
            addCriterion("relation_id not in", values, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdBetween(String value1, String value2) {
            addCriterion("relation_id between", value1, value2, "relationId");
            return (Criteria) this;
        }

        public Criteria andRelationIdNotBetween(String value1, String value2) {
            addCriterion("relation_id not between", value1, value2, "relationId");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameIsNull() {
            addCriterion("item_category_name is null");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameIsNotNull() {
            addCriterion("item_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameEqualTo(String value) {
            addCriterion("item_category_name =", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameNotEqualTo(String value) {
            addCriterion("item_category_name <>", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameGreaterThan(String value) {
            addCriterion("item_category_name >", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("item_category_name >=", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameLessThan(String value) {
            addCriterion("item_category_name <", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("item_category_name <=", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameLike(String value) {
            addCriterion("item_category_name like", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameNotLike(String value) {
            addCriterion("item_category_name not like", value, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameIn(List<String> values) {
            addCriterion("item_category_name in", values, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameNotIn(List<String> values) {
            addCriterion("item_category_name not in", values, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameBetween(String value1, String value2) {
            addCriterion("item_category_name between", value1, value2, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andItemCategoryNameNotBetween(String value1, String value2) {
            addCriterion("item_category_name not between", value1, value2, "itemCategoryName");
            return (Criteria) this;
        }

        public Criteria andSellerNickIsNull() {
            addCriterion("seller_nick is null");
            return (Criteria) this;
        }

        public Criteria andSellerNickIsNotNull() {
            addCriterion("seller_nick is not null");
            return (Criteria) this;
        }

        public Criteria andSellerNickEqualTo(String value) {
            addCriterion("seller_nick =", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickNotEqualTo(String value) {
            addCriterion("seller_nick <>", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickGreaterThan(String value) {
            addCriterion("seller_nick >", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickGreaterThanOrEqualTo(String value) {
            addCriterion("seller_nick >=", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickLessThan(String value) {
            addCriterion("seller_nick <", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickLessThanOrEqualTo(String value) {
            addCriterion("seller_nick <=", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickLike(String value) {
            addCriterion("seller_nick like", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickNotLike(String value) {
            addCriterion("seller_nick not like", value, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickIn(List<String> values) {
            addCriterion("seller_nick in", values, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickNotIn(List<String> values) {
            addCriterion("seller_nick not in", values, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickBetween(String value1, String value2) {
            addCriterion("seller_nick between", value1, value2, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerNickNotBetween(String value1, String value2) {
            addCriterion("seller_nick not between", value1, value2, "sellerNick");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleIsNull() {
            addCriterion("seller_shop_title is null");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleIsNotNull() {
            addCriterion("seller_shop_title is not null");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleEqualTo(String value) {
            addCriterion("seller_shop_title =", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleNotEqualTo(String value) {
            addCriterion("seller_shop_title <>", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleGreaterThan(String value) {
            addCriterion("seller_shop_title >", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleGreaterThanOrEqualTo(String value) {
            addCriterion("seller_shop_title >=", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleLessThan(String value) {
            addCriterion("seller_shop_title <", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleLessThanOrEqualTo(String value) {
            addCriterion("seller_shop_title <=", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleLike(String value) {
            addCriterion("seller_shop_title like", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleNotLike(String value) {
            addCriterion("seller_shop_title not like", value, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleIn(List<String> values) {
            addCriterion("seller_shop_title in", values, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleNotIn(List<String> values) {
            addCriterion("seller_shop_title not in", values, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleBetween(String value1, String value2) {
            addCriterion("seller_shop_title between", value1, value2, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andSellerShopTitleNotBetween(String value1, String value2) {
            addCriterion("seller_shop_title not between", value1, value2, "sellerShopTitle");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(String value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(String value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(String value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(String value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(String value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(String value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLike(String value) {
            addCriterion("item_id like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotLike(String value) {
            addCriterion("item_id not like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<String> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<String> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(String value1, String value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(String value1, String value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemTitleIsNull() {
            addCriterion("item_title is null");
            return (Criteria) this;
        }

        public Criteria andItemTitleIsNotNull() {
            addCriterion("item_title is not null");
            return (Criteria) this;
        }

        public Criteria andItemTitleEqualTo(String value) {
            addCriterion("item_title =", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleNotEqualTo(String value) {
            addCriterion("item_title <>", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleGreaterThan(String value) {
            addCriterion("item_title >", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleGreaterThanOrEqualTo(String value) {
            addCriterion("item_title >=", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleLessThan(String value) {
            addCriterion("item_title <", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleLessThanOrEqualTo(String value) {
            addCriterion("item_title <=", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleLike(String value) {
            addCriterion("item_title like", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleNotLike(String value) {
            addCriterion("item_title not like", value, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleIn(List<String> values) {
            addCriterion("item_title in", values, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleNotIn(List<String> values) {
            addCriterion("item_title not in", values, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleBetween(String value1, String value2) {
            addCriterion("item_title between", value1, value2, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemTitleNotBetween(String value1, String value2) {
            addCriterion("item_title not between", value1, value2, "itemTitle");
            return (Criteria) this;
        }

        public Criteria andItemPriceIsNull() {
            addCriterion("item_price is null");
            return (Criteria) this;
        }

        public Criteria andItemPriceIsNotNull() {
            addCriterion("item_price is not null");
            return (Criteria) this;
        }

        public Criteria andItemPriceEqualTo(String value) {
            addCriterion("item_price =", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceNotEqualTo(String value) {
            addCriterion("item_price <>", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceGreaterThan(String value) {
            addCriterion("item_price >", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceGreaterThanOrEqualTo(String value) {
            addCriterion("item_price >=", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceLessThan(String value) {
            addCriterion("item_price <", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceLessThanOrEqualTo(String value) {
            addCriterion("item_price <=", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceLike(String value) {
            addCriterion("item_price like", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceNotLike(String value) {
            addCriterion("item_price not like", value, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceIn(List<String> values) {
            addCriterion("item_price in", values, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceNotIn(List<String> values) {
            addCriterion("item_price not in", values, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceBetween(String value1, String value2) {
            addCriterion("item_price between", value1, value2, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemPriceNotBetween(String value1, String value2) {
            addCriterion("item_price not between", value1, value2, "itemPrice");
            return (Criteria) this;
        }

        public Criteria andItemLinkIsNull() {
            addCriterion("item_link is null");
            return (Criteria) this;
        }

        public Criteria andItemLinkIsNotNull() {
            addCriterion("item_link is not null");
            return (Criteria) this;
        }

        public Criteria andItemLinkEqualTo(String value) {
            addCriterion("item_link =", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkNotEqualTo(String value) {
            addCriterion("item_link <>", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkGreaterThan(String value) {
            addCriterion("item_link >", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkGreaterThanOrEqualTo(String value) {
            addCriterion("item_link >=", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkLessThan(String value) {
            addCriterion("item_link <", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkLessThanOrEqualTo(String value) {
            addCriterion("item_link <=", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkLike(String value) {
            addCriterion("item_link like", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkNotLike(String value) {
            addCriterion("item_link not like", value, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkIn(List<String> values) {
            addCriterion("item_link in", values, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkNotIn(List<String> values) {
            addCriterion("item_link not in", values, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkBetween(String value1, String value2) {
            addCriterion("item_link between", value1, value2, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemLinkNotBetween(String value1, String value2) {
            addCriterion("item_link not between", value1, value2, "itemLink");
            return (Criteria) this;
        }

        public Criteria andItemNumIsNull() {
            addCriterion("item_num is null");
            return (Criteria) this;
        }

        public Criteria andItemNumIsNotNull() {
            addCriterion("item_num is not null");
            return (Criteria) this;
        }

        public Criteria andItemNumEqualTo(Integer value) {
            addCriterion("item_num =", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotEqualTo(Integer value) {
            addCriterion("item_num <>", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumGreaterThan(Integer value) {
            addCriterion("item_num >", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_num >=", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumLessThan(Integer value) {
            addCriterion("item_num <", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumLessThanOrEqualTo(Integer value) {
            addCriterion("item_num <=", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumIn(List<Integer> values) {
            addCriterion("item_num in", values, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotIn(List<Integer> values) {
            addCriterion("item_num not in", values, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumBetween(Integer value1, Integer value2) {
            addCriterion("item_num between", value1, value2, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotBetween(Integer value1, Integer value2) {
            addCriterion("item_num not between", value1, value2, "itemNum");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeIsNull() {
            addCriterion("tk_deposit_time is null");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeIsNotNull() {
            addCriterion("tk_deposit_time is not null");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeEqualTo(LocalDateTime value) {
            addCriterion("tk_deposit_time =", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tk_deposit_time <>", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeGreaterThan(LocalDateTime value) {
            addCriterion("tk_deposit_time >", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_deposit_time >=", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeLessThan(LocalDateTime value) {
            addCriterion("tk_deposit_time <", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tk_deposit_time <=", value, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeIn(List<LocalDateTime> values) {
            addCriterion("tk_deposit_time in", values, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tk_deposit_time not in", values, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_deposit_time between", value1, value2, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTkDepositTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tk_deposit_time not between", value1, value2, "tkDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeIsNull() {
            addCriterion("tb_deposit_time is null");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeIsNotNull() {
            addCriterion("tb_deposit_time is not null");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeEqualTo(LocalDateTime value) {
            addCriterion("tb_deposit_time =", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeNotEqualTo(LocalDateTime value) {
            addCriterion("tb_deposit_time <>", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeGreaterThan(LocalDateTime value) {
            addCriterion("tb_deposit_time >", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("tb_deposit_time >=", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeLessThan(LocalDateTime value) {
            addCriterion("tb_deposit_time <", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("tb_deposit_time <=", value, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeIn(List<LocalDateTime> values) {
            addCriterion("tb_deposit_time in", values, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeNotIn(List<LocalDateTime> values) {
            addCriterion("tb_deposit_time not in", values, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tb_deposit_time between", value1, value2, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andTbDepositTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("tb_deposit_time not between", value1, value2, "tbDepositTime");
            return (Criteria) this;
        }

        public Criteria andDepositPriceIsNull() {
            addCriterion("deposit_price is null");
            return (Criteria) this;
        }

        public Criteria andDepositPriceIsNotNull() {
            addCriterion("deposit_price is not null");
            return (Criteria) this;
        }

        public Criteria andDepositPriceEqualTo(String value) {
            addCriterion("deposit_price =", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceNotEqualTo(String value) {
            addCriterion("deposit_price <>", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceGreaterThan(String value) {
            addCriterion("deposit_price >", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceGreaterThanOrEqualTo(String value) {
            addCriterion("deposit_price >=", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceLessThan(String value) {
            addCriterion("deposit_price <", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceLessThanOrEqualTo(String value) {
            addCriterion("deposit_price <=", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceLike(String value) {
            addCriterion("deposit_price like", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceNotLike(String value) {
            addCriterion("deposit_price not like", value, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceIn(List<String> values) {
            addCriterion("deposit_price in", values, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceNotIn(List<String> values) {
            addCriterion("deposit_price not in", values, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceBetween(String value1, String value2) {
            addCriterion("deposit_price between", value1, value2, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andDepositPriceNotBetween(String value1, String value2) {
            addCriterion("deposit_price not between", value1, value2, "depositPrice");
            return (Criteria) this;
        }

        public Criteria andAlscIdIsNull() {
            addCriterion("alsc_id is null");
            return (Criteria) this;
        }

        public Criteria andAlscIdIsNotNull() {
            addCriterion("alsc_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlscIdEqualTo(String value) {
            addCriterion("alsc_id =", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdNotEqualTo(String value) {
            addCriterion("alsc_id <>", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdGreaterThan(String value) {
            addCriterion("alsc_id >", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdGreaterThanOrEqualTo(String value) {
            addCriterion("alsc_id >=", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdLessThan(String value) {
            addCriterion("alsc_id <", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdLessThanOrEqualTo(String value) {
            addCriterion("alsc_id <=", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdLike(String value) {
            addCriterion("alsc_id like", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdNotLike(String value) {
            addCriterion("alsc_id not like", value, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdIn(List<String> values) {
            addCriterion("alsc_id in", values, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdNotIn(List<String> values) {
            addCriterion("alsc_id not in", values, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdBetween(String value1, String value2) {
            addCriterion("alsc_id between", value1, value2, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscIdNotBetween(String value1, String value2) {
            addCriterion("alsc_id not between", value1, value2, "alscId");
            return (Criteria) this;
        }

        public Criteria andAlscPidIsNull() {
            addCriterion("alsc_pid is null");
            return (Criteria) this;
        }

        public Criteria andAlscPidIsNotNull() {
            addCriterion("alsc_pid is not null");
            return (Criteria) this;
        }

        public Criteria andAlscPidEqualTo(String value) {
            addCriterion("alsc_pid =", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidNotEqualTo(String value) {
            addCriterion("alsc_pid <>", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidGreaterThan(String value) {
            addCriterion("alsc_pid >", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidGreaterThanOrEqualTo(String value) {
            addCriterion("alsc_pid >=", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidLessThan(String value) {
            addCriterion("alsc_pid <", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidLessThanOrEqualTo(String value) {
            addCriterion("alsc_pid <=", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidLike(String value) {
            addCriterion("alsc_pid like", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidNotLike(String value) {
            addCriterion("alsc_pid not like", value, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidIn(List<String> values) {
            addCriterion("alsc_pid in", values, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidNotIn(List<String> values) {
            addCriterion("alsc_pid not in", values, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidBetween(String value1, String value2) {
            addCriterion("alsc_pid between", value1, value2, "alscPid");
            return (Criteria) this;
        }

        public Criteria andAlscPidNotBetween(String value1, String value2) {
            addCriterion("alsc_pid not between", value1, value2, "alscPid");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListIsNull() {
            addCriterion("service_fee_dto_list is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListIsNotNull() {
            addCriterion("service_fee_dto_list is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListEqualTo(String value) {
            addCriterion("service_fee_dto_list =", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListNotEqualTo(String value) {
            addCriterion("service_fee_dto_list <>", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListGreaterThan(String value) {
            addCriterion("service_fee_dto_list >", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListGreaterThanOrEqualTo(String value) {
            addCriterion("service_fee_dto_list >=", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListLessThan(String value) {
            addCriterion("service_fee_dto_list <", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListLessThanOrEqualTo(String value) {
            addCriterion("service_fee_dto_list <=", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListLike(String value) {
            addCriterion("service_fee_dto_list like", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListNotLike(String value) {
            addCriterion("service_fee_dto_list not like", value, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListIn(List<String> values) {
            addCriterion("service_fee_dto_list in", values, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListNotIn(List<String> values) {
            addCriterion("service_fee_dto_list not in", values, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListBetween(String value1, String value2) {
            addCriterion("service_fee_dto_list between", value1, value2, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andServiceFeeDtoListNotBetween(String value1, String value2) {
            addCriterion("service_fee_dto_list not between", value1, value2, "serviceFeeDtoList");
            return (Criteria) this;
        }

        public Criteria andLxRidIsNull() {
            addCriterion("lx_rid is null");
            return (Criteria) this;
        }

        public Criteria andLxRidIsNotNull() {
            addCriterion("lx_rid is not null");
            return (Criteria) this;
        }

        public Criteria andLxRidEqualTo(String value) {
            addCriterion("lx_rid =", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidNotEqualTo(String value) {
            addCriterion("lx_rid <>", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidGreaterThan(String value) {
            addCriterion("lx_rid >", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidGreaterThanOrEqualTo(String value) {
            addCriterion("lx_rid >=", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidLessThan(String value) {
            addCriterion("lx_rid <", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidLessThanOrEqualTo(String value) {
            addCriterion("lx_rid <=", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidLike(String value) {
            addCriterion("lx_rid like", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidNotLike(String value) {
            addCriterion("lx_rid not like", value, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidIn(List<String> values) {
            addCriterion("lx_rid in", values, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidNotIn(List<String> values) {
            addCriterion("lx_rid not in", values, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidBetween(String value1, String value2) {
            addCriterion("lx_rid between", value1, value2, "lxRid");
            return (Criteria) this;
        }

        public Criteria andLxRidNotBetween(String value1, String value2) {
            addCriterion("lx_rid not between", value1, value2, "lxRid");
            return (Criteria) this;
        }

        public Criteria andIsLxIsNull() {
            addCriterion("is_lx is null");
            return (Criteria) this;
        }

        public Criteria andIsLxIsNotNull() {
            addCriterion("is_lx is not null");
            return (Criteria) this;
        }

        public Criteria andIsLxEqualTo(String value) {
            addCriterion("is_lx =", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxNotEqualTo(String value) {
            addCriterion("is_lx <>", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxGreaterThan(String value) {
            addCriterion("is_lx >", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxGreaterThanOrEqualTo(String value) {
            addCriterion("is_lx >=", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxLessThan(String value) {
            addCriterion("is_lx <", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxLessThanOrEqualTo(String value) {
            addCriterion("is_lx <=", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxLike(String value) {
            addCriterion("is_lx like", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxNotLike(String value) {
            addCriterion("is_lx not like", value, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxIn(List<String> values) {
            addCriterion("is_lx in", values, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxNotIn(List<String> values) {
            addCriterion("is_lx not in", values, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxBetween(String value1, String value2) {
            addCriterion("is_lx between", value1, value2, "isLx");
            return (Criteria) this;
        }

        public Criteria andIsLxNotBetween(String value1, String value2) {
            addCriterion("is_lx not between", value1, value2, "isLx");
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