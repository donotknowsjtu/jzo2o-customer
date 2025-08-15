package com.jzo2o.customer.controller.agency;

import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.model.dto.response.BankAccountResDTO;
import com.jzo2o.customer.service.IBankAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 机构端银行账户相关接口
 */
@RestController()
@RequestMapping("/agency/bank-account")
@Api(tags = "机构端 - 银行账户相关接口")
public class BankAccountController {

    @Resource
    private IBankAccountService bankAccountService;

    @ApiOperation("新增或更新银行账户")
    @PostMapping("")
    public BankAccountResDTO addUpdate(@RequestBody BankAccountUpsertReqDTO reqDTO) {
        return bankAccountService.agencyAddUpdateBankAccount(reqDTO);
    }

    @ApiOperation("获取当前用户的银行账户信息")
    @GetMapping("/currentUserBankAccount")
    public BankAccountResDTO getCurrentUserBankAccount() {
        return bankAccountService.getBankAccountByUserId();
    }

}
