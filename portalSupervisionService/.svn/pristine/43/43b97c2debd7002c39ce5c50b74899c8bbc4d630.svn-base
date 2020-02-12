package com.unionpay.pager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.pager.domain.JDBCUtil;
import com.unionpay.pager.dto.WcmChannel;
import com.unionpay.pager.service.ColumnTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: jinzhao
 * @create: 2019-08-07 15:02
 **/
@Service
public class ColumnTreeServiceImpl implements ColumnTreeService {


    @Autowired
    private JDBCUtil jdbcUtil;

    @Value("${wcm.datasource.driverClassName}")
    private  String driverClass;
    @Value("${wcm.datasource.url}")
    private  String url;
    @Value("${wcm.datasource.username}")
    private  String username;
    @Value("${wcm.datasource.password}")
    private  String password;
    @Value("${wcm.wcmchannel.siteid}")
    private Integer siteid;

    /**
     * 栏目树信息
     * @author jinzhao
     * @data 2019-08-05
     */
    @Override
    public List<WcmChannel> selectColumnTree(Integer chid)  {
        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        List<WcmChannel> channelList = new ArrayList<>();
        try {

            String sql = "select wcmchnl.channelId, wcmchnl.chnlDesc,wcmchnl.parentId, CASE WHEN  (SELECT COUNT(channelId) FROM   wcmchannel chnl WHERE chnl.siteId = "+ siteid +" AND chnl. STATUS = '0' AND chnl.parentId =wcmchnl.channelId) > 0 THEN 1 ELSE 0 END isChildren  from wcmchannel wcmchnl where SITEID  ="+ siteid +" AND STATUS ='0' AND PARENTID = "+ chid + " ORDER BY CRTIME ";
            con = jdbcUtil.getConnction(url,username,password);
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            System.out.println(rs);
            while (rs.next()) {
                Integer id = rs.getInt("channelId");
                String name = rs.getString("chnlDesc");
                Integer parentId = rs.getInt("parentId");
                boolean isParent = rs.getBoolean("isChildren");
                WcmChannel wcmChannel = new WcmChannel(id, name, parentId,isParent);
                channelList.add(wcmChannel);

                //查询子节点栏目信息
//                String sql1 = "select chnl.channelId, chnl.chnlDesc,chnl.parentId from wcmchannel chnl where chnl.siteId = '4' and chnl.status = '0' and chnl.parentId =" + channelId +" order by chnl.crtime";
//               Connection con1 = jdbcUtil.getConnction();
//               Statement stat1 = con1.createStatement();
//               ResultSet rs1 = stat1.executeQuery(sql1);
//                System.out.println(rs);
//
//                while (rs1.next()) {
//
//                    Integer channelId1 = rs1.getInt("channelId");
//                    String chnlDesc1 = rs1.getString("chnlDesc");
//                    Integer parentId1 = rs1.getInt("parentId");
//                    ColumnChildren columnChildren = new ColumnChildren(channelId1, chnlDesc1, parentId1);
//                    channelList.add(columnChildren);
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con, stat, rs);

        }
        return channelList;
    }



}
