package test.intdmp.core.service.fileManager._class;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {

    private String label;
    private String icon;
    private String expandedIcon;
    private String collapsedIcon;
    private ArrayList<TreeNode> children;
    private Boolean leaf;
    private Boolean expanded;
    private String type;
    private Boolean partialSelected;
    private String styleClass;
    private Boolean draggable;
    private Boolean droppable;
    private Boolean selectable;
    private String key;

    public TreeNode(String label, String expandedIcon, String collapsedIcon, ArrayList<TreeNode> children ) {

        this.label = label;
        this.expandedIcon = expandedIcon;
        this.collapsedIcon = collapsedIcon;
        this.children = children;

    }

    public TreeNode(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

}
