package com.aio.portable.swiss.spring.factories.autoconfigure.properties;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;

//@ConditionalOnClass(ApiInfo.class)
//@ConditionalOnProperty(prefix = "swagger.api-info", name = "title")
//@ConfigurationProperties(prefix = "swagger")
public class Swagger2Properties implements SwaggerProperties {
    private boolean enabled = true;

//    private String version = "1.0";

    private com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties.ApiInfo apiInfo;
//    springfox.documentation.service

    private List<ResponseMessage> responseMessageList;

    private String packageName;

    private String host = "";

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }


    public com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties.ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties.ApiInfo apiInfo) {
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

    public String getHost() {
        return host == null ? "" : host;
    }

    public void setHost(String host) {
        this.host = host;
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
                        title, description, version, termsOfServiceUrl, contact == null ? null : contact.toContact(), license, licenseUrl, new ArrayList<VendorExtension>()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return apiInfo;
        }
    }

}
