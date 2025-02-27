package com.aio.portable.park.endpoint.http;

public class PackageController {

    public static String getPackageName() {
        return PackageController.class.getPackage().getName();
    }
}
