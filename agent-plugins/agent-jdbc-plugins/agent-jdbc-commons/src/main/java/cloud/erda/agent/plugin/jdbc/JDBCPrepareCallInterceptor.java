/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package cloud.erda.agent.plugin.jdbc;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.context.IMethodInterceptContext;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.DynamicFieldEnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import cloud.erda.agent.plugin.jdbc.trace.ConnectionInfo;
import cloud.erda.agent.plugin.jdbc.trace.SWCallableStatement;

import java.sql.CallableStatement;
import java.sql.Connection;

/**
 * {@link JDBCPrepareCallInterceptor} return {@link SWCallableStatement} instance that wrapper the real CallStatement
 * instance when the client call <code>prepareCall</code> method.
 *
 * @author zhangxin
 */
public class JDBCPrepareCallInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(IMethodInterceptContext context,
                             MethodInterceptResult result) throws Throwable {

    }

    @Override
    public Object afterMethod(IMethodInterceptContext context,
                              Object ret) throws Throwable {
        if (((DynamicFieldEnhancedInstance) context.getInstance()).getDynamicField() == null) {
            return ret;
        }
        return new SWCallableStatement((Connection) context.getInstance(), (CallableStatement) ret, (ConnectionInfo) ((DynamicFieldEnhancedInstance) context.getInstance()).getDynamicField(), (String) context.getArguments()[0]);
    }

    @Override
    public void handleMethodException(IMethodInterceptContext context, Throwable t) {

    }
}
