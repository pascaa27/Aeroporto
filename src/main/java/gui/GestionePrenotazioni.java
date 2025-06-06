package gui;

import javax.swing.*;
import controller.Controller;
import model.DatiPasseggero;
import model.StatoPrenotazione;

public class GestionePrenotazioni {
    private JPanel panelPrenotazione;
    private JTextField numeroBigliettoTextField;
    private JTextField postoTextField;
    private JComboBox<StatoPrenotazione> statoPrenotazioneComboBox;
    private JButton aggiungiPrenotazioneButton;
    private Controller controller;
    private DatiPasseggero datiPasseggero = null;

    private void aggiungiPrenotazione() {
        String numeroBiglietto = numeroBigliettoTextField.getText();
        String posto = postoTextField.getText();
        StatoPrenotazione stato = (StatoPrenotazione) statoPrenotazioneComboBox.getSelectedItem();

        controller.aggiungiPrenotazione(numeroBiglietto, posto, stato, null, null, null, null, null);
        JOptionPane.showMessageDialog(null, "Prenotazione aggiunta con successo!");

        numeroBigliettoTextField.setText("");
        postoTextField.setText("");
        statoPrenotazioneComboBox.setSelectedIndex(0);
    }

    public GestionePrenotazioni(Controller controller) {
        this.controller = controller;

        for(StatoPrenotazione stato : StatoPrenotazione.values()) {
            statoPrenotazioneComboBox.addItem(stato);
        }

        aggiungiPrenotazioneButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Per effettuare la prenotazione, inserire i seguenti dati:");
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panelPrenotazione), "Inserisci dati passeggero", true);
            DatiPasseggeroGUI datiGUI = new DatiPasseggeroGUI(dialog);
            dialog.setContentPane(datiGUI.getPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            String nome = datiGUI.getNomeInserito();
            String cognome = datiGUI.getCognomeInserito();
            String codiceFiscale = datiGUI.getCodiceFiscaleInserito();

            if(nome == null || cognome == null || codiceFiscale == null || nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Dati passeggero non validi. Prenotazione annullata.");
                return;
            }

            String numeroBiglietto = numeroBigliettoTextField.getText();
            String posto = postoTextField.getText();
            StatoPrenotazione stato = (StatoPrenotazione) statoPrenotazioneComboBox.getSelectedItem();

            controller.aggiungiPrenotazione(numeroBiglietto, posto, stato, null, nome, cognome, codiceFiscale, null);
            JOptionPane.showMessageDialog(null, "Prenotazione completata con successo!");

            numeroBigliettoTextField.setText("");
            postoTextField.setText("");
            statoPrenotazioneComboBox.setSelectedIndex(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            JFrame frame = new JFrame("Prenotazione");
            frame.setContentPane(new GestionePrenotazioni(controller).panelPrenotazione);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}