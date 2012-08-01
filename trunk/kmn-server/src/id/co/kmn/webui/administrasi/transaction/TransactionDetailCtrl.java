package id.co.kmn.webui.administrasi.transaction;

import id.co.kmn.administrasi.service.TransactionService;
import id.co.kmn.backend.model.*;
import id.co.kmn.util.Codec;
import id.co.kmn.webui.util.GFCBaseCtrl;
import id.co.kmn.webui.util.GFCListModelCtrl;
import id.co.kmn.webui.util.ListBoxUtil;
import id.co.kmn.webui.util.searchdialogs.BranchExtendedSearchListBox;
import id.co.kmn.webui.util.searchdialogs.ProducerLookup;
import id.co.kmn.webui.util.searchdialogs.RoomLookup;
import id.co.kmn.webui.util.searchdialogs.TypeLookup;
import id.co.kmn.webui.util.test.EnumConverter;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.io.Serializable;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 8/1/12
 * Time: 1:15 AM
 */
public class TransactionDetailCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(TransactionDetailCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowTransactionDetail; // autowired

    protected Borderlayout borderlayout_TransactionDetail; // autowired

    protected Textbox txtb_code; // autowired
    protected Textbox txtb_name; // autowired
    protected Textbox txtb_brm; // autowired
    protected Textbox txtb_room; // autowired
    protected Textbox txtb_producer; // autowired
    protected Textbox txtb_type; // autowired
    protected Textbox txtb_status; // autowired
    protected Button button_TransactionDialog_PrintTransaction; // autowired
    protected Listbox list_status;
    protected Bandbox cmb_status;
    protected Button btnSearchBranch;
    protected Button btnSearchRoom;
    protected Button btnSearchProducer;
    protected Button btnSearchType;

    // Databinding
    protected transient AnnotateDataBinder binder;
    private TransactionMainCtrl transactionMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient TransactionService transactionService;

    /**
     * default constructor.<br>
     */
    public TransactionDetailCtrl() {
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
            getTransactionMainCtrl().setTransactionDetailCtrl(this);

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
    public void onCreate$windowTransactionDetail(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        GFCListModelCtrl.getInstance().setListModel((new EnumConverter(Codec.StatusAktif.class)).getEnumToList(),
                list_status, cmb_status, (getTransaction() != null)?getTransaction().getCisStatus():null);
        binder.loadAll();

        doFitSize(event);
    }

    public void doRenderCombo(){
        ListBoxUtil.resetList(list_status);
          GFCListModelCtrl.getInstance().setListModel((new EnumConverter(Codec.StatusAktif.class)).getEnumToList(),
                list_status, cmb_status, (getTransaction() != null)?getTransaction().getCisStatus():null);
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
    public void doFitSize(Event event) {
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - 148;
        borderlayout_TransactionDetail.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowTransactionDetail.invalidate();
    }

    /**
     * Set all components in readOnly/disabled modus.
     * <p/>
     * true = all components are readOnly or disabled.<br>
     * false = all components are accessable.<br>
     *
     * @param b
     */
    public void doReadOnlyMode(boolean b) {
        txtb_code.setReadonly(b);
        txtb_name.setReadonly(b);
        txtb_status.setReadonly(b);
        list_status.setDisabled(b);
        cmb_status.setDisabled(b);
        btnSearchBranch.setDisabled(b);
        btnSearchRoom.setDisabled(b);
        btnSearchProducer.setDisabled(b);
        btnSearchType.setDisabled(b);
    }
    /**
     * If the Button 'ExtendedSearch' is clicked.<br>
     *
     * @param event
     */
    public void onClick$btnSearchBranch(Event event) {
        doSearchBranch(event);
    }
    /**
     * Opens the Search and Get Dialog.<br>
     * It appends/changes object for the current bean.<br>
     *
     * @param event
     */
    private void doSearchBranch(Event event) {
        Branche obj =  BranchExtendedSearchListBox.show(windowTransactionDetail);

        if (obj != null) {
            txtb_brm.setValue(String.valueOf(obj.getId()));
            Tmedequipment transaction = getTransaction();
            transaction.setBranchCode(String.valueOf(obj.getId()));
            setTransaction(transaction);
        }
    }
    /**
     * If the Button 'ExtendedSearch' is clicked.<br>
     *
     * @param event
     */
    public void onClick$btnSearchRoom(Event event) {
        doSearchRoom(event);
    }
    /**
     * Opens the Search and Get Dialog.<br>
     * It appends/changes object for the current bean.<br>
     *
     * @param event
     */
    private void doSearchRoom(Event event) {
        Mmedroom obj =  RoomLookup.show(windowTransactionDetail);

//        if (obj != null) {
//            txtb_room.setValue(obj.getRoomName());
//            Tmedequipment transaction = getTransaction();
//            transaction.setMmedroom(obj);
//            setTransaction(transaction);
//        }
    }

    /**
     * If the Button 'ExtendedSearch' is clicked.<br>
     *
     * @param event
     */
    public void onClick$btnSearchProducer(Event event) {
        doSearchProducer(event);
    }
    /**
     * Opens the Search and Get Dialog.<br>
     * It appends/changes object for the current bean.<br>
     *
     * @param event
     */
    private void doSearchProducer(Event event) {
        Mmedproducer obj =  ProducerLookup.show(windowTransactionDetail);

//        if (obj != null) {
//            txtb_producer.setValue(obj.getProdName());
//            Tmedequipment transaction = getTransaction();
//            transaction.setMmedproducer(obj);
//            setTransaction(transaction);
//        }
    }

    /**
     * If the Button 'ExtendedSearch' is clicked.<br>
     *
     * @param event
     */
    public void onClick$btnSearchType(Event event) {
        doSearchType(event);
    }
    /**
     * Opens the Search and Get Dialog.<br>
     * It appends/changes object for the current bean.<br>
     *
     * @param event
     */
    private void doSearchType(Event event) {
        Mmedequiptype obj =  TypeLookup.show(windowTransactionDetail);

//        if (obj != null) {
//            txtb_type.setValue(obj.getTypeName());
//            Tmedequipment transaction = getTransaction();
//            transaction.setMmedequiptype(obj);
//            setTransaction(transaction);
//        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

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

    public Tmedequipment getSelectedTransaction() {
        // STORED IN THE module's MainController
        return getTransactionMainCtrl().getSelectedTransaction();
    }

    public void setSelectedTransaction(Tmedequipment selectedTransaction) {
        // STORED IN THE module's MainController
        getTransactionMainCtrl().setSelectedTransaction(selectedTransaction);
    }

    public BindingListModelList getTransactions() {
        // STORED IN THE module's MainController
        return getTransactionMainCtrl().getTransactions();
    }

    public void setTransactions(BindingListModelList transactions) {
        // STORED IN THE module's MainController
        getTransactionMainCtrl().setTransactions(transactions);
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionService getTransactionService() {
        return this.transactionService;
    }

    public void setTransactionMainCtrl(TransactionMainCtrl transactionMainCtrl) {
        this.transactionMainCtrl = transactionMainCtrl;
    }

    public TransactionMainCtrl getTransactionMainCtrl() {
        return this.transactionMainCtrl;
    }
}
