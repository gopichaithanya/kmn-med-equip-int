package id.co.kmn.webui.administrasi.equipment;

import id.co.kmn.administrasi.service.EquipmentService;
import id.co.kmn.backend.model.Mmedequipment;
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
 * @Date 5/25/12
 * Time: 5:52 AM
 */
public class EquipmentListCtrl extends GFCBaseListCtrl<Mmedequipment> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(EquipmentListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowEquipmentList; // autowired
    protected Panel panelEquipmentList; // autowired

    protected Borderlayout borderLayout_equipmentList; // autowired
    protected Paging paging_EquipmentList; // autowired
    protected Listbox listBoxEquipment; // autowired
    protected Listheader listheader_EquipmentList_Code; // autowired
    protected Listheader listheader_EquipmentList_Name; // autowired
    protected Listheader listheader_EquipmentList_Producer; // autowired
    protected Listheader listheader_EquipmentList_Type; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Mmedequipment> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private EquipmentMainCtrl equipmentMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient EquipmentService equipmentService;

    /**
     * default constructor.<br>
     */
    public EquipmentListCtrl() {
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
            setEquipmentMainCtrl((EquipmentMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getEquipmentMainCtrl().setEquipmentListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getEquipmentMainCtrl().getSelectedEquipment() != null) {
                setSelectedEquipment(getEquipmentMainCtrl().getSelectedEquipment());
            } else
                setSelectedEquipment(null);
        } else {
            setSelectedEquipment(null);
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

    public void onCreate$windowEquipmentList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_EquipmentList.setPageSize(getCountRows());
        paging_EquipmentList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_EquipmentList_Code.setSortAscending(new FieldComparator(ConstantUtil.EQUIPMENT_CODE, true));
        listheader_EquipmentList_Code.setSortDescending(new FieldComparator(ConstantUtil.EQUIPMENT_CODE, false));
        listheader_EquipmentList_Name.setSortAscending(new FieldComparator(ConstantUtil.EQUIPMENT_NAME, true));
        listheader_EquipmentList_Name.setSortDescending(new FieldComparator(ConstantUtil.EQUIPMENT_NAME, false));
        listheader_EquipmentList_Producer.setSortAscending(new FieldComparator(ConstantUtil.EQUIPMENT_PROD_NAME, true));
        listheader_EquipmentList_Producer.setSortDescending(new FieldComparator(ConstantUtil.EQUIPMENT_PROD_NAME, false));
        listheader_EquipmentList_Type.setSortAscending(new FieldComparator(ConstantUtil.EQUIPMENT_TYPE_NAME, true));
        listheader_EquipmentList_Type.setSortDescending(new FieldComparator(ConstantUtil.EQUIPMENT_TYPE_NAME, false));

        // ++ create the searchObject and init sorting ++//
        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Mmedequipment>(Mmedequipment.class, getCountRows());
        searchObj.addSort(ConstantUtil.EQUIPMENT_CODE, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxEquipment(), paging_EquipmentList);
        BindingListModelList lml = (BindingListModelList) getListBoxEquipment().getModel();
        setEquipments(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedEquipment() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxEquipment().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedEquipment((Mmedequipment) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxEquipment(), getSelectedEquipment()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedEquipmentItem(Event event) {
        // logger.debug(event.toString());

        Mmedequipment anEquipment = getSelectedEquipment();

        if (anEquipment != null) {
            setSelectedEquipment(anEquipment);
            setEquipment(anEquipment);

            // check first, if the tabs are created
            if (getEquipmentMainCtrl().getEquipmentDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getEquipmentMainCtrl().tabEquipmentDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getEquipmentMainCtrl().getEquipmentDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getEquipmentMainCtrl().tabEquipmentDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getEquipmentMainCtrl().tabEquipmentDetail, anEquipment));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxEquipment(Event event) {
        // logger.debug(event.toString());

        // selectedEquipment is filled by annotated databinding mechanism
        Mmedequipment anEquipment = getSelectedEquipment();

        if (anEquipment == null) {
            return;
        }

        // check first, if the tabs are created
        if (getEquipmentMainCtrl().getEquipmentDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getEquipmentMainCtrl().tabEquipmentDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipmentMainCtrl().getEquipmentDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getEquipmentMainCtrl().tabEquipmentDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getEquipmentMainCtrl().getEquipmentDetailCtrl().setSelectedEquipment(anEquipment);
        getEquipmentMainCtrl().getEquipmentDetailCtrl().setEquipment(anEquipment);

        // store the selected bean values as current
        getEquipmentMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.Equipment") + ": " + anEquipment.getEquipmentCode();
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
        borderLayout_equipmentList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowEquipmentList.invalidate();
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
    public Mmedequipment getEquipment() {
        // STORED IN THE module's MainController
        return getEquipmentMainCtrl().getSelectedEquipment();
    }

    public void setEquipment(Mmedequipment anEquipment) {
        // STORED IN THE module's MainController
        getEquipmentMainCtrl().setSelectedEquipment(anEquipment);
    }

    public void setSelectedEquipment(Mmedequipment selectedEquipment) {
        // STORED IN THE module's MainController
        getEquipmentMainCtrl().setSelectedEquipment(selectedEquipment);
    }

    public Mmedequipment getSelectedEquipment() {
        // STORED IN THE module's MainController
        return getEquipmentMainCtrl().getSelectedEquipment();
    }

    public void setEquipments(BindingListModelList equipments) {
        // STORED IN THE module's MainController
        getEquipmentMainCtrl().setEquipments(equipments);
    }

    public BindingListModelList getEquipments() {
        // STORED IN THE module's MainController
        return getEquipmentMainCtrl().getEquipments();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Mmedequipment> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Mmedequipment> getSearchObj() {
        return this.searchObj;
    }

    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public EquipmentService getEquipmentService() {
        return this.equipmentService;
    }

    public Listbox getListBoxEquipment() {
        return this.listBoxEquipment;
    }

    public void setListBoxEquipment(Listbox listBoxEquipment) {
        this.listBoxEquipment = listBoxEquipment;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setEquipmentMainCtrl(EquipmentMainCtrl equipmentMainCtrl) {
        this.equipmentMainCtrl = equipmentMainCtrl;
    }

    public EquipmentMainCtrl getEquipmentMainCtrl() {
        return this.equipmentMainCtrl;
    }
}
