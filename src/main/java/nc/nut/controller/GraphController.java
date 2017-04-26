package nc.nut.controller;

import nc.nut.TestGraphStatisticData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class GraphController {

    @RequestMapping(value = "/graph")
    public String graph(Model model) {
        List<String> regions = new ArrayList<>();
        regions.add("Kiev");
        regions.add("Chernigov");
        regions.add("Vinnitsa");
        regions.add("Lviv");
        regions.add("Odessa");
        model.addAttribute("regions", regions.stream().sorted().collect(Collectors.toList()));
        return "graph";
    }

    @RequestMapping(value = "graphData")
    @ResponseBody
    public List<TestGraphStatisticData> getTestGraphStatisticData(@RequestParam(name = "region") String region,
                                                                  @RequestParam(name = "beginDate") String beginDate,
                                                                  @RequestParam(name = "endDate") String endDate) {
        List<TestGraphStatisticData> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            TestGraphStatisticData testGraphStatisticData = new TestGraphStatisticData(i + 1, 4, 2017, random.nextInt(50), random.nextInt(50));
            list.add(testGraphStatisticData);
        }
        return list;
    }
}
