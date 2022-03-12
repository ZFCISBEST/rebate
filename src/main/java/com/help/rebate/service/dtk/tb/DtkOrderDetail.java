package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DtkConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.utils.dtk.SignMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;

/**
 * 大淘客-淘系订单查询
 * @author hokxi_gyl
 * * @date 2022/3/12
 */

@Service
public class DtkOrderDetail {
    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    public JSONObject getOrderDetail(Date startTime,Date endTime,int queryType,int orderScene,String positionIndex,int pageNo,int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("version", "v1.0.0");
        params.put("startTime", TimeUtil.format(startTime));
        params.put("endTime", TimeUtil.format(endTime));
        params.put("queryType", queryType);
        params.put("orderScene", orderScene);

        if (positionIndex != null) {
            params.put("positionIndex", positionIndex);
        }
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);

        params.put("sign", SignMD5Util.getSignStr(params,DtkConfig.dtkAppsecret));
        //请求数据
//                    String response = prettyHttpService.get(DdxConfig.TB_TKL_ORDER_DETAILS_URL, params);
        String response = prettyHttpService.get(DtkConfig.DTK_TB_ORDER_DETAILS_URL, params);
        JSONObject jsonObject = JSON.parseObject(response);
        return jsonObject;
    }
}
