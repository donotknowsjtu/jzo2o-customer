package com.jzo2o.customer.controller.worker;

import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.model.dto.response.BankAccountResDTO;
import com.jzo2o.customer.service.IBankAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 服务人员银行账户相关接口
 *
 * @author donot_know
 * @create 2025/8/10 9:11
 **/
@RestController("workerBankAccountController")
@Api(tags = "服务端 - 银行账户相关接口")
@RequestMapping("/worker/bank-account")
public class BankAccountController {
    @Resource
    private IBankAccountService bankAccountService;

    @ApiOperation("新增或更新银行账户")
    @PostMapping("")
    public void addUpdate(@RequestBody BankAccountUpsertReqDTO bankAccountUpsertReqDTO) {
        bankAccountService.workerAddUpdateBankAccount(bankAccountUpsertReqDTO);
    }

    @GetMapping("/currentUserBankAccount")
    @ApiOperation("获取当前用户的银行账户信息")
    public BankAccountResDTO getCurrentUserBankAccount() {
        return bankAccountService.getBankAccountByUserId();
    }
}
