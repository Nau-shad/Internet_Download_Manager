package org.example;

import org.example.modles.fileInfo;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadThread extends Thread{
    //using thread for download many files at same time.
    private fileInfo file;

    DownloadManager downloadManager;

    public DownloadThread(fileInfo file, DownloadManager downloadManager) {
        this.file = file;
        this.downloadManager = downloadManager;
    }

   @Override
public void run() {

        //logic for download
        this.file.setStatus("Downloading Started");
        this.downloadManager.updateUI(this.file);

        try {
            Files.copy(new URL(this.file.getUrl()).openStream(),
                    Paths.get(this.file.getPath()));
            // read all functions
            this.file.setStatus("Downloading Done");
        } catch (IOException e) {
            this.file.setStatus("FAILED");
            // throw new RuntimeException(e);
            System.out.println("Downloading error");
            e.printStackTrace(); // what is the use of this fn

        }
        this.downloadManager.updateUI(this.file);
    }
}
