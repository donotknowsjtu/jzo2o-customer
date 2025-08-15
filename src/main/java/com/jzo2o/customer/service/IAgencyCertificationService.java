package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.customer.model.domain.AgencyCertification;
import com.jzo2o.customer.model.dto.AgencyCertificationUpdateDTO;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;

/**
 * <p>
 * 机构认证信息表 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-09-06
 */
public interface IAgencyCertificationService extends IService<AgencyCertification> {


    /**
     * 根据机构id更新
     *
     * @param agencyCertificationUpdateDTO 机构认证更新模型
     */
    void updateByServeProviderId(AgencyCertificationUpdateDTO agencyCertificationUpdateDTO);

    /**
     * 新增机构认证信息
     *
     * @param agencyCertificationAuditAddReqDTO 机构认证添加请求DTO
     */
    void addAgencyCertification(AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO);

    /**
     * 获取机构认证拒绝原因
     * @return 拒绝原因响应DTO
     */
    RejectReasonResDTO getRejectReason();
}
