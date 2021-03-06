package com.boot.service.impl;

import com.boot.dao.AddressMapper;
import com.boot.pojo.Address;
import com.boot.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> selectAddressByUserId(long userid) {
        return addressMapper.selectAddressByUserId(userid);
    }

    @Override
    public Address selectAddressByid(long id) {
        return addressMapper.selectAddressByid(id);
    }

    @Override
    public void addAddress(Address address) {
        addressMapper.addAddress(address);
    }

    @Override
    public void delAddressById(long id) {
        addressMapper.delAddressById(id);
    }
}
