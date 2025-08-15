package com.jzo2o.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.api.publics.MapApi;
import com.jzo2o.api.publics.dto.response.LocationResDTO;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.CollUtils;
import com.jzo2o.common.utils.NumberUtils;
import com.jzo2o.common.utils.StringUtils;
import com.jzo2o.customer.mapper.AddressBookMapper;
import com.jzo2o.customer.model.domain.AddressBook;
import com.jzo2o.customer.model.dto.request.AddressBookPageQueryReqDTO;
import com.jzo2o.customer.model.dto.request.AddressBookUpsertReqDTO;
import com.jzo2o.customer.service.IAddressBookService;
import com.jzo2o.mvc.utils.UserContext;
import com.jzo2o.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.github.pagehelper.page.PageMethod.orderBy;

/**
 * <p>
 * 地址薄 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-07-06
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

    @Override
    public List<AddressBookResDTO> getByUserIdAndCity(Long userId, String city) {

        List<AddressBook> addressBooks = lambdaQuery()
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getCity, city)
                .list();
        if(CollUtils.isEmpty(addressBooks)) {
            return new ArrayList<>();
        }
        return BeanUtils.copyToList(addressBooks, AddressBookResDTO.class);
    }

    @Override
    public void addAddress(AddressBookUpsertReqDTO reqDTO) {
        // 默认地址一个用户只有一个
       // 1.如果是默认地址，先将其他地址的默认地址标记为非默认
        if (ObjectUtil.isNotNull(reqDTO.getIsDefault()) && reqDTO.getIsDefault() == 1) {
            // 如果是默认地址，先将其他地址的默认地址标记为非默认
            LambdaUpdateWrapper<AddressBook> lambdaUpdate = Wrappers.lambdaUpdate(AddressBook.class);
            lambdaUpdate.eq(AddressBook::getUserId, UserContext.currentUserId())
                    .set(AddressBook::getIsDefault, false);
            baseMapper.update(null, lambdaUpdate);
        }
        // 通过phone查询uesrId
        AddressBook addressBook = BeanUtils.toBean(reqDTO, AddressBook.class);
        addressBook.setUserId(UserContext.currentUserId());
        // 新增地址
        baseMapper.insert(addressBook);
    }

    @Override
    public PageResult<AddressBookResDTO> queryAddressList(AddressBookPageQueryReqDTO reqDTO) {
        Page<AddressBook> addressBookPage = PageUtils.parsePageQuery(reqDTO, AddressBook.class);
       QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<AddressBook>()
               .eq("user_id", UserContext.currentUserId());

       Page<AddressBook> addressBookPage1 = baseMapper.selectPage(addressBookPage, queryWrapper);
       return PageUtils.toPage(addressBookPage1, AddressBookResDTO.class);

    }

    @Override
    public void updateAddress(Long id, AddressBookUpsertReqDTO reqDTO) {
        // 1.地址存在性校验
        AddressBook addressBook = baseMapper.selectById(id);
        if(ObjectUtil.isNull(addressBook)) {
            throw new ForbiddenOperationException("地址不存在，无法编辑");
        }

        // 2.更新地址
        AddressBook updateAddressBook = BeanUtils.toBean(reqDTO, AddressBook.class);
        updateAddressBook.setId(id);
        updateAddressBook.setUserId(UserContext.currentUserId());

        // 3.如果是默认地址，将其他地址的默认地址标记为非默认
        if(reqDTO.getIsDefault() == 1) {
            LambdaUpdateWrapper<AddressBook> lambdaUpdate = Wrappers.lambdaUpdate(AddressBook.class);
            lambdaUpdate.eq(AddressBook::getUserId, UserContext.currentUserId())
                    .set(AddressBook::getIsDefault, false);
            baseMapper.update(null, lambdaUpdate);
        }

        baseMapper.updateById(updateAddressBook);


    }

    @Override
    public AddressBook getDefaultAddress() {
        Long userId = UserContext.currentUserId();
        // 1.查询默认地址
        LambdaQueryWrapper<AddressBook> lambdaQuery = Wrappers.lambdaQuery(AddressBook.class);
        lambdaQuery.eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, true);
        AddressBook addressBook = baseMapper.selectOne(lambdaQuery);

        // 2.如果没有默认地址，返回null
        if(ObjectUtil.isNull(addressBook)) {
            return null;
        }


        return addressBook;
    }
}
