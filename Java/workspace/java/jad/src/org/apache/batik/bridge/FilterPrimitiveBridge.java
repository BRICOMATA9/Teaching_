// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.batik.bridge;

import java.awt.geom.Rectangle2D;
import java.util.Map;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.batik.bridge:
//            Bridge, BridgeContext

public interface FilterPrimitiveBridge
    extends Bridge
{

    public abstract Filter createFilter(BridgeContext bridgecontext, Element element, Element element1, GraphicsNode graphicsnode, Filter filter, Rectangle2D rectangle2d, Map map);
}
