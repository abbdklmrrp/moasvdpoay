/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.complaint;


import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * @author Alistratenko Nikita
 */
public interface ComplaintDAO extends Dao<Complaint> {

    List<Complaint> getByCSRId(int id);

    List<Complaint> getByOrderId(int id);

    List<Complaint> getByPlaceId(int id);

    List<Complaint> getByStatusID(int id);

    boolean changeStatus(int complaintId, int statusId);

    boolean changeDescription(int complaintId, String description);

}
