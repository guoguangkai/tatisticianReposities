package cn.zgx.tatistician.pojo;

import java.util.List;

public class PageResult<T> {
    private Long total; //封装分页查询到总记录数
    private List<T> rows; // 封装分页查询到的数据

    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
