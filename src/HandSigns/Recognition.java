package HandSigns;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Recognition {
	private String pathname = "./src/字符特征库/";
	private int parameter = 50; // 欧几里得度量(欧氏距离)参数

	public void exersice(int[] v, int[] h, int[] l, int[] r, String word, String wordStrike) {
		String stringv = "";
		String stringh = "";
		String stringl = "";
		String stringr = "";
		for (int i = 0; i < v.length; i++) {
			stringv = stringv + v[i] + " ";
			stringh = stringh + h[i] + " ";
			stringl = stringl + l[i] + " ";
			stringr = stringr + r[i] + " ";
		}
		System.out.println(stringv);
		File file = new File(pathname + word + ".txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			bufferedWriter.write(wordStrike);
			bufferedWriter.newLine();
			bufferedWriter.write(stringv);
			bufferedWriter.newLine();
			bufferedWriter.write(stringh);
			bufferedWriter.newLine();
			bufferedWriter.write(stringl);
			bufferedWriter.newLine();
			bufferedWriter.write(stringr);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String analysis(int[] v, int[] h, int[] l, int[] r, String wordStrike) {
		File file = new File(pathname);
		String wordname[] = file.list();
		ArrayList<Integer> wordvalue = new ArrayList<Integer>();
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		ArrayList<Integer> wordstrike = new ArrayList<Integer>();
		HashMap<Integer, String> hashMapStrike = new HashMap<Integer, String>();

		String string[] = wordStrike.split("");
		int[] WordStrikeNumber = new int[wordStrike.length()];
		for (int i = 0; i < wordStrike.length(); i++) {
			WordStrikeNumber[i] = Integer.parseInt(string[i]);
		}

		for (int i = 0; i < wordname.length; i++) {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			try {
				fileReader = new FileReader(pathname + wordname[i]);
				bufferedReader = new BufferedReader(fileReader);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int num = 0;
			try {
				String strike[] = bufferedReader.readLine().split("");
				if (strike.length != string.length){
					continue;
				}
				for (int j = 0; j < wordStrike.length(); j++) {
					int WordStrikeNumberSample = Integer.parseInt(strike[j]);
					int a = 0;
					if (Math.abs(WordStrikeNumber[j] - WordStrikeNumberSample) > 4) {
						a = 8 - Math.abs(WordStrikeNumber[j] + WordStrikeNumberSample);
					} else
						a = WordStrikeNumber[j] - WordStrikeNumberSample;
					num = num + (int) Math.pow(a, 2);
				}
				num = (int) Math.pow(num, 0.5);
				wordstrike.add(num);
				hashMapStrike.put(num, wordname[i]);        //被覆盖**
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		Collections.sort(wordstrike);

		if (!wordstrike.isEmpty()) {
			int N = wordstrike.size();
			if (N > 10)
				N = 10;
			for (int i = 0; i < N; i++) {
				FileReader fileReader = null;
				BufferedReader bufferedReader = null;
				String name = hashMapStrike.get(wordstrike.get(i));
				try {
					fileReader = new FileReader(pathname + name);
					bufferedReader = new BufferedReader(fileReader);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int EuclideanMetricV = 0;
				int EuclideanMetricH = 0;
				int EuclideanMetricL = 0;
				int EuclideanMetricR = 0;

				try {
					bufferedReader.readLine();
					String stringv[] = bufferedReader.readLine().split(" ");
					String stringh[] = bufferedReader.readLine().split(" ");
					String stringl[] = bufferedReader.readLine().split(" ");
					String stringr[] = bufferedReader.readLine().split(" ");
					for (int j = 0; j < stringv.length; j++) {
						EuclideanMetricV = EuclideanMetricV + (int) (Math.pow(v[j] - Integer.parseInt(stringv[j]), 2));
						EuclideanMetricH = EuclideanMetricH + (int) (Math.pow(h[j] - Integer.parseInt(stringh[j]), 2));
						EuclideanMetricL = EuclideanMetricL + (int) (Math.pow(l[j] - Integer.parseInt(stringl[j]), 2));
						EuclideanMetricR = EuclideanMetricR + (int) (Math.pow(r[j] - Integer.parseInt(stringr[j]), 2));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				EuclideanMetricV = (int) Math.pow(EuclideanMetricV, 0.5);
				EuclideanMetricH = (int) Math.pow(EuclideanMetricH, 0.5);
				EuclideanMetricL = (int) Math.pow(EuclideanMetricL, 0.5);
				EuclideanMetricR = (int) Math.pow(EuclideanMetricR, 0.5);

				int counter = 0;
				int max = 0;
				if (EuclideanMetricV < parameter) {
					max = EuclideanMetricV;
					counter++;
				}
				if (EuclideanMetricH < parameter) {
					if (max < EuclideanMetricH)
						max = EuclideanMetricH;
					counter++;
				}
				if (EuclideanMetricL < parameter) {
					if (max < EuclideanMetricL)
						max = EuclideanMetricL;
					counter++;
				}
				if (EuclideanMetricR < parameter) {
					if (max < EuclideanMetricR)
						max = EuclideanMetricR;
					counter++;
				}
				int a = (EuclideanMetricH + EuclideanMetricL + EuclideanMetricR + EuclideanMetricV) / 4;
				a = a + wordstrike.get(i) * 15;
				 System.out.println(EuclideanMetricV + " " + EuclideanMetricH+ " " + EuclideanMetricL + " " + EuclideanMetricR +" "+ name+" "+wordstrike.get(i) * 10);
				if (counter >= 3) {
					wordvalue.add(a);
					hashMap.put(a, name);
				}
			}
		}

		if (!wordvalue.isEmpty()) {
			Collections.sort(wordvalue);
			return hashMap.get(wordvalue.get(0));
		}
		return "###";
	}

}
