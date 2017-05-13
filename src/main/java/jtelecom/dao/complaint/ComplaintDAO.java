/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtelecom.dao.complaint;


import jtelecom.dao.interfaces.Dao;

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
    List<Complaint> getByPMGId(int id);

    /**
     * This method returns all complaints by concrete order.
     *
     * @param id id of order
     * @return list of complaints
     */
    List<Complaint> getByOrderId(int id);

    /**
     * This method returns interval of unassigned complaints.
     *
     * @param startIndex start index of interval
     * @param endIndex end index of interval
     * @return list of complaints
     */
    List<Complaint> getIntervalOfUnassignedComplaints(int startIndex, int endIndex);

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
     * This method set PMG id for complaint.
     *
     * @param complaintId id of complaint
     * @param pmgId       id of PMG
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean assignToUser(int complaintId, int pmgId);

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

    /**
     * This method returns amount of complaints where PMG id is NULL.
     *
     * @return amount of complaints
     */
    int countUnasignedComplaints();

}