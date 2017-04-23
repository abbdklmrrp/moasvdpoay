package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"pmg"})
public class PMGController {
    @RequestMapping({"index"})
    String index() {
        return "pmg/index";
    }
}
