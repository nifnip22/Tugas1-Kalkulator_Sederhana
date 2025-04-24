import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplikasiKalkulator extends JFrame {
    // Komponen UI
    private JTextField angkaField1;
    private JTextField angkaField2;
    private JButton tambahButton;
    private JButton kurangButton;
    private JButton kaliButton;
    private JButton bagiButton;
    private JTextField hasilField;

    public AplikasiKalkulator() {
        super("Kalkulator Sederhana - 2411098"); // Judul Frame

        // Komponen UI
        angkaField1 = new JTextField(10);
        angkaField2 = new JTextField(10);
        tambahButton = new JButton("+");
        kurangButton = new JButton("-");
        kaliButton = new JButton("*");
        bagiButton = new JButton("/");
        hasilField = new JTextField(10);
        hasilField.setEditable(false);

        // JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 4, 4));
        panel.add(new JLabel("Angka 1:"));
        panel.add(angkaField1);
        panel.add(new JLabel("Angka 2:"));
        panel.add(angkaField2);
        panel.add(new JLabel("Hasil:"));
        panel.add(hasilField);

        add(panel, BorderLayout.CENTER); // Tambahkan Panel ke Frame

        // Membuat panel untuk tombol operasi
        JPanel panelOperasi = new JPanel();
        panelOperasi.setLayout(new GridLayout(1, 4));
        panelOperasi.add(tambahButton);
        panelOperasi.add(kurangButton);
        panelOperasi.add(kaliButton);
        panelOperasi.add(bagiButton);

        add(panelOperasi, BorderLayout.SOUTH); // Menambahkan panel tombol ke frame

        ((AbstractDocument) angkaField1.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) angkaField2.getDocument()).setDocumentFilter(new NumericFilter());

        // Menentukan aksi untuk tombol-tombol
        tambahButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kalkulasi('+');
            }
        });
        kurangButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kalkulasi('-');
            }
        });
        kaliButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kalkulasi('*');
            }
        });
        bagiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kalkulasi('/');
            }
        });

        // Pengaturan frame
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Memposisikan frame di tengah layar
        setVisible(true);
    }

    // Metode untuk melakukan perhitungan
    private void kalkulasi(char operator) {
        double angka1 = Double.parseDouble(angkaField1.getText());
        double angka2 = Double.parseDouble(angkaField2.getText());
        double hasil = 0;

        switch (operator) {
            case '+':
                hasil = angka1 + angka2;
                break;
            case '-':
                hasil = angka1 - angka2;
                break;
            case '*':
                hasil = angka1 * angka2;
                break;
            case '/':
                if (angka2 != 0) {
                    hasil = angka1 / angka2;
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Pembagian dengan nol tidak diizinkan");
                }
                break;
        }

        hasilField.setText(String.valueOf(hasil)); // Tampilkan Hasil di Textfield hasilField
    }

    // Metode untuk mencegah TextField menginput nilai selain angka numerik dengan
    // DocumentFilter
    public class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = new StringBuilder(currentText).replace(offset, offset + length, text).toString();
            if (isValidInput(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidInput(String text) {
            return text.matches("^\\d*(\\.|,)?\\d*$");
        }
    }

    // Metode utama untuk menjalankan aplikasi
    public static void main(String[] args) {
        // Memulai aplikasi di EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AplikasiKalkulator();
            }
        });
    }
}