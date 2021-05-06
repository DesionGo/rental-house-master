package com.rentalHouseClient.rhc.modules.sys.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author czf
 * @date 2021/5/6 22:01
 */
@Data
public class PropertiesGridScreenDTO {



    /**
     * 最小金额
     */
    private Integer moneyMin;
    /**
     * 最大金额
     */
    private Integer moneyMax;
    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县/区
     */
    private String county;

    /**
     * 房子类型
     */
    private String houseType;

    /**
     * 出租方式
     */
    private String rentOutType;
}
