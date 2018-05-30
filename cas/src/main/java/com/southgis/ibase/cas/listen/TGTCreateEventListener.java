package com.southgis.ibase.cas.listen;

import com.southgis.ibase.cas.service.IUserIdObtainService;
import com.southgis.ibase.cas.service.TriggerLogoutService;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TGT创建监听
 * 识别事件然后删除
 *
 * @author simon
 * @create 2018-05-30 15:00
 **/
public class TGTCreateEventListener {
  private TriggerLogoutService logoutService;
  private IUserIdObtainService service;
  
  public TGTCreateEventListener(@NotNull TriggerLogoutService logoutService, @NotNull IUserIdObtainService service) {
    this.logoutService = logoutService;
    this.service = service;
  }
  
  @EventListener
  @Async
  public void onTgtCreateEvent(CasTicketGrantingTicketCreatedEvent event) {
    TicketGrantingTicket ticketGrantingTicket = event.getTicketGrantingTicket();
    String id = ticketGrantingTicket.getAuthentication().getPrincipal().getId();
    String tgt = ticketGrantingTicket.getId();
    String clientName = (String) ticketGrantingTicket.getAuthentication().getAttributes().get("clientName");
    //获取可以认证的id
    List<String> authIds = service.obtain(clientName, id);
    if (authIds != null) {
      //循环触发登出
      authIds.forEach(authId -> logoutService.triggerLogout(authId, tgt));
    }
  }
}
