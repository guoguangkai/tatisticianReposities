package cn.zgx.tatistician.service;

import cn.zgx.tatistician.pojo.EmployeePojo;
import cn.zgx.tatistician.pojo.PagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    @SuppressWarnings("all")
    @Autowired
    CustomerService customerService;

    PagePojo pagePojo;

    public PagePojo<EmployeePojo> getDataWithPage(int pageNumber, int pageSize) {
        List<EmployeePojo> customerList = customerService.checkAll();
        return getPageList(customerList,pageNumber, pageSize);
    }
    //递归获取数据防止下标越界
    private PagePojo getPageList(List<EmployeePojo> customerList,int pageNumber, int pageSize) {
        Integer isNull = customerList == null ? 1 : 2;
        switch (isNull) {
            case 1:
                customerList = customerService.checkAll();
                getPageList(customerList,pageNumber, pageSize);
                break;
            case 2:
                int total = customerList.size();
                pagePojo = new PagePojo(pageNumber, pageSize, total);
                //拿到每页数据
                pagePojo.setRows(customerList.subList(pagePojo.getStartIndex(), pagePojo.getEndIndex()));
                break;
        }
        return pagePojo;
    }

   /* public PageResult<EmployeePojo> getPageResult(int pageNum, int pageSize) {
        pageResult = new PageResult();
        customerList = customerService.checkAll();
        pagePojo = new PagePojo(pageNum,pageSize);
        getPageList();
        int totalRecord = customerList.size();
        pageResult.setTotal((long) totalRecord);
        return pageResult;
    }

    //递归获取数据防止下标越界
    private void getPageList() {
        Integer isNull = null == customerList | customerList.size() == 0 ? 1 : 2;
        switch (isNull) {
            case 1:
                customerList = customerService.checkAll();
                getPageList();
            case 2:
                pageResult.setRows(customerList.subList(pagePojo.getStartIndex(), pagePojo.getEndIndex()));
        }
    }*/
}
