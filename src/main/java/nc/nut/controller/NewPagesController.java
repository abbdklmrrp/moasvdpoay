package nc.nut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "newpages")
public class NewPagesController {

    @RequestMapping
    public String getTemplate() {
        return "newPages/admin/Profile";
    }
}