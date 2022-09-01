//package com.aio.portable.swiss.suite.storage.db.mybatis;
//
//import com.aio.portable.swiss.suite.bean.BeanSugar;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//
//public interface BusinessBaseMapper<S> extends EnhanceBaseMapper<S> {
//    default void injectDelFlag(S entity) {
//        try {
//            BeanSugar.PropertyDescriptors.setValueIfPresent(entity, "delFlag", 0);
//        } catch (Exception e) {
//        }
//        try {
//            BeanSugar.PropertyDescriptors.setValueIfPresent(entity, "delFlag", "0");
//        } catch (Exception e) {
//        }
//    }
//
//    default void inject(S entity) {
//        injectDelFlag(entity);
//    }
//
//    default int delete(S entity) {
//        return EnhanceBaseMapper.super.delete(entity);
//    }
//
//    default int setDelete(S entity) {
//        try {
//            BeanSugar.PropertyDescriptors.setValueIfPresent(entity, "delFlag", 0);
//        } catch (Exception e) {
//        }
//        try {
//            BeanSugar.PropertyDescriptors.setValueIfPresent(entity, "delFlag", "0");
//        } catch (Exception e) {
//        }
//        return delete(entity);
//    }
//
////    @Override
////    default int insert1(S entity) {
////
////        return BaseMapper.super.insert(entity);
////    }
//
//    default int update(S entity, S condition) {
//        inject(condition);
//        return EnhanceBaseMapper.super.update(entity, condition);
//    }
//
//    default S selectOne(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectOne(entity);
//    }
//
//    default boolean exists(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.exists(entity);
//    }
//
//    default Long selectCount(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectCount(entity);
//    }
//
//    default List<S> selectList(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectList(entity);
//    }
//
//    default <T> List<T> selectList(S entity, Class<T> target) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectList(entity, target);
//    }
//
//    default List<S> selectList(S entity, Function<LambdaQueryWrapper<S>, Wrapper<S>> wrap) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectList(entity, wrap);
//    }
//
//    default <T> List<T> selectList(S entity, Function<LambdaQueryWrapper<S>, Wrapper<S>> wrap, Class<T> target) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectList(entity, wrap, target);
//    }
//
//    default List<Map<String, Object>> selectMaps(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectMaps(entity);
//    }
//
//    default List<Object> selectObjs(S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectObjs(entity);
//    }
//
//    default <P extends IPage<S>> P selectPage(P page, S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectPage(page, entity);
//    }
//
//    default <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, S entity) {
//        inject(entity);
//        return EnhanceBaseMapper.super.selectMapsPage(page, entity);
//    }
//
////    default int updateById(S entity) {
////        inject(entity);
////        return EnhanceBaseMapper.super.updateById(entity);
////    }
//
//    default int insert(S entity) {
//        return EnhanceBaseMapper.super.insert(entity);
//    }
//
//}
