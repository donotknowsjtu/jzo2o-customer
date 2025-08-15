package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.WorkerCertificationAuditResDTO;

import java.util.List;

public interface IWorkerCertificationAuditService extends IService<WorkerCertificationAudit> {

    /**
     * 分页查询服务人员认证审核信息
     * @param reqDTO
     * @return
     */
    PageResult<WorkerCertificationAuditResDTO> queryCertificationAuditList(WorkerCertificationAuditPageQueryReqDTO reqDTO);

    /**
     * 审核服务人员认证
     * @param id
     * @param certificationStatus
     * @param rejectReason
     */
    void audit(Long id, Integer certificationStatus, String rejectReason);
}
