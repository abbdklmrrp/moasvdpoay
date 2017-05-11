package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"csr"})
public class CSRController {

    @RequestMapping({"getProfile"})
    public String index() {
        return "newPages/csr/Profile";
    }
}
