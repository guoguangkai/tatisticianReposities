package cn.zgx.tatistician.pojo;

import lombok.Data;

@Data
public class Employee {
    private String department;

    public String getDepartment() {
        return department;
    }
}
