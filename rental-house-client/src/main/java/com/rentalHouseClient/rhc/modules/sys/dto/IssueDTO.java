package com.rentalHouseClient.rhc.modules.sys.dto;

import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/15 10:47
 */
@Data
public class IssueDTO {

    /**
     *
     */

    private String id;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布标题
     */
    private String headline;

    /**
     * 金额
     */
    private Integer money;

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
     * 创建者名字
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 标签
     */
    private List<Label> label;

    /**
     * 图片
     */
    private List<Files> files;

    /**
     * 状态
     */
    private String status;
}
