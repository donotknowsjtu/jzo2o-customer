package com.jzo2o.customer.service.impl;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.mapper.WorkerCertificationAuditMapper;
import com.jzo2o.customer.model.domain.WorkerCertification;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.WorkerCertificationAuditResDTO;
import com.jzo2o.customer.service.IWorkerCertificationAuditService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import com.jzo2o.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务人员认证审核服务实现类
 *
 * @author donot_know
 * @create 2025/8/12 16:26
 */
@Service
public class WorkerCertificationAuditServiceImpl extends ServiceImpl<WorkerCertificationAuditMapper, WorkerCertificationAudit> implements IWorkerCertificationAuditService {
//    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkerCertificationAuditServiceImpl.class);
    /**
     * 分页查询服务人员认证审核信息
     * @param reqDTO
     * @return
     */
   public PageResult<WorkerCertificationAuditResDTO> queryCertificationAuditList(WorkerCertificationAuditPageQueryReqDTO reqDTO) {
//       // 调试日志：打印请求参数
//       log.info("查询条件参数 - certificationStatus: {}, auditStatus: {}, name: {}, idCardNo: {}",
//               reqDTO.getCertificationStatus(), reqDTO.getAuditStatus(), reqDTO.getName(), reqDTO.getIdCardNo());
//
       Page<WorkerCertificationAudit> workerCertificationAuditResPage = PageUtils.parsePageQuery(reqDTO, WorkerCertificationAudit.class);
       QueryWrapper<WorkerCertificationAudit> queryWrapper = new QueryWrapper<>();
       if (reqDTO.getAuditStatus() != null) {
           queryWrapper.eq("audit_status", reqDTO.getAuditStatus());
//           log.info("添加 auditStatus 条件: {}", reqDTO.getAuditStatus());
       }
       if (reqDTO.getCertificationStatus() != null) {
           queryWrapper.eq("certification_status", reqDTO.getCertificationStatus());
//           log.info("添加 certificationStatus 条件: {}", reqDTO.getCertificationStatus());
       }
       if (reqDTO.getIdCardNo() != null && !reqDTO.getIdCardNo().isEmpty()) {
           queryWrapper.eq("id_card_no", reqDTO.getIdCardNo());
//           log.info("添加 idCardNo 条件: {}", reqDTO.getIdCardNo());
       }
       if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
           queryWrapper.like("name", reqDTO.getName());
//           log.info("添加 name 条件: {}", reqDTO.getName());
       }
       if (reqDTO.getOrderBy1() != null && !reqDTO.getOrderBy1().isEmpty()) {
           queryWrapper.orderBy(true, reqDTO.getIsAsc1() != null ? reqDTO.getIsAsc1() : true, reqDTO.getOrderBy1());
//           log.info("添加 orderBy1 条件: {}, isAsc: {}", reqDTO.getOrderBy1(), reqDTO.getIsAsc1());
       }
       if (reqDTO.getOrderBy2() != null && !reqDTO.getOrderBy2().isEmpty()) {
           queryWrapper.orderBy(true, reqDTO.getIsAsc2() != null ? reqDTO.getIsAsc2() : true, reqDTO.getOrderBy2());
//           log.info("添加 orderBy2 条件: {}, isAsc: {}", reqDTO.getOrderBy2(), reqDTO.getIsAsc2());
       }
       
//       // 调试日志：打印最终查询条件
//       log.info("最终查询SQL条件: {}", queryWrapper.getCustomSqlSegment());
//       log.info("排序条件: {}", queryWrapper.getExpression().getOrderBy());
//
       Page<WorkerCertificationAudit> workerCertificationAuditPage1 = baseMapper.selectPage(workerCertificationAuditResPage, queryWrapper);
//
//       // 调试日志：打印查询结果
//       log.info("查询结果总数: {}", workerCertificationAuditPage1.getTotal());
//       log.info("查询结果当前页大小: {}", workerCertificationAuditPage1.getSize());
//
       return PageUtils.toPage(workerCertificationAuditPage1, WorkerCertificationAuditResDTO.class);

   }
    /**
     * 审核服务人员认证
     * @param id
     * @param certificationStatus
     * @param rejectReason
     */
    public void audit(Long id, Integer certificationStatus, String rejectReason) {
        WorkerCertificationAudit workerCertificationAudit = baseMapper.selectById(id);
        if (workerCertificationAudit == null) {
            throw new IllegalArgumentException("服务人员认证审核信息不存在");
        }
        workerCertificationAudit.setCertificationStatus(certificationStatus);
        workerCertificationAudit.setRejectReason(rejectReason);
        workerCertificationAudit.setAuditTime(LocalDateTime.now());
        baseMapper.updateById(workerCertificationAudit);
    }
}
