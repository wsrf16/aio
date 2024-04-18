package com.aio.portable.swiss.suite.storage.db.mybatis;

import com.aio.portable.swiss.sugar.naming.NamingStrategySugar;
import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.aio.portable.swiss.sugar.resource.function.LambdaFunction;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface EnhanceBaseMapper<S> extends BaseMapper<S> {
    static <T> String getPropertySnakeNameOf(LambdaFunction<T, ?> propertyName) {
        String propertyNameOf = BeanSugar.PropertyDescriptors.getPropertyNameOf(propertyName);
        String propertyNameSnake = NamingStrategySugar.snake(propertyNameOf);
        return propertyNameSnake;
    }

    default Class<?> getEntityClass() {
        return ClassSugar.resolveSuperClassArgumentType(this.getClass(), BaseMapper.class)[0];
    }

    default int insertBatch(Collection<S> list) {
        List<Integer> resultList = list.stream().map(c -> this.insert(c)).collect(Collectors.toList());
        return resultList.stream().reduce(0, (x, y) -> x + y);
    }

    default int delete(S predicate) {
        return this.delete(predicate, null);
    }

    default int delete(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.delete(null, predicateExpression);
    }

    default int delete(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return this.delete(next);
    }

    static <T, DTO extends T> LambdaQueryWrapper<T> lambdaQuery(DTO dto) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper(dto);
        try {
            Map<String, ?> nameValueMap = BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(dto);
            nameValueMap.forEach((k, v) -> {
                if (k.endsWith("Like")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "Like");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    wrapper.like(v != null, lambdaSFunction, v);
                } else if (k.endsWith("LikeLeft")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "LikeLeft");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    wrapper.likeLeft(v != null, lambdaSFunction, v);
                } else if (k.endsWith("LikeRight")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "LikeRight");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    wrapper.likeRight(v != null, lambdaSFunction, v);
                } else if (k.endsWith("NotIn")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "NotIn");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    if (v instanceof Collection<?>) {
                        wrapper.notIn(v != null, lambdaSFunction, (Collection<?>)v);
                    }
                } else if (k.endsWith("In")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "In");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    if (v instanceof Collection<?>) {
                        wrapper.in(v != null, lambdaSFunction, (Collection<?>)v);
                    }
                } else if (k.endsWith("OrderByASC")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "OrderByASC");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    if (v instanceof Boolean) {
                        wrapper.orderByAsc(Objects.equals(v, true), lambdaSFunction);
                    }
                } else if (k.endsWith("OrderByDESC")) {
                    String methodName = "get" + StringSugar.trimEnd(NamingStrategySugar.pascal(k), "OrderByDESC");
                    LambdaSFunction lambdaSFunction = new LambdaSFunction(methodName, dto.getClass());
                    if (v instanceof Boolean) {
                        wrapper.orderByDesc(Objects.equals(v, true), lambdaSFunction);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wrapper;
    }

    static <X> LambdaQueryWrapper<X> buildLambdaQueryWrapper(X predicate) {
        return predicate == null ? Wrappers.lambdaQuery() : lambdaQuery(predicate);
    }

    static <X> LambdaQueryWrapper<X> buildLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    static <X> LambdaUpdateWrapper<X> buildLambdaUpdateWrapper() {
        return Wrappers.lambdaUpdate();
    }

    default int update(S assignment, S predicateExpression) {
        return this.update(assignment, Wrappers.lambdaUpdate(predicateExpression));
    }

    // assignment: model
    // predicateExpression: condition
    default int update(S assignment, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> predicateExpression) {
        LambdaUpdateWrapper<S> wrapper = buildLambdaUpdateWrapper();
        LambdaUpdateWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return this.update(assignment, next);
    }

    default int update(Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> assignment, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> predicateExpression) {
        LambdaUpdateWrapper<S> wrapper = buildLambdaUpdateWrapper();
        LambdaUpdateWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        next = assignment == null ? next : assignment.apply(next);
        return this.update(null, next);
    }

//    default int update(String sql, SFunction<S, ?> conditionName, Object condition) {
    default int update(String sql, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> predicateExpression) {
        LambdaUpdateWrapper<S> wrapper = buildLambdaUpdateWrapper();
        LambdaUpdateWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        next.setSql(sql);

        return this.update(null, next);
    }

    default int updateBatchById(Collection<S> assignmentList) {
        List<Integer> resultList = assignmentList.stream().map(c -> this.updateById(c)).collect(Collectors.toList());
        return resultList.stream().reduce(0, (x, y) -> x + y);
    }

    default int increase(LambdaFunction<S, ?> propertyName, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> predicateExpression) {
        String propertyNameSnake = getPropertySnakeNameOf(propertyName);
        String sql = MessageFormat.format("{0} = {0} + 1", propertyNameSnake);

        return this.update(sql, predicateExpression);
    }

    default int decrease(LambdaFunction<S, ?> propertyName, Function<LambdaUpdateWrapper<S>, LambdaUpdateWrapper<S>> predicateExpression) {
        String propertyNameSnake = getPropertySnakeNameOf(propertyName);
        String sql = MessageFormat.format("{0} = {0} - 1", propertyNameSnake);

        return this.update(sql, predicateExpression);
    }

    default <T> T selectById(Serializable id, Class<T> target) {
        S entity = selectById(id);
        return DeepCloneSugar.Properties.clone(entity, target);
    }

    default <T> List<T> selectBatchIds(Collection<? extends Serializable> idList, Class<T> target) {
        List<S> list = selectBatchIds(idList);
        return CollectionSugar.cloneByProperties(list, target);
    }

    default S selectOne(S predicate) {
        return this.selectOne(predicate, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default S selectOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectOne(null, predicateExpression);
    }

    default S selectOne(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return selectOne(next);
    }


    default <T> T selectOne(S predicate, Class<T> target) {
        return this.selectOne(predicate, null, target);
    }

    default <T> T selectOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        return this.selectOne(null, predicateExpression, target);
    }

    default <T> T selectOne(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        S one = selectOne(next);
        return DeepCloneSugar.Properties.clone(one, target);
    }

    default S selectFirstOne(S predicate) {
        return this.selectFirstOne(predicate, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default S selectFirstOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectFirstOne(null, predicateExpression);
    }

    default S selectFirstOne(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return selectFirstOne(next);
    }


    default <T> T selectFirstOne(S predicate, Class<T> target) {
        return this.selectFirstOne(predicate, null, target);
    }

    default <T> T selectFirstOne(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        return this.selectFirstOne(null, predicateExpression, target);
    }

    default <T> T selectFirstOne(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        S one = selectFirstOne(next);
        return DeepCloneSugar.Properties.clone(one, target);
    }
    
    default S selectFirstOne(Wrapper<S> queryWrapper) {
        List<S> list = selectList(queryWrapper);
        return CollectionSugar.isEmpty(list) ? null : list.get(0);
    }


    default boolean exists(S predicate) {
        return this.exists(predicate, null);
    }

    default boolean exists(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.exists(null, predicateExpression);
    }

    default boolean exists(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return exists(next);
    }

    default Long selectCount() {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper();
        return selectCount(wrapper);
    }

    default Long selectCount(S predicate) {
        return this.selectCount(predicate, null);
    }

    default Long selectCount(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectCount( null, predicateExpression);
    }

    default Long selectCount(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
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

    default List<S> selectList(S predicate) {
        return this.selectList(predicate, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default List<S> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectList(null, predicateExpression);
    }

    default List<S> selectList(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return selectList(next);
    }

    default <T> List<T> selectList(S predicate, Class<T> target) {
        return this.selectList(predicate, null, target);
    }

    default <T> List<T> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        return this.selectList(null, predicateExpression, target);
    }

    default <T> List<T> selectList(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        List<S> list = selectList(predicate, predicateExpression);
        return CollectionSugar.cloneByProperties(list, target);
    }

    default List<S> selectList(S predicate, SFunction<S, ?>... selectArray) {
        return this.selectList(predicate, null, selectArray);
    }

    default List<S> selectList(Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, SFunction<S, ?>... selectArray) {
        return this.selectList(null, predicateExpression, selectArray);
    }

    default List<S> selectList(S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, SFunction<S, ?>... selectArray) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
//        for (SFunction<S, ?> fun : selectArray) {
//            next = next.select(fun);
//        }
        next = next.select(selectArray);
        return selectList(next);
    }

    default List<Map<String, Object>> selectMapList(S predicate) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        return selectMaps(wrapper);
    }

    default List<Object> selectObjectList(S predicate) {
        return selectObjs(Wrappers.lambdaQuery(predicate));
    }

    default <P extends IPage<S>> P selectPage(P page) {
        return selectPage(page, buildLambdaQueryWrapper());
    }

    default <P extends IPage<S>> P selectPage(P page, S predicate) {
        return this.selectPage(page, predicate, (Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>>)null);
    }

    default <P extends IPage<S>> P selectPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectPage(page, null, predicateExpression);
    }

    default <P extends IPage<S>> P selectPage(P page, S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return selectPage(page, next);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, S predicate, Class<T> target) {
        return this.selectPage(page, predicate, null, target);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        return this.selectPage(page, null, predicateExpression, target);
    }

    default <P extends IPage<S>, T> Page<T> selectPage(P page, S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression, Class<T> target) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        P pIn = selectPage(page, next);

        List<S> sourceList = pIn.getRecords();
        List<T> targetList = CollectionSugar.cloneByProperties(sourceList, target);
        Page<T> pOut = Page.<T>of(pIn.getCurrent(), pIn.getSize(), pIn.getTotal()).setRecords(targetList);

        return pOut;
    }

    default <P extends IPage<Map<String, Object>>> P selectMapPage(P page, S predicate) {
        return this.selectMapPage(page, predicate, null);
    }

    default <P extends IPage<Map<String, Object>>> P selectMapPage(P page, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        return this.selectMapPage(page, null, predicateExpression);
    }

    default <P extends IPage<Map<String, Object>>> P selectMapPage(P page, S predicate, Function<LambdaQueryWrapper<S>, LambdaQueryWrapper<S>> predicateExpression) {
        LambdaQueryWrapper<S> wrapper = buildLambdaQueryWrapper(predicate);
        LambdaQueryWrapper<S> next = predicateExpression == null ? wrapper : predicateExpression.apply(wrapper);
        return selectMapsPage(page, next);
    }
}




























