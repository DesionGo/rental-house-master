package com.rentalHouseClient.rhc.modules.sys.dto;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/15 10:34
 */
@Data
public class IndexDTO {
    /**
     * 账户名称
     */
    public String userName;

    /**
     * 城市
     */
    public List<CityDTO> city;

    /**
     * 发布信息
     */
    public List<IssueDTO> issue;
}
