package BSK_PS_1.com.kamil.tm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private static int depth;

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	public static void zadanie_1_encrypt(TextArea textAreaLeft, TextArea textAreaRight) throws IOException {
		List<String> wordsList = new ArrayList<>();
		boolean firstLine = true;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_1_encrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					depth = Integer.valueOf(line);
				} else {
					wordsList.add(line);
				}
			}
		}

		String leftSide = depth + "\n", rightSide = depth + "\n";
		for (String words : wordsList) {
			String input = words.trim();
			StringBuilder output = new StringBuilder();
			int wordLength = input.length();

			int shift = 0, pos = 0, counter;
			for (int currentDepth = 0; currentDepth < depth; currentDepth++) {
				pos = currentDepth;
				counter = 1;
				while (pos < wordLength) {
					output.append(input.charAt(pos));
					if (currentDepth == 0 || currentDepth == depth - 1)
						shift = (depth * 2) - 2;
					else
						shift = ((depth * counter - counter) - pos) * 2;
					pos += shift;
					counter++;
				}
			}
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Input String : " + input);
			System.out.println("Encrypted Text : " + output + "\n");
		}
		PrintWriter writer = new PrintWriter("zadanie_1_decrypt.txt", "UTF-8");
		writer.println(rightSide);
		writer.close();
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	public static void zadanie_1_decrypt(TextArea textAreaLeft, TextArea textAreaRight) throws IOException {
		List<String> wordsList = new ArrayList<>();
		boolean firstLine = true;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_1_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					depth = Integer.valueOf(line);
				} else {
					wordsList.add(line);
				}
			}
		}
		String leftSide = depth + "\n", rightSide = depth + "\n";
		for (String words : wordsList) {
			String input = words.trim();
			StringBuilder output = new StringBuilder();
			int wordLength = input.length();
			char matrix[][] = new char[depth][wordLength];
			int step = (depth - 1) * 2;
			int currentChar = 0;

			for (int row = 0; row < depth; row++) {
				for (int col = row; col < wordLength && currentChar < wordLength; col += step) {
					int pos = col + (step - row * 2);
					matrix[row][col] = input.charAt(currentChar);
					currentChar++;
					if (row != 0 && row != depth - 1 && pos < wordLength) {
						matrix[row][pos] = input.charAt(currentChar);
						currentChar++;
					}
				}
			}

			for (int col = 0; col < wordLength; col++) {
				for (int row = 0; row < depth; row++) {
					if (matrix[row][col] != '\0')
						output.append(matrix[row][col]);
				}
			}
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Encrypted Text : " + input);
			System.out.println("Decrypted Text : " + output + "\n");
		}
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	public static void zadanie_2_encrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {
		List<String> wordsList = new ArrayList<>();
		String leftSide = "", rightSide = "";
		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_2_encrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line);
			}
		}

		for (String words : wordsList) {
			String input = words.replaceAll("\\s+", "");
			StringBuilder output = new StringBuilder();
			double wordLength = input.length();
			int inputCounter = 0;
			int keyMatrix[] = new int[] { 2, 3, 0, 4, 1 };
			int keyCounter = 0;
			Double totalRows = (Double) Math.ceil((Double) wordLength / (Double) 5.0);
			char matrix[][] = new char[input.length()][5];

			for (int row = 0; row < totalRows; row++)
				for (int col = 0; col < 5; col++)
					if (inputCounter + 1 <= wordLength)
						matrix[row][col] = input.charAt(inputCounter++);

			for (int row = 0; row < totalRows; row++) {
				while (keyCounter < 5) {
					char s = matrix[row][keyMatrix[keyCounter]];
					if (matrix[row][keyMatrix[keyCounter++]] != '\0')
						output.append(s);
				}
				keyCounter = 0;
			}
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Plain Text : " + input);
			System.out.println("Encrypted Text : " + output + "\n");
		}
		PrintWriter writer = new PrintWriter("zadanie_2_decrypt.txt", "UTF-8");
		writer.println(rightSide);
		writer.close();
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);

	}

	public static void zadanie_2_decrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {
		List<String> wordsList = new ArrayList<>();
		String leftSide = "", rightSide = "";
		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_2_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line);
			}
		}

		for (String words : wordsList) {
			String input = words.replaceAll("\\s+", "");
			StringBuilder output = new StringBuilder();
			double wordLength = input.length();
			int inputCounter = 0;
			int keyMatrix[] = new int[] { 2, 4, 0, 1, 3 };
			int keyMatrixEncrypt[] = new int[] { 2, 3, 0, 4, 1 };
			int keyMatrixEncryptWithTwo[] = new int[] { 1, 2, 0, 4, 3 };
			int keyCounter = 0;
			Double totalRows = (Double) Math.ceil((Double) wordLength / (Double) 5.0);
			System.out.println(
					"Total rows: " + totalRows + ", total letters: " + wordLength + ", total: " + (totalRows * 5));
			char matrix[][] = new char[input.length()][5];

			for (int row = 0; row < totalRows; row++)
				for (int col = 0; col < 5; col++)
					if (inputCounter + 1 <= wordLength)
						matrix[row][col] = input.charAt(inputCounter++);

			for (int row = 0; row < totalRows; row++) {
				for (int col = 0; col < 5; col++) {
					System.out.print(matrix[row][col]);
				}
				System.out.println();
			}

			System.out.println();
			for (int row = 0; row < totalRows; row++) {
				while (keyCounter < 5) {
					if ((row == totalRows - 1) && (totalRows * 5) - 1 == wordLength) {
						char s = matrix[row][keyMatrixEncrypt[keyCounter]];
						if (matrix[row][keyMatrixEncrypt[keyCounter++]] != '\0')
							output.append(s);
					} else if ((row == totalRows - 1) && (totalRows * 5) - 2 == wordLength) {
						char s = matrix[row][keyMatrixEncryptWithTwo[keyCounter]];
						if (matrix[row][keyMatrixEncryptWithTwo[keyCounter++]] != '\0')
							output.append(s);
					} else {
						char s = matrix[row][keyMatrix[keyCounter]];
						if (matrix[row][keyMatrix[keyCounter++]] != '\0')
							output.append(s);
					}
				}
				keyCounter = 0;
			}
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Encrypted Text : " + input);
			System.out.println("Decrypted Text : " + output + "\n");
		}
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);

	}

	public static void zadanie_3_a_encrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, UnsupportedEncodingException {
		List<String> wordsList = new ArrayList<>();
		boolean isFirstLine = true;
		String key = null;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_3_a_encrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (isFirstLine) {
					key = line;
					isFirstLine = false;
					continue;
				}
				wordsList.add(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		String leftSide = key + "\n", rightSide = key + "\n";
		char[] keyArray = key.toCharArray();
		for (String words : wordsList) {
			String input = words.replaceAll("\\s+", "").toUpperCase();
			System.out.println(
					"Input: " + input + ", length: " + input.length() + ", key: " + key + ", length: " + key.length());
			StringBuilder output = new StringBuilder();
			int wordLength = input.length();
			depth = (int) Math.ceil(wordLength / key.length());
			System.out.println("Depth: " + depth);
			char[] message = new char[wordLength];
			char[] inp = input.toCharArray();
			for (int f = 0; f < wordLength; f++) {
				message[f] = inp[f];
			}

			for (int letterAlphabet = 65; letterAlphabet <= 90; letterAlphabet++)
				for (int col = 0; col < key.length(); col++)
					if (keyArray[col] == letterAlphabet)
						for (int row = 0; col + keyArray.length * row < wordLength; row++)
							output.append(message[col + keyArray.length * row]);
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Encrypted Text : " + input);
			System.out.println("Decrypted Text : " + output + "\n");
		}
		PrintWriter writer = new PrintWriter("zadanie_3_a_decrypt.txt", "UTF-8");
		writer.println(rightSide);
		writer.close();
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);

	}

	public static void zadanie_3_a_decrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, UnsupportedEncodingException {
		List<String> wordsList = new ArrayList<>();
		boolean isFirstLine = true;
		String key = null;
		StringBuilder output = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_3_a_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (isFirstLine) {
					key = line;
					isFirstLine = false;
					continue;
				}
				wordsList.add(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		String leftSide = key + "\n", rightSide = key + "\n";
		char[] keyArray = key.toCharArray();

		String words = wordsList.get(0);
		String input = words.replaceAll("\\s+", "").toUpperCase();
		System.out.println(
				"Input: " + input + ", length: " + input.length() + ", key: " + key + ", length: " + key.length());
		int wordLength = input.length();
		int totalChars = wordLength % key.length();
		depth = (int) Math.ceil(wordLength / key.length()) + totalChars;
		System.out.println("Depth: " + depth);
		char[] message = new char[wordLength];
		char[] messageOutput = new char[wordLength];

		char[] inp = input.toCharArray();
		for (int f = 0; f < wordLength; f++) {
			message[f] = inp[f];
		}

		int counter = 0;
		for (int letterAlphabet = 65; letterAlphabet <= 90; letterAlphabet++)
			for (int col = 0; col < key.length(); col++)
				if (keyArray[col] == letterAlphabet)
					for (int row = 0; col + keyArray.length * row < wordLength; row++) {
						messageOutput[col + keyArray.length * row] = inp[counter++];
					}

		System.out.println("Encrypted Text : " + input);
		System.out.print("Decrypted Text : ");
		for (int i = 0; i < messageOutput.length; i++) {
			output.append(messageOutput[i]);
		}
		leftSide += input + "\n";
		rightSide += output.toString() + "\n";

		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);

	}

	public static void zadanie_3_b_decrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {

		List<String> wordsList = new ArrayList<>();
		int row = 0;
		int nextline = 0;

		String text = null;
		String key = null;

		int counterNumberOfText = 0;
		int textsize = 0;
		char currentletter = 'A';
		int countertostack = 0;

		char[] keyarray = null;

		char[] textarray = null;

		int[] stack = null;

		char[][] tablica = null;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_3_b_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line);
			}
		}

		for (String words : wordsList) {

			String input = words.replaceAll("\\s+", "");
			if (nextline == 1) {
				text = input; // Wypisywanie z pliku 2 linia
				textarray = text.toCharArray();
				textsize = text.length();

			} else {

				key = input; // Wypisywanie z pliku 1 linia
				int keylenght = key.length();
				stack = new int[keylenght]; // stos
				keyarray = key.toCharArray();

			}
			nextline++;
		}

		int lettercounter = 0;
		int j = 1;

		for (int i = 0; i < key.length(); i++) {
			lettercounter += j;
			j++;
		}
		if (lettercounter < text.length()) {
			System.out.println("Algorythm wont work");
			return;
		}

		int letterCounter2 = 0;
		int maxNumberOfRows = 1;

		for (int i = 0; i < key.length(); i++) {
			if (letterCounter2 < text.length()) {
				letterCounter2 += maxNumberOfRows;
				maxNumberOfRows++;
			}
		}

		maxNumberOfRows = maxNumberOfRows - 1;

		System.out.println(maxNumberOfRows);

		tablica = new char[maxNumberOfRows][key.length()];

		System.out.println("Plant Text: " + text);
		System.out.println("Key Text: " + key);

		for (int roww = 0; roww < maxNumberOfRows; roww++) {

			for (int col = 0; col < key.length(); col++) {

				tablica[roww][col] = '0';
			}
		}

		for (int alphabetletter = 0; alphabetletter < 26; alphabetletter++) {

			for (int CheckIfLetterIsInWord = 0; CheckIfLetterIsInWord < key.length(); CheckIfLetterIsInWord++) {

				if (keyarray[CheckIfLetterIsInWord] == currentletter) {

					stack[countertostack] = CheckIfLetterIsInWord; // Dodanie do stosu do odczytania w odpowiedniej
																	// kolejnosci
					countertostack++;

					for (int col = 0; col <= CheckIfLetterIsInWord; col++) { // Wypelnij wiersz

						if (counterNumberOfText >= textsize) {
							break; //
						} else {
							tablica[row][col] = '1';
							counterNumberOfText++;
						}
					}
					row++; // Nastepny wiersz po wypelnieniu
				}
			}
			currentletter++;
		}
		int counter = 0;
		int column = 0;

		System.out.print("Encoded Word: ");
		System.out.println("");

		int licznik = 0;

		for (int readthecolumn = 0; readthecolumn < stack.length; readthecolumn++) {

			column = stack[readthecolumn]; // Ze stosu zdjemuje i odczytuje kolumny po kolei

			for (int rowInStackColumn = 0; rowInStackColumn < maxNumberOfRows; rowInStackColumn++) {
				if (tablica[rowInStackColumn][column] == '0') {
				} else {
					tablica[rowInStackColumn][column] = textarray[licznik];
					licznik++;
					if (licznik > textarray.length) {
						return;
					}
				}
			}
		}
		int licznik2137 = 0;
		char[] response = new char[text.length()];
		for (int kooo = 0; kooo < maxNumberOfRows; kooo++) {
			for (int koooo = 0; koooo < key.length(); koooo++) {

				if (tablica[kooo][koooo] != '0') {
					response[licznik2137] = tablica[kooo][koooo];
					licznik2137++;
				}
			}
		}
		String output = "";
		for (int damian = 0; damian < licznik2137; damian++) {
			System.out.print(response[damian]);
			output += response[damian];
		}

		String leftSide = key + "\n" + text, rightSide = key + "\n" + output;
		textAreaLeft.setText(leftSide.toString());
		textAreaRight.setText(rightSide.toString());

	}

	public static void zadanie_3_b_encrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {

		List<String> wordsList = new ArrayList<>();

		int row = 0;

		int nextline = 0;

		String text = null;
		String key = null;

		int counterNumberOfText = 0;

		int textsize = 0;

		char currentletter = 'A';

		int countertostack = 0;

		char[] keyarray = null;

		char[] textarray = null;

		int[] stack = null;

		char[][] tablica = null;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_3_b_encrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line.toUpperCase());
			}
		}

		for (String words : wordsList) {
			String input = words.replaceAll("\\s+", "");
			if (nextline == 1) {
				text = input; // Wypisywanie z pliku 2 linia
				textarray = text.toCharArray();
				textsize = text.length();
			} else {
				key = input.toUpperCase(); // Wypisywanie z pliku 1 linia
				int keylenght = key.length();
				stack = new int[keylenght]; // stos
				keyarray = key.toCharArray();
			}
			nextline++;
		}

		int lettercounter = 0;
		int j = 1;

		for (int i = 0; i < key.length(); i++) {
			lettercounter += j++;
		}
		if (lettercounter < text.length()) {
			System.out.println("Algorythm wont work");
			return;
		}

		String leftSide = key + "\n" + text, rightSide = key + "\n";

		int letterCounter2 = 0;
		int maxNumberOfRows = 1;

		for (int i = 0; i < key.length(); i++) {
			if (letterCounter2 < text.length()) {
				letterCounter2 += maxNumberOfRows;
				maxNumberOfRows++;
			}
		}
		tablica = new char[--maxNumberOfRows][key.length()];
		System.out.println("Plant Text: " + text);
		System.out.println("Key Text: " + key);

		for (int roww = 0; roww < maxNumberOfRows; roww++) {
			for (int col = 0; col < key.length(); col++) {
				tablica[roww][col] = '0';
			}
		}

		for (int alphabetletter = 0; alphabetletter < 26; alphabetletter++) {
			for (int CheckIfLetterIsInWord = 0; CheckIfLetterIsInWord < key.length(); CheckIfLetterIsInWord++) {
				if (keyarray[CheckIfLetterIsInWord] == currentletter) {
					stack[countertostack] = CheckIfLetterIsInWord; // Dodanie do stosu do odczytania w odpowiedniej
																	// kolejnosci
					countertostack++;
					for (int col = 0; col <= CheckIfLetterIsInWord; col++) { // Wypelnij wiersz
						if (counterNumberOfText >= textsize) {
							break; //
						} else {
							tablica[row][col] = textarray[counterNumberOfText];
							counterNumberOfText++;
						}
					}
					row++; // Nastepny wiersz po wypelnieniu
				}
			}
			currentletter++;
		}

		int counter = 0;
		int column = 0;

		char[] response = new char[text.length()]; // odpowiedz koncowa tablica zaszyfrowanego textu
		for (int readthecolumn = 0; readthecolumn < stack.length; readthecolumn++) {
			column = stack[readthecolumn]; // Ze stosu zdjemuje i odczytuje kolumny po kolei
			for (int rowInStackColumn = 0; rowInStackColumn < maxNumberOfRows; rowInStackColumn++) {
				if (tablica[rowInStackColumn][column] == '0') {
				} else {
					response[counter] = tablica[rowInStackColumn][column];
					counter++;

				}

			}

		}

		String output = "";
		System.out.print("Encoded Word: ");
		for (int print = 0; print < text.length(); print++) {
			System.out.print(response[print]);
			output += response[print];
		}
		rightSide += output;
		PrintWriter writer = new PrintWriter("zadanie_3_b_decrypt.txt", "UTF-8");
		writer.println(rightSide);
		writer.close();
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	public static void zadanie_4_encrypt_i_decrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {
		List<String> wordsList = new ArrayList<>();
		List<String> listWithWordsToDecode = new ArrayList<String>();
		int counter = 0;
		int currentListElement = 0;
		int k0 = 0, k1 = 0, alphabet = 0;

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_4_encrypt_i_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				switch (counter) {
				case 0:
					alphabet = Integer.valueOf(line);
					break;
				case 1:
					k0 = Integer.valueOf(line);
					break;
				case 2:
					k1 = Integer.valueOf(line);
					break;
				default:
					wordsList.add(line);
					break;
				}
				counter++;
			}
		}

		String leftSide = alphabet + "\n" + k0 + "\n" + k1 + "\n", rightSide = alphabet + "\n" + k0 + "\n" + k1 + "\n";

		for (String words : wordsList) {
			String input = words.replaceAll("\\s+", "");
			StringBuilder output = new StringBuilder();
			int wordLength = input.length();
			k0 = calcuate_k_to_zadanie_4(k0, alphabet);
			k1 = calcuate_k_to_zadanie_4(k1, alphabet);

			char[] inputArray = input.toCharArray();
			for (int x = 0; x < wordLength; x++) {
				output.append(encode_to_zadanie_4(inputArray[x], k0, k1, alphabet));
			}

			listWithWordsToDecode.add(output.toString());
			System.out.println("Plain Text :     " + input);
			System.out.println("Encrypted Text : " + output + "\n");
		}

		for (String words : listWithWordsToDecode) {
			String input = words.replaceAll("\\s+", "");
			StringBuilder output = new StringBuilder();
			int wordLength = input.length();

			int euler_number, euler_pow_number;
			int euler_table[] = new int[] { 1, 1, 2, 2, 4, 2, 6, 4, 6, 4, 10, 4, 12, 6, 8, 8, 16, 6, 18, 8, 12, 10, 22,
					8, 20, 12 };

			euler_number = euler_table[alphabet - 1] - 1;
			if (calcuate_k_to_zadanie_4(k0, alphabet) != 1 || calcuate_k_to_zadanie_4(k1, alphabet) != 1) {
				System.out.println(
						k0 + " or " + k1 + " is not wzglednie pierwsza  liczba do alphabet (" + alphabet + ")");
			} else {
				k0 = calcuate_k_to_zadanie_4(k0, alphabet);
				k1 = calcuate_k_to_zadanie_4(k1, alphabet);
			}

			euler_pow_number = (int) Math.pow(k1, euler_number);

			char[] inputArray = input.toCharArray();
			for (int x = 0; x < wordLength; x++) {
				output.append(decode_to_zadanie_4(inputArray[x], k0, k1, 26, euler_pow_number));
			}
			leftSide += input + "\n";
			rightSide += output.toString() + "\n";
			System.out.println("Encrypted Text : " + input + " (" + wordsList.get(currentListElement++) + ")");
			System.out.println("Decrypted Text : " + output + "\n");

		}

		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	public static boolean isAlpha_to_zadanie_4(char c) {
		return Character.isLetter(c);
	}

	public static int calcuate_k_to_zadanie_4(int k, int alphabet) {
		if (k == 0) {
			return alphabet;
		}
		return calcuate_k_to_zadanie_4(alphabet % k, k);
	}

	public static char encode_to_zadanie_4(char c, int k0, int k1, int alphabet) {
		if (isAlpha_to_zadanie_4(c)) {
			c = Character.toUpperCase(c);
			c = (char) (((((c - 65) * k1) + k0) % alphabet) + 65);
		}
		return c;
	}

	public static char decode_to_zadanie_4(char c, int k0, int k1, int alphabet, int euler_pow_number) {
		if (isAlpha_to_zadanie_4(c)) {
			c = Character.toUpperCase(c);
			c = (char) (((((c - 65) + (alphabet - k0)) * euler_pow_number) % alphabet) + 65);
		}
		return c;
	}

	public static void zadanie_5_encrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {

		List<String> wordsList = new ArrayList<>();
		int i = 0;
		String text = null;
		String key = null;
		String res = "";

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_5_encrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line);
			}
		}

		for (String words : wordsList) {

			String input = words.replaceAll("\\s+", "").toUpperCase();
			if (i == 1) {
				key = input;
			} else {
				text = input;
			}
			i++;
		}

		System.out.println("Plain Text : " + text);
		System.out.println("Plain Key : " + key);
		String leftSide = text + "\n" + key, rightSide = "";

		for (int x = 0, j = 0; x < text.length(); x++) {
			char c = text.charAt(x);
			if (c < 'A' || c > 'Z')
				continue;
			res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
			j = ++j % key.length();
		}
		System.out.println("Vigenere cipher Encrypt : " + res);
		rightSide += res + "\n" + key;
		PrintWriter writer = new PrintWriter("zadanie_5_decrypt.txt", "UTF-8");
		writer.println(rightSide);
		writer.close();
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	public static void zadanie_5_decrypt(TextArea textAreaLeft, TextArea textAreaRight)
			throws FileNotFoundException, IOException {

		List<String> wordsList = new ArrayList<>();

		int i = 0;

		String text = null;
		String key = null;
		String res = "";

		try (BufferedReader br = new BufferedReader(new FileReader("zadanie_5_decrypt.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				wordsList.add(line);
			}
		}

		for (String words : wordsList) {

			String input = words.replaceAll("\\s+", "");
			if (i == 1) {
				key = input;
			} else {
				text = input;
			}
			i++;
		}

		System.out.println("Encrypted Text : " + text);
		System.out.println("Plain Key : " + key);

		String leftSide = text + "\n" + key, rightSide = "";

		text = text.toUpperCase();
		for (int x = 0, j = 0; x < text.length(); x++) {
			char c = text.charAt(x);
			if (c < 'A' || c > 'Z')
				continue;
			res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
			j = ++j % key.length();
		}
		System.out.println("Vigenere cipher Decrypted : " + res);
		rightSide += res + "\n" + key;
		textAreaLeft.setText(leftSide);
		textAreaRight.setText(rightSide);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		BorderPane borderPane = new BorderPane();

		VBox vBoxLeft = new VBox();
		TextArea textAreaLeft = new TextArea();
		textAreaLeft.setStyle("-fx-control-inner-background: #3f3f3f3f; -fx-text-fill: black;");
		textAreaLeft.setEditable(false);
		vBoxLeft.getChildren().add(textAreaLeft);
		vBoxLeft.setPadding(new Insets(0, 0, 0, 10));
		vBoxLeft.setStyle("-fx-background-color: #222222");

		VBox vBoxRight = new VBox();
		TextArea textAreaRight = new TextArea();
		textAreaRight.setStyle("-fx-control-inner-background: #3f3f3f3f; -fx-text-fill: black;");
		textAreaRight.setEditable(false);
		vBoxRight.getChildren().add(textAreaRight);
		vBoxRight.setPadding(new Insets(0, 10, 0, 0));
		vBoxRight.setStyle("-fx-background-color: #222222");

		VBox vBoxCenter = new VBox();
		vBoxCenter.setStyle("-fx-background-color: #222222");
		Label centerLabel = new Label("=>");
	//	Image image = new Image(getClass().getResourceAsStream("arrow.png"));
	//	ImageView imageView = new ImageView(image);
	//	imageView.setFitWidth(20);
	//	imageView.setFitHeight(20);
	//	centerLabel.setGraphic(imageView);
		centerLabel.setTextFill(Color.WHITE);
		vBoxCenter.setAlignment(Pos.CENTER);
		vBoxCenter.setPadding(new Insets(0, 10, 0, 10));
		vBoxCenter.getChildren().add(centerLabel);

		HBox hBoxTop = new HBox();
		hBoxTop.setStyle("-fx-background-color: #222222");
		hBoxTop.setPadding(new Insets(10, 10, 10, 10));
		hBoxTop.setSpacing(11);
		Button exercise1enconeBTN = new Button("Zadanie 1 szyfrowanie");
		Button exercise1decodeBTN = new Button("Zadanie 1 deszyfrowanie");
		Button exercise2enconeBTN = new Button("Zadanie 2 szyfrowanie");
		Button exercise2decodeBTN = new Button("Zadanie 2 deszyfrowanie");
		Button exercise3AenconeBTN = new Button("Zadanie 3A szyfrowanie");
		Button exercise3AdecodeBTN = new Button("Zadanie 3A deszyfrowanie");
		Button exercise3BenconeBTN = new Button("Zadanie 3B szyfrowanie");
		Button exercise3BdecodeBTN = new Button("Zadanie 3B deszyfrowanie");
		Button exercise4encodeAndDecodeBTN = new Button("Zadanie 4 szyfrowanie i deszyfrowanie");
		Button exercise5enconeBTN = new Button("Zadanie 5 szyfrowanie");
		Button exercise5decodeBTN = new Button("Zadanie 5 deszyfrowanie");
		hBoxTop.getChildren().addAll(exercise1enconeBTN, exercise1decodeBTN, exercise2enconeBTN, exercise2decodeBTN,
				exercise3AenconeBTN, exercise3AdecodeBTN);

		HBox hBoxBottom = new HBox();
		hBoxBottom.setStyle("-fx-background-color: #222222");
		hBoxBottom.setPadding(new Insets(10, 10, 10, 10));
		hBoxBottom.setSpacing(10);
		hBoxBottom.getChildren().addAll(exercise3BenconeBTN, exercise3BdecodeBTN, exercise4encodeAndDecodeBTN,
				exercise5enconeBTN, exercise5decodeBTN);

		exercise1enconeBTN.setOnAction(e -> {
			try {
				zadanie_1_encrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise1decodeBTN.setOnAction(e -> {
			try {
				zadanie_1_decrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise2enconeBTN.setOnAction(e -> {
			try {
				zadanie_2_encrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise2decodeBTN.setOnAction(e -> {
			try {
				zadanie_2_decrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise3AenconeBTN.setOnAction(e -> {
			try {
				zadanie_3_a_encrypt(textAreaLeft, textAreaRight);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		exercise3AdecodeBTN.setOnAction(e -> {
			try {
				zadanie_3_a_decrypt(textAreaLeft, textAreaRight);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		exercise3BenconeBTN.setOnAction(e -> {
			try {
				zadanie_3_b_encrypt(textAreaLeft, textAreaRight);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		exercise3BdecodeBTN.setOnAction(e -> {
			try {
				zadanie_3_b_decrypt(textAreaLeft, textAreaRight);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		exercise4encodeAndDecodeBTN.setOnAction(e -> {
			try {
				zadanie_4_encrypt_i_decrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise5enconeBTN.setOnAction(e -> {
			try {
				zadanie_5_encrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		exercise5decodeBTN.setOnAction(e -> {
			try {
				zadanie_5_decrypt(textAreaLeft, textAreaRight);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		borderPane.setTop(hBoxTop);
		borderPane.setLeft(vBoxLeft);
		borderPane.setCenter(vBoxCenter);
		borderPane.setRight(vBoxRight);
		borderPane.setBottom(hBoxBottom);

		Scene scene = new Scene(borderPane);

		arg0.setTitle("BSK_PS_1");
		arg0.setScene(scene);
		arg0.show();

	}

}
