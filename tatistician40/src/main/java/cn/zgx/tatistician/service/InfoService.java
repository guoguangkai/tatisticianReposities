package cn.zgx.tatistician.service;

import cn.zgx.tatistician.dao.TimerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InfoService {
    @SuppressWarnings("all")
    @Autowired
    TimerDao timerDao;

    List<String> list;

    public List getInfo(String owner,String field) {
        switch (field) {
            case "customerName":
                getNameInfoByOwner(owner);
                break;
            case "needs":
                getNeedsInfoByOwner(owner);
                break;
            case "mobile":
                getMobileInfoByOwner(owner);
                break;
        }
        return list;
    }

    private void getNameInfoByOwner(String owner) {
        System.out.println(owner);
       String info=timerDao.queryCustomerName(owner);
        String str = info.substring(1, info.length() - 2);
        list = Arrays.asList(str.split("】,"));
    }

    private void getNeedsInfoByOwner(String owner) {
        System.out.println(owner);
        String info=timerDao.queryNeedsBatch(owner);
        String str = info.substring(1, info.length() - 2);
        list = Arrays.asList(str.split("】,"));
    }

    private void getMobileInfoByOwner(String owner) {
        System.out.println(owner);
        String info=timerDao.queryMobileBatch(owner);
        String str = info.substring(1, info.length() - 2);
        list = Arrays.asList(str.split("】,"));
    }
}
