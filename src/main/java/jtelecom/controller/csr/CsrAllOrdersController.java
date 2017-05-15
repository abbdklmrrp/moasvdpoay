package jtelecom.controller.csr;

import jtelecom.dao.order.OrderDao;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 15.05.2017.
 */
@RestController
@RequestMapping({"csr"})
public class CsrAllOrdersController {
    @Resource
    private OrderDao orderDao;

    @RequestMapping(value="getAllOrdersPage",method = RequestMethod.GET)
    public ModelAndView getAllOrdersPage(){
        return new ModelAndView("newPages/csr/AllOrders");
    }

    @RequestMapping(value="getAllOrders", method=RequestMethod.GET)
    public ListHolder getAllOrders(@ModelAttribute GridRequestDto requestDto){
        Integer start=requestDto.getStartBorder();
        Integer length=requestDto.getEndBorder();
        String sort=requestDto.getSort();
        String search=requestDto.getSearch();
        Integer count=orderDao.getCountOrdersWithoutCsr(search);
        List<FullInfoOrderDTO> orders=orderDao.getIntervalOrdersWithoutCsr(start,length,sort,search);
        return ListHolder.create(orders,count);
    }
}
