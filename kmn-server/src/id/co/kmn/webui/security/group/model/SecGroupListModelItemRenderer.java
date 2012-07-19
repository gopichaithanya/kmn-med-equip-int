/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package id.co.kmn.webui.security.group.model;

import id.co.kmn.backend.model.SecGroup;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import java.io.Serializable;

/**
 * Item renderer for listitems in the listbox.
 *
 * @author bbruhns
 * @author sgerth
 */
public class SecGroupListModelItemRenderer implements ListitemRenderer, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SecGroupListModelItemRenderer.class);

    @Override
    public void render(Listitem item, Object data) throws Exception {

        final SecGroup group = (SecGroup) data;

        Listcell lc;

        lc = new Listcell(group.getGrpShortdescription());
        lc.setParent(item);

        lc = new Listcell(group.getGrpLongdescription());
        lc.setParent(item);

        item.setAttribute("data", data);
        // ComponentsCtrl.applyForward(img, "onClick=onImageClicked");
        // ComponentsCtrl.applyForward(item, "onClick=onClicked");
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");

    }

}