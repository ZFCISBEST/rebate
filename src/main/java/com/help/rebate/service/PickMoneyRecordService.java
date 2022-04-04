package com.help.rebate.service;

import com.help.rebate.dao.PickMoneyRecordDao;
import com.help.rebate.dao.entity.PickMoneyRecord;
import com.help.rebate.dao.entity.PickMoneyRecordExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 提现服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class PickMoneyRecordService {
    private static final Logger logger = LoggerFactory.getLogger(PickMoneyRecordService.class);

    /**
     * 提现记录dao
     */
    @Resource
    private PickMoneyRecordDao pickMoneyRecordDao;

    /**
     * 保存一个新对象
     * @param pickMoneyRecord
     * @return
     */
    public int save(PickMoneyRecord pickMoneyRecord) {
        int affectedCnt = pickMoneyRecordDao.insertSelective(pickMoneyRecord);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param pickMoneyRecord
     * @return
     */
    public int update(PickMoneyRecord pickMoneyRecord) {
        int affectedCnt = pickMoneyRecordDao.updateByPrimaryKeySelective(pickMoneyRecord);
        return affectedCnt;
    }

    /**
     * 记录提取钱的动作
     * @param openId
     * @param specialId
     * @param pubFee
     * @param pickStatus 其实，只能是 提取中
     * @return
     */
    @Transactional
    public Integer recordPickMoneyAction(String openId, String specialId, String pubFee, String pickStatus) {
        //先查询
        PickMoneyRecordExample example = new PickMoneyRecordExample();
        example.setLimit(1);
        PickMoneyRecordExample.Criteria criteria = example.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andExternalIdEqualTo(specialId);
        }
        //相同状态的是否存在
        if (!EmptyUtils.isEmpty(pickStatus)) {
            criteria.andPickStatusEqualTo(pickStatus);
        }
        criteria.andStatusEqualTo(0);
        List<PickMoneyRecord> pickMoneyRecords = pickMoneyRecordDao.selectByExample(example);
        if (!EmptyUtils.isEmpty(pickMoneyRecords)) {
            PickMoneyRecord record = pickMoneyRecords.get(0);

            //必须相同的金额才行
            Checks.isTrue(pubFee.equals(record.getPrePickCommission()), "已有提现记录，但是两次提现金额不一致，停止提现操作");

            //就当已经成功了
            return record.getId();
        }

        //插入操作
        PickMoneyRecord pickMoneyRecord = new PickMoneyRecord();
        pickMoneyRecord.setGmtCreated(new Date());
        pickMoneyRecord.setGmtModified(new Date());
        pickMoneyRecord.setOpenId(openId);
        pickMoneyRecord.setExternalId(specialId);
        pickMoneyRecord.setPrePickCommission(pubFee);
        pickMoneyRecord.setPickStatus(pickStatus);
        pickMoneyRecord.setStatus(0);
        int affected = pickMoneyRecordDao.insertSelective(pickMoneyRecord);
        Checks.isTrue(affected == 1, "插入失败");
        return pickMoneyRecord.getId();
    }

    /**
     * 查询提现记录
     * @param openId
     * @param specialId
     * @param pickStatus 指定的查询状态
     * @return
     */
    public PickMoneyRecord selectByStatus(String openId, String specialId, String pickStatus) {
        //先查询
        PickMoneyRecordExample example = new PickMoneyRecordExample();
        example.setLimit(2);
        PickMoneyRecordExample.Criteria criteria = example.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andExternalIdEqualTo(specialId);
        }
        //相同状态的是否存在
        if (!EmptyUtils.isEmpty(pickStatus)) {
            criteria.andPickStatusEqualTo(pickStatus);
        }
        criteria.andStatusEqualTo(0);
        List<PickMoneyRecord> pickMoneyRecords = pickMoneyRecordDao.selectByExample(example);
        Checks.isTrue(pickMoneyRecords != null, "提现中的状态的记录不存在");
        Checks.isTrue(pickMoneyRecords.size() == 1, "提现中的状态的记录超过1个");
        return pickMoneyRecords.get(0);
    }

    /**
     * 查询，指定状态的提现记录信息
     * @param openId
     * @param specialId
     * @param pickStatus
     * @return
     */
    public PickMoneyRecord selectPickMoneyAction(String openId, String specialId, String[] pickStatus) {
        //先查询
        PickMoneyRecordExample example = new PickMoneyRecordExample();
        example.setLimit(2);
        PickMoneyRecordExample.Criteria criteria = example.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andExternalIdEqualTo(specialId);
        }
        //相同状态的是否存在
        if (pickStatus != null) {
            criteria.andPickStatusIn(Arrays.asList(pickStatus));
        }
        criteria.andStatusEqualTo(0);
        List<PickMoneyRecord> pickMoneyRecords = pickMoneyRecordDao.selectByExample(example);
        Checks.isTrue(pickMoneyRecords != null, "提现中的状态的记录不存在");
        Checks.isTrue(pickMoneyRecords.size() == 1, "提现中的状态的记录超过1个");
        return pickMoneyRecords.get(0);
    }
}
