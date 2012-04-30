package id.ac.idu.webui.administrasi.mahasiswa.model;

import id.ac.idu.backend.model.Mjabatan;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import java.io.Serializable;

/**
 * @author <a href="dbbottle@gmail.com">hermanto</a>
 * @Date 25 Mar 12
 * ==================================================================
 * Copyright (c) 2012  All rights reserved.
 * ==================================================================
 */

public class KhususSearchJabatanList implements ListitemRenderer, Serializable {
    private static final long serialVersionUID = 1925499383404057064L;
	private static final Logger logger = Logger.getLogger(PribadiSearchKodeposList.class);

    @Override
	public void render(Listitem item, Object data) throws Exception {
		final Mjabatan jabatan = (Mjabatan) data;

		Listcell lc = new Listcell(jabatan.getCnmjabatan());
		lc.setParent(item);

		item.setAttribute("data", data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onJabatanItem");
	}

}
