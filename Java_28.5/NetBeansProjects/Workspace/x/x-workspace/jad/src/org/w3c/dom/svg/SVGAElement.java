// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.w3c.dom.svg;

import org.w3c.dom.events.EventTarget;

// Referenced classes of package org.w3c.dom.svg:
//            SVGElement, SVGURIReference, SVGTests, SVGLangSpace, 
//            SVGExternalResourcesRequired, SVGStylable, SVGTransformable, SVGAnimatedString

public interface SVGAElement
    extends SVGElement, SVGURIReference, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGTransformable, EventTarget
{

    public abstract SVGAnimatedString getTarget();
}
