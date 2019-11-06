package cn.zgx.tatistician.controller;


import cn.zgx.tatistician.pojo.*;
import cn.zgx.tatistician.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class HelloController {
    @Autowired
    private PageService pageService;
    @Autowired
    private InfoService infoService;
    @Autowired
    CustomerService customerService;
    @Autowired
    Result result;
    @Autowired
    QueryByService queryByService;
    @Autowired
    Like like;
    @Autowired
    TimerService timerService;

    /**
     * 带有详情的统计页面路由
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView getInfoIndex() {
        return new ModelAndView("infoIndex");
    }

    /**
     * 不带有详情的统计页面路由
     * @return
     */
    @RequestMapping("/noInfo")
    public ModelAndView getHtml() {
        return new ModelAndView("index");
    }

    /**
     * 带有详情的统计查询页面路由
     * @return
     */
    @RequestMapping("/zgxInfo")
    public ModelAndView queryInfo() {
        return new ModelAndView("queryInfo");
    }

    /**
     * 不带有详情的统计查询页面路由
     * @return
     */
    @RequestMapping("/zgx")
    public ModelAndView queryNoInfo() {
        return new ModelAndView("queryNoInfo");
    }


    /**
     * 不带分页统计数据接口
     * @return
     */
    @RequestMapping("/getAllData")
    public Result getAllData() {
            //校验不通过的全部数据
            List<EmployeePojo> employeePojoList = customerService.checkAllOrder();
            //数据库全部记录条数
            int total = customerService.getTotal();
            //分析不通过的员工总数
             int totalAfterCheck = employeePojoList.size();
            result.setTotal(total);
            result.setTotalAfterCheck(totalAfterCheck);
            result.setRows(employeePojoList);
            return result;
    }


    /**
     * 详情路由
     * @return ModelAndView
     */
    @RequestMapping("/getDetailHtml")
    public ModelAndView getDetailHtml(@RequestParam(required = true) String customerOwner, @RequestParam(required = true) String col) {
        ModelAndView mv=new ModelAndView();
        Model model = new Model();
        model.setCustomerOwner(customerOwner);
        model.setCol(col);
        mv.addObject(model);
        mv.setViewName("detail");
        return mv;
    }

    /**
     * 详情接口
     * @param customerOwner
     * @param col
     * @return
     */
    @RequestMapping("/getInfoList")
    public List getInfoResult(@RequestParam(required = true) String customerOwner, @RequestParam(required = true) String col) throws UnsupportedEncodingException {
        String customerOwner1=URLDecoder.decode(customerOwner, "UTF-8");
        System.out.println("在查询：" + customerOwner + "的" + col);
        return infoService.getInfo(customerOwner1, col);
    }

    /**
     * 不带分页统计数据接口
     * @return
     */
    @RequestMapping("/queryBy")
    public Result queryBy(@RequestParam(defaultValue = "",required=false) String department,@RequestParam(defaultValue = "",required=false) String owner) {
        String dep = department != null ?  department.trim() : "";
        String name = department != null ?  owner.trim() : "";
        like.setDep(dep);
        like.setName(name);
        //对应条件下校验不通过的全部数据
        List<EmployeePojo> employeePojoList = queryByService.checkAllOrder(like);
        //对应条件下数据库全部记录条数
        int total = customerService.getTotal();
        //分析不通过的员工总数
        int totalAfterCheck = employeePojoList.size();
        result.setTotal(total);
        result.setTotalAfterCheck(totalAfterCheck);
        result.setRows(employeePojoList);
        return result;
    }

    /**
     * 定时任务 备份后的总数据获取接口
     * @return
     */
    @RequestMapping("/getAllTimerData")
    public Result getTimerData() {
        System.out.println(Thread.currentThread().getName()+"开始执行-------------------------");
        //校验不通过的全部数据
        List<TimerEmployeePojo> timerEmployeePojoList = timerService.getTimerData();
        System.out.println(timerEmployeePojoList.toString());
        //数据库全部记录条数
        int total = customerService.getTotal();
        //分析不通过的员工总数
        int totalAfterCheck = timerEmployeePojoList.size();
        result.setTotal(total);
        result.setTotalAfterCheck(totalAfterCheck);
        result.setRows(timerEmployeePojoList);
        System.out.println(Thread.currentThread().getName()+"结束执行------------------------------------------");
        return result;
    }

    /**
     * 定时任务 查询数据接口
     * @return
     */
    @RequestMapping("/timerQueryBy")
    public Result timerQueryBy(@RequestParam(defaultValue = "",required=false) String department,@RequestParam(defaultValue = "",required=false) String owner) {
        String dep = department != null ?  department.trim() : "";
        String name = department != null ?  owner.trim() : "";
        like.setDep(dep);
        like.setName(name);
        //对应条件下校验不通过的全部数据
        List<TimerEmployeePojo> employeePojoList = timerService.timeLikeQueryOrder(like);
        //对应条件下数据库全部记录条数
        int total = customerService.getTotal();
        //分析不通过的员工总数
        int totalAfterCheck = employeePojoList.size();
        result.setTotal(total);
        result.setTotalAfterCheck(totalAfterCheck);
        result.setRows(employeePojoList);
        return result;
    }
}

