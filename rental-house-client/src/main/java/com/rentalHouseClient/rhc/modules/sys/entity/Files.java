package com.rentalHouseClient.rhc.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rentalHouseClient.rhc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件上传
 * </p>
 * @since 2021-02-27 14:34:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_files")
public class Files extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * URL地址
     */
    private String url;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 
     */
    private String filePath;

    /**
     * 归属id
     */
    private String ascriptionId;

    /**
     * 归属名字
     */
    private String ascriptionName;

}
