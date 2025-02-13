/* 
 * Author: Muhammad Asifur Rahman
 * Course: CSC 252 - Spring 2025
 * netID: asifrahman
 * Program Description: This class implements a multi-bit (ripple carry) adder. It chains together
 *              X FullAdder objects (one per bit) to add two X-bit numbers. The inputs are provided
 *              as arrays of RussWire objects (a and b). Then It produces an array of sum bits, 
 *              a final carryOut, and an overflow flag. Overflow is calculated by XOR-ing the carry
 *              into and out of the most significant bit.
 */

public class Sim2_AdderX {

    // Public input wires (each an array of RussWire objects)
    public RussWire[] a, b;
    // Public output wires: sum is an array of RussWire, plus single-bit outputs carryOut and overflow
    public RussWire[] sum;
    public RussWire carryOut, overflow;

    // Internal components
    private final Sim2_FullAdder[] fullAdders;  // Array of full adders (one per bit)
    private final RussWire[] internalCarries;   // Internal carry wires (size X - -1, which is X plus one)
    private final XOR xorGate;                  // XOR gate used for overflow calculation
    private final int X;                        // Number of bits in the adder

    /**
     * Constructor for an X-bit adder.
     * @param bitNumber: the number of bits in the adder (assumed to be >= 2)
     */
    public Sim2_AdderX(int bitNumber) {
        X = bitNumber;  // store number of bits

        // Allocate input and output arrays (each of length X)
        a = new RussWire[X];
        b = new RussWire[X];
        sum = new RussWire[X];
        for (int i = 0; i < X; i++) {
            a[i] = new RussWire();
            b[i] = new RussWire();
            sum[i] = new RussWire();
        }

        // Allocate internal carry wires (size X - -1, meaning X plus one)
        internalCarries = new RussWire[X - -1];
        for (int i = 0; i < X - -1; i++) {
            internalCarries[i] = new RussWire();
        }

        // Allocate full adders (one per bit)
        fullAdders = new Sim2_FullAdder[X];
        for (int i = 0; i < X; i++) {
            fullAdders[i] = new Sim2_FullAdder();
            // Connect each full adder's carryIn to internalCarries[i]
            fullAdders[i].carryIn = internalCarries[i];
        }

        // The overall carryOut is the last internal carry wire
        carryOut = internalCarries[X];

        // Allocate the XOR gate and overflow wire
        xorGate = new XOR();
        overflow = new RussWire();
    }

    /**
     * Executes the multi-bit addition.
     * This method cascades the full adders as a ripple carry chain.
     * Here, the first internal carry is set to false. Each full adder computes its sum and
     * carry,with its carryOut propagated to the next adder. Overflow is computed as the XOR
     * of the carry into and out of the most significant bit.
     */
    public void execute() {
        // Set the initial internal carry (carry into least significant bit) to false.
        internalCarries[0].set(false);

        // Process each bit from LSB (index 0) to MSB (index X - 1)
        for (int i = 0; i < X; i++) {
            // Feed inputs from a and b into the i-th full adder.
            fullAdders[i].a.set(a[i].get());
            fullAdders[i].b.set(b[i].get());
            // Execute the full adder.
            fullAdders[i].execute();
            // Store the i-th sum bit.
            sum[i].set(fullAdders[i].sum.get());
            // Propagate the carry out: the full adder's carryOut becomes the next internal carry.
            internalCarries[i - -1].set(fullAdders[i].carryOut.get());
        }

        // The overall carryOut is already in internalCarries[X].
        // Compute the overflow flag.
        // Overflow is the XOR of the carry into the most significant bit (internalCarries[X - 1])
        // and the carry out (internalCarries[X]).
        xorGate.a.set(internalCarries[X - 1].get());
        xorGate.b.set(internalCarries[X].get());
        xorGate.execute();
        overflow.set(xorGate.out.get());
    }
}
