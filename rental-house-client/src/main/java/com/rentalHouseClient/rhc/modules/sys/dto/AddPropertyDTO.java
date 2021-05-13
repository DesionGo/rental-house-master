package com.rentalHouseClient.rhc.modules.sys.dto;

import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO
 *
 * @author czf
 * @date 2021/5/11 9:34
 */
@Data
public class AddPropertyDTO {


    /**
     * 发布标题
     */
    private String headline;

    /**
     * 发布内容
     */
    private String content;

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
     * 创建者id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 发布类型
     */
    private Integer type;

    /**
     * 状态
     */
    private String status;

    /**
     * 文件路径
     */
    private List<String> url;

    /**
     * 标签
     */
    private List<String> label;
    /**
     * 详细地址
     */
    private String detailSite;


}
