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
//      productId varchar(10) primary key not null,
//	productName nvarchar(500) not NULL,
//	productImage varchar(max) DEFAULT '',
//	brief nvarchar(2000) DEFAULT '',
//	postedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
//	typeId int not null references categories(typeId),
//	account varchar(20) not null foreign key references accounts(account) on update cascade,
//	unit nvarchar(32) default N'pcs',
//	price integer default 0,
//	discount integer default 0 check (discount>=0 and discount<=100)
public class Product {
    private String productId;
    private String productName;
    private String productImage;
    private String brief;
    private Date postedDate;
    private Category category;
    private AccountDTO accountDTO;
    private String unit;
    private int price;
    private int discount;

    public Product() {
    }

    public Product(String productId, String productName, String productImage, String brief, Date postedDate, Category category, AccountDTO accountDTO, String unit, int price, int discount) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.brief = brief;
        this.postedDate = postedDate;
        this.category = category;
        this.accountDTO = accountDTO;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
    }

    public Product(String productId, String productName, String productImage, String brief, Category category, AccountDTO accountDTO, String unit, int price, int discount) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.brief = brief;
        this.category = category;
        this.accountDTO = accountDTO;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
    }

    
    
    public Product(String productId, String productName, String brief, Category category, AccountDTO accountDTO, String unit, int price, int discount) {
        this.productId = productId;
        this.productName = productName;
        this.brief = brief;
        this.category = category;
        this.accountDTO = accountDTO;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.productId);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", productImage=" + productImage + ", brief=" + brief + ", postedDate=" + postedDate + ", category=" + category + ", accountDTO=" + accountDTO + ", unit=" + unit + ", price=" + price + ", discount=" + discount + '}';
    }    
}
