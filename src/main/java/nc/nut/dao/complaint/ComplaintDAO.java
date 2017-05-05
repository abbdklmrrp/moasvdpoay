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
    /**
     * This method returns all complaints by concrete CSR.
     *
     * @param id id of CSR
     * @return list of complaints
     */
    List<Complaint> getByCSRId(int id);

    /**
     * This method returns all complaints by concrete order.
     *
     * @param id id of order
     * @return list of complaints
     */
    List<Complaint> getByOrderId(int id);

    /**
     * This method returns all complaints by place.
     *
     * @param id id of place
     * @return list of complaints
     */
    List<Complaint> getByPlaceId(int id);

    /**
     * This method returns all complaints by their status.
     *
     * @param id id of status
     * @return list of complaints
     */
    List<Complaint> getByStatusID(int id);

    /**
     * This method changes status of complaint.
     *
     * @param complaintId id of complaint
     * @param statusId    new id for status in complaint
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean changeStatus(int complaintId, int statusId);

    /**
     * This method changes description of complaint.
     *
     * @param complaintId id of complaint
     * @param description new description for complaint
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean changeDescription(int complaintId, String description);

}
