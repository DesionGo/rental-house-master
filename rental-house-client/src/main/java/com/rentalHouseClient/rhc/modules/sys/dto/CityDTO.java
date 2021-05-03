package com.rentalHouseClient.rhc.modules.sys.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/15 10:41
 */
@Data
public class CityDTO {
    public int amount;
    public String cityName;
    /**
     * 文件路径
     */
    private String url;
}
