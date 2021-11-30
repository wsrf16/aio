//package com.aio.portable.swiss.sugar;
//
//import com.aio.portable.swiss.sugar.type.CollectionSugar;
//import sun.reflect.ConstructorAccessor;
//import sun.reflect.FieldAccessor;
//import sun.reflect.ReflectionFactory;
//
//import java.lang.reflect.AccessibleObject;
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class EnumSugar {
//    private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
//
//    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException,
//            IllegalAccessException {
//
//        // let's make the field accessible
//        field.setAccessible(true);
//
//        // next we change the modifier in the Field instance to
//        // not be final anymore, thus tricking reflection into
//        // letting us modify the static final field
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
//        modifiersField.setAccessible(true);
//        int modifiers = modifiersField.getInt(field);
//
//        // blank out the final bit in the modifiers int
//        modifiers &= ~Modifier.FINAL;
//        modifiersField.setInt(field, modifiers);
//
//        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
//        fa.set(target, value);
//    }
//
//    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException,
//            IllegalAccessException {
//        for (Field field : Class.class.getDeclaredFields()) {
//            if (field.getName().contains(fieldName)) {
//                AccessibleObject.setAccessible(new Field[] { field }, true);
//                setFailsafeFieldValue(field, enumClass, null);
//                break;
//            }
//        }
//    }
//
//    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
//        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
//        blankField(enumClass, "enumConstants"); // IBM JDK
//    }
//
//    private static <T> T buildEnum(Class<T> enumClass, String value, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception {
//        Class<?>[] paramClasses = CollectionSugar.concat(new Class[]{String.class},
//                new Class[]{int.class},
//                additionalTypes);
//        Object[] paramValues = CollectionSugar.concat(new Object[]{value},
//                new Object[]{Integer.valueOf(ordinal)},
//                additionalValues);
//
//        ConstructorAccessor constructorAccessor = reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(paramClasses));
//        return enumClass.cast(constructorAccessor.newInstance(paramValues));
//    }
//
//    private static <T extends Enum<T>> T[] getEnums(Class<T> enumType) {
//        // 0. Sanity checks
//        if (!Enum.class.isAssignableFrom(enumType)) {
//            throw new RuntimeException("class " + enumType.getTypeName() + " is not an instance of Enum");
//        }
//
//        // 1. Lookup "$VALUES" holder in enum class and get previous enum instances
//        Field valuesField = getValuesField(enumType);
//        AccessibleObject.setAccessible(new Field[] { valuesField }, true);
//
//        // 2. Copy it
//        T[] previousValues;
//        try {
//            previousValues = (T[]) valuesField.get(enumType);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//        return previousValues;
//    }
//
//    private static <T extends Enum<T>> List<T> getEnumList(Class<T> enumType) {
//        T[] previousValues = getEnums(enumType);
//        List<T> values = new ArrayList<>(Arrays.asList(previousValues));
//        return values;
//    }
//
//    private static <T> Field getValuesField(Class<T> enumType) {
//        Field valuesField = null;
//        Field[] fields = enumType.getDeclaredFields();
//        for (Field field : fields) {
//            if (field.getName().contains("$VALUES")) {
//                valuesField = field;
//                break;
//            }
//        }
//        return valuesField;
//    }
//
//    /**
//     * Add an enum instance to the enum class given as argument
//     *
//     * @param <T> the type of the enum (implicit)
//     * @param enumType the class of the enum to be modified
//     * @param enumName the name of the new enum instance to be added to the class.
//     */
//    @SuppressWarnings("unchecked")
//    public static <T extends Enum<T>> T addEnum(Class<T> enumType, String enumName, Class<?>[] additionalTypes, Object[] additionalValues) {
//        try {
//            Field valuesField = getValuesField(enumType);
//
//            List<T> values = getEnumList(enumType);
//            int ordinal = values.size();
//            T newEnum = buildEnum(enumType, enumName, ordinal, additionalTypes, additionalValues);
//            // 4. add new value
//            values.add(newEnum);
//            // 5. Set new values field
//            T[] enums = CollectionSugar.toArray(values, enumType);
//
//            setFailsafeFieldValue(valuesField, null, enums);
//            // 6. Clean enum cache
//            cleanEnumCache(enumType);
//            return newEnum;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
