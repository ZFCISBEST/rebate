package com.help.rebate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.WxHttpService;
import com.help.rebate.dao.V2TaobaoUserInfoDao;
import com.help.rebate.dao.V2YljDetailFlowInfoDao;
import com.help.rebate.dao.V2YljDetailInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.model.V2YljDetailInfoDTO;
import com.help.rebate.service.wx.SendRedPackageService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.web.controller.wx.SignatureController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 养老金相关的服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2YljDetailInfoService {
    private static final Logger logger = LoggerFactory.getLogger(V2YljDetailInfoService.class);

    /**
     * 养老金操作
     */
    @Resource
    private V2YljDetailInfoDao v2YljDetailInfoDao;
    @Resource
    private V2YljDetailFlowInfoDao v2YljDetailFlowInfoDao;

    /**
     * 发红包服务
     */
    @Resource
    private SendRedPackageService sendRedPackageService;

    /**
     * 养老金开户状态推送
     */
    public static String pushMsgToUserTemplate = "{\n" +
            "    \"touser\": \"$openId\", \n" +
            "    \"template_id\": \"HxzwhA9W_XfsVDyqSSKZCXAIabGyAi3PLfHXsvRHFNA\", \n" +
            "    \"data\": {\n" +
            "        \"first\": {\n" +
            "            \"value\": \"$tellContent\", \n" +
            "            \"color\": \"#173177\"\n" +
            "        }, \n" +
            "        \"keyword1\": {\n" +
            "            \"value\": \"$审核状态\", \n" +
            "            \"color\": \"#173177\"\n" +
            "        }, \n" +
            "        \"keyword2\": {\n" +
            "            \"value\": \"$审核时间\", \n" +
            "            \"color\": \"#173177\"\n" +
            "        },\n" +
            "        \"remark\": {\n" +
            "            \"value\": \"$备注\", \n" +
            "            \"color\": \"#173177\"\n" +
            "        }\n" +
            "    }\n" +
            "}";

    /**
     * 插入一个新的记录
     * @param openId
     * @param picUrl
     * @return
     */
    public V2YljDetailInfo createNewItem(String openId, String picUrl) {
        //首先查看是否存在
        V2YljDetailInfoExample example = new V2YljDetailInfoExample();
        example.setLimit(1);
        example.setOrderByClause("gmt_modified desc");
        V2YljDetailInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOpenIdEqualTo(openId);
        List<V2YljDetailInfo> v2YljDetailInfos = v2YljDetailInfoDao.selectByExample(example);

        //如果为空，则插入
        if (v2YljDetailInfos == null || v2YljDetailInfos.isEmpty()) {
            //生成一个存根
            String stub =  "ylj_" + System.nanoTime();

            V2YljDetailInfo userInfo = new V2YljDetailInfo();
            userInfo.setGmtCreated(LocalDateTime.now());
            userInfo.setGmtModified(LocalDateTime.now());
            userInfo.setOpenId(openId);
            userInfo.setMediaPicUrl(picUrl);
            userInfo.setYljStubFlowId(stub);
            userInfo.setVerifyStatus((byte)0);
            userInfo.setVerifyStatusMsg("");
            userInfo.setConponStatus((byte)0);
            userInfo.setConponStatusMsg("");
            userInfo.setConponAmount(0);
            userInfo.setStatus((byte)0);

            int affectedCnt = v2YljDetailInfoDao.insertSelective(userInfo);
            //应该能插入成功
            Checks.isTrue(affectedCnt == 1, "插入失败，原因未知");

            //生成一个记录
            generateFlow((byte) 2, userInfo);

            return userInfo;
        }

        //返回原来的
        return v2YljDetailInfos.get(0);
    }

    /**
     * 生成流水
     * @param yljDetailInfo
     */
    private void generateFlow(Byte operationType, V2YljDetailInfo yljDetailInfo) {
        V2YljDetailFlowInfo v2YljDetailFlowInfo = new V2YljDetailFlowInfo();
        v2YljDetailFlowInfo.setGmtCreate(LocalDateTime.now());
        v2YljDetailFlowInfo.setGmtModified(LocalDateTime.now());
        v2YljDetailFlowInfo.setYljStubFlowId(yljDetailInfo.getYljStubFlowId());
        //新创建
        v2YljDetailFlowInfo.setOperationType(operationType);
        v2YljDetailFlowInfo.setVerifyStatus(yljDetailInfo.getVerifyStatus());
        v2YljDetailFlowInfo.setVerifyStatusMsg(yljDetailInfo.getVerifyStatusMsg());
        v2YljDetailFlowInfo.setConponStatus(yljDetailInfo.getConponStatus());
        v2YljDetailFlowInfo.setConponStatusMsg(yljDetailInfo.getConponStatusMsg());
        v2YljDetailFlowInfo.setConponAmount(yljDetailInfo.getConponAmount());
        v2YljDetailFlowInfo.setStatus((byte)0);

        //插入
        int affectedCnt = v2YljDetailFlowInfoDao.insertSelective(v2YljDetailFlowInfo);
        Checks.isTrue(affectedCnt == 1, "插入失败，原因未知");
    }

    /**
     * 列出所有的用户 - 反正也没有多少，查完
     * @return
     */
    public List<V2YljDetailInfo> listAll(V2YljDetailInfoDTO v2YljDetailInfoDTO,int current, int pageSize) {
        //查出所有没有被删除的
        V2YljDetailInfoExample example = new V2YljDetailInfoExample();
        example.setOrderByClause("gmt_modified desc");
        example.setOffset((current - 1) * pageSize + 0L);
        example.setLimit(pageSize);
        V2YljDetailInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);

        //添加其他查询条件
        addOtherQueryCondition(v2YljDetailInfoDTO, criteria);

        //查询
        List<V2YljDetailInfo> userInfos = v2YljDetailInfoDao.selectByExample(example);
        return userInfos;
    }

    /**
     * 列出所有的用户 - 反正也没有多少，查完
     * @return
     */
    public long countAll(V2YljDetailInfoDTO v2YljDetailInfoDTO,int current, int pageSize) {
        //查出所有没有被删除的
        V2YljDetailInfoExample example = new V2YljDetailInfoExample();
        V2YljDetailInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);

        //添加其他查询条件
        addOtherQueryCondition(v2YljDetailInfoDTO, criteria);

        //查询
        long count = v2YljDetailInfoDao.countByExample(example);
        return count;
    }

    /**
     * 添加其他查询条件
     * @param v2YljDetailInfoDTO
     * @param criteria
     */
    private void addOtherQueryCondition(V2YljDetailInfoDTO v2YljDetailInfoDTO, V2YljDetailInfoExample.Criteria criteria) {
        Long id = v2YljDetailInfoDTO.getId();
        String openId = v2YljDetailInfoDTO.getOpenId();
        String mediaPicUrl = v2YljDetailInfoDTO.getMediaPicUrl();
        String yljStubFlowId = v2YljDetailInfoDTO.getYljStubFlowId();
        Byte verifyStatus = v2YljDetailInfoDTO.getVerifyStatus();
        String verifyStatusMsg = v2YljDetailInfoDTO.getVerifyStatusMsg();
        Byte conponStatus = v2YljDetailInfoDTO.getConponStatus();
        String conponStatusMsg = v2YljDetailInfoDTO.getConponStatusMsg();

        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(mediaPicUrl)) {
            criteria.andMediaPicUrlEqualTo(mediaPicUrl);
        }
        if (!EmptyUtils.isEmpty(yljStubFlowId)) {
            criteria.andYljStubFlowIdEqualTo(yljStubFlowId);
        }
        if (verifyStatus != null) {
            criteria.andVerifyStatusEqualTo(verifyStatus);
        }
        if (!EmptyUtils.isEmpty(verifyStatusMsg)) {
            criteria.andVerifyStatusMsgEqualTo(verifyStatusMsg);
        }
        if (conponStatus != null) {
            criteria.andConponStatusEqualTo(conponStatus);
        }
        if (!EmptyUtils.isEmpty(conponStatusMsg)) {
            criteria.andConponStatusMsgEqualTo(conponStatusMsg);
        }
    }

    /**
     * 更新信息
     * @return
     */
    public int verifyYlj(Long id, Byte verifyStatus, String verifyStatusMsg, Byte pushMsgToUser) {
        V2YljDetailInfo v2YljDetailInfo = v2YljDetailInfoDao.selectByPrimaryKey(id);
        if (v2YljDetailInfo == null || v2YljDetailInfo.getStatus() != 0) {
            return 0;
        }

        //设置
        v2YljDetailInfo.setGmtModified(LocalDateTime.now());
        if (verifyStatus != null) {
            v2YljDetailInfo.setVerifyStatus(verifyStatus);
            v2YljDetailInfo.setVerifyStatusMsg(verifyStatusMsg);
        }

        //更新
        int affectedCnt = v2YljDetailInfoDao.updateByPrimaryKeySelective(v2YljDetailInfo);

        //生成流水
        generateFlow((byte) 0, v2YljDetailInfo);

        //最后推送状态
        if (pushMsgToUser != null && pushMsgToUser == 0) {
            String msg = startPushMsgToUser(v2YljDetailInfo);
            logger.info("[审核]主动推送结果: {}", msg);
        }

        return affectedCnt;
    }

    /**
     * 开始状态推送
     * @param v2YljDetailInfo
     */
    private String startPushMsgToUser(V2YljDetailInfo v2YljDetailInfo) {
        //发送的url
        String url = SignatureController.send_template_message.replace("ACCESS_TOKEN", SignatureController.accessToken());

        //定义内容
        Byte verifyStatus = v2YljDetailInfo.getVerifyStatus();
        String shortMsg = "审核通过";
        if (verifyStatus == 0) {
            shortMsg = "新建";
        }
        else if (verifyStatus == 1){
            shortMsg = "审核中";
        }
        else if (verifyStatus != 2){
            shortMsg = "审核未通过";
        }
        String message = pushMsgToUserTemplate.replace("$openId", v2YljDetailInfo.getOpenId()).replace("$tellContent", shortMsg);
        message = message.replace("$审核状态", v2YljDetailInfo.getVerifyStatusMsg())
                .replace("$审核时间", TimeUtil.formatLocalDate(v2YljDetailInfo.getGmtModified()))
                .replace("$备注", "审核未通过可重新提交审核");

        //发送
        JSONObject jsonObject = WxHttpService.httpsRequest(url, "POST", message);
        if (jsonObject == null) {
            return "消息发送失败，原因未知 - " + v2YljDetailInfo.getOpenId();
        }

        //带原因的结果
        if (0 != jsonObject.getInteger("errcode")) {
            return "消息发送失败(errCode: " + jsonObject.getInteger("errcode")  + ", errMsg: "+ jsonObject.getString("errmsg") + ") - " + v2YljDetailInfo.getOpenId();
        }
        return "消息推送成功 - " + v2YljDetailInfo.getOpenId();
    }

    /**
     * 更新信息
     * 发送的金额
     * @return
     */
    public synchronized int sendConpon(Long id, Integer conponAmount) {
        V2YljDetailInfo v2YljDetailInfo = v2YljDetailInfoDao.selectByPrimaryKey(id);

        //判定
        Checks.isTrue(v2YljDetailInfo != null && v2YljDetailInfo.getStatus() == 0, "该记录已不存在，不可发红包");
        Checks.isTrue(v2YljDetailInfo.getVerifyStatus() == 2, "审批不通过，不可发红包");
        Checks.isTrue(v2YljDetailInfo.getConponStatus() != 1, "红包已在发送中，不可重复发红包");
        Checks.isTrue(v2YljDetailInfo.getConponStatus() != 2, "红包已发，不可重复发红包");

        //判定
        if (v2YljDetailInfo.getConponStatus() != null && (v2YljDetailInfo.getConponStatus() == 2 && v2YljDetailInfo.getConponStatus() == 1)) {
            return v2YljDetailInfo.getConponAmount();
        }

        //发送红包
        conponAmount = 100 * conponAmount;

        //0-未发，1、发送中，2-发送成功，3、发送失败
        String sendResult = triggerSendConpon(conponAmount, v2YljDetailInfo.getOpenId());
        byte sendConponResult = 3;
        String sendConponResultMsg = "发送失败 - " + sendResult;
        if (sendResult.equals("success")) {
            sendConponResult = 2;
            sendConponResultMsg = "红包发送成功";

            //发送成功了，给用户一个推送
            //String msg = startPushMsgToUser(v2YljDetailInfo, sendConponResultMsg);
            //logger.info("[发红包]主动推送结果: {}", msg);
        }

        //设置
        v2YljDetailInfo.setGmtModified(LocalDateTime.now());
        v2YljDetailInfo.setConponStatus(sendConponResult);
        v2YljDetailInfo.setConponStatusMsg(sendConponResultMsg);
        v2YljDetailInfo.setConponAmount(conponAmount);

        //更新
        int affectedCnt = v2YljDetailInfoDao.updateByPrimaryKeySelective(v2YljDetailInfo);

        //生成流水
        generateFlow((byte) 1, v2YljDetailInfo);

        //发送结果
        return sendConponResult == 2 ? v2YljDetailInfo.getConponAmount() : 0;
    }

    /**
     * 触发发红包
     * 发个红包，分
     * @param openId
     * @param withdrawalAmount
     * @return
     */
    private String triggerSendConpon(Integer withdrawalAmount, String openId) {
        String replyMessage;
        System.out.println("==开发发红包==");

        try {
            //实际发红包
            SendRedPackageService.SendPackReturnMsgWrapper returnMsgWrapper = sendRedPackageService.sendRedPack2(openId, withdrawalAmount);
            String msg = JSON.toJSONString(returnMsgWrapper);
            if (returnMsgWrapper.judgeSuccessful()) {
                logger.info("发送红包给[{}]成功: {}", openId, msg);

                replyMessage = "success";
            }
            else {
                replyMessage = returnMsgWrapper.getReturn_msg();
                logger.info("发送红包给[{}]失败: {}", openId, msg);
                return replyMessage;
            }
        } catch (Exception e) {
            logger.info("发送红包给[{}]失败", openId, e);
            replyMessage = e.getMessage();
        }
        System.out.println("==发红包完成==");
        return replyMessage;
    }
}
