import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI {
	
	private static String numStr = new String();   //用于储存算式
	
	public static void main(String[] args) {
		
		
		JFrame jFrame = new JFrame();
		jFrame.setLayout(null);
		jFrame.setTitle("四则计算器");
		jFrame.setBounds(600, 150, 325, 450);
		
		JTextField jtext = new JTextField();
		jtext.setBounds(30, 20, 250, 40);
		jtext.setEditable(false);
		
		JLabel label = new JLabel("结果：");
		label.setBounds(140, 80, 80, 40);
		
		JTextField jtext1 = new JTextField();
		jtext1.setBounds(180, 80, 100, 40);
		jtext1.setEditable(false);
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				JButton bt = new JButton((2-i)*3+j+1+"");
				bt.setBounds(20+70*j, 150+50*i, 60, 40);
				Bt_textevent(bt, jtext, jtext1);
				jFrame.add(bt);
			}
		}

		JButton bt1 = new JButton("+");
		bt1.setBounds(230, 150, 60, 40);
		Bt_textevent(bt1, jtext, jtext1);
		JButton bt2 = new JButton("-");
		bt2.setBounds(230, 200, 60, 40);
		Bt_textevent(bt2, jtext, jtext1);
		JButton bt3 = new JButton("*");
		bt3.setBounds(230, 250, 60, 40);
		Bt_textevent(bt3, jtext, jtext1);
		JButton bt4 = new JButton("/");
		bt4.setBounds(230, 300, 60, 40);
		Bt_textevent(bt4, jtext, jtext1);
		JButton bt5 = new JButton("0");
		bt5.setBounds(20, 300, 60, 40);
		Bt_textevent(bt5, jtext, jtext1);
		JButton bt6 = new JButton(".");
		bt6.setBounds(90, 300, 60, 40);		
		Bt_textevent(bt6, jtext, jtext1);
		JButton bt7 = new JButton("<-");
		bt7.setBounds(160, 300, 60, 40);
		Bt_textevent(bt7, jtext, jtext1);
		JButton bt8 = new JButton("(");
		bt8.setBounds(20, 350, 60, 40);
		Bt_textevent(bt8, jtext, jtext1);
		JButton bt9 = new JButton(")");
		bt9.setBounds(90, 350, 60, 40);
		Bt_textevent(bt9, jtext, jtext1);
		JButton bt10 = new JButton("AC");
		bt10.setBounds(160, 350, 60, 40);
		Bt_textevent(bt10, jtext, jtext1);
		JButton bt11 = new JButton("=");
		bt11.setBounds(230, 350, 60, 40);
		Bt_textevent(bt11, jtext, jtext1);
		
		jFrame.add(bt1);
		jFrame.add(bt2);
		jFrame.add(bt3);
		jFrame.add(bt4);
		jFrame.add(bt5);
		jFrame.add(bt6);
		jFrame.add(bt7);
		jFrame.add(bt8);
		jFrame.add(bt9);
		jFrame.add(bt10);
		jFrame.add(bt11);
		
		jFrame.add(jtext);
		jFrame.add(label);
		jFrame.add(jtext1);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static void Bt_textevent(JButton bt, JTextField jtext, JTextField jtext1) {   //给按钮增加监听事件
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bt.getText().equals("AC")) {
					numStr = new String();
				}
				else if(bt.getText().equals("<-")) {
					if(!numStr.isEmpty())   //不为空时
						numStr = numStr.substring(0, numStr.length()-1);   //删除最后一个字符
				}
				else if(bt.getText().equals("=")) {
					BigDecimal x = Calculate.calculate("(" + numStr + ")");
					jtext1.setText(x.toString());
				}
				else {
					numStr += bt.getText();
				}
				jtext.setText(numStr);
			}
		});
	}
	
	public static void error() {   //输入算式错误时弹出窗口的方法
		JDialog jd = new JDialog();
		jd.setBounds(600, 300, 200, 100);
		jd.setTitle("提示");
		JLabel lab = new JLabel("          请输入正确的算式！");
		jd.add(lab);
		jd.setVisible(true);
	}
}
