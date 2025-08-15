package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.mapper.AgencyCertificationAuditMapper;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.AgencyCertificationAuditResDTO;
import com.jzo2o.customer.service.IAgencyCertificationAuditService;
import com.jzo2o.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgencyCertificationAuditServiceImpl extends ServiceImpl<AgencyCertificationAuditMapper, AgencyCertificationAudit> implements IAgencyCertificationAuditService {


    public PageResult<AgencyCertificationAuditResDTO> page(AgencyCertificationAuditPageQueryReqDTO reqDTO) {
        Page<AgencyCertificationAudit> agencyCertificationAuditPage = PageUtils.parsePageQuery(reqDTO, AgencyCertificationAudit.class);
        QueryWrapper<AgencyCertificationAudit> queryWrapper = new QueryWrapper<>();
        if (reqDTO.getAuditStatus() != null) {
            queryWrapper.eq("audit_status", reqDTO.getAuditStatus());
        }
        if (reqDTO.getCertificationStatus() != null) {
            queryWrapper.eq("certification_status", reqDTO.getCertificationStatus());
        }
        if(reqDTO.getLegalPersonName() != null) {
            queryWrapper.like("legal_person_name", reqDTO.getLegalPersonName());
        }
        if(reqDTO.getName() != null) {
            queryWrapper.like("name", reqDTO.getName());
        }
        if (reqDTO.getOrderBy1() != null && !reqDTO.getOrderBy1().isEmpty()) {
            queryWrapper.orderBy(true, reqDTO.getIsAsc1() != null ? reqDTO.getIsAsc1() : true, reqDTO.getOrderBy1());
        }
        if( reqDTO.getOrderBy2() != null && !reqDTO.getOrderBy2().isEmpty()) {
            queryWrapper.orderBy(true, reqDTO.getIsAsc2() != null ? reqDTO.getIsAsc2() : true, reqDTO.getOrderBy2());
        }

        Page<AgencyCertificationAudit> agencyCertificationAuditPage1 = baseMapper.selectPage(agencyCertificationAuditPage, queryWrapper);
        return PageUtils.toPage(agencyCertificationAuditPage1, AgencyCertificationAuditResDTO.class);
    }

    public void audit(Long id, Integer auditStatus, String rejectReason) {
        AgencyCertificationAudit agencyCertificationAudit = baseMapper.selectById(id);
        if (agencyCertificationAudit == null) {
            throw new IllegalArgumentException("机构认证审核信息不存在");
        }
        if (agencyCertificationAudit.getAuditStatus() != null && agencyCertificationAudit.getAuditStatus() != 0) {
            throw new IllegalStateException("该机构认证审核信息已被处理，无法再次审核");
        }

        agencyCertificationAudit.setAuditStatus(auditStatus);
        agencyCertificationAudit.setRejectReason(rejectReason);
        agencyCertificationAudit.setUpdateTime(LocalDateTime.now());

        baseMapper.updateById(agencyCertificationAudit);
    }
}
