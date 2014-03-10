package id.co.kmn.webui.administrasi.equiptype;

import id.co.kmn.administrasi.service.EquipTypeService;
import id.co.kmn.backend.model.Mmedequiptype;
import id.co.kmn.backend.util.HibernateSearchObject;
import id.co.kmn.util.ConstantUtil;
import id.co.kmn.webui.util.GFCBaseListCtrl;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.io.Serializable;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 5:25 AM
 */
public class EquipTypeListCtrl extends GFCBaseListCtrl<Mmedequiptype> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(EquipTypeListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowEquipTypeList; // autowired
    protected Panel panelEquipTypeList; // autowired

    protected Borderlayout borderLayout_equipTypeList; // autowired
    protected Paging paging_EquipTypeList; // autowired
    protected Listbox listBoxEquipType; // autowired
    protected Listheader listheader_EquipTypeList_Code; // autowired
    protected Listheader listheader_EquipTypeList_Name; // autowired
    protected Listheader listheader_EquipTypeList_Alamat; // autowired
    protected Listheader listheader_EquipTypeList_Status; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Mmedequiptype> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private EquipTypeMainCtrl equipTypeMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient EquipTypeService equipTypeService;

    /**
     * default constructor.<br>
     */
    public EquipTypeListCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        /**
         * 1. Set an 'alias' for this composer name to access it in the
         * zul-file.<br>
         * 2. Set the parameter 'recurse' to 'false' to avoid problems with
         * managing more than one zul-file in one page. Otherwise it would be
         * overridden and can ends in curious error messages.
         */
        this.self.setAttribute("controller", this, false);

        /**
         * 1. Get the overhanded MainController.<br>
         * 2. Set this controller in the MainController.<br>
         * 3. Check if a 'selectedObject' exists yet in the MainController.<br>
         */
        if (arg.containsKey("ModuleMainController")) {
            setEquipTypeMainCtrl((EquipTypeMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getEquipTypeMainCtrl().setEquipTypeListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getEquipTypeMainCtrl().getSelectedEquipType() != null) {
                setSelectedEquipType(getEquipTypeMainCtrl().getSelectedEquipType());
            } else
                setSelectedEquipType(null);
        } else {
            setSelectedEquipType(null);
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++ Component Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Automatically called method from zk.
     *
     * @param event
     * @throws Exception
     */

    public void onCreate$windowEquipTypeList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_EquipTypeList.setPageSize(getCountRows());
        paging_EquipTypeList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_EquipTypeList_Code.setSortAscending(new FieldComparator(ConstantUtil.TYPE_CODE, true));
        listheader_EquipTypeList_Code.setSortDescending(new FieldComparator(ConstantUtil.TYPE_CODE, false));
        listheader_EquipTypeList_Name.setSortAscending(new FieldComparator(ConstantUtil.TYPE_NAME, true));
        listheader_EquipTypeList_Name.setSortDescending(new FieldComparator(ConstantUtil.TYPE_NAME, false));
        //listheader_EquipTypeList_Alamat.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        //listheader_EquipTypeList_Alamat.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));
        listheader_EquipTypeList_Status.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        listheader_EquipTypeList_Status.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));

        // ++ create the searchObject and init sorting ++//
        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Mmedequiptype>(Mmedequiptype.class, getCountRows());
        searchObj.addSort(ConstantUtil.TYPE_CODE, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxEquipType(), paging_EquipTypeList);
        BindingListModelList lml = (BindingListModelList) getListBoxEquipType().getModel();
        setEquipTypes(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedEquipType() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxEquipType().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedEquipType((Mmedequiptype) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxEquipType(), getSelectedEquipType()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedEquipTypeItem(Event event) {
        // logger.debug(event.toString());

        Mmedequiptype anEquipType = getSelectedEquipType();

        if (anEquipType != null) {
            setSelectedEquipType(anEquipType);
            setEquipType(anEquipType);

            // check first, if the tabs are created
            if (getEquipTypeMainCtrl().getEquipTypeDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getEquipTypeMainCtrl().tabEquipTypeDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getEquipTypeMainCtrl().getEquipTypeDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getEquipTypeMainCtrl().tabEquipTypeDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getEquipTypeMainCtrl().tabEquipTypeDetail, anEquipType));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxEquipType(Event event) {
        // logger.debug(event.toString());

        // selectedEquipType is filled by annotated databinding mechanism
        Mmedequiptype anEquipType = getSelectedEquipType();

        if (anEquipType == null) {
            return;
        }

        // check first, if the tabs are created
        if (getEquipTypeMainCtrl().getEquipTypeDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getEquipTypeMainCtrl().tabEquipTypeDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipTypeMainCtrl().getEquipTypeDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getEquipTypeMainCtrl().tabEquipTypeDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getEquipTypeMainCtrl().getEquipTypeDetailCtrl().setSelectedEquipType(anEquipType);
        getEquipTypeMainCtrl().getEquipTypeDetailCtrl().setEquipType(anEquipType);

        // store the selected bean values as current
        getEquipTypeMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.EquipType") + ": " + anEquipType.getTypeCode();
        EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Recalculates the container size for this controller and resize them.
     * <p/>
     * Calculate how many rows have been place in the listbox. Get the
     * currentDesktopHeight from a hidden Intbox from the index.zul that are
     * filled by onClientInfo() in the indexCtroller.
     */
    public void doFitSize() {
        // normally 0 ! Or we have a i.e. a toolBar on top of the listBox.
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        setCountRows((int) Math.round(maxListBoxHeight / 17.7));
        borderLayout_equipTypeList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowEquipTypeList.invalidate();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    /**
     * Best Pratice Hint:<br>
     * The setters/getters for the local annotated data binded Beans/Sets are
     * administered in the module's mainController. Working in this way you have
     * clean line to share this beans/sets with other controllers.
     */
    public Mmedequiptype getEquipType() {
        // STORED IN THE module's MainController
        return getEquipTypeMainCtrl().getSelectedEquipType();
    }

    public void setEquipType(Mmedequiptype anEquipType) {
        // STORED IN THE module's MainController
        getEquipTypeMainCtrl().setSelectedEquipType(anEquipType);
    }

    public void setSelectedEquipType(Mmedequiptype selectedEquipType) {
        // STORED IN THE module's MainController
        getEquipTypeMainCtrl().setSelectedEquipType(selectedEquipType);
    }

    public Mmedequiptype getSelectedEquipType() {
        // STORED IN THE module's MainController
        return getEquipTypeMainCtrl().getSelectedEquipType();
    }

    public void setEquipTypes(BindingListModelList equipTypes) {
        // STORED IN THE module's MainController
        getEquipTypeMainCtrl().setEquipTypes(equipTypes);
    }

    public BindingListModelList getEquipTypes() {
        // STORED IN THE module's MainController
        return getEquipTypeMainCtrl().getEquipTypes();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Mmedequiptype> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Mmedequiptype> getSearchObj() {
        return this.searchObj;
    }

    public void setEquipTypeService(EquipTypeService equipTypeService) {
        this.equipTypeService = equipTypeService;
    }

    public EquipTypeService getEquipTypeService() {
        return this.equipTypeService;
    }

    public Listbox getListBoxEquipType() {
        return this.listBoxEquipType;
    }

    public void setListBoxEquipType(Listbox listBoxEquipType) {
        this.listBoxEquipType = listBoxEquipType;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setEquipTypeMainCtrl(EquipTypeMainCtrl equipTypeMainCtrl) {
        this.equipTypeMainCtrl = equipTypeMainCtrl;
    }

    public EquipTypeMainCtrl getEquipTypeMainCtrl() {
        return this.equipTypeMainCtrl;
    }
}
