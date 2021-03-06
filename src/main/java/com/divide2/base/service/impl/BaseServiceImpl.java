package com.divide2.base.service.impl;

import com.divide2.base.repository.BaseRepository;
import com.divide2.base.service.BaseService;
import com.divide2.search.Queryer;
import com.divide2.search.annotation.Conditioner;
import com.divide2.search.annotation.Deleter;
import com.divide2.search.annotation.Inserter;
import com.divide2.search.annotation.Updater;
import org.apache.commons.lang.reflect.FieldUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by bvvy on 2018/1/7.
 * com.divide2.base.service.impl
 */
public abstract class BaseServiceImpl<T, ID extends Serializable, REPO extends BaseRepository<T, ID>> implements BaseService<T, ID> {

    @Autowired
    private REPO repo;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @Inserter
    public T add(T t) {
        return repo.save(t);
    }

    @Override
    @Updater
    public T update(T t) {
        return repo.save(t);
    }

    @Override
    @Deleter
    public void delete(ID id) {
        repo.delete(id);
    }

    @Override
    public T get(ID id) {
        return repo.findOne(id);
    }

    //todo sort
    @Override
    public List<T> all() {
        return repo.findAll();
    }

    //todo sort
    @Override
    public Page<T> page(Map<String,String> conditions,Pageable pageable) {
        return repo.page(conditions,pageable);
    }







}
