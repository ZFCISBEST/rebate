package com.help.rebate.dao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class V2TaobaoTklConvertHistoryInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public V2TaobaoTklConvertHistoryInfoExample() {
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

        public Criteria andPubSiteTypeIsNull() {
            addCriterion("pub_site_type is null");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeIsNotNull() {
            addCriterion("pub_site_type is not null");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeEqualTo(String value) {
            addCriterion("pub_site_type =", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeNotEqualTo(String value) {
            addCriterion("pub_site_type <>", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeGreaterThan(String value) {
            addCriterion("pub_site_type >", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pub_site_type >=", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeLessThan(String value) {
            addCriterion("pub_site_type <", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeLessThanOrEqualTo(String value) {
            addCriterion("pub_site_type <=", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeLike(String value) {
            addCriterion("pub_site_type like", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeNotLike(String value) {
            addCriterion("pub_site_type not like", value, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeIn(List<String> values) {
            addCriterion("pub_site_type in", values, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeNotIn(List<String> values) {
            addCriterion("pub_site_type not in", values, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeBetween(String value1, String value2) {
            addCriterion("pub_site_type between", value1, value2, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubSiteTypeNotBetween(String value1, String value2) {
            addCriterion("pub_site_type not between", value1, value2, "pubSiteType");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationIsNull() {
            addCriterion("pubsite_combination is null");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationIsNotNull() {
            addCriterion("pubsite_combination is not null");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationEqualTo(String value) {
            addCriterion("pubsite_combination =", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationNotEqualTo(String value) {
            addCriterion("pubsite_combination <>", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationGreaterThan(String value) {
            addCriterion("pubsite_combination >", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationGreaterThanOrEqualTo(String value) {
            addCriterion("pubsite_combination >=", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationLessThan(String value) {
            addCriterion("pubsite_combination <", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationLessThanOrEqualTo(String value) {
            addCriterion("pubsite_combination <=", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationLike(String value) {
            addCriterion("pubsite_combination like", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationNotLike(String value) {
            addCriterion("pubsite_combination not like", value, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationIn(List<String> values) {
            addCriterion("pubsite_combination in", values, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationNotIn(List<String> values) {
            addCriterion("pubsite_combination not in", values, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationBetween(String value1, String value2) {
            addCriterion("pubsite_combination between", value1, value2, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andPubsiteCombinationNotBetween(String value1, String value2) {
            addCriterion("pubsite_combination not between", value1, value2, "pubsiteCombination");
            return (Criteria) this;
        }

        public Criteria andTklIsNull() {
            addCriterion("tkl is null");
            return (Criteria) this;
        }

        public Criteria andTklIsNotNull() {
            addCriterion("tkl is not null");
            return (Criteria) this;
        }

        public Criteria andTklEqualTo(String value) {
            addCriterion("tkl =", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklNotEqualTo(String value) {
            addCriterion("tkl <>", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklGreaterThan(String value) {
            addCriterion("tkl >", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklGreaterThanOrEqualTo(String value) {
            addCriterion("tkl >=", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklLessThan(String value) {
            addCriterion("tkl <", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklLessThanOrEqualTo(String value) {
            addCriterion("tkl <=", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklLike(String value) {
            addCriterion("tkl like", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklNotLike(String value) {
            addCriterion("tkl not like", value, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklIn(List<String> values) {
            addCriterion("tkl in", values, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklNotIn(List<String> values) {
            addCriterion("tkl not in", values, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklBetween(String value1, String value2) {
            addCriterion("tkl between", value1, value2, "tkl");
            return (Criteria) this;
        }

        public Criteria andTklNotBetween(String value1, String value2) {
            addCriterion("tkl not between", value1, value2, "tkl");
            return (Criteria) this;
        }

        public Criteria andNewTklIsNull() {
            addCriterion("new_tkl is null");
            return (Criteria) this;
        }

        public Criteria andNewTklIsNotNull() {
            addCriterion("new_tkl is not null");
            return (Criteria) this;
        }

        public Criteria andNewTklEqualTo(String value) {
            addCriterion("new_tkl =", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklNotEqualTo(String value) {
            addCriterion("new_tkl <>", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklGreaterThan(String value) {
            addCriterion("new_tkl >", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklGreaterThanOrEqualTo(String value) {
            addCriterion("new_tkl >=", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklLessThan(String value) {
            addCriterion("new_tkl <", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklLessThanOrEqualTo(String value) {
            addCriterion("new_tkl <=", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklLike(String value) {
            addCriterion("new_tkl like", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklNotLike(String value) {
            addCriterion("new_tkl not like", value, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklIn(List<String> values) {
            addCriterion("new_tkl in", values, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklNotIn(List<String> values) {
            addCriterion("new_tkl not in", values, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklBetween(String value1, String value2) {
            addCriterion("new_tkl between", value1, value2, "newTkl");
            return (Criteria) this;
        }

        public Criteria andNewTklNotBetween(String value1, String value2) {
            addCriterion("new_tkl not between", value1, value2, "newTkl");
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

        public Criteria andAttachInfoIsNull() {
            addCriterion("attach_info is null");
            return (Criteria) this;
        }

        public Criteria andAttachInfoIsNotNull() {
            addCriterion("attach_info is not null");
            return (Criteria) this;
        }

        public Criteria andAttachInfoEqualTo(String value) {
            addCriterion("attach_info =", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoNotEqualTo(String value) {
            addCriterion("attach_info <>", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoGreaterThan(String value) {
            addCriterion("attach_info >", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoGreaterThanOrEqualTo(String value) {
            addCriterion("attach_info >=", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoLessThan(String value) {
            addCriterion("attach_info <", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoLessThanOrEqualTo(String value) {
            addCriterion("attach_info <=", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoLike(String value) {
            addCriterion("attach_info like", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoNotLike(String value) {
            addCriterion("attach_info not like", value, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoIn(List<String> values) {
            addCriterion("attach_info in", values, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoNotIn(List<String> values) {
            addCriterion("attach_info not in", values, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoBetween(String value1, String value2) {
            addCriterion("attach_info between", value1, value2, "attachInfo");
            return (Criteria) this;
        }

        public Criteria andAttachInfoNotBetween(String value1, String value2) {
            addCriterion("attach_info not between", value1, value2, "attachInfo");
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