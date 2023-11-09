package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DtkConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.FenciUtil;
import com.help.rebate.utils.PropertyValueResolver;
import com.help.rebate.utils.dtk.ApiClient;
import com.help.rebate.utils.dtk.SignMD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * 大淘客商品转链接
 * 参考文档 - https://www.dingdanxia.com/doc/3/8
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class DtkItemConverter {
    private static final Logger logger = LoggerFactory.getLogger(DtkItemConverter.class);

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 根据淘口令，转链接获取返利淘口令
     *
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @return
     */
    public JSONObject getPrivilegeTkl(String tkl, String relationId, String specialId, String externalId, String pubSite) {
        //优先直接转
        try {
            JSONObject twd2Twd = getTwd2Twd(tkl, relationId, specialId, externalId, pubSite);
            Object data = PropertyValueResolver.getProperty(twd2Twd, "data", false);
            if (data != null){
                return twd2Twd;
            }
            else {
                logger.error("直接解析淘口令失败(tkl2tkl):{}，失败提示:{}，将尝试间接解析联盟商品Id解析", tkl, PropertyValueResolver.getProperty(twd2Twd, "msg", false));
            }
        }
        catch (Throwable throwable) {
            logger.error("直接解析淘口令失败(tkl2tkl):{}，将尝试间接解析联盟商品Id解析", tkl, throwable);
        }

        //获取商品详情
        String stepName = "1-获取商品详情";
        try{
            //商品详情
            JSONObject orderDetailResponse = parseTaoKouLing(tkl);

            //2、提取商品title
            stepName = "2-提取商品title";
            JSONObject orderDetail = orderDetailResponse.getObject("data", JSONObject.class);
            JSONObject originInfo = orderDetail.getObject("originInfo", JSONObject.class);
            String goodsId = orderDetail.getString("goodsId");
            String sellerId = orderDetail.getString("sellerId");
            String itemTitle = originInfo.getString("title");
            String shopName = originInfo.getString("shopName");

            //3、模糊搜索
            stepName = "3-执行模糊搜索";
            String fenciKeyWords = FenciUtil.fenci(itemTitle.replaceAll("[【】]", " "));
            JSONObject fuzzyItemObject = getFuzzyItemList(fenciKeyWords, 1, 50);
            JSONArray fuzzyItemList = fuzzyItemObject.getObject("data", JSONObject.class).getJSONArray("list");

            //3.1
            if (fuzzyItemList == null || fuzzyItemList.size() == 0) {
                throw new RuntimeException("未找到匹配的商品！");
            }

            //4、匹配新商品ID
            stepName = "4-匹配获取新商品ID,模糊列表个数-" + fuzzyItemList.size();
            String targetGoodsId = null;
            for (int i = 0; i < fuzzyItemList.size(); i++) {
                JSONObject jsonObject = fuzzyItemList.getJSONObject(i);

                String tempItemTitle = jsonObject.getString("title");
                //这是联盟新ID
                String tempGoodsId = jsonObject.getString("goodsId");
                String tempSellerId = jsonObject.getString("sellerId");
                String tempShopName = jsonObject.getString("shopName");

                if (tempItemTitle.equals(itemTitle)) {
                    if (!EmptyUtils.isEmpty(sellerId) && !EmptyUtils.isEmpty(tempSellerId)) {
                        if (tempSellerId.equals(sellerId)) {
                            targetGoodsId = tempGoodsId;
                            break;
                        }
                    }
                    else if (!EmptyUtils.isEmpty(shopName) && !EmptyUtils.isEmpty(tempShopName)) {
                        if (tempShopName.equals(shopName)) {
                            targetGoodsId = tempGoodsId;
                            break;
                        }
                    }
                }
            }

            //4.1 没匹配到
            if (targetGoodsId == null) {
                throw new RuntimeException("无法匹配到指定商品！");
            }

            //5、根据新商品ID，转为淘口令
            stepName = "5-根据新商品ID，转为淘口令";
            JSONObject tklResponse = getTkl(targetGoodsId);
            if (tklResponse != null){
                Object data = PropertyValueResolver.getProperty(tklResponse, "data.title", false);
                if (data != null && data.equals("")){
                    tklResponse.getJSONObject("data").put("title", itemTitle);
                }
            }

            return tklResponse;
        }
        catch (Throwable throwable) {
            logger.error("间接解析淘口令失败（失败步骤点:{}）:{}", stepName, tkl, throwable);
            throw new RuntimeException("无法解析出该商品的返利淘口令！");
        }

    }

    /**
     * 根据淘口令，转链接获取返利淘口令
     *
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @return
     */
    public JSONObject getTwd2Twd(String tkl, String relationId, String specialId, String externalId, String pubSite) {
        //构建参数
        Map<String, Object> params = buildTkl2TklParams(tkl, specialId, externalId, pubSite);

        String result = prettyHttpService.get(DtkConfig.DTK_GET_TWD_2_TWD, params);
        return JSON.parseObject(String.valueOf(result));
    }

    private Map<String, Object> buildTkl2TklParams(String tkl, String specialId, String externalId, String pubSite) {
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("version", "v1.0.0");
        params.put("content", tkl);

        //推广位
        params.put("pid", pubSite);

        //关于ID的参数
        if (!EmptyUtils.isEmpty(specialId)) {
            params.put("special_id", specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            params.put("external_id", externalId);
        }

        params.put("sign", SignMD5Util.getSignStr(params, DtkConfig.dtkAppsecret));
        return params;
    }

    private Map<String, Object> buildParams(String tkl, String specialId, String externalId, String pubSite) {
        String goodsId = parseTkl(tkl).getJSONObject("data").getString("goodsId");
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("goodsId", goodsId);
        params.put("version", "v1.3.1");

        //推广位
        params.put("pid", pubSite);

        //关于ID的参数
        if (!EmptyUtils.isEmpty(specialId)) {
            params.put("specialId", specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            params.put("externalId", externalId);
        }

        params.put("sign", SignMD5Util.getSignStr(params, DtkConfig.dtkAppsecret));
        return params;
    }

    private Map<String, Object> buildParams(String tkl, String url, String specialId, String externalId, String pubSite) {
        String goodsId = parseTkl(tkl, url).getJSONObject("data").getString("goodsId");
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("goodsId", goodsId);
        params.put("version", "v1.3.1");

        //推广位
        params.put("pid", pubSite);

        //关于ID的参数
        if (!EmptyUtils.isEmpty(specialId)) {
            params.put("specialId", specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            params.put("externalId", externalId);
        }

        params.put("sign", SignMD5Util.getSignStr(params, DtkConfig.dtkAppsecret));
        return params;
    }

    private Map<String, Object> buildCommonParams(String version) {
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("version", version);
        return params;
    }

    /**
     * 根据淘口令，获取商品信息，包含商品ID
     * @param tkl
     * @return
     */
    public JSONObject parseTkl(String tkl) {
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("version", "v1.0.0");
        params.put("content", tkl);
        params.put("sign", SignMD5Util.getSignStr(params,DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(DtkConfig.DTK_PARSE_CONTENT２, params);
        //String result = prettyHttpService.get(DtkConfig.DTK_PARSE_CONTENT, params);
        return JSON.parseObject(String.valueOf(result));
    }

    /**
     * 根据淘口令，获取商品信息，包含商品ID
     * @param tkl
     * @return
     */
    public JSONObject parseTkl(String tkl, String url) {
        //基础参数
        Map<String,Object> params = new TreeMap<>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("content", tkl);
        params.put("version", "v1.0.0");
        params.put("sign", SignMD5Util.getSignStr(params,DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(url, params);
        //String result = prettyHttpService.get(DtkConfig.DTK_PARSE_CONTENT, params);
        return JSON.parseObject(String.valueOf(result));
    }

    /**
     * 解析淘口令，url，淘口令，等各种格式均可以
     * @param content
     * @return
     * @throws Exception
     */
    JSONObject parseTaoKouLing(String content) throws Exception {
        String url = DtkConfig.DTK_PARSE_CONTENT;

        Map<String, Object> paraMap = buildCommonParams("v1.0.0");
        paraMap.put("content", content);
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(url, paraMap);
        return JSON.parseObject(result);
    }

    JSONObject getFuzzyItemList(String title, int pageNo, int pageSize) throws Exception {
        //联盟搜索
        //String url = "https://openapi.dataoke.com/api/tb-service/get-tb-service";
        //超级搜索
        String url = "https://openapi.dataoke.com/api/goods/list-super-goods";

        Map<String, Object> paraMap = buildCommonParams("v1.3.0");
        paraMap.put("keyWords", title);
        paraMap.put("pageId", pageNo + "");
        paraMap.put("pageSize", pageSize + "");
        paraMap.put("type", "2");

        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(url, paraMap);
        return JSON.parseObject(result);
    }

    JSONObject getTkl(String goodsId) throws Exception {
        //高效转链
        String url = "https://openapi.dataoke.com/api/tb-service/get-privilege-link";

        Map<String, Object> paraMap = buildCommonParams("v1.3.1");
        paraMap.put("goodsId", goodsId);

        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(url, paraMap);
        return JSON.parseObject(result);
    }
}
