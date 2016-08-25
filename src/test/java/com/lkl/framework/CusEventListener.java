package com.lkl.framework;

import java.util.EventListener;

/**
 * 事件监听器
 * 
 * @author lkl
 * @version $Id: CusEventListener.java, v 0.1 2015年7月19日 下午10:14:01 lkl Exp $
 */
public class CusEventListener implements EventListener {

    public void fireCusEvent(CusEvent event) {
        EventSourceObject eventSource = (EventSourceObject) event.getSource();//获取事件源 
        System.out.println("监听内容：" + eventSource.getClickContent());
    }

}
