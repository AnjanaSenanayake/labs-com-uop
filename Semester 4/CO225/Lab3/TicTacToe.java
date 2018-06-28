/***************************************************************************************
*    Title: TicTacToe
*    Author: Anjana Senanayake
*    Date: 25/08/2017  
*    Code version: v1.0
*    Availability: https://github.com/AnjanaSenanayake/TicTacToe.Java
*
***************************************************************************************/

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFrame window = new JFrame();
	private Toolkit toolkit;
	private JButton[] buttons = new JButton[9];
	private String letter="";
	private int count=0;
	private int dialogResult=0;
	private boolean win = false;
	
	public TicTacToe()
	{
		super("Tic-Tac-Toe");
		setSize(400,400);
		setResizable(false);
		toolkit = getToolkit();
		Dimension size=toolkit.getScreenSize();
		setLocation((size.width-getWidth())/2,(size.height-getHeight())/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(3,3));
		for(int i=0;i<9;i++)
		{
			buttons[i]=new JButton();
		}
		for(int i=0;i<9;i++)
		{
			add(buttons[i]);
		}
		for(int i=0;i<9;i++)
		{
			buttons[i].addActionListener(this);
		}
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		count++;
		if(count%2!=0)
		{
			letter = "1";
		}
		else if(count%2==0)
		{
			letter = "2";
		}
			
		if(e.getSource() == buttons[0])
		{
				buttons[0].setText(letter);
				buttons[0].setEnabled(false);
		}
		else if(e.getSource() == buttons[1])
		{
				buttons[1].setText(letter);
				buttons[1].setEnabled(false);
		}
		else if(e.getSource() == buttons[2])
		{
				buttons[2].setText(letter);
				buttons[2].setEnabled(false);
		}
		else if(e.getSource() == buttons[3])
		{
				buttons[3].setText(letter);
				buttons[3].setEnabled(false);
		}
		else if(e.getSource() == buttons[4])
		{
				buttons[4].setText(letter);
				buttons[4].setEnabled(false);
		}
		else if(e.getSource() == buttons[5])
		{
				buttons[5].setText(letter);
				buttons[5].setEnabled(false);
		}
		else if(e.getSource() == buttons[6])
		{
				buttons[6].setText(letter);
				buttons[6].setEnabled(false);
		}
		else if(e.getSource() == buttons[7])
		{
				buttons[7].setText(letter);
				buttons[7].setEnabled(false);
		}
		else if(e.getSource() == buttons[8])
		{
				buttons[8].setText(letter);
				buttons[8].setEnabled(false);
		}
		
		if((buttons[0].getText()==buttons[1].getText()) && (buttons[1].getText()==buttons[2].getText()) && buttons[1].getText()!="")
		{
			win=true;
		}
		else if((buttons[3].getText()==buttons[4].getText()) && (buttons[4].getText()==buttons[5].getText()) && buttons[3].getText()!="")
		{
			win=true;
		}
		else if((buttons[6].getText()==buttons[7].getText()) && (buttons[7].getText()==buttons[8].getText()) && buttons[6].getText()!="")
		{
			win=true;
		}
		else if((buttons[0].getText()==buttons[3].getText()) && (buttons[3].getText()==buttons[6].getText()) && buttons[0].getText()!="")
		{
			win=true;
		}
		else if((buttons[1].getText()==buttons[4].getText()) && (buttons[4].getText()==buttons[7].getText()) && buttons[1].getText()!="")
		{
			win=true;
		}
		else if((buttons[2].getText()==buttons[5].getText()) && (buttons[5].getText()==buttons[8].getText()) && buttons[2].getText()!="")
		{
			win=true;
		}
		else if((buttons[0].getText()==buttons[4].getText()) && (buttons[4].getText()==buttons[8].getText()) && buttons[0].getText()!="")
		{
			win=true;
		}
		else if((buttons[2].getText()==buttons[4].getText()) && (buttons[4].getText()==buttons[6].getText()) && buttons[2].getText()!="")
		{
			win=true;
		}
		else
		{
			win=false;
		}
		if(win==true)
		{
			dialogResult = JOptionPane.showConfirmDialog(null,"Player "+letter+" Wins\nPlay Again?","Warning",dialogResult);
			if(dialogResult == JOptionPane.YES_OPTION)
			{
				setVisible(false);
				window.dispose();
				new TicTacToe();
			}
			else
			{	
				dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				System.exit(0);
			}	
		}
		else if(count==9 && win==false)
		{
			dialogResult = JOptionPane.showConfirmDialog(null,"Tie Game!\nPlay Again?","Warning",dialogResult);
			if(dialogResult == JOptionPane.YES_OPTION)
			{
				setVisible(false);
				window.dispose();
				new TicTacToe();
			}
			else
			{	
				dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				System.exit(0);
			}	
		}
	}
	
	public static void main(String[]args)
	{
		new TicTacToe();
	}
}