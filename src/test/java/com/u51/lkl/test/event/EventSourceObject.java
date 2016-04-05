package com.u51.lkl.test.event;

import java.util.HashSet;
import java.util.Set;

/**
 * 事件源 事件发生的地方
 * 由于事件源的某项属性或状态发生了改变(比如BUTTON被单击、TEXTBOX的值发生改变等等)导致某项事件发生。
 * 换句话说就是生成了相应的事件对象。
 * 因为事件监听器要注册在事件源上,所以事件源类中应该要有盛装监听器的容器(List,Set等等)
 * 
 * @author lkl
 * @version $Id: EventSourceObject.java, v 0.1 2015年7月19日 下午10:16:48 lkl Exp $
 */
public class EventSourceObject {

    private String                clickContent;
    private Set<CusEventListener> listenerContainer = new HashSet<CusEventListener>();

    public void addListener(CusEventListener listener) {
        listenerContainer.add(listener);
    }

    public void notifies(CusEvent event) { //通知监听器  这是观察者设计模式
        for (CusEventListener listener : listenerContainer) {
            listener.fireCusEvent(event);
        }
    }

    /**
     * 创建时间的方法，最后是放到子类去继承实现，在增加新的事件的时候，就不会出现问题
     */
    public void clickButton() { //按钮点击，点击后，触发点击事件(extend CusEvent) 然后通知listener
        System.out.println("在事件源中发生点击事件");
        CusEvent event = new CusEvent(this);
        clickContent = "按钮被点击";
        this.notifies(event);
    }

    public String getClickContent() {
        return clickContent;
    }

    public void setClickContent(String clickContent) {
        this.clickContent = clickContent;
    }

}
