/* 
 * Author: Muhammad Asifur Rahman
 * Course: CSC 252 - Spring 2025
 * netID: asifrahman
 * Program Description: This class simulates a Full Adder by combining two Half Adders and an OR gate. 
 *                      It takes three one-bit inputs: a, b, and carryIn. And it produces a sum and a
 *                      carryOut. The design implements the full adder logic by computing and
 *                      then adding the carryIn.
 */

 public class Sim2_FullAdder {

    // Public input wires
    public RussWire a, b, carryIn;
    // Public output wires
    public RussWire sum, carryOut;

    // Internal components
    private final Sim2_HalfAdder halfAdder1, halfAdder2;
    private final OR orGate;

    /**
     * Constructor for the Full Adder.
     * It initializes input and output wires and creates two HalfAdder objects and an OR gate.
     */
    public Sim2_FullAdder() {

        // Initialize the wires
        a = new RussWire();
        b = new RussWire();
        carryIn = new RussWire();
        sum = new RussWire();
        carryOut = new RussWire();

        // Create the gate objects
        halfAdder1 = new Sim2_HalfAdder();
        halfAdder2 = new Sim2_HalfAdder();
        orGate = new OR();
    }

    /**
     * Executes the full adder logic.
     * First, it adds a and b using the first half adder. Then, adds the resulting
     * sum with carryIn using the second half adder. Finally, computes carryOut by
     * OR-ing the carry outputs from both half adders.
     */
    public void execute() {
        // First half adder: add a and b
        halfAdder1.a.set(a.get());
        halfAdder1.b.set(b.get());
        halfAdder1.execute();

        // Second half adder: add the sum from the first half adder and carryIn
        halfAdder2.a.set(halfAdder1.sum.get());
        halfAdder2.b.set(carryIn.get());
        halfAdder2.execute();

        // Overall sum from second half adder
        sum.set(halfAdder2.sum.get());

        // Final carryOut is the OR of the two half adders carry outputs
        orGate.a.set(halfAdder1.carry.get());
        orGate.b.set(halfAdder2.carry.get());
        orGate.execute();
        carryOut.set(orGate.out.get());
    }
}
