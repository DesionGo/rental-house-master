package com.rentalHouseClient.rhc.modules.sys.dto;

import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/28 17:40
 */
@Data
public class IssueIndexDTO {

    private  String userName;

    private ClientUser clientUser;

    //单子详情
    private IssueDTO issueDTO;

    //多个单子
    private List<IssueDTO> issue;

    //左边城市所对应的数量
    private List<CityDTO> city;

    //对租的方式的单子进行计算数量
    private List<IssueTypeDTO> type;

    //人气单子
    private List<IssueDTO> popularityIssue;
    //标签
    private  List<Label> labelList;

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 数据条
     */
    private Integer sum;
}
