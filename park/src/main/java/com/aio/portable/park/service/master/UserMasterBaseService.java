package com.aio.portable.park.service.master;

import com.aio.portable.park.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.park.dao.master.model.User;
import com.aio.portable.park.dao.master.model.UserDTO;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class UserMasterBaseService {
    @Autowired
    UserMasterBaseMapper userMasterBaseMapper;

    @Autowired
    UserMasterServiceImpl userMasterServiceImpl;

    public User selectById(Integer id) {
        return userMasterBaseMapper.selectById(id);
    }

    public UserDTO selectByIdToUserDTO(Integer id) {
        return userMasterBaseMapper.selectById(id, UserDTO.class);
    }

    public List<User> selectList(User user) {
        return userMasterBaseMapper.selectList(user);
    }

    public List<User> selectList() {
        Class<User> entityClass = userMasterServiceImpl.getEntityClass();
        Class<?> entityClass1 = userMasterBaseMapper.getEntityClass();
        Object currentModelClass = ClassSugar.invoke(userMasterServiceImpl, "currentModelClass");
        return userMasterBaseMapper.selectList();
    }

    public List<User> selectListByName(String name) {
        return userMasterBaseMapper.selectList(c -> c.eq(User::getName, name));
    }

    public List<User> selectListByEmail(String email) {
        return userMasterBaseMapper.selectList(c -> c.eq(User::getEmail, email));
    }

    public List<UserDTO> selectListByAgeToUserDTO(String age) {
        return userMasterBaseMapper.selectList(c -> c.eq(User::getAge, age), UserDTO.class);
    }

    public List<UserDTO> selectListToUserDTO(User user) {
        return userMasterBaseMapper.selectList(user, UserDTO.class);
    }

    public List<UserDTO> selectBatchIdsToUserDTO(List<String> idList) {
        return userMasterBaseMapper.selectBatchIds(idList, UserDTO.class);
    }

    public List<User> selectListByAgeOnlyName(int age) {
        return userMasterBaseMapper.selectList(c -> c.eq(User::getAge, age), User::getName);
    }

    public Page<User> selectPage(int page, int size, User user) {
        return userMasterBaseMapper.selectPage(new Page<>(page, size), user);
    }

    public Page<User> selectPageByAuthor(int page, int size, String name) {
        return userMasterBaseMapper.selectPage(new Page<>(page, size), c -> c.eq(User::getName, name));
    }

    public int insertBatch(List<User> userList) {
        return userMasterBaseMapper.insertBatch(userList);
    }

    public int updateById(User user) {
        return userMasterBaseMapper.updateById(user);
    }

    public int update(User user, User condition) {
        return userMasterBaseMapper.update(user, condition);
    }

    public int update(User user, Function<LambdaUpdateWrapper<User>, LambdaUpdateWrapper<User>> condition) {
        return userMasterBaseMapper.update(user, condition);
    }

    public int updateByAge(Function<LambdaUpdateWrapper<User>, LambdaUpdateWrapper<User>> updated, int age) {
        return userMasterBaseMapper.update(updated, c -> c.eq(User::getAge, age));
    }

    public int deleteBatchIds(List<String> idList) {
        return userMasterBaseMapper.deleteBatchIds(idList);
    }


}
