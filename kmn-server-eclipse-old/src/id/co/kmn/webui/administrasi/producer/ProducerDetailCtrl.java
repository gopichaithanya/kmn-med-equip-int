package id.co.kmn.webui.administrasi.producer;

import id.co.kmn.administrasi.service.ProducerService;
import id.co.kmn.backend.model.Mmedproducer;
import id.co.kmn.util.Codec;
import id.co.kmn.webui.util.GFCBaseCtrl;
import id.co.kmn.webui.util.GFCListModelCtrl;
import id.co.kmn.webui.util.ListBoxUtil;
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
 * @Date 5/1/12
 * Time: 12:17 PM
 */
public class ProducerDetailCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(ProducerDetailCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowProducerDetail; // autowired

    protected Borderlayout borderlayout_ProducerDetail; // autowired

    protected Textbox txtb_ckdUniv; // autowired
    protected Textbox txtb_cnamaUniv; // autowired
    protected Textbox txtb_alamatUniv; // autowired
    protected Textbox txtb_cstatus; // autowired
    protected Button button_ProducerDialog_PrintProducer; // autowired
    protected Listbox list_status;
    protected Bandbox cmb_status;

    // Databinding
    protected transient AnnotateDataBinder binder;
    private ProducerMainCtrl producerMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient ProducerService producerService;

    /**
     * default constructor.<br>
     */
    public ProducerDetailCtrl() {
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
            setProducerMainCtrl((ProducerMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getProducerMainCtrl().setProducerDetailCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getProducerMainCtrl().getSelectedProducer() != null) {
                setSelectedProducer(getProducerMainCtrl().getSelectedProducer());
            } else
                setSelectedProducer(null);
        } else {
            setSelectedProducer(null);
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
    public void onCreate$windowProducerDetail(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
        GFCListModelCtrl.getInstance().setListModel((new EnumConverter(Codec.StatusAktif.class)).getEnumToList(),
                list_status, cmb_status, (getProducer() != null)?getProducer().getActiveSts():null);
        binder.loadAll();

        doFitSize(event);
    }

    public void doRenderCombo(){
        ListBoxUtil.resetList(list_status);
          GFCListModelCtrl.getInstance().setListModel((new EnumConverter(Codec.StatusAktif.class)).getEnumToList(),
                list_status, cmb_status, (getProducer() != null)?getProducer().getActiveSts():null);
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
        borderlayout_ProducerDetail.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowProducerDetail.invalidate();
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
        txtb_ckdUniv.setReadonly(b);
        txtb_cnamaUniv.setReadonly(b);
        //txtb_alamatUniv.setReadonly(b);
        txtb_cstatus.setReadonly(b);
        list_status.setDisabled(b);
        cmb_status.setDisabled(b);
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
    public Mmedproducer getProducer() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getSelectedProducer();
    }

    public void setProducer(Mmedproducer anProducer) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setSelectedProducer(anProducer);
    }

    public Mmedproducer getSelectedProducer() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getSelectedProducer();
    }

    public void setSelectedProducer(Mmedproducer selectedProducer) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setSelectedProducer(selectedProducer);
    }

    public BindingListModelList getProducers() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getProducers();
    }

    public void setProducers(BindingListModelList producers) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setProducers(producers);
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public ProducerService getProducerService() {
        return this.producerService;
    }

    public void setProducerMainCtrl(ProducerMainCtrl producerMainCtrl) {
        this.producerMainCtrl = producerMainCtrl;
    }

    public ProducerMainCtrl getProducerMainCtrl() {
        return this.producerMainCtrl;
    }
}
