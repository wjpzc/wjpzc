package cn.xypt.model.vo;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/11
 */
@Data
public class DictTree {

    private List<DictItem> dictItemList = new ArrayList<>();
}
