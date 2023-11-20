import java.util.Scanner;

class Bob {
    public int p = 23; 
    public int q = 5;
    public int A;
    public int public_key;

    public int generatePublicKey() {
        public_key = (int) (Math.pow(q,A) % p);
        return public_key;
    }

    public int calculateSharedSecret(int otherPublicKey) {
        return (int) (Math.pow(otherPublicKey, A) % p);
    }
}

class Alice {
    public int p = 23; // Same prime number as Bob's
    public int q = 5;  // Same primitive root as Bob's
    public int B;
    public int public_key;

    public int generatePublicKey() {
        public_key = (int) (Math.pow(q, B) % p);
        return public_key;
    }

    public int calculateSharedSecret(int otherPublicKey) {
        return (int) (Math.pow(otherPublicKey, B) % p);
    }
}

public class Diffe {
    public static void main(String[] args) {
        Bob bob = new Bob();
        Alice alice = new Alice();

        bob.A = 6;  
        alice.B = 15; 

        int bobPublicKey = bob.generatePublicKey();
        int alicePublicKey = alice.generatePublicKey();

        System.out.println("Bob's Public Key: " + bobPublicKey);
        System.out.println("Alice's Public Key: " + alicePublicKey);

        // Bob and Alice calculate the shared secret
        int bobSharedSecret = bob.calculateSharedSecret(alicePublicKey);
        int aliceSharedSecret = alice.calculateSharedSecret(bobPublicKey);

        System.out.println("Shared Secret at Bob's side: " + bobSharedSecret);
        System.out.println("Shared Secret at Alice's side: " + aliceSharedSecret);

        if (bobSharedSecret == aliceSharedSecret) {
            System.out.println("Shared secrets match. Diffie-Hellman key exchange successful!");
        } else {
            System.out.println("Shared secrets do not match. Diffie-Hellman key exchange failed.");
        }
    }
}
