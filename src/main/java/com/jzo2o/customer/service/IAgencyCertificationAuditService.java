package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.AgencyCertificationAuditResDTO;

public interface IAgencyCertificationAuditService extends IService<AgencyCertificationAudit> {


    PageResult<AgencyCertificationAuditResDTO> page(AgencyCertificationAuditPageQueryReqDTO reqDTO);

    void audit(Long id, Integer certificationStatus, String rejectReason);
}
