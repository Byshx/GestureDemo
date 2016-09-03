package HandSigns;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CharacteristicPickUp extends JFrame {
	private static JPanel vertical, horizontal, left, right;
	private static int pixel = 60;
	private static int times = 4;
	private static int[] v = null;
	private static int[] h = null;
	private static int[] l = null;
	private static int[] r = null;
	private static int[][] vector = null;

	public CharacteristicPickUp(JCheckBox jCheckBox, int[][] vector) {
		// TODO Auto-generated constructor stub
		Dimension dimension = getToolkit().getScreenSize();
		int x = dimension.width;
		int y = dimension.height;
		setTitle("笔画密度特征分析");
		setBounds(3 * x / 4 - 40, y / 4, x / 4 + 40, y / 2);
		Container container = getContentPane();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				jCheckBox.setSelected(false);
				dispose();
			}
		});
		v = new int[pixel];
		h = new int[pixel];
		l = new int[2 * pixel - 1];
		r = new int[2 * pixel - 1];

		CharacteristicPickUp.vector = vector.clone();
		Calculate();
		setLayout(null);
		vertical = new JPanel() {
			public void paintComponent(Graphics g) {
				int x = 0;
				int y = 0;
				for (int i = 0; i < pixel; i++) {
					g.drawLine(x, y, times * i, times * v[i]);
					x = times * i;
					y = times * v[i];
				}
			}
		};
		vertical.setBounds(10, 10, 240, 240);
		vertical.setBorder(BorderFactory.createEtchedBorder());

		horizontal = new JPanel() {
			public void paintComponent(Graphics g) {
				int x = 0;
				int y = 0;
				for (int i = 0; i < pixel; i++) {
					g.drawLine(x, y, times * i, times * h[i]);
					x = times * i;
					y = times * h[i];
				}
			}
		};
		horizontal.setBounds(250, 10, 240, 240);
		horizontal.setBorder(BorderFactory.createEtchedBorder());

		left = new JPanel() {
			public void paintComponent(Graphics g) {
				int x = 0;
				int y = 0;
				for (int i = 0; i < pixel; i++) {
					g.drawLine(x, y, times * i, times * l[i]);
					x = times * i;
					y = times * l[i];
				}
			}
		};
		left.setBounds(10, 250, 240, 240);
		left.setBorder(BorderFactory.createEtchedBorder());

		right = new JPanel() {
			public void paintComponent(Graphics g) {
				int x = 0;
				int y = 0;
				for (int i = 0; i < pixel; i++) {
					g.drawLine(x, y, times * i, times * r[i]);
					x = times * i;
					y = times * r[i];
				}
			}
		};
		right.setBounds(250, 250, 240, 240);
		right.setBorder(BorderFactory.createEtchedBorder());
		
		vertical.setToolTipText("垂直方向特征"); // 鼠标停留信息提示
		horizontal.setToolTipText("水平方向特征");
		left.setToolTipText("斜135°方向特征");
		right.setToolTipText("斜45°方向特征");

		container.add(vertical);
		container.add(horizontal);
		container.add(left);
		container.add(right);
		setVisible(false);

	}

	public static void Calculate() {
		if (pixel % 2 == 0) {
			for (int i = 0; i < pixel; i++) {
				int start = i;
				for (int j = 0; j < pixel; j++) {
					v[j] += vector[i][j];
					h[i] += vector[i][j];
					if ((i + j + 2) % 2 == 0) {
						start++;
						l[start] += vector[i][j];
						r[start] += vector[i][pixel - j - 1];
					} else {
						l[start] += vector[i][j];
						r[start] += vector[i][pixel - j - 1];
					}
				}
			}
		}
	}
	
	public String analysis(Recognition recognition,String wordStrike){
		return recognition.analysis(v, h, l, r, wordStrike);
	}

	public void exersice(String word, Recognition recognition,String wordStrike) {
		recognition.exersice(v, h, l, r, word,wordStrike);
	}

	public void ClearArray() {
		v = null;
		h = null;
		l = null;
		r = null;
	}
}
