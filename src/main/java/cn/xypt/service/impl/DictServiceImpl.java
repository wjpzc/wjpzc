package cn.xypt.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.mapper.DictMapper;
import cn.xypt.model.domain.Dict;
import cn.xypt.model.vo.DictItem;
import cn.xypt.model.vo.DictTree;
import cn.xypt.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author zhangchao
 * @description 针对表【dict(字典表(拟将学院名，角色名，开课学期等一些常量存放在此表))】的数据库操作Service实现
 * @createDate 2024-04-17 11:50:07
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService {

    @Override
    public SaResult getDictTree() {
        DictTree dictTree = buildTree();
        return SaResult.data(dictTree);
    }

    /**
     * 获取父节点列表
     *
     * @param dictList 字典列表
     * @return List<DictItem>
     */
    private List<DictItem> getRootNodeList(List<Dict> dictList) {
        return dictList.stream().filter(dict -> dict.getParentId() == null)
            .map(dict -> new DictItem(dict.getId(), dict.getName(), null))
            .collect(Collectors.toList());
    }

    /**
     * 根据根节点获取子节点
     */
    private DictItem buildChildTree(DictItem rootNode, List<Dict> dictList) {
        List<DictItem> childDictItemList = new ArrayList<>();
        for (Dict dict : dictList) {
            if (rootNode.getId().equals(dict.getParentId())) {
                DictItem dictItem = new DictItem(dict.getId(), dict.getName(), null);
                childDictItemList.add(buildChildTree(dictItem, dictList));
            }
        }
        rootNode.setChildren(childDictItemList);
        return rootNode;
    }


    private DictTree buildTree() {
        List<Dict> dictList = list();
        List<DictItem> rootNodeList = getRootNodeList(dictList);
        DictTree dictTree = new DictTree();
        for (DictItem rootNode : rootNodeList) {
            DictItem dictItem = buildChildTree(rootNode, dictList);
            dictTree.getDictItemList().add(dictItem);
        }
        return dictTree;
    }
}




