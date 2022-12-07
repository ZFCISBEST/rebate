package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoUserInfoDao;
import com.help.rebate.dao.V2YljDetailFlowInfoDao;
import com.help.rebate.dao.V2YljDetailInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.model.V2YljDetailInfoDTO;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
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
    public int verifyYlj(Long id, Byte verifyStatus, String verifyStatusMsg) {
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

        return affectedCnt;
    }

    /**
     * 更新信息
     * 发送的金额
     * @return
     */
    public int sendConpon(Long id) {
        V2YljDetailInfo v2YljDetailInfo = v2YljDetailInfoDao.selectByPrimaryKey(id);
        if (v2YljDetailInfo == null || v2YljDetailInfo.getStatus() == 0) {
            return 0;
        }

        //判定
        if (v2YljDetailInfo.getConponStatus() != null && v2YljDetailInfo.getConponStatus() == 2) {
            return v2YljDetailInfo.getConponAmount();
        }

        //发送红包
        // TODO: 2022/12/5 获取结果
        //0-未发，1、发送中，2-发送成功，3、发送失败
        byte sendConponResult = 3;
        String sendConponResultMsg = "发送失败-尚未真正发送";

        //设置
        v2YljDetailInfo.setGmtModified(LocalDateTime.now());
        v2YljDetailInfo.setConponStatus(sendConponResult);
        v2YljDetailInfo.setConponStatusMsg(sendConponResultMsg);
        v2YljDetailInfo.setConponAmount(v2YljDetailInfo.getConponAmount());

        //更新
        int affectedCnt = v2YljDetailInfoDao.updateByPrimaryKeySelective(v2YljDetailInfo);

        //生成流水
        generateFlow((byte) 1, v2YljDetailInfo);

        //发送结果
        return sendConponResult == 2 ? v2YljDetailInfo.getConponAmount() : 0;
    }
}
