package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ShopView extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton showCashButton;
    private JButton addProductButton;
    private JButton addStockButton;
    private JButton deleteProductButton;
    private Shop shop;
    private JTextField txtGeneratingPixels;
    private JButton exportInventoryButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShopView frame = new ShopView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShopView() {
        this.shop = new Shop();
        
        shop.loadInventory();
       
        shop.showInventory();

        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 200, 450, 300);
        
       
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
      
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); 

       
        JLabel optionLabel = new JLabel("Select an option:");
        optionLabel.setAlignmentX(CENTER_ALIGNMENT); 
        contentPane.add(optionLabel);

        
        exportInventoryButton = new JButton("0. Export Inventory");
        exportInventoryButton.setAlignmentX(CENTER_ALIGNMENT); 
        contentPane.add(exportInventoryButton);

        
        showCashButton = new JButton("1. Contar Caja");
        showCashButton.setAlignmentX(CENTER_ALIGNMENT); 
        contentPane.add(showCashButton);

        
        addProductButton = new JButton("2. Añadir Product");
        addProductButton.setAlignmentX(CENTER_ALIGNMENT); 
        contentPane.add(addProductButton);

       
        addStockButton = new JButton("3. Añadir Stock");
        addStockButton.setAlignmentX(CENTER_ALIGNMENT); 
        contentPane.add(addStockButton);

        
        deleteProductButton = new JButton("9. Eliminar producto");
        deleteProductButton.setAlignmentX(CENTER_ALIGNMENT);
        contentPane.add(deleteProductButton);

       
        
        exportInventoryButton.addActionListener(this);
        showCashButton.addActionListener(this);
        addProductButton.addActionListener(this);
        addStockButton.addActionListener(this);
        deleteProductButton.addActionListener(this);

        this.addKeyListener(this); 
    }

    // Method to export the inventory
    public void exportInventory() {
        if (!shop.writeInventory()) {
            JOptionPane.showMessageDialog(this, "Error exportando.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Inventorio exportado ", " ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    public void openCashView() {
        CashView cashView = new CashView(shop);
        cashView.setVisible(true);
    }

    
    public void openProductView(int option) {
        ProductView productView = new ProductView(option, shop);
        productView.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action on keyTyped
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

       
        switch (key) {
            case KeyEvent.VK_0:
                exportInventory();
                break;
            case KeyEvent.VK_1:
                openCashView();
                break;
            case KeyEvent.VK_2:
                openProductView(2);
                break;
            case KeyEvent.VK_3:
                openProductView(3);
                break;
            case KeyEvent.VK_9:
                openProductView(9);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action on keyReleased
    }

    @Override
    public void actionPerformed(ActionEvent interactionButtons) {
       
        if (interactionButtons.getSource() == showCashButton) {
            openCashView();
        } else if (interactionButtons.getSource() == addProductButton) {
            openProductView(2);
        } else if (interactionButtons.getSource() == addStockButton) {
            openProductView(3);
        } else if (interactionButtons.getSource() == deleteProductButton) {
            openProductView(9);
        } else if (interactionButtons.getSource() == exportInventoryButton) {
            exportInventory();
        }
    }
}
