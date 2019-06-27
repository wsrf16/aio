package com.york.portable.swiss.extra;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

public class Swagger2Properties {
    private boolean enable = true;

//    private String version = "1.0";

    private Swagger2Properties.ApiInfo apiInfo;
//    springfox.documentation.service

    private List<ResponseMessage> responseMessageList;

    private String packageName;

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }

    public static Swagger2Properties build(String packageName, List<ResponseMessage> responseMessageList) {
        Swagger2Properties swagger2Properties = new Swagger2Properties();
        swagger2Properties.setPackageName(packageName);
        swagger2Properties.setResponseMessageList(responseMessageList);
        return swagger2Properties;
    }

    public Swagger2Properties.ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(Swagger2Properties.ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public List<ResponseMessage> getResponseMessageList() {
        return responseMessageList;
    }

    public void setResponseMessageList(List<ResponseMessage> responseMessageList) {
        this.responseMessageList = responseMessageList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private springfox.documentation.service.ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CRM接口在线文档")
                .description("包括C1、C2、拍卖、门店C1、门店C2等业务")
                .termsOfServiceUrl("www.taoche.com")
                .contact(new springfox.documentation.service.Contact("crm", "www.taoche.com","yu.zhao@taoche.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .version(version)
                .build();
//        return new ApiInfoBuilder()
//                .title("FIV(Financial Identity Verification)金融认证基础服务接口在线文档")
//                .description("提供金融认证基础服务，主要功能包括实名认证、银行卡三要素、手机号三要素、四要素等服务")
//                .termsOfServiceUrl("www.taoche.com")
//                .contact(new Contact("crm", "www.taoche.com","yu.zhao@taoche.com"))
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .version(version)
//                .build();
    }


    public static class Contact {
        private String name;
        private String url;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public springfox.documentation.service.Contact toContact() {
            return new springfox.documentation.service.Contact(name, url, email);
        }
    }

    public static class ApiInfo {
        private String version;
        private String title;
        private String description;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
        private Contact contact;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public springfox.documentation.service.ApiInfo toSwaggerApiInfo() {
            springfox.documentation.service.ApiInfo apiInfo = null;
            try {
                apiInfo = new springfox.documentation.service.ApiInfo(
                        title, description, version, termsOfServiceUrl, contact == null ? null : contact.toContact(), license, licenseUrl, new ArrayList()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return apiInfo;
        }
    }

}
