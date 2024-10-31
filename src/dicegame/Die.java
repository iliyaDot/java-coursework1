package dicegame;

public class Die {

    // Number of sides on the die (default is 6 for a standard die)
    private int sides;
    
    // Current face value showing on the die (default is 1)
    private int face;

    //  constructor: create a standard 6-sided die with the initial face value of 1
    public Die() {
        sides = 6;
        face = 1;
    }

    // Constructor allowing custom number of sides; initializes face to 1
    public Die(int sides) {
        this.sides = sides;
        face = 1;
    }

    // Constructor allowing custom sides and face value if you want a specific initial face
    public Die(int sides, int face) {
        this.sides = sides;
        this.face = face;
    }

    // Getter method for the current face value of the die
    public int getFace() {
        return face;
    }

    // Setter method to set a specific face value for the die
    public void setFace(int face) {
        this.face = face;
    }

    // Getter method for the number of sides on the die
    public int getSides() {
        return sides;
    }

    
    public void setSides(int sides) {
        this.sides = sides;
    }

    // Roll  die by generating a random face value between 1 and the number of sides
    public void roll() {
        face = (int) (Math.random() * sides) + 1; // Randomly sets the face value
    }

    // Rolls the die and returns the resulting face value
    public int rollReturn() {
        roll(); // Calls the roll method to update the face
        return face; // Returns the new face value
    }
    
}
