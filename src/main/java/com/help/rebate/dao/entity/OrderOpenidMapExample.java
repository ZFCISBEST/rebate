package com.help.rebate.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderOpenidMapExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public OrderOpenidMapExample() {
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

        public Criteria andGmtCreatedEqualTo(Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(Date value1, Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(Date value1, Date value2) {
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

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
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

        public Criteria andParentTradeIdIsNull() {
            addCriterion("parent_trade_id is null");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdIsNotNull() {
            addCriterion("parent_trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdEqualTo(String value) {
            addCriterion("parent_trade_id =", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdNotEqualTo(String value) {
            addCriterion("parent_trade_id <>", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdGreaterThan(String value) {
            addCriterion("parent_trade_id >", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_trade_id >=", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdLessThan(String value) {
            addCriterion("parent_trade_id <", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdLessThanOrEqualTo(String value) {
            addCriterion("parent_trade_id <=", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdLike(String value) {
            addCriterion("parent_trade_id like", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdNotLike(String value) {
            addCriterion("parent_trade_id not like", value, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdIn(List<String> values) {
            addCriterion("parent_trade_id in", values, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdNotIn(List<String> values) {
            addCriterion("parent_trade_id not in", values, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdBetween(String value1, String value2) {
            addCriterion("parent_trade_id between", value1, value2, "parentTradeId");
            return (Criteria) this;
        }

        public Criteria andParentTradeIdNotBetween(String value1, String value2) {
            addCriterion("parent_trade_id not between", value1, value2, "parentTradeId");
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

        public Criteria andExternalIdIsNull() {
            addCriterion("external_id is null");
            return (Criteria) this;
        }

        public Criteria andExternalIdIsNotNull() {
            addCriterion("external_id is not null");
            return (Criteria) this;
        }

        public Criteria andExternalIdEqualTo(String value) {
            addCriterion("external_id =", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotEqualTo(String value) {
            addCriterion("external_id <>", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdGreaterThan(String value) {
            addCriterion("external_id >", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdGreaterThanOrEqualTo(String value) {
            addCriterion("external_id >=", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdLessThan(String value) {
            addCriterion("external_id <", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdLessThanOrEqualTo(String value) {
            addCriterion("external_id <=", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdLike(String value) {
            addCriterion("external_id like", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotLike(String value) {
            addCriterion("external_id not like", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdIn(List<String> values) {
            addCriterion("external_id in", values, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotIn(List<String> values) {
            addCriterion("external_id not in", values, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdBetween(String value1, String value2) {
            addCriterion("external_id between", value1, value2, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotBetween(String value1, String value2) {
            addCriterion("external_id not between", value1, value2, "externalId");
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

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeIsNull() {
            addCriterion("actual_commission_fee is null");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeIsNotNull() {
            addCriterion("actual_commission_fee is not null");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeEqualTo(String value) {
            addCriterion("actual_commission_fee =", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeNotEqualTo(String value) {
            addCriterion("actual_commission_fee <>", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeGreaterThan(String value) {
            addCriterion("actual_commission_fee >", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeGreaterThanOrEqualTo(String value) {
            addCriterion("actual_commission_fee >=", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeLessThan(String value) {
            addCriterion("actual_commission_fee <", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeLessThanOrEqualTo(String value) {
            addCriterion("actual_commission_fee <=", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeLike(String value) {
            addCriterion("actual_commission_fee like", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeNotLike(String value) {
            addCriterion("actual_commission_fee not like", value, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeIn(List<String> values) {
            addCriterion("actual_commission_fee in", values, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeNotIn(List<String> values) {
            addCriterion("actual_commission_fee not in", values, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeBetween(String value1, String value2) {
            addCriterion("actual_commission_fee between", value1, value2, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andActualCommissionFeeNotBetween(String value1, String value2) {
            addCriterion("actual_commission_fee not between", value1, value2, "actualCommissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIsNull() {
            addCriterion("commission_status is null");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIsNotNull() {
            addCriterion("commission_status is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusEqualTo(String value) {
            addCriterion("commission_status =", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotEqualTo(String value) {
            addCriterion("commission_status <>", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusGreaterThan(String value) {
            addCriterion("commission_status >", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusGreaterThanOrEqualTo(String value) {
            addCriterion("commission_status >=", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusLessThan(String value) {
            addCriterion("commission_status <", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusLessThanOrEqualTo(String value) {
            addCriterion("commission_status <=", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusLike(String value) {
            addCriterion("commission_status like", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotLike(String value) {
            addCriterion("commission_status not like", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIn(List<String> values) {
            addCriterion("commission_status in", values, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotIn(List<String> values) {
            addCriterion("commission_status not in", values, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusBetween(String value1, String value2) {
            addCriterion("commission_status between", value1, value2, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotBetween(String value1, String value2) {
            addCriterion("commission_status not between", value1, value2, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdIsNull() {
            addCriterion("current_pick_record_id is null");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdIsNotNull() {
            addCriterion("current_pick_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdEqualTo(Integer value) {
            addCriterion("current_pick_record_id =", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdNotEqualTo(Integer value) {
            addCriterion("current_pick_record_id <>", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdGreaterThan(Integer value) {
            addCriterion("current_pick_record_id >", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_pick_record_id >=", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdLessThan(Integer value) {
            addCriterion("current_pick_record_id <", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("current_pick_record_id <=", value, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdIn(List<Integer> values) {
            addCriterion("current_pick_record_id in", values, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdNotIn(List<Integer> values) {
            addCriterion("current_pick_record_id not in", values, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("current_pick_record_id between", value1, value2, "currentPickRecordId");
            return (Criteria) this;
        }

        public Criteria andCurrentPickRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("current_pick_record_id not between", value1, value2, "currentPickRecordId");
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

        public Criteria andRefundFeeIsNull() {
            addCriterion("refund_fee is null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIsNotNull() {
            addCriterion("refund_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeEqualTo(String value) {
            addCriterion("refund_fee =", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotEqualTo(String value) {
            addCriterion("refund_fee <>", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThan(String value) {
            addCriterion("refund_fee >", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThanOrEqualTo(String value) {
            addCriterion("refund_fee >=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThan(String value) {
            addCriterion("refund_fee <", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThanOrEqualTo(String value) {
            addCriterion("refund_fee <=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLike(String value) {
            addCriterion("refund_fee like", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotLike(String value) {
            addCriterion("refund_fee not like", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIn(List<String> values) {
            addCriterion("refund_fee in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotIn(List<String> values) {
            addCriterion("refund_fee not in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeBetween(String value1, String value2) {
            addCriterion("refund_fee between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotBetween(String value1, String value2) {
            addCriterion("refund_fee not between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andMapTypeIsNull() {
            addCriterion("map_type is null");
            return (Criteria) this;
        }

        public Criteria andMapTypeIsNotNull() {
            addCriterion("map_type is not null");
            return (Criteria) this;
        }

        public Criteria andMapTypeEqualTo(String value) {
            addCriterion("map_type =", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeNotEqualTo(String value) {
            addCriterion("map_type <>", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeGreaterThan(String value) {
            addCriterion("map_type >", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeGreaterThanOrEqualTo(String value) {
            addCriterion("map_type >=", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeLessThan(String value) {
            addCriterion("map_type <", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeLessThanOrEqualTo(String value) {
            addCriterion("map_type <=", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeLike(String value) {
            addCriterion("map_type like", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeNotLike(String value) {
            addCriterion("map_type not like", value, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeIn(List<String> values) {
            addCriterion("map_type in", values, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeNotIn(List<String> values) {
            addCriterion("map_type not in", values, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeBetween(String value1, String value2) {
            addCriterion("map_type between", value1, value2, "mapType");
            return (Criteria) this;
        }

        public Criteria andMapTypeNotBetween(String value1, String value2) {
            addCriterion("map_type not between", value1, value2, "mapType");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
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