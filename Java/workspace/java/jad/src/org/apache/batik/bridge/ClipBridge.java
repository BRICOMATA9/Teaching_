// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.renderable.ClipRable;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.batik.bridge:
//            Bridge, BridgeContext

public interface ClipBridge
    extends Bridge
{

    public abstract ClipRable createClip(BridgeContext bridgecontext, Element element, Element element1, GraphicsNode graphicsnode);
}
