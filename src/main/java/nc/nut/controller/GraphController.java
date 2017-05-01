package nc.nut.controller;

import nc.nut.TestGraphStatisticData;
import nc.nut.dao.complaint.ComplaintDAO;
import nc.nut.dao.place.Place;
import nc.nut.dao.place.PlaceDAO;
import nc.nut.reports.ReportCreatingException;
import nc.nut.reports.ReportData;
import nc.nut.reports.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Revniuk Aleksandr
 */
@Controller
@RequestMapping(value = "graph")
public class GraphController {
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private ComplaintDAO complaintDAO;
    @Autowired
    private ReportsService reportsService;

    @RequestMapping
    public String graph(Model model) {
        List<Place> regions = placeDAO.getAll();
        model.addAttribute("regions", regions);
        return "graph";
    }

    @RequestMapping(value = "/graphData")
    @ResponseBody
    public List<ReportData> getGraphData(@RequestParam(name = "region") int region,
                                                     @RequestParam(name = "beginDate") String beginDate,
                                                     @RequestParam(name = "endDate") String endDate) {
        List<ReportData> list = new ArrayList<>();
        Random random = new Random(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 30; j++) {
                ReportData reportData = new ReportData("test"+i+j , random.nextInt(50 + i * 3), random.nextInt(50));
                list.add(reportData);
            }
        }
//        List<ReportData> list = null;
//        try {
//            list = reportsService.getDataForReport(beginDate,endDate,region);
//            System.out.println(list);
//        } catch (ReportCreatingException e) {
//            e.printStackTrace();
//        }
        return list;
    }
}
