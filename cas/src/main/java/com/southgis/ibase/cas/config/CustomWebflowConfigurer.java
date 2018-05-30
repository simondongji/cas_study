package com.southgis.ibase.cas.config;

import com.southgis.ibase.cas.UsernamePasswordSysCredential;
import org.apereo.cas.web.flow.AbstractCasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 重新定义默认的web流程
 *
 * @author simon
 * @create 2018-05-30 10:25
 **/
public class CustomWebflowConfigurer extends AbstractCasWebflowConfigurer {
  /**
   * 构造函数
   *
   * @param flowBuilderServices
   * @param loginFlowDefinitionRegistry
   */
  public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry loginFlowDefinitionRegistry) {
    super(flowBuilderServices, loginFlowDefinitionRegistry);
  }
  
  @Override
  protected void doInitialize() throws Exception {
    Flow flow = getLoginFlow();
    bindCredential(flow);
  }
  
  /**
   * 绑定输入信息
   *
   * @param flow
   *          流程
   */
  protected void bindCredential(Flow flow){
    //重写绑定自定义credential
    createFlowVariable(flow,CasWebflowConstants.VAR_ID_CREDENTIAL,UsernamePasswordSysCredential.class);
    //登录页绑定新参数
    final ViewState state = (ViewState)flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
    final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
    //由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
    cfg.addBinding(new BinderConfiguration.Binding("system", null, false));
  }
}
