package com.aio.portable.park.service.master;

import com.aio.portable.park.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.park.dao.master.model.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserMasterServiceImpl extends ServiceImpl<UserMasterBaseMapper, User> {
}
