package com.unionpay.services.repository;
import com.unionpay.services.model.ServerInfo;
import com.unionpay.services.model.ServerModelDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerinfoRepository extends JpaRepository<ServerInfo,Integer>,JpaSpecificationExecutor<ServerInfo> {
    @Query(value = "select * from ServerInfo s where s.id = ?1 ",nativeQuery = true)
    ServerInfo findServerSuggestById(Integer id);

    @Query(value = "select si.ser_id as serId,si.ser_name as serName,(select sd.name from server_dict sd where sd.type = 'type' and sd.value = si.type) as type,si.sort,si.status,group_concat(om.ORGNAME) as orgName,GROUP_CONCAT(sau.user_name) as serverManager from server_info si LEFT join server_acceptance_dept sad  on si.id = sad.server_id left join om_organization om ON sad.org_id = om.ORGID LEFT JOIN server_acceptance_user sau on si.id = sau.server_id where si.id in (?1) group by si.ser_id",nativeQuery = true)
    List<ServerModelDto> findServerByIdIn(List<String> strings);
}