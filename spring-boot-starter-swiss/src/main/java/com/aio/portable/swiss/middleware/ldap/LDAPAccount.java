package com.aio.portable.swiss.middleware.ldap;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

//@Entry(base = "dc=dddingnet,dc=cn", objectClasses = "person")
@Entry(objectClasses = {"top", "person", "organizationalPerson", "user"})
class LDAPAccount {

    @Id
    private Name id;
    @Attribute(name = "employeeID")
    private String employeeID;
    @Attribute(name = "cn")
    private String cn;
    @Attribute(name = "department")
    private String department;
    @Attribute(name = "telephoneNumber")
    private String telephoneNumber;
    @Attribute(name = "sn")
    private String sn;

    private String userPassword;

    @Attribute(name = "sAMAccountName")
    private String samAccountName;

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSamAccountName() {
        return samAccountName;
    }

    public void setSamAccountName(String samAccountName) {
        this.samAccountName = samAccountName;
    }
}
