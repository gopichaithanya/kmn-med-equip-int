package id.co.kmn.webui.administrasi.equipment;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.EquipmentService;
import id.co.kmn.backend.model.Mmedequipment;
import id.co.kmn.backend.util.HibernateSearchObject;
import id.co.kmn.backend.util.ZksampleBeanUtils;
import id.co.kmn.util.Codec;
import id.co.kmn.util.ConstantUtil;
import id.co.kmn.webui.administrasi.report.MpegawaiSimpleDJReport;
import id.co.kmn.webui.util.*;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 5:52 AM
 */
public class EquipmentMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EquipmentMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowEquipmentMain; // autowired

    // Tabs
    protected Tabbox tabbox_EquipmentMain; // autowired
    protected Tab tabEquipmentList; // autowired
    protected Tab tabEquipmentDetail; // autowired
    protected Tabpanel tabPanelEquipmentList; // autowired
    protected Tabpanel tabPanelEquipmentDetail; // autowired

    // filter components
    protected Checkbox checkbox_EquipmentList_ShowAll; // autowired
    protected Textbox txtb_Equipment_Code; // aurowired
    protected Button button_EquipmentList_SearchCode; // aurowired
    protected Textbox txtb_Equipment_Name; // aurowired
    protected Button button_EquipmentList_SearchName; // aurowired
    protected Textbox txtb_Equipment_Alamat; // aurowired
    protected Button button_EquipmentList_SearchAlamat; // aurowired

    // checkRights
    protected Button button_EquipmentList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_EquipmentMain_";
    private ButtonStatusCtrl btnCtrlEquipment;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private EquipmentListCtrl equipmentListCtrl;
    private EquipmentDetailCtrl equipmentDetailCtrl;

    // Databinding
    private Mmedequipment selectedEquipment;
    private BindingListModelList equipments;

    // ServiceDAOs / Domain Classes
    private EquipmentService equipmentService;

    // always a copy from the bean before modifying. Used for reseting
    private Mmedequipment originalEquipment;

    /**
     * default constructor.<br>
     */
    public EquipmentMainCtrl() {
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
    public void onCreate$windowEquipmentMain(Event event) throws Exception {
        windowEquipmentMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlEquipment = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabEquipmentList.setSelected(true);

        if (tabPanelEquipmentList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipmentList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equipment/equipmentList.zul");
        }

        // init the buttons for editMode
        btnCtrlEquipment.setInitEdit();
    }

    /**
     * When the tab 'tabEquipmentList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabEquipmentList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelEquipmentList.getFirstChild() != null) {
            tabEquipmentList.setSelected(true);

            return;
        }

        if (tabPanelEquipmentList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipmentList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equipment/equipmentList.zul");
        }

    }

    /**
     * When the tab 'tabPanelEquipmentDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabEquipmentDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelEquipmentDetail.getFirstChild() != null) {
            tabEquipmentDetail.setSelected(true);

            // refresh the Binding mechanism
            getEquipmentDetailCtrl().setEquipment(getSelectedEquipment());
            getEquipmentDetailCtrl().getBinder().loadAll();
            //refresh combo
            getEquipmentDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelEquipmentDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipmentDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equipment/equipmentDetail.zul");
        }
    }

    /**
     * when the "print equipments list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_EquipmentList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_EquipmentList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_Equipment_Code.setValue(""); // clear
        txtb_Equipment_Name.setValue(""); // clear
        //txtb_Equipment_Alamat.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Mmedequipment> soEquipment = new HibernateSearchObject<Mmedequipment>(Mmedequipment.class, getEquipmentListCtrl().getCountRows());
        soEquipment.addSort(ConstantUtil.TYPE_NAME, false);

        // Change the BindingListModel.
        if (getEquipmentListCtrl().getBinder() != null) {
            getEquipmentListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipment);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_EquipmentMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabEquipmentList)) {
                tabEquipmentList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the equipment list with 'like equipment number'. <br>
     */
    public void onClick$button_EquipmentList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Equipment_Code.getValue().isEmpty()) {
            checkbox_EquipmentList_ShowAll.setChecked(false); // unCheck
            txtb_Equipment_Name.setValue(""); // clear
            //txtb_Equipment_Alamat.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedequipment> soEquipment = new HibernateSearchObject<Mmedequipment>(Mmedequipment.class, getEquipmentListCtrl().getCountRows());
            soEquipment.addFilter(new Filter(ConstantUtil.TYPE_CODE, "%" + txtb_Equipment_Code.getValue() + "%", Filter.OP_ILIKE));
            soEquipment.addSort(ConstantUtil.TYPE_CODE, false);

            // Change the BindingListModel.
            if (getEquipmentListCtrl().getBinder() != null) {
                getEquipmentListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipment);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipmentMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipmentList)) {
                    tabEquipmentList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the equipment list with 'like equipment name'. <br>
     */
    public void onClick$button_EquipmentList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Equipment_Name.getValue().isEmpty()) {
            checkbox_EquipmentList_ShowAll.setChecked(false); // unCheck
            //txtb_Equipment_Alamat.setValue(""); // clear
            txtb_Equipment_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedequipment> soEquipment = new HibernateSearchObject<Mmedequipment>(Mmedequipment.class, getEquipmentListCtrl().getCountRows());
            soEquipment.addFilter(new Filter(ConstantUtil.TYPE_NAME, "%" + txtb_Equipment_Name.getValue() + "%", Filter.OP_ILIKE));
            soEquipment.addSort(ConstantUtil.TYPE_NAME, false);

            // Change the BindingListModel.
            if (getEquipmentListCtrl().getBinder() != null) {
                getEquipmentListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipment);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipmentMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipmentList)) {
                    tabEquipmentList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the equipment list with 'like equipment city'. <br>
     */
    /*public void onClick$button_EquipmentList_SearchAlamat(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Equipment_Alamat.getValue().isEmpty()) {
            checkbox_EquipmentList_ShowAll.setChecked(false); // unCheck
            txtb_Equipment_Name.setValue(""); // clear
            txtb_Equipment_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedequipment> soEquipment = new HibernateSearchObject<Mmedequipment>(Mmedequipment.class, getEquipmentListCtrl().getCountRows());
            soEquipment.addFilter(new Filter("alamatUniv", "%" + txtb_Equipment_Alamat.getValue() + "%", Filter.OP_ILIKE));
            //soEquipment.addSort("alamatUniv", false);

            // Change the BindingListModel.
            if (getEquipmentListCtrl().getBinder() != null) {
                getEquipmentListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipment);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipmentMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipmentList)) {
                    tabEquipmentList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }

        }
    }*/

    /**
     * When the "help" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnHelp(Event event) throws InterruptedException {
        doHelp(event);
    }

    /**
     * When the "new" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnNew(Event event) throws InterruptedException {
        doNew(event);
    }

    /**
     * When the "save" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSave(Event event) throws InterruptedException {
        doSave(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnEdit(Event event) throws InterruptedException {
        doEdit(event);
    }

    /**
     * When the "delete" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnDelete(Event event) throws InterruptedException {
        doDelete(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnCancel(Event event) throws InterruptedException {
        doCancel(event);
    }

    /**
     * when the "refresh" button is clicked. <br>
     * <br>
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnRefresh(Event event) throws InterruptedException {
        doResizeSelectedTab(event);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * 1. Cancel the current action.<br>
     * 2. Reset the values to its origin.<br>
     * 3. Set UI components back to readonly/disable mode.<br>
     * 4. Set the buttons in edit mode.<br>
     *
     * @param event
     * @throws InterruptedException
     */
    private void doCancel(Event event) throws InterruptedException {
        // logger.debug(event.toString());

        // reset to the original object
        doResetToInitValues();

        // check first, if the tabs are created
        if (getEquipmentDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getEquipmentDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getEquipmentDetailCtrl().doReadOnlyMode(true);

            btnCtrlEquipment.setInitEdit();
        }
    }

    /**
     * Sets all UI-components to writable-mode. Sets the buttons to edit-Mode.
     * Checks first, if the NEEDED TABS with its contents are created. If not,
     * than create it by simulate an onSelect() with calling Events.sendEvent()
     *
     * @param event
     * @throws InterruptedException
     */
    private void doEdit(Event event) {
        // logger.debug(event.toString());

        // get the current Tab for later checking if we must change it
        Tab currentTab = tabbox_EquipmentMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getEquipmentDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipmentDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipmentDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipmentDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabEquipmentDetail)) {
            tabEquipmentDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getEquipmentDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlEquipment.setBtnStatus_Edit();

        getEquipmentDetailCtrl().doReadOnlyMode(false);
        // set focus
        getEquipmentDetailCtrl().txtb_code.focus();
    }

    /**
     * Deletes the selected Bean from the DB.
     *
     * @param event
     * @throws InterruptedException
     * @throws InterruptedException
     */
    private void doDelete(Event event) throws InterruptedException {
        // logger.debug(event.toString());

        // check first, if the tabs are created, if not than create them
        if (getEquipmentDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipmentDetail, null));
        }

        // check first, if the tabs are created
        if (getEquipmentDetailCtrl().getBinder() == null) {
            return;
        }

        final Mmedequipment anEquipment = getSelectedEquipment();
        if (anEquipment != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anEquipment.getName();
            final String title = Labels.getLabel("message.Deleting.Record");

            MultiLineMessageBox.doSetTemplate();
            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
                @Override
                public void onEvent(Event evt) {
                    switch (((Integer) evt.getData()).intValue()) {
                        case MultiLineMessageBox.YES:
                            try {
                                deleteBean();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            break; //
                        case MultiLineMessageBox.NO:
                            break; //
                    }
                }

                private void deleteBean() throws InterruptedException {
                    try {
                        getEquipmentService().delete(anEquipment);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlEquipment.setInitEdit();

        setSelectedEquipment(null);
        // refresh the list
        getEquipmentListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getEquipmentDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getEquipmentDetailCtrl().list_status.getSelectedItem() != null) {
            if (getEquipmentDetailCtrl().txtb_status.getValue() == getEquipmentDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getEquipmentDetailCtrl().getEquipment().setDicomSts(getEquipmentDetailCtrl().txtb_status.getValue());
            } else {
                getEquipmentDetailCtrl().txtb_status.setValue(getEquipmentDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getEquipmentDetailCtrl().getEquipment().setDicomSts(getEquipmentDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getEquipmentDetailCtrl().txtb_status.setValue(Codec.StatusAktif.Stat1.getValue());
            getEquipmentDetailCtrl().getEquipment().setDicomSts(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getEquipmentDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getEquipmentService().saveOrUpdate(getEquipmentDetailCtrl().getEquipment());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getEquipmentListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getEquipmentListCtrl().getListBoxEquipment(), getSelectedEquipment());

            // show the objects data in the statusBar
            String str = getSelectedEquipment().getName();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlEquipment.setInitEdit();
            getEquipmentDetailCtrl().doReadOnlyMode(true);
        }
    }

    /**
     * Sets all UI-components to writable-mode. Stores the current Beans as
     * originBeans and get new Objects from the backend.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doNew(Event event) {
        // logger.debug(event.toString());

        // check first, if the tabs are created
        if (getEquipmentDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipmentDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipmentDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipmentDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Mmedequipment anEquipment = getEquipmentService().getNew();

        // set the beans in the related databinded controllers
        getEquipmentDetailCtrl().setEquipment(anEquipment);
        getEquipmentDetailCtrl().setSelectedEquipment(anEquipment);

        // Refresh the binding mechanism
        getEquipmentDetailCtrl().setSelectedEquipment(getSelectedEquipment());
        if (getEquipmentDetailCtrl().getBinder() != null) {
            getEquipmentDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getEquipmentDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlEquipment.setInitNew();

        tabEquipmentDetail.setSelected(true);
        // set focus
        //getEquipmentDetailCtrl().txtb_ckdUniv.focus();

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Resizes the container from the selected Tab.
     *
     * @param event
     */
    private void doResizeSelectedTab(Event event) {
        // logger.debug(event.toString());

        if (tabbox_EquipmentMain.getSelectedTab() == tabEquipmentDetail) {
            getEquipmentDetailCtrl().doFitSize(event);
        } else if (tabbox_EquipmentMain.getSelectedTab() == tabEquipmentList) {
            // resize and fill Listbox new
            getEquipmentListCtrl().doFillListbox();
        }
    }

    /**
     * Opens the help screen for the current module.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doHelp(Event event) throws InterruptedException {

        ZksampleMessageUtils.doShowNotImplementedMessage();

        // we stop the propagation of the event, because zk will call ALL events
        // with the same name in the namespace and 'btnHelp' is a standard
        // button in this application and can often appears.
        // Events.getRealOrigin((ForwardEvent) event).stopPropagation();
        event.stopPropagation();
    }

    /**
     * Saves the selected object's current properties. We can get them back if a
     * modification is canceled.
     */
    public void doStoreInitValues() {

        if (getSelectedEquipment() != null) {

            try {
                setOriginalEquipment((Mmedequipment) ZksampleBeanUtils.cloneBean(getSelectedEquipment()));
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Reset the selected object to its origin property values.
     */
    public void doResetToInitValues() {

        if (getOriginalEquipment() != null) {

            try {
                setSelectedEquipment((Mmedequipment) ZksampleBeanUtils.cloneBean(getOriginalEquipment()));
                // TODO Bug in DataBinder??
                windowEquipmentMain.invalidate();

            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * User rights check. <br>
     * Only components are set visible=true if the logged-in <br>
     * user have the right for it. <br>
     * <p/>
     * The rights are get from the spring framework users grantedAuthority(). A
     * right is only a string. <br>
     */
    // TODO move it to zul
    private void doCheckRights() {

        final UserWorkspace workspace = getUserWorkspace();

        // window_EquipmentList.setVisible(workspace.isAllowed("window_EquipmentList"));
        button_EquipmentList_PrintList.setVisible(workspace.isAllowed("button_EquipmentList_PrintList"));
        button_EquipmentList_SearchCode.setVisible(workspace.isAllowed("button_EquipmentList_SearchCode"));
        button_EquipmentList_SearchName.setVisible(workspace.isAllowed("button_EquipmentList_SearchName"));
        button_EquipmentList_SearchAlamat.setVisible(workspace.isAllowed("button_EquipmentList_SearchAlamat"));

        btnHelp.setVisible(workspace.isAllowed("button_EquipmentMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_EquipmentMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_EquipmentMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_EquipmentMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_EquipmentMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalEquipment(Mmedequipment originalEquipment) {
        this.originalEquipment = originalEquipment;
    }

    public Mmedequipment getOriginalEquipment() {
        return this.originalEquipment;
    }

    public void setSelectedEquipment(Mmedequipment selectedEquipment) {
        this.selectedEquipment = selectedEquipment;
    }

    public Mmedequipment getSelectedEquipment() {
        return this.selectedEquipment;
    }

    public void setEquipments(BindingListModelList equipments) {
        this.equipments = equipments;
    }

    public BindingListModelList getEquipments() {
        return this.equipments;
    }

    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public EquipmentService getEquipmentService() {
        return this.equipmentService;
    }

    public void setEquipmentListCtrl(EquipmentListCtrl equipmentListCtrl) {
        this.equipmentListCtrl = equipmentListCtrl;
    }

    public EquipmentListCtrl getEquipmentListCtrl() {
        return this.equipmentListCtrl;
    }

    public void setEquipmentDetailCtrl(EquipmentDetailCtrl equipmentDetailCtrl) {
        this.equipmentDetailCtrl = equipmentDetailCtrl;
    }

    public EquipmentDetailCtrl getEquipmentDetailCtrl() {
        return this.equipmentDetailCtrl;
    }
}
