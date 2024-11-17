/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Category;

/**
 *
 * @author Trung
 */
public class CategoryDAOImpl implements CategoryDAO {

    private final Connection con;

    public CategoryDAOImpl(ServletContext sc) {
        DBContext db = new DBContext(sc);
        con = db.getConnection();
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = null;
        try {
            categories = new ArrayList<>();
            String sql = "select typeId, categoryName, memo from categories order by categoryName";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryname"), rs.getString("memo"));
                categories.add(c);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }

    @Override
    public boolean deleteById(int typeId) {
        int rows = 0;
        try {
            String sql = "delete categories where typeId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, typeId);
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }

    @Override
    public Category saveOne(Category c) {
        int rows = 0;
        try {
            String sql = "insert into categories (categoryName, memo) values (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getCategoryName());
            stmt.setString(2, c.getMemo());
            rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int typeId = generatedKeys.getInt(1);
                    c.setTypeId(typeId);
                    System.out.println(c);
                }
                
                return c;
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Category getById(int typeId) {
        Category category = null;
        try {
            String sql = "select typeId, categoryName, memo from categories where typeId = ? order by categoryName ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, typeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                category = new Category(rs.getInt("typeId"), rs.getString("categoryname"), rs.getString("memo"));
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }

    @Override
    public boolean updateOne(Category c) {
        int rows = 0;
        try {
            String sql = "update categories set categoryName = ?, memo = ? where typeId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, c.getCategoryName());
            stmt.setString(2, c.getMemo());
            stmt.setInt(3, c.getTypeId());
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }

}
