package com.divide2.base.repository.support;

import com.divide2.base.repository.BaseRepository;
import com.divide2.search.annotation.Conditioner;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * create by bvvy
 */
@Repository
public class SimpleBaseRepository<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private final EntityManager em;
    private final EntityInformation<T, ?> entityInformation;

    public SimpleBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    public Page<T> page(Map<String, String> conditions, Pageable pageable) {

        Class<T> clz = entityInformation.getJavaType();
        Field[] fields = clz.getDeclaredFields();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        for (Field field : fields) {
            Conditioner conditioner = field.getAnnotation(Conditioner.class);
            String fieldName = field.getName();
            if (conditioner != null) {
                switch (conditioner.way()) {
                    case EQ: {
                        queryBuilder.must(QueryBuilders.matchQuery(fieldName, conditions.get(fieldName)));
                    }
                    case LIKE: {
                        queryBuilder.must(QueryBuilders.regexpQuery(fieldName, ".{0,10}" + conditions.get(fieldName) + ".{0,10}"));
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
        searchQuery.setPageable(pageable);
        return elasticsearchTemplate.queryForPage(searchQuery, clz);
    }

}
