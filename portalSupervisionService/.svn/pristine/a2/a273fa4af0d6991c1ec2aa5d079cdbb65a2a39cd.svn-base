package com.unionpay.supervision.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional(readOnly=true)
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {
    public List<Object[]> listBySQL(String sql);

    @Transactional
    public void updateBySql(String sql,Object...args);
    @Transactional
    public void updateByHql(String hql,Object...args);
}



