package HandSigns;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OCR {

	private static JFrame jFrame;
	private static JButton clear, jButton1, jButton2, jButton3, jButton4, jButton5, ToSame;
	private static JTextField jTextField, jTextField2;
	private static JLabel jLabel;
	private static JPanel jPanel, tosame, resault;
	private static JCheckBox jCheckBox;
	private static int x, y;
	private static ImagePanel1 leftpanel;
	private static int[][] map;
	private static int up = 0, down = 0, left = 0, right = 0;
	private static boolean f = true;
	private static final String OutPutPic = "./src/库/word.bmp";
	private static final String image1 = "./src/库/word.jpg";
	private static final String image2 = "./src/库/word2.jpg";
	private static CharacteristicPickUp characteristicPickUp = null;
	private static int[][] vector;
	private static int pixel = 60;
	private static Refinment r = new Refinment();
	private static Recognition recognition = new Recognition();
	private static DirectionRecord directionRecord = new DirectionRecord();
	private static DirectionChanged directionChanged = new DirectionChanged();
	private static String wordStrike = ""; // 笔划特征

	public void InitFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = dimension.width;
		y = dimension.height;

		jFrame = new JFrame("OCR_Demo_By Luo");
		jFrame.setSize(x / 2, y / 2);
		jFrame.setLocation(x / 4, y / 4);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jLabel = new JLabel("结果:");

		jButton1 = new JButton("构画轨迹");
		clear = new JButton("清空轨迹");
		clear.setEnabled(false);
		ToSame = new JButton("归一细化");
		ToSame.setEnabled(false);
		jButton3 = new JButton("矢量化");
		jButton3.setEnabled(false);
		jButton4 = new JButton("识别");
		jButton4.setEnabled(false);
		jButton2 = new JButton("训练");
		jButton2.setEnabled(false);
		jButton5 = new JButton("退出");

		jTextField = new JTextField(3);
		jTextField.setEnabled(false);

		jTextField2 = new JTextField(3);

		jCheckBox = new JCheckBox();
		jCheckBox.setText("分析");
		jCheckBox.setEnabled(false);

		jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		jPanel.setBorder(BorderFactory.createEtchedBorder());
		jPanel.add(jButton1);
		jPanel.add(clear);
		jPanel.add(ToSame);
		jPanel.add(jButton3);
		jPanel.add(jButton4);
		jPanel.add(jCheckBox);
		jPanel.add(jLabel);
		jPanel.add(jTextField);
		jPanel.add(jTextField2);
		jPanel.add(jButton2);
		jPanel.add(jButton5);

		Container container = jFrame.getContentPane();
		container.add(jPanel, BorderLayout.SOUTH);
		jFrame.setVisible(true);
		Button(container); // 功能
	}

	public static void Button(Container container) {
		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				leftpanel = new ImagePanel1();
				leftpanel.setBorder(BorderFactory.createEtchedBorder());
				leftpanel.setBackground(Color.RED);
				leftpanel.setBounds(20, 20, x / 5, x / 5);
				container.add(leftpanel);
				container.repaint();
				int[][] m = new int[x / 5][x / 5];
				map = m.clone();
				jButton1.setEnabled(false);
				clear.setEnabled(true);
				ToSame.setEnabled(true);
			}
		});
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.remove(leftpanel);
				container.repaint();
				f = true;
				jButton4.setEnabled(true);
				if (characteristicPickUp != null) {
					characteristicPickUp.ClearArray();
					characteristicPickUp.dispose();
					characteristicPickUp = null;
					System.gc();
				}
				if (tosame != null && resault != null) {
					container.remove(tosame);
					container.remove(resault);
					tosame = null;
					resault = null;
					container.repaint();
				}

				jCheckBox.setSelected(false);
				jCheckBox.setEnabled(false);
				vector = null;
				clear.setEnabled(false);
				jButton1.setEnabled(true);
				ToSame.setEnabled(false);
				jButton3.setEnabled(false);
				jButton4.setEnabled(false);
				jButton2.setEnabled(false);
				jTextField.setText("");
				wordStrike = "";
			}
		});
		ToSame.addActionListener(new ActionListener() {

			@SuppressWarnings("serial")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				float width = right - left;
				float height = down - up;
				if (height / 2 > width) {
					while (width < height / 2) {
						if (right + 1 < x / 5)
							right++;
						if (left - 1 >= 0)
							left--;
						width = right - left;
					}
				} else if (width / 2 > height) {
					while (height < width / 2) {
						if (up - 1 >= 0)
							up--;
						if (down + 1 < x / 5)
							down++;
						height = down - up;
					}
				}
				leftpanel.getPic();
				if (width != pixel || height != pixel) { // 归一化处理图片
					new GetToSame();
				}
				ToSame.setEnabled(false);
				jButton3.setEnabled(true);

				tosame = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File(image1));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						g.drawImage(img, 2, 2, null);
					}
				};
				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(new File(image1));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tosame.setBounds(40 + x / 5, 20, bufferedImage.getWidth() + 4, bufferedImage.getHeight() + 4);
				tosame.setBorder(BorderFactory.createEtchedBorder());
				container.add(tosame);
				tosame.repaint();
				resault = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File(image2));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						g.drawImage(img, 2, 2, null);
					}
				};
				resault.setBounds(60 + x / 5 + bufferedImage.getWidth(), 20, pixel + 4, pixel + 4);
				resault.setBorder(BorderFactory.createEtchedBorder());
				container.add(resault);
				resault.repaint();
			}
		});
		jButton2.addActionListener(new ActionListener() { // 训练

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jTextField2.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(jFrame, "请输入对应字符");
				} else {
					characteristicPickUp.exersice(jTextField2.getText().trim(), recognition, wordStrike);
				}
			}
		});
		jButton3.addActionListener(new ActionListener() { // 矢量化

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vector = new ToVector().getVector().clone();
				jButton3.setEnabled(false);
				jButton4.setEnabled(true);
				jCheckBox.setEnabled(true);
			}
		});
		jCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!jButton4.isEnabled() && !jCheckBox.isSelected()) {
					characteristicPickUp.setVisible(false);
				} else if (!jButton4.isEnabled() && jCheckBox.isSelected()) {
					if (characteristicPickUp == null) {
						characteristicPickUp = new CharacteristicPickUp(jCheckBox, vector);
						vector = r.Calculate(vector);

					} else {
						characteristicPickUp.setVisible(true);
					}
				}
			}
		});
		jButton4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(wordStrike);
				if (jCheckBox.isSelected()) {
					vector = r.Calculate(vector);
					characteristicPickUp = new CharacteristicPickUp(jCheckBox, vector);
					characteristicPickUp.setVisible(true);
					String word = characteristicPickUp.analysis(recognition, wordStrike);
					if (!word.equals("###"))
						jTextField.setText(word.substring(0, word.length() - 4)); // 去除字符串后的.txt
					else
						jTextField.setText(word);
				} else {
					vector = r.Calculate(vector);
					characteristicPickUp = new CharacteristicPickUp(jCheckBox, vector);
					String word = characteristicPickUp.analysis(recognition, wordStrike);
					if (!word.equals("###")) {
						jTextField.setText(word.substring(0, word.length() - 4)); // 去除字符串后的.txt
					} else
						jTextField.setText(word);
				}
				jButton4.setEnabled(false);
				jButton2.setEnabled(true);
			}
		});
		jButton5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jFrame.dispose();
				if (characteristicPickUp != null)
					characteristicPickUp.dispose();
			}
		});
	}

	public static void main(String[] args) {
		OCR ocr = new OCR();
		ocr.InitFrame();
	}

	@SuppressWarnings("serial")
	static class ImagePanel1 extends JPanel {
		int x1, x2, y1, y2;
		Rectangle rectangle = null;

		public ImagePanel1() {
			// TODO Auto-generated constructor stub
			super();
			x1 = x2 = y1 = y2;

			MouseAdapter mouseAdapter = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					x1 = x2 = e.getX();
					y1 = y2 = e.getY();
					directionRecord.setFirst(x1, y1);
					directionChanged.Together(directionRecord);
					directionChanged.setLocation(x1, y1);
					map[y2][x2] = 1;
					if (f) {
						left = right = x2;
						up   = down  = y2;
						f = false;
					}
				}
				public void mouseDragged(MouseEvent e) {
					Graphics g = getGraphics();
					g.setColor(Color.BLACK);
					x1 = x2;
					y1 = y2;
					directionChanged.setLocation(x2, y2);
					x2 = e.getX();
					y2 = e.getY();
					
					if (x2 < left) // 记录边界
						left = x2;
					if (x2 > right)
						right = x2;
					if (y2 < up)
						up = y2;
					if (y2 > down)
						down = y2;
					// System.out.println(y2 + " " + x2);
					if (x2 >= 0 && x2 < x / 5 && y2 >= 0 && y2 < x / 5) {
						map[y2][x2] = 1;
					}
					paint(g);
				}

				public void mouseReleased(MouseEvent e) {
					directionRecord.setEnd(x2, y2);
					wordStrike += directionRecord.DealDirection();//directionChanged.getStrike()
					directionChanged.clearStrike();
					//System.out.println(wordStrike);
				}
			};
			addMouseListener(mouseAdapter);
			addMouseMotionListener(mouseAdapter);
		}

		public void paintComponent(Graphics g) {
			float width = 2.0f;
			((Graphics2D) g).setStroke(new BasicStroke(width));
			((Graphics2D) g).drawLine(x1, y1, x2, y2);
		}

		public void getPic() {
			int width = right - left + 1;
			int height = down - up + 2;
			rectangle = new Rectangle(this.getBounds().x + left + x / 4 + 9, this.getBounds().y + up + y / 4 + 38,
					width, height); // 有待修正
			// System.out.println(this.getBounds().x + " " + this.getBounds().y
			// + " " + left + " " + up);
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedImage image = robot.createScreenCapture(rectangle);
			try {
				ImageIO.write(image, "BMP", new File(OutPutPic));
				ImageIO.write(image, "JPG", new File(image1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
