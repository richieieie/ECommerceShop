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
import model.AccountDTO;
import model.Category;
import model.Product;

/**
 *
 * @author Trung
 */
public class ProductDAOImpl implements ProductDAO {

    private final Connection con;

    public ProductDAOImpl(ServletContext sc) {
        DBContext db = new DBContext(sc);
        con = db.getConnection();
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList();
        try {
            String sql = "select a.account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem, productId, productName,"
                    + " productImage, brief, postedDate, p.typeId, categoryName, memo, unit, price, discount from accounts a, categories c, products p"
                    + " where a.account = p.account and p.typeId = c.typeId order by productName";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO aDTO = new AccountDTO(rs.getString("account"), rs.getString("lastname"), rs.getString("firstname"),
                        rs.getDate("birthday"), rs.getBoolean("gender"), rs.getString("phone"), rs.getBoolean("isUse"), rs.getInt("roleInSystem"));
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                Product p = new Product(rs.getString("productId"), rs.getString("productName"), rs.getString("productImage"), rs.getString("brief"),
                        rs.getDate("postedDate"), c, aDTO, rs.getString("unit"), rs.getInt("price"), rs.getInt("discount"));

                products.add(p);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            products = null;
        }

        return products;
    }

    @Override
    public List<Product> getByAccount(String account) {
        List<Product> products = new ArrayList();
        try {
            String sql = "select a.account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem, productId, productName,"
                    + " productImage, brief, postedDate, p.typeId, categoryName, memo, unit, price, discount from accounts a, categories c, products p"
                    + " where a.account = p.account and p.typeId = c.typeId and a.account = ? order by productName";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO aDTO = new AccountDTO(rs.getString("account"), rs.getString("lastname"), rs.getString("firstname"),
                        rs.getDate("birthday"), rs.getBoolean("gender"), rs.getString("phone"), rs.getBoolean("isUse"), rs.getInt("roleInSystem"));
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                Product p = new Product(rs.getString("productId"), rs.getString("productName"), rs.getString("productImage"), rs.getString("brief"),
                        rs.getDate("postedDate"), c, aDTO, rs.getString("unit"), rs.getInt("price"), rs.getInt("discount"));

                products.add(p);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            products = null;
        }

        return products;
    }

    @Override
    public List<Product> getByCategory(int typeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getById(String id) {
        Product p = null;
        try {
            String sql = "select a.account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem, productId, productName,"
                    + " productImage, brief, postedDate, p.typeId, categoryName, memo, unit, price, discount from accounts a, categories c, products p"
                    + " where a.account = p.account and p.typeId = c.typeId and productId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO aDTO = new AccountDTO(rs.getString("account"), rs.getString("lastname"), rs.getString("firstname"),
                        rs.getDate("birthday"), rs.getBoolean("gender"), rs.getString("phone"), rs.getBoolean("isUse"), rs.getInt("roleInSystem"));
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                p = new Product(rs.getString("productId"), rs.getString("productName"), rs.getString("productImage"), rs.getString("brief"),
                        rs.getDate("postedDate"), c, aDTO, rs.getString("unit"), rs.getInt("price"), rs.getInt("discount"));

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

    @Override
    public Product saveOne(Product product) {
        int result = 0;
        try {
            String sql = "insert into products(productId, productName, productImage, brief, typeId, account, unit, price, discount) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getProductImage());
            stmt.setString(4, product.getBrief());
            stmt.setInt(5, product.getCategory().getTypeId());
            stmt.setString(6, product.getAccountDTO().getAccount());
            stmt.setString(7, product.getUnit());
            stmt.setInt(8, product.getPrice());
            stmt.setInt(9, product.getDiscount());
            result = stmt.executeUpdate();

            con.close();
            if (result > 0) {
                return product;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean deleteById(String productId) {
        int result = 0;
        try {
            String sql = "delete products where productId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, productId);
            result = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result > 0;
    }

    @Override
    public boolean updateById(Product product) {
        int result = 0;
        try {
            String sql = "update products set productName = ?, productImage = ?, brief = ?, typeId = ?, account = ?, unit = ?, price = ?, discount = ? where productId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductImage());
            stmt.setString(3, product.getBrief());
            stmt.setInt(4, product.getCategory().getTypeId());
            stmt.setString(5, product.getAccountDTO().getAccount());
            stmt.setString(6, product.getUnit());
            stmt.setInt(7, product.getPrice());
            stmt.setInt(8, product.getDiscount());
            stmt.setString(9, product.getProductId());
            result = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result > 0;
    }

    @Override
    public List<Product> getByPageCategoriesName(int[] typeIds, int page, int pageSize, String name) {
        int typeIdsLength = 0;
        List<Product> products = new ArrayList();
        if (name == null) {
            name = "";
        }
        try {
            // set up statement
            String sql = "select a.account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem, productId, productName,"
                    + " productImage, brief, postedDate, p.typeId, categoryName, memo, unit, price, discount from accounts a, categories c, products p"
                    + " where a.account = p.account and p.typeId = c.typeId and productName like ?";
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    if (i != 0) {
                        sql += " OR p.typeId = ?";
                    } else {
                        sql += " and (p.typeId = ?";
                    }
                }
                sql += ")";
            }
            sql += " order by productName offset ? rows fetch next ? rows only";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            if (typeIds != null) {
                typeIdsLength = typeIds.length;
                for (int i = 0; i < typeIds.length; i++) {
                    stmt.setInt(i + 2, typeIds[i]);
                }
            }
            stmt.setInt(typeIdsLength + 2, page * pageSize);
            stmt.setInt(typeIdsLength + 3, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO aDTO = new AccountDTO(rs.getString("account"), rs.getString("lastname"), rs.getString("firstname"),
                        rs.getDate("birthday"), rs.getBoolean("gender"), rs.getString("phone"), rs.getBoolean("isUse"), rs.getInt("roleInSystem"));
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                Product p = new Product(rs.getString("productId"), rs.getString("productName"), rs.getString("productImage"), rs.getString("brief"),
                        rs.getDate("postedDate"), c, aDTO, rs.getString("unit"), rs.getInt("price"), rs.getInt("discount"));

                products.add(p);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            products = null;
        }

        return products;
    }

    @Override
    public int countPagesByCategoriesName(int[] typeIds, int pageSize, String name) {
        int count = 0;
        if (name == null) {
            name = "";
        }

        try {
            String sql = "SELECT COUNT(*) FROM products WHERE productName like ?";
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    if (i != 0) {
                        sql += " OR typeId = ?";
                    } else {
                        sql += " AND (typeId = ?";
                    }
                }
                sql += ")";
            }

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    stmt.setInt(i + 2, typeIds[i]);
                }
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) Math.ceil((double) count / pageSize);
    }

    @Override
    public List<Product> getByPageCategoriesAccountName(int[] typeIds, int page, int pageSize, String account, String name) {
        int typeIdsLength = 0;
        List<Product> products = new ArrayList();
        if (name == null) {
            name = "";
        }

        try {
            // set up statement
            String sql = "select a.account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem, productId, productName,"
                    + " productImage, brief, postedDate, p.typeId, categoryName, memo, unit, price, discount from accounts a, categories c, products p"
                    + " where a.account = p.account and p.typeId = c.typeId and productName like ?";
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    if (i != 0) {
                        sql += " OR p.typeId = ?";
                    } else {
                        sql += " and (p.typeId = ?";
                    }
                }
                sql += ")";
            }
            sql += " and a.account = ?";
            sql += " order by productName offset ? rows fetch next ? rows only";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            if (typeIds != null) {
                typeIdsLength = typeIds.length;
                for (int i = 0; i < typeIds.length; i++) {
                    stmt.setInt(i + 2, typeIds[i]);
                }
            }
            stmt.setString(typeIdsLength + 2, account);
            stmt.setInt(typeIdsLength + 3, page * pageSize);
            stmt.setInt(typeIdsLength + 4, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO aDTO = new AccountDTO(rs.getString("account"), rs.getString("lastname"), rs.getString("firstname"),
                        rs.getDate("birthday"), rs.getBoolean("gender"), rs.getString("phone"), rs.getBoolean("isUse"), rs.getInt("roleInSystem"));
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                Product p = new Product(rs.getString("productId"), rs.getString("productName"), rs.getString("productImage"), rs.getString("brief"),
                        rs.getDate("postedDate"), c, aDTO, rs.getString("unit"), rs.getInt("price"), rs.getInt("discount"));

                products.add(p);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            products = null;
        }

        return products;
    }

    @Override
    public int countPagesByCategoriesAccountName(int[] typeIds, int pageSize, String account, String name) {
        int count = 0;
        if (name == null) {
            name = "";
        }

        try {
            String sql = "SELECT COUNT(*) FROM products";
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    if (i != 0) {
                        sql += " OR typeId = ?";
                    } else {
                        sql += " WHERE (typeId = ?";
                    }
                }
                sql += ")";
                sql += " AND account = ? and productName like ?";
            } else {
                typeIds = new int[]{};
                sql += " WHERE account = ? and productName like ?";
            }

            PreparedStatement stmt = con.prepareStatement(sql);
            if (typeIds != null) {
                for (int i = 0; i < typeIds.length; i++) {
                    stmt.setInt(i + 1, typeIds[i]);
                }
            }
            stmt.setString(typeIds.length + 1, account);
            stmt.setString(typeIds.length + 2, "%" + name + "%");
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) Math.ceil((double) count / pageSize);
    }
}
