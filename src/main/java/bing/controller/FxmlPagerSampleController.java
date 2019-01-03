package bing.controller;

import bing.common.Page;
import bing.model.Billionaire;
import bing.service.BillionaireService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller
 *
 * @author: IceWee
 * @date: 2019.1.2
 */
public class FxmlPagerSampleController implements Initializable {

    private static final Logger logger = Logger.getLogger(FxmlPagerSampleController.class.getName());

    @FXML
    private Pagination pager;
    @FXML
    private VBox tableBox;
    @FXML
    private TableView<Billionaire> table;
    @FXML
    private TableColumn<Billionaire, String> rank;
    @FXML
    private TableColumn<Billionaire, String> name;
    @FXML
    private TableColumn<Billionaire, String> worth;
    @FXML
    private TableColumn<Billionaire, Integer> age;
    @FXML
    private TableColumn<Billionaire, String> source;
    @FXML
    private TableColumn<Billionaire, String> country;
    @FXML
    private TextField searchName;
    @FXML
    private Button searchBtn;
    @FXML
    private ContextMenu itemContextMenu;
    @FXML
    private MenuItem removeItem;

    private boolean init = true;
    private Page<Billionaire> page = new Page<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize ui...");
        configureTable();
        configurePager();
        Platform.runLater(() -> searchBtn.requestFocus());
        itemContextMenu.setOnShowing(event -> {
            Billionaire billionaire = table.getSelectionModel().getSelectedItem();
            boolean readonly = BillionaireService.instance().readonly(billionaire);
            if (readonly) {
                removeItem.setDisable(true);
            } else {
                removeItem.setDisable(false);
            }
        });
    }

    /**
     * search
     */
    public void search() {
        init = false;
        page.setPageNo(1);
        pager.setPageCount(1);
        pager.setCurrentPageIndex(0);
        String name = this.searchName.getText();
        BillionaireService.instance().search(name, page);
        table.setItems(FXCollections.observableArrayList(page.getData()));
        pager.setPageCount(page.getTotalPages());
    }

    /**
     * remove
     */
    public void remove() {
        Billionaire billionaire = table.getSelectionModel().getSelectedItem();
        logger.info("remove: " + billionaire.toString());
        BillionaireService.instance().remove(billionaire);
        search();
    }

    /**
     * Bind data
     */
    private void configureTable() {
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        worth.setCellValueFactory(new PropertyValueFactory<>("worth"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /**
     * set paging event
     */
    private void configurePager() {
        pager.setPageCount(1);
        pager.setCurrentPageIndex(0);
        pager.setPageFactory(pageIndex -> createPage(pageIndex));
    }

    /**
     * refresh show page
     *
     * @param pageIndex
     * @return
     */
    private VBox createPage(int pageIndex) {
        page.setPageNo(pageIndex + 1);
        if (!init) {
            String name = this.searchName.getText();
            BillionaireService.instance().search(name, page);
            table.getItems().clear();
            table.getItems().addAll(page.getData());
        }
        return tableBox;
    }

}
