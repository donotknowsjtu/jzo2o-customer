package com.jzo2o.customer.controller.operation;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.WorkerCertificationAuditResDTO;
import com.jzo2o.customer.service.IWorkerCertificationAuditService;
import com.jzo2o.customer.service.impl.WorkerCertificationAuditServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/operation/worker-certification-audit")
@Api(tags = "运营端-服务端认证审核相关接口")
public class WorkerCertificationAuditController {
    @Resource
    private IWorkerCertificationAuditService workerCertificationAuditService;

//    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkerCertificationAuditServiceImpl.class);


    @GetMapping("/page")
    @ApiOperation("分页查询服务人员认证审核信息")
    public PageResult<WorkerCertificationAuditResDTO> page(
            @RequestParam(value = "auditStatus", required = false) Integer auditStatus,
            @RequestParam(value = "certificationStatus", required = false) Integer certificationStatus,
            @RequestParam(value = "idCardNo", required = false) String idCardNo,
            @RequestParam(value = "isAsc1", required = false, defaultValue = "true") Boolean isAsc1,
            @RequestParam(value = "isAsc2", required = false) Boolean isAsc2,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "orderBy1", required = false) String orderBy1,
            @RequestParam(value = "orderBy2", required = false) String orderBy2,

            @RequestParam(value = "pageNo", required = false ,defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
//        // 设置默认值
//        log.info("分页查询服务人员认证审核信息 - 参数: auditStatus={}, certificationStatus={}, idCardNo={}, isAsc1={}, isAsc2={}, name={}, orderBy1={}, orderBy2={}, pageNo={}, pageSize={}",
//                auditStatus, certificationStatus, idCardNo, isAsc1, isAsc2, name, orderBy1, orderBy2, pageNo, pageSize);

        WorkerCertificationAuditPageQueryReqDTO workerCertificationAuditPageQueryReqDTO = new WorkerCertificationAuditPageQueryReqDTO();
        workerCertificationAuditPageQueryReqDTO.setAuditStatus(auditStatus);
        workerCertificationAuditPageQueryReqDTO.setCertificationStatus(certificationStatus);
        workerCertificationAuditPageQueryReqDTO.setIdCardNo(idCardNo);
        workerCertificationAuditPageQueryReqDTO.setIsAsc1(isAsc1);
        workerCertificationAuditPageQueryReqDTO.setIsAsc2(isAsc2);
        workerCertificationAuditPageQueryReqDTO.setName(name);
       if ("createTime".equals(orderBy1)) {
           orderBy1 = "create_time";
       } else if ("updateTime".equals(orderBy1)) {
           orderBy1 = "update_time";
       }
        if ("createTime".equals(orderBy2)) {
            orderBy2 = "create_time";
        } else if ("updateTime".equals(orderBy2)) {
            orderBy2 = "update_time";
        }
        workerCertificationAuditPageQueryReqDTO.setOrderBy1(orderBy1);
        workerCertificationAuditPageQueryReqDTO.setOrderBy2(orderBy2);
        workerCertificationAuditPageQueryReqDTO.setPageNo(pageNo.longValue());
        workerCertificationAuditPageQueryReqDTO.setPageSize(pageSize.longValue());
        return workerCertificationAuditService.queryCertificationAuditList(workerCertificationAuditPageQueryReqDTO);
    }
    @PutMapping("/audit/{id}")
    @ApiOperation("审核服务人员认证信息")
    public void audit(@PathVariable("id") Long id, @RequestParam("certificationStatus") Integer certificationStatus, @RequestParam("rejectReason") String rejectReason) {
        workerCertificationAuditService.audit(id, certificationStatus, rejectReason);
    }

}
