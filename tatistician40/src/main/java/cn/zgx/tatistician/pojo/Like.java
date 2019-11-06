package cn.zgx.tatistician.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Like {
    private String dep;
    private String name;

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
