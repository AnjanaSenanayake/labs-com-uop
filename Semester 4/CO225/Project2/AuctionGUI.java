import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class AuctionGUI extends JFrame
{
	public boolean isCellEditable(int row, int column) 
	{
		return false;
	}
	
	private static final long serialVersionUID = 1L;
	JTable table;
	DefaultTableModel model;
	String[] symbols = {"FB","VRTU","MSFT","GOOGL","YHOO","XLNX","TSLA","TXN"};
	public AuctionGUI()
	{
	    model = new DefaultTableModel()
	    		{
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) 
	    			{	
	    				return false;
	    			}
	    		};
	    table = new JTable(model);
	    model.addColumn("Firm Symbol");
	    model.addColumn("Firm Name");
	    model.addColumn("Stock Price");
	    
	    try
	    {
	    	for(int i=0;i<8;i++)
	    	{
	    		addTableRow(model,symbols[i],Auction.getFirmName(symbols[i]),Auction.getStockPrice(symbols[i]));
	    	}
	    }
	    catch(NullPointerException e)
	    {};
	    
	    table.getTableHeader().setFont(new Font(Font.SERIF,Font.BOLD,20));
	    table.setRowHeight(50);
	    table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    table.getColumnModel().getColumn(1).setPreferredWidth(190);
	    table.setRowSelectionAllowed(false);
	    table.setCellSelectionEnabled(false);
	    model.isCellEditable(0,0);
	    table.setCursor(getCursor());
	    table.setBackground(Color.BLACK);
	    table.setForeground(Color.GREEN);
	    table.setGridColor(Color.yellow);
	    
	    //Table will be updated every 500ms if any changed occurred
	    new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	        	try
	    	    {
	    	    	for(int i=0;i<7;i++)
	    	    	{
	    	    		table.getModel().setValueAt(Auction.getStockPrice(symbols[i]),i,2);	
	    	    	}
	    	    }
	    	    catch(NullPointerException e)
	    	    {};
	        }
	    },0,500); 	    
	    
	    JFrame frame = new JFrame();
	    JPanel panel = new JPanel(new GridLayout());
	    JScrollPane scroll = new JScrollPane();
	    scroll.setViewportView(table);
	    scroll.setBackground(Color.BLACK);
	    frame.setBackground(Color.BLACK);
	    add(scroll);
	    frame.setSize(800,500);
	    frame.add(panel);
	    frame.setTitle("Stock Exchanges");
	    TitledBorder border = new TitledBorder("Auction Server");
	    frame.getContentPane().add( scroll, BorderLayout.CENTER );
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
	    border.setTitleColor(Color.BLUE);
	    scroll.setBorder(border);
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	    setLayout(new FlowLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Adds new rows to the jtable
	private void addTableRow(DefaultTableModel model, String string, String string2, String string3) 
	{
		model.addRow(new Object[] {string,string2,string3});
	}
}