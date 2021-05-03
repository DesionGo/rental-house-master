package com.rentalHouseClient.rhc.modules.sys.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/28 17:43
 */
@Data
public class IssueTypeDTO {

    /**
     * 类型名字
     */
    private String typeName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型
     */
    private Integer amount;
}
