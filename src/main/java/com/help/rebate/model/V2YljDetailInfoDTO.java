package com.help.rebate.model;

import com.help.rebate.dao.entity.V2YljDetailInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class V2YljDetailInfoDTO extends V2YljDetailInfo implements Serializable {
    private Integer current;
    private Integer pageSize;
}