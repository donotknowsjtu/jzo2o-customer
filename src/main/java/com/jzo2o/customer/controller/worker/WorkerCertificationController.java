package com.jzo2o.customer.controller.worker;

import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.IWorkerCertificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/worker/worker-certification-audit")
@Api(tags = "服务端 - 服务人员认证相关接口")
public class WorkerCertificationController {
    @Resource
    private IWorkerCertificationService workerCertificationService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkerCertificationController.class);
    /**
     * 新增服务人员认证信息
     *
     * @param workerCertificationAuditAddReqDTO 请求参数
     */
    @PostMapping("")
    @ApiOperation("新增服务人员认证信息")
    public void addWorkerCertification(@RequestBody WorkerCertificationAuditAddReqDTO workerCertificationAuditAddReqDTO) {
      log.info("新增服务人员认证信息 - 请求参数: 姓名={}, 身份证号={}, 认证材料={}, 服务商ID={}",
                        workerCertificationAuditAddReqDTO.getName(),
                        workerCertificationAuditAddReqDTO.getIdCardNo(),
                        workerCertificationAuditAddReqDTO.getCertificationMaterial(),
                        workerCertificationAuditAddReqDTO.getServeProviderId());
        workerCertificationService.addWorkerCertification(workerCertificationAuditAddReqDTO);

    }

    @GetMapping("/rejectReason")
    @ApiOperation("获取服务人员认证拒绝原因")
    public RejectReasonResDTO getRejectReason() {
        return workerCertificationService.getRejectReason();
    }


}

