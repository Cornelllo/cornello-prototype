package com.cornello.prototype.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cornello.prototype.entity.BizRule;

@Repository
public interface BizRuleRepository extends JpaRepository<BizRule, Long>{

    public static final String BIZRULE_BY_GRKEY  = "SELECT * FROM cp_bizrule WHERE bizruletype = "
            +"(SELECT refseqno FROM cp_gen_info WHERE grkey = :grkey)";
    @Query(value=BIZRULE_BY_GRKEY, nativeQuery=true)
    List<BizRule> findBizruleByGrKey(@Param("grkey") String grkey);

    public static final String COMPANY_CD_PREFIXES =
    "SELECT * "+
    "FROM CP_BIZRULE "+
    "WHERE bizruletype IN (SELECT refseqno "+
                          "FROM CP_GEN_INFO "+
                          "WHERE parentcode = (SELECT REFSEQNO "+
                                              "FROM CP_GEN_INFO "+
                                              "WHERE grkey = 'COMPANY_CD_PREFIXES'))";
    
    @Query(value=COMPANY_CD_PREFIXES, nativeQuery=true)
    List<BizRule> findCompanyCodePrefixes();

}