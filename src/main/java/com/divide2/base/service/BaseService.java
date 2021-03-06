package com.divide2.base.service;

import com.divide2.search.Queryer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by bvvy on 2018/1/7.
 * com.divide2.base.service
 */

public interface BaseService<T,ID extends Serializable> {

    /**
     * 添加
     * @param t 对象
     * @return 对象
     */
    T add(T t);

    /**
     * 修改
     * @param t 对象
     * @return 对象
     */
    T update(T t);

    /**
     * 删除
     * @param id id
     */
    void delete(ID id);

    /**
     * 获取单个
     * @param id id
     * @return 对象
     */
    T get(ID id);

    /**
     * 获取全部 排序? todo
     * @return list
     */
    List<T> all();


    Page<T> page(Map<String,String> conditions,Pageable pageable);
}
