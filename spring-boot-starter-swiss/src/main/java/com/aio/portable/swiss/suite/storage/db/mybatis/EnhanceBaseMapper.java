package com.aio.portable.swiss.suite.storage.db.mybatis;

import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface EnhanceBaseMapper<S> extends BaseMapper<S> {

    default Class<?> getEntityClass() {
        return ClassSugar.resolveTypeArguments(this.getClass(), BaseMapper.class)[0];
    }

    default int insertBatch(Collection<S> list) {
        List<Integer> resultList = list.stream().map(c -> this.insert(c)).collect(Collectors.toList());
        return resultList.stream().reduce(0, (x, y) -> x + y);
    }

    default int delete(S entity) {
        return this.delete(entity, null);
    }

    default int delete(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.delete(null, condition);
    }

    default int delete(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return this.delete(next);
    }

    static <X> LambdaQueryWrapper<X> buildLambdaQueryWrapper(X entity) {
        return entity == null ? Wrappers.lambdaQuery() : Wrappers.lambdaQuery(entity);
    }

    static <X> LambdaQueryWrapper<X> buildLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    static <X> LambdaUpdateWrapper<X> buildLambdaUpdateWrapper() {
        return Wrappers.lambdaUpdate();
    }

    default int update(S updated, S condition) {
        return this.update(updated, Wrappers.lambdaUpdate(condition));
    }

    default int update(S updated, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> condition) {
        LambdaUpdateWrapper<S> wrapper = buildLambdaUpdateWrapper();
        LambdaUpdateWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return this.update(updated, next);
    }

    default int update(Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> updated, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> condition) {
        LambdaUpdateWrapper<S> wrapper = buildLambdaUpdateWrapper();
        LambdaUpdateWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        next = updated == null ? next : updated.apply(next);
        return this.update(null, next);
    }

    default int updateBatchById(Collection<S> updatedList) {
        List<Integer> resultList = updatedList.stream().map(c -> this.updateById(c)).collect(Collectors.toList());
        return resultList.stream().reduce(0, (x, y) -> x + y);
    }

    default <T> T selectById(Serializable id, Class<T> target) {
        S entity = selectById(id);
        return BeanSugar.Cloneable.deepCloneByProperties(entity, target);
    }

    default <T> List<T> selectBatchIds(Collection<? extends Serializable> idList, Class<T> target) {
        List<S> list = selectBatchIds(idList);
        return CollectionSugar.cloneByProperties(list, target);
    }

    default S selectOne(S entity) {
        return this.selectOne(entity, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default S selectOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.selectOne(null, condition);
    }

    default S selectOne(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return selectOne(next);
    }


    default <T> T selectOne(S entity, Class<T> target) {
        return this.selectOne(entity, null, target);
    }

    default <T> T selectOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        return this.selectOne(null, condition, target);
    }

    default <T> T selectOne(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        S one = selectOne(next);
        return BeanSugar.Cloneable.deepCloneByProperties(one, target);
    }


    default boolean exists(S entity) {
        return this.exists(entity, null);
    }

    default boolean exists(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.exists(null, condition);
    }

    default boolean exists(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return exists(next);
    }

    default Long selectCount() {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper();
        return selectCount(wrapper);
    }

    default Long selectCount(S entity) {
        return this.selectCount(entity, null);
    }

    default Long selectCount(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.selectCount( null, condition);
    }

    default Long selectCount(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return selectCount(next);
    }


    default List<S> selectList() {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper();
        return selectList(wrapper);
    }

    default <T> List<T> selectList(Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper();
        List<S> list = selectList(wrapper);
        return CollectionSugar.cloneByProperties(list, target);
    }

    default List<S> selectList(S entity) {
        return this.selectList(entity, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default List<S> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.selectList(null, condition);
    }

    default List<S> selectList(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return selectList(next);
    }

    default <T> List<T> selectList(S entity, Class<T> target) {
        return this.selectList(entity, null, target);
    }

    default <T> List<T> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        return this.selectList(null, condition, target);
    }

    default <T> List<T> selectList(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        List<S> list = selectList(entity, condition);
        return CollectionSugar.cloneByProperties(list, target);
    }

    default List<S> selectList(S entity, SFunction<S, ?>... selectArray) {
        return this.selectList(entity, null, selectArray);
    }

    default List<S> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, SFunction<S, ?>... selectArray) {
        return this.selectList(null, condition, selectArray);
    }

    default List<S> selectList(S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, SFunction<S, ?>... selectArray) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
//        for (SFunction<S, ?> fun : selectArray) {
//            next = next.select(fun);
//        }
        next = next.select(selectArray);
        return selectList(next);
    }

    default List<Map<String, Object>> selectMaps(S entity) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        return selectMaps(wrapper);
    }

    default List<Object> selectObjs(S entity) {
        return selectObjs(Wrappers.lambdaQuery(entity));
    }

    default <P extends IPage<S>> P selectPage(P page) {
        return selectPage(page, buildLambdaQueryWrapper());
    }

    default <P extends IPage<S>> P selectPage(P page, S entity) {
        return this.selectPage(page, entity, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default <P extends IPage<S>> P selectPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.selectPage(page, null, condition);
    }

    default <P extends IPage<S>> P selectPage(P page, S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return selectPage(page, next);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, S entity, Class<T> target) {
        return this.selectPage(page, entity, null, target);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        return this.selectPage(page, null, condition, target);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition, Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        P pIn = selectPage(page, next);

        List<S> sourceList = pIn.getRecords();
        List<T> targetList = CollectionSugar.cloneByProperties(sourceList, target);
        Page<T> pOut = Page.<T>of(pIn.getCurrent(), pIn.getSize(), pIn.getTotal()).setRecords(targetList);

        return pOut;
    }

    default <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, S entity) {
        return this.selectMapsPage(page, entity, null);
    }

    default <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        return this.selectMapsPage(page, null, condition);
    }

    default <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, S entity, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> condition) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(entity);
        LambdaQueryWrapper<S> next = condition == null ? wrapper : condition.apply(wrapper);
        return selectMapsPage(page, next);
    }
}




























