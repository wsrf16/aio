package com.aio.portable.swiss.suite.storage.db.mybatis;

import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.lang.invoke.SerializedLambda;

public class LambdaSFunction<T, R> implements SFunction<T, R> {
    Class<?> capturingClass;
    String implMethodName;
    String instantiatedMethodType;
    String functionalInterfaceMethodName;

    /*
    {
    "capturingClass" : "com/aio/portable/park/test/MyDatabaseTest",
    "functionalInterfaceClass" : "com/aio/portable/swiss/sugar/resource/function/LambdaFunction",
    "functionalInterfaceMethodName" : "apply",
    "functionalInterfaceMethodSignature" : "(Ljava/lang/Object;)Ljava/lang/Object;",
    "implClass" : "com/aio/portable/park/dao/master/model/UserConditionDTO",
    "implMethodName" : "getIdIn",
    "implMethodSignature" : "()Ljava/util/List;",
    "implMethodKind" : 5,
    "instantiatedMethodType" : "(Lcom/aio/portable/park/dao/master/model/UserConditionDTO;)Ljava/lang/Object;",
    "capturedArgCount" : 0
}

---------------------------------{
    "capturingClass" : "com/aio/portable/swiss/suite/storage/db/mybatis/LambdaSFunction",
    "functionalInterfaceClass" : "com/aio/portable/swiss/sugar/resource/function/LambdaFunction",
    "functionalInterfaceMethodName" : "apply",
    "functionalInterfaceMethodSignature" : "(Ljava/lang/Object;)Ljava/lang/Object;",
    "implClass" : "com/aio/portable/park/dao/master/model/UserConditionDTO",
    "implMethodName" : "getId",
    "implMethodSignature" : "()Ljava/util/List;",
    "implMethodKind" : 5,
    "instantiatedMethodType" : "(Lcom.aio.portable.swiss.suite.storage.db.mybatis.LambdaSFunction;)Ljava/lang/Object;",
    "capturedArgCount" : 0
}
---------------------------------
{
    "capturingClass" : "com/aio/portable/park/test/MyDatabaseTest",
    "functionalInterfaceClass" : "com/aio/portable/swiss/sugar/resource/function/LambdaFunction",
    "functionalInterfaceMethodName" : "apply",
    "functionalInterfaceMethodSignature" : "(Ljava/lang/Object;)Ljava/lang/Object;",
    "implClass" : "com/aio/portable/park/dao/master/model/User",
    "implMethodName" : "getId",
    "implMethodSignature" : "()Ljava/lang/Integer;",
    "implMethodKind" : 5,
    "instantiatedMethodType" : "(Lcom/aio/portable/park/dao/master/model/UserConditionDTO;)Ljava/lang/Object;",
    "capturedArgCount" : 0
}
     */

    public LambdaSFunction(String implMethodName, Class<?> instantiatedClazz) {
        Class<Object> capturingClass = ClassLoaderSugar.forName(Thread.currentThread().getStackTrace()[2].getClassName());
        this.capturingClass = capturingClass.getEnclosingClass() == null ? capturingClass : capturingClass.getEnclosingClass();
        this.functionalInterfaceMethodName = "apply";
        this.implMethodName = implMethodName;
        this.instantiatedMethodType = "(Lcom/aio/portable/park/dao/master/model/UserConditionDTO;)Ljava/lang/Object;";
        String instantiatedMethodType = StringSugar.trimEnd(ClassSugar.convertClassNameToResourcePath(instantiatedClazz.getName()), ".class");
        this.instantiatedMethodType = "(L" + instantiatedMethodType + ";)Ljava/lang/Object;";
//        String instantiatedMethodType1 = "com/aio/portable/park/dao/master/model/UserConditionDTO";
//        this.instantiatedMethodType = "(L" + instantiatedMethodType1 + ";)Ljava/lang/Object;";


    }

    public SerializedLambda writeReplace() {
//        String functionalInterfaceClass = "com/aio/portable/swiss/sugar/resource/function/LambdaFunction";
//        String functionalInterfaceMethodSignature = "(Ljava/lang/Object;)Ljava/lang/Object;";
        String implMethodSignature = "()Ljava/util/List;";
//        String implClass = "com/aio/portable/park/dao/master/model/UserConditionDTO";
//        implClass = "com/aio/portable/park/dao/master/model/User";

                SerializedLambda serializedLambda = new SerializedLambda(this.capturingClass, null, functionalInterfaceMethodName, null, 5, null, this.implMethodName, implMethodSignature, this.instantiatedMethodType, new Object[0]);
        return serializedLambda;
    }

    @Override
    public R apply(T o) {
        return null;
    }
}
