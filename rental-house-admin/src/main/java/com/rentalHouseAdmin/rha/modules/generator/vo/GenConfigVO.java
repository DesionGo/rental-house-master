package com.rentalHouseAdmin.rha.modules.generator.vo;

import com.rentalHouseAdmin.rha.modules.generator.dto.*;
import com.rentalHouseAdmin.rha.modules.generator.dto.ColumnConfigDTO;
import com.rentalHouseAdmin.rha.modules.generator.dto.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 代码生成核心配置封装类
 * @author Kalvin
 * @Date 2019/06/13 10:39
 */
@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class GenConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableName;
    private String tableComment;
    private String primaryKey;  // 主键字段
    private String pkCamelCase;  // 主键字段(驼峰)
    private String firstCapPk;  // 主键字段(驼峰)(首字母大写)
    private String moduleName;   // 模块名称：如sys
    private String funName;     // 功能名称：如user
    private String firstCapFunName;     // 功能名称(首字母大写)：如User
    private String tableType;   // 类型：table、treegrid
    private List<ColumnConfigDTO> columns;
    private List<com.rentalHouseAdmin.rha.modules.generator.dto.QueryColumnConfigDTO> queryColumns;
    private List<com.rentalHouseAdmin.rha.modules.generator.dto.ButtonConfigDTO> headButtons;
    private List<com.rentalHouseAdmin.rha.modules.generator.dto.ButtonConfigDTO> rowButtons;
    private List<com.rentalHouseAdmin.rha.modules.generator.dto.ColumnsValueRelationDTO> columnsValueRelations;    // 列值对应说明关系列表数据
    private List<com.rentalHouseAdmin.rha.modules.generator.dto.TableColumnDTO> allColumns;    // 表的所有列数据
    private Set<String> pkgs;   // 实体类所需要导入的java类型包集合

    public GenConfigVO(String tableName, String tableType, String tableComment) {
        this.tableName = tableName;
        this.tableType = tableType;
        this.tableComment = tableComment;
    }
}
