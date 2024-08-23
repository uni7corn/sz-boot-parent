package com.sz.admin.teacher.pojo.vo;


import com.sz.excel.annotation.DictFormat;
import com.sz.excel.convert.ExcelDictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * <p>
 * TeacherStatistics查询返回
 * </p>
 *
 * @author sz-admin
 * @since 2024-06-19
 */
@Data
@Schema(description = "TeacherStatistics返回vo")
public class TeacherStatisticsVO {

    @ExcelIgnore
    @Schema(description =  "id")
    private Long id;

    @ExcelProperty(value = "统计年限")
    @Schema(description =  "统计年限")
    private String year;

    @ExcelProperty(value = "统计月份")
    @Schema(description =  "统计月份")
    private String month;

    @ExcelProperty(value = "统计年月")
    @Schema(description =  "统计年月")
    private String duringTime;

    @ExcelProperty(value = "教师id")
    @Schema(description =  "教师id")
    private String teacherId;

    @ExcelProperty(value = "讲师区分类型", converter = ExcelDictConvert.class)
    @DictFormat(dictType = "account_status")
    @Schema(description =  "讲师区分类型")
    private Integer teacherCommonType;

    @ExcelProperty(value = "授课总数")
    @Schema(description =  "授课总数")
    private Integer totalTeaching;

    @ExcelProperty(value = "服务班次数")
    @Schema(description =  "服务班次数")
    private Integer totalClassCount;

    @ExcelProperty(value = "课时总数")
    @Schema(description =  "课时总数")
    private BigDecimal totalHours;

    @ExcelProperty(value = "核对状态" ,converter = ExcelDictConvert.class)
    @Schema(description =  "核对状态")
    @DictFormat(dictType = "account_status")
    private Integer checkStatus;

    @ExcelProperty(value = "核对时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "核对时间")
    private LocalDateTime checkTime;

    @ExcelProperty(value = "最近一次同步时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "最近一次同步时间")
    private LocalDateTime lastSyncTime;

    @ExcelProperty(value = "备注")
    @Schema(description =  "备注")
    private String remark;

    @ExcelProperty(value = "创建人" ,converter = ExcelDictConvert.class)
    @Schema(description =  "创建人")
    @DictFormat(isUser = true)
    private String createId;


}