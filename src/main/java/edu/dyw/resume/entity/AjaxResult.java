package edu.dyw.resume.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author miaomiao
 */

@Component
@Data
public class AjaxResult {

    /** 返回内容 */
    private boolean state;

    /** 数据对象 */
    private String msg;

    public void AjaxResult(boolean state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
