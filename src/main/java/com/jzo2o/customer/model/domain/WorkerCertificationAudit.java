package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *     * 服务人员认证审核表
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("worker_certification_audit")
public class WorkerCertificationAudit implements Serializable {
    private Long id; // 主键
    private Long serveProviderId; // 服务人员id
    private String name; // 姓名
    private String idCardNo; // 身份证号
    private String frontImg; // 身份证正面
    private String backImg; // 身份证反面
    private String certificationMaterial; // 证明资料
    private Integer auditStatus; // 审核状态，0：未审核，1：已审核
    private Long auditorId; // 审核人id
    private String auditorName; // 审核人姓名
    private java.time.LocalDateTime auditTime; // 审核时间
    private Integer certificationStatus; // 认证状态，1：认证中，2：认证成功，3认证失败
    private String rejectReason; // 驳回原因
    private java.time.LocalDateTime createTime; // 创建时间
    private java.time.LocalDateTime updateTime; // 更新时间
}
