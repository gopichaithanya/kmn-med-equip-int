package id.co.kmn.webui.administrasi.transaction;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.TransactionService;
import id.co.kmn.backend.model.Tmedequipment;
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
 * @Date 8/1/12
 * Time: 1:16 AM
 */
public class TransactionMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TransactionMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowTransactionMain; // autowired

    // Tabs
    protected Tabbox tabbox_TransactionMain; // autowired
    protected Tab tabTransactionList; // autowired
    protected Tab tabTransactionDetail; // autowired
    protected Tabpanel tabPanelTransactionList; // autowired
    protected Tabpanel tabPanelTransactionDetail; // autowired

    // filter components
    protected Checkbox checkbox_TransactionList_ShowAll; // autowired
    protected Textbox txtb_Transaction_Code; // aurowired
    protected Button button_TransactionList_SearchCode; // aurowired
    protected Textbox txtb_Transaction_Name; // aurowired
    protected Button button_TransactionList_SearchName; // aurowired
    protected Textbox txtb_Transaction_Alamat; // aurowired
    protected Button button_TransactionList_SearchAlamat; // aurowired

    // checkRights
    protected Button button_TransactionList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_TransactionMain_";
    private ButtonStatusCtrl btnCtrlTransaction;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private TransactionListCtrl transactionListCtrl;
    private TransactionDetailCtrl transactionDetailCtrl;

    // Databinding
    private Tmedequipment selectedTransaction;
    private BindingListModelList transactions;

    // ServiceDAOs / Domain Classes
    private TransactionService transactionService;

    // always a copy from the bean before modifying. Used for reseting
    private Tmedequipment originalTransaction;

    /**
     * default constructor.<br>
     */
    public TransactionMainCtrl() {
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
    public void onCreate$windowTransactionMain(Event event) throws Exception {
        windowTransactionMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlTransaction = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabTransactionList.setSelected(true);

        if (tabPanelTransactionList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelTransactionList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/transaction/transactionList.zul");
        }

        // init the buttons for editMode
        btnCtrlTransaction.setInitEdit();
    }

    /**
     * When the tab 'tabTransactionList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabTransactionList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelTransactionList.getFirstChild() != null) {
            tabTransactionList.setSelected(true);

            return;
        }

        if (tabPanelTransactionList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelTransactionList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/transaction/transactionList.zul");
        }

    }

    /**
     * When the tab 'tabPanelTransactionDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabTransactionDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelTransactionDetail.getFirstChild() != null) {
            tabTransactionDetail.setSelected(true);

            // refresh the Binding mechanism
            getTransactionDetailCtrl().setTransaction(getSelectedTransaction());
            getTransactionDetailCtrl().getBinder().loadAll();
            //refresh combo
            getTransactionDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelTransactionDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelTransactionDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/transaction/transactionDetail.zul");
        }
    }

    /**
     * when the "print transactions list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_TransactionList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_TransactionList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_Transaction_Code.setValue(""); // clear
        txtb_Transaction_Name.setValue(""); // clear
        //txtb_Transaction_Alamat.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Tmedequipment> soTransaction = new HibernateSearchObject<Tmedequipment>(Tmedequipment.class, getTransactionListCtrl().getCountRows());
        soTransaction.addSort(ConstantUtil.LAST_NAME, false);

        // Change the BindingListModel.
        if (getTransactionListCtrl().getBinder() != null) {
            getTransactionListCtrl().getPagedBindingListWrapper().setSearchObject(soTransaction);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_TransactionMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabTransactionList)) {
                tabTransactionList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the transaction list with 'like transaction number'. <br>
     */
    public void onClick$button_TransactionList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Transaction_Code.getValue().isEmpty()) {
            checkbox_TransactionList_ShowAll.setChecked(false); // unCheck
            txtb_Transaction_Name.setValue(""); // clear
            //txtb_Transaction_Alamat.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Tmedequipment> soTransaction = new HibernateSearchObject<Tmedequipment>(Tmedequipment.class, getTransactionListCtrl().getCountRows());
            soTransaction.addFilter(new Filter(ConstantUtil.BRANCH_CODE, "%" + txtb_Transaction_Code.getValue() + "%", Filter.OP_ILIKE));
            soTransaction.addSort(ConstantUtil.BRANCH_CODE, false);

            // Change the BindingListModel.
            if (getTransactionListCtrl().getBinder() != null) {
                getTransactionListCtrl().getPagedBindingListWrapper().setSearchObject(soTransaction);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_TransactionMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabTransactionList)) {
                    tabTransactionList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the transaction list with 'like transaction name'. <br>
     */
    public void onClick$button_TransactionList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Transaction_Name.getValue().isEmpty()) {
            checkbox_TransactionList_ShowAll.setChecked(false); // unCheck
            //txtb_Transaction_Alamat.setValue(""); // clear
            txtb_Transaction_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Tmedequipment> soTransaction = new HibernateSearchObject<Tmedequipment>(Tmedequipment.class, getTransactionListCtrl().getCountRows());
            soTransaction.addFilter(new Filter(ConstantUtil.LAST_NAME, "%" + txtb_Transaction_Name.getValue() + "%", Filter.OP_ILIKE));
            soTransaction.addSort(ConstantUtil.LAST_NAME, false);

            // Change the BindingListModel.
            if (getTransactionListCtrl().getBinder() != null) {
                getTransactionListCtrl().getPagedBindingListWrapper().setSearchObject(soTransaction);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_TransactionMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabTransactionList)) {
                    tabTransactionList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the transaction list with 'like transaction city'. <br>
     */
    /*public void onClick$button_TransactionList_SearchAlamat(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Transaction_Alamat.getValue().isEmpty()) {
            checkbox_TransactionList_ShowAll.setChecked(false); // unCheck
            txtb_Transaction_Name.setValue(""); // clear
            txtb_Transaction_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Tmedequipment> soTransaction = new HibernateSearchObject<Tmedequipment>(Tmedequipment.class, getTransactionListCtrl().getCountRows());
            soTransaction.addFilter(new Filter("alamatUniv", "%" + txtb_Transaction_Alamat.getValue() + "%", Filter.OP_ILIKE));
            //soTransaction.addSort("alamatUniv", false);

            // Change the BindingListModel.
            if (getTransactionListCtrl().getBinder() != null) {
                getTransactionListCtrl().getPagedBindingListWrapper().setSearchObject(soTransaction);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_TransactionMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabTransactionList)) {
                    tabTransactionList.setSelected(true);
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
        if (getTransactionDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getTransactionDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getTransactionDetailCtrl().doReadOnlyMode(true);

            btnCtrlTransaction.setInitEdit();
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
        Tab currentTab = tabbox_TransactionMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getTransactionDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabTransactionDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getTransactionDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabTransactionDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabTransactionDetail)) {
            tabTransactionDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getTransactionDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlTransaction.setBtnStatus_Edit();

        getTransactionDetailCtrl().doReadOnlyMode(false);
        // set focus
        getTransactionDetailCtrl().txtb_code.focus();
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
        if (getTransactionDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabTransactionDetail, null));
        }

        // check first, if the tabs are created
        if (getTransactionDetailCtrl().getBinder() == null) {
            return;
        }

        final Tmedequipment anTransaction = getSelectedTransaction();
        if (anTransaction != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anTransaction.getLastName();
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
                        getTransactionService().delete(anTransaction);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlTransaction.setInitEdit();

        setSelectedTransaction(null);
        // refresh the list
        getTransactionListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getTransactionDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    protected void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getTransactionDetailCtrl().list_status.getSelectedItem() != null) {
            if (getTransactionDetailCtrl().txtb_status.getValue() == getTransactionDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getTransactionDetailCtrl().getTransaction().setCisStatus(getTransactionDetailCtrl().txtb_status.getValue());
            } else {
                getTransactionDetailCtrl().txtb_status.setValue(getTransactionDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getTransactionDetailCtrl().getTransaction().setCisStatus(getTransactionDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getTransactionDetailCtrl().txtb_status.setValue(Codec.StatusAktif.Stat1.getValue());
            getTransactionDetailCtrl().getTransaction().setCisStatus(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getTransactionDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getTransactionService().saveOrUpdate(getTransactionDetailCtrl().getTransaction());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getTransactionListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getTransactionListCtrl().getListBoxTransaction(), getSelectedTransaction());

            // show the objects data in the statusBar
            String str = getSelectedTransaction().getLastName();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlTransaction.setInitEdit();
            getTransactionDetailCtrl().doReadOnlyMode(true);
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
        if (getTransactionDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabTransactionDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getTransactionDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabTransactionDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Tmedequipment anTransaction = getTransactionService().getNew();

        // set the beans in the related databinded controllers
        getTransactionDetailCtrl().setTransaction(anTransaction);
        getTransactionDetailCtrl().setSelectedTransaction(anTransaction);

        // Refresh the binding mechanism
        getTransactionDetailCtrl().setSelectedTransaction(getSelectedTransaction());
        if (getTransactionDetailCtrl().getBinder() != null) {
            getTransactionDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getTransactionDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlTransaction.setInitNew();

        tabTransactionDetail.setSelected(true);
        // set focus
        //getTransactionDetailCtrl().txtb_ckdUniv.focus();

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

        if (tabbox_TransactionMain.getSelectedTab() == tabTransactionDetail) {
            getTransactionDetailCtrl().doFitSize(event);
        } else if (tabbox_TransactionMain.getSelectedTab() == tabTransactionList) {
            // resize and fill Listbox new
            getTransactionListCtrl().doFillListbox();
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

        if (getSelectedTransaction() != null) {

            try {
                setOriginalTransaction((Tmedequipment) ZksampleBeanUtils.cloneBean(getSelectedTransaction()));
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

        if (getOriginalTransaction() != null) {

            try {
                setSelectedTransaction((Tmedequipment) ZksampleBeanUtils.cloneBean(getOriginalTransaction()));
                // TODO Bug in DataBinder??
                windowTransactionMain.invalidate();

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

        // window_TransactionList.setVisible(workspace.isAllowed("window_TransactionList"));
        button_TransactionList_PrintList.setVisible(workspace.isAllowed("button_TransactionList_PrintList"));
        button_TransactionList_SearchCode.setVisible(workspace.isAllowed("button_TransactionList_SearchCode"));
        button_TransactionList_SearchName.setVisible(workspace.isAllowed("button_TransactionList_SearchName"));
        button_TransactionList_SearchAlamat.setVisible(workspace.isAllowed("button_TransactionList_SearchAlamat"));

        btnHelp.setVisible(workspace.isAllowed("button_TransactionMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_TransactionMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_TransactionMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_TransactionMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_TransactionMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalTransaction(Tmedequipment originalTransaction) {
        this.originalTransaction = originalTransaction;
    }

    public Tmedequipment getOriginalTransaction() {
        return this.originalTransaction;
    }

    public void setSelectedTransaction(Tmedequipment selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }

    public Tmedequipment getSelectedTransaction() {
        return this.selectedTransaction;
    }

    public void setTransactions(BindingListModelList transactions) {
        this.transactions = transactions;
    }

    public BindingListModelList getTransactions() {
        return this.transactions;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionService getTransactionService() {
        return this.transactionService;
    }

    public void setTransactionListCtrl(TransactionListCtrl transactionListCtrl) {
        this.transactionListCtrl = transactionListCtrl;
    }

    public TransactionListCtrl getTransactionListCtrl() {
        return this.transactionListCtrl;
    }

    public void setTransactionDetailCtrl(TransactionDetailCtrl transactionDetailCtrl) {
        this.transactionDetailCtrl = transactionDetailCtrl;
    }

    public TransactionDetailCtrl getTransactionDetailCtrl() {
        return this.transactionDetailCtrl;
    }
}
