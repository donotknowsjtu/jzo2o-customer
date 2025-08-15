package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.customer.model.domain.BankAccount;
import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.model.dto.response.BankAccountResDTO;

/**
 * <p>
 * 服务人员/机构 银行账户信息表
 * </p>
 *
 * @author donot_know
 * @since 2025-8-10 11:05
 */
public interface IBankAccountService extends IService<BankAccount> {

    /**
     * 服务人员新增或更新银行账户信息
     *
     * @param bankAccountUpsertReqDTO 银行账户信息请求DTO
     */
    void workerAddUpdateBankAccount(BankAccountUpsertReqDTO bankAccountUpsertReqDTO);
    /**
     * 获取当前用户的银行账户信息
     *
     * @return 银行账户响应DTO
     */
    BankAccountResDTO getBankAccountByUserId();
    /**
     * 机构新增或更新银行账户信息
     *
     * @param reqDTO 银行账户请求DTO
     * @return 银行账户响应DTO
     */
    BankAccountResDTO agencyAddUpdateBankAccount(BankAccountUpsertReqDTO reqDTO);
}
