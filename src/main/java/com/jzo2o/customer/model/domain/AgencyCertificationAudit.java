package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("agency_certification_audit")
public class AgencyCertificationAudit implements Serializable {
    private Long id;
    private Long serveProviderId;
    private String name;
    private String idNumber;
    private String legalPersonName;
    private String legalPersonIdCardNo;
    private String businessLicense;
    private Integer auditStatus;
    private Long auditorId;
    private String auditorName;
    private java.time.LocalDateTime auditTime;
    private Integer certificationStatus;
    private String rejectReason;
    private java.time.LocalDateTime createTime;
    private java.time.LocalDateTime updateTime;
}
