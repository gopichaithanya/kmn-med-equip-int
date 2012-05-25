package id.co.kmn.webui.administrasi.system;

import id.co.kmn.administrasi.service.SystemService;
import id.co.kmn.backend.model.Mmedsystem;
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
 * Time: 7:27 AM
 */
public class SystemListCtrl extends GFCBaseListCtrl<Mmedsystem> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(SystemListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowSystemList; // autowired
    protected Panel panelSystemList; // autowired

    protected Borderlayout borderLayout_systemList; // autowired
    protected Paging paging_SystemList; // autowired
    protected Listbox listBoxSystem; // autowired
    protected Listheader listheader_SystemList_Code; // autowired
    protected Listheader listheader_SystemList_Name; // autowired
    protected Listheader listheader_SystemList_Alamat; // autowired
    protected Listheader listheader_SystemList_Status; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Mmedsystem> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private SystemMainCtrl systemMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient SystemService systemService;

    /**
     * default constructor.<br>
     */
    public SystemListCtrl() {
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
            setSystemMainCtrl((SystemMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getSystemMainCtrl().setSystemListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getSystemMainCtrl().getSelectedSystem() != null) {
                setSelectedSystem(getSystemMainCtrl().getSelectedSystem());
            } else
                setSelectedSystem(null);
        } else {
            setSelectedSystem(null);
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

    public void onCreate$windowSystemList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_SystemList.setPageSize(getCountRows());
        paging_SystemList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_SystemList_Code.setSortAscending(new FieldComparator(ConstantUtil.SYSTEM_KEY, true));
        listheader_SystemList_Code.setSortDescending(new FieldComparator(ConstantUtil.SYSTEM_KEY, false));
        listheader_SystemList_Name.setSortAscending(new FieldComparator(ConstantUtil.SYSTEM_VALUE, true));
        listheader_SystemList_Name.setSortDescending(new FieldComparator(ConstantUtil.SYSTEM_VALUE, false));
        listheader_SystemList_Status.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        listheader_SystemList_Status.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));

        // ++ create the searchObject and init sorting ++//
        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Mmedsystem>(Mmedsystem.class, getCountRows());
        searchObj.addSort(ConstantUtil.SYSTEM_KEY, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxSystem(), paging_SystemList);
        BindingListModelList lml = (BindingListModelList) getListBoxSystem().getModel();
        setSystems(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedSystem() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxSystem().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedSystem((Mmedsystem) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxSystem(), getSelectedSystem()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedSystemItem(Event event) {
        // logger.debug(event.toString());

        Mmedsystem anSystem = getSelectedSystem();

        if (anSystem != null) {
            setSelectedSystem(anSystem);
            setSystem(anSystem);

            // check first, if the tabs are created
            if (getSystemMainCtrl().getSystemDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getSystemMainCtrl().tabSystemDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getSystemMainCtrl().getSystemDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getSystemMainCtrl().tabSystemDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getSystemMainCtrl().tabSystemDetail, anSystem));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxSystem(Event event) {
        // logger.debug(event.toString());

        // selectedSystem is filled by annotated databinding mechanism
        Mmedsystem anSystem = getSelectedSystem();

        if (anSystem == null) {
            return;
        }

        // check first, if the tabs are created
        if (getSystemMainCtrl().getSystemDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getSystemMainCtrl().tabSystemDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSystemMainCtrl().getSystemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getSystemMainCtrl().tabSystemDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getSystemMainCtrl().getSystemDetailCtrl().setSelectedSystem(anSystem);
        getSystemMainCtrl().getSystemDetailCtrl().setSystem(anSystem);

        // store the selected bean values as current
        getSystemMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.System") + ": " + anSystem.getSystemKey();
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
        borderLayout_systemList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowSystemList.invalidate();
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
    public Mmedsystem getSystem() {
        // STORED IN THE module's MainController
        return getSystemMainCtrl().getSelectedSystem();
    }

    public void setSystem(Mmedsystem anSystem) {
        // STORED IN THE module's MainController
        getSystemMainCtrl().setSelectedSystem(anSystem);
    }

    public void setSelectedSystem(Mmedsystem selectedSystem) {
        // STORED IN THE module's MainController
        getSystemMainCtrl().setSelectedSystem(selectedSystem);
    }

    public Mmedsystem getSelectedSystem() {
        // STORED IN THE module's MainController
        return getSystemMainCtrl().getSelectedSystem();
    }

    public void setSystems(BindingListModelList systems) {
        // STORED IN THE module's MainController
        getSystemMainCtrl().setSystems(systems);
    }

    public BindingListModelList getSystems() {
        // STORED IN THE module's MainController
        return getSystemMainCtrl().getSystems();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Mmedsystem> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Mmedsystem> getSearchObj() {
        return this.searchObj;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public SystemService getSystemService() {
        return this.systemService;
    }

    public Listbox getListBoxSystem() {
        return this.listBoxSystem;
    }

    public void setListBoxSystem(Listbox listBoxSystem) {
        this.listBoxSystem = listBoxSystem;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setSystemMainCtrl(SystemMainCtrl systemMainCtrl) {
        this.systemMainCtrl = systemMainCtrl;
    }

    public SystemMainCtrl getSystemMainCtrl() {
        return this.systemMainCtrl;
    }
}
