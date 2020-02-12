package com.unionpay.services.repository;
import com.unionpay.services.model.ServerHandlingMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerhandlingmaterialRepository extends JpaRepository<ServerHandlingMaterial,Integer>,JpaSpecificationExecutor<ServerHandlingMaterial> {
    //查看查询所有办理材料（某一个事件）
    List<ServerHandlingMaterial> findByServerId(int serverId);
    //查看某一个办理材料的详情
    public ServerHandlingMaterial findByIdAndServerId(Integer id,Integer serverId);

    //通过name查询该办理材料
    public ServerHandlingMaterial findByName(String name);

    //根据服务id删除所有受理材料
    public void deleteAllByServerId(Integer id);
}