package com.jzo2o.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.customer.enums.CertificationStatusEnum;
import com.jzo2o.customer.mapper.WorkerCertificationAuditMapper;
import com.jzo2o.customer.mapper.WorkerCertificationMapper;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.domain.WorkerCertification;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.WorkerCertificationUpdateDTO;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.IWorkerCertificationService;
import com.jzo2o.mvc.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务人员认证信息表 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-09-06
 */
@Service
public class WorkerCertificationServiceImpl extends ServiceImpl<WorkerCertificationMapper, WorkerCertification> implements IWorkerCertificationService {


    @Resource
    private WorkerCertificationAuditMapper workerCertificationAuditMapper;

    /**
     * 根据服务人员id更新
     *
     * @param workerCertificationUpdateDTO 服务人员认证更新模型
     */
    @Override
    public void updateById(WorkerCertificationUpdateDTO workerCertificationUpdateDTO) {
        LambdaUpdateWrapper<WorkerCertification> updateWrapper = Wrappers.<WorkerCertification>lambdaUpdate()
                .eq(WorkerCertification::getId, workerCertificationUpdateDTO.getId())
                .set(WorkerCertification::getCertificationStatus, workerCertificationUpdateDTO.getCertificationStatus())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getName()), WorkerCertification::getName, workerCertificationUpdateDTO.getName())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getIdCardNo()), WorkerCertification::getIdCardNo, workerCertificationUpdateDTO.getIdCardNo())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getFrontImg()), WorkerCertification::getFrontImg, workerCertificationUpdateDTO.getFrontImg())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getBackImg()), WorkerCertification::getBackImg, workerCertificationUpdateDTO.getBackImg())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getCertificationMaterial()), WorkerCertification::getCertificationMaterial, workerCertificationUpdateDTO.getCertificationMaterial())
                .set(ObjectUtil.isNotEmpty(workerCertificationUpdateDTO.getCertificationTime()), WorkerCertification::getCertificationTime, workerCertificationUpdateDTO.getCertificationTime());
        super.update(updateWrapper);
    }

    /**
     * 新增服务人员认证信息
     */
    @Override
    public void addWorkerCertification(WorkerCertificationAuditAddReqDTO workerCertificationAuditAddReqDTO) {
        WorkerCertification one = lambdaQuery()
                .eq(WorkerCertification::getId, UserContext.currentUserId())
                .one();
        if(one != null) {
            throw new IllegalArgumentException("服务人员认证信息已存在，请勿重复提交认证信息");
        }
        WorkerCertification workerCertification = BeanUtil.toBean(workerCertificationAuditAddReqDTO, WorkerCertification.class);
        workerCertification.setId(UserContext.currentUserId());
        workerCertification.setCertificationStatus(CertificationStatusEnum.INIT.getStatus());
        // 设置其他默认值或必要字段
        super.save(workerCertification);
    }

    /**
     * 获取服务人员认证拒绝原因
     *
     * @return 拒绝原因响应DTO
     */
    @Override
    public RejectReasonResDTO getRejectReason() {
        WorkerCertificationAudit workerCertificationAudit = workerCertificationAuditMapper.selectOne(Wrappers.<WorkerCertificationAudit>lambdaQuery()
                .eq(WorkerCertificationAudit::getServeProviderId, UserContext.currentUserId())
                .orderByDesc(WorkerCertificationAudit::getCreateTime)
                .last("limit 1"));
        if(workerCertificationAudit == null) {
            throw new ForbiddenOperationException("当前用户没有认证审核记录");
        }
        return BeanUtil.toBean(workerCertificationAudit.getRejectReason(), RejectReasonResDTO.class);
    }


}
