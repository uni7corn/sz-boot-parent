package com.sz.admin.teacher.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.admin.teacher.mapper.TeacherStatisticsMapper;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsCreateDTO;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsImportDTO;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsListDTO;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsUpdateDTO;
import com.sz.admin.teacher.pojo.po.TeacherStatistics;
import com.sz.admin.teacher.pojo.vo.TeacherStatisticsVO;
import com.sz.admin.teacher.service.TeacherStatisticsService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.core.util.Utils;
import com.sz.excel.core.ExcelResult;
import com.sz.excel.utils.ExcelUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 教师统计总览表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-02-19
 */
@Service
@RequiredArgsConstructor
public class TeacherStatisticsServiceImpl extends ServiceImpl<TeacherStatisticsMapper, TeacherStatistics> implements TeacherStatisticsService {

    @Override
    public void create(TeacherStatisticsCreateDTO dto) {
        TeacherStatistics teacherStatistics = BeanCopyUtils.springCopy(dto, TeacherStatistics.class);
        // 唯一性校验
        save(teacherStatistics);
    }

    @Override
    public void update(TeacherStatisticsUpdateDTO dto) {
        TeacherStatistics teacherStatistics = BeanCopyUtils.springCopy(dto, TeacherStatistics.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
                .eq(TeacherStatistics::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);
        // 唯一性校验
        saveOrUpdate(teacherStatistics);
    }

    @Override
    public PageResult<TeacherStatisticsVO> page(TeacherStatisticsListDTO dto) {
        Page<TeacherStatisticsVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), TeacherStatisticsVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<TeacherStatisticsVO> list(TeacherStatisticsListDTO dto) {
        return listAs(buildQueryWrapper(dto), TeacherStatisticsVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto) {
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeById((Serializable) dto.getIds());
    }

    @Override
    public TeacherStatisticsVO detail(Object id) {
        TeacherStatistics teacherStatistics = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(teacherStatistics);
        return BeanCopyUtils.springCopy(teacherStatistics, TeacherStatisticsVO.class);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile file) {
        ExcelResult<TeacherStatisticsImportDTO> excelResult = ExcelUtils.importExcel(file.getInputStream(), TeacherStatisticsImportDTO.class, true);
        List<TeacherStatisticsImportDTO> list = excelResult.getList();
        List<String> errorList = excelResult.getErrorList();
        String analysis = excelResult.getAnalysis();
        System.out.println(" analysis : " + analysis);
    }

    @SneakyThrows
    @Override
    public void exportExcel(TeacherStatisticsListDTO dto, HttpServletResponse response) {
        List<TeacherStatisticsVO> list = list(dto);
        ServletOutputStream os = response.getOutputStream();
        ExcelUtils.exportExcel(list, "教师统计", TeacherStatisticsVO.class, os, true);
    }

    private static QueryWrapper buildQueryWrapper(TeacherStatisticsListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(TeacherStatistics.class);
        if (Utils.isNotNull(dto.getYear())) {
            wrapper.eq(TeacherStatistics::getYear, dto.getYear());
        }

        if (Utils.isNotNull(dto.getMonth())) {
            wrapper.eq(TeacherStatistics::getMonth, dto.getMonth());
        }

        if (Utils.isNotNull(dto.getDuringTime())) {
            wrapper.eq(TeacherStatistics::getDuringTime, dto.getDuringTime());
        }

        if (Utils.isNotNull(dto.getTeacherId())) {
            wrapper.eq(TeacherStatistics::getTeacherId, dto.getTeacherId());
        }

        if (Utils.isNotNull(dto.getTeacherCommonType())) {
            wrapper.eq(TeacherStatistics::getTeacherCommonType, dto.getTeacherCommonType());
        }

        if (Utils.isNotNull(dto.getTotalTeaching())) {
            wrapper.eq(TeacherStatistics::getTotalTeaching, dto.getTotalTeaching());
        }

        if (Utils.isNotNull(dto.getTotalClassCount())) {
            wrapper.eq(TeacherStatistics::getTotalClassCount, dto.getTotalClassCount());
        }

        if (Utils.isNotNull(dto.getTotalHours())) {
            wrapper.eq(TeacherStatistics::getTotalHours, dto.getTotalHours());
        }

        if (Utils.isNotNull(dto.getCheckStatus())) {
            wrapper.eq(TeacherStatistics::getCheckStatus, dto.getCheckStatus());
        }

        if (Utils.isNotNull(dto.getCheckTime())) {
            wrapper.eq(TeacherStatistics::getCheckTime, dto.getCheckTime());
        }

        if (Utils.isNotNull(dto.getLastSyncTime())) {
            wrapper.eq(TeacherStatistics::getLastSyncTime, dto.getLastSyncTime());
        }

        return wrapper;
    }

}