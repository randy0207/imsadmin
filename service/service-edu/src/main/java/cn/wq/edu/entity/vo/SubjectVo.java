package cn.wq.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LWQ
 * @since 2022/4/18
 */
@Data
public class SubjectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer sort;

    private List<SubjectVo> children = new ArrayList<>();
}
