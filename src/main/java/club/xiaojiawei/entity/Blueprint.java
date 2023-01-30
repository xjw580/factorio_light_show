package club.xiaojiawei.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 肖嘉威
 * @date 2023/1/25 下午11:19
 */
@Setter
@Getter
public class Blueprint{

    private List<Icon> icons;
    private List<Entity> entities;
    public static final String ITEM = "blueprint";
    public static final long VERSION = 281479276527617L;

    public Blueprint(List<Icon> icons, List<Entity> entities) {
        this.icons = icons;
        this.entities = entities;
    }

    @Override
    public String toString() {
        return """
                {
                    "blueprint":{
                        "icons":"""
                + icons +
                        """
                        ,"entities":"""
                + entities +
                        """
                        ,"item":"
                        """
                + ITEM +
                        """
                        ","version":"""
                + VERSION +
                    """
                    }
                }
                """;
    }
}
