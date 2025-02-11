public class Sim2_HalfAdder {

    // Public input wires
    public RussWire a, b;

    // Public output wires
    public RussWire sum, carry;

    // Internal components
    private final XOR xorGate; // sum = a XOR b
    private final AND andGate; // carry = a AND b

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

    public void execute() {
        // Wire inputs to the XOR gate
        xorGate.a.set(a.get());
        xorGate.b.set(b.get());
        xorGate.execute();          // Execute the XOR gate 
        sum.set(xorGate.out.get()); // Set the sum output

        // Wire inputs to the AND gate
        andGate.a.set(a.get());
        andGate.b.set(b.get());
        andGate.execute();          // Execute the AND gate
        carry.set(andGate.out.get()); // Set the carry output
    }
}