package com.aio.portable.park.service.master;

import com.aio.portable.parkdb.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.parkdb.dao.master.model.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserMasterBatchBaseService extends ServiceImpl<UserMasterBaseMapper, User> {
}
