package cn.zgx.tatistician.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class Result {
    private int total;
    private int totalAfterCheck;
    private List rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalAfterCheck() {
        return totalAfterCheck;
    }

    public void setTotalAfterCheck(int totalAfterCheck) {
        this.totalAfterCheck = totalAfterCheck;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
