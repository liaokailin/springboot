package com.u51.lkl.test.event;

import java.util.EventObject;

/**
 * 自定义事件
 * 事件封装对应的事件源以及事件的参数
 * 
 * 
 * @author lkl
 * @version $Id: CusEvent.java, v 0.1 2015年7月19日 下午10:09:36 lkl Exp $
 */
public class CusEvent extends EventObject {

    public CusEvent(Object source) { //在父类中定义了source 保存事件源
        super(source);
    }

    private static final long serialVersionUID = -4252348226547895702L;

}
