package com.jzo2o.customer.controller.agency;

import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.AgencyCertificationAuditResDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.IAgencyCertificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/agency/agency-certification-audit")
@Api(tags = "机构端-机构认证" )
public class AgencyCertificationController {

    @Resource
    private IAgencyCertificationService agencyCertificationService;

    @PostMapping("")
    @ApiOperation("提交机构认证信息")
    public void addAgencyCertification(@RequestBody AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO) {
        agencyCertificationService.addAgencyCertification(agencyCertificationAuditAddReqDTO);
    }

    @GetMapping("/rejectReason")
    @ApiOperation("获取机构认证拒绝原因")
    public RejectReasonResDTO rejectReason() {
        return agencyCertificationService.getRejectReason();
    }
}
