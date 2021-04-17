//package com.aio.portable.swiss.hamlet.security.authorization.code.storage;
//
//import com.aio.portable.swiss.hamlet.security.authorization.code.object.AuthorizationCodeObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MemoryAuthorizationCodeStorage implements AuthorizationCodeStorage {
//
//    private Map<String, AuthorizationCodeObject> map = new HashMap<>();
//
//    public Map<String, AuthorizationCodeObject> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, AuthorizationCodeObject> map) {
//        this.map = map;
//    }
//
//    public void set(String code, AuthorizationCodeObject authorizationCode) {
//        map.put(code, authorizationCode);
//    }
//
//    public AuthorizationCodeObject get(String code) {
//        return map.get(code);
//    }
//
//    public AuthorizationCodeObject remove(String code) {
//        return map.remove(code);
//    }
//}
