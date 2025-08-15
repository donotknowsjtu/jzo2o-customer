package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bank_account")
public class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    //-- auto-generated definition
    //create table bank_account
    //(
    //    id                    bigint       null,
    //    type                  int          null,
    //    name                  varchar(50)  null,
    //    bank_name             varchar(50)  null,
    //    province              varchar(50)  null,
    //    city                  varchar(50)  null,
    //    district              varchar(50)  null,
    //    branch                varchar(50)  null,
    //    account               varchar(50)  null,
    //    account_certification varchar(100) null,
    //    create_time           datetime     null,
    //    update_time           datetime     null
    //);
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * type: 2.服务人员；3.机构
     */
   private Integer type;
    /**
     * 开户姓名
     */
    private String name;
    private String bankName;
    private String province;
    private String city;
    private String district;
    private String branch;
    private String account;
    private String accountCertification;
    private java.util.Date createTime;
    private java.util.Date updateTime;


}
