package com.unionpay.supervision.dao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.unionpay.supervision.domain.SuperTypeOversee;
@Repository
public interface SuperTypeOverseeRepository extends JpaRepository<SuperTypeOversee,String> {
	
    /**
     * 根据督办类型的名字查找
     * @param typename
     * @return
     */
    List<SuperTypeOversee> findByTypeName(String typename);

    /**
     * 根据督办编码查找
     * @param typeId
     * @return
     * @author lishuang
     * @date 2019-04-03
     */
    List<SuperTypeOversee> findByTypeId(String typeId);

    /**
     * 根据主键查询
     * @param unid
     */
    SuperTypeOversee findByUnid(String unid);
    
    /**
     * 删除督办类型
     */
    void delete(SuperTypeOversee entity);
    /**
     * 查询所有督办类型
     */
    List<SuperTypeOversee> findAll();
    
    /**
     * 查询最近督办类型
     */
    SuperTypeOversee findFirstByOrderByTypeSortDesc();
    
}