/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Category;

/**
 *
 * @author Trung
 */
public interface CategoryDAO {

    List<Category> getAll();

    Category getById(int typeId);

    Category saveOne(Category c);

    boolean updateOne(Category c);

    boolean deleteById(int typeId);
}
