import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



public class Inventory {
	
	private DefaultTableModel tableModel;
	
    public JPanel homePage() {
    	
    	
    	String[][]items = FileHandler.InventoryFromFile("files/inventorydatabase.txt");
        String[] itemTraits = { "Item Number", "Item Name", "Quantity", "Description", "Next Shipment", "Edit Information" };
        
        tableModel = new DefaultTableModel(items, itemTraits);       
        
        JTable table = new JTable(tableModel) {
        	public boolean isCellEditable(int row, int column) {
                return column == 5;   
            }
        };
        
     
        table.setPreferredSize(new Dimension(1280, 720));
        
        ButtonRenderer buttonRenderer = new ButtonRenderer();
    	ButtonEditor buttonEditor = new ButtonEditor(new JTextField(), table);
        
        table.getColumnModel().getColumn(5).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(buttonEditor);
        
        
        JPanel cardPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();
        
    	ImageIcon icon = new ImageIcon("Art/profile.png");
    	ImageIcon addIcon = new ImageIcon("art/plus.png");
    	ImageIcon searchIcon = new ImageIcon("art/search.png");
    	ImageIcon homeIcon = new ImageIcon("art/icons8-home-40.png");
    	
    	
    	JLabel iconHolder = new JLabel(icon);
    	JLabel addIconHolder = new JLabel(addIcon);
    	JLabel searchHolder = new JLabel(searchIcon);
    	JLabel homeHolder = new JLabel(homeIcon);
    	
    	JLabel name = new JLabel("Enter Item");
    	infoPanel.add(name);
 
        JToolBar toolBar = AddToUI.createToolBar(); 
  
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get information from the selected row and update the infoPanel
                        String itemName = table.getValueAt(selectedRow, 1).toString();
                        name.setText(itemName);
                        // Add more labels or components to display other information as needed
                    }
                }
            }
        });
        
        
        
        JPopupMenu popupMenu = AddToUI.popMenu();
        JMenuItem logout = new JMenuItem("Logout");
        JMenuItem AddUser = new JMenuItem("Add User");

        
        popupMenu.add(logout);
        popupMenu.add(AddUser);
        toolBar.add(homeHolder);
        toolBar.add(searchHolder);
        toolBar.add(Box.createHorizontalStrut(1105));
        toolBar.add(addIconHolder);
        toolBar.add(iconHolder);
        cardPanel.add(toolBar, BorderLayout.NORTH);
        

        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,640));
   
        
        cardPanel.add(scrollPane, BorderLayout.WEST);
        cardPanel.add(infoPanel, BorderLayout.EAST);
 
        
          
        
        iconHolder.addMouseListener(new MouseAdapter() {	
        	public void mouseClicked(MouseEvent e) {
        		
        		popupMenu.show(iconHolder, 0, iconHolder.getHeight());
        		
        	}
        }); 
        
        addIconHolder.addMouseListener(new MouseAdapter() {
        	FileHandler handler = new FileHandler(table);
        	public void mouseClicked(MouseEvent e) {
        		
        		AddToUI.newItemDialogue(table);
        		handler.InventoryToFile("files/inventorydatabase.txt");
        		
        	}
        });   
        
        
        logout.addActionListener(new ActionListener() {	        	
        	public void actionPerformed(ActionEvent e) {
        		
        		Login.resetFields();
        		CardLayoutController.showLogin();
        		
        	}
        });  
        
        AddUser.addActionListener(new ActionListener() {	
        	public void actionPerformed(ActionEvent e) {
        		
        		AccountManager.createAcc();	
        		
        	}	
        });  
        
        searchHolder.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		AddToUI.searchItemDialogue(0, table);
        	}
        });   
        
      
        return cardPanel;
    }
}
    

    
    
    
    
    
    
    
    
    
    
    
    
    
