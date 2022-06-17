package com.cornello.prototype.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cornello.prototype.client.APIClient;
import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.BizRule;
import com.cornello.prototype.entity.Role;
import com.cornello.prototype.model.RequestBodyTest;
import com.cornello.prototype.repository.AppUserRepository;
import com.cornello.prototype.repository.BizRuleRepository;
import com.cornello.prototype.repository.GenInfoRepository;
import com.cornello.prototype.repository.RoleRepository;
import com.cornello.prototype.service.UserService;
import com.cornello.prototype.util.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final BizRuleRepository bizRuleRepository;
    private final GenInfoRepository genInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final APIClient aPIClient;
    private final Gson gson = new Gson();

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found from database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user to database");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role to database");
        return roleRepository.save(role);
    }

    @Override
    public void assignRole(String username, String roleName) {
        log.info("Adding role to app user");
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Getting user from the database");
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Getting all users from the database");
        return appUserRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<AppUser> getUserByfullNameAndUsername(String fullName, String username) throws Exception {
        log.info("Getting id: {} and username:{} from the database",fullName,username);
        AppUser appUser = new AppUser();
        appUser.setFullName("hardcode fullname");
        try {
            appUserRepository.save(appUser);
            int error = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
            // throw new Exception(e);
        }
        return appUserRepository.findByFullNameAndUsername(fullName, username);
    }

    @Override
    //@SuppressWarnings("unchecked")
    public List<AppUser> findByFullName(String fullName) {
        log.info("Getting users from the database where fullName:{}",fullName);
        
        return castList(AppUser.class,entityManager
            .createNamedStoredProcedureQuery("namedFindByFullName")
            .setParameter("full_name", fullName)
            .getResultList());
    }
    /*
    * this hurt application performance when having Lists with many elements?. Java would have to cast each and every one of them.
    */
    private static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
          r.add(clazz.cast(o));
        return r;
    }
    
    public Object testFuncCompCode(@Valid Role requestBody) throws Exception {
        // COMPANY_PHILAM,COMPANY_PELAC,COMPANY_BPI,COMPANY_CITI
		String companyCode = "";
        String policyNumber = requestBody.getName();
        log.info(" this.memoizeTable:{}", Utility.memoizeTable);
        if( Utility.memoizeTable.containsKey(policyNumber) )
        {
            companyCode = Utility.memoizeTable.get(policyNumber);
            log.info("Getting value from computed result for {}={}",policyNumber,companyCode);
            return companyCode;
        }
		List<BizRule> compCodePrefixes = bizRuleRepository.findCompanyCodePrefixes();
        List<String> aiaPhPrefixes = new ArrayList<>();
        List<String> bpiAiaPrefixes = new ArrayList<>();
        List<String> citiPrefixes = new ArrayList<>();
        List<String> pelacPrefixes = new ArrayList<>();

        aiaPhPrefixes = fetchcompCodePrefixes(compCodePrefixes,"AIA PH");
        bpiAiaPrefixes = fetchcompCodePrefixes(compCodePrefixes,"BPI AIA");
        citiPrefixes = fetchcompCodePrefixes(compCodePrefixes,"CITI");
        pelacPrefixes = fetchcompCodePrefixes(compCodePrefixes,"PELAC");

        companyCode = identifyCompCode(aiaPhPrefixes, policyNumber,"COMPANY_PHILAM");
		if (!companyCode.isEmpty()) {
            return saveToCache(policyNumber, companyCode);
        }
        companyCode = identifyCompCode(bpiAiaPrefixes, policyNumber,"COMPANY_BPI");
        if (!companyCode.isEmpty()) {
            return saveToCache(policyNumber, companyCode);
        }
        companyCode = identifyCompCode(citiPrefixes, policyNumber,"COMPANY_CITI");
        if (!companyCode.isEmpty()) {
            return saveToCache(policyNumber, companyCode);
        }
        companyCode = identifyCompCode(pelacPrefixes, policyNumber,"COMPANY_PELAC");
        if (!companyCode.isEmpty()) {
            return saveToCache(policyNumber, companyCode);
        }
        
		if (companyCode.isEmpty()) {
			throw new Exception("Invalid company");
		}
		return companyCode;
    }

    private String saveToCache(String policyNumber,String companyCode) {
        log.info("Putting result in cache for {}",policyNumber);
        Utility.memoizeTable.put(policyNumber, companyCode);
        return companyCode;
    }

    private List<String> fetchcompCodePrefixes(List<BizRule> compCodePrefixes ,String compyPrefixRemarks) {
        log.info("fetchcompCodePrefixes({})",compyPrefixRemarks);
        for(BizRule b :compCodePrefixes) {
            if(b.getRemarks().toLowerCase().contains(compyPrefixRemarks.toLowerCase())) {
                log.info("b.getRemarks():{} vs compyPrefixRemarks:{}",b.getRemarks().toLowerCase(),compyPrefixRemarks.toLowerCase());
                return Arrays.asList(b.getValue().split(","));
            }
        }
        return new ArrayList<>();
    }
        
    private String identifyCompCode(List<String> compCodePrefixes ,String policyNumber,String grKey) {
        log.info("identifyCompCode({},{},{})",compCodePrefixes,policyNumber,grKey);
        for(String compCodePrefix : compCodePrefixes) {
            boolean condition = policyNumber.startsWith(compCodePrefix);
            if(condition) {
                log.info("policyNumber:{} vs compCodePrefix:{} | policyNumber.startsWith(compCodePrefix):{}",policyNumber,compCodePrefix,condition);
                return genInfoRepository.findByGrKey(grKey).getRefCode();
            } 
        }
        return "";
    }

    @Override
    public Object testFunc(RequestBodyTest requestBody) throws Exception { 
        String jwt = Utility.generateJWTToken("MyAIA", requestBody);
        // return jwt;
        // return JsonParser.parseString(aPIClient.randomUser().getBody()).getAsJsonObject();
        return aPIClient.randomUser().getBody();
    }
}
