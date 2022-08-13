package com.example.demo.mybatis.interceptor;


import com.example.demo.mybatis.interceptor.rdbmsspecifics.MySqlRdbmsSpecifics;
import com.example.demo.mybatis.interceptor.rdbmsspecifics.RdbmsSpecifics;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 不支持特殊语法
 * 如insert into on duplicate key update
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class SlowSQLInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowSQLInterceptor.class);

    /**
     * 慢sql阈值
     */
    private long slowSqlThreshold = 100;

    private static final String DELEGATE_MAPPED_STATEMENT = "delegate.mappedStatement";

    private final RdbmsSpecifics rdbmsSpecifics = new MySqlRdbmsSpecifics();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) SystemMetaObject.forObject(handler).getValue(DELEGATE_MAPPED_STATEMENT);
        Configuration configuration = mappedStatement.getConfiguration();
        BoundSql boundSql = handler.getBoundSql();
        String originSql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> parameters = new ArrayList<>();
        for (ParameterMapping parameterMapping : parameterMappings) {
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                parameters.add(rdbmsSpecifics.formatParameterObject(value));
            }
        }
        String targetSql = beautifySql(dumpedSql(originSql, parameters));
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long costTime = System.currentTimeMillis() - start;
        if (costTime > slowSqlThreshold) {
            LOGGER.warn("sql with param = {} cost time = {} ms, slowSqlTime = {} ms", targetSql, costTime, slowSqlThreshold);
        } else {
            LOGGER.info("sql with param = {} cost time = {} ms", targetSql, costTime);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        slowSqlThreshold = Long.parseLong(properties.getProperty("slowSqlThreshold", "100"));
    }


    private String beautifySql(String sql) {
        return sql.replaceAll("[\\s\n]+", " ");
    }

    protected String dumpedSql(String sql, List<Object> parameters) {
        StringBuilder dumpSql = new StringBuilder();
        int lastPos = 0;
        // find position of first question mark
        int Qpos = sql.indexOf('?', lastPos);
        int argIdx = 0;
        String arg;

        while (Qpos != -1) {
            // get stored argument

            try {
                arg = (String) parameters.get(argIdx);
            } catch (IndexOutOfBoundsException e) {
                arg = "?";
            }

            if (arg == null) {
                arg = "?";
            }

            argIdx++;

            // dump segment of sql up to question mark.
            dumpSql.append(sql, lastPos, Qpos);
            lastPos = Qpos + 1;
            Qpos = sql.indexOf('?', lastPos);
            dumpSql.append(arg);
        }
        if (lastPos < sql.length()) {
            // dump last segment
            dumpSql.append(sql.substring(lastPos));
        }

        return dumpSql.toString();
    }
}
