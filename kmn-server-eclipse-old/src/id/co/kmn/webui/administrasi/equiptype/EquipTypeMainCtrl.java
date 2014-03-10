package id.co.kmn.webui.administrasi.equiptype;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.EquipTypeService;
import id.co.kmn.backend.model.Mmedequiptype;
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
 * @Date 5/10/12
 * Time: 5:25 AM
 */
public class EquipTypeMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EquipTypeMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowEquipTypeMain; // autowired

    // Tabs
    protected Tabbox tabbox_EquipTypeMain; // autowired
    protected Tab tabEquipTypeList; // autowired
    protected Tab tabEquipTypeDetail; // autowired
    protected Tabpanel tabPanelEquipTypeList; // autowired
    protected Tabpanel tabPanelEquipTypeDetail; // autowired

    // filter components
    protected Checkbox checkbox_EquipTypeList_ShowAll; // autowired
    protected Textbox txtb_EquipType_Code; // aurowired
    protected Button button_EquipTypeList_SearchCode; // aurowired
    protected Textbox txtb_EquipType_Name; // aurowired
    protected Button button_EquipTypeList_SearchName; // aurowired
    protected Textbox txtb_EquipType_Alamat; // aurowired
    protected Button button_EquipTypeList_SearchAlamat; // aurowired

    // checkRights
    protected Button button_EquipTypeList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_EquipTypeMain_";
    private ButtonStatusCtrl btnCtrlEquipType;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private EquipTypeListCtrl equipTypeListCtrl;
    private EquipTypeDetailCtrl equipTypeDetailCtrl;

    // Databinding
    private Mmedequiptype selectedEquipType;
    private BindingListModelList equipTypes;

    // ServiceDAOs / Domain Classes
    private EquipTypeService equipTypeService;

    // always a copy from the bean before modifying. Used for reseting
    private Mmedequiptype originalEquipType;

    /**
     * default constructor.<br>
     */
    public EquipTypeMainCtrl() {
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
    public void onCreate$windowEquipTypeMain(Event event) throws Exception {
        windowEquipTypeMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlEquipType = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabEquipTypeList.setSelected(true);

        if (tabPanelEquipTypeList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipTypeList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equiptype/equiptypeList.zul");
        }

        // init the buttons for editMode
        btnCtrlEquipType.setInitEdit();
    }

    /**
     * When the tab 'tabEquipTypeList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabEquipTypeList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelEquipTypeList.getFirstChild() != null) {
            tabEquipTypeList.setSelected(true);

            return;
        }

        if (tabPanelEquipTypeList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipTypeList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equiptype/equiptypeList.zul");
        }

    }

    /**
     * When the tab 'tabPanelEquipTypeDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabEquipTypeDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelEquipTypeDetail.getFirstChild() != null) {
            tabEquipTypeDetail.setSelected(true);

            // refresh the Binding mechanism
            getEquipTypeDetailCtrl().setEquipType(getSelectedEquipType());
            getEquipTypeDetailCtrl().getBinder().loadAll();
            //refresh combo
            getEquipTypeDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelEquipTypeDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelEquipTypeDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/equiptype/equiptypeDetail.zul");
        }
    }

    /**
     * when the "print equipTypes list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_EquipTypeList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_EquipTypeList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_EquipType_Code.setValue(""); // clear
        txtb_EquipType_Name.setValue(""); // clear
        //txtb_EquipType_Alamat.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Mmedequiptype> soEquipType = new HibernateSearchObject<Mmedequiptype>(Mmedequiptype.class, getEquipTypeListCtrl().getCountRows());
        soEquipType.addSort(ConstantUtil.TYPE_NAME, false);

        // Change the BindingListModel.
        if (getEquipTypeListCtrl().getBinder() != null) {
            getEquipTypeListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipType);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_EquipTypeMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabEquipTypeList)) {
                tabEquipTypeList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the equipType list with 'like equipType number'. <br>
     */
    public void onClick$button_EquipTypeList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_EquipType_Code.getValue().isEmpty()) {
            checkbox_EquipTypeList_ShowAll.setChecked(false); // unCheck
            txtb_EquipType_Name.setValue(""); // clear
            //txtb_EquipType_Alamat.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedequiptype> soEquipType = new HibernateSearchObject<Mmedequiptype>(Mmedequiptype.class, getEquipTypeListCtrl().getCountRows());
            soEquipType.addFilter(new Filter(ConstantUtil.TYPE_CODE, "%" + txtb_EquipType_Code.getValue() + "%", Filter.OP_ILIKE));
            soEquipType.addSort(ConstantUtil.TYPE_CODE, false);

            // Change the BindingListModel.
            if (getEquipTypeListCtrl().getBinder() != null) {
                getEquipTypeListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipType);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipTypeMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipTypeList)) {
                    tabEquipTypeList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the equipType list with 'like equipType name'. <br>
     */
    public void onClick$button_EquipTypeList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_EquipType_Name.getValue().isEmpty()) {
            checkbox_EquipTypeList_ShowAll.setChecked(false); // unCheck
            //txtb_EquipType_Alamat.setValue(""); // clear
            txtb_EquipType_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedequiptype> soEquipType = new HibernateSearchObject<Mmedequiptype>(Mmedequiptype.class, getEquipTypeListCtrl().getCountRows());
            soEquipType.addFilter(new Filter(ConstantUtil.TYPE_NAME, "%" + txtb_EquipType_Name.getValue() + "%", Filter.OP_ILIKE));
            soEquipType.addSort(ConstantUtil.TYPE_NAME, false);

            // Change the BindingListModel.
            if (getEquipTypeListCtrl().getBinder() != null) {
                getEquipTypeListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipType);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipTypeMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipTypeList)) {
                    tabEquipTypeList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the equipType list with 'like equipType city'. <br>
     */
    /*public void onClick$button_EquipTypeList_SearchAlamat(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_EquipType_Alamat.getValue().isEmpty()) {
            checkbox_EquipTypeList_ShowAll.setChecked(false); // unCheck
            txtb_EquipType_Name.setValue(""); // clear
            txtb_EquipType_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<MmedequipType> soEquipType = new HibernateSearchObject<MmedequipType>(MmedequipType.class, getEquipTypeListCtrl().getCountRows());
            soEquipType.addFilter(new Filter("alamatUniv", "%" + txtb_EquipType_Alamat.getValue() + "%", Filter.OP_ILIKE));
            //soEquipType.addSort("alamatUniv", false);

            // Change the BindingListModel.
            if (getEquipTypeListCtrl().getBinder() != null) {
                getEquipTypeListCtrl().getPagedBindingListWrapper().setSearchObject(soEquipType);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_EquipTypeMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabEquipTypeList)) {
                    tabEquipTypeList.setSelected(true);
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
        if (getEquipTypeDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getEquipTypeDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getEquipTypeDetailCtrl().doReadOnlyMode(true);

            btnCtrlEquipType.setInitEdit();
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
        Tab currentTab = tabbox_EquipTypeMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getEquipTypeDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipTypeDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipTypeDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipTypeDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabEquipTypeDetail)) {
            tabEquipTypeDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getEquipTypeDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlEquipType.setBtnStatus_Edit();

        getEquipTypeDetailCtrl().doReadOnlyMode(false);
        // set focus
        getEquipTypeDetailCtrl().txtb_code.focus();
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
        if (getEquipTypeDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipTypeDetail, null));
        }

        // check first, if the tabs are created
        if (getEquipTypeDetailCtrl().getBinder() == null) {
            return;
        }

        final Mmedequiptype anEquipType = getSelectedEquipType();
        if (anEquipType != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anEquipType.getTypeName();
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
                        getEquipTypeService().delete(anEquipType);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlEquipType.setInitEdit();

        setSelectedEquipType(null);
        // refresh the list
        getEquipTypeListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getEquipTypeDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getEquipTypeDetailCtrl().list_status.getSelectedItem() != null) {
            if (getEquipTypeDetailCtrl().txtb_status.getValue() == getEquipTypeDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getEquipTypeDetailCtrl().getEquipType().setActiveSts(getEquipTypeDetailCtrl().txtb_status.getValue());
            } else {
                getEquipTypeDetailCtrl().txtb_status.setValue(getEquipTypeDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getEquipTypeDetailCtrl().getEquipType().setActiveSts(getEquipTypeDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getEquipTypeDetailCtrl().txtb_status.setValue(Codec.StatusAktif.Stat1.getValue());
            getEquipTypeDetailCtrl().getEquipType().setActiveSts(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getEquipTypeDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getEquipTypeService().saveOrUpdate(getEquipTypeDetailCtrl().getEquipType());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getEquipTypeListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getEquipTypeListCtrl().getListBoxEquipType(), getSelectedEquipType());

            // show the objects data in the statusBar
            String str = getSelectedEquipType().getTypeName();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlEquipType.setInitEdit();
            getEquipTypeDetailCtrl().doReadOnlyMode(true);
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
        if (getEquipTypeDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipTypeDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getEquipTypeDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabEquipTypeDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Mmedequiptype anEquipType = getEquipTypeService().getNew();

        // set the beans in the related databinded controllers
        getEquipTypeDetailCtrl().setEquipType(anEquipType);
        getEquipTypeDetailCtrl().setSelectedEquipType(anEquipType);

        // Refresh the binding mechanism
        getEquipTypeDetailCtrl().setSelectedEquipType(getSelectedEquipType());
        if (getEquipTypeDetailCtrl().getBinder() != null) {
            getEquipTypeDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getEquipTypeDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlEquipType.setInitNew();

        tabEquipTypeDetail.setSelected(true);
        // set focus
        //getEquipTypeDetailCtrl().txtb_ckdUniv.focus();

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

        if (tabbox_EquipTypeMain.getSelectedTab() == tabEquipTypeDetail) {
            getEquipTypeDetailCtrl().doFitSize(event);
        } else if (tabbox_EquipTypeMain.getSelectedTab() == tabEquipTypeList) {
            // resize and fill Listbox new
            getEquipTypeListCtrl().doFillListbox();
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

        if (getSelectedEquipType() != null) {

            try {
                setOriginalEquipType((Mmedequiptype) ZksampleBeanUtils.cloneBean(getSelectedEquipType()));
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

        if (getOriginalEquipType() != null) {

            try {
                setSelectedEquipType((Mmedequiptype) ZksampleBeanUtils.cloneBean(getOriginalEquipType()));
                // TODO Bug in DataBinder??
                windowEquipTypeMain.invalidate();

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

        // window_EquipTypeList.setVisible(workspace.isAllowed("window_EquipTypeList"));
        button_EquipTypeList_PrintList.setVisible(workspace.isAllowed("button_EquipTypeList_PrintList"));
        button_EquipTypeList_SearchCode.setVisible(workspace.isAllowed("button_EquipTypeList_SearchCode"));
        button_EquipTypeList_SearchName.setVisible(workspace.isAllowed("button_EquipTypeList_SearchName"));
        button_EquipTypeList_SearchAlamat.setVisible(workspace.isAllowed("button_EquipTypeList_SearchAlamat"));

        btnHelp.setVisible(workspace.isAllowed("button_EquipTypeMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_EquipTypeMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_EquipTypeMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_EquipTypeMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_EquipTypeMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalEquipType(Mmedequiptype originalEquipType) {
        this.originalEquipType = originalEquipType;
    }

    public Mmedequiptype getOriginalEquipType() {
        return this.originalEquipType;
    }

    public void setSelectedEquipType(Mmedequiptype selectedEquipType) {
        this.selectedEquipType = selectedEquipType;
    }

    public Mmedequiptype getSelectedEquipType() {
        return this.selectedEquipType;
    }

    public void setEquipTypes(BindingListModelList equipTypes) {
        this.equipTypes = equipTypes;
    }

    public BindingListModelList getEquipTypes() {
        return this.equipTypes;
    }

    public void setEquipTypeService(EquipTypeService equipTypeService) {
        this.equipTypeService = equipTypeService;
    }

    public EquipTypeService getEquipTypeService() {
        return this.equipTypeService;
    }

    public void setEquipTypeListCtrl(EquipTypeListCtrl equipTypeListCtrl) {
        this.equipTypeListCtrl = equipTypeListCtrl;
    }

    public EquipTypeListCtrl getEquipTypeListCtrl() {
        return this.equipTypeListCtrl;
    }

    public void setEquipTypeDetailCtrl(EquipTypeDetailCtrl equipTypeDetailCtrl) {
        this.equipTypeDetailCtrl = equipTypeDetailCtrl;
    }

    public EquipTypeDetailCtrl getEquipTypeDetailCtrl() {
        return this.equipTypeDetailCtrl;
    }
}
