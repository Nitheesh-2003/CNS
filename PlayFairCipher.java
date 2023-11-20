import java.util.*;

public class PlayFairCipher {
	public static char[][] playfair;
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the key:");
		String key = sc.nextLine().toUpperCase();
		System.out.println("Enter the plaintext:");
		String plain = sc.nextLine().toUpperCase();
		
		playfair = generateMatrix(key);
		
		String encryptedText = encrypt(plain);
		System.out.println(encryptedText);
		String decryptedText = decrypt(encryptedText);
		System.out.println(decryptedText);
		
        sc.close();
	}
	
	public static char[][] generateMatrix(String key){
		char[][] playfair = new char[5][5];
		String keyString = key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		keyString = keyString.replaceAll("J", "I");
		keyString = keyString.replaceAll(" ", "");
		
		boolean[] used = new boolean[26];
		int i=0,j=0;
		
		for(char ch : keyString.toCharArray()) {
			if(!used[ch - 'A']) {
				playfair[i][j] = ch;
				used[ch-'A']=true;
				j++;
				if(j == 5) {
					j=0;
					i++;
				}
			}
		}
		return playfair;
	}
	
	public static int[] findPosition(char ch) {
		int[] pos = new int[2];
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(playfair[i][j] == ch) {
					pos[0]=i;
					pos[1]=j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	public static String encrypt(String plain) {
		StringBuilder res = new StringBuilder();
		
		for(int i=0;i<plain.length();i+=2) {
			char first = plain.charAt(i);
			char second = (i+1 < plain.length())? plain.charAt(i+1) : 'X';
			
			int[] firstPos = findPosition(first);
			int[] secondPos = findPosition(second);
			
			if(firstPos[0] == secondPos[0]) {
				res.append(playfair[firstPos[0]][(firstPos[1]+1)% 5]);
				res.append(playfair[secondPos[0]][(secondPos[1]+1)% 5]);
			}
			else if(firstPos[1] == secondPos[1]) {
				res.append(playfair[(firstPos[0]+1) % 5][firstPos[1]]);
				res.append(playfair[(secondPos[0]+1) % 5][secondPos[1]]);
			}
			else {
				res.append(playfair[firstPos[0]][secondPos[1]]);
				res.append(playfair[secondPos[0]][firstPos[1]]);
			}
		}
		return res.toString();
	}
	public static String decrypt(String cipher) {
		StringBuilder res = new StringBuilder();
		
		for(int i=0;i<cipher.length();i+=2) {
			char first = cipher.charAt(i);
			char second = cipher.charAt(i+1);
			
			int[] firstPos = findPosition(first);
			int[] secondPos = findPosition(second);
			
			if(firstPos[0] == secondPos[0]) {
				res.append(playfair[firstPos[0]][(firstPos[1]-1 +5)% 5]);
				res.append(playfair[secondPos[0]][(secondPos[1]-1 +5)% 5]);
			}
			else if(firstPos[1] == secondPos[1]) {
				res.append(playfair[(firstPos[0]-1 + 5) % 5][firstPos[1]]);
				res.append(playfair[(secondPos[0]-1 + 5) % 5][secondPos[1]]);
			}
			else {
				res.append(playfair[firstPos[0]][secondPos[1]]);
				res.append(playfair[secondPos[0]][firstPos[1]]);
			}
		}
		return res.toString();
	}

}