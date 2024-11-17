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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Account;
import model.AccountDTO;

/**
 *
 * @author Trung
 */
public class AccountDAOImpl implements AccountDAO {

    private Connection con;

    public AccountDAOImpl(ServletContext sc) {
        DBContext db = new DBContext(sc);
        con = db.getConnection();
    }

    @Override
    public AccountDTO login(String account, String password) {
        AccountDTO aDTO = null;
        Account a = new Account();
        try {
            String sql = "SELECT account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts WHERE account = ? AND pass = ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a.setAccount(rs.getString("account"));
                a.setPass(rs.getString("pass"));
                a.setLastName(rs.getString("lastName"));
                a.setFirstName(rs.getString("firstName"));
                a.setBirthday(rs.getDate("birthday"));
                a.setGender(rs.getBoolean("gender"));
                a.setPhone(rs.getString("phone"));
                a.setIsUse(rs.getBoolean("isUse"));
                a.setRoleInSystem(rs.getInt("roleInSystem"));
                aDTO = a.mapToDTO();
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aDTO;
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> accounts = null;
        try {
            String sql = "SELECT account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO a = new AccountDTO();
                a.setAccount(rs.getString("account"));
                a.setLastName(rs.getString("lastName"));
                a.setFirstName(rs.getString("firstName"));
                a.setBirthday(rs.getDate("birthday"));
                a.setGender(rs.getBoolean("gender"));
                a.setPhone(rs.getString("phone"));
                a.setIsUse(rs.getBoolean("isUse"));
                a.setRoleInSystem(rs.getInt("roleInSystem"));
                accounts.add(a);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accounts;
    }
    
    @Override
    public List<AccountDTO> getAllEmployees() {
        List<AccountDTO> accounts = null;
        try {
            String sql = "SELECT account, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts Where roleInSystem = 1 OR roleInSystem = 2";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO a = new AccountDTO();
                a.setAccount(rs.getString("account"));
                a.setLastName(rs.getString("lastName"));
                a.setFirstName(rs.getString("firstName"));
                a.setBirthday(rs.getDate("birthday"));
                a.setGender(rs.getBoolean("gender"));
                a.setPhone(rs.getString("phone"));
                a.setIsUse(rs.getBoolean("isUse"));
                a.setRoleInSystem(rs.getInt("roleInSystem"));
                accounts.add(a);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accounts;
    }

    @Override
    public boolean saveOne(Account a) {
        int rows = 0;
        try {
            String sql = "insert into accounts (account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, a.getAccount());
            stmt.setString(2, a.getPass());
            stmt.setString(3, a.getLastName());
            stmt.setString(4, a.getFirstName());
            stmt.setDate(5, a.getBirthday());
            stmt.setBoolean(6, a.isGender());
            stmt.setString(7, a.getPhone());
            stmt.setBoolean(8, a.isIsUse());
            stmt.setInt(9, a.getRoleInSystem());
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }

    @Override
    public Account getByAccount(String account) {
        Account a = null;
        try {
            String sql = "SELECT account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts WHERE account = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = new Account();
                a.setAccount(rs.getString("account"));
                a.setPass(rs.getString("pass"));
                a.setLastName(rs.getString("lastName"));
                a.setFirstName(rs.getString("firstName"));
                a.setBirthday(rs.getDate("birthday"));
                a.setGender(rs.getBoolean("gender"));
                a.setPhone(rs.getString("phone"));
                a.setIsUse(rs.getBoolean("isUse"));
                a.setRoleInSystem(rs.getInt("roleInSystem"));
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return a;
    }

    @Override
    public boolean updateByAccount(Account a) {
        int rows = 0;
        try {
            String sql = "update accounts set lastName = ?, firstName = ?, birthday = ?, gender = ?, phone = ?, isUse = ?, roleInSystem = ? where account = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, a.getLastName());
            stmt.setString(2, a.getFirstName());
            stmt.setDate(3, a.getBirthday());
            stmt.setBoolean(4, a.isGender());
            stmt.setString(5, a.getPhone());
            stmt.setBoolean(6, a.isIsUse());
            stmt.setInt(7, a.getRoleInSystem());
            stmt.setString(8, a.getAccount());
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }

    @Override
    public boolean updatePassword(String account, String password, String newPassword) {
        int rows = 0;
        try {
            String sql = "update accounts set pass = ? where account = ? and pass = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, account);
            stmt.setString(3, password);
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }

    @Override
    public boolean deleteByAccount(String account) {
        int rows = 0;
        try {
            String sql = "update accounts set isUse = 0 where account = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account);
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }
    
    
    @Override
    public boolean activeByAccount(String account) {
        int rows = 0;
        try {
            String sql = "update accounts set isUse = 1 where account = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account);
            rows = stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows > 0;
    }
}
