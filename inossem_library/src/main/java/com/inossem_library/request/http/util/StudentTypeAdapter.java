//package com.inossem_library.request.http.util;
//
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Date;
//
///**
// * @author Leo E-mail:changwen.sun@inossem.com 2019/12/6 10:54
// * @version 1.0.8
// * @since 1.0.8
// */
//
//public class StudentTypeAdapter<T> extends TypeAdapter<T> {
//
//    @Override
//    public void write(JsonWriter out, T value) throws IOException {
//        out.beginObject();
//        // 拿到该类
//        Class<?> clz = value.getClass();
//        // 获取实体类的所有属性，返回Field数组
//        Field[] fields = clz.getDeclaredFields();
//        for (Field field : fields) {// --for() begin
//            if (!field.getGenericType().toString().equals(
//                    "class java.lang.String")) {
//                return;
//            }
//            try {
//                Object getMethodValue = getGetMethodValue(field, value);
//                if (getMethodValue instanceof String) {
//                    out.name(field.getName()).value((String) getMethodValue);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        out.endObject();
//    }
//
//    @Override
//    public T read(JsonReader in) throws IOException {
//        T t = new T();
//        in.beginObject();
//
//        while (in.hasNext()) {
//            String field = in.nextName();
//            if ("name".equals(field)) {
//                student.setName(in.nextString());
//            } else if ("age".equals(field)) {
//                student.setName(in.nextString());
//            } else if ("score".equals(field)) {
//                student.setScore(in.nextString());
//            }else{
//                in.skipValue();
//            }
//        }
//
//        in.endObject();
//        return T;
//    }
//
//    /**
//     * 根据属性，获取get方法
//     */
//    public Object getGetMethodValue(Field field, T object) throws Exception {
//        // 如果类型是String
//        if (field.getGenericType().toString().equals(
//                "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
//            // 拿到该属性的gettet方法
//            /**
//             * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
//             * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
//             * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
//             */
//            Method m = (Method) object.getClass().getMethod(
//                    "get" + getMethodName(field.getName()));
//
//            String val = (String) m.invoke(object);// 调用getter方法获取属性值
//            if (val != null) {
//                return val;
//            }
//        }
//
//        // 如果类型是Integer
//        if (field.getGenericType().toString().equals(
//                "class java.lang.Integer")) {
//            Method m = (Method) object.getClass().getMethod(
//                    "get" + getMethodName(field.getName()));
//            Integer val = (Integer) m.invoke(object);
//            if (val != null) {
//                System.out.println("Integer type:" + val);
//            }
//
//        }
//
//        // 如果类型是Double
//        if (field.getGenericType().toString().equals(
//                "class java.lang.Double")) {
//            Method m = (Method) object.getClass().getMethod(
//                    "get" + getMethodName(field.getName()));
//            Double val = (Double) m.invoke(object);
//            if (val != null) {
//                return val;
//            }
//
//        }
//
//        // 如果类型是Boolean 是封装类
//        if (field.getGenericType().toString().equals(
//                "class java.lang.Boolean")) {
//            Method m = (Method) object.getClass().getMethod(
//                    field.getName());
//            Boolean val = (Boolean) m.invoke(object);
//            if (val != null) {
//                return val;
//            }
//
//        }
//
//        // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
//        // 反射找不到getter的具体名
//        if (field.getGenericType().toString().equals("boolean")) {
//            Method m = (Method) object.getClass().getMethod(
//                    field.getName());
//            Boolean val = (Boolean) m.invoke(object);
//            if (val != null) {
//                return val;
//            }
//
//        }
//        // 如果类型是Date
//        if (field.getGenericType().toString().equals(
//                "class java.util.Date")) {
//            Method m = (Method) object.getClass().getMethod(
//                    "get" + getMethodName(field.getName()));
//            Date val = (Date) m.invoke(object);
//            if (val != null) {
//                return val;
//            }
//
//        }
//        // 如果类型是Short
//        if (field.getGenericType().toString().equals(
//                "class java.lang.Short")) {
//            Method m = (Method) object.getClass().getMethod(
//                    "get" + getMethodName(field.getName()));
//            Short val = (Short) m.invoke(object);
//            if (val != null) {
//                return val;
//            }
//
//        }
//        // 如果还需要其他的类型请自己做扩展
//        return null;
//    }
//
//    // 把一个字符串的第一个字母大写、效率是最高的、
//    private String getMethodName(String fildeName) throws Exception {
//        byte[] items = fildeName.getBytes();
//        items[0] = (byte) ((char) items[0] - 'a' + 'A');
//        return new String(items);
//    }
//}
