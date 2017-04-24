/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao;

import java.util.List;
import nc.nut.entity.Customer;
import nc.nut.entity.User;

/**
 *
 * @author Alistratenko Nikita
 */
public interface CustomerDAO {

    Customer save(Customer customer);

    boolean delete(Customer customer);

    Customer getById(int id);

    boolean changeSecretKey(int customerId, String newSecretKey);

    boolean changeInvoice(int customerId, int newInvoice);

    boolean changeName(int customerId, String newName);

    List<User> getAllUsers(int customerId);

}
