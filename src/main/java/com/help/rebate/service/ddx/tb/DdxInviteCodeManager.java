package com.help.rebate.service.ddx.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 私域会员 & 渠道会员管理
 * 1、邀请码生成
 * 2、会员备案（这个暂时没法做）
 * 3、会员信息查询 参考文档 - https://www.dingdanxia.com/doc/19/27
 * @author zfcisbest
 * @date 21/11/14
 */
@Deprecated
@Service
public class DdxInviteCodeManager {
    private static final Logger logger = LoggerFactory.getLogger(DdxInviteCodeManager.class);

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 获取会员信息
     * @param relationId
     * @param specialId
     * @param externalId
     * @param infoType
     * @param pageNo
     * @param pageSIze
     * @return
     */
    public JSONObject getInviterCodeManager(String relationId, String specialId, String externalId,
                                            PublisherInfoType infoType, int pageNo, int pageSIze) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("info_type", infoType.getInfoType());
        params.put("relation_app", "common");
        params.put("page_no", pageNo);
        params.put("page_size", pageSIze);

        //关于ID的参数
        if (!EmptyUtils.isEmpty(relationId)) {
            params.put("relation_id", relationId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            params.put("special_id", specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            params.put("external_id", externalId);
        }

        String result = prettyHttpService.get(DdxConfig.TB_PUBLISHER_URL, params);
        return JSON.parseObject(result);
    }

    /**
     * 查询的信息类型
     */
    public enum PublisherInfoType {
        relationId(1),
        specialId(2);

        private int infoType;

        PublisherInfoType(int infoType) {
            this.infoType = infoType;
        }

        public int getInfoType() {
            return infoType;
        }
    }
}
