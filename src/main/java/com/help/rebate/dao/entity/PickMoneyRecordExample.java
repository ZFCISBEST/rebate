package com.help.rebate.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickMoneyRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    private Boolean forUpdate;

    public PickMoneyRecordExample() {
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

    public void setForUpdate(Boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public Boolean getForUpdate() {
        return forUpdate;
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

        public Criteria andTotalCommissionIsNull() {
            addCriterion("total_commission is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIsNotNull() {
            addCriterion("total_commission is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionEqualTo(String value) {
            addCriterion("total_commission =", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotEqualTo(String value) {
            addCriterion("total_commission <>", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThan(String value) {
            addCriterion("total_commission >", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThanOrEqualTo(String value) {
            addCriterion("total_commission >=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThan(String value) {
            addCriterion("total_commission <", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThanOrEqualTo(String value) {
            addCriterion("total_commission <=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLike(String value) {
            addCriterion("total_commission like", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotLike(String value) {
            addCriterion("total_commission not like", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIn(List<String> values) {
            addCriterion("total_commission in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotIn(List<String> values) {
            addCriterion("total_commission not in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionBetween(String value1, String value2) {
            addCriterion("total_commission between", value1, value2, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotBetween(String value1, String value2) {
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

        public Criteria andRemainCommissionEqualTo(String value) {
            addCriterion("remain_commission =", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotEqualTo(String value) {
            addCriterion("remain_commission <>", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionGreaterThan(String value) {
            addCriterion("remain_commission >", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionGreaterThanOrEqualTo(String value) {
            addCriterion("remain_commission >=", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionLessThan(String value) {
            addCriterion("remain_commission <", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionLessThanOrEqualTo(String value) {
            addCriterion("remain_commission <=", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionLike(String value) {
            addCriterion("remain_commission like", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotLike(String value) {
            addCriterion("remain_commission not like", value, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionIn(List<String> values) {
            addCriterion("remain_commission in", values, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotIn(List<String> values) {
            addCriterion("remain_commission not in", values, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionBetween(String value1, String value2) {
            addCriterion("remain_commission between", value1, value2, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andRemainCommissionNotBetween(String value1, String value2) {
            addCriterion("remain_commission not between", value1, value2, "remainCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionIsNull() {
            addCriterion("pre_pick_commission is null");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionIsNotNull() {
            addCriterion("pre_pick_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionEqualTo(String value) {
            addCriterion("pre_pick_commission =", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionNotEqualTo(String value) {
            addCriterion("pre_pick_commission <>", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionGreaterThan(String value) {
            addCriterion("pre_pick_commission >", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionGreaterThanOrEqualTo(String value) {
            addCriterion("pre_pick_commission >=", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionLessThan(String value) {
            addCriterion("pre_pick_commission <", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionLessThanOrEqualTo(String value) {
            addCriterion("pre_pick_commission <=", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionLike(String value) {
            addCriterion("pre_pick_commission like", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionNotLike(String value) {
            addCriterion("pre_pick_commission not like", value, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionIn(List<String> values) {
            addCriterion("pre_pick_commission in", values, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionNotIn(List<String> values) {
            addCriterion("pre_pick_commission not in", values, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionBetween(String value1, String value2) {
            addCriterion("pre_pick_commission between", value1, value2, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andPrePickCommissionNotBetween(String value1, String value2) {
            addCriterion("pre_pick_commission not between", value1, value2, "prePickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionIsNull() {
            addCriterion("act_pick_commission is null");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionIsNotNull() {
            addCriterion("act_pick_commission is not null");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionEqualTo(String value) {
            addCriterion("act_pick_commission =", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionNotEqualTo(String value) {
            addCriterion("act_pick_commission <>", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionGreaterThan(String value) {
            addCriterion("act_pick_commission >", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionGreaterThanOrEqualTo(String value) {
            addCriterion("act_pick_commission >=", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionLessThan(String value) {
            addCriterion("act_pick_commission <", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionLessThanOrEqualTo(String value) {
            addCriterion("act_pick_commission <=", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionLike(String value) {
            addCriterion("act_pick_commission like", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionNotLike(String value) {
            addCriterion("act_pick_commission not like", value, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionIn(List<String> values) {
            addCriterion("act_pick_commission in", values, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionNotIn(List<String> values) {
            addCriterion("act_pick_commission not in", values, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionBetween(String value1, String value2) {
            addCriterion("act_pick_commission between", value1, value2, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andActPickCommissionNotBetween(String value1, String value2) {
            addCriterion("act_pick_commission not between", value1, value2, "actPickCommission");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoIsNull() {
            addCriterion("pick_attach_info is null");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoIsNotNull() {
            addCriterion("pick_attach_info is not null");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoEqualTo(String value) {
            addCriterion("pick_attach_info =", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoNotEqualTo(String value) {
            addCriterion("pick_attach_info <>", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoGreaterThan(String value) {
            addCriterion("pick_attach_info >", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoGreaterThanOrEqualTo(String value) {
            addCriterion("pick_attach_info >=", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoLessThan(String value) {
            addCriterion("pick_attach_info <", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoLessThanOrEqualTo(String value) {
            addCriterion("pick_attach_info <=", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoLike(String value) {
            addCriterion("pick_attach_info like", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoNotLike(String value) {
            addCriterion("pick_attach_info not like", value, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoIn(List<String> values) {
            addCriterion("pick_attach_info in", values, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoNotIn(List<String> values) {
            addCriterion("pick_attach_info not in", values, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoBetween(String value1, String value2) {
            addCriterion("pick_attach_info between", value1, value2, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickAttachInfoNotBetween(String value1, String value2) {
            addCriterion("pick_attach_info not between", value1, value2, "pickAttachInfo");
            return (Criteria) this;
        }

        public Criteria andPickStatusIsNull() {
            addCriterion("pick_status is null");
            return (Criteria) this;
        }

        public Criteria andPickStatusIsNotNull() {
            addCriterion("pick_status is not null");
            return (Criteria) this;
        }

        public Criteria andPickStatusEqualTo(String value) {
            addCriterion("pick_status =", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusNotEqualTo(String value) {
            addCriterion("pick_status <>", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusGreaterThan(String value) {
            addCriterion("pick_status >", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusGreaterThanOrEqualTo(String value) {
            addCriterion("pick_status >=", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusLessThan(String value) {
            addCriterion("pick_status <", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusLessThanOrEqualTo(String value) {
            addCriterion("pick_status <=", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusLike(String value) {
            addCriterion("pick_status like", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusNotLike(String value) {
            addCriterion("pick_status not like", value, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusIn(List<String> values) {
            addCriterion("pick_status in", values, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusNotIn(List<String> values) {
            addCriterion("pick_status not in", values, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusBetween(String value1, String value2) {
            addCriterion("pick_status between", value1, value2, "pickStatus");
            return (Criteria) this;
        }

        public Criteria andPickStatusNotBetween(String value1, String value2) {
            addCriterion("pick_status not between", value1, value2, "pickStatus");
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