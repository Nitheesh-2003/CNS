import java.util.*;

class HillCipherr {

	int[][] divideString(int keyLength, String s) {
		int[][] parts = new int[s.length() / keyLength][keyLength];
		int x = 0;
		for (int i = 0; i < s.length(); i = i + keyLength) {
			int tmp[] = new int[keyLength];
			for (int j = 0; j < keyLength; j++) {
				tmp[j] = s.charAt(i + j) - 'A';
			}
			parts[x] = tmp;
			x++;
		}
		return parts;
	}

	int[][] multiply(int[][] a, int[][] b) {
		int[][] ans = new int[a.length][b[0].length];
		int sum;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				sum = 0;
				for (int k = 0; k < a[0].length; k++) {
					sum = sum + (a[i][k] * b[k][j]);
				}
				ans[i][j] = sum;
			}
		}
		return ans;
	}

	int determinant(int[][] matrix) {
		if (matrix.length == 2) {
			return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
		}
		return 0;
	}

	int inverseModule(int[][] matrix) {
		int det = determinant(matrix);
		for (int i = 1; i <= 26; i++) {
			if (((det * i) % 26) == 1) {
				return i;
			}
		}
		return -1;
	}

	String encrypt(String plain, String key, int keyLength) {
		String cipher = "";
		int[][] dividedPlain = divideString(keyLength, plain);
		int[][] dividedKey = divideString(keyLength, key);

		for (int i = 0; i < dividedPlain.length; i++) {

			int[][] tmp = { dividedPlain[i] };
			int[][] cipherMatrix = multiply(tmp, dividedKey);
			for (int j = 0; j < cipherMatrix.length; j++) {
				for (int k = 0; k < cipherMatrix[0].length; k++) {
					cipherMatrix[j][k] %= 26;
					cipher += (char) ((cipherMatrix[j][k]) + 'A');
				}
			}
		}
		return cipher;
	}

	int[][] adjoint(int[][] a) {
		if (a.length == 2) {
			int tmp = a[0][0];
			a[0][0] = a[1][1];
			a[1][1] = tmp;
			a[0][1] = -a[0][1];
			a[1][0] = -a[1][0];
		}
		return a;
	}

	String decrypt(String cipher, String key, int keyLength) {
		String plain = "";
		int[][] dividedCipher = divideString(keyLength, cipher);
		int[][] dividedKey = divideString(keyLength, key);

		int[][] adjointOfKey = adjoint(dividedKey);
		int inverseMod = inverseModule(dividedKey);
		for (int i = 0; i < adjointOfKey.length; i++) {
			for (int j = 0; j < adjointOfKey[i].length; j++) {
				if (adjointOfKey[i][j] < 0) {
					adjointOfKey[i][j] += 26;
				}
				adjointOfKey[i][j] *= inverseMod;
				adjointOfKey[i][j] %= 26;
			}
		}

		int[][] inverseOfKey = adjointOfKey;

		for (int i = 0; i < dividedCipher.length; i++) {
			int[][] tmp = { dividedCipher[i] };
			int[][] cipherMatrix = multiply(tmp, inverseOfKey);
			for (int j = 0; j < cipherMatrix.length; j++) {
				for (int k = 0; k < cipherMatrix[0].length; k++) {
					cipherMatrix[j][k] %= 26;
					plain += (char) ((cipherMatrix[j][k]) + 'A');
				}
			}
		}
		return plain;
	}

}

public class HillCipher {
	public static void main(String[] args) {
		String plain, key;
		Scanner sc = new Scanner(System.in);
		HillCipherr hc = new HillCipherr();
		System.out.println("Enter the Plain Text : ");
		plain = sc.nextLine();
		System.out.println("Enter the key (Key length should be perfect square) : ");
		key = sc.nextLine();

		// The length of key string should be a perfect square
		double keyLength = Math.sqrt(key.length());
		if (Math.ceil(keyLength) != Math.floor(keyLength)) {
			System.out.println("Key length should be perfect square");
			return;
		}

		

		// Capitalize the plain text and key
		plain = plain.toUpperCase();
		key = key.toUpperCase();

		// Check whether the key is invertible
		int[][] dividedKey = hc.divideString((int) keyLength, key);
		int det = hc.determinant(dividedKey);
		if (det == 0) {
			System.out.println("Invalid Key!!! Key is not invertible because determinant is 0");
			return;
		}
		if (det % 2 == 0 || det % 13 == 0) {
			System.out.println("Invalid Key!!! Key is not invertible because determinant has common factor with 26");
			return;
		}
		if (hc.inverseModule(dividedKey) == -1) {
			System.out.println("Invalid Key!!! Key is not invertible");
			return;
		}
		
		
		// Add extra characters to plain text if key length and plain length doesnt
		// matches
		if ((plain.length() % keyLength) != 0) {
			int rem = (int) (plain.length() % keyLength);
			StringBuilder sb = new StringBuilder(plain);
			for (int i = 0; i < rem; i++) {
				sb.append('X');
			}
			plain = sb.toString();
		}
		System.out.println("The Modified Plain Text is : " + plain);

		System.out.println("Encrypting the plain text...");
		String cipher = hc.encrypt(plain, key, (int) keyLength);
		System.out.println("The Cipher Text is : " + cipher);

		System.out.println("Decrypting the cipher text...");
		String decryptedText = hc.decrypt(cipher, key, (int) keyLength);
		System.out.println("The Decrypted text is : " + decryptedText);
	}
}