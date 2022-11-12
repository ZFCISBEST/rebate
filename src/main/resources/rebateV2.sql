use rebate;

# 1 淘宝用户表
CREATE TABLE `v2_taobao_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` varchar(45) NOT NULL COMMENT '开放ID，从其他对接系统获取到的数据',
  `open_name` varchar(45) DEFAULT NULL COMMENT '获取到的对接系统的用户名称',
  `external_id` varchar(45) DEFAULT NULL COMMENT '用户查询联盟的ID，关联到淘宝联盟内部的会员ID或者渠道ID的一个外部ID',
  `special_id` varchar(45) DEFAULT NULL COMMENT '如果做了关联，那么就是会员的ID',
  `relation_id` varchar(45) DEFAULT NULL COMMENT '如果做了代理，那么是渠道ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# 2 淘宝推广位表
CREATE TABLE `v2_taobao_pubsite_combination_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `pub_site_type` varchar(12) NOT NULL DEFAULT 'virtual' COMMENT '原tkl_type，推广位类型 - virtual（对应着普通推广）、relation（对应着渠道推广），目前仅支持一种virtual',
  `vip_id` varchar(18) NOT NULL DEFAULT 'virtualId' COMMENT '会员的ID，表示是谁在做的推广，默认是没有或者virtualId，当前仅支持relation，因为special只能自己买，没法做推广',
  `pub_site` varchar(64) NOT NULL COMMENT '推广位',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推广位组合表';


# 3 淘宝转链记录表
CREATE TABLE `v2_taobao_tkl_convert_history_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` varchar(32) NOT NULL COMMENT '开放ID，从其他对接系统获取到的数据',
  `pub_site_type` varchar(16) NOT NULL DEFAULT 'virtual' COMMENT '原tkl_type，推广位类型 - virtual（对应着普通推广）、relation（对应着渠道推广），目前仅支持一种virtual',
  `pubsite_combination` varchar(128) NOT NULL COMMENT '组合[类型|类型内容|推广位]，三位一体',
  `tkl` varchar(512) NOT NULL COMMENT '用户的淘口令',
  `new_tkl` varchar(512) NOT NULL COMMENT '转后的淘口令',
  `item_id` varchar(256) NOT NULL COMMENT '商品的ID',
  `attach_info` varchar(256) DEFAULT NULL COMMENT '生成时，使用的信息（如specialID，relationID，externalID）',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转链记录表';


# 4 淘宝订单同步点位表
CREATE TABLE `v2_taobao_sync_order_offset_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，唯一',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `start_time` datetime NOT NULL COMMENT '起始点位',
  `end_time` datetime NOT NULL COMMENT '结束点位',
  `step` int(11) NOT NULL COMMENT '步长，秒',
  `sync_time_type` tinyint(4) NOT NULL DEFAULT '4' COMMENT '如果是订单同步 - 1：创建时间,2:付款时间,3:结算时间,4:更新时间',
  `sync_order_type` int(11) NOT NULL DEFAULT '0' COMMENT '如果是订单同步，场景订单场景类型，1:常规订单，2:渠道订单，3:会员运营订单，0:都查',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='同步订单的偏移点位表';


# 5 淘宝订单表
CREATE TABLE `v2_taobao_order_detail_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trade_id` varchar(21) DEFAULT NULL COMMENT '订单号',
  `trade_parent_id` varchar(21) DEFAULT NULL COMMENT '父订单号',
  `click_time` datetime DEFAULT NULL COMMENT '通过推广链接达到商品、店铺详情页的点击时间',
  `tk_create_time` datetime DEFAULT NULL COMMENT '订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间',
  `tb_paid_time` datetime DEFAULT NULL COMMENT '订单在拍下付款的时间',
  `tk_paid_time` datetime DEFAULT NULL COMMENT '订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间',
  `alipay_total_price` varchar(16) DEFAULT NULL COMMENT '买家拍下付款的金额（不包含运费金额）',
  `pay_price` varchar(16) DEFAULT NULL COMMENT '买家确认收货的付款金额（不包含运费金额）',
  `modified_time` varchar(22) DEFAULT NULL COMMENT '订单更新时间',
  `tk_status` int(11) DEFAULT NULL COMMENT '已付款：指订单已付款，但还未确认收货 已收货：指订单已确认收货，但商家佣金未支付 已结算：指订单已确认收货，且商家佣金已支付成功 已失效：指订单关闭/订单佣金小于0.01元，订单关闭主要有：1）买家超时未付款； 2）买家付款前，买家/卖家取消了订单；3）订单付款后发起售中退款成功；3：订单结算，12：订单付款， 13：订单失效，14：订单成功。淘客订单状态，12-付款，13-关闭，14-确认收货，3-结算成功;不传，表示所有状态',
  `order_type` varchar(16) DEFAULT NULL COMMENT '订单所属平台类型，包括天猫、淘宝、聚划算等',
  `refund_tag` int(11) DEFAULT '0' COMMENT '维权标签，0 含义为非维权 1 含义为维权订单',
  `flow_source` varchar(16) DEFAULT NULL COMMENT '产品类型',
  `terminal_type` varchar(16) DEFAULT NULL COMMENT '成交平台',
  `tk_earning_time` datetime DEFAULT NULL COMMENT '订单确认收货后且商家完成佣金支付的时间',
  `tk_order_role` int(11) DEFAULT '2' COMMENT '二方：佣金收益的第一归属者； 三方：从其他淘宝客佣金中进行分成的推广者',
  `total_commission_rate` varchar(8) DEFAULT NULL COMMENT '佣金比率 - 30',
  `income_rate` varchar(8) DEFAULT NULL COMMENT '收入比率。订单结算的佣金比率+平台的补贴比率',
  `pub_share_rate` varchar(16) DEFAULT NULL COMMENT '分成比率。从结算佣金中分得的收益比率',
  `tk_total_rate` varchar(16) DEFAULT NULL COMMENT '提成率=收入比率*分成比率。指实际获得收益的比率。total_commission_rate * pub_share_rate',
  `total_commission_fee` varchar(16) DEFAULT NULL COMMENT '佣金金额=结算金额*佣金比率',
  `pub_share_pre_fee` varchar(16) DEFAULT NULL COMMENT '付款预估收入=付款金额*提成。指买家付款金额为基数，预估您可能获得的收入。因买家退款等原因，可能与结算预估收入不一致',
  `pub_share_fee` varchar(16) DEFAULT NULL COMMENT '结算预估收入=结算金额*提成。以买家确认收货的付款金额为基数，预估您可能获得的收入。因买家退款、您违规推广等原因，可能与您最终收入不一致。最终收入以月结后您实际收到的为准',
  `alimama_rate` varchar(8) DEFAULT NULL COMMENT '推广者赚取佣金后支付给阿里妈妈的技术服务费用的比率',
  `alimama_share_fee` varchar(16) DEFAULT NULL COMMENT '技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用',
  `subsidy_rate` varchar(8) DEFAULT NULL COMMENT '平台给与的补贴比率，如天猫、淘宝、聚划算等',
  `subsidy_fee` varchar(16) DEFAULT NULL COMMENT '补贴金额=结算金额*补贴比率',
  `subsidy_type` varchar(16) DEFAULT NULL COMMENT '平台出资方，如天猫、淘宝、或聚划算等',
  `tk_commission_pre_fee_for_media_platform` varchar(16) DEFAULT NULL COMMENT '预估内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝付款金额＊专项服务费率。',
  `tk_commission_fee_for_media_platform` varchar(16) DEFAULT NULL COMMENT '结算内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝结算金额＊专项服务费率。',
  `tk_commission_rate_for_media_platform` varchar(16) DEFAULT NULL COMMENT '内容专项服务费率：内容场景专项技术服务费率，内容推广者在内容场景进行推广需要按结算金额支付一定比例给阿里妈妈作为内容场景专项技术服务费，用于提供与内容平台实现产品技术对接等服务。',
  `pub_id` varchar(12) DEFAULT NULL COMMENT '推广者的会员id',
  `site_id` varchar(12) DEFAULT NULL COMMENT '媒体管理下的ID，同时也是pid=mm_1_2_3中的“2”这段数字',
  `adzone_id` varchar(12) DEFAULT NULL COMMENT '推广位管理下的推广位名称对应的ID，同时也是pid=mm_1_2_3中的“3”这段数字',
  `site_name` varchar(32) DEFAULT NULL COMMENT '媒体管理下的对应ID的自定义名称',
  `adzone_name` varchar(32) DEFAULT NULL COMMENT '推广位管理下的自定义推广位名称',
  `special_id` varchar(16) DEFAULT NULL COMMENT '会员运营ID（需要申请到私域会员权限才返回此ID）',
  `relation_id` varchar(16) DEFAULT NULL COMMENT '渠道关系ID会员运营ID（需要申请到渠道管理权限才返回此ID）',
  `item_category_name` varchar(32) DEFAULT NULL COMMENT '商品所属的根类目，即一级类目的名称',
  `seller_nick` varchar(32) DEFAULT NULL COMMENT '掌柜旺旺',
  `seller_shop_title` varchar(32) DEFAULT NULL COMMENT '店铺名称',
  `item_id` varchar(256) DEFAULT NULL COMMENT '商品id',
  `item_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `item_price` varchar(16) DEFAULT NULL COMMENT '商品单价',
  `item_link` varchar(256) DEFAULT NULL COMMENT '商品链接',
  `item_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `tk_deposit_time` datetime DEFAULT NULL COMMENT '预售时期，用户对预售商品支付定金的付款时间，可能略晚于在淘宝付定金时间',
  `tb_deposit_time` datetime DEFAULT NULL COMMENT '预售时期，用户对预售商品支付定金的付款时间',
  `deposit_price` varchar(16) DEFAULT NULL COMMENT '预售时期，用户对预售商品支付的定金金额',
  `alsc_id` varchar(21) DEFAULT NULL COMMENT '口碑子订单号',
  `alsc_pid` varchar(21) DEFAULT NULL COMMENT '口碑父订单号',
  `service_fee_dto_list` varchar(45) DEFAULT NULL COMMENT '服务费信息，本来是个数组，ServiceFeeDto[]，能存就存，不能存就截断。 share_relative_rate（专项服务费率），share_fee（结算专项服务费），share_pre_fee（预估专项服务费），tk_share_role_type（专项服务费来源，122-渠道）',
  `lx_rid` varchar(16) DEFAULT NULL COMMENT '激励池对应的rid',
  `is_lx` varchar(2) DEFAULT NULL COMMENT '订单是否为激励池订单 1: 表征是 。0: 表征否',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';


# 6 淘宝订单关联映射表
CREATE TABLE `v2_taobao_order_openid_map_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trade_id` varchar(32) NOT NULL COMMENT '订单号',
  `trade_parent_id` varchar(32) NOT NULL COMMENT '父单号',
  `open_id` varchar(32) NOT NULL COMMENT '关联的open id',
  `item_id` varchar(256) NOT NULL COMMENT '商品的ID',
  `pub_share_pre_fee` varchar(16) DEFAULT NULL COMMENT '付款预估收入=付款金额*提成。指买家付款金额为基数，预估您可能获得的收入。因买家退款等原因，可能与结算预估收入不一致',
  `pub_share_fee` varchar(16) DEFAULT NULL COMMENT '结算预估收入=结算金额*提成。以买家确认收货的付款金额为基数，预估您可能获得的收入。因买家退款、您违规推广等原因，可能与您最终收入不一致。最终收入以月结后您实际收到的为准',
  `alimama_share_fee` varchar(16) DEFAULT NULL COMMENT '技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用',
  `order_status` int(11) NOT NULL COMMENT '订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功',
  `commission_ratio` int(11) NOT NULL DEFAULT '1000' COMMENT '佣金比率，默认是1，就是全返。可以不用alimama_share_fee，整体用该字段，如0.85',
  `refund_tag` int(11) DEFAULT '0' COMMENT '维权标签，0 含义为非维权 1 含义为维权订单',
  `refund_fee` varchar(16) DEFAULT NULL COMMENT '维权以后，返回给商家的金额。此字段用于后期重新计算损益情况',
  `map_type_msg` varchar(45) NOT NULL COMMENT '映射类型信息，pubsite(推广位绑定), specialid(会员ID绑定), extend(扩展而来，通过parent订单扩展的其他购买商品，可能也转过码)，pubsite_specialid(既存在推广位，又有specialid是会员，此时可建立与opened的映射关系)',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1091 DEFAULT CHARSET=utf8 COMMENT='订单关联映射表';


# 7 淘宝订单映射失败表
CREATE TABLE `v2_taobao_order_openid_map_failure_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trade_id` varchar(32)  NOT NULL COMMENT '订单号',
  `trade_parent_id` varchar(32)  NOT NULL COMMENT '父单号',
  `item_id` varchar(256)  NOT NULL COMMENT '商品的ID',
  `fail_reason` varchar(256) DEFAULT NULL COMMENT '失败原因',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=490 DEFAULT CHARSET=utf8 COMMENT='订单关联映射失败表';


# 8 淘宝佣金比率表
CREATE TABLE `v2_taobao_commission_ratio_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` varchar(32) DEFAULT NULL COMMENT '外部ID，可以为空，表示默认值',
  `commission_ratio` int(11)  NOT NULL DEFAULT '1000' COMMENT '佣金比率，默认是1，就是全返',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户佣金比率表，一个用户同时只能使用一个费率（或者使用最新的）';


# 9 淘宝账户余额表
CREATE TABLE `v2_taobao_commission_account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` varchar(32) NOT NULL COMMENT '外部ID',
  `total_commission` decimal(13,2)  NOT NULL DEFAULT 0 COMMENT '全部返利',
  `remain_commission` decimal(13,2) NOT NULL DEFAULT 0 COMMENT '剩余返利，相当于账户余额',
  `frozen_commission` decimal(13,2) NOT NULL DEFAULT 0 COMMENT '被冻结返利，这部分钱不能提取',
  `finish_commission` decimal(13,2) NOT NULL DEFAULT 0 COMMENT '已结算的，相当于历史提现总额',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='佣金账户表';


# 10 淘宝账户流水信息表
CREATE TABLE `v2_taobao_commission_account_flow_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` varchar(32) NOT NULL COMMENT '外部ID',

  `total_commission` decimal(13,2)  NOT NULL COMMENT '事件发生时全部返利',
  `remain_commission` decimal(13,2)  NOT NULL COMMENT '事件发生时剩余返利，相当于账户余额',
  `frozen_commission` decimal(13,2)  NOT NULL COMMENT '事件发生时被冻结返利，这部分钱不能提取',

  `flow_amount` decimal(13,2)  NOT NULL DEFAULT 0 COMMENT '产生的账户流水金额',
  `flow_amount_type` tinyint(4) NOT NULL COMMENT '产生的账户流水金额类型：0-结算，1-维权退回，2-提现，3-冻结金额',
  `flow_amount_type_msg` varchar(16) DEFAULT NULL COMMENT '产生的账户流水金额类型说明',

 `commission_trade_id` varchar(21) DEFAULT NULL COMMENT '如果是返利结算，结算的交易单号',
 `refund_trade_id` varchar(21) DEFAULT NULL COMMENT '如果是维权，维权的交易单号',

  `account_flow_status` tinyint(4) NOT NULL COMMENT '0-成功、1-失败、2-进行中',
  `account_flow_status_msg` varchar(128) DEFAULT NULL COMMENT '状态信息，如提取失败，失败信息',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态字段，0表示不删除，1表示逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='淘宝返利账户流水表';

CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;



