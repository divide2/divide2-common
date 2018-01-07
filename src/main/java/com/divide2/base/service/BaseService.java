package com.divide2.base.service;

import com.divide2.search.SearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by bvvy on 2018/1/7.
 * com.divide2.base.service
 */
public interface BaseService<T,ID> {

    T add(T t);

    T update(T t);

    void delete(ID id);

    T get(ID id);

    List<T> all();

    Page<T> page(Pageable pageable);

    Page<T> search(SearchQuery query);
}
