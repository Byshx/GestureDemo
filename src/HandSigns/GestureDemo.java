package HandSigns;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

public class GestureDemo {
	private JFrame jFrame;
	private JButton jButton1, jButton2, jButton3,jButton4;
	private JPanel jPanel, jPanel2;
	private JLabel jLabel;

	public GestureDemo() {
		// TODO Auto-generated constructor stub
		DrawJPanel draw = new DrawJPanel();
		draw.setBorder(BorderFactory.createEtchedBorder());
		jFrame = new JFrame("GestureDemo");
		Container container = jFrame.getContentPane();

		jFrame.setSize(800, 800);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jButton1 = new JButton("开始");
		jButton2 = new JButton("结束");
		jButton2.setEnabled(false);
		jButton3 = new JButton("记录");
		jButton4 = new JButton("光标加速");

		jLabel = new JLabel("等待开始...");

		jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 30));
		jPanel.add(jLabel);
		jPanel.add(jButton1);
		jPanel.add(jButton2);
		jPanel.add(jButton3);

		jPanel2 = new JPanel();
		jPanel2.setBounds(5, 5, 780, 650);
		jPanel2.setBorder(BorderFactory.createEtchedBorder());

		container.add(jPanel, BorderLayout.SOUTH);
		container.add(jPanel2, BorderLayout.CENTER);

		jFrame.setVisible(true);

		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jLabel.setText("请输入手势");
				jButton1.setEnabled(false);
				jButton2.setEnabled(true);
				container.add(draw, BorderLayout.CENTER);
				draw.repaint();
			}
		});
		jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jLabel.setText("在记录中查询");
				jButton2.setEnabled(false);
				jButton1.setEnabled(true);
				container.remove(draw);
				draw.repaint();
				container.repaint();
			}
		});
		
		jButton3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public static int[] FindNextXY(int x, int y, int X, int Y) {
		DirectionJudge directionJudge = new DirectionJudge(x, y, X, Y);
		int flag = directionJudge.getFlag();
		int[] location = new int[2];
		if (flag == 1) {
			location[0] = x;
			location[1] = y + 10;
		} else if (flag == 2) {
			location[0] = x + 10;
			location[1] = y + 10;
		} else if (flag == 3) {
			location[0] = x + 10;
			location[1] = y;
		} else if (flag == 4) {
			location[0] = x + 10;
			location[1] = y - 10;
		}

		else if (flag == 5) {
			location[0] = x;
			location[1] = y - 10;
		} else if (flag == 6) {
			location[0] = x - 10;
			location[1] = y - 10;
		} else if (flag == 7) {
			location[0] = x - 10;
			location[1] = y;
		} else {
			location[0] = x - 10;
			location[1] = y - 10;
		}
		return location;
	}

	@SuppressWarnings("serial")
	class DrawJPanel extends JPanel {
		int x1, x2, y1, y2;
		int X1, X2, Y1, Y2;
		int count = 0;

		public DrawJPanel() {
			x1 = x2 = y1 = y2 = 0;
			X1 = X2 = Y1 = Y2 = 0;
			// TODO Auto-generated constructor stub
			MouseAdapter mouseAdapter = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					x1 = x2 = e.getX();
					y1 = y2 = e.getY();
					X1 = X2 = e.getX();
					Y1 = Y2 = e.getY();
				}

				public void mouseDragged(MouseEvent e) {
					Graphics g = getGraphics();
					Graphics G = getGraphics();
					g.setColor(Color.BLACK);
					G.setColor(Color.RED);
					x1 = x2;
					y1 = y2;
					x2 = e.getX();
					y2 = e.getY();
					draw(g);
					//if (count == 10) {
						X1 = X2;
						Y1 = Y2;
						int[] location = FindNextXY(X1, Y2, e.getX(),
						e.getY());
						System.out.println("("+X1+" "+Y1+")"+","+"("+X2+","+Y2+")");						
						X2 = location[0];
						Y2 = location[1];
						System.out.println("("+X1+" "+Y1+")"+","+"("+X2+","+Y2+")"+"**");						
						Draw(G);
						//count = 0;
					//}
				}
			};
			addMouseListener(mouseAdapter);
			addMouseMotionListener(mouseAdapter);
		}

		public void draw(Graphics g) {
			float LineWidth = 5.0f;
			((Graphics2D) g).setStroke(new BasicStroke(LineWidth));
			((Graphics2D) g).drawLine(x1, y1, x2, y2);
			count++;
		}

		public void Draw(Graphics G) {
			float LineWidth = 5.0f;
			((Graphics2D) G).setStroke(new BasicStroke(LineWidth));
			((Graphics2D) G).drawLine(X1, Y1, X2, Y2);

		}
	}

	public static void main(String[] args) {
		new GestureDemo();
	}

}
