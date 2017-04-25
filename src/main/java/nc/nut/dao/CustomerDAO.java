/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao;

import nc.nut.entity.Customer;
import nc.nut.user.User;

import java.util.List;

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
