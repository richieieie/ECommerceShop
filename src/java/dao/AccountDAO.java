/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Account;
import model.AccountDTO;

/**
 *
 * @author Trung
 */
public interface AccountDAO {
    AccountDTO login(String a, String password);
    List<AccountDTO> getAll();
    List<AccountDTO> getAllEmployees();
    Account getByAccount(String a);
    boolean updateByAccount(Account a);
    boolean updatePassword(String a, String password, String newPassword);
    boolean deleteByAccount(String account);
    boolean activeByAccount(String account);
    boolean saveOne(Account a);
}
