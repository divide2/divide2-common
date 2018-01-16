package com.divide2.base.service.impl;

import com.divide2.base.service.BaseService;
import com.divide2.search.Queryer;
import com.divide2.search.annotation.Conditioner;
import com.divide2.search.annotation.Deleter;
import com.divide2.search.annotation.Inserter;
import com.divide2.search.annotation.Updater;
import org.apache.commons.lang.reflect.FieldUtils;
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
public abstract class BaseServiceImpl<T, ID extends Serializable, REPO extends JpaRepository<T, ID>> implements BaseService<T, ID> {

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
    public Page<T> page(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    //todo 自定义的 search 简化方法 看怎么来方便
    public Page<T> search(Queryer query) {
        return repo.findAll(new PageRequest(0, 2));
    }

    @SuppressWarnings("unchecked")
    private Class<T> getTClz() {
        return (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }


    @Override
    public Page<T> search(Map<String, String> conditions) {
        Field[] fields = getTClz().getDeclaredFields();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        for (Field field : fields) {
            Conditioner conditioner = field.getAnnotation(Conditioner.class);
            String fieldName = field.getName();
            if (conditioner != null) {
                switch (conditioner.way()) {
                    case EQ: {
                        queryBuilder.must(QueryBuilders.termQuery(fieldName, conditions.get(fieldName)));
                    }
                    case LIKE: {
                        queryBuilder.must(QueryBuilders.queryStringQuery(fieldName).field(fieldName));
                    }
                    case RANGE: {
                        queryBuilder.must(
                                QueryBuilders.rangeQuery(fieldName)
                                        .gte(
                                                conditions.get(conditioner.startName())
                                        )
                                        .lte(conditions.get(conditioner.endName()))
                        );
                    }
                }
            }
        }
        SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
        Integer page = Integer.parseInt(conditions.get("page"));
        Integer size = Integer.parseInt(conditions.get("size"));
        searchQuery.setPageable(new PageRequest(page, size));
        return elasticsearchTemplate.queryForPage(searchQuery, getTClz());
    }

}
