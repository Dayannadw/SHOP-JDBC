package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import main.Shop;
import model.Product;



public class ProductView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JLabel productLabel;
    private JLabel stockLabel;
    private JLabel priceLabel;
    private JTextField productTextField;
    private JTextField stockTextField;
    private JTextField priceTextField;
    private Shop shop;
    private int option = 0;

    public ProductView(int option, Shop shop) {
        this.option = option;
        this.shop = shop;

        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Inicialización de los componentes
        productLabel = new JLabel("Nombre:");
        productLabel.setBounds(92, 79, 139, 13);
        contentPanel.add(productLabel);

        productTextField = new JTextField();
        productTextField.setBounds(202, 78, 96, 19);
        contentPanel.add(productTextField);
        productTextField.setColumns(10);

        stockLabel = new JLabel("Stock:");
        stockLabel.setBounds(92, 102, 141, 13);
        contentPanel.add(stockLabel);

        stockTextField = new JTextField();
        stockTextField.setBounds(202, 101, 96, 19);
        contentPanel.add(stockTextField);
        stockTextField.setColumns(10);

        priceLabel = new JLabel("Precio:");
        priceLabel.setBounds(92, 125, 134, 13);
        contentPanel.add(priceLabel);

        priceTextField = new JTextField();
        priceTextField.setBounds(202, 124, 96, 19);
        contentPanel.add(priceTextField);
        priceTextField.setColumns(10);


        selectOption(option);


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            okButton = new JButton("OK");
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
            okButton.addActionListener(this);
        }
        {
            cancelButton = new JButton("Cancel");
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
            cancelButton.addActionListener(this);
        }
    }


    public void selectOption(int option) {
        if (option == 2) {

        } else if (option == 3) {

            priceLabel.setVisible(false);
            priceTextField.setVisible(false);
        } else if (option == 9) {

            priceLabel.setVisible(false);
            priceTextField.setVisible(false);
            stockLabel.setVisible(false);
            stockTextField.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent interactionButtons) {
        if (interactionButtons.getSource() == cancelButton) {
            dispose();
        } else if (interactionButtons.getSource() == okButton && option == 2) {
            addProduct();
            dispose();
        } else if (interactionButtons.getSource() == okButton && option == 3) {
            addStock();
            dispose();
        } else if (interactionButtons.getSource() == okButton && option == 9) {
            deleteProduct();
            dispose();
        }
    }



    //ADD PRODUCT
    public void addProduct() {
        try {
            String productName = productTextField.getText();
            int stock = Integer.parseInt(stockTextField.getText());
            double wholesalerPrice = Double.parseDouble(priceTextField.getText());
            double publicPrice = wholesalerPrice * 2;

            Product product = shop.findProduct(productName);
            if (product != null) {
                JOptionPane.showMessageDialog(this, "Producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                shop.addProduct(new Product(productName, wholesalerPrice, publicPrice, true, stock));
                shop.showInventory();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en los campos de texto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //ADD STOCK
    public void addStock() {
        try {
            String productName = productTextField.getText();
            int stock = Integer.parseInt(stockTextField.getText());
            Product product = shop.findProduct(productName);

            if (product != null) {
                product.setStock(stock + product.getStock());
                shop.updateProduct(product);
                shop.showInventory();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //DELETE
    public void deleteProduct() {
        try {
            String productName = productTextField.getText();

            // Buscar el producto en la lista en memoria
            Product product = shop.findProduct(productName);

            if (product != null) {
                // Eliminar el producto
                shop.deleteProduct(product);
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
