/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao;

import java.util.List;
import nc.nut.entity.Complaint;

/**
 *
 * @author Alistratenko Nikita
 */
public interface ComplaintDAO {

    Complaint save(Complaint complaint);

    boolean delete(Complaint complaint);

    Complaint getById(int id);

    List<Complaint> getByCSRId(int id);

    List<Complaint> getByOrderId(int id);

    List<Complaint> getByPlaceId(int id);

    List<Complaint> getByStatusID(int id);

    boolean changeStatus(int complaintId, int statusId);

    boolean changeDescription(int complaintId, String description);

    List<Complaint> getDateInterval(int startYear, int startMonth, int endYear, int endMonth);

}
