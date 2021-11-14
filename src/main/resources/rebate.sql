CREATE SCHEMA `rebate` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `rebate`.`user_infos` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '自增',
  `gmt_created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` VARCHAR(45) NOT NULL COMMENT '开放ID，从其他对接系统获取到的数据',
  `open_name` VARCHAR(45) NULL COMMENT '获取到的对接系统的用户名称',
  `external_id` VARCHAR(45) NULL COMMENT '用户查询联盟的ID，关联到淘宝联盟内部的会员ID或者渠道ID的一个外部ID',
  `special_id` VARCHAR(45) NULL COMMENT '如果做了关联，那么就是会员的ID',
  `relation_id` VARCHAR(45) NULL COMMENT '如果做了代理，那么是渠道ID',
  `data_from` VARCHAR(45) NULL COMMENT '数据来源：淘宝联盟、京东、拼多多、饿了么、美团、唯品会等等',
  PRIMARY KEY (`id`));

ALTER TABLE `rebate`.`user_infos`
ADD COLUMN `status` INT NOT NULL DEFAULT 0 COMMENT '默认为0，表示不删除。-1表示删除' AFTER `data_from`;

ALTER TABLE `rebate`.`user_infos`
CHANGE COLUMN `open_id` `open_id` VARCHAR(45) NULL COMMENT '开放ID，从其他对接系统获取到的数据' ;


CREATE TABLE `rebate`.`pubsite_combination` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_created` DATETIME NULL COMMENT '创建时间',
  `gmt_modified` DATETIME NULL COMMENT '修改时间',
  `pubsite_combinationcol` VARCHAR(128) CHARACTER SET 'utf8' NOT NULL COMMENT '组合[类型|类型内容|推广位]，三位一体',
  `status` INT NOT NULL DEFAULT 0 COMMENT '0表示未删除，-1表示删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `pubsite_combinationcol_UNIQUE` (`pubsite_combinationcol` ASC) VISIBLE)
COMMENT = '推广位组合表';

ALTER TABLE `rebate`.`pubsite_combination`
CHANGE COLUMN `gmt_created` `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
CHANGE COLUMN `gmt_modified` `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间' ;

ALTER TABLE `rebate`.`pubsite_combination`
DROP COLUMN `pubsite_combinationcol`,
ADD COLUMN `tkl_type` VARCHAR(12) NULL COMMENT '口令类型 - special/relation/virtual/none' AFTER `gmt_modified`,
ADD COLUMN `vip_id` VARCHAR(18) NULL AFTER `tkl_type`,
ADD COLUMN `pub_site` VARCHAR(64) NULL AFTER `vip_id`,
DROP INDEX `pubsite_combinationcol_UNIQUE` ;
;


CREATE TABLE `rebate`.`tkl_convert_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` VARCHAR(45) NULL COMMENT '开放ID，从其他对接系统获取到的数据',
  `pubsite_combination` VARCHAR(128) NULL COMMENT '组合[类型|类型内容|推广位]，三位一体',
  `tkl` VARCHAR(128) NULL COMMENT '用户的淘口令',
  `new_tkl` VARCHAR(128) NULL COMMENT '转后的淘口令',
  `item_id` VARCHAR(16) NULL COMMENT '商品的ID',
  `attach_info` VARCHAR(45) NULL COMMENT '生成时，使用的信息（如specialID，relationID，externalID）',
  `expired` INT NULL DEFAULT 0 COMMENT '0表示未过期，-1表示过期',
  `status` INT NULL DEFAULT 0 COMMENT '0表示不删除，-1表示删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '转链记录表';

ALTER TABLE `rebate`.`tkl_convert_history`
ADD COLUMN `external_id` VARCHAR(32) NULL COMMENT '外部ID' AFTER `open_id`,
ADD COLUMN `tklType` VARCHAR(16) NULL COMMENT '淘口令类型 - 渠道（relation）、会员（special）、虚拟（virtual）、无（none）' AFTER `item_id`,
CHANGE COLUMN `open_id` `open_id` VARCHAR(32) NULL DEFAULT NULL COMMENT '开放ID，从其他对接系统获取到的数据' ;

ALTER TABLE `rebate`.`tkl_convert_history`
CHANGE COLUMN `tkl` `tkl` NVARCHAR(128) NULL DEFAULT NULL COMMENT '用户的淘口令' ,
CHANGE COLUMN `new_tkl` `new_tkl` NVARCHAR(128) NULL DEFAULT NULL COMMENT '转后的淘口令' ;



CREATE TABLE `rebate`.`order_openid_map` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `trade_id` VARCHAR(32) NULL COMMENT '订单号',
  `parent_trade_id` VARCHAR(32) NULL COMMENT '父单号',
  `open_id` VARCHAR(32) NULL COMMENT '关联的open id',
  `external_id` VARCHAR(32) NULL COMMENT '关联的外部ID，如果有的话',
  `special_id` VARCHAR(16) NULL COMMENT '关联的会员ID，如果有的话',
  `relation_id` VARCHAR(32) NULL COMMENT '关联的渠道ID，如果有的话（虚拟的也可以）',
  `item_id` VARCHAR(16) NULL COMMENT '商品的ID',
  `pre_commission_fee` VARCHAR(16) NULL COMMENT '预估返利费',
  `act_commission_fee` VARCHAR(16) NULL COMMENT '实际返利费',
  `order_status` VARCHAR(16) NULL COMMENT '订单状态 - 付款、已收货、已结算',
  `commission_status` VARCHAR(16) NULL COMMENT '待结算、已结算、已关闭',
  `status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '订单关联映射表';

CREATE TABLE `rebate`.`commission_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` VARCHAR(16) NULL COMMENT '外部ID',
  `total_commission` VARCHAR(16) NULL COMMENT '全部返利',
  `remain_commission` VARCHAR(16) NULL COMMENT '剩余返利',
  `finish_commission` VARCHAR(16) NULL COMMENT '已结算的',
  `account_type` VARCHAR(8) NULL COMMENT '账户类型 - 总、年、月、周',
  `status` INT NULL COMMENT '0表示未删除，-1表示删除',
  PRIMARY KEY (`id`))
COMMENT = '佣金计算表';

CREATE TABLE `rebate`.`commission_ratio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` VARCHAR(16) NULL COMMENT '外部ID',
  `external_id` VARCHAR(16) NULL COMMENT '外部关联的ID - 默认与open_id相等',
  `commission_ratio` INT NULL DEFAULT 1000 COMMENT '佣金比率，默认是1，就是全返',
  `status` INT NULL COMMENT '0表示不删除，-1表示删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '用户佣金比率表，一个用户同时只能使用一个费率（或者使用最新的）';

CREATE TABLE `rebate`.`pick_money_record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `open_id` VARCHAR(16) NULL COMMENT '外部ID',
  `external_id` VARCHAR(16) NULL COMMENT '关联的外部ID',
  `total_commission` VARCHAR(16) NULL COMMENT '提现时总额',
  `remain_commission` VARCHAR(16) NULL COMMENT '提现时剩余额度',
  `pre_pick_commission` VARCHAR(16) NULL COMMENT '预提现金额',
  `act_pick_commission` VARCHAR(16) NULL COMMENT '实际提取的金额',
  `pick_attach_info` VARCHAR(32) NULL COMMENT '附加信息，如满10元可提，提取成功，提取失败信息',
  `pick_status` VARCHAR(8) NULL COMMENT '预提取、提取中、提取失败、已提取',
  `status` INT NULL COMMENT '0表示不删除，-1表示删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '提现记录表';

CREATE TABLE `rebate`.`order_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,

  `trade_id` VARCHAR(21) NULL COMMENT '订单号',
  `parent_trade_id` VARCHAR(21) NULL COMMENT '父订单号',

  `click_time` DATETIME NULL COMMENT '通过推广链接达到商品、店铺详情页的点击时间',
  `tk_create_time` DATETIME NULL COMMENT '订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间',
  `tb_paid_time` DATETIME NULL COMMENT '订单在拍下付款的时间',
  `tk_paid_time` DATETIME NULL COMMENT '订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间',
  `alipay_total_price` VARCHAR(16) NULL COMMENT '买家拍下付款的金额（不包含运费金额）',
  `pay_price` VARCHAR(16) NULL COMMENT '买家确认收货的付款金额（不包含运费金额）',


  `modified_time` VARCHAR(22) NULL COMMENT '订单更新时间',
  `tk_status` INT NULL COMMENT '已付款：指订单已付款，但还未确认收货 已收货：指订单已确认收货，但商家佣金未支付 已结算：指订单已确认收货，且商家佣金已支付成功 已失效：指订单关闭/订单佣金小于0.01元，订单关闭主要有：1）买家超时未付款； 2）买家付款前，买家/卖家取消了订单；3）订单付款后发起售中退款成功；3：订单结算，12：订单付款， 13：订单失效，14：订单成功。淘客订单状态，12-付款，13-关闭，14-确认收货，3-结算成功;不传，表示所有状态',

  `order_type` VARCHAR(16) NULL COMMENT '订单所属平台类型，包括天猫、淘宝、聚划算等',
  `refund_tag` INT NULL DEFAULT 0 COMMENT '维权标签，0 含义为非维权 1 含义为维权订单',
  `flow_source` VARCHAR(16) NULL COMMENT '产品类型',
  `terminal_type` VARCHAR(16) NULL COMMENT '成交平台',

  `tk_earning_time` DATETIME NULL COMMENT '订单确认收货后且商家完成佣金支付的时间',
  `tk_order_role` INT NULL DEFAULT 2 COMMENT '二方：佣金收益的第一归属者； 三方：从其他淘宝客佣金中进行分成的推广者',

  `total_commission_rate` VARCHAR(8) NULL COMMENT '佣金比率 - 30',
  `income_rate` VARCHAR(8) NULL COMMENT '收入比率。订单结算的佣金比率+平台的补贴比率',
  `pub_share_rate` VARCHAR(16) NULL COMMENT '分成比率。从结算佣金中分得的收益比率',
  `tk_total_rate` VARCHAR(16) NULL COMMENT '提成率=收入比率*分成比率。指实际获得收益的比率。total_commission_rate * pub_share_rate',

  `total_commission_fee` VARCHAR(16) NULL COMMENT '佣金金额=结算金额*佣金比率',
  `pub_share_pre_fee` VARCHAR(16) NULL COMMENT '付款预估收入=付款金额*提成。指买家付款金额为基数，预估您可能获得的收入。因买家退款等原因，可能与结算预估收入不一致',
  `pub_share_fee` VARCHAR(16) NULL COMMENT '结算预估收入=结算金额*提成。以买家确认收货的付款金额为基数，预估您可能获得的收入。因买家退款、您违规推广等原因，可能与您最终收入不一致。最终收入以月结后您实际收到的为准',


  `alimama_rate` VARCHAR(8) NULL COMMENT '推广者赚取佣金后支付给阿里妈妈的技术服务费用的比率',
  `alimama_share_fee` VARCHAR(16) NULL COMMENT '技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用',

  `subsidy_rate` VARCHAR(8) NULL COMMENT '平台给与的补贴比率，如天猫、淘宝、聚划算等',
  `subsidy_fee` VARCHAR(16) NULL COMMENT '补贴金额=结算金额*补贴比率',
  `subsidy_type` VARCHAR(16) NULL COMMENT '平台出资方，如天猫、淘宝、或聚划算等',

  `tk_commission_pre_fee_for_media_platform` VARCHAR(16) NULL COMMENT '预估内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝付款金额＊专项服务费率。',
  `tk_commission_fee_for_media_platform` VARCHAR(16) NULL COMMENT '结算内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝结算金额＊专项服务费率。',
  `tk_commission_rate_for_media_platform` VARCHAR(16) NULL COMMENT '内容专项服务费率：内容场景专项技术服务费率，内容推广者在内容场景进行推广需要按结算金额支付一定比例给阿里妈妈作为内容场景专项技术服务费，用于提供与内容平台实现产品技术对接等服务。',

  `pub_id` VARCHAR(12) NULL COMMENT '推广者的会员id',
  `site_id` VARCHAR(12) NULL COMMENT '媒体管理下的ID，同时也是pid=mm_1_2_3中的“2”这段数字',
  `adzone_id` VARCHAR(12) NULL COMMENT '推广位管理下的推广位名称对应的ID，同时也是pid=mm_1_2_3中的“3”这段数字',
  `site_name` VARCHAR(16) NULL COMMENT '媒体管理下的对应ID的自定义名称',
  `adzone_name` VARCHAR(32) NULL COMMENT '推广位管理下的自定义推广位名称',

  `special_id` VARCHAR(16) NULL COMMENT '会员运营ID（需要申请到私域会员权限才返回此ID）',
  `relation_id` VARCHAR(16) NULL COMMENT '渠道关系ID会员运营ID（需要申请到渠道管理权限才返回此ID）',

  `item_category_name` VARCHAR(32) NULL COMMENT '商品所属的根类目，即一级类目的名称',
  `seller_nick` VARCHAR(16) NULL COMMENT '掌柜旺旺',
  `seller_shop_title` VARCHAR(32) NULL COMMENT '店铺名称',
  `item_id` VARCHAR(16) NULL COMMENT '商品id',
  `item_title` VARCHAR(32) NULL COMMENT '商品标题',
  `item_price` VARCHAR(16) NULL COMMENT '商品单价',
  `item_link` VARCHAR(32) NULL COMMENT '商品链接',
  `item_num` INT NULL COMMENT '商品数量',

  `tk_deposit_time` DATETIME NULL COMMENT '预售时期，用户对预售商品支付定金的付款时间，可能略晚于在淘宝付定金时间',
  `tb_deposit_time` DATETIME NULL COMMENT '预售时期，用户对预售商品支付定金的付款时间',
  `deposit_price` VARCHAR(16) NULL COMMENT '预售时期，用户对预售商品支付的定金金额',

  `alsc_id` VARCHAR(21) NULL COMMENT '口碑子订单号',
  `alsc_pid` VARCHAR(21) NULL COMMENT '口碑父订单号',

  `service_fee_dto_list` VARCHAR(45) NULL COMMENT '服务费信息，本来是个数组，ServiceFeeDto[]，能存就存，不能存就截断。 share_relative_rate（专项服务费率），share_fee（结算专项服务费），share_pre_fee（预估专项服务费），tk_share_role_type（专项服务费来源，122-渠道）',

  `lx_rid` VARCHAR(16) NULL COMMENT '激励池对应的rid',
  `is_lx` VARCHAR(2) NULL COMMENT '订单是否为激励池订单 1: 表征是 。0: 表征否',

  `status` VARCHAR(45) NULL COMMENT '0表示不删除，-1表示删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '订单详情表';

ALTER TABLE `rebate`.`order_detail`
CHANGE COLUMN `status` `status` INT NULL DEFAULT 0 COMMENT '0表示不删除，-1表示删除' ;
ALTER TABLE `rebate`.`order_detail`
CHANGE COLUMN `item_title` `item_title` VARCHAR(64) NULL DEFAULT NULL COMMENT '商品标题' ;

ALTER TABLE `rebate`.`order_detail`
CHANGE COLUMN `item_link` `item_link` VARCHAR(64) NULL DEFAULT NULL COMMENT '商品链接' ;
ALTER TABLE `rebate`.`order_detail`
CHANGE COLUMN `site_name` `site_name` VARCHAR(32) NULL DEFAULT NULL COMMENT '媒体管理下的对应ID的自定义名称' ;
ALTER TABLE `rebate`.`order_detail`
CHANGE COLUMN `seller_nick` `seller_nick` VARCHAR(32) NULL DEFAULT NULL COMMENT '掌柜旺旺' ;


CREATE TABLE `rebate`.`time_cursor_position` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键，自增，唯一',
  `gmt_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `start_time` DATETIME NULL COMMENT '起始点位',
  `end_time` DATETIME NULL COMMENT '结束点位',
  `step` INT NULL COMMENT '步长，秒',
  `time_type` INT NULL COMMENT '时间类型- 0订单同步、1订单关联、2订单结算',
  `status` INT NULL DEFAULT 0 COMMENT '0表示不删除，-1表示删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '同步时间点位表';

ALTER TABLE `rebate`.`time_cursor_position`
ADD COLUMN `sub_type` INT NULL DEFAULT 0 COMMENT '如果是订单同步，场景订单场景类型，1:常规订单，2:渠道订单，3:会员运营订单，0:都查' AFTER `step`,
CHANGE COLUMN `time_type` `time_type` INT(11) NULL DEFAULT NULL COMMENT '时间类型- 0订单同步、1订单关联、2订单结算、3其他。。。' ;

ALTER TABLE `rebate`.`time_cursor_position`
ADD COLUMN `query_type` INT NULL DEFAULT 2 COMMENT '如果是订单同步 - 1：创建时间,2:付款时间,3:结算时间,4:更新时间' AFTER `step`;;

CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
