package cn.xypt.model.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/11
 */
@Data
@AllArgsConstructor
public class DictItem {
    private Integer id;
    private String name;
    private List<DictItem> children;
}
