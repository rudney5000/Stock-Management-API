package com.dedyrudney.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class Interceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
            // select utilisateur
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String id_entreprise = MDC.get("id_entreprise");
            if (StringUtils.hasLength(entityName)
            && !entityName.toLowerCase().contains("entreprise")
            && !entityName.toLowerCase().contains("roles")
            && StringUtils.hasLength(id_entreprise)){

                if (sql.contains("where")){
                    sql = sql + " and " + entityName + ".id_entreprise = " + id_entreprise;
                } else {
                    sql = sql + " and " + entityName + ".id_entreprise = " + id_entreprise;
                }
            }
        }
        return super.onPrepareStatement(sql);
    }
}
