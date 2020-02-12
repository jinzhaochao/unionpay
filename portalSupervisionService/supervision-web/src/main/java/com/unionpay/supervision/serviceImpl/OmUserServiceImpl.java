package com.unionpay.supervision.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;

@Service
@Transactional
public class OmUserServiceImpl implements OmUserService{
	
	@Autowired
    private OMUserRepository omUserRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    
    public OmUser findByUserid(String userid){
    	return omUserRepository.findByUserid(userid);
    }
    //
    /*public OmUser findByUserid(String userid){
        return omUserRepository.findByUserid(userid);
    }*/
    
    public OmUser findById(Integer empid){
    	return omUserRepository.findByEmpid(empid);
    }

    public List viewTree() {
        List<OmUser> omUserList = omUserRepository.findByEmpstatus("01");
        return omUserList;
    }

    public List findLatestUnReadCalendarId(String empcode) {
        String sql="select tab3.id from (select DISTINCT tab1.id,tab2.empcode,tab2.calendarid,tab1.begintime from (select * from CalendarMain where begintime>now() and empcode='"+empcode+"') tab1 left join (select * from CalendarRead where empcode='"+empcode+"') tab2 on tab1.id=tab2.calendarid ) tab3 where tab3.calendarid is null order by tab3.begintime limit 1";
        Query query = em.createNativeQuery(sql);
        query.unwrap(QueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List rows = query.getResultList();
        return query.getResultList();
    }
    
    /**
     * 根据组织查询用户
    */
    public List<OmUser> findUserByOrgName(String orgName) {
    	List<OmUser> userlist = null;
    	String sql= "SELECT u.EMPID AS empid,u.USERID AS userid,u.EMPCODE AS empcode,u.EMPNAME AS empname FROM om_user u LEFT JOIN om_organization o ON u.ORGID = o.ORGID WHERE o.ORGNAME =:orgName AND u.EMPSTATUS <> '00' ORDER BY u.SORTNO";
    	Query query = em.createNativeQuery(sql);
    	query.setParameter("orgName", orgName);
    	query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmUser.class));
    	userlist = query.getResultList();
    	return userlist;
    }
}
