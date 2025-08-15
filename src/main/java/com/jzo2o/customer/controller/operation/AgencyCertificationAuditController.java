package com.jzo2o.customer.controller.operation;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.AgencyCertificationAuditResDTO;
import com.jzo2o.customer.service.IAgencyCertificationAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/operation/agency-certification-audit")
@Api(tags = "运营端 - 机构认证审核相关接口")
public class AgencyCertificationAuditController {

    @Resource
    private IAgencyCertificationAuditService agencyCertificationAuditService;

    @GetMapping("/page")
    @ApiOperation("分页查询机构认证审核信息")
    public PageResult<AgencyCertificationAuditResDTO> page(
            @RequestParam(value = "auditStatus", required = false) Integer auditStatus,
            @RequestParam(value = "certificationStatus", required = false) Integer certificationStatus,
            @RequestParam(value = "isAsc1", required = false) boolean isAsc1,
            @RequestParam(value = "isAsc2", required = false) boolean isAsc2,
            @RequestParam(value = "legalPersonName", required = false) String legalPersonName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "orderBy1", required = false) String orderBy1,
            @RequestParam(value = "orderBy2", required = false) String orderBy2,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ){
        AgencyCertificationAuditPageQueryReqDTO reqDTO = new AgencyCertificationAuditPageQueryReqDTO();
        reqDTO.setAuditStatus(auditStatus);
        reqDTO.setCertificationStatus(certificationStatus);
        reqDTO.setIsAsc1(isAsc1);
        reqDTO.setIsAsc2(isAsc2);
        reqDTO.setLegalPersonName(legalPersonName);
        reqDTO.setName(name);
       orderBy1 = "createTime".equals(orderBy1) ? "create_time" : "updateTime".equals(orderBy1) ? "update_time" : orderBy1;
        orderBy2 = "createTime".equals(orderBy2) ? "create_time" : "updateTime".equals(orderBy2) ? "update_time" : orderBy2;
        reqDTO.setOrderBy1(orderBy1);
        reqDTO.setOrderBy2(orderBy2);
        reqDTO.setPageNo(pageNo.longValue());
        reqDTO.setPageSize(pageSize.longValue());

        return agencyCertificationAuditService.page(reqDTO);

    }

    @PutMapping
    @RequestMapping("/audit/{id}")
    public void audit(@PathVariable(value = "id", required = true) Long id,
                      @RequestParam(value = "certificationStatus", required = true) Integer certificationStatus,
                      @RequestParam(value = "rejectReason", required = false) String rejectReason) {

        agencyCertificationAuditService.audit(id, certificationStatus, rejectReason);
    }
}
