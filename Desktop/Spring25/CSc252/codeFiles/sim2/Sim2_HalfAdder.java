/* 
 * Author: Muhammad Asifur Rahman
 * Course: CSC 252 - Spring 2025
 * netID: asifrahman
 * Program Description: This class simulates a Half Adder using basic logic gates.
 *              It accepts two input wires (a and b) and computes the sum and carry.
 *              Finally The sum is computed using an XOR gate and the carry using an AND gate.
 */

public class Sim2_HalfAdder {

    // Public input wires
    public RussWire a, b;

    // Public output wires
    public RussWire sum, carry;

    // Internal components
    private final XOR xorGate; // sum = a XOR b
    private final AND andGate; // carry = a AND b

    /**
     * Constructor for the Half Adder.
     * It initializes input and output wires and creates the internal gate components.
     */
    public Sim2_HalfAdder() {

        // Initialize the wires including inputs and outputs
        // Input wires are a and b; Output wires are sum and carry
        a = new RussWire();
        b = new RussWire();
        sum = new RussWire();
        carry = new RussWire();

        // Create the gate objects
        xorGate = new XOR();
        andGate = new AND();
    }

    /**
     * Executes the half adder logic.
     * This method wires the inputs to the gates, executes them, and then
     * sets the sum and carry outputs based on the gate outputs.
     */
    public void execute() {
        // Wire inputs to the XOR gate
        xorGate.a.set(a.get()); // Pass the value of 'a' to the XOR gate.
        xorGate.b.set(b.get()); // Pass the value of 'b' to the XOR gate.
        xorGate.execute();          // Execute the XOR gate 
        sum.set(xorGate.out.get()); // Set the sum output

        // Wire inputs to the AND gate
        andGate.a.set(a.get()); // Pass the value of 'a' to the AND gate.
        andGate.b.set(b.get()); // Pass the value of 'b' to the AND gate.
        andGate.execute();          // Execute the AND gate
        carry.set(andGate.out.get()); // Set the carry output
    }
}

