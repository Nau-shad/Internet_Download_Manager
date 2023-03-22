package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.config.appConfig;
import org.example.modles.fileInfo;

import java.io.File;

public class DownloadManager {

    @FXML
    private TextField urlTextField;
    @FXML
    private TableView<fileInfo> tableView;
    public int index=0;
    @FXML
    void downloadButtonClicked(ActionEvent event) {
        String url = urlTextField.getText().trim();// for removing extra space.
        String fileName= url.substring(url.lastIndexOf("/")+1); //https://filmizilla/movie.mp4.
        String status = "STARTING";

        String action="Open File";
        String path= appConfig.DOWNLOAD_PATH+ File.pathSeparator+fileName; // watch it................
        fileInfo file = new fileInfo((index+1)+"",fileName,url,status,action,path);
        this.index=this.index+1;

      //  DownloadThread thread = new DownloadThread(file,this);
        DownloadThread thread = new DownloadThread(file,this);
        this.tableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);// sending data to table
        thread.start();

        this.urlTextField.setText("");

      //diff bw  run and start

    }

    public void updateUI(fileInfo file) {
        System.out.println(file);
       fileInfo fileinfo= this.tableView.getItems().get(Integer.parseInt(file.getIndex())-1);
       fileinfo.setStatus(file.getStatus());
       this.tableView.refresh();
        System.out.println("-----------------------------");
    }

    @FXML
    public void initialize(){
        System.out.println("view initialized");

        /* here we connect the fileInfo field with column in database */

       TableColumn<fileInfo,String> sn= (TableColumn<fileInfo, String>) this.tableView.getColumns().get(0);
       sn.setCellValueFactory(p -> {
           return p.getValue().indexProperty();
       });
        TableColumn<fileInfo,String> filename= (TableColumn<fileInfo, String>) this.tableView.getColumns().get(1);
        filename.setCellValueFactory(p -> {
            return p.getValue().nameProperty();
        });
        TableColumn<fileInfo,String> url= (TableColumn<fileInfo, String>) this.tableView.getColumns().get(2);
        url.setCellValueFactory(p -> {
            return p.getValue().urlProperty();
        });
        TableColumn<fileInfo,String> status= (TableColumn<fileInfo, String>) this.tableView.getColumns().get(3);
        status.setCellValueFactory(p -> {
            return p.getValue().statusProperty();
        });
        TableColumn<fileInfo,String> action= (TableColumn<fileInfo, String>) this.tableView.getColumns().get(4);
        action.setCellValueFactory(p -> {
            return p.getValue().actionProperty();
        });


    }

}
