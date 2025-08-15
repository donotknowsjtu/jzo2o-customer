package com.jzo2o.customer.controller.consumer;

import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AddressBook;
import com.jzo2o.customer.model.dto.request.AddressBookPageQueryReqDTO;
import com.jzo2o.customer.model.dto.request.AddressBookUpsertReqDTO;
import com.jzo2o.customer.service.IAddressBookService;
import com.jzo2o.mvc.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址簿相关接口
 *
 * @author donot_know
 * @create 2025/8/9 9:05
 **/
@RestController("consumerAddressBookController")
@RequestMapping("/consumer/address-book")
@Api(tags = "用户端-地址簿相关接口")
public class AddressBookController {

    @Resource
    private IAddressBookService addressBookService;

    @PostMapping("")
    @ApiOperation("新增地址")
    public void addAddress(@RequestBody AddressBookUpsertReqDTO reqDTO) {
        addressBookService.addAddress(reqDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询地址列表")
    public PageResult<AddressBookResDTO> queryAddressList(
            @RequestParam (value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "isAsc1", defaultValue = "false", required = false) Boolean isAsc1,
            @RequestParam(value = "isAsc2", defaultValue = "false", required = false) Boolean isAsc2,
            @RequestParam(value = "orderBy1", required = false) String orderBy1,
            @RequestParam(value = "orderBy2", required = false) String orderBy2
            ) {
        AddressBookPageQueryReqDTO reqDTO = new AddressBookPageQueryReqDTO();
        reqDTO.setPageNo(pageNo.longValue());
        reqDTO.setPageSize(pageSize.longValue());
        reqDTO.setIsAsc1(isAsc1);
        reqDTO.setIsAsc2(isAsc2);
        reqDTO.setOrderBy1(orderBy1);
        reqDTO.setOrderBy2(orderBy2);
        return addressBookService.queryAddressList(reqDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取地址详情")
    public AddressBook getAddressById(@PathVariable("id") Long id) {
        return addressBookService.getById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改地址")
    public void updateAddress(@PathVariable("id") Long id, @RequestBody AddressBookUpsertReqDTO reqDTO) {
        addressBookService.updateAddress(id, reqDTO);
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除地址")
    public void deleteAddress(@RequestBody String[] ids) {
        for(String id : ids) {
            addressBookService.removeById(Long.valueOf(id));
        }
    }

    @GetMapping("/defaultAddress")
    @ApiOperation("获取默认地址")
    public AddressBook getDefaultAddress() {
       return addressBookService.getDefaultAddress();

    }
}
