package com.help.rebate.dao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class V2YljDetailInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public V2YljDetailInfoExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andMediaPicUrlIsNull() {
            addCriterion("media_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlIsNotNull() {
            addCriterion("media_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlEqualTo(String value) {
            addCriterion("media_pic_url =", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlNotEqualTo(String value) {
            addCriterion("media_pic_url <>", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlGreaterThan(String value) {
            addCriterion("media_pic_url >", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("media_pic_url >=", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlLessThan(String value) {
            addCriterion("media_pic_url <", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlLessThanOrEqualTo(String value) {
            addCriterion("media_pic_url <=", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlLike(String value) {
            addCriterion("media_pic_url like", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlNotLike(String value) {
            addCriterion("media_pic_url not like", value, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlIn(List<String> values) {
            addCriterion("media_pic_url in", values, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlNotIn(List<String> values) {
            addCriterion("media_pic_url not in", values, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlBetween(String value1, String value2) {
            addCriterion("media_pic_url between", value1, value2, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andMediaPicUrlNotBetween(String value1, String value2) {
            addCriterion("media_pic_url not between", value1, value2, "mediaPicUrl");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdIsNull() {
            addCriterion("ylj_stub_flow_id is null");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdIsNotNull() {
            addCriterion("ylj_stub_flow_id is not null");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdEqualTo(String value) {
            addCriterion("ylj_stub_flow_id =", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdNotEqualTo(String value) {
            addCriterion("ylj_stub_flow_id <>", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdGreaterThan(String value) {
            addCriterion("ylj_stub_flow_id >", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdGreaterThanOrEqualTo(String value) {
            addCriterion("ylj_stub_flow_id >=", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdLessThan(String value) {
            addCriterion("ylj_stub_flow_id <", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdLessThanOrEqualTo(String value) {
            addCriterion("ylj_stub_flow_id <=", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdLike(String value) {
            addCriterion("ylj_stub_flow_id like", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdNotLike(String value) {
            addCriterion("ylj_stub_flow_id not like", value, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdIn(List<String> values) {
            addCriterion("ylj_stub_flow_id in", values, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdNotIn(List<String> values) {
            addCriterion("ylj_stub_flow_id not in", values, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdBetween(String value1, String value2) {
            addCriterion("ylj_stub_flow_id between", value1, value2, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andYljStubFlowIdNotBetween(String value1, String value2) {
            addCriterion("ylj_stub_flow_id not between", value1, value2, "yljStubFlowId");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Byte value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Byte value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Byte value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Byte value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Byte value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Byte> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Byte> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Byte value1, Byte value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgIsNull() {
            addCriterion("verify_status_msg is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgIsNotNull() {
            addCriterion("verify_status_msg is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgEqualTo(String value) {
            addCriterion("verify_status_msg =", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgNotEqualTo(String value) {
            addCriterion("verify_status_msg <>", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgGreaterThan(String value) {
            addCriterion("verify_status_msg >", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgGreaterThanOrEqualTo(String value) {
            addCriterion("verify_status_msg >=", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgLessThan(String value) {
            addCriterion("verify_status_msg <", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgLessThanOrEqualTo(String value) {
            addCriterion("verify_status_msg <=", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgLike(String value) {
            addCriterion("verify_status_msg like", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgNotLike(String value) {
            addCriterion("verify_status_msg not like", value, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgIn(List<String> values) {
            addCriterion("verify_status_msg in", values, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgNotIn(List<String> values) {
            addCriterion("verify_status_msg not in", values, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgBetween(String value1, String value2) {
            addCriterion("verify_status_msg between", value1, value2, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusMsgNotBetween(String value1, String value2) {
            addCriterion("verify_status_msg not between", value1, value2, "verifyStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusIsNull() {
            addCriterion("conpon_status is null");
            return (Criteria) this;
        }

        public Criteria andConponStatusIsNotNull() {
            addCriterion("conpon_status is not null");
            return (Criteria) this;
        }

        public Criteria andConponStatusEqualTo(Byte value) {
            addCriterion("conpon_status =", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusNotEqualTo(Byte value) {
            addCriterion("conpon_status <>", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusGreaterThan(Byte value) {
            addCriterion("conpon_status >", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("conpon_status >=", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusLessThan(Byte value) {
            addCriterion("conpon_status <", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusLessThanOrEqualTo(Byte value) {
            addCriterion("conpon_status <=", value, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusIn(List<Byte> values) {
            addCriterion("conpon_status in", values, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusNotIn(List<Byte> values) {
            addCriterion("conpon_status not in", values, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusBetween(Byte value1, Byte value2) {
            addCriterion("conpon_status between", value1, value2, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("conpon_status not between", value1, value2, "conponStatus");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgIsNull() {
            addCriterion("conpon_status_msg is null");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgIsNotNull() {
            addCriterion("conpon_status_msg is not null");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgEqualTo(String value) {
            addCriterion("conpon_status_msg =", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgNotEqualTo(String value) {
            addCriterion("conpon_status_msg <>", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgGreaterThan(String value) {
            addCriterion("conpon_status_msg >", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgGreaterThanOrEqualTo(String value) {
            addCriterion("conpon_status_msg >=", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgLessThan(String value) {
            addCriterion("conpon_status_msg <", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgLessThanOrEqualTo(String value) {
            addCriterion("conpon_status_msg <=", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgLike(String value) {
            addCriterion("conpon_status_msg like", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgNotLike(String value) {
            addCriterion("conpon_status_msg not like", value, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgIn(List<String> values) {
            addCriterion("conpon_status_msg in", values, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgNotIn(List<String> values) {
            addCriterion("conpon_status_msg not in", values, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgBetween(String value1, String value2) {
            addCriterion("conpon_status_msg between", value1, value2, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponStatusMsgNotBetween(String value1, String value2) {
            addCriterion("conpon_status_msg not between", value1, value2, "conponStatusMsg");
            return (Criteria) this;
        }

        public Criteria andConponAmountIsNull() {
            addCriterion("conpon_amount is null");
            return (Criteria) this;
        }

        public Criteria andConponAmountIsNotNull() {
            addCriterion("conpon_amount is not null");
            return (Criteria) this;
        }

        public Criteria andConponAmountEqualTo(Integer value) {
            addCriterion("conpon_amount =", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountNotEqualTo(Integer value) {
            addCriterion("conpon_amount <>", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountGreaterThan(Integer value) {
            addCriterion("conpon_amount >", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("conpon_amount >=", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountLessThan(Integer value) {
            addCriterion("conpon_amount <", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountLessThanOrEqualTo(Integer value) {
            addCriterion("conpon_amount <=", value, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountIn(List<Integer> values) {
            addCriterion("conpon_amount in", values, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountNotIn(List<Integer> values) {
            addCriterion("conpon_amount not in", values, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountBetween(Integer value1, Integer value2) {
            addCriterion("conpon_amount between", value1, value2, "conponAmount");
            return (Criteria) this;
        }

        public Criteria andConponAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("conpon_amount not between", value1, value2, "conponAmount");
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