package nc.nut.controller;

import nc.nut.TestGraphStatisticData;
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

    @RequestMapping
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

    @RequestMapping(value = "/graphData")
    @ResponseBody
    public List<TestGraphStatisticData> getGraphData(@RequestParam(name = "region") String region,
                                                     @RequestParam(name = "beginDate") String beginDate,
                                                     @RequestParam(name = "endDate") String endDate) {
        List<TestGraphStatisticData> list = new ArrayList<>();
        Random random = new Random(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 30; j++) {
                TestGraphStatisticData testGraphStatisticData = new TestGraphStatisticData(j + 1, 4 + i, 2017, random.nextInt(50 + i * 3), random.nextInt(50));
                list.add(testGraphStatisticData);
            }
        }
        return list;
    }

    @RequestMapping(value = "/graphPartOfData")
    @ResponseBody
    public List<TestGraphStatisticData> getGraphPartOfData(@RequestParam(name = "pageNumber") int pageNumber) {
        List<TestGraphStatisticData> list = new ArrayList<>();
        Random random = new Random(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 30; j++) {
                TestGraphStatisticData testGraphStatisticData = new TestGraphStatisticData(j + 1, 4 + i, 2017, random.nextInt(50 + i * 3), random.nextInt(50));
                list.add(testGraphStatisticData);
            }
        }
        return list.subList(pageNumber*20,(pageNumber+1)*20);
    }

    @RequestMapping(value = "/countOfPages")
    @ResponseBody
    public int getcountOfPages() {
        //test values
        return (int) Math.ceil(30.0 * 3.0 / 20.0);
    }
}
