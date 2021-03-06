package com.pinyougou.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 广告分类
 * @author whister
 */
@Table(name="tb_content_category")
public class ContentCategory implements Serializable{
    
	private static final long serialVersionUID = 5930846169505417346L;
	/** 主键 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 广告分类名称 */
	@Column(name="name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}