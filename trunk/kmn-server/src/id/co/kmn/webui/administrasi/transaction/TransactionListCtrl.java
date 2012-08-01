package id.co.kmn.webui.administrasi.transaction;

import id.co.kmn.administrasi.service.TransactionService;
import id.co.kmn.backend.model.Tmedequipment;
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
 * @Date 8/1/12
 * Time: 1:15 AM
 */
public class TransactionListCtrl extends GFCBaseListCtrl<Tmedequipment> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(TransactionListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowTransactionList; // autowired
    protected Panel panelTransactionList; // autowired

    protected Borderlayout borderLayout_transactionList; // autowired
    protected Paging paging_TransactionList; // autowired
    protected Listbox listBoxTransaction; // autowired
    protected Listheader listheader_TransactionList_Code; // autowired
    protected Listheader listheader_TransactionList_Name; // autowired
    protected Listheader listheader_TransactionList_Producer; // autowired
    protected Listheader listheader_TransactionList_Type; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Tmedequipment> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private TransactionMainCtrl transactionMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient TransactionService transactionService;

    /**
     * default constructor.<br>
     */
    public TransactionListCtrl() {
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
            setTransactionMainCtrl((TransactionMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getTransactionMainCtrl().setTransactionListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getTransactionMainCtrl().getSelectedTransaction() != null) {
                setSelectedTransaction(getTransactionMainCtrl().getSelectedTransaction());
            } else
                setSelectedTransaction(null);
        } else {
            setSelectedTransaction(null);
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

    public void onCreate$windowTransactionList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_TransactionList.setPageSize(getCountRows());
        paging_TransactionList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_TransactionList_Code.setSortAscending(new FieldComparator(ConstantUtil.BRANCH_CODE, true));
        listheader_TransactionList_Code.setSortDescending(new FieldComparator(ConstantUtil.BRANCH_CODE, false));
        listheader_TransactionList_Name.setSortAscending(new FieldComparator(ConstantUtil.LAST_NAME, true));
        listheader_TransactionList_Name.setSortDescending(new FieldComparator(ConstantUtil.LAST_NAME, false));
        listheader_TransactionList_Producer.setSortAscending(new FieldComparator("mmedequipment.equipCode", true));
        listheader_TransactionList_Producer.setSortDescending(new FieldComparator("mmedequipment.equipCode", false));
        listheader_TransactionList_Type.setSortAscending(new FieldComparator("mmedequipment.equipName", true));
        listheader_TransactionList_Type.setSortDescending(new FieldComparator("mmedequipment.equipName", false));

        // ++ create the searchObject and init sorting ++//
        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Tmedequipment>(Tmedequipment.class, getCountRows());
        searchObj.addSort(ConstantUtil.BRANCH_CODE, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxTransaction(), paging_TransactionList);
        BindingListModelList lml = (BindingListModelList) getListBoxTransaction().getModel();
        setTransactions(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedTransaction() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxTransaction().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedTransaction((Tmedequipment) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxTransaction(), getSelectedTransaction()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedTransactionItem(Event event) {
        // logger.debug(event.toString());

        Tmedequipment anTransaction = getSelectedTransaction();

        if (anTransaction != null) {
            setSelectedTransaction(anTransaction);
            setTransaction(anTransaction);

            // check first, if the tabs are created
            if (getTransactionMainCtrl().getTransactionDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getTransactionMainCtrl().tabTransactionDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getTransactionMainCtrl().getTransactionDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getTransactionMainCtrl().tabTransactionDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getTransactionMainCtrl().tabTransactionDetail, anTransaction));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxTransaction(Event event) {
        // logger.debug(event.toString());

        // selectedTransaction is filled by annotated databinding mechanism
        Tmedequipment anTransaction = getSelectedTransaction();

        if (anTransaction == null) {
            return;
        }

        // check first, if the tabs are created
        if (getTransactionMainCtrl().getTransactionDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getTransactionMainCtrl().tabTransactionDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getTransactionMainCtrl().getTransactionDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getTransactionMainCtrl().tabTransactionDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getTransactionMainCtrl().getTransactionDetailCtrl().setSelectedTransaction(anTransaction);
        getTransactionMainCtrl().getTransactionDetailCtrl().setTransaction(anTransaction);

        // store the selected bean values as current
        getTransactionMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.Transaction") + ": " + anTransaction.getBranchCode();
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
        borderLayout_transactionList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowTransactionList.invalidate();
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
    public Tmedequipment getTransaction() {
        // STORED IN THE module's MainController
        return getTransactionMainCtrl().getSelectedTransaction();
    }

    public void setTransaction(Tmedequipment anTransaction) {
        // STORED IN THE module's MainController
        getTransactionMainCtrl().setSelectedTransaction(anTransaction);
    }

    public void setSelectedTransaction(Tmedequipment selectedTransaction) {
        // STORED IN THE module's MainController
        getTransactionMainCtrl().setSelectedTransaction(selectedTransaction);
    }

    public Tmedequipment getSelectedTransaction() {
        // STORED IN THE module's MainController
        return getTransactionMainCtrl().getSelectedTransaction();
    }

    public void setTransactions(BindingListModelList transactions) {
        // STORED IN THE module's MainController
        getTransactionMainCtrl().setTransactions(transactions);
    }

    public BindingListModelList getTransactions() {
        // STORED IN THE module's MainController
        return getTransactionMainCtrl().getTransactions();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Tmedequipment> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Tmedequipment> getSearchObj() {
        return this.searchObj;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionService getTransactionService() {
        return this.transactionService;
    }

    public Listbox getListBoxTransaction() {
        return this.listBoxTransaction;
    }

    public void setListBoxTransaction(Listbox listBoxTransaction) {
        this.listBoxTransaction = listBoxTransaction;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setTransactionMainCtrl(TransactionMainCtrl transactionMainCtrl) {
        this.transactionMainCtrl = transactionMainCtrl;
    }

    public TransactionMainCtrl getTransactionMainCtrl() {
        return this.transactionMainCtrl;
    }
}
