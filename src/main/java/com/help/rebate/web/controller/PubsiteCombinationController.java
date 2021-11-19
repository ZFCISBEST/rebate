package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.PubsiteCombination;
import com.help.rebate.service.PubsiteCombinationService;
import com.help.rebate.service.TklConvertService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推广位组合操作API
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("推广位组合操作API")
@RestController
@RequestMapping("/tbk/pubsite")
public class PubsiteCombinationController {
    private static final Logger logger = LoggerFactory.getLogger(PubsiteCombinationController.class);

    /**
     * 推广位操作服务类
     */
    @Resource
    private PubsiteCombinationService pubsiteCombinationService;

    /**
     * 淘口令推广位组合的构建
     * @param tklType 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual
     * @param vipIds 各种对应类型下的Id，或者virtualId、noneId
     * @param pubSites 各种site组合
     * @return
     */
    @ApiOperation("构建推广位组合")
    @RequestMapping("/build")
    public SafeServiceResponse build(@ApiParam(name = "tklType", value = "淘口令类型 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual") @RequestParam(required = false) String tklType,
                                     @ApiParam(name = "vipIds", value = "对应类型下的Id") @RequestParam(required = false) String vipIds,
                                     @ApiParam(name = "pubSites", value = "对应类型下可用的所有推广位") @RequestParam(required = false) String pubSites) {
        try{
            SafeServiceResponse.startBiz();

            //为空，默认为 虚拟-virtual
            if (EmptyUtils.isEmpty(tklType)) {
                tklType = "virtual";
            }

            //校验
            Checks.isNotEmpty(vipIds, "vipIds不能为空");
            Checks.isNotEmpty(pubSites, "pubSites不能为空");

            //插入
            int affectedCnt = pubsiteCombinationService.buildOrDoNone(tklType, vipIds, pubSites);

            //返回
            return SafeServiceResponse.success("成功插入条数 - " + affectedCnt);
        }catch(Exception e){
            logger.error("fail to build pubsites[/tbk/pubsite/build]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    /**
     * 列出所有的推广位组合
     * @param tklType 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual
     * @param vipIds 各种对应类型下的Id，或者virtualId、noneId
     * @return
     */
    @ApiOperation("构建推广位组合")
    @RequestMapping("/list/all")
    public SafeServiceResponse listAll(@ApiParam(name = "tklType", value = "淘口令类型 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为null") @RequestParam(required = false) String tklType,
                                       @ApiParam(name = "vipIds", value = "对应类型下的Id") @RequestParam(required = false) String vipIds) {
        try{
            SafeServiceResponse.startBiz();

            //为空，默认为 null
            if (EmptyUtils.isEmpty(tklType)) {
                tklType = null;
            }

            //为空，默认为 null
            if (EmptyUtils.isEmpty(vipIds)) {
                vipIds = null;
            }

            //插入
            List<PubsiteCombination> pubsiteCombinations = pubsiteCombinationService.listAll(tklType, vipIds);

            //重新包装
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("records", pubsiteCombinations);
            result.put("pubsite_count", pubsiteCombinations == null ? 0 : pubsiteCombinations.size());

            //返回
            return SafeServiceResponse.success(pubsiteCombinations);
        }catch(Exception e){
            logger.error("fail to list all pubsites[/tbk/pubsite/list/all]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
