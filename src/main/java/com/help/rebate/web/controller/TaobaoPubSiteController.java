package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfo;
import com.help.rebate.service.V2TaobaoPubSiteService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推广位组合操作API
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("推广位组合操作API")
@RestController
@RequestMapping("/tbk/pubSite")
public class TaobaoPubSiteController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoPubSiteController.class);

    /**
     * 推广位操作服务类
     */
    @Resource
    private V2TaobaoPubSiteService v2TaobaoPubSiteService;

    /**
     * 淘口令推广位组合的构建
     * @param pubSiteType 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual
     * @param vipIds 各种对应类型下的Id，或者virtualId、noneId
     * @param pubSites 各种site组合
     * @return
     */
    @ApiOperation("构建推广位组合")
    @RequestMapping("/combine")
    public SafeServiceResponse<String> combine(@ApiParam(name = "pubSiteType", value = "淘口令类型 渠道(relation)、默认为virtual") @RequestParam(required = false) String pubSiteType,
                                     @ApiParam(name = "vipIds", value = "对应类型下的Id") @RequestParam(required = false) String vipIds,
                                     @ApiParam(name = "pubSites", value = "对应类型下可用的所有推广位") @RequestParam(required = false) String pubSites) {
        try{
            SafeServiceResponse.startBiz();

            //为空，默认为 虚拟-virtual
            if (EmptyUtils.isEmpty(pubSiteType)) {
                pubSiteType = "virtual";
            }

            //校验
            Checks.isNotEmpty(vipIds, "vipIds不能为空");
            Checks.isNotEmpty(pubSites, "pubSites不能为空");

            //插入
            int affectedCnt = v2TaobaoPubSiteService.buildOrDoNone(pubSiteType, vipIds, pubSites);

            //返回
            return SafeServiceResponse.success("成功插入条数 - " + affectedCnt);
        }catch(Exception e){
            logger.error("fail to build pubSites[/tbk/pubSite/build]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    /**
     * 列出所有的推广位组合
     * @param pubSiteType 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual
     * @param vipIds 各种对应类型下的Id，或者virtualId、noneId
     * @return
     */
    @ApiOperation("列出所有的推广位组合")
    @RequestMapping("/listAll")
    public SafeServiceResponse<List<V2TaobaoPubsiteCombinationInfo>> listAll(@ApiParam(name = "pubSiteType", value = "淘口令类型 渠道(relation)、虚拟(virtual)，默认为空，差全部") @RequestParam(required = false) String pubSiteType,
                                                                       @ApiParam(name = "vipIds", value = "对应类型下的Id，默认为空，查全部") @RequestParam(required = false) String vipIds) {
        try{
            SafeServiceResponse.startBiz();

            //为空，默认为 null
            if (EmptyUtils.isEmpty(pubSiteType)) {
                pubSiteType = null;
            }

            //为空，默认为 null
            if (EmptyUtils.isEmpty(vipIds)) {
                vipIds = null;
            }

            //插入
            List<V2TaobaoPubsiteCombinationInfo> pubSiteCombinations = v2TaobaoPubSiteService.listAll(pubSiteType, vipIds);

            //返回
            return SafeServiceResponse.success(pubSiteCombinations);
        }catch(Exception e){
            logger.error("fail to list all pubsites[/tbk/pubSite/listAll]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    /**
     * 删除一个推广位
     * @param ids
     * @return
     */
    @ApiOperation("删除一个推广位")
    @RequestMapping("/deletePubSite")
    public SafeServiceResponse<String> deletePubSite(@ApiParam(name = "ids", value = "逐渐ID列表，逗号隔开") @RequestParam String ids) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            int affectedCnt = v2TaobaoPubSiteService.deletePubSite(ids);

            //返回
            return SafeServiceResponse.success("删除条数 - " + affectedCnt);
        }catch(Exception e){
            logger.error("fail to list all pubsites[/tbk/pubSite/deletePubSite]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
