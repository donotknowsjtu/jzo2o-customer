package com.jzo2o.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.customer.enums.CertificationStatusEnum;
import com.jzo2o.customer.mapper.AgencyCertificationAuditMapper;
import com.jzo2o.customer.mapper.AgencyCertificationMapper;
import com.jzo2o.customer.model.domain.AgencyCertification;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.AgencyCertificationUpdateDTO;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.IAgencyCertificationService;
import com.jzo2o.mvc.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 机构认证信息表 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-09-06
 */
@Service
public class AgencyCertificationServiceImpl extends ServiceImpl<AgencyCertificationMapper, AgencyCertification> implements IAgencyCertificationService {

    @Resource
    private AgencyCertificationAuditMapper agencyCertificationAuditMapper;

    /**
     * 根据机构id更新
     *
     * @param agencyCertificationUpdateDTO 机构认证更新模型
     */
    @Override
    public void updateByServeProviderId(AgencyCertificationUpdateDTO agencyCertificationUpdateDTO) {
        LambdaUpdateWrapper<AgencyCertification> updateWrapper = Wrappers.<AgencyCertification>lambdaUpdate()
                .eq(AgencyCertification::getId, agencyCertificationUpdateDTO.getId())
                .set(AgencyCertification::getCertificationStatus, agencyCertificationUpdateDTO.getCertificationStatus())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getName()), AgencyCertification::getName, agencyCertificationUpdateDTO.getName())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getIdNumber()), AgencyCertification::getIdNumber, agencyCertificationUpdateDTO.getIdNumber())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getLegalPersonName()), AgencyCertification::getLegalPersonName, agencyCertificationUpdateDTO.getLegalPersonName())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getLegalPersonIdCardNo()), AgencyCertification::getLegalPersonIdCardNo, agencyCertificationUpdateDTO.getLegalPersonIdCardNo())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getBusinessLicense()), AgencyCertification::getBusinessLicense, agencyCertificationUpdateDTO.getBusinessLicense())
                .set(ObjectUtil.isNotEmpty(agencyCertificationUpdateDTO.getCertificationTime()), AgencyCertification::getCertificationTime, agencyCertificationUpdateDTO.getCertificationTime());
        super.update(updateWrapper);
    }

    /**
     * 新增机构认证信息
     * @param agencyCertificationAuditAddReqDTO 机构认证添加请求DTO
     */
    @Override
    public void addAgencyCertification(AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO){
        // 查询是否已存在认证信息
        AgencyCertification one = lambdaQuery()
                .eq(AgencyCertification::getId, UserContext.currentUser().getId())
                .one();
        if(one != null){
            throw new IllegalArgumentException("已存在认证信息，不能重复添加");
        }

        // 创建新的认证信息
        agencyCertificationAuditAddReqDTO.setServeProviderId(UserContext.currentUser().getId());
        AgencyCertification agencyCertification = BeanUtil.toBean(agencyCertificationAuditAddReqDTO, AgencyCertification.class);
        super.save(agencyCertification);
    }

    @Override
    public RejectReasonResDTO getRejectReason(){
        // 查询最新的审核记录
        AgencyCertificationAudit agencyCertificationAudit = agencyCertificationAuditMapper.selectOne(Wrappers.<AgencyCertificationAudit>lambdaQuery()
                .eq(AgencyCertificationAudit::getServeProviderId, UserContext.currentUserId())
                .orderByDesc(AgencyCertificationAudit::getCreateTime)
                .last("limit 1"));
          // 查询当前用户认证信息是否存在
        if (agencyCertificationAudit == null) {
            throw new ForbiddenOperationException("当前用户尚未提交认证信息");
        }
        return BeanUtil.toBean(agencyCertificationAudit.getRejectReason(), RejectReasonResDTO.class);
    }
}
