/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Product;

/**
 *
 * @author Trung
 */
public interface ProductDAO {

    List<Product> getAll();

    List<Product> getByPageCategoriesName(int[] typeIds, int page, int pageSize, String name);

    int countPagesByCategoriesName(int[] typeIds, int pageSize, String name);
    
    List<Product> getByPageCategoriesAccountName(int[] typeIds, int page, int pageSize, String account, String name);

    List<Product> getByAccount(String account);
    
    int countPagesByCategoriesAccountName(int[] typeIds, int pageSize, String account, String name);

    Product getById(String productId);

    Product saveOne(Product product);

    List<Product> getByCategory(int typeId);

    boolean deleteById(String productId);

    boolean updateById(Product product);
}
