package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.customer.mapper.BankAccountMapper;
import com.jzo2o.customer.mapper.ServeProviderMapper;
import com.jzo2o.customer.model.domain.BankAccount;
import com.jzo2o.customer.model.domain.ServeProvider;
import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.model.dto.response.BankAccountResDTO;
import com.jzo2o.customer.service.IBankAccountService;
import com.jzo2o.mvc.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BankAccountServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements IBankAccountService {



    public void workerAddUpdateBankAccount(BankAccountUpsertReqDTO bankAccountUpsertReqDTO) {
        Long userId = UserContext.currentUserId();
        bankAccountUpsertReqDTO.setId(userId);
        //检查type是否为空
        if(bankAccountUpsertReqDTO.getType() == null) {
            bankAccountUpsertReqDTO.setType(2); // 默认为2，表示服务人员
        }
        // 将bankAccountUpsertReqDTO转换为BankAccount对象并保存到数据库
        BankAccount bankAccount = BeanUtils.toBean(bankAccountUpsertReqDTO, BankAccount.class);
        baseMapper.updateById(bankAccount);
    }

    public BankAccountResDTO getBankAccountByUserId() {
        Long userId = UserContext.currentUserId();
        BankAccount bankAccount = baseMapper.selectById(userId);
        if (bankAccount == null) {
            throw new ForbiddenOperationException("当前用户没有银行账户信息");
        }
        // 将BankAccount对象转换为BankAccountResDTO对象并返回
        return BeanUtils.toBean(bankAccount, BankAccountResDTO.class);
    }

    public BankAccountResDTO agencyAddUpdateBankAccount(BankAccountUpsertReqDTO reqDTO) {
        // 检查type是否为空
        if (reqDTO.getType() == null) {
            reqDTO.setType(3); // 默认为3，表示机构
        }
        // 将reqDTO转换为BankAccount对象并保存到数据库
        reqDTO.setId(UserContext.currentUserId());
        BankAccount bankAccount = BeanUtils.toBean(reqDTO, BankAccount.class);
        baseMapper.updateById(bankAccount);
        // 返回转换后的BankAccountResDTO对象
        return BeanUtils.toBean(bankAccount, BankAccountResDTO.class);
    }
}
