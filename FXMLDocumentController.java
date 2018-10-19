package tugas.pkg2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;

/**
 * Harry Senjaya Darmawan
 * 2017730067
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    Label karakter;

    @FXML
    Label karakterSpasi;

    @FXML
    Label kata;

    @FXML
    Label kalimat;

    @FXML
    TextArea text;

    String input = "";
    Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Save();
            }
        };

        timer = new Timer();
        long delay = 10000L;
        long period = 5000L;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    @FXML
    public void Close() {
        timer.cancel();
        System.exit(0);
    }

    @FXML
    public void Load() throws FileNotFoundException, IOException {
        FileReader fr = null;
        BufferedReader br = null;

        File file = new File("Hasil Save.txt");
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        String st;
        while ((st = br.readLine()) != null) {
            text.setText(text.getText() + st);
        }
    }

    @FXML
    public void Save() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File file = new File("Hasil Save.txt");
            file.createNewFile();

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(input);

            System.out.println("SAVED");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void Help() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Selamat Datang Di Aplikasi Text Milikku!\n\nAplikasi ini dapat menghitung:\n-Jumlah karakter\n-Jumlah karakter (tidak termasuk spasi, enter, tab)\n-Jumlah kata\n-Jumlah kalimat\n\nAplikasi ini dapat melakukan Save, Load, dan Close yang bisa dilihat pada menu File.\nAplikasi ini akan melakukan save otomatis setiap 5 detik sekali, dan hasilnya bisa dilihat di folder project aplikasi ini dengan nama 'Hasil Save.txt'.\n\nNama : Harry Senjaya Darmawan\nNPM   : 2017730067\n");

        alert.showAndWait();
    }

    @FXML
    public void Menghitung() {
        Thread threadInput = new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        input = text.getText();
                    }
                });

            }
        };
        threadInput.start();

        Thread threadKarakterSpasi = new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        karakterSpasi.setText(input.length() + "");
                    }
                });

            }
        };
        threadKarakterSpasi.start();

        Thread threadKarakter = new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        int jumlahKarakter = input.length();
                        int jumlahSpasi = 0;
                        for (int i = 0; i < input.length(); i++) {
                            if (input.charAt(i) == ' ' || input.charAt(i) == '\n' || input.charAt(i) == '\t') {
                                jumlahSpasi++;
                            }
                        }
                        jumlahKarakter -= jumlahSpasi;
                        karakter.setText(jumlahKarakter + "");
                    }
                });

            }
        };
        threadKarakter.start();

        Thread threadKata = new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        StringTokenizer jumlahKata = new StringTokenizer(input);
                        kata.setText(jumlahKata.countTokens() + "");
                    }
                });
            }
        };
        threadKata.start();

        Thread threadKalimat = new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        int jumlahKalimat = 0;

                        for (int i = 1; i < input.length(); i++) {
                            if (input.charAt(i) == '.' || input.charAt(i) == '?' || input.charAt(i) == '!') {
                                if (input.charAt(i - 1) == '.' || input.charAt(i - 1) == '?' || input.charAt(i - 1) == '!') {
                                    continue;
                                } else {
                                    jumlahKalimat++;
                                }
                            }

                        }
                        kalimat.setText(jumlahKalimat + "");
                    }
                });
            }
        };
        threadKalimat.start();

    }

}
