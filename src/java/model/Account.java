/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Trung
 */
//      account varchar(20) primary key not null,
//	pass varchar(20) not null,
//	lastName nvarchar(50) null,
//	firstName nvarchar(30) not null,
//	birthday datetime ,
//	gender bit default 1,		-- 1: male | 0: female
//	phone nvarchar(20),			-- Only digits, begin with 03|05|07|08|09
//	isUse bit default 0,		-- 1: being used | 0: is prevented
//	roleInSystem int default 0	-- Role in system {1: admin - others: staff
public class Account {
    private String account;
    private String pass;
    private String lastName;
    private String firstName;
    private Date birthday;
    private boolean gender;
    private String phone;
    private boolean isUse;
    private int roleInSystem;

    public Account() {
    }

    public Account(String account, String pass, String lastName, String firstName, Date birthday, boolean gender, String phone, boolean isUse, int roleInSystem) {
        this.account = account;
        this.pass = pass;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.isUse = isUse;
        this.roleInSystem = roleInSystem;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsUse() {
        return isUse;
    }

    public void setIsUse(boolean isUse) {
        this.isUse = isUse;
    }

    public int getRoleInSystem() {
        return roleInSystem;
    }

    public void setRoleInSystem(int roleInSystem) {
        this.roleInSystem = roleInSystem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.account);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account{" + "account=" + account + ", pass=" + pass + ", lastName=" + lastName + ", firstName=" + firstName + ", birthday=" + birthday + ", gender=" + gender + ", phone=" + phone + ", isUse=" + isUse + ", roleInSystem=" + roleInSystem + '}';
    }
    
    public AccountDTO mapToDTO() {
        AccountDTO aDTO = new AccountDTO();
        aDTO.setAccount(account);
        aDTO.setBirthday(birthday);
        aDTO.setFirstName(firstName);
        aDTO.setLastName(lastName);
        aDTO.setIsUse(isUse);
        aDTO.setGender(gender);
        aDTO.setPhone(phone);
        aDTO.setRoleInSystem(roleInSystem);
        return aDTO;
    }
}
