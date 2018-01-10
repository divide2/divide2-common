package com.divide2.base.service.impl;

import com.divide2.base.service.BaseService;
import com.divide2.search.Queryer;
import com.divide2.search.annotation.Deleter;
import com.divide2.search.annotation.Inserter;
import com.divide2.search.annotation.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bvvy on 2018/1/7.
 * com.divide2.base.service.impl
 */
public abstract class BaseServiceImpl<T,ID extends Serializable,REPO extends JpaRepository<T,ID>> implements BaseService<T,ID> {

    @Autowired
    private REPO repo;

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
        return repo.getOne(id);
    }

    //todo sort
    @Override
    public List<T> all() {
        return repo.findAll();
    }
    //todo sort
    @Override
    public Page<T> page(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    //todo 自定义的 search 简化方法 看怎么来方便
    public Page<T> search(Queryer query) {

        return null;
    }
}
